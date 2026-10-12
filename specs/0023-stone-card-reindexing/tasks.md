# Feature 0023 Tasks

Status: T0023-001 environment gate passed for the complete requested 0020–0024 batch; all implementation tickets completed and verified.
The user explicitly authorized all tickets and one commit at the end of each feature.

## Mandatory First Ticket: Environment Readiness

- [x] T0023-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. Fresh batch checks are recorded below.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0023-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [x] T0023-002 — Add durable jobs, items, creation control and DTO mapping. Depends on T0023-001.
- [x] T0023-003 — Implement bounded reindex preparation and recovery. Depends on T0023-001 and T0023-002.
- [x] T0023-004 — Expose job HTTP operations and generated contract. Depends on T0023-001 and T0023-003.
- [x] T0023-005 — Review reindex acceptance and layer boundaries. Depends on T0023-001 and T0023-004.

## Exact File Boundaries and Verification

Paths are repository-relative. Expand J to
stone-shelter-api/src/main/java/lab/stoneshelter, T to
stone-shelter-api/src/test/java/lab/stoneshelter and D to
stone-shelter-api/src/main/resources/db/changelog. Each named path below is an
individual allowlisted file; there are no directory/glob editing permissions.
New paths are planned artifacts, not files already implemented.

Each execution ticket may additionally update only this feature's tasks.md and
acceptance.md to record its actual status/evidence. No spec/ADR/other-feature
document edits are implicit during implementation. If another file is needed,
stop and revise the authorized ticket boundary first. Never modify applied
migrations; db.changelog-master.yaml may receive only the new named include.

### T0023-001

Writable: specs/0023-stone-card-reindexing/tasks.md and acceptance.md only.
Verification: read-only readiness probes and authorized routine startup described
in plan.md; no application test selector. Record corrective actions and affected
IDs on BLOCKED. Do not check any ticket complete from documentation alone.

### T0023-002: Add durable jobs, items, creation control and DTO mapping

Acceptance references: AC-0023-001 through AC-0023-003, AC-0023-006.

Writable:

- D/0023-create-stone-card-reindex-jobs.xml
- D/db.changelog-master.yaml
- J/entities/StoneCardReindexJobEntity.java
- J/entities/StoneCardReindexItemEntity.java
- J/enums/StoneCardReindexTarget.java
- J/enums/StoneCardReindexStatus.java
- J/repositories/StoneCardReindexJobRepository.java
- J/repositories/StoneCardReindexItemRepository.java
- J/repositories/StoneCardReindexControlRepository.java
- J/shared/StoneCardReindexStartRequest.java
- J/shared/StoneCardReindexStartResponse.java
- J/shared/StoneCardReindexStatusResponse.java
- J/shared/StoneCardReindexError.java
- J/mappers/dtos/StoneCardReindexJobEntityToStoneCardReindexStartResponseMapper.java
- J/mappers/dtos/StoneCardReindexJobEntityToStoneCardReindexStatusResponseMapper.java
- T/StoneCardReindexPersistenceTest.java

Selector: `StoneCardReindexPersistenceTest`.

Verify one active durable job, manifest/progress uniqueness and restart persistence with isolated PostgreSQL.

### T0023-003: Implement bounded reindex preparation and recovery

Acceptance references: AC-0023-003 through AC-0023-009.

Writable:

- J/services/StoneCardReindexSettings.java
- J/services/StoneCardReindexRunnerService.java
- J/services/StoneCardReindexTransactionService.java
- J/services/StoneCreationService.java
- J/repositories/StoneEntityRepository.java
- J/repositories/StoneCardEmbeddingRepository.java
- J/repositories/StoneIndexSourceVersionRepository.java
- J/exceptions/StoneCardReindexConflictException.java
- .env.example
- T/StoneCardReindexServiceTest.java

Selector: `StoneCardReindexServiceTest`.

Verify batch 20/configuration, fresh forced events, gate races/restart/release, independent errors, deleted skips, version-safe orphan deletion and no model wait. Append only feature environment entries.

### T0023-004: Expose job HTTP operations and generated contract

Acceptance references: AC-0023-001, AC-0023-002, AC-0023-006.

Writable:

- J/controllers/StoneCardReindexController.java
- J/services/StoneCardReindexService.java
- J/exceptions/InvalidStoneCardReindexRequestException.java
- J/exceptions/StoneCardReindexNotFoundException.java
- J/handlers/ApiExceptionHandler.java
- T/StoneCardReindexControllerTest.java
- T/StoneCatalogControllerTest.java

Selector: `StoneCardReindexControllerTest,StoneCatalogControllerTest`.

Verify routes/request/DTO/status/error contract, unknown selections/jobs, active/full-job conflicts and existing catalog success; inspect generated JSON/YAML OpenAPI for these operations only.

### T0023-005: Review reindex acceptance and layer boundaries

Acceptance references: AC-0023-001 through AC-0023-009.

Writable:

- T/StoneCardReindexArchitectureTest.java

Selector: `StoneCardReindexArchitectureTest`.

Verify new-component boundaries and review focused evidence; ENQUEUED must not be reported as final vectors. No live full-catalog rebuild.

## Execution Boundary

