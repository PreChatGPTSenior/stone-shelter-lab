# ADR 0023: Stone-Card Initial Indexing and Reindexing

Date: 2026-10-12

Status: Accepted; feature documents synchronized. Runtime readiness is not checked.

Feature number: 0023. The ADR number and filename prefix match the feature
specification directory number.

## Context

Feature [0023: Stone-Card Initial Indexing and Reindexing](../../specs/0023-stone-card-reindexing/spec.md)
provides explicit initial indexing and recovery. It reuses the outbox publisher,
index-event contracts and consumer defined in
[ADR-0020](0020-outbox-kafka-publication.md),
[ADR-0021](0021-stone-card-index-event-publication.md) and
[ADR-0022](0022-stone-card-index-event-processing.md).

## Accepted Decisions

### Operator Entry Point and Jobs

- Start reindexing through a backend HTTP operation.
- Return a job identifier and expose a separate HTTP operation for job state
  and progress. Exact routes, statuses and HTTP DTOs are defined in the
  synchronized feature specification; generate OpenAPI during implementation.
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

## Accepted Out-of-Scope Decisions

Matching entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0023-001 | Changes to the embedding model, profile or dimensionality | Reindex only with the current agreed embedding profile. Do not switch models even at the same dimensionality, migrate vector dimensions or implement profile/dimension migration in this feature. |
| OOS-0023-002 | Job cancellation | Provide no job-cancellation operation in the initial scope. |
| OOS-0023-003 | Job-history expiry and automatic cleanup | Retain job history and errors indefinitely; defer decisions about when to clean them and any cleanup implementation. |
| OOS-0023-004 | End-to-end index-completion tracking | Initially report durable event enqueueing as ENQUEUED. Revisit tracking actual consumer completion and final index readiness in a separate discussion. |
| OOS-0023-005 | Scheduled recurring reindexing | Use explicit operator-triggered initial indexing and recovery, without daily or other scheduled recurring runs. |

## Consequences

Targeted recovery can avoid rebuilding unrelated cards, while a full run also
reconciles projections for missing catalog records. The full-run creation lock
prevents new cards entering its selection, but ordinary updates and deletions
remain governed by current-snapshot transactions and source-version guards.
The lock ends at durable enqueueing; index freshness remains asynchronous.

## Preparation Boundary

The specification and plan define reconciliation/version handling and HTTP
requirements; acceptance criteria and exact task boundaries are synchronized. Generated OpenAPI remains the HTTP source of truth;
acceptance criteria belong exclusively in acceptance.md. This ADR authorizes no
runtime setup, business import, application code, tests, commits or pushes.
