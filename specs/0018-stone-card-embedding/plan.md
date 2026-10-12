# Feature 0018 Technical Plan

Status: Prepared for implementation after a separately authorized requested
batch passes T0018-001. No code or runtime checks have been executed.

## References and Constraints

Read [spec.md](spec.md), [acceptance.md](acceptance.md),
[ADR-0010](../../docs/adr/0010-stone-card-embedding.md), the constitution and
root/backend instructions before execution. Follow backend AGENTS_CODE_CONVENTION.md.
This plan defines technical choices within the accepted scope, not additional
product features. Acceptance criteria live exclusively in acceptance.md.
No dependency or pinned version changes are planned; do not read pom.xml,
build outputs or framework configuration to make speculative changes.

## Reuse and Service Boundaries

Reuse StoneDocumentEmbeddingService, StoneDocumentEmbeddingCriteria,
StoneDocumentEmbeddingEntity and StoneDocumentIndexSettings as internal
dependencies. Their document-oriented names do not require renaming/refactoring.
OllamaEmbeddingRepository and its input mapper already supply the selected model,
1024 dimensions, truncate=false, query instruction, digest profile and timeouts.
Leave these files unchanged. Reuse the existing protocol-independent
StoneDocumentDependencyUnavailableException for provider/profile failures;
translate database failures to that dependency exception at the new service boundary.

Internal APIs:

| Service | Method | Shared input/output |
| --- | --- | --- |
| StoneCardEmbeddingService | save | StoneCardSaveRequest -> StoneCardSaveResponse |
| StoneCardRetrievalService | search | StoneCardSearchRequest -> StoneCardSearchResponse |

StoneCardSaveRequest has five fields: Long stoneId, String name, StoneType
stoneType, StoneSize stoneSize and String biography. Use a class with a no-argument
constructor/getters/setters. Save response contains stoneId only. Search request
is a two-field record (query, limit); search response wraps a matches list of
StoneCardMatchResponse records (stoneId, score). These are internal DTOs, not
new HTTP resources. Validate inputs in these services because no controller
Bean Validation boundary exists. Use InvalidStoneCardRequestException for
invalid inputs/settings, without HTTP coupling.

## Rendering and Mapping

StoneCardSaveRequestToStoneCardEmbeddingEntityMapper creates a mutable class
target and renders exactly:

```text
Name: <name>
Stone type: <StoneType.name()>
Size: <StoneSize.name()>
Biography: <biography or empty string>
```

Use LF separators, no trailing newline and no trimming or inferred content.
StoneCardEmbeddingEntity has stoneId, text, embeddingProfile and embedding
(double[]), with no-argument constructor and accessors. Service validation
precedes mapping. Generate card embedding with query=false, then set the profile
and vector from the ready embedding result. The repository returns the saved
entity; StoneCardEmbeddingEntityToStoneCardSaveResponseMapper creates its response.

## Persistence and Transactions

Add 0018-create-stone-card-embedding.xml and include it in the master changelog.
Do not edit earlier changesets. Table stone_card_embedding has:

| Column | Definition |
| --- | --- |
| stone_id | bigint primary key |
| text | text not null |
| embedding_profile | text not null |
| embedding | vector(1024) not null |

Use the existing vector extension and JDBC pattern from StoneDocumentIndexRepository.
No foreign key or JPA association to stone: this independent projection must not
add existence checks or affect catalog deletion. Do not add timestamps, lifecycle
flags, outbox records or ANN indexes. An atomic INSERT ... ON CONFLICT (stone_id)
DO UPDATE replaces text/profile/vector together; the primary key handles concurrent
saves. Return the complete saved row through the repository's save method.

StoneCardEmbeddingService has class-level @Transactional; its save method suspends
an ambient transaction using NOT_SUPPORTED while mapping/embedding. After preparation,
call StoneCardEmbeddingTransactionService.save to perform the short write transaction
through its separately injected service proxy. Do not hold a database transaction
or lock while calling Ollama. A failed embedding leaves the previous row alone;
atomic SQL and rollback prevent a partially replaced row. Do not implement
idempotency-key, source-version ordering or retry infrastructure.

