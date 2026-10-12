# Feature 0019 Acceptance Criteria

Status: AC-0019-001 through AC-0019-009 verified by the permitted focused checks on 2026-10-12.

Read [spec.md](spec.md). The criteria below define the accepted outcomes; actual verification evidence
is recorded separately at the end. Permitted selectors and file boundaries live
in [tasks.md](tasks.md).

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

T0019-001 completed fresh readiness for T0019-002 through T0019-004 on
2026-10-12: READY. See tasks.md for expected/observed components and application
service impacts. Readiness does not satisfy functional acceptance criteria.

- T0019-002: OutboxValidationTest passed 5 tests (0 failures/errors/skips),
  covering AC-0019-002/003 and mapper input isolation. First-write metadata,
  repeated-identity validation and rollback behavior await T0019-003.

- T0019-003: OutboxPersistenceTest passed 11 tests (0 failures/errors/skips),
  covering AC-0019-001 and AC-0019-004 through AC-0019-009: metadata, duplicate
  semantics, validation-before-lookup, concurrent identities, atomic rollback,
  domain independence and multiple writes in one transaction. Migration/check
  constraints were applied through the complete root changelog in an isolated
  PostgreSQL container. No live catalog data was changed.

- T0019-004: OutboxArchitectureTest passed 5 tests (0 failures/errors/skips).
  Protocol/layer/DTO/entity/mapper/transaction boundaries and unrelated-integration
  exclusions were checked. Earlier functional results were reviewed without
  repeating unchanged successful selectors.

| Criterion | Result | Evidence |
| --- | --- | --- |
| AC-0019-001 | PASS | Mandatory transaction rejection, atomic commit/rollback, caught validation rollback-only and actual SQL-write-failure rollback in OutboxPersistenceTest. |
| AC-0019-002 | PASS | Missing/blank/nonpositive values and invalid JSON shapes rejected; arbitrary nonblank types accepted by OutboxValidationTest; integer schemaVersion is enforced by the typed internal request and database. |
| AC-0019-003 | PASS | Exactly 262144 compact UTF-8 bytes accepted, larger ASCII/multibyte payload rejected, formatting excluded in OutboxValidationTest. |
| AC-0019-004 | PASS | Caller UUID, jsonb round-trip, fixed UTC occurredAt/createdAt and PENDING persisted in OutboxPersistenceTest. |
| AC-0019-005 | PASS | Changed valid repeated payload ignored; one original row/timestamps/state returned in OutboxPersistenceTest. |
| AC-0019-006 | PASS | Type/version mismatch and invalid repeated payload rejected before identity lookup in OutboxPersistenceTest. |
| AC-0019-007 | PASS | Concurrent matching UUID writes retain one complete event; conflicting identities retain only the winning business commit in OutboxPersistenceTest. |
| AC-0019-008 | PASS | No outbox FK, business deletion retains events; domain/envelope source fields and independent layer rules reviewed/tested. |
| AC-0019-009 | PASS | Multiple writes share one transaction; scoped API/migration review and OutboxArchitectureTest confirm no dispatcher/Kafka/batch-write API/cleanup/configurable size policy. |

Final total: 21 passing focused tests; 0 final failures/errors/skips. The new
Liquibase change was verified through the root changelog in an isolated container;
no migration was applied to the live project database. Services stopped at
readiness are not dependencies of these selectors; no backend/frontend deployment
or wider regression suite is claimed. See tasks.md for the corrected initial
test-context/package-rule failures and the complete readiness report.
