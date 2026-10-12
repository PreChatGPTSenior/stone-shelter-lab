# Feature 0024 Acceptance Criteria

Status: Criteria defined from accepted ADR-0024; not executed.

Read [spec.md](spec.md). These are planned acceptance outcomes, not verification
evidence. Permitted selectors and file boundaries live in [tasks.md](tasks.md).

## AC-0024-001: Structured boundary

English requests produce validated SEARCH/CLARIFY/UNSUPPORTED decisions through the provider interface using Spring AI 2.0.1/Ollama qwen2.5:3b; no SQL or native tools are executed.

## AC-0024-002: Exact filters

Type/size/availability/date groups use OR within groups and AND across groups. Always require AVAILABLE. Unsupported properties are explained rather than silently dropped.

## AC-0024-003: Required clarification

No narrowing criteria means no catalog query or listing and at least two specific questions, including an all-available request. Ambiguous dates/references are clarified rather than guessed.

## AC-0024-004: Date semantics

UTC today/yesterday resolve from the clock; an explicit date needs a year; month/year covers the month. Dates are inclusive-start/exclusive-end ranges, ORed within the date group.

## AC-0024-005: Results

Use current catalog data, exact total count, admissionDate descending/id ascending, at most five real IDs/names, templates/refinement for more matches and absence-only wording for zero.

## AC-0024-006: Follow-ups and references

Inherit earlier criteria for follow-ups and reset for a new search. Ordinals use the latest shown list; page references use submitted context. Deleted/unavailable/ambiguous references request clarification without replacement.

## AC-0024-007: Invalid model output

Permit one repair and at most two calls per turn, without hidden retries. Still-invalid output is HTTP 502 and appends no completed pair.

## AC-0024-008: Context and deadlines

Keep the 120-second turn deadline and 50-second per-call limit; fit full prompt plus output within 4096, with output at most 512. Remove old whole pairs, preserving current request/system and stored UI history.

## AC-0024-009: Shared admission

All chat and embedding invocations share environment-configurable N=1. Customer slot occupancy immediately returns 503 CHAT_CAPACITY_EXCEEDED/Retry-After 1; budget exhaustion is 429 until next UTC day; no customer queue.

## AC-0024-010: Budget settlement

Default daily budget is 100000 tokens, environment configurable. Reserve 4096 separately for each chat call and profile maximum input per embedding; atomically settle reliable actual usage and release only the difference.

## AC-0024-011: Unknown usage and day boundary

Timeout/restart leaves unknown usage reserved against its original UTC day. A new UTC day has a separate budget; previous ledger records remain. Concurrent admission cannot overdraw the day budget.

## AC-0024-012: Separate waiting flows

Background indexing waits on healthy capacity/budget without attempts or DLQ; stone creation still returns 201 after commit. Markdown imports wait within their existing overall deadline and fail without partial publication when it expires.

## AC-0024-013: Availability and replay

Actual Ollama/control outage closes send/history/replay until verified recovery, without fallback. Healthy admission rejections do not invalidate shared availability. Committed replay makes no new model call/reservation; existing ownership/turnId/history policies remain.

## AC-0024-014: Initial qualification and scope

Named basic structured-output/token/profile checks are required, not a broad quality/tuning suite. No semantic matching, care answers, external provider, extra wording call or native tooling is added; preserve the after-0030 follow-ups.

## Verification Evidence

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.
