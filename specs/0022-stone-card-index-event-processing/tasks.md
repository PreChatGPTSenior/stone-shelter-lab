# Feature 0022 Tasks

Status: T0022-001 environment gate passed for the complete requested 0020–0024 batch; all feature 0022 tickets completed; shared admission retrofit remains in T0024-005.
The user explicitly authorized all tickets and one commit at the end of each feature.

## Mandatory First Ticket: Environment Readiness

- [x] T0022-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. Fresh batch checks are recorded below.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0022-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [x] T0022-002 — Add processing state and atomic projection apply. Depends on T0022-001.
- [x] T0022-003 — Prepare embeddings separately and wire automatic consumption. Depends on T0022-001 and T0022-002.
- [x] T0022-004 — Implement partition retries and durable DLQ transfer. Depends on T0022-001 and T0022-003.
- [x] T0022-005 — Review processing acceptance and architecture. Depends on T0022-001 and T0022-004.

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

### Feature 0022 readiness recheck

T0022-002–005: READY. Fresh authenticated PostgreSQL 18.6 connectivity, Kafka
metadata at kafka:9092 and Ollama 0.40.3 inventory/digests at the backend's
host.docker.internal endpoint confirmed before source changes. Existing backend,
PostgreSQL, Kafka, Redis and MinIO remain healthy; stopped frontend is unused.

### T0022-002 result

StoneIndexProcessingPersistenceTest: 5 passing cases. Atomic vector/version and deletion/tombstone commits, rollback, concurrency recheck, incompatible-profile rejection and restart-visible lossless attempt bytes verified.

### T0022-003 result

StoneIndexProcessingTest: 4 passing cases. Stale/duplicate skip before Ollama, preparation outside a database transaction, guarded commit before manual ack and earliest/manual/lossless consumer settings verified.

### T0022-004 result

StoneIndexRetryTest: 4 passing cases. Three charged attempts and 2s/4s pauses
survive consumer/processor restart. Kafka/Ollama/storage/profile waits do not
charge attempts. Invalid contracts transfer directly, malformed bytes round-trip
losslessly, failed DLQ remains durable and recovery works without the source
record; source ack follows DLQ ack. Native nack pauses while the container keeps
polling; the poll deadline exceeds the configured bounded embedding call.

### T0022-005 result

StoneIndexProcessingArchitectureTest: 4 passing cases. Services/repositories/domain
boundaries, transaction annotations, mapper inheritance and source-coordinate
attempt state checked. All 17 feature-0022 focused cases pass. Native startup,
manual-offset/earliest semantics and no gap/model-switch/management additions
reviewed against AC-0022-001–009. The accepted healthy slot/budget waiting portion
of AC-0022-006 is installed and regression-tested separately by T0024-005.
