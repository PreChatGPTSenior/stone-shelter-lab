# Feature 0022 Acceptance Criteria

Status: Feature 0022 scope verified; shared-admission extension awaits T0024-005.

Read [spec.md](spec.md). These are planned acceptance outcomes, not verification
evidence. Permitted selectors and file boundaries live in [tasks.md](tasks.md).

## AC-0022-001: Startup and offsets

Start one stone-card-indexer consumer automatically. A new group uses earliest retained data; restart resumes committed offsets.

## AC-0022-002: Durable apply

A successful update commits vector, text and source-version state together before source acknowledgement. Embedding runs outside a PostgreSQL transaction.

## AC-0022-003: Stale and duplicate work

Versions less than or equal to persisted state skip without Ollama and can be acknowledged; version is rechecked during atomic apply.

## AC-0022-004: Deletion guard

Delete vector and persist the higher version/deletion marker atomically. A delayed earlier update cannot restore the deleted projection, including after restart.

## AC-0022-005: Retries

Event-specific failure is attempted three times total with 2-second/4-second delays; persisted attempt state survives restart and the whole source partition waits.

## AC-0022-006: Unavailable dependencies and admission

Kafka, PostgreSQL or Ollama outages do not consume attempts or acknowledge unfinished work. Under 0024, occupied slots/exhausted budgets also wait without attempts or DLQ.

## AC-0022-007: Terminal contracts

Invalid payloads, unknown types or unsupported schema versions go directly to DLQ, preserving the original record rather than silently dropping malformed input.

## AC-0022-008: DLQ boundary

Exhausted processing uses shared DLQ with CONSUMER-stage metadata. Source acknowledgement occurs only after DLQ acknowledgement; failed transfer/restart blocks the partition and retries transfer.

## AC-0022-009: Recovery boundaries

Incompatible profiles wait for correction without automatic switching/rebuild. Expired Kafka work needs explicit 0023 reindexing. No processed-UUID registry, retry topics or new management API is added.

## Verification Evidence

Whole-batch readiness and fresh feature checks are recorded in tasks.md.
Named selectors ran with isolated PostgreSQL and mocked Kafka/embedding providers.

| Criteria | Evidence |
| --- | --- |
| AC-0022-001–004 | StoneIndexProcessingTest: 4 passing cases; preparation outside transactions, stale skip, recheck and commit before ack. Native startup/manual-offset settings reviewed. StoneIndexProcessingPersistenceTest: 5 passing cases; atomic apply/delete, tombstones, concurrency, rollback and restart-visible state. |
| AC-0022-005–008 | StoneIndexRetryTest: 4 passing cases; 3 total attempts across restart, 2s/4s nack backoff, no charges for Kafka/Ollama/storage/profile waits, direct malformed-contract DLQ and lossless bytes, durable transfer recovery before source ack. |
| AC-0022-009 and boundaries | StoneIndexProcessingArchitectureTest: 4 passing cases; layers, entity shapes and source-coordinate attempt state. No processed-event UUID registry, retry topics, profile switching, gap recovery or management API introduced. |

All 17 current-feature cases pass. Healthy model-slot occupancy/token-budget
waiting under AC-0022-006 is explicitly dependent on the shared guard installation
and named regression selector in T0024-005; it has not been claimed verified here.
