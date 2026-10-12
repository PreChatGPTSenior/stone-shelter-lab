# Feature 0017 Technical Plan

Status: Ready for staged development. Documentation preparation is complete;
application code, model calls, imports and runtime tests have not started.

Requirements: [spec.md](spec.md). Decisions:
[ADR-0009](../../docs/adr/0009-markdown-document-indexing.md).
All acceptance criteria and query oracles live in [acceptance.md](acceptance.md).
Execute only a separately requested task; no commits or pushes are authorized.

## Existing Integration Points and Dependencies

- The backend already uses MinIO SDK and OkHttp in MinioPhotoStorageService.
  Use those libraries for document reading and Ollama calls, without extending
  the photograph service's bucket rules or cleanup behavior.
- Existing OpenAPI code/tests use Jackson JSON support. Reuse available JSON
  support rather than adding an SDK, RAG framework, tokenizer or Markdown library.
  Compilation in the first code task verifies the chosen existing APIs.
- PostgreSQL/pgvector is already enabled by feature 0014. Existing IntegrationTest
  supplies the pinned PostgreSQL/pgvector Testcontainer; reuse it for database tests.
- Use repository-owned JDBC for vector bindings and queries. Do not introduce
  a vector ORM dependency or pass SQL/JPA APIs into services.
- Keep the project's pinned versions; no pom.xml, build file, application
  configuration, host runtime or existing migration is changed by this plan.

## Components and Layer Boundaries

1. StoneEncyclopediaController binds UUID-v4 Idempotency-Key, rejects nonempty
   bodies, owns statuses and returns ready shared DTOs. Bodyless methods pass
   their validated scalar header to the service; no empty Request DTO is created.
2. StoneEncyclopediaIndexService coordinates admission, source reading, processing,
   embedding and publication. It does not depend on HTTP statuses, servlet APIs,
   repositories' SQL details or JPA query types.
3. StoneDocumentIndexTransactionService owns short transactional operations:
   claim, publication/deletion, confirmed failure and reconciliation.
4. StoneMarkdownDocumentService handles heading/topic structure and transient
   text processing. Entity-target mappers produce fragment entities.
5. MinioStoneDocumentSourceRepository performs the fixed object read.
   OllamaEmbeddingRepository owns provider HTTP/JSON transport; services receive
   entities and translate provider unavailability into protocol-independent errors.
6. StoneDocumentIndexRepository owns metadata/fragments, vector SQL, operation
   ledger and atomic guard updates. DTO-target mappers produce ready import,
   delete and internal search responses.
7. ApiExceptionHandler alone translates application exceptions into ProblemDetail.
   Services and custom exceptions remain independent of protocol error handling.

All types stay in the allowed responsibility packages. Mapper names identify
their complete source and target types and inherit the existing null policy.
Use classes with no-argument constructors and setters for entity targets and
types with more than four fields; do not bypass the established base mappers.

### Dynamic Success Status Without ResponseEntity

Keep exactly three import JSON properties. A nonserialized domain outcome
(CREATED or REPLACED) accompanies the ready response for controller status
selection; hide it from JSON and generated schema. Persist that outcome with
the historical result so replay preserves the original status without service
dependencies on HttpStatus. Default creation mapping uses @ResponseStatus(CREATED);
the controller adjusts HttpServletResponse to 200 for replacement/replay as needed.
No DTO/entity mapping or business decision occurs in the controller.

## Persistence

Add one new Liquibase changeset, registered through the existing root changelog
only if its inclusion strategy requires registration. Never edit an applied
changeset, including 0014-enable-vector-extension.xml.

| Table | Purpose |
| --- | --- |
| stone_document_index | Stable document identity; published hash/profile/count; active operation and ownership token |
| stone_document_fragment | Ordered derived text, heading provenance, optional stone type, source hash/profile and vector(1024) |
| stone_document_index_operation | UUID key, IMPORT/DELETE kind, owner, RUNNING/SUCCEEDED/FAILED/EXPIRED state and historical result/expiry |

Use one document row as the permanent coordination anchor; deletion clears the
published projection/metadata but retains the anchor and operation markers.
Fragment order is unique within the document. SourceVersion is lowercase
hexadecimal SHA-256 of the exact source bytes. Keep the embedding profile with
the projection so queries cannot compare incompatible model spaces.

Use prepared JDBC parameters and explicit vector casts, with finite-value and
dimension validation before persistence. Initial retrieval uses exact cosine
distance ordering and a bounded result count. ANN indexing, benchmark work and
query-speed tuning are outside this delivery.

