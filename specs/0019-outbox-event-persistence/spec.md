# Feature 0019: Outbox Event Persistence

Status: Accepted requirements synchronized with ADR-0019; implementation and runtime readiness pending.

## References

- Feature decisions: [ADR-0019](../../docs/adr/0019-outbox-event-persistence.md).
- Shared discovery: [0015](../0015-llm-search-discovery/spec.md).
- Acceptance criteria: [acceptance.md](acceptance.md), exclusively.
- Technical decisions: [plan.md](plan.md).
- Exact execution boundaries: [tasks.md](tasks.md).

## Requirements

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

### Business-Object Reference

Keep identifiers such as stoneId inside the concrete payload. Do not add common
aggregateType or aggregateId fields. This keeps the reusable envelope independent
of domain-specific object references; the user explicitly accepted this choice.

## Internal Contract

`OutboxWriteRequest` contains eventId (UUID), eventType (string), schemaVersion
(positive integer) and payload (JSON object). `OutboxService.save` returns the
stored `OutboxEventEntity`, including on a valid duplicate; it requires an already
active caller transaction. Invalid input, mismatched repeated identity and absent
transactions raise protocol-independent exceptions. This feature adds no HTTP
endpoint and publishes no Kafka messages.

Object identifiers live only in concrete payloads; the envelope has no
aggregateType or aggregateId. Every invocation is validated before duplicate
lookup. The 262144-byte bound applies only to compact UTF-8 JSON payload.

## Accepted Exclusions

OOS-0019-001, OOS-0019-002, OOS-0019-003, OOS-0019-004 are recorded with their authoritative meaning in
[ADR-0019](../../docs/adr/0019-outbox-event-persistence.md) and the
[central register](../../docs/out-of-scope-decisions.md).
No unanswered proposal is an accepted exclusion.

## Dependencies and Delivery Gate

- [0014](../0014-llm-search-infrastructure/spec.md).

Reuse existing pinned versions and capabilities. Prior task completion is not
current runtime readiness. The first ticket in tasks.md checks the entire
explicitly requested batch, with exact file allowlists and focused selectors.
No implementation is authorized by documentation synchronization. Do not run
application tests, inference, business imports, commits or pushes as part of
this documentation work. New schema changes use new Liquibase changesets;
generated OpenAPI remains the HTTP source of truth and AsyncAPI the Kafka
source of truth. Feature 0025 is not part of this review or preparation.
