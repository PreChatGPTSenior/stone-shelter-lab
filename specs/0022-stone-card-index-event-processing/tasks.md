# Feature 0022 Tasks

Status: Execution tickets prepared from accepted ADR-0022; all remain unexecuted.
Documentation preparation does not authorize application implementation.

## Mandatory First Ticket: Environment Readiness

- [ ] T0022-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. No checks have run here.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0022-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [ ] T0022-002 — Add processing state and atomic projection apply. Depends on T0022-001.
- [ ] T0022-003 — Prepare embeddings separately and wire automatic consumption. Depends on T0022-001 and T0022-002.
- [ ] T0022-004 — Implement partition retries and durable DLQ transfer. Depends on T0022-001 and T0022-003.
- [ ] T0022-005 — Review processing acceptance and architecture. Depends on T0022-001 and T0022-004.

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

### T0022-001

Writable: specs/0022-stone-card-index-event-processing/tasks.md and acceptance.md only.
Verification: read-only readiness probes and authorized routine startup described
in plan.md; no application test selector. Record corrective actions and affected
IDs on BLOCKED. Do not check any ticket complete from documentation alone.

### T0022-002: Add processing state and atomic projection apply

Acceptance references: AC-0022-002 through AC-0022-004.

Writable:

- D/0022-create-stone-card-index-processing.xml
- D/db.changelog-master.yaml
- J/entities/StoneCardIndexStateEntity.java
- J/entities/StoneCardIndexAttemptEntity.java
- J/repositories/StoneCardIndexStateRepository.java
- J/repositories/StoneCardIndexAttemptRepository.java
- J/repositories/StoneCardEmbeddingRepository.java
- J/services/StoneIndexProcessingTransactionService.java
- T/StoneIndexProcessingPersistenceTest.java

Selector: `StoneIndexProcessingPersistenceTest`.

Verify lock/recheck, atomic update/delete/tombstone and restart-visible attempt state; no model or broker invocation.

### T0022-003: Prepare embeddings separately and wire automatic consumption

Acceptance references: AC-0022-001 through AC-0022-004.

Writable:

- J/services/StoneCardEmbeddingService.java
- J/services/StoneIndexConsumerService.java
- J/services/StoneIndexProcessingService.java
- .env.example
- T/StoneIndexProcessingTest.java

Selector: `StoneIndexProcessingTest`.

Verify stale skip before Ollama, no transaction during preparation, guarded commit before manual ack and group/start-position settings with mocked embedding/provider and isolated DB.

### T0022-004: Implement partition retries and durable DLQ transfer

Acceptance references: AC-0022-005 through AC-0022-009.

Writable:

- J/services/StoneIndexConsumerService.java
- J/services/StoneIndexProcessingService.java
- J/services/StoneIndexAttemptTransactionService.java
- J/repositories/StoneCardIndexAttemptRepository.java
- J/entities/OutboxDeadLetterEntity.java
- T/StoneIndexRetryTest.java

Selector: `StoneIndexRetryTest`.

Verify three total attempts across restart, pause/resume of partition, dependency waits, direct malformed-record DLQ, lossless raw record and ack-after-DLQ only. Shared admission is installed/verified by T0024-005.

### T0022-005: Review processing acceptance and architecture

Acceptance references: AC-0022-001 through AC-0022-009.

Writable:

- T/StoneIndexProcessingArchitectureTest.java

Selector: `StoneIndexProcessingArchitectureTest`.

Verify new-component mapper/service/repository boundaries and review prior evidence, with the accepted 0024 guard retrofit identified separately.

## Execution Boundary

Run only the selected ticket's named selector with controlled environment,
including JAVA_HOME, using ./mvnw -Dtest=<selector> test from stone-shelter-api.
No full suite, broad verify, business imports or unrequested later tickets.
Verification fixtures create/clean their own data and use isolated databases.
After the requested ticket(s), report results and stop; no commits or pushes
without explicit user authorization. No feature 0025 execution is authorized.
