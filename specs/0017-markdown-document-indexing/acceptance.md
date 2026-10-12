# Feature 0017 Acceptance Criteria

Status: T0017-001 through T0017-010 are complete. Focused functional, HTTP,
architecture and persistence verification passed; full real-source retrieval
qualification passed 20/20 queries on user-approved installed Ollama 0.40.3.
Performance measurement and speed acceptance remain out of scope.

## Source and Passage Processing

- AC-001: The initial English encyclopedia produces passages for all ten stone
  types with document identity/version, language, heading provenance, fragment
  order and applicable stone type. Shared passages are not attributed to one type.
- AC-002: Topic passages contain their document/stone/topic context in the text
  actually embedded. Introductory stone attributes and short subsections remain
  complete. Long paragraphs split between whole sentences, starting around 500
  tokens including context/overlap; initial intra-topic overlap is 50 tokens and
  independent topics do not overlap. Indivisible over-model-limit blocks are rejected.
- AC-003: Scale passages retain explanatory context and table headings. Quick
  comparison rows retain stone identity, column names and table qualifications.
- AC-004: Personalities and rituals are indexed without a real/fictional flag
  or exclusion. Opening introduction, About This Guide and Contents produce
  no searchable passages or embedding context, as does Sources and Editorial Notes.
  Index operations leave the MinIO source intact.
- AC-005: MinIO remains the independent, sole raw-Markdown source. No additional
  raw copy, snapshot, source archive or restoration capability is persisted.
  Derived fragments, provenance and hashes are allowed; index failure does not
  promise restoration of earlier raw contents overwritten externally.
  No import operation accesses or indexes the forbidden engineering-log directory.

## HTTP and Lifecycle

- AC-006: POST /api/stone-encyclopedia/index needs no authentication,
  authorization or chat session; it has an empty body and requires Idempotency-Key.
  It reads only the fixed stone-encyclopedia_en.md object from the stone-encyclopedia
  MinIO bucket and uses the internal documentKey stone-encyclopedia-en. Generated
  OpenAPI describes the routes, DTOs and responses; no handwritten OpenAPI exists.
- AC-007: Importing the same document identity again leaves one complete current
  projection without duplicate fragments. Successful replacement removes old
  sections absent from the new document.
- AC-008: Preparation failure during parsing/embedding or a confirmed publication
  rollback leaves the previous indexed source hash, metadata and index unchanged.
  Readers do not observe a partially published replacement. A lost response or
  commit acknowledgement is not treated as proof of rollback; reconciliation
  determines the durable operation result before repeat publication.
- AC-009: DELETE /api/stone-encyclopedia/index leaves none of this document's
  fragments searchable and does not delete or rewrite the MinIO source. DELETE
  requires a UUID v4 Idempotency-Key and returns 200 with exactly
  {"id":"stone-encyclopedia-en"}, including for an absent index. Saved deletion
  replay does not remove an index created later; used-key markers remain.
- AC-010: HTTP business/dependency errors follow the finalized status contract
  and RFC 9457 ProblemDetail. Controllers, services, repositories and mappers
  retain their constitutional responsibilities.

## Model and Retrieval Qualification

- AC-011: Local qwen3-embedding:0.6b produces finite vectors of exactly 1024
  dimensions for the qualification documents and queries. Query-side retrieval
  instructions and compatible document/query profiles are recorded.
- AC-012: A finalized set of 20 English queries includes direct topics,
  paraphrases, personality/ritual content, shared rules and comparisons. The
  initial quality target is successful retrieval for at least 18 of 20 queries
  within the first five passages. A single-topic success needs a sufficient
  passage from its predefined relevant set; a comparison succeeds only when
  passages collectively support all requested stone types/aspects.
  Q03 (character), Q04 (ritual) and Q09 (two-type comparison) must each succeed
  independently of the aggregate target.
- AC-013: Relevant sets are defined before evaluation. A relevant comparison
  row, introductory attribute passage or topical passage may be accepted when
  it contains the required information; no exact ordering is required between
  equally sufficient sources.
- AC-014: Configurable waiting limits default to 30 seconds for connection,
  5 minutes for MinIO source reading, 30 minutes per embedding request and
  2 hours for an import. Functional tests verify timeout/cancellation and ownership
  with controlled short settings or a test clock, not real hours-long waits.
  No import/query speed benchmark, provider-duration analysis or latency target
  is required for acceptance. HTTP-client/proxy waiting limits are documented.

## Search Context and Retry Recovery

- AC-015: Ritual fragment embedding context uses Care Ritual instead of Fictional
  Care Ritual. Original Markdown and the exact original heading remain available
  in source provenance; no real/fictional classification is added.
- AC-016: Import is synchronous and requires a UUID v4 Idempotency-Key. Successful
  publication and its operation result commit atomically. For three days after
  commit, replay returns the saved status and response without reading changed
  MinIO content to reindex, generating embeddings or publishing again. Later
  replacement/deletion is not undone by an old retry.
- AC-017: A used key reused with changed operation/document identity returns
  409 ProblemDetail. A retry of an active attempt receives 409 with detail
  This operation is already running. The key namespace covers POST and DELETE
  for this index; keys cannot be reused for another operation.