Run only the selected ticket's named selector with controlled environment,
including JAVA_HOME, using ./mvnw -Dtest=<selector> test from stone-shelter-api.
No full suite, broad verify, business imports or unrequested later tickets.
Verification fixtures create/clean their own data and use isolated databases.
After the requested ticket(s), report results and stop; no commits or pushes
without explicit user authorization. No feature 0025 execution is authorized.

## Batch Readiness Evidence (2026-10-12)

User requested continuous execution of every feature 0020–0024 ticket, committing
at each completed feature before the next. Covered implementation IDs: T0020-002–T0020-005; T0021-002–T0021-004; T0022-002–T0022-005; T0023-002–T0023-005; T0024-002–T0024-007.
Result: READY for the entire requested batch's environment prerequisites.

| Component | Expected | Observed |
| --- | --- | --- |
| Java/Maven | JDK 23.0.2 via JAVA_HOME; Maven 3.9.9 | Fresh java and wrapper checks report exact selected versions. |
| Docker/database | Reachable daemon; approved immutable image; PostgreSQL 18.6/vector 0.8.6 | Docker 29.8.1 reachable; selected pgvector digest installed; authenticated read-only query at backend-network postgres:5432 succeeds and confirms 18.6/0.8.6. |
| Kafka | 4.2.2 and actual broker metadata | Existing broker started; CLI version 4.2.2; metadata from kafka:9092 succeeds; published host listener localhost:29092 reachable. |
| Redis | 8.2.10 and connectivity | Existing container started; PONG and exact server version confirmed. |
| MinIO/source | Ready server and fixed source | Ready HTTP 200; authenticated HEAD of stone-encyclopedia/stone-encyclopedia_en.md is HTTP 200, 46347 bytes. Contents not read or rewritten. Credentials checked as present only. |
| Ollama/models | Previously user-approved installed runtime/profile | 0.40.3 confirmed at host and from backend container; qwen2.5:3b and qwen3-embedding:0.6b installed with digests; metadata reports 32768 input context and embedding profile length 1024. No inference. |
| Existing SDKs | Selected existing dependencies | Spring AI Ollama 2.0.1, Kafka clients 4.2.1 and Resilience4j 2.4.0 cached; no dependency changes. |
| Backend/frontend | Actual endpoints and task impact | Existing backend started, health HTTP 200/UP; frontend remains stopped and is outside these backend/HTTP/model selectors. |

Preparation: started only existing Redis, Kafka and backend containers, preserving
volumes/data. PostgreSQL and MinIO were already running; no model/runtime update,
download, import, application test or business mutation was used as readiness.
The generic plan's historical Ollama 0.40.2 text is superseded by the user's
explicit approval of installed 0.40.3 in feature 0017 and its reuse in feature
0018's plan; this batch reuses that authorization rather than changing runtime.

Agent commands explicitly set JAVA_HOME to the installed Homebrew 23.0.2 path,
DOCKER_HOST to the Desktop socket and TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE to
/var/run/docker.sock. Relevant tests use isolated databases and mocked providers,
except the explicitly named T0024-002 qualification. Known declared host/source
endpoints are supplied per command where absent from inherited configuration;
no private .env is read. Model behavior/context/usage qualification remains work
for T0024-002, not an inference-based readiness check. Future feature prerequisites
within this batch are produced by its earlier completed tickets; external resource
readiness covers the whole batch and is rechecked when moving between features.

### Feature 0023 readiness recheck

T0023-002–005: READY. Fresh authenticated PostgreSQL 18.6 and backend health
UP confirmed; database/Kafka/Redis/MinIO/backend containers healthy. Frontend
stopped and unused. JDK 23.0.2 and isolated-database verification remain explicit
command-scoped prerequisites. No live catalog rebuild or inference is a probe.

### T0023-002 result

StoneCardReindexPersistenceTest: 4 passing cases. Unique active job, manifest
uniqueness, restart-visible counts/errors/gate, atomic rollback and response
mapping verified. enqueuedCount includes live and reconciliation intentions;
reconciledCount is its orphan-reconciliation subset.

### T0023-003 result

StoneCardReindexServiceTest: 6 passing cases. Configurable bounds, forced versions,
resume without duplicate completed work, full/targeted creation gates and the
in-flight creation race, deleted skips, version-safe orphan reconciliation,
independent errors and PostgreSQL waiting verified. Existing vectors are retained;
no synchronous model/Kafka wait. Test-only proxy/fixture cleanup issues corrected
without changing expected behavior.

### T0023-004 result

StoneCardReindexControllerTest: 14 passing cases; StoneCatalogControllerTest:
105 passing cases. Verified start/progress DTOs, 201/no Location, conditional
selection validation, missing IDs/jobs, overlap/full creation conflicts,
storage 503, durable per-item errors and generated JSON/YAML OpenAPI.
GET reads progress/errors within one repeatable-read snapshot and translates
transaction-start failure as unavailable storage.

### T0023-005 result

StoneCardReindexArchitectureTest: 4 passing cases. Controller/DTO, service,
repository/domain and exception boundaries, transaction declarations and mapper
contracts checked; no model/Kafka invocation in preparation. All 133 focused
cases for feature 0023 pass. AC-0023-001–009 reviewed; ENQUEUED remains enqueue
completion only and histories/errors are not expired.
