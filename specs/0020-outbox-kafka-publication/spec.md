# Feature 0020: Outbox Kafka Publication

Status: Accepted requirements synchronized with ADR-0020; implementation and runtime readiness pending.

## References

- Feature decisions: [ADR-0020](../../docs/adr/0020-outbox-kafka-publication.md).
- Shared discovery: [0015](../0015-llm-search-discovery/spec.md).
- Acceptance criteria: [acceptance.md](acceptance.md), exclusively.
- Technical decisions: [plan.md](plan.md).
- Exact execution boundaries: [tasks.md](tasks.md).

## Requirements

### Dispatcher and Polling

- Run the dispatcher inside the existing backend and start it automatically
  with the backend.
- Poll every 2 seconds and select up to 20 events per pass. Configure both
  values through environment variables.
- Publish committed outbox events. Do not keep a PostgreSQL transaction open
  while waiting for Kafka publication.
- A problematic event must not block publication of other events.
- Support one running backend instance and one dispatcher. Concurrent
  publication from multiple backend instances is outside this feature.

### Acknowledgement and Duplicate Delivery

- Mark normal publication successful only after receiving Kafka's
  acknowledgement. Use acks=all and enable producer idempotence.
- Duplicate deliveries are permitted and must be handled correctly by
  consumers using the stable event identity. This feature does not implement
  the indexing or analytics consumers.

### Bounded Attempts and Dead Letters

- Permit three total attempts to publish an event normally: the initial
  attempt, a retry after 2 seconds and a further retry after 4 seconds.
- Event-specific failures, including missing routes, follow this bounded
  policy. Exhaustion transitions the event to dead-letter publication rather
  than silently discarding it.
- Kafka unavailability does not consume this three-attempt budget. Retain
  the event in PENDING and resume normal publication when Kafka recovers.
- Detailed analysis and special handling of a Kafka deployment that never
  becomes available are outside scope; the basic durable waiting policy
  remains in scope.
- Use one shared Kafka topic named outbox-dead-letter.
- Dead-letter messages contain the complete original event, the failure
  reason, the normal-publication attempt count and the original topic when
  known. Do not omit the event when no route was available.
- If dead-letter publication fails, keep the event in PostgreSQL and retry
  the dead-letter transfer until Kafka acknowledges it, including after a
  backend restart. Do not resume normal publication for that event.
- Mark dead-letter transfer complete only after Kafka acknowledgement.
- Do not consume, repair or replay dead-letter events in this feature.

### Delivery State and Fields

- Use PENDING and PUBLISHED for normal publication, plus
  DEAD_LETTER_PENDING for an event awaiting transfer and DEAD_LETTERED after
  acknowledged dead-letter publication.
- Persist attemptCount, nextAttemptAt, lastError, publishedAt and
  deadLetteredAt.
- attemptCount counts normal-publication attempts charged to the bounded
  policy; Kafka-unavailability failures do not exhaust that budget.
- Set publishedAt only on successful normal publication, and deadLetteredAt
  only on successful dead-letter transfer. Do not mark dead-letter events as
  successfully published to their original destination.

### Message Envelope, Routing and Key

- Publish a JSON envelope containing eventId, eventType, schemaVersion,
  occurredAt and payload. Do not include internal outbox delivery fields in
  the normal message.
- Configure eventType-to-topic routing; the dispatcher does not modify the
  business payload. Domain features define concrete event types and topics.
- Default the Kafka message key to eventId, with an event-type-specific key
  rule supported for future contracts, such as stone identity in feature 0021.
- Define the shared envelope and delivery rules in AsyncAPI for this feature.
  Concrete indexing and analytics contracts remain future feature work.

### Topic Provisioning, Retention and Observation

- Explicitly provision outbox-dead-letter in feature 0020. Provision normal
  indexing and analytics topics in their respective domain features.
- The dead-letter Kafka topic retains messages for 24 hours by default; make
  that duration configurable through environment settings.
- Retain the original PostgreSQL outbox records indefinitely under ADR-0019.
  Kafka topic expiry does not authorize outbox cleanup.
- Provide logs and the agreed delivery/diagnostic fields in PostgreSQL.
  Additional HTTP operations, management UI and dedicated metrics are outside
  the current feature scope.
- Detailed analysis of the diagnostic fields and expanded observability are
  deferred; this does not remove the agreed fields or their basic semantics.

## Kafka Contract

[asyncapi.json](asyncapi.json) is the source of truth for the reusable envelope
and shared dead-letter format. Normal domain topic contracts belong to their
features. Configure routes through environment settings; use eventId as the
default key or a configured payload-field rule. Do not transform payloads.

Dead-letter metadata distinguishes PUBLISHER and CONSUMER failures. Preserve
malformed consumer records losslessly as base64 in originalEvent when a JSON
representation cannot preserve the record. There is no DLQ receive operation.
This feature adds no HTTP API.

## Accepted Exclusions

OOS-0020-001, OOS-0020-002, OOS-0020-003, OOS-0020-004 are recorded with their authoritative meaning in
[ADR-0020](../../docs/adr/0020-outbox-kafka-publication.md) and the
[central register](../../docs/out-of-scope-decisions.md).
No unanswered proposal is an accepted exclusion.

## Dependencies and Delivery Gate

- [0019](../0019-outbox-event-persistence/spec.md).

Reuse existing pinned versions and capabilities. Prior task completion is not
current runtime readiness. The first ticket in tasks.md checks the entire
explicitly requested batch, with exact file allowlists and focused selectors.
No implementation is authorized by documentation synchronization. Do not run
application tests, inference, business imports, commits or pushes as part of
this documentation work. New schema changes use new Liquibase changesets;
generated OpenAPI remains the HTTP source of truth and AsyncAPI the Kafka
source of truth. Feature 0025 is not part of this review or preparation.