- AC-018: After three days, a known expired key returns 409 rather than starting
  another mutation. Minimal used-key markers remain indefinitely, including
  after DELETE; clients need a new key for a new operation.
- AC-019: Import and index deletion cannot execute concurrently for this document.
  A conflicting request receives 409 ProblemDetail with detail
  This operation is already running. No conflicting work is queued.
- AC-020: Successful first index creation returns 201, replacement returns 200,
  and the response contains exactly documentKey, sourceVersion and indexedFragments.
  A retained committed-key replay returns the original status and response.
- AC-021: sourceVersion is the SHA-256 digest of the exact imported source
  bytes; identical bytes yield the same value. The published hash changes only
  with successful publication, independently of a later MinIO overwrite.
- AC-022: Bodyless POST/DELETE reject missing/non-UUID-v4 keys or nonempty bodies
  with 400 ProblemDetail. Missing MinIO source returns 404, unindexable source
  returns 422, and unavailable required dependencies return 503 ProblemDetail.
- AC-023: Initial source/fragment limits are configurable at 1 MiB and 2000.
  Exceeding either limit, invalid source text, no useful passages after exclusions
  or an indivisible over-model-limit block returns 422 without partial publication.
- AC-024: Rejection of a new request before execution starts leaves its key
  available for another attempt. A definitively failed started attempt is terminal:
  its old key returns 409 and another attempt requires a new key.
- AC-025: Restart recovery first reconciles authoritative commit state and verifies
  the prior attempt stopped. A committed attempt follows success/expiry replay;
  a stopped uncommitted attempt becomes failed without automatic reimport.
  Uncertain execution/commit state does not permit another document mutation.
- AC-026: Deadline expiry stops further work and requests cancellation, without
  releasing the active owner merely because the HTTP caller stopped waiting.
  A late or stale worker cannot publish after losing ownership. Confirmed failure
  preserves the previous projection; uncertain commit outcome is reconciled.

## Predeclared Retrieval Fixture

The original nine discussed examples are extended below to 20 source-grounded
queries before implementation/evaluation. Do not redefine expected sources to
fit failed retrieval results. A listed alternative must contain sufficient
information; merely sharing a topic heading does not count.
The table names original source headings for provenance; searchable ritual
heading context is normalized to Care Ritual as specified above.

| ID | Query | Expected sufficient sources |
| --- | --- | --- |
| Q01 | How do I clean basalt every month? | Basalt > Monthly Care |
| Q02 | A quiet companion that enjoys shared silence | Basalt > Fictional Care Ritual; Psychological Vibe supports the quiet personality |
| Q03 | Which stone likes keeping everything organized? | Granite or Slate > Psychological Vibe / Fictional Care Ritual; their introductory archetypes or Quick Comparison rows also identify the organizer/order-keeper character |
| Q04 | How can I give obsidian its monthly evening ritual? | Obsidian > Fictional Care Ritual |
| Q05 | What happens if obsidian falls onto tiles? | Obsidian > Fragility and Drop Behavior; Physical Properties supports brittle/sharp-fragment behavior |
| Q06 | Can basalt stay on a bathroom shelf? | Basalt > Ideal Places at Home or Quick Comparison > Basalt; Sunlight and Moisture Resistance supports moisture qualifications |
| Q07 | Does a higher care rating mean daily chores? | Rating Scales > Care Demand Scale |
| Q08 | Can I use acidic cleaner on marble or limestone? | General Care Rules > Cleaning Principles |
| Q09 | Compare basalt and obsidian fragility | Introductory attributes or Quick Comparison rows for both types, collectively providing both ratings |
| Q10 | Why does pumice feel so light compared with a dense stone of the same size? | Pumice > Physical Properties |
| Q11 | How should pumice dry after rinsing? | Pumice > Sunlight and Moisture Resistance |
| Q12 | Should I apply oil or varnish to limestone to intensify its color? | Limestone > Monthly Care |
| Q13 | Where can I keep friable sandstone away from splashes? | Sandstone > Ideal Places at Home |
| Q14 | What can repeated wetting and drying do to shale? | Shale > Sunlight and Moisture Resistance |
| Q15 | Does a small marble specimen need monthly polishing or regular sealing? | Marble > Monthly Care |
| Q16 | Why does gneiss have alternating light and dark bands? | Gneiss > Geological Profile; Physical Properties supports its mineral banding |
| Q17 | What can happen if a thin slate plate lands on its edge? | Slate > Fragility and Drop Behavior |
| Q18 | Do my stones need daily watering or sunbathing? | General Care Rules > Everyday Essentials |
| Q19 | Is fragility the same as resistance to scratching? | Rating Scales > Fragility Scale |
| Q20 | Compare pumice and shale care demand | Introductory attributes or Quick Comparison rows for both types, collectively providing both ratings |

## Execution Evidence

### T0017-010 — Environment Readiness, 2026-10-12

Preflight executed at 09:15 UTC. Coverage: all remaining tickets T0017-002
through T0017-009. Overall result: **BLOCKED**; T0017-010 remains unchecked
until a fresh readiness check passes. No implementation ticket was executed.