## Search and Configuration

StoneCardSearchSettings reads STONE_CARD_SEARCH_MIN_SIMILARITY from the process
environment, default 0.5. Validate an explicitly supplied value as finite and
within [-1, 1]; empty/malformed input is invalid. Add only that documented entry
to .env.example; do not edit .env or framework settings.

StoneCardRetrievalService.search embeds query=true outside a database transaction
and sends StoneCardSearchCriteria(vector, profile, limit, minimumSimilarity)
to StoneCardEmbeddingRepository.search. Keep SQL in the repository. In one short
read-only operation, detect incompatible stored profiles and reject rather than
compare different vector spaces; use one consistent database snapshot for the
profile guard and results (a read-only REPEATABLE_READ transaction in the repository).
Compute score as 1 - (embedding <=> queryVector), filter score >= threshold and
order by score DESC, stone_id ASC before LIMIT. No catalog join or status filter.
Use SQL bind parameters, with the existing vector string serialization pattern.

Return StoneCardSearchResultEntity wrapping Match(stoneId, score) entries, then
use StoneCardSearchResultEntityToStoneCardSearchResponseMapper to build the
shared wrapper and match DTOs. Records have at most four fields. Protect vector
and list boundaries with copies where needed; do not expose mutable repository
results through response mapping. New services remain independent of HTTP and
JPA query APIs. Class-level @Transactional remains the default; search/provider
operations declare read-only NOT_SUPPORTED as necessary.

## Environment and Verification

T0018-001 is mandatory for every requested implementation batch. Expected pins:
JDK 23.0.2 selected by command-scoped JAVA_HOME, Maven wrapper 3.3.4/Maven 3.9.9,
PostgreSQL 18.6/pgvector 0.8.6 and the existing immutable Testcontainers image
from ADR-0001. Reuse the user-approved installed Ollama 0.40.3 from feature 0017,
with installed qwen3-embedding:0.6b; verify these states again, do not assume them.
Do not download models or change runtime versions under this plan.

Supply authorized endpoints/settings to the agent's own processes, including
STONE_DOCUMENT_OLLAMA_BASE_URL and any required existing Spring environment
settings. Reuse STONE_DOCUMENT_CONNECT_TIMEOUT (PT30S) and
STONE_DOCUMENT_EMBEDDING_TIMEOUT (PT30M), using injected Clock for deadlines.
Do not change document MinIO source settings. Required values absent from the
process can be supplied from authorized local configuration; report secrets
only as present/missing. Do not invent values or require terminal exports.

Inspect only the relevant endpoint/configuration declarations when needed for
readiness; do not scan unrelated configuration. Check database connectivity with
an authenticated read-only query, server version and vector extension; inspect
Docker availability and pinned image metadata for Testcontainers. Verify Ollama
/api/version and /api/tags/model digest without generating embeddings. Check
backend/frontend health at their configured endpoints and report stopped services
and task impact. Redis/MinIO/Kafka are not direct card dependencies; if a focused
Spring test context needs them through existing startup wiring, establish that
dependency and verify its actual configured endpoint before the batch starts.
Do not declare them ready from process/container presence alone.

The agent may start required existing services using approved configuration,
preserving existing data. Record startup separately from read-only checks.
Missing models/configuration or unverifiable prerequisites block affected batch
tickets. No import, catalog save, business test or inference is a readiness check.
Unit-only batches require tools/source/configuration checks, with all application
service states reported; their unrelated stopped services are not silently
treated as blockers. Persistence batches need Docker/database prerequisites;
T0018-006 additionally needs the real Ollama model endpoint.

Run only the task's named selector using command-scoped JAVA_HOME and necessary
endpoints/environment, from stone-shelter-api: ./mvnw -Dtest=<selector> test.
No full suite or broad verify command is authorized. Tests create/clean their
own data. The basic real query uses an isolated Testcontainer, not the live catalog.

## Exact Task File Allowlists and Focused Checks

