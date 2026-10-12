# ADR 0022: Stone-Card Index Event Processing

Date: 2026-10-12

Status: Accepted; feature documents synchronized. Runtime readiness is not checked.

Feature number: 0022. The ADR number and filename prefix match the feature
specification directory number.

## Context

Feature [0022: Stone-Card Index Event Processing](../../specs/0022-stone-card-index-event-processing/spec.md)
consumes the index events defined in
[ADR-0021](0021-stone-card-index-event-publication.md) and uses the supporting
embedding capabilities from
[feature 0018](../../specs/0018-stone-card-embedding/spec.md).

The outbox publisher remains the separate responsibility defined in
[ADR-0020](0020-outbox-kafka-publication.md).

## Accepted Decisions

### Consumer Startup and Position

- Run one consumer automatically inside the backend, using consumer group
  stone-card-indexer.
- A new group starts with the earliest records still available in the topic.
  After restart, resume from the group's committed position rather than reset
  to the beginning.

### Durable Processing and Acknowledgement

- Confirm successful processing to Kafka only after the projection and its
  source-version state are committed to PostgreSQL.
- Persist the last processed sourceVersion and a deletion marker per stone.
  Keep that state when deleting the vector, so a delayed event cannot restore
  a card whose deletion has already been processed.
- Compute embeddings outside a database transaction. Save vector, rendered
  text and version state together in one short transaction.
- Delete the vector and update the deletion/version state atomically.
- If incoming sourceVersion is less than or equal to the last processed
  version, skip the event without invoking Ollama and confirm the skipped
  record. Use this version guard without a separate processed-UUID registry.

### Event-Specific Retries and Kafka Outages

- For event-specific processing failures, permit three total attempts: the
  initial attempt, a retry after 2 seconds and another retry after 4 seconds,
  followed by dead-letter handling.
- Kafka unavailability is not an event-specific failure and does not consume
  this retry budget. An outage alone must not discard an event or trigger
  dead-letter transfer.
- For the publisher, preserve pending events in PostgreSQL. Once publication
  detects Kafka unavailability, suspend ordinary sending while it remains
  unavailable and resume after recovery, consistent with ADR-0020. Do not
  delete pending events because Kafka is unavailable.
- For this consumer, do not confirm unfinished processing during Kafka
  unavailability. The exact recovery mechanics must respect the separately
  agreed Kafka topic retention; this decision does not promise indefinite
  Kafka-record retention.
- Ollama and PostgreSQL unavailability also do not consume the event-specific
  attempt budget. Wait for recovery without confirming unfinished processing.
- Under [ADR-0024](0024-exact-llm-search.md), healthy shared model-slot occupancy
  or token-budget exhaustion also waits without consuming attempts or entering
  DLQ. Stone creation still returns HTTP 201 after the stone/outbox commit;
  only the background indexer waits.
- Persist the event-specific processing-attempt count across backend restarts;
  restarting must not reset the three-attempt budget.
- Retry sequentially, delaying the whole source partition during an event's
  retries. Do not advance past the failed source record while it is unresolved.

### Dead-Letter Transfer

- Use the shared outbox-dead-letter topic and include metadata identifying a
  consumer-stage failure, together with the original event and agreed failure
  information.
- On exhausted event-specific attempts, confirm the source record only after
  successful, acknowledged dead-letter transfer. If transfer fails, leave the
  source record unconfirmed and retry transfer before continuing the partition.
- Invalid payloads, unknown event types and unsupported schemaVersion values
  go directly to dead-letter handling, without retries that cannot correct
  their contract.

### Profile Compatibility, Recovery and Observation

- An incompatible embedding profile stops processing until configuration is
  corrected. Do not switch models or rebuild the index automatically.
- If source events expire after the agreed 24-hour Kafka retention, restore
  the index through explicit reindexing in feature 0023. Do not implement
  automatic gap recovery in this feature.
- Provide logs and dead-letter error information without new HTTP operations,
  a management UI or dedicated metrics. Required durable version, deletion
  and attempt state remains in scope.

## Retry Strategy Rationale and Considered Alternative

The selected strategy is blocking, sequential retries on the original partition.
It fits the agreed single consumer and single partition, requires no additional
retry topics or routing contracts, and keeps source-offset advancement tied to
successful processing or acknowledged dead-letter transfer. Its accepted cost
is that a failing event delays later events in that partition, including while
waiting for a required dependency to recover.

The considered alternative was nonblocking retry topics: move failed work to
separate retry topics so later source events can continue. That approach offers
better progress for unrelated stones during retries, but adds topics, routing,
attempt tracking and coordination between the main and retry streams. It was
not selected for the initial implementation. sourceVersion guards remain
necessary with the selected strategy because the upstream dispatcher can
publish business changes out of order and Kafka can redeliver records.

## Accepted Out-of-Scope Decisions

Matching entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0022-001 | Separate processed-event UUID registry | Skip duplicate and stale work using per-stone sourceVersion state; do not add a separate registry of successfully processed UUIDs. Durable attempt tracking remains required. |
| OOS-0022-002 | Automatic model switching and incompatible-profile index rebuilding | Stop processing until configuration is corrected; do not change the embedding model or rebuild automatically. |
| OOS-0022-003 | Automatic recovery of events expired from Kafka | Restore missing index work through explicit reindexing in feature 0023 rather than automatic gap recovery in feature 0022. |
| OOS-0022-004 | Extended consumer observability and management interfaces | Keep logs, required processing state and DLQ error information. Do not add dedicated metrics, new HTTP operations or a management UI. |

## Preparation Boundary

The specification, acceptance criteria, technical plan and exact task
boundaries are synchronized with these decisions.
Acceptance criteria belong exclusively in acceptance.md. This ADR does not
authorize runtime preparation, code changes, application tests, commits or pushes.