| Prerequisite | Expected | Observed | Result |
| --- | --- | --- | --- |
| Maven execution JDK | 23.0.2 | JAVA_HOME explicitly supplied for the command; Maven reports Java 23.0.2 | Ready |
| Maven wrapper | Executable; Maven 3.9.9 | Wrapper executable; `./mvnw --version` reports 3.9.9 | Ready |
| Docker daemon | Accessible for Testcontainers | `docker info` succeeds; server 29.8.1 | Ready |
| Pinned PostgreSQL/pgvector image | Approved image digest available locally | `docker image inspect` succeeds for `pgvector/pgvector@sha256:78bf48b801e792f99e3ac62b5036fd3876e9be48afda16c1e331af1c75ceb2ff` | Ready; no container started |
| Repository encyclopedia | Existing UTF-8 source within 1 MiB | 46,347 bytes; strict UTF-8 decoding succeeds | Ready |
| Ollama | 0.40.2; selected endpoint reachable | Explicit STONE_DOCUMENT_OLLAMA_BASE_URL=http://localhost:11434; /api/version returns 200 and 0.40.2 | Ready |
| Embedding model | Installed qwen3-embedding:0.6b; 1024-dimensional metadata | /api/tags and /api/show return 200; dimensions 1024, advertised context 32768 | Metadata ready; functional qualification remains T0017-002 |
| MinIO connection configuration | Approved endpoint and configured credentials available to the agent | Host endpoint explicitly supplied as http://localhost:9000; existing container credentials supplied to the check process without printing their values | Values available; authentication not verified |
| Feature resource/time limits | Selected defaults explicitly available | Command environment supplies PT30S/PT5M/PT30M/PT2H, 1048576 bytes and 2000 fragments | Configured for the check |
| MinIO source service | Running with a responding readiness endpoint | Existing minio container is exited; /minio/health/ready refuses connections | Blocked |
| Fixed MinIO source object | Authenticated access to stone-encyclopedia/stone-encyclopedia_en.md | Existence, metadata and access cannot be verified while MinIO is unavailable | Blocked; do not assume the object is missing |

The installed embedding model digest is
`ac6da0dfba84a81fdbfbaf330198c33cd77c4cdfc53e8bc50eb581914a15621d`.
This preflight obtained metadata only; no embeddings were generated and no
vector/input/retrieval qualification result is claimed.

Direct impact: MinIO unavailability prevents verifying source integration and
the real import/retrieval pipeline required by T0017-005, T0017-006, T0017-008
and T0017-009. Under the batch readiness rule, no ticket in T0017-002 through
T0017-009 starts, even though T0017-002's own environment prerequisites are ready.

Corrective actions: start the existing MinIO service with
`docker compose start minio`, confirm its readiness endpoint, then verify
authenticated metadata access to the exact bucket/object above. Provision that
source separately if it is actually absent; this check neither uploaded a source
nor established absence. Re-run T0017-010 before further implementation.

Other application services: the existing PostgreSQL, Redis, Kafka, backend and
frontend Compose containers are also exited. Redis/Kafka/UI are outside this
feature's runtime scope; an existing running backend is not required for the
focused tests. Database verification uses Testcontainers and the approved image,
so a stopped Compose PostgreSQL container is not itself a test prerequisite
failure. A later live full-stack run would require starting its selected services.

Only read-only checks were performed. No services were started, models pulled,
application tests run, documents imported, persistent environment settings
changed or source copies written. Credential values were never printed.

### T0017-010 — Environment Preparation and Recheck, 2026-10-12

Following the user's authorization for autonomous environment startup/setup,
the agent prepared the environment and rechecked the affected prerequisites.
Coverage remains T0017-002 through T0017-009. Latest result at 09:31 UTC:
**READY**; the earlier 09:15 UTC blocked report is retained as history.

- Docker was already available. `docker compose --profile frontend start`
  started the six existing project containers without building images or
  removing volumes. PostgreSQL, MinIO, Redis, Kafka, backend and frontend all
  report running/healthy. The full stack was explicitly authorized by the user;
  UI/Redis/Kafka remain unnecessary for the Markdown qualification task itself.
- Actual host checks returned HTTP 200 from backend /actuator/health with UP,
  frontend /stone-shelter/catalog, MinIO /minio/health/ready and Ollama /api/version
  with version 0.40.2.
- Signed MinIO HEAD requests initially returned 404 for the declared bucket and
  object. The agent created the missing stone-encyclopedia bucket and provisioned
  stone-encyclopedia_en.md from the existing approved repository encyclopedia
  (46,347 UTF-8 bytes). The initial object PUT used If-None-Match: *; an existing
  object would be preserved. The final authenticated object HEAD returned 200.
  This was environment source provisioning, not a document index import; no raw
  snapshot/archive or extra local source file was written.
- The approved command-scoped JDK/endpoints/resource limits and credentials from
  the existing MinIO container configuration were used. Credential values were
  not printed. The successful unaffected tool/model/image/source prerequisites
  from the earlier checks remain applicable; no model download or application
  test was repeated as part of this preparation.
- No implementation ticket, embedding generation, database index publication,
  commit or push was executed. T0017-010 is complete; the next authorized
  implementation step remains T0017-002 functional model qualification.

### T0017-002 — Provider Adapter, 2026-10-12

