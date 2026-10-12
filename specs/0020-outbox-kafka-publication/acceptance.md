# Feature 0020 Acceptance Criteria

Status: All nine criteria verified on 2026-10-12.

Read [spec.md](spec.md). These are planned acceptance outcomes, not verification
evidence. Permitted selectors and file boundaries live in [tasks.md](tasks.md).

## AC-0020-001: Automatic polling

One in-backend dispatcher starts automatically; defaults are 2 seconds and 20 eligible rows, both environment configurable.

## AC-0020-002: Transaction boundary

Only committed events are sent; no PostgreSQL transaction spans a Kafka call. PUBLISHED/publishedAt are recorded only after acks=all acknowledgement.

## AC-0020-003: Delivery identity

Enable producer idempotence while permitting duplicates after send-success/state-write failure. Normal envelope and routing/key rules match AsyncAPI; payload is unchanged.

## AC-0020-004: Bounded attempts

Event-specific failures including missing routes charge exactly three total normal attempts, with 2-second and 4-second retry delays. Other eligible rows still progress.

## AC-0020-005: Broker outage

Kafka unavailability preserves pending data, consumes no bounded attempts and suspends ordinary sends until recovery.

## AC-0020-006: Durable dead letters

Exhaustion persists DEAD_LETTER_PENDING, transfers complete original event and failure metadata to outbox-dead-letter, then marks DEAD_LETTERED/deadLetteredAt only after acknowledgement.

## AC-0020-007: DLQ recovery

Failed transfer and restart preserve the DLQ destination and retry indefinitely, without returning the event to normal publication.

## AC-0020-008: Delivery fields

attemptCount counts charged normal attempts; nextAttemptAt and lastError reflect persisted work; publishedAt is absent for DLQ-only completion.

## AC-0020-009: Provisioning and retention

Explicit DLQ provisioning uses configurable default 24-hour retention. PostgreSQL records are not expired. Logs/fields are supplied without HTTP/UI/metrics or DLQ consumption.

## Verification Evidence

READY batch prerequisites and individual selector results are recorded in tasks.md.
All checks used JDK 23.0.2 and disposable PostgreSQL with mocked Kafka; no live
business publication was performed.

| Criteria | Evidence |
| --- | --- |
| AC-0020-001, 004, 005, 007 | OutboxDispatcherTest: 7 passing cases, including automatic scheduling, batch bound, retry timing, independent progress, outage/restart recovery. |
| AC-0020-002, 003, 006 | OutboxKafkaContractTest: 4 passing cases; dispatcher verifies acknowledged state transitions and absence of an active database transaction during sends. |
| AC-0020-008, 009 | OutboxDeliveryPersistenceTest: 4 passing cases; durable state/defaults/constraints and explicit retention provisioning. No PostgreSQL expiry or DLQ consumer exists. |
| All criteria: boundaries | OutboxArchitectureTest: 5 passing cases; source and canonical AsyncAPI reviewed. |
