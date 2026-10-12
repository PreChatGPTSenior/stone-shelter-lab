# ADR 0019: Outbox Event Persistence

Date: 2026-10-12

Status: Accepted; feature documents synchronized. Runtime readiness is not checked.

Feature number: 0019. The ADR number and filename prefix match the feature
specification directory number; this is a feature decision record, not an
execution record for an individual T0019-xxx task.

## Context

Feature [0019: Outbox Event Persistence](../../specs/0019-outbox-event-persistence/spec.md)
defines a durable event write in the caller's existing PostgreSQL transaction.
Implementation is not authorized by this discussion record.

## Accepted Decisions

### Responsibility and Transaction Boundary

- Provide a protocol-independent internal operation for persisting outgoing
  events in PostgreSQL, reusable by indexing and analytics.
- Require the caller's already open transaction. A call without an active
  transaction fails; the outbox operation must not start its own transaction.
- Persist business changes and their outbox events in the same transaction.
  A rollback must not retain the event, and an event-write failure must not
  allow the associated business change to commit independently.
- Support individual event writes within one shared business transaction,
  including several calls in that transaction. No separate batch-write API
  was selected.

### Event Identity and Stored Fields

- The calling service creates and supplies eventId as a UUID.
- The common event fields are eventId, eventType, schemaVersion, occurredAt
  and payload. The outbox adds creation time and delivery state.
- The outbox service is the receiving service that generates occurredAt and
  createdAt when the event is first persisted, using UTC instants. The caller
  does not supply occurredAt under this agreed contract.
- Initialize delivery state to PENDING. Additional delivery fields and the
  dispatch lifecycle belong to feature 0020.

### Payload and Validation

- Store payload as a PostgreSQL jsonb object. Concrete indexing and analytics
  payload fields are defined by their respective event contracts.
- Require a non-null UUID eventId, a nonblank eventType, a positive integer
  schemaVersion and a nonempty JSON-object payload.
- Reject null, empty objects, arrays and scalar payloads.
- Enforce a fixed payload maximum of 256 KiB (262144 bytes) of JSON encoded
  in UTF-8. This is the selected project limit, not a claim of a universal
  recommended JSON limit.
- The common outbox validates the envelope and payload representation. The
  calling service validates the structure and meaning of its concrete event.
- Measure the payload alone using compact JSON serialization in UTF-8,
  without formatting whitespace or the common event envelope. Validate every
  invocation before duplicate lookup, including a repeated eventId; an invalid
  repeated payload is rejected even though valid repeated payloads are ignored.
- Accept any nonblank eventType without a registry of allowed event types.
  The dispatcher in feature 0020 handles routing and support for concrete
  types, determining the Kafka topic from eventType.

### Repeated Event IDs

- Look up the persisted event by eventId and compare only eventType and
  schemaVersion when that ID is reused.
- Matching type and schema version produce a successful no-op without a new
  row. Ignore the repeated payload; do not compare its contents with the
  stored payload.
- A type or schema-version mismatch raises an error.
- Preserve the original payload, occurredAt, createdAt and delivery state;
  a repeated write must not reset delivery or regenerate timestamps.
- The caller must ensure that one UUID always represents one immutable event.
- This decision supersedes the earlier proposal to reject any difference in
  event content. A payload-only change is not detected by the outbox.

### Retention and Domain Independence

- Retain events indefinitely, including pending and successfully dispatched
  events; provide no automatic expiry or cleanup in this feature.
- Keep events after deletion of an associated business object. Do not add
  a foreign key to the stone or another associated business object.
- Source-version information used to order stone changes belongs to the
  indexing event contract in feature 0021.

## Accepted Out-of-Scope Decisions

The following exclusions were explicitly accepted during discussion. Matching
entries are maintained in the [central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0019-001 | Support for empty event payloads | Reject null, empty objects, arrays and scalar payloads. Only nonempty JSON objects are accepted. |
| OOS-0019-002 | Automatic event expiry and cleanup | Retain events indefinitely, including pending and successfully dispatched events. This feature does not delete them through a retention policy or cleanup job. |
| OOS-0019-003 | Configurable and event-specific payload-size limits | Enforce a fixed maximum of 256 KiB (262144 bytes) of compact JSON payload encoded in UTF-8, excluding the event envelope. Custom limit configuration and different limits per event type are excluded. |
| OOS-0019-004 | Comparing payload content on repeated event IDs | Validate every invocation first. For an existing eventId with valid input, compare only eventType and schemaVersion. Matching values produce a successful no-op regardless of the supplied payload; a mismatch raises an error. Preserve the original payload, timestamps and delivery state. The caller must ensure that one UUID always identifies one immutable event. |

## Business-Object Reference

Keep identifiers such as stoneId inside the concrete payload. Do not add common
aggregateType or aggregateId fields. This keeps the reusable envelope independent
of domain-specific object references; the user explicitly accepted this choice.

## Preparation Boundary

The specification, acceptance criteria, technical plan and implementation
tasks are synchronized with this record. This ADR does not execute
or authorize implementation tickets.
Acceptance criteria remain exclusively in the feature's acceptance.md.

Dispatcher behavior, retries, Kafka keys and topic contracts are owned by
[ADR-0020](0020-outbox-kafka-publication.md) and the concrete domain contracts.