- Implemented the existing-library Ollama adapter, shared document/query profile,
  request mapper, environment settings and lightweight English token estimator.
  Requests use one input, qwen3-embedding:0.6b, dimensions=1024 and truncate=false;
  query inputs carry the Qwen English retrieval instruction and documents do not.
  Provider responses require a matching model, one finite nonzero 1024-element
  vector and a positive integral prompt_eval_count. No timing fields are analyzed.
- On JDK 23.0.2, `./mvnw -Dtest=StoneDocumentEmbeddingTest test -q` passed all
  27 tests. They cover provider JSON, profile/dimension/token validation,
  nonnumeric/nonfinite/zero vectors, model-capacity errors, unavailable dependencies,
  no retry, controlled request timeout, remaining-deadline bounds, configuration
  validation and missing configuration. The local HTTP-server tests required
  execution outside the sandbox's socket-binding restriction.
- The adapter now checks the installed model digest before and after embedding
  within one request deadline. The digest is part of the document/query profile;
  missing models start no embedding call, and a model changed during the call
  is rejected. Focused tests passed again after these additions. Real-model
  qualification also checks that all passage/query profiles have the same digest.
- Separately ran `./mvnw -Dtest=StoneDocumentEmbeddingQualificationTest
  -Dstone.document.qualification=true
  -DSTONE_DOCUMENT_OLLAMA_BASE_URL=http://localhost:11434 test -q`.
  The preflight POST /api/show returned 404 for qwen3-embedding:0.6b, so the
  qualification test failed before embedding requests. This URL was the standard
  local-address assumption; STONE_DOCUMENT_OLLAMA_BASE_URL was unset in the shell.
  Actual model output, input capacity, estimator/provider token agreement and
  representative retrieval remain unqualified. The test reads representative
  passages directly from the existing encyclopedia and is explicitly opt-in.
- No model was downloaded, source uploaded, document imported, raw-source copy
  persisted or database schema changed. T0017-002 remains incomplete and gates
  T0017-003 until the selected model is available and functional qualification
  passes. The full 20-query retrieval fixture remains assigned to T0017-008.

#### Successful Installed-Model Qualification

The batch preflight rechecked running/healthy containers, Ollama and MinIO HTTP
200 responses, 1024-dimensional model metadata and authenticated fixed-source
HEAD 200 before continuing. The agent supplied the approved JDK and endpoints.

`./mvnw -Dtest=StoneDocumentEmbeddingQualificationTest
-Dstone.document.qualification=true test -q` passed on JDK 23.0.2 with the
installed qwen3-embedding:0.6b. Six source passages and six instructed queries
returned finite 1024-dimensional vectors with a consistent profile containing
the installed digest. Each representative query ranked its predefined source
first among the six passages. This is preliminary qualification, not the final
20-query encyclopedia audit.

| Source passage | Estimated tokens | Provider prompt_eval_count |
| --- | --- | --- |
| Basalt > Monthly Care | 66 | 52 |
| Basalt > Care Ritual | 55 | 39 |
| Granite > Psychological Vibe | 133 | 86 |
| Obsidian > Care Ritual | 70 | 49 |
| Rating Scales > Care Demand Scale | 239 | 165 |
| General Care Rules > Cleaning Principles | 157 | 107 |

The lightweight estimator exceeded actual counts for these English samples;
500/50 remain approximate initial targets, not exact tokenizer measurements.
Model metadata advertises 32768 input tokens; a repeated-word input beyond that
capacity was rejected with truncate=false. Provider rejection remains the
authoritative hard-cap safeguard. No duration fields or speed results were used.
T0017-002 is complete; schema work may now start.

### T0017-003 — Prepared Persistence, Verification Blocked

The task's allowed new migration, index/fragment/operation entities, enums,
repository and StoneDocumentIndexPersistenceTest were implemented. The new
changelog is registered after the existing vector-extension migration; no
existing applied changeset was edited and no working application database was
migrated by this task.

The first focused test attempt failed in Testcontainers initialization because
the local Docker API returned 503 (Docker Desktop unable to start). The agent
attempted authorized Desktop startup and a bounded graceful restart. Shutdown
failed on confirmed Docker processes; SIGTERM was sent only to those processes,
then the single remaining confirmed orphaned Docker backend was terminated to
permit startup. No Docker data, image, volume or settings file was deleted.

The Docker API briefly returned 200 and project service restoration began.
A second focused test attempt reached Spring context startup but the database
became unreachable at its Testcontainers-assigned localhost port. Liquibase
initialization failed with connection refused; all five test cases reported
context errors, not successful assertions. The later Docker API requests timed
out, Compose reported a 502 gateway failure, and `docker desktop status --format
json` reported stopped while the start command reported already running.

Result: **BLOCKED**, not a verified persistence implementation. T0017-010 is
reopened because the environment is no longer ready for the remaining batch.
T0017-004 through T0017-009 were not started. Further application changes and
another test retry require a stable Docker API and a restored readiness check;
the failed tests were not weakened or replaced with a non-Testcontainers database.
The user was asked for the Docker Desktop UI error text, since normal automatic
startup/restart did not establish stable engine availability. No host reboot,
factory reset, data cleanup, diagnostic-log collection, commit or push occurred.