All paths below are repository-relative. J = stone-shelter-api/src/main/java/lab/stoneshelter;
T = stone-shelter-api/src/test/java/lab/stoneshelter;
D = stone-shelter-api/src/main/resources/db/changelog.
Each task may additionally update only this feature's tasks.md and acceptance.md
to record its own actual status/evidence. No other documentation or code files
may be modified during that task without a revised authorized boundary.

### T0018-001: Environment readiness

Writable: specs/0018-stone-card-embedding/tasks.md and acceptance.md only.
Permitted checks: read-only tools, configuration presence, service health,
connectivity and source/model/image metadata; no application test selector.
Record the complete requested batch, component expectations/observations, any
preparation, READY/BLOCKED result and affected task IDs/corrective actions.

### T0018-002: Text, DTOs, settings and mapping

Writable:

- J/shared/StoneCardSaveRequest.java
- J/shared/StoneCardSaveResponse.java
- J/entities/StoneCardEmbeddingEntity.java
- J/exceptions/InvalidStoneCardRequestException.java
- J/services/StoneCardSearchSettings.java
- J/mappers/entities/StoneCardSaveRequestToStoneCardEmbeddingEntityMapper.java
- J/mappers/dtos/StoneCardEmbeddingEntityToStoneCardSaveResponseMapper.java
- T/StoneCardMappingTest.java
- .env.example (add the threshold entry only)

Selector: StoneCardMappingTest. Focus: deterministic field projection, null/empty
biography, mapper null contracts and valid/default/invalid threshold settings.

### T0018-003: Persistence foundation

Writable:

- D/0018-create-stone-card-embedding.xml
- D/db.changelog-master.yaml (append the new include only)
- J/repositories/StoneCardEmbeddingRepository.java
- T/StoneCardEmbeddingPersistenceTest.java

Selector: StoneCardEmbeddingPersistenceTest. Focus: new migration/upsert, replacing
all stored fields, uniqueness under concurrent saves, confirmed rollback and an
independent projection without a catalog row. No application lifecycle hooks.

### T0018-004: Internal save service

Writable:

- J/services/StoneCardEmbeddingService.java
- J/services/StoneCardEmbeddingTransactionService.java
- T/StoneCardEmbeddingServiceTest.java

Selector: StoneCardEmbeddingServiceTest. Focus: snapshot validation, reuse of the
embedding service with query=false, no persistence on embedding failure, atomic
replacement and failure propagation. Verify through Spring service proxies that
provider work has no active database transaction and writing uses the separate
transaction service. Mock Ollama; use Testcontainers where persistence is checked.

### T0018-005: Internal semantic retrieval

Writable:

- J/repositories/StoneCardEmbeddingRepository.java (add search only)
- J/criteria/StoneCardSearchCriteria.java
- J/entities/StoneCardSearchResultEntity.java
- J/shared/StoneCardSearchRequest.java
- J/shared/StoneCardSearchResponse.java
- J/shared/StoneCardMatchResponse.java
- J/mappers/dtos/StoneCardSearchResultEntityToStoneCardSearchResponseMapper.java
- J/services/StoneCardRetrievalService.java
- T/StoneCardRetrievalTest.java
- T/StoneCardEmbeddingArchitectureTest.java

Selectors: StoneCardRetrievalTest,StoneCardEmbeddingArchitectureTest.
Focus: deterministic cosine vectors verify inclusive threshold, limit/order/ties,
empty results, incompatible profiles, query validation/provider flags, dependency
errors and response mapping. Architecture checks target the new types only.
Mock Ollama; database checks use the existing pinned Testcontainer.

### T0018-006: Basic real-model check and acceptance review

Writable:

- T/StoneCardEmbeddingBasicQueryTest.java
- specs/0018-stone-card-embedding/tasks.md
- specs/0018-stone-card-embedding/acceptance.md

Selector: StoneCardEmbeddingBasicQueryTest only. Implement the single AC-011
fixture using the installed provider and isolated database, default threshold
0.5 and cleanup. Review prior focused evidence against all acceptance criteria
without rerunning successful checks whose inputs did not change. Record outcomes
and limitations; do not add a query-quality suite or modify production code here.
