# Feature 0021 Acceptance Criteria

Status: Criteria defined from accepted ADR-0021; not executed.

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

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.