### T0017-010 — Resume Readiness Recheck, 2026-10-12

Requested continuation was interpreted as the next unfinished task, T0017-003,
with its mandatory readiness gate. T0017-001 and T0017-002 have recorded
completion evidence. The T0017-003 migration, entities, operation enums,
repository and focused persistence test are present; its verification remains
incomplete. T0017-004 through T0017-009 were not started.

Preparation: started installed Docker Desktop, then started only the existing
MinIO and PostgreSQL project containers. No persistent configuration, model,
source object, database migration or application code was changed.

| Component | Expected | Observed | Result |
| --- | --- | --- | --- |
| Maven execution JDK | 23.0.2 | Explicit JAVA_HOME selects Homebrew JDK 23.0.2; Maven confirms it | Ready |
| Maven wrapper | Executable; Maven 3.9.9 | Executable; version 3.9.9 | Ready |
| Docker | Accessible daemon | Initially unavailable; after Desktop startup, server 29.8.1 responds | Available; persistence test not run |
| pgvector Testcontainers image | Approved immutable digest installed | Exact approved image inspect succeeds | Ready |
| PostgreSQL service | Database accepts connections | Existing container running/healthy; pg_isready on 127.0.0.1:5432 accepts connections | Ready for checked endpoint |
| MinIO | Ready; authenticated fixed source accessible | Container running/healthy; readiness 200; signed HEAD 200, 46,347 bytes | Ready |
| Source resource | Valid UTF-8 within 1 MiB | Repository source 46,347 bytes; strict UTF-8 decoding passes | Ready |
| Ollama runtime | Approved 0.40.2 | http://localhost:11434/api/version returns 200 with 0.40.3 | Blocked: version mismatch |
| Embedding model | Installed selected model; 1024 dimensions | qwen3-embedding:0.6b present; prior qualified digest unchanged; metadata 1024 dimensions and 32768 context | Metadata ready; no new embedding calls |
| Feature configuration | Approved endpoints, limits and credentials obtainable | Endpoints remain localhost:9000 and localhost:11434; existing MinIO container credentials present and used without printing; documented limits unchanged | Available for command-scoped execution |

Overall: **BLOCKED** for T0017-003; T0017-010 remains unchecked. The installed
Ollama runtime differs from the approved pin. Corrective action: restore the
approved 0.40.2 runtime, or explicitly authorize adopting 0.40.3 and updating
the applicable version decisions before proceeding; no silent version change
or host downgrade was attempted. Recheck affected prerequisites before running
StoneDocumentIndexPersistenceTest. No application tests ran during readiness.

Other application services: backend, frontend, Redis and Kafka containers remain
stopped. Backend and frontend host endpoint checks failed to connect; these
services are not needed for the focused persistence test, which starts its own
Spring context and PostgreSQL Testcontainer. Redis/Kafka are outside this task.
No further implementation ticket, commit or push was executed.

### T0017-010 — Approved Local Runtime Recheck, 2026-10-12

The user explicitly approved using the already installed Ollama 0.40.3 without
updating it. This supersedes the runtime-version blocker for this continuation;
historical 0.40.2 qualification evidence is unchanged. Covered batch: T0017-003
only. Overall result: **READY** before focused persistence verification.

| Component | Expected | Fresh observation | Result |
| --- | --- | --- | --- |
| Maven/JDK | Executable wrapper, Maven 3.9.9, JAVA_HOME JDK 23.0.2 | Explicit JAVA_HOME; Maven reports 3.9.9 and Java 23.0.2; wrapper executable | Ready |
| Docker/image | Responding daemon and approved pgvector digest | Server 29.8.1 responds; exact pinned image installed | Ready |
| PostgreSQL | Running/healthy and database connectivity | Existing container healthy; pg_isready at 127.0.0.1:5432 accepts connections | Ready |
| MinIO/source | Readiness and authenticated fixed-object access | Healthy; HTTP readiness 200; signed HEAD 200, 46,347 bytes | Ready |
| Ollama/model | User-approved local 0.40.3 and qualified model metadata | Version 200/0.40.3; qwen3-embedding:0.6b installed with unchanged qualified digest, 1024 dimensions and 32768 context | Ready for this task |
| Repository source | Valid UTF-8 within configured byte limit | Strict decoding passes; 46,347 bytes | Ready |
| Configuration | Approved endpoints, resource limits and credentials available | localhost:9000/11434; credentials present and authenticated without printing; PT30S/PT5M/PT30M/PT2H and 1048576/2000 limits supplied for execution | Ready |

Backend, frontend, Redis and Kafka remain stopped; backend/frontend HTTP checks
cannot connect. They are not required by T0017-003's focused Testcontainers
verification. This recheck performed no startup, model download, embedding
generation, import or application test. Testcontainers connectivity is verified
by the subsequent focused test, not claimed from Compose database readiness.

### T0017-003 — Successful Persistence Verification, 2026-10-12

After the fresh READY report, the focused StoneDocumentIndexPersistenceTest
ran with explicit JDK 23.0.2 and approved feature endpoints/limits. The first
run executed five tests with one failure: Spring repository exception translation
wrapped the invalid-vector IllegalArgumentException in
InvalidDataAccessApiUsageException. Declaring the intended exception on
insertFragments preserves it at the repository boundary. No test was changed.

