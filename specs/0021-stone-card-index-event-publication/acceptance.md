# Feature 0021 Acceptance Criteria

Status: All eight criteria verified on 2026-10-12.

Read [spec.md](spec.md). These are planned acceptance outcomes, not verification
evidence. Permitted selectors and file boundaries live in [tasks.md](tasks.md).

## AC-0021-001: Creation

Creation initializes sourceVersion 1 and atomically persists a full indexing snapshot and fresh caller-generated event UUID. HTTP 201 waits only for the existing catalog workflow and stone/outbox commit, not Kafka/model/indexing.

## AC-0021-002: Relevant updates

Changes to name, stoneType, stoneSize or biography atomically increment the per-stone version and emit the complete new snapshot.

## AC-0021-003: Unchanged and excluded fields

Unchanged indexed values, photo-only, admission-date-only or adoption-status-only changes emit no indexing event and do not advance its source version.

## AC-0021-004: Deletion

Deletion emits only stoneId and the next sourceVersion in the same transaction; deleting the stone does not delete its durable version history or outbox event.

## AC-0021-005: Initial data

Existing cards start at version 0 without automatic events. Versions increase monotonically under concurrent relevant mutations and forced reindexing.

## AC-0021-006: Failure atomicity

Outbox/version failure rolls back the catalog mutation and maps to HTTP 500 ProblemDetail. Successful public DTOs gain no event/version fields.

## AC-0021-007: Contract and topic

Both event types at schemaVersion 1 match AsyncAPI; both use string stoneId keys on stone-card-index-events, one partition, replication factor 1 and 24-hour retention.

## AC-0021-008: Boundary

No embedding generation, new dispatcher or automatic existing-card reindex is added.

## Verification Evidence

See tasks.md for whole-batch readiness and named selector results. Tests use
isolated PostgreSQL and mocked delivery/model dependencies.

| Criteria | Evidence |
| --- | --- |
| AC-0021-001–004, 006 | StoneIndexPublicationTest: 5 passing cases, including full snapshots, unchanged/excluded fields, deletion history and atomic rollback. |
| AC-0021-005, 007 | StoneIndexSourceVersionTest: 4 passing cases; existing version-0 seeding, concurrent allocations, rollback/deletion survival and canonical payload contract. Topic/key registration checked by publication selector. |
| AC-0021-001, 006–008 | StoneCatalogControllerTest: 105 passing cases; HTTP 201/500, public responses and generated OpenAPI preserved. StoneIndexPublicationArchitectureTest: 3 passing cases; no synchronous embedding/Kafka work in catalog transactions. |

All 117 focused checks pass. Existing pessimistic catalog locking was reused;
no new dispatcher or automatic existing-card reindex was introduced.
