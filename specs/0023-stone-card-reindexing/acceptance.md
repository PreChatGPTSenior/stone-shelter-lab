# Feature 0023 Acceptance Criteria

Status: All nine criteria verified on 2026-10-12.

Read [spec.md](spec.md). These are planned acceptance outcomes, not verification
evidence. Permitted selectors and file boundaries live in [tasks.md](tasks.md).

## AC-0023-001: Start contract

Valid ALL or SELECTED (including one ID) creates a durable job and returns HTTP 201 with jobId/status. Invalid selection is 400; any explicitly missing ID is 404; active-job overlap is 409 ProblemDetail.

## AC-0023-002: Progress contract

GET returns the agreed state, counts, timestamps and recorded item errors; unknown job is 404 and unavailable storage 503. ENQUEUED is enqueue completion only.

## AC-0023-003: Durability

One active job is enforced in PostgreSQL; unfinished work resumes after restart. Job/item progress and each event commit together, so retry does not generate duplicate reindex work for a completed item.

## AC-0023-004: Bounded preparation

Default batches of 20 are environment configurable; no transaction covers processing the whole catalog. Forced reindex advances sourceVersion and emits even unchanged content.

## AC-0023-005: Mutation races

Snapshot, version and outbox write are atomic; cards deleted before item processing are skipped. Ordinary updates/deletes remain permitted and stale versions cannot overwrite later state.

## AC-0023-006: Creation gate

An active full job rejects new card creation with HTTP 409, including after restart. Targeted jobs do not impose that gate. Release it at durable ENQUEUED, without Kafka/vector waiting.

## AC-0023-007: Reconciliation

Full runs enqueue deletion recovery for projections absent from the catalog, preserving durable version/tombstone guards; valid projections are not discarded before replacement succeeds.

## AC-0023-008: Independent failures

Record a per-card preparation error and continue other items. PostgreSQL outage pauses unfinished work without falsely marking it done; completed work is preserved.

## AC-0023-009: Scope and history

Use only the current embedding profile; retain histories/errors indefinitely. No cancellation, scheduled run, profile/dimension change or end-to-end index-completion claim is added.

## Verification Evidence

Batch readiness, fresh prerequisites and selector details are recorded in tasks.md.
Isolated PostgreSQL and mocked background/model/broker components preserve live
catalog data. No live full-catalog rebuild was performed.

| Criteria | Evidence |
| --- | --- |
| AC-0023-001–003, 006 | StoneCardReindexPersistenceTest: 4 passing cases; unique active job, manifest/progress, durable gate and rollback. StoneCardReindexControllerTest: 14 passing cases; HTTP 201/400/404/409/503, generated JSON/YAML schemas and durable errors. |
| AC-0023-003–008 | StoneCardReindexServiceTest: 6 passing cases; bounded independent transactions, forced versions, completed-item replay safety, creation/start race, gate/release, deletion skip, orphan version above counter/processed state, independent errors and storage pause. |
| AC-0023-009 and architecture | StoneCardReindexArchitectureTest: 4 passing cases; no provider/Kafka call or new completion state. ENQUEUED describes durable intentions; current profile/vectors and indefinite history are preserved. |
| Existing catalog contract | StoneCatalogControllerTest: 105 passing cases after the new gate wiring. |

All 133 focused cases pass. The end-to-end completion follow-up remains deferred
as explicitly agreed; current jobs do not assert that vectors are ready.
