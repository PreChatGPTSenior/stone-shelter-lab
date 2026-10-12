# Feature 0023 Acceptance Criteria

Status: Criteria defined from accepted ADR-0023; not executed.

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

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.
