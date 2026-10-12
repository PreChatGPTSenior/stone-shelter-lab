# Feature 0023 Tasks

Status: Execution tickets prepared from accepted ADR-0023; all remain unexecuted.
Documentation preparation does not authorize application implementation.

## Mandatory First Ticket: Environment Readiness

- [ ] T0023-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. No checks have run here.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0023-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [ ] T0023-002 — Add durable jobs, items, creation control and DTO mapping. Depends on T0023-001.
- [ ] T0023-003 — Implement bounded reindex preparation and recovery. Depends on T0023-001 and T0023-002.
- [ ] T0023-004 — Expose job HTTP operations and generated contract. Depends on T0023-001 and T0023-003.
- [ ] T0023-005 — Review reindex acceptance and layer boundaries. Depends on T0023-001 and T0023-004.

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
