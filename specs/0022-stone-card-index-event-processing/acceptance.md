# Feature 0022 Acceptance Criteria

Status: Criteria defined from accepted ADR-0022; not executed.

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

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.
