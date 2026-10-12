# Feature 0023: Stone-Card Initial Indexing and Reindexing

Status: Accepted requirements synchronized with ADR-0023; implementation and runtime readiness pending.

## References

- Feature decisions: [ADR-0023](../../docs/adr/0023-stone-card-reindexing.md).
- Shared discovery: [0015](../0015-llm-search-discovery/spec.md).
- Acceptance criteria: [acceptance.md](acceptance.md), exclusively.
- Technical decisions: [plan.md](plan.md).
- Exact execution boundaries: [tasks.md](tasks.md).

## Requirements

### Operator Entry Point and Jobs

- Start reindexing through a backend HTTP operation.
- Return a job identifier and expose a separate HTTP operation for job state
  and progress. Exact routes and HTTP DTOs require contract preparation.
- Permit one active job at a time; reject overlapping starts.
- Persist job state and progress in PostgreSQL. Automatically resume an
  unfinished job after backend restart.
- Provide no cancellation in the initial scope.
- Retain job history and errors indefinitely without automatic cleanup.
- This is an explicit initial-indexing or recovery operation rather than a
  scheduled daily routine.

### Work Selection and Event Generation

- Support one stone, an explicit list of stone IDs and the full catalog. A
  single selected stone is the one-element case of an explicit ID list.
- Reject requests containing explicitly selected nonexistent stone IDs with
  HTTP 404 ProblemDetail.
- Process cards in batches of 20, configurable through environment settings.
  Do not use a single transaction for the entire catalog.
- Create fresh index events through the existing outbox and consumer path.
- Forced reindexing increments sourceVersion and emits an event even when
  indexed fields have not changed. This is an explicit reindexing operation,
  distinct from the no-op ordinary-update rule in ADR-0021.
- Read the current card snapshot and advance its source version in one
  transaction with its outbox write. Skip a card deleted before that step.
- A card's failure does not invalidate or roll back work for other cards.
  Record individual errors and continue the remaining cards. Pause the job
  when PostgreSQL is unavailable.

### Concurrent Creation and Index Reconciliation

- While a full-catalog reindexing run is active, reject creation of new stone
  cards with HTTP 409 ProblemDetail instead of adding them to that run.
- Release the creation lock when the job's events are durably saved to the
  outbox and the job reaches ENQUEUED. Do not wait for Kafka publication or
  vector processing to release that lock.
- This decision changes the earlier proposal to permit new cards and rely on
  ordinary indexing events for them. No additional blanket prohibition on
  updates or deletions was accepted.
- A full run also reconciles stale projections for stones absent from the
  catalog, to repair missed deletions. Reconciliation must preserve durable
  deletion/version guards so delayed events cannot resurrect removed cards.
- Do not interpret full reconciliation as permission to discard valid
  projections before their replacement succeeds.

### Job Completion

- The selected initial result is ENQUEUED: all successfully prepared job
  events have been durably saved to the outbox, with individual preparation
  failures recorded separately.
- ENQUEUED does not mean every event has reached Kafka or every vector has
  been updated. Publication and consumer failures remain governed by their
  existing delivery policies.

### Embedding Profile

- Reindex only with the current agreed embedding profile. Changes to the
  model, profile or vector dimensionality are outside this feature's scope,
  including switching to another model with the same dimensionality.

## Deferred Question: End-to-End Completion Must Be Revisited

The user explicitly requested a separate record that the completion definition
must be revisited. The current job tracks durable event enqueueing, not final
index readiness. A future discussion must decide whether to track consumer
application, superseded events and terminal consumer failures per job, and how
to present an actual fully indexed result.

This follow-up is not implementation authorization or a scheduled task. Do not
silently label ENQUEUED as end-to-end completion. Creation-lock release is
explicitly tied to ENQUEUED under the current decision; reopening creation
does not establish that all vectors are ready.

## HTTP Contract Requirements

- POST `/api/v1/stone-card-reindex-jobs`: create a durable asynchronous job and
  return HTTP 201 with StoneCardReindexStartResponse containing jobId and status.
  No Location header is added. The body is StoneCardReindexStartRequest with
  target ALL or SELECTED and stoneIds. SELECTED requires a nonempty list of
  positive IDs (one ID represents a single card); deduplicate selected IDs.
  ALL accepts no selected IDs. Invalid input is 400, missing selected cards 404,
  an active job 409, and unavailable required storage 503, all ProblemDetail.
- GET `/api/v1/stone-card-reindex-jobs/{jobId}`: return HTTP 200 with
  StoneCardReindexStatusResponse: jobId, target, status, totalCount,
  processedCount, enqueuedCount, skippedCount, failedCount, reconciledCount,
  createdAt, updatedAt and errors (stoneId and error for each failed item).
  Unknown job is 404; unavailable required storage is 503 ProblemDetail.
- Durable states are PENDING, RUNNING and ENQUEUED. Dependency waiting preserves
  the unfinished state. ENQUEUED includes recorded per-item errors and means
  enqueue preparation has ended, not that all vectors are ready.
- Catalog creation during an active ALL job returns HTTP 409 ProblemDetail.
  Other catalog success DTOs remain unchanged; no blanket update/delete lock.

Generate OpenAPI from controllers/shared DTOs during implementation; no handwritten
OpenAPI is maintained. This feature adds no new Kafka event shape: reuse 0021.

## Explicit Follow-up

Revisit end-to-end completion separately as recorded in ADR-0023 and
OOS-0023-004. ENQUEUED and creation-lock release remain tied to durable outbox
preparation, including any required orphan deletion events.

## Accepted Exclusions

OOS-0023-001, OOS-0023-002, OOS-0023-003, OOS-0023-004, OOS-0023-005 are recorded with their authoritative meaning in
[ADR-0023](../../docs/adr/0023-stone-card-reindexing.md) and the
[central register](../../docs/out-of-scope-decisions.md).
No unanswered proposal is an accepted exclusion.

## Dependencies and Delivery Gate

- [0022](../0022-stone-card-index-event-processing/spec.md).

Reuse existing pinned versions and capabilities. Prior task completion is not
current runtime readiness. The first ticket in tasks.md checks the entire
explicitly requested batch, with exact file allowlists and focused selectors.
No implementation is authorized by documentation synchronization. Do not run
application tests, inference, business imports, commits or pushes as part of
this documentation work. New schema changes use new Liquibase changesets;
generated OpenAPI remains the HTTP source of truth and AsyncAPI the Kafka
source of truth. Feature 0025 is not part of this review or preparation.