Do not create raw-source columns, snapshot tables, backup objects or disk caches.
Read source bytes transiently, process them and release them after the attempt.

## Admission, Ownership and Recovery

### Short Admission Transaction

- Look up the UUID key and operation identity first. Reconstruct retained success
  without source/model access; reject failed/expired/mismatched used keys.
- For new mutations, use a repository-owned nonblocking transaction advisory
  lock in this feature's namespace, then inspect the document's active slot.
  This serializes claim/publication decisions without queuing a second pipeline.
- A busy slot returns the selected 409 and does not consume a new key. Claim
  RUNNING and the active owner token atomically before reading the source.
- The active slot remains persistent throughout remote work; do not use a timed
  lease that automatically admits another worker after expiration.

### Remote Work and Atomic Publication

Read one fixed MinIO object with a bounded, closed stream; do not list buckets,
follow Markdown links or load referenced files. Hash bytes, decode source text,
split/validate fragments, then obtain embeddings. Stage only derived entities
in memory; publish nothing until the entire candidate projection is ready.

Before publication, verify the operation is RUNNING, its ownership token still
matches and its deadline permits progression. A short transaction replaces all
fragments, updates published metadata, saves the successful domain result and
clears the active slot together. DELETE uses the same ownership/result protocol.
Repositories reject stale-owner writes even if an earlier worker resumes.

Services retain class-level @Transactional defaults. The long orchestration
method explicitly suspends transactions with NOT_SUPPORTED; separate transaction
service calls use normal short transactions. Remote I/O must not run inside a
two-hour database transaction.

### Failed, Expired and Interrupted Operations

Confirmed failure becomes FAILED and clears ownership only after that worker
exits; another attempt needs a new key. Uncertain commit outcomes are reconciled
against authoritative operation state before failure is recorded. Retain active
ownership when the old execution/commit outcome cannot be established.

Recovery runs only after the prior owner is verified stopped, as required by
the spec. Controlled shutdown/restart of the existing single-backend local setup
can establish that condition; a different instance UUID or expired clock alone
cannot. Do not silently clear another live process's slot or claim distributed
failover support. A recovery method with an explicit verified-stopped owner input
reconciles committed success or marks uncommitted work failed; no public recovery
endpoint or automatic reimport is introduced. An uncertain owner remains blocked.

After three days, remove historical response fields and retain EXPIRED identity
markers indefinitely. Perform logical expiry checks on every replay; compact
expired response data opportunistically in short repository operations, without
deleting markers or introducing a scheduled job. Inject a clock for expiry and
deadline tests, rather than waiting days/hours.

## Provider and Token Qualification

Use Ollama /api/embed with qwen3-embedding:0.6b, dimensions 1024 and truncate=false.
Use single-input calls initially so prompt_eval_count can qualify input-token
accounting per passage. Ignore provider duration fields; do not collect benchmark
results. Confirm actual model identity/profile, input-limit errors and finite
1024-element output before adding the schema.

The splitter's 500/50 token settings are initial targets at meaningful boundaries,
not a claim that character count is an exact Qwen tokenizer. Qualify a lightweight
English estimator against provider token counts; reject hard model-limit excess
instead of silent truncation. If exact tokenization requires a new dependency,
stop and revise its separately authorized ticket with a reason and explicit
version; do not add one speculatively.

Format queries with the selected English Qwen retrieval instruction and keep the
document/query profile compatible. Profile metadata must identify the model used
for indexing; an incompatible current profile cannot query those vectors silently.

## Timeouts, Environment and Operator Preconditions

Use configurable defaults: connection PT30S, MinIO read PT5M, one embedding
request PT30M, import deadline PT2H. Each remote call is bounded by the remaining
operation deadline. Cancellation/deadline tests use controlled shorter settings;
the feature has no duration benchmark or speed acceptance threshold.

Introduce source/provider settings through environment variables, using a
feature-local settings class and .env.example entries. Addresses/credentials have
no hardcoded fallback, are validated when the operation needs them and are never
logged. Missing required settings become dependency-unavailable failure rather
than performing host setup or networking during application construction.

