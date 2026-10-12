# Feature 0019 Acceptance Criteria

Status: Criteria defined from accepted ADR-0019; not executed.

Read [spec.md](spec.md). These are planned acceptance outcomes, not verification
evidence. Permitted selectors and file boundaries live in [tasks.md](tasks.md).

## AC-0019-001: Atomic persistence

A committed caller transaction retains business data and one event; rollback or an event-write failure retains neither. Calling without an active transaction fails without creating one.

## AC-0019-002: Envelope validation

Reject null UUID, blank type, nonpositive/noninteger schema version, and null, empty-object, array or scalar payload; accept any valid nonblank eventType without a type registry.

## AC-0019-003: Payload size

Accept exactly 262144 bytes of compact UTF-8 JSON payload and reject a larger payload. Formatting whitespace and envelope bytes are excluded; multibyte characters count by UTF-8 bytes.

## AC-0019-004: First-write metadata

First persistence uses caller UUID, stores jsonb, assigns UTC occurredAt/createdAt and PENDING. Caller input does not control timestamps.

## AC-0019-005: Valid duplicate

An existing UUID with matching type/version succeeds without another row or payload comparison; original payload, timestamps and state remain unchanged.

## AC-0019-006: Duplicate errors

Type/version mismatch fails. Every duplicate invocation is validated first, so invalid repeated payload or envelope is rejected.

## AC-0019-007: Concurrent identity

Concurrent writes with one UUID retain at most one row. Matching type/version succeeds idempotently; a conflicting type/version fails without allowing the caller business transaction to commit independently.

## AC-0019-008: Domain independence

Business identifiers occur in payload only; no catalog foreign key or aggregate envelope fields. Deleted business data does not remove outbox history.

## AC-0019-009: Scope

Multiple individual writes can share a caller transaction. No Kafka send, batch API, automatic cleanup or configurable size policy is introduced.

## Verification Evidence

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.
