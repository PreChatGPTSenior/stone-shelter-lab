# ADR 0020: Outbox Kafka Publication

Date: 2026-10-12

Status: Accepted; feature documents synchronized. Runtime readiness is not checked.

Feature number: 0020. The ADR number and filename prefix match the feature
specification directory number.

## Context

Feature [0020: Outbox Kafka Publication](../../specs/0020-outbox-kafka-publication/spec.md)
dispatches committed events persisted under
[ADR-0019](0019-outbox-event-persistence.md). This record captures confirmed
answers without authorizing implementation.

## Accepted Decisions

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
  rule supported for domain contracts, such as stone identity in feature 0021.
- Define the shared envelope and delivery rules in AsyncAPI for this feature.
  Concrete indexing and analytics contracts belong to their domain features.

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

## Accepted Out-of-Scope Decisions

Matching entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0020-001 | Processing and replaying dead-letter events | Transfer exhausted events reliably to outbox-dead-letter, then do not consume, repair or replay them in this feature. |
| OOS-0020-002 | Concurrent dispatchers across multiple backend instances | Support one running backend instance and one dispatcher; do not implement distributed publication ownership in this feature. |
| OOS-0020-003 | Detailed analysis of Kafka never becoming available | Keep pending events durable without consuming the event-specific attempt limit during broker unavailability. Special escalation, terminal-outage policies and a detailed permanent-outage analysis are deferred. |
| OOS-0020-004 | Detailed diagnostic-field analysis and expanded observability | Keep the agreed logs and database fields with their basic semantics. Defer full analysis of those fields, dedicated metrics, new HTTP management operations and a management UI. |

## Consequences

Broker outages preserve pending normal publication for recovery. In contrast,
events exhausting their event-specific attempt budget terminate normal
publication and move to an unprocessed dead-letter topic. Successful dead-letter
transfer is not successful indexing or analytics processing.

Producer idempotence does not remove the need for consumer duplicate handling
when Kafka publication succeeds but the outbox result cannot be recorded.

## Preparation Boundary

The implementation specification, technical plan and task details are
synchronized with these decisions. Acceptance criteria belong exclusively in
the feature's acceptance.md. No runtime readiness, code changes, application
tests, commits or pushes are authorized by this ADR.
