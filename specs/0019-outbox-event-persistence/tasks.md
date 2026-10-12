# Feature 0019 Tasks

Status: Execution tickets prepared from accepted ADR-0019; all remain unexecuted.
Documentation preparation does not authorize application implementation.

## Mandatory First Ticket: Environment Readiness

- [ ] T0019-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. No checks have run here.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0019-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [ ] T0019-002 — Define request, entity mapping and validation. Depends on T0019-001.
- [ ] T0019-003 — Add transactional outbox persistence. Depends on T0019-001 and T0019-002.
- [ ] T0019-004 — Review focused acceptance and layer boundaries. Depends on T0019-001 and T0019-003.

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

### T0019-001

Writable: specs/0019-outbox-event-persistence/tasks.md and acceptance.md only.
Verification: read-only readiness probes and authorized routine startup described
in plan.md; no application test selector. Record corrective actions and affected
IDs on BLOCKED. Do not check any ticket complete from documentation alone.

### T0019-002: Define request, entity mapping and validation

Acceptance references: AC-0019-002 through AC-0019-006.

Writable:

- J/shared/OutboxWriteRequest.java
- J/entities/OutboxEventEntity.java
- J/enums/OutboxDeliveryState.java
- J/mappers/entities/OutboxWriteRequestToOutboxEventEntityMapper.java
- J/exceptions/OutboxWriteException.java
- J/exceptions/OutboxValidationException.java
- J/exceptions/OutboxIdentityConflictException.java
- J/services/OutboxPayloadValidationService.java
- T/OutboxValidationTest.java

Selector: `OutboxValidationTest`.

Verify compact UTF-8 boundaries, invalid envelopes, first-write mapping and duplicate input validation; no database or broker needed.

### T0019-003: Add transactional outbox persistence

Acceptance references: AC-0019-001, AC-0019-004 through AC-0019-009.

Writable:

- D/0019-create-outbox-event.xml
- D/db.changelog-master.yaml
- J/repositories/OutboxEventRepository.java
- J/services/OutboxService.java
- T/OutboxPersistenceTest.java

Selector: `OutboxPersistenceTest`.

Verify MANDATORY caller transaction, rollback, conflict-safe concurrent writes, preserved repeated state and no business FK in isolated PostgreSQL; append migration include only.

### T0019-004: Review focused acceptance and layer boundaries

Acceptance references: AC-0019-001 through AC-0019-009.

Writable:

- T/OutboxArchitectureTest.java

Selector: `OutboxArchitectureTest`.

Check protocol-independent services/DTO and mapper boundaries for the new types; review earlier focused evidence without rerunning unchanged tests.

## Execution Boundary

Run only the selected ticket's named selector with controlled environment,
including JAVA_HOME, using ./mvnw -Dtest=<selector> test from stone-shelter-api.
No full suite, broad verify, business imports or unrequested later tickets.
Verification fixtures create/clean their own data and use isolated databases.
After the requested ticket(s), report results and stop; no commits or pushes
without explicit user authorization. No feature 0025 execution is authorized.
