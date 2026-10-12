# Feature 0020 Tasks

Status: Execution tickets prepared from accepted ADR-0020; all remain unexecuted.
Documentation preparation does not authorize application implementation.

## Mandatory First Ticket: Environment Readiness

- [ ] T0020-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. No checks have run here.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0020-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [ ] T0020-002 — Add delivery state, settings and explicit DLQ provisioning. Depends on T0020-001.
- [ ] T0020-003 — Implement contract, route/key and acknowledged publication. Depends on T0020-001 and T0020-002.
- [ ] T0020-004 — Implement automatic polling and durable retries. Depends on T0020-001 and T0020-003.
- [ ] T0020-005 — Review delivery acceptance and architecture. Depends on T0020-001 and T0020-004.

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

### T0020-001

Writable: specs/0020-outbox-kafka-publication/tasks.md and acceptance.md only.
Verification: read-only readiness probes and authorized routine startup described
in plan.md; no application test selector. Record corrective actions and affected
IDs on BLOCKED. Do not check any ticket complete from documentation alone.

### T0020-002: Add delivery state, settings and explicit DLQ provisioning

Acceptance references: AC-0020-001, AC-0020-008, AC-0020-009.

Writable:

- D/0020-add-outbox-delivery.xml
- D/db.changelog-master.yaml
- J/entities/OutboxEventEntity.java
- J/enums/OutboxDeliveryState.java
- J/services/OutboxDeliverySettings.java
- J/services/OutboxKafkaTopicService.java
- .env.example
- T/OutboxDeliveryPersistenceTest.java

Selector: `OutboxDeliveryPersistenceTest`.

Verify new fields/state constraints, eligible polling and restart-visible state; append a migration include and only feature environment entries.

### T0020-003: Implement contract, route/key and acknowledged publication

Acceptance references: AC-0020-002, AC-0020-003, AC-0020-006.

Writable:

- J/repositories/OutboxKafkaRepository.java
- J/services/OutboxEventRoutingService.java
- J/entities/OutboxDeadLetterEntity.java
- J/mappers/entities/OutboxEventEntityToOutboxDeadLetterEntityMapper.java
- T/OutboxKafkaContractTest.java

Selector: `OutboxKafkaContractTest`.

Validate normal/DLQ serialization against asyncapi.json, unchanged payload, default/configured keys and success/failure acknowledgements with mocked producer results.

### T0020-004: Implement automatic polling and durable retries

Acceptance references: AC-0020-001 through AC-0020-008.

Writable:

- J/repositories/OutboxEventRepository.java
- J/services/OutboxDispatcherService.java
- J/services/OutboxDeliveryTransactionService.java
- T/OutboxDispatcherTest.java

Selector: `OutboxDispatcherTest`.

Verify no DB transaction during producer calls, charged 3-attempt backoff, broker suspension/recovery, progress of other rows and durable DLQ state/restart using isolated PostgreSQL and mocked Kafka.

### T0020-005: Review delivery acceptance and architecture

Acceptance references: AC-0020-001 through AC-0020-009.

Writable:

- T/OutboxArchitectureTest.java

Selector: `OutboxArchitectureTest`.

Extend new-component layering checks; review prior evidence and the AsyncAPI contract. No live business publication or broad suite.

## Execution Boundary

Run only the selected ticket's named selector with controlled environment,
including JAVA_HOME, using ./mvnw -Dtest=<selector> test from stone-shelter-api.
No full suite, broad verify, business imports or unrequested later tickets.
Verification fixtures create/clean their own data and use isolated databases.
After the requested ticket(s), report results and stop; no commits or pushes
without explicit user authorization. No feature 0025 execution is authorized.
