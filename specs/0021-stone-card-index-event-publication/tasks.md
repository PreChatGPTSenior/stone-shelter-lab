# Feature 0021 Tasks

Status: Execution tickets prepared from accepted ADR-0021; all remain unexecuted.
Documentation preparation does not authorize application implementation.

## Mandatory First Ticket: Environment Readiness

- [ ] T0021-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. No checks have run here.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0021-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [ ] T0021-002 — Define concrete payloads and durable source versions. Depends on T0021-001.
- [ ] T0021-003 — Integrate catalog transactions and indexing topic. Depends on T0021-001 and T0021-002.
- [ ] T0021-004 — Verify catalog HTTP preservation and architecture. Depends on T0021-001 and T0021-003.

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

### T0021-001

Writable: specs/0021-stone-card-index-event-publication/tasks.md and acceptance.md only.
Verification: read-only readiness probes and authorized routine startup described
in plan.md; no application test selector. Record corrective actions and affected
IDs on BLOCKED. Do not check any ticket complete from documentation alone.

### T0021-002: Define concrete payloads and durable source versions

Acceptance references: AC-0021-002 through AC-0021-005, AC-0021-007.

Writable:

- D/0021-create-stone-index-source-version.xml
- D/db.changelog-master.yaml
- J/entities/StoneIndexSourceVersionEntity.java
- J/repositories/StoneIndexSourceVersionRepository.java
- J/shared/StoneIndexUpdatePayload.java
- J/shared/StoneIndexDeletePayload.java
- J/enums/StoneIndexEventType.java
- J/mappers/dtos/StoneEntityToStoneIndexUpdatePayloadMapper.java
- T/StoneIndexSourceVersionTest.java

Selector: `StoneIndexSourceVersionTest`.

Verify version-0 seeding without events, concurrent monotonic allocation, deletion survival and payload contract/schemaVersion using isolated PostgreSQL.

### T0021-003: Integrate catalog transactions and indexing topic

Acceptance references: AC-0021-001 through AC-0021-008.

Writable:

- J/services/StoneIndexEventService.java
- J/services/StoneIndexKafkaTopicService.java
- J/services/StoneCreationService.java
- J/services/StoneService.java
- J/repositories/StoneEntityRepository.java
- J/handlers/ApiExceptionHandler.java
- .env.example
- T/StoneIndexPublicationTest.java

Selector: `StoneIndexPublicationTest`.

Verify creation/update/delete atomicity, relevant/no-op field behavior, string stoneId routes and no model/Kafka wait in catalog mutation; preserve existing photo workflow and response mapping.

### T0021-004: Verify catalog HTTP preservation and architecture

Acceptance references: AC-0021-001, AC-0021-006 through AC-0021-008.

Writable:

- T/StoneCatalogControllerTest.java
- T/StoneIndexPublicationArchitectureTest.java

Selector: `StoneCatalogControllerTest,StoneIndexPublicationArchitectureTest`.

Verify HTTP 201 after commit, 500 rollback translation and unchanged successful DTOs; compare generated OpenAPI for catalog operations and review prior acceptance evidence.

## Execution Boundary

Run only the selected ticket's named selector with controlled environment,
including JAVA_HOME, using ./mvnw -Dtest=<selector> test from stone-shelter-api.
No full suite, broad verify, business imports or unrequested later tickets.
Verification fixtures create/clean their own data and use isolated databases.
After the requested ticket(s), report results and stop; no commits or pushes
without explicit user authorization. No feature 0025 execution is authorized.