The same focused selector then passed with exit code 0 (all five tests).
Evidence covers new migration startup in the pinned PostgreSQL/pgvector
Testcontainer, 1024-dimensional vector/provenance round trip, deterministic
fragment order, duplicate ordinal and source-version constraints, retained
failed-operation keys after projection deletion, cross-kind key uniqueness,
invalid-vector rejection and absence of raw-source archive columns.
Docker and the existing PostgreSQL endpoint remained available after verification.

T0017-003 is complete. Migration execution occurred only in the isolated test
database; no working application database was migrated. Atomic lifecycle,
successful replay and recovery verification remain assigned to T0017-006.
T0017-004 was not started. No runtime update, commit or push occurred.

### T0017-010 — Remaining Batch Readiness, 2026-10-12

Coverage: T0017-004 through T0017-009. Overall: **READY**.
No preparation was necessary during this recheck.

| Component | Expected | Observed | Result |
| --- | --- | --- | --- |
| Maven/JDK | Executable wrapper, Maven 3.9.9, JDK 23.0.2 | Wrapper executable; command-scoped JAVA_HOME; reported versions match | Ready |
| Docker/pgvector | Accessible daemon and pinned image | Server 29.8.1; approved immutable image installed | Ready |
| PostgreSQL | Connection available | Healthy; 127.0.0.1:5432 accepts connections | Ready |
| MinIO/fixed source | Ready and authenticated object access | Healthy; readiness 200; signed HEAD 200; 46,347 bytes | Ready |
| Ollama/model | Approved installed 0.40.3; qualified model | Version 200/0.40.3; unchanged qualified digest; 1024 dimensions, 32768 context | Ready |
| Source/tools/config | Valid source and approved settings obtainable | Strict UTF-8, within 1 MiB; credentials present; endpoints localhost:9000/11434; default limits available | Ready |

Backend/UI/Redis/Kafka remain stopped. Backend/UI HTTP checks cannot connect;
focused tests launch their own application context and Testcontainers databases,
so these stopped services do not block this batch. No test, embedding generation
or import ran as part of readiness. Known settings and credentials are supplied
by the agent in controlled execution environments without printing secret values.

### T0017-004 — Markdown Processing

StoneMarkdownDocumentTest passed five tests on JDK 23.0.2. The repository
encyclopedia was read directly, without creating a copy. Verified ten stone
types, attributes, ritual provenance/normalized context, shared scale tables,
comparison qualifications/columns, editorial exclusions, whole-sentence splits,
intra-topic overlap, indivisible list items, hard-cap rejection and fragment limits.

### T0017-005 — Source and Preparation

StoneDocumentSourceTest passed four tests: fixed-object read, exact byte hash,
missing/oversized source, bounded source read and expired deadline, UTF-8 rejection,
complete staged vectors/profile and generation failure. Reads close their streams;
no bucket/object writes or raw caches were added. The first test attempt exposed
an incorrect JSON content type on the simulated S3 XML error; the support server
now emits the provider's XML media type. Source and embedding calls disable retries.

### T0017-006 — Owned Lifecycle

StoneDocumentIndexLifecycleTest passed eleven tests on the pinned database.
Verified replacement/removal of old sections, historical import/delete replay,
absent deletion, permanent cross-kind/expired/failed keys, preparation failure,
publication rollback, busy admission without key consumption, active retry,
deadline ownership until worker exit, verified-stopped recovery/stale workers,
uncertain reconciliation retaining ownership, and readers observing the prior
complete projection until commit. An afterCommit failure simulates lost commit
acknowledgement: reconciliation returns durable success without a second insert.
The first run had a test-setup overload-selection error; explicit Object typing
fixed clock injection without altering the behavior assertions. No full suite ran.

### T0017-007 — HTTP and Generated Contract

The four allowed selectors passed 35 checks: StoneEncyclopediaControllerTest,
StoneDocumentIndexArchitectureTest, OpenApiDocumentationTest and ArchitectureTest.
Verified 201 creation/replay, 200 replacement, identifier-only deletion, UUID-v4
headers, empty-body validation before admission, 400/404/409/422/503 ProblemDetail,
exact three import properties, hidden outcome and generated JSON/YAML contract.
Controller-owned response advice selects replacement status after Spring applies
@ResponseStatus, preserving direct DTO return values. The exhaustive route check
now also recognizes the already implemented chat-history route. An initial run
exposed status override order and test restubbing; both were corrected.

### T0017-008 — Internal Retrieval Qualification

StoneDocumentRetrievalTest passed both focused tests: exact cosine ordering,
bounded results, profile mismatch rejection, query profile and mapper wrappers.
StoneDocumentRetrievalQualificationTest passed its opt-in real-source evaluation
on installed Ollama 0.40.3 and qwen3-embedding:0.6b. It read only the fixed MinIO
object and published 127 fragments into an isolated pinned Testcontainers database.
Published sourceVersion: 802e57824e9b04759229be73112019fc61b1f8852ea3501d9dd91f08bbeaf322.
One consistent model profile was retained; all provider vectors were validated
as finite, nonzero and 1024-dimensional. No raw source copy was persisted.