Environment entries: STONE_DOCUMENT_MINIO_ENDPOINT, STONE_DOCUMENT_MINIO_ACCESS_KEY,
STONE_DOCUMENT_MINIO_SECRET_KEY, STONE_DOCUMENT_OLLAMA_BASE_URL,
STONE_DOCUMENT_CONNECT_TIMEOUT, STONE_DOCUMENT_MINIO_READ_TIMEOUT,
STONE_DOCUMENT_EMBEDDING_TIMEOUT, STONE_DOCUMENT_IMPORT_TIMEOUT,
STONE_DOCUMENT_MAX_SOURCE_BYTES and STONE_DOCUMENT_MAX_FRAGMENTS.
The fixed bucket, object, documentKey and model remain the selected source profile.

The operator provisions the bucket/object, available model and environment before
runtime qualification. Model download, bucket upload and stack changes are not
executed by documentation preparation. Initially invoke the backend directly;
any HTTP client/proxy actually used must permit the import deadline plus response
margin. Do not modify frontend/proxy files merely to document that requirement.

## Task File Allowlists and Focused Checks

Path aliases below expand exactly; they are not wildcard permissions:

- M = stone-shelter-api/src/main/java/lab/stoneshelter/
- T = stone-shelter-api/src/test/java/lab/stoneshelter/
- R = stone-shelter-api/src/test/resources/stone-document-index/
- D = stone-shelter-api/src/main/resources/db/changelog/
- F = specs/0017-markdown-document-indexing/
- ADR = docs/adr/0009-markdown-document-indexing.md

For every later task, F/acceptance.md and F/tasks.md may record actual evidence
and completion, and ADR may record actual qualification evidence. This does not
permit new requirements or unrelated edits. All other writable files are listed
under their task. Only create a listed file when its task needs it.

### T0017-001 — Documentation Preparation

Files: ADR; F/spec.md; F/acceptance.md; F/plan.md; F/tasks.md.
Checks: local links, unchanged task IDs, query IDs/oracles, decision consistency
and diff whitespace. No application test or runtime operation.

### T0017-002 — Functional Embedding Qualification

Files: M/repositories/OllamaEmbeddingRepository.java;
M/services/StoneDocumentEmbeddingService.java;
M/services/StoneDocumentIndexSettings.java;
M/entities/StoneDocumentEmbeddingInputEntity.java;
M/entities/StoneDocumentEmbeddingEntity.java;
M/criteria/StoneDocumentEmbeddingCriteria.java;
M/mappers/entities/StoneDocumentEmbeddingCriteriaToStoneDocumentEmbeddingInputEntityMapper.java;
M/exceptions/StoneDocumentDependencyUnavailableException.java;
T/StoneDocumentEmbeddingTest.java; T/StoneDocumentEmbeddingQualificationTest.java;
T/StoneDocumentHttpTestSupport.java; .env.example.

Checks: StoneDocumentEmbeddingTest for provider JSON, dimensions, finite values,
truncate=false and timeout errors; separately selected
StoneDocumentEmbeddingQualificationTest for the available local model and
representative retrieval vectors. Test support uses the JDK local HTTP server,
not a new mocking-server library. Do not capture latency or download a model.

### T0017-003 — Database Projection and Operation Ledger

Files: D/0017-create-stone-document-index.xml; D/db.changelog-master.yaml;
M/entities/StoneDocumentIndexEntity.java;
M/entities/StoneDocumentFragmentEntity.java;
M/entities/StoneDocumentIndexOperationEntity.java;
M/enums/StoneDocumentIndexOperationKind.java;
M/enums/StoneDocumentIndexOperationState.java;
M/enums/StoneDocumentIndexOutcome.java;
M/repositories/StoneDocumentIndexRepository.java;
T/StoneDocumentIndexPersistenceTest.java.

Checks: StoneDocumentIndexPersistenceTest with the existing PostgreSQL/pgvector
Testcontainer; migration, vector binding, identity/ordinal constraints and
operation retention without raw-source columns. No full suite.

### T0017-004 — Markdown Processing

Files: M/services/StoneMarkdownDocumentService.java;
M/criteria/StoneMarkdownDocumentCriteria.java;
M/criteria/StoneMarkdownSectionCriteria.java;
M/mappers/entities/StoneMarkdownSectionCriteriaToStoneDocumentFragmentEntityMapper.java;
M/exceptions/StoneDocumentSourceUnindexableException.java;
T/StoneMarkdownDocumentTest.java.

Checks: StoneMarkdownDocumentTest using small synthetic strings and the existing
repository encyclopedia read directly. Cover exclusions, context/provenance,
shared tables, comparison rows, meaningful splits, overlap and fragment limits;
do not save a copied encyclopedia fixture.

