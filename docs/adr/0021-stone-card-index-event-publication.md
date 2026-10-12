# ADR 0021: Stone-Card Index Event Publication

Date: 2026-10-12

Status: Accepted; feature documents synchronized. Runtime readiness is not checked.

Feature number: 0021. The ADR number and filename prefix match the feature
specification directory number.

## Context

Feature [0021: Stone-Card Index Event Publication](../../specs/0021-stone-card-index-event-publication/spec.md)
connects stone changes to the shared outbox and Kafka dispatcher defined in
[ADR-0019](0019-outbox-event-persistence.md) and
[ADR-0020](0020-outbox-kafka-publication.md). Embedding input follows
[feature 0018](../../specs/0018-stone-card-embedding/spec.md).

## Accepted Decisions

### Events and Triggers

- Use one indexing-update event type for both card creation and relevant card
  changes, and a separate index-deletion event type.
- Relevant fields are name, stoneType, stoneSize and biography. Changes to
  photographs or admission date do not request an embedding update.
- Adoption status does not affect indexing in this feature. Availability and
  adoption filtering belong to the future search feature.
- If the indexed field values are unchanged, do not create an indexing event
  and do not increment sourceVersion.

### Snapshots and Source Versions

- Update-event payloads contain the complete agreed card snapshot: stoneId,
  name, stoneType, stoneSize and biography, together with sourceVersion. Do
  not substitute an identifier-only event that requires a catalog reload.
- Use the accepted initial sourceVersion baseline: a monotonically increasing
  numeric version per stone, advanced for changes that produce index events.
  Assign the source version in the same transaction as the business change
  and its event. This is distinct from the event-format schemaVersion.
- New cards start at sourceVersion 1. Initialize existing cards to version 0
  without automatically publishing events for them.
- Deletion payloads contain only stoneId and the next sourceVersion; do not
  include the deleted card snapshot.
- Both event formats start with schemaVersion 1. Stale-source-version
  handling belongs to the consumer in feature 0022.

### Publication and HTTP Behavior

- Write each business change and its outbox event in one database transaction.
  The calling service supplies the UUID eventId under ADR-0019.
- Return the existing successful HTTP response after commit without waiting
  for Kafka acknowledgement, embedding generation or index processing.
- An outbox-write failure rolls back the business change and returns HTTP 500
  ProblemDetail rather than a successful response. Services remain independent
  of HTTP error translation under the project constitution.
- Preserve existing successful HTTP response bodies; do not expose event
  identity or source-version fields through the catalog operations.
- Use one Kafka topic, stone-card-index-events, for update and deletion events,
  with one partition and replication factor 1. Provision it explicitly in
  feature 0021. Topic retention is 24 hours.
- Use the string representation of stoneId as the Kafka message key for both
  event types.

### Feature Boundaries

- Feature 0021 integrates event creation with card creation, relevant updates
  and deletion, and defines the concrete indexing AsyncAPI contract before
  Kafka implementation.
- Feature 0020 publishes the events; do not create another dispatcher.
- Feature 0022 generates embeddings, consumes index events, handles duplicate
  or stale work and deletes index projections.
- Feature 0023 owns explicit reindexing, including existing cards.

## Accepted Out-of-Scope Decisions

Matching entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0021-001 | Reindexing for photograph or admission-date changes | Publish update intentions for name, stoneType, stoneSize and biography; photograph and admission-date changes do not trigger indexing. |
| OOS-0021-002 | Adoption-status-driven index changes and availability filtering | Adoption status does not change indexing behavior in this feature. Availability and adoption filtering belong to future search work. |
| OOS-0021-003 | Embedding generation, event consumption and projection deletion | Feature 0022 processes indexing events, generates embeddings and deletes projections; feature 0021 only persists publication intentions. |
| OOS-0021-004 | Explicit reindexing and automatic publication for existing cards | Initialize existing source versions without emitting events. Feature 0023 owns explicit reindexing of existing cards. |
| OOS-0021-005 | Adding event or source-version fields to successful catalog HTTP responses | Preserve the existing successful response contracts; event identity and source versions are internal event data. |

## Rationale for the Final Three Decisions

### One Topic, One Partition and Replication Factor 1

Updates and deletions belong to the same stone-index lifecycle. One topic keeps
their publication history together and avoids the additional routing and
cross-topic coordination of separate update and deletion topics. One partition
keeps the initial local setup and consumption sequence simple; several partitions
would provide more consumer parallelism without a demonstrated requirement for
it in the current project.

Replication factor 1 fits the approved single-broker local infrastructure.
Additional replicas would require additional brokers and an infrastructure-scope
change. This choice provides no broker-level redundancy.

Kafka preserves the order in which records are written within a partition,
not the original business-change order when dispatcher retries delay an older
event. Therefore sourceVersion checks in feature 0022 remain necessary.
The [Kafka concepts reference](https://kafka.apache.org/42/getting-started/introduction/#main-concepts-and-terminology)
describes partition ordering, keys and replication.

### No Event or Version Increment for Unchanged Indexed Values

An unchanged indexing snapshot does not request new index content. Skipping
the event avoids redundant outbox rows, Kafka messages and embedding work.
Leaving sourceVersion unchanged keeps the version tied to meaningful index
changes. Explicit reindexing remains the responsibility of feature 0023.

### The Same stoneId Key for Updates and Deletions

stoneId identifies the shared lifecycle affected by both event types, whereas
eventId identifies an individual event. Using a stable stoneId key gives updates
and deletions a consistent per-stone routing identity. Under fixed topic
partitioning, both types for that key route to the same partition; eventId remains
available for duplicate detection, and sourceVersion for stale-event rejection.

## Preparation Boundary

The feature specification, acceptance criteria, technical plan and exact task
boundaries are synchronized with these decisions.
Acceptance criteria remain exclusively in acceptance.md. This ADR authorizes no
runtime preparation, code changes, application tests, commits or pushes.
