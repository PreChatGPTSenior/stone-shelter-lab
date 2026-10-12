# Feature 0019 Tasks

Status: All feature tickets T0019-001 through T0019-004 are complete; focused acceptance checks passed.
The user explicitly authorized execution of all feature 0019 tickets on 2026-10-12.

## Mandatory First Ticket: Environment Readiness

- [x] T0019-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. Fresh checks are recorded below.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0019-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [x] T0019-002 — Define request, entity mapping and validation. Depends on T0019-001.
- [x] T0019-003 — Add transactional outbox persistence. Depends on T0019-001 and T0019-002.
- [x] T0019-004 — Review focused acceptance and layer boundaries. Depends on T0019-001 and T0019-003.

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

## Execution Evidence: T0019-001 (2026-10-12)

Requested batch: all feature 0019 tickets. Covered implementation IDs:
T0019-002, T0019-003 and T0019-004. Result: READY.

| Component | Expected | Observed / impact |
| --- | --- | --- |
| Maven JVM | JDK 23.0.2 selected by JAVA_HOME | Homebrew 23.0.2 at /usr/local/Cellar/openjdk/23.0.2/libexec/openjdk.jdk/Contents/Home; wrapper reports Java 23.0.2. Inherited JDK 11 is overridden per command. |
| Maven | 3.9.9 | Wrapper reports 3.9.9. |
| Docker | Reachable daemon and disposable test support | Engine 29.8.1 reachable through unix:///Users/viktoryia/.docker/run/docker.sock; Ryuk 0.14.0 cached. |
| PostgreSQL image | Approved immutable pgvector image | sha256:78bf48b801e792f99e3ac62b5036fd3876e9be48afda16c1e331af1c75ceb2ff installed and matches the selected image. |
| PostgreSQL connectivity | Authenticated backend-network endpoint and PostgreSQL 18.6 | Read-only psql connection to postgres:5432/stone_shelter succeeds as configured user; server 18.6, vector extension 0.8.6. Credentials checked as present only. |
| Focused-test prerequisites | Existing pinned libraries available | Selected Spring Context/JDBC 7.0.9, Jackson 3.1.5, Liquibase 5.0.4, PostgreSQL JDBC 42.7.13, Testcontainers 2.0.5 and ArchUnit 1.5.1 artifacts cached; no new dependency required. |
| Backend / frontend | State and actual endpoint check | Both stopped; localhost:8080 and localhost:5173 refuse connections. Not dependencies of the isolated unit, transaction-context and architecture selectors. |
| Other services | Report relevance | Redis/Kafka stopped; not used by this batch. MinIO container running; not used or claimed ready by this batch. Ollama/model calls are not prerequisites. |

No service startup, model download, inference or business import was performed.
Initial socket/HTTP probes were denied by sandbox; authorized elevated read-only
probes established the states above. Test commands explicitly supply JAVA_HOME,
DOCKER_HOST and the Docker Desktop socket override. T0019-003 uses its own
isolated PostgreSQL container and a minimal Spring transaction context, not the
application context; there are no implicit Redis/MinIO/Kafka/model requirements.

### T0019-002 result

OutboxValidationTest: 5 tests, 0 failures/errors/skips; Maven BUILD SUCCESS on
JDK 23.0.2 (2026-10-12). Added only this ticket's nine source/test files. Checked
invalid envelope/payload inputs, arbitrary type support, mapper null/deep-copy
behavior, exact 262144-byte ASCII and UTF-8 boundaries and compact serialization.
No database/broker/model calls. Save/duplicate transaction behavior remains for
T0019-003. No successful selector is rerun without a relevant input change.

### T0019-003 result

OutboxPersistenceTest: 11 tests, 0 failures/errors/skips; Maven BUILD SUCCESS on
JDK 23.0.2 (2026-10-12). PostgreSQL connectivity was rechecked immediately before
implementation. The pinned disposable container and Ryuk started successfully.
Liquibase applied all eight master changesets, including the new 0019 change;
only the new include was appended to the master and applied migrations were not
modified. All fixtures and the disposable container were cleaned up.

Verified mandatory caller transaction, atomic business/event commit and rollback,
rollback-only after a caught validation error, actual SQL-write-failure rollback,
original metadata/payload/state on duplicates, type/version conflicts, validation
before duplicate lookup, concurrent matching/conflicting UUIDs and database
payload/version constraints. No catalog FK or live database mutation was added.
The first run failed during test context construction because JdbcTemplate was
not registered; registration was corrected and the named selector rerun without
changing its assertions. No application/provider services were started.

### T0019-004 result

OutboxArchitectureTest: 5 tests, 0 failures/errors/skips; Maven BUILD SUCCESS on
JDK 23.0.2 (2026-10-12). Checked service/repository/domain dependency boundaries,
protocol independence, request-record and entity/accessor shapes, mapper
inheritance/return contracts, service-class transaction annotations and absence
of Kafka/model/Redis/MinIO/controller integration in the outbox feature.

An initial architecture rule incorrectly matched mappers.entities as domain
entities; its package predicate was corrected to lab.stoneshelter.entities and
lab.stoneshelter.exceptions, preserving the domain constraint and the explicitly
permitted mapper-to-DTO dependency. The named selector then passed. Production
code was unchanged by that correction. Earlier successful functional selectors
were reviewed without rerunning them because their inputs did not change.

Final acceptance review: all AC-0019-001 through AC-0019-009 satisfied by the
recorded focused results and scoped source/migration review. Final total: 21
passing tests across OutboxValidationTest (5), OutboxPersistenceTest (11) and
OutboxArchitectureTest (5); no final failures, errors or skips. Main and test
sources compiled under the selected module roots. Only ticket-allowlisted files
and this feature's execution evidence were modified. No live catalog mutation,
Kafka/model call, full test suite, additional feature, commit or push performed.
