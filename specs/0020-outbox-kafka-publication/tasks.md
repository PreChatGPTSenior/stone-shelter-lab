# Feature 0020 Tasks

Status: T0020-001 environment gate passed for the complete requested 0020–0024 batch; all implementation tickets completed and verified.
The user explicitly authorized all tickets and one commit at the end of each feature.

## Mandatory First Ticket: Environment Readiness

- [x] T0020-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. Fresh batch checks are recorded below.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0020-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [x] T0020-002 — Add delivery state, settings and explicit DLQ provisioning. Depends on T0020-001.
- [x] T0020-003 — Implement contract, route/key and acknowledged publication. Depends on T0020-001 and T0020-002.
- [x] T0020-004 — Implement automatic polling and durable retries. Depends on T0020-001 and T0020-003.
- [x] T0020-005 — Review delivery acceptance and architecture. Depends on T0020-001 and T0020-004.

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

### T0020-002 result

OutboxDeliveryPersistenceTest: 4 passing tests, 0 failures/errors/skips on JDK
23.0.2. Verified migration from an existing 0019 pending row, persisted delivery
fields/constraints/eligibility, environment defaults/validation and explicit DLQ
provisioning/recovery with configured retention. Disposable database cleaned;
only new migration/master include and feature environment entries changed.

### T0020-003 result

OutboxKafkaContractTest: 4 passing tests, 0 failures/errors/skips. Verified
acks=all/idempotence configuration, complete AsyncAPI normal/DLQ envelopes,
unchanged payloads, key rules and waiting for successful acknowledgements.
Initial sandbox execution denied Mockito agent attachment; the identical selector
passed with authorized process access, without code/assertion changes.

### T0020-004 result

OutboxDispatcherTest: 7 passing tests, no failures/errors/skips. Confirmed
actual context-driven polling, configured batch bounds, no transaction during
Kafka calls, backoff, other-row progress, outage suspension/recovery, restart-safe
DLQ intent and uncharged storage failure after acknowledged publication.

### T0020-005 result

OutboxArchitectureTest: 5 passing tests, no failures/errors/skips. New delivery
components preserve layer, mapper, transaction and entity-shape boundaries;
no model/Redis/MinIO/HTTP management integration. Reviewed AC-0020-001–009
against these checks and the earlier persistence/contract selectors. Total:
20 passing focused tests (4 persistence, 4 contract, 7 dispatcher, 5 architecture).