### T0017-005 — MinIO Read and Prepared Embeddings

Files: M/repositories/MinioStoneDocumentSourceRepository.java;
M/entities/StoneDocumentSourceEntity.java;
M/services/StoneDocumentPreparationService.java;
M/services/StoneDocumentIndexSettings.java;
M/services/StoneDocumentEmbeddingService.java;
M/exceptions/StoneDocumentSourceNotFoundException.java;
T/StoneDocumentSourceTest.java; T/StoneDocumentHttpTestSupport.java; .env.example.

Checks: StoneDocumentSourceTest for fixed-object reading, stream closure, source
hash, read limits, missing object and generation failure with staged entities.
Reuse existing SDK/HTTP support. No source upload, bucket creation or raw cache.

### T0017-006 — Owned Lifecycle, Replay and Recovery

Files: M/services/StoneEncyclopediaIndexService.java;
M/services/StoneDocumentIndexTransactionService.java;
M/repositories/StoneDocumentIndexRepository.java;
M/criteria/StoneDocumentIndexOperationCriteria.java;
M/mappers/entities/StoneDocumentIndexOperationCriteriaToStoneDocumentIndexOperationEntityMapper.java;
M/shared/StoneEncyclopediaIndexResponse.java;
M/shared/StoneEncyclopediaDeleteResponse.java;
M/mappers/dtos/StoneDocumentIndexOperationEntityToStoneEncyclopediaIndexResponseMapper.java;
M/exceptions/StoneDocumentIndexConflictException.java;
T/StoneDocumentIndexLifecycleTest.java.

Checks: StoneDocumentIndexLifecycleTest with PostgreSQL and controlled source/
embedding collaborators; atomic replacement/deletion, same-key replay, expiry,
cross-kind conflicts, busy admission, failed-key rejection, deadline/ownership,
stale publication and verified-stopped-owner recovery. Use a test clock.

### T0017-007 — HTTP and Generated OpenAPI

Files: M/controllers/StoneEncyclopediaController.java;
M/exceptions/InvalidStoneDocumentIndexRequestException.java;
M/handlers/ApiExceptionHandler.java;
M/shared/StoneEncyclopediaIndexResponse.java;
T/StoneEncyclopediaControllerTest.java;
T/StoneDocumentIndexArchitectureTest.java;
T/OpenApiDocumentationTest.java.

Checks: StoneEncyclopediaControllerTest; StoneDocumentIndexArchitectureTest;
OpenApiDocumentationTest; ArchitectureTest. These are explicit focused selectors,
not permission for all tests. Extend the existing exhaustive OpenAPI route list
for this specified route and verify only the selected response properties,
bodyless methods, required header, statuses and ProblemDetail.

### T0017-008 — Internal Retrieval and Quality Fixture

Files: M/services/StoneDocumentRetrievalService.java;
M/criteria/StoneDocumentSearchCriteria.java;
M/entities/StoneDocumentSearchResultEntity.java;
M/repositories/StoneDocumentIndexRepository.java;
M/shared/StoneDocumentSearchRequest.java;
M/shared/StoneDocumentSearchResponse.java;
M/shared/StoneDocumentPassageResponse.java;
M/mappers/dtos/StoneDocumentSearchResultEntityToStoneDocumentSearchResponseMapper.java;
T/StoneDocumentRetrievalTest.java;
T/StoneDocumentRetrievalQualificationTest.java;
R/retrieval-queries.json.

Checks: StoneDocumentRetrievalTest for repository ordering/profile boundaries
and mapped wrapper responses; separately selected
StoneDocumentRetrievalQualificationTest for the real local-model 20-query fixture.
Use the acceptance oracles unchanged. No public search route or speed benchmark.

### T0017-009 — Acceptance Audit

Files: F/acceptance.md; F/tasks.md; ADR only.
Checks: review existing task evidence and run only unresolved relevant selectors
listed above. Do not repeat successful checks whose inputs have not changed;
do not repair application files under this audit ticket or run a full suite.
Record any necessary fix in a separately scoped ticket before changing code.

## Development Readiness

No product question blocks starting the staged work. T0017-001 is complete as
documentation preparation; T0017-002 is the next task and must functionally
qualify the embedding setup before T0017-003 schema implementation. Source/model
availability is an execution precondition, not a claimed verified fact.
Neither performance analysis nor runtime model downloads are prerequisites to
writing the first scoped code; functional qualification still gates the schema.