All **20/20** queries met their predeclared relevant sets within the first five
passages, including mandatory Q03, Q04 and Q09. Query text and source alternatives
were recorded in retrieval-queries.json before evaluation, without adjusting
oracles to results. Both comparison queries retrieved supporting rows/attributes
for every requested stone type. Test data and operation records were cleaned up.

| Query | Result | Retrieved section provenance (top five) |
| --- | --- | --- |
| Q01 | PASS | Stone Encyclopedia > Basalt > Monthly Care, Stone Encyclopedia > Basalt > Daily Care, Stone Encyclopedia > Basalt > Fictional Care Ritual, Stone Encyclopedia > Basalt > Sunlight and Moisture Resistance, Stone Encyclopedia > Granite > Monthly Care |
| Q02 | PASS | Stone Encyclopedia > Shale > Psychological Vibe, Stone Encyclopedia > Slate > Psychological Vibe, Stone Encyclopedia > Limestone > Psychological Vibe, Stone Encyclopedia > Sandstone > Psychological Vibe, Stone Encyclopedia > Basalt > Fictional Care Ritual |
| Q03 | PASS | Stone Encyclopedia > Slate > Psychological Vibe, Stone Encyclopedia > Granite > Psychological Vibe, Stone Encyclopedia > Slate > Fictional Care Ritual, Stone Encyclopedia > Shale > Psychological Vibe, Stone Encyclopedia > Limestone > Psychological Vibe |
| Q04 | PASS | Stone Encyclopedia > Obsidian > Fictional Care Ritual, Stone Encyclopedia > Obsidian > Psychological Vibe, Stone Encyclopedia > Obsidian > Monthly Care, Stone Encyclopedia > Quick Comparison > Obsidian, Stone Encyclopedia > Slate > Fictional Care Ritual |
| Q05 | PASS | Stone Encyclopedia > Obsidian > Fragility and Drop Behavior, Stone Encyclopedia > Marble > Fragility and Drop Behavior, Stone Encyclopedia > Obsidian > Geological Profile, Stone Encyclopedia > Basalt > Fragility and Drop Behavior, Stone Encyclopedia > General Care Rules > Handling Principles |
| Q06 | PASS | Stone Encyclopedia > Basalt > Ideal Places at Home, Stone Encyclopedia > Quick Comparison > Basalt, Stone Encyclopedia > Basalt > Sunlight and Moisture Resistance, Stone Encyclopedia > Basalt > Fragility and Drop Behavior, Stone Encyclopedia > Basalt > Psychological Vibe |
| Q07 | PASS | Stone Encyclopedia > Rating Scales > Care Demand Scale, Stone Encyclopedia > Gneiss > Daily Care, Stone Encyclopedia > Shale > Fictional Care Ritual, Stone Encyclopedia > General Care Rules > Everyday Essentials, Stone Encyclopedia > Rating Scales |
| Q08 | PASS | Stone Encyclopedia > General Care Rules > Cleaning Principles, Stone Encyclopedia > Marble > Daily Care, Stone Encyclopedia > Limestone > Daily Care, Stone Encyclopedia > Granite > Monthly Care, Stone Encyclopedia > Marble > Monthly Care |
| Q09 | PASS | Stone Encyclopedia > Basalt > Physical Properties, Stone Encyclopedia > Basalt > Attributes, Stone Encyclopedia > Basalt > Fragility and Drop Behavior, Stone Encyclopedia > Quick Comparison > Basalt, Stone Encyclopedia > Quick Comparison > Obsidian |
| Q10 | PASS | Stone Encyclopedia > Pumice > Geological Profile, Stone Encyclopedia > Pumice > Physical Properties, Stone Encyclopedia > Pumice > Psychological Vibe, Stone Encyclopedia > Pumice > Fragility and Drop Behavior, Stone Encyclopedia > Pumice > Attributes |
| Q11 | PASS | Stone Encyclopedia > Pumice > Daily Care, Stone Encyclopedia > Pumice > Monthly Care, Stone Encyclopedia > Quick Comparison > Pumice, Stone Encyclopedia > Pumice > Ideal Places at Home, Stone Encyclopedia > Pumice > Sunlight and Moisture Resistance |
| Q12 | PASS | Stone Encyclopedia > Limestone > Monthly Care, Stone Encyclopedia > Limestone > Typical Colors, Stone Encyclopedia > Quick Comparison > Limestone, Stone Encyclopedia > General Care Rules > Cleaning Principles, Stone Encyclopedia > Limestone > Physical Properties |
| Q13 | PASS | Stone Encyclopedia > General Care Rules > Handling Principles, Stone Encyclopedia > Sandstone > Ideal Places at Home, Stone Encyclopedia > Sandstone > Monthly Care, Stone Encyclopedia > Quick Comparison > Sandstone, Stone Encyclopedia > Sandstone > Daily Care |
| Q14 | PASS | Stone Encyclopedia > Shale > Sunlight and Moisture Resistance, Stone Encyclopedia > Quick Comparison > Shale, Stone Encyclopedia > Shale > Physical Properties, Stone Encyclopedia > Shale > Monthly Care, Stone Encyclopedia > Shale > Daily Care |
| Q15 | PASS | Stone Encyclopedia > Marble > Monthly Care, Stone Encyclopedia > Marble > Fictional Care Ritual, Stone Encyclopedia > Marble > Daily Care, Stone Encyclopedia > General Care Rules > Cleaning Principles, Stone Encyclopedia > Limestone > Monthly Care |
| Q16 | PASS | Stone Encyclopedia > Gneiss > Typical Colors, Stone Encyclopedia > Gneiss > Geological Profile, Stone Encyclopedia > Gneiss > Physical Properties, Stone Encyclopedia > Gneiss > Psychological Vibe, Stone Encyclopedia > Quick Comparison > Gneiss |
| Q17 | PASS | Stone Encyclopedia > Slate > Fragility and Drop Behavior, Stone Encyclopedia > Shale > Fragility and Drop Behavior, Stone Encyclopedia > Slate > Physical Properties, Stone Encyclopedia > Slate > Monthly Care, Stone Encyclopedia > Slate > Ideal Places at Home |
| Q18 | PASS | Stone Encyclopedia > General Care Rules > Everyday Essentials, Stone Encyclopedia > Basalt > Daily Care, Stone Encyclopedia > Limestone > Daily Care, Stone Encyclopedia > Pumice > Daily Care, Stone Encyclopedia > Basalt > Sunlight and Moisture Resistance |
| Q19 | PASS | Stone Encyclopedia > Rating Scales > Fragility Scale, Stone Encyclopedia > Gneiss > Fragility and Drop Behavior, Stone Encyclopedia > Limestone > Fragility and Drop Behavior, Stone Encyclopedia > Slate > Fragility and Drop Behavior, Stone Encyclopedia > Sandstone > Fragility and Drop Behavior |
| Q20 | PASS | Stone Encyclopedia > Quick Comparison > Pumice, Stone Encyclopedia > Pumice > Attributes, Stone Encyclopedia > Quick Comparison > Shale, Stone Encyclopedia > Pumice > Ideal Places at Home, Stone Encyclopedia > Pumice > Fragility and Drop Behavior |

