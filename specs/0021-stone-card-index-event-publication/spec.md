# Feature 0021: Stone-Card Index Event Publication

Status: Accepted requirements synchronized with ADR-0021; implementation and runtime readiness pending.

## References

- Feature decisions: [ADR-0021](../../docs/adr/0021-stone-card-index-event-publication.md).
- Shared discovery: [0015](../0015-llm-search-discovery/spec.md).
- Acceptance criteria: [acceptance.md](acceptance.md), exclusively.
- Technical decisions: [plan.md](plan.md).
- Exact execution boundaries: [tasks.md](tasks.md).

## Requirements

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

## Kafka and HTTP Contract Requirements

[asyncapi.json](asyncapi.json) defines STONE_CARD_INDEX_UPDATED and
STONE_CARD_INDEX_DELETED, both at schemaVersion 1, and imports the common envelope
from feature 0020. Provision stone-card-index-events with one partition,
replication factor 1 and 24-hour retention; key both event types by string stoneId.

Keep catalog routes and successful DTOs unchanged. Creation returns HTTP 201
after the stone and outbox commit, without waiting for Kafka/model/indexing.
Outbox failures roll back the business change and map to HTTP 500 ProblemDetail.
Existing photo-transfer behavior remains governed by its own feature; add the
outbox write to StoneCreationService's business transaction, not after commit.

## Accepted Exclusions

OOS-0021-001, OOS-0021-002, OOS-0021-003, OOS-0021-004, OOS-0021-005 are recorded with their authoritative meaning in
[ADR-0021](../../docs/adr/0021-stone-card-index-event-publication.md) and the
[central register](../../docs/out-of-scope-decisions.md).
No unanswered proposal is an accepted exclusion.

## Dependencies and Delivery Gate

- [0020](../0020-outbox-kafka-publication/spec.md).
- [0018](../0018-stone-card-embedding/spec.md).

Reuse existing pinned versions and capabilities. Prior task completion is not
current runtime readiness. The first ticket in tasks.md checks the entire
explicitly requested batch, with exact file allowlists and focused selectors.
No implementation is authorized by documentation synchronization. Do not run
application tests, inference, business imports, commits or pushes as part of
this documentation work. New schema changes use new Liquibase changesets;
generated OpenAPI remains the HTTP source of truth and AsyncAPI the Kafka
source of truth. Feature 0025 is not part of this review or preparation.
