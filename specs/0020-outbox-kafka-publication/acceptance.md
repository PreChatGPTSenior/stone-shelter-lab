# Feature 0020 Acceptance Criteria

Status: Criteria defined from accepted ADR-0020; not executed.

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

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.