Before the audit, focused regression checks passed again for changed source/
lifecycle code and the new retrieval types. Provider capacity errors wrapped by
Spring still become unindexable-source errors; owned-operation selection now
checks ledger and anchor identity together in the repository. The verified
selectors were StoneDocumentSourceTest, StoneDocumentIndexLifecycleTest,
StoneMarkdownDocumentTest, StoneDocumentRetrievalTest,
StoneDocumentIndexArchitectureTest and ArchitectureTest. No performance
measurements, public search endpoint, runtime update or working-database import
was added.

### T0017-009 — Acceptance Audit

Result: **PASS**. Reviewed all AC-001 through AC-026 against the implementation
and focused execution evidence. No application file was repaired under this
audit ticket, and no full suite or unchanged successful selector was repeated.
The earlier regression run was required by source/lifecycle changes and new
retrieval types before the audit. Exact fixture query text matches all twenty
original acceptance queries; permitted relevant alternatives remain unchanged.

| Criteria | Evidence |
| --- | --- |
| AC-001–004, AC-015 | Markdown tests: all types, context, attributes, complete short sections, meaningful splits/overlap, scale/table/comparison context, exclusions and original ritual provenance |
| AC-005 | Source repository reads only the fixed MinIO object with a closed bounded stream; persistence schema contains derived text/metadata/ledger only, no raw archive columns |
| AC-006, AC-010, AC-020, AC-022 | Open HTTP/controller tests, generated JSON/YAML contract, direct DTOs, exact wire fields, header/body validation, documented statuses and ProblemDetail; architecture checks |
| AC-007–009 | Lifecycle tests: replacement removes old sections, atomic reader visibility, rollback/preparation preservation, deletion/absence and old deletion replay preserving later projection |
| AC-011–013 | Actual MinIO/model pipeline: 127 finite 1024-dimensional passage vectors, compatible query profiles and 20/20 predefined top-five relevant sets; mandatory controls pass |
| AC-014, AC-026 | Controlled provider/source deadline tests, overall import clock/ownership test, stale-worker rejection and uncertain outcome guard; client/proxy wait requirement in generated operation description |
| AC-016–019 | Durable success result/atomic publication, historical replay without preparation, permanent markers, exact three-day expiry, changed-kind and busy conflicts without queued work |
| AC-021, AC-023 | Exact-byte SHA-256 test; strict UTF-8, source/fragment limits, empty useful content and indivisible/provider capacity rejection |
| AC-024–025 | Busy new key remains reusable, failed key is terminal; verified-stopped recovery, stale-owner rejection, uncertain reconciliation retains guard and lost commit acknowledgement reconciles durable success |

All 57 task-listed implementation/test/migration/fixture artifacts exist under
the specified module roots. Targeted source checks found no wildcard imports or
package-root errors; git diff whitespace validation passed. Existing applied
migrations were not modified. No handwritten HTTP contract, raw-source copy,
new dependency, public semantic-search endpoint, speed analysis or unrelated
feature was introduced.

Scope of qualification: focused tests and isolated Testcontainers databases,
with the declared real MinIO source and installed local embedding model. The
working application database was not migrated or imported; backend/UI/Redis/Kafka
remain stopped as reported in readiness. No runtime update, commit or push occurred.
