# Feature 0024 Acceptance Criteria

Status: Implemented; focused acceptance verification completed on 2026-10-13.

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

Keep the 120-second turn deadline and 50-second per-call limit; fit full prompt plus output within 8192, with output at most 512. Remove old whole pairs, preserving current request/system and stored UI history.

## AC-0024-009: Shared admission

All chat and embedding invocations share environment-configurable N=1. Customer slot occupancy immediately returns 503 CHAT_CAPACITY_EXCEEDED/Retry-After 1; budget exhaustion is 429 until next UTC day; no customer queue.

## AC-0024-010: Budget settlement

Default daily budget is 100000 tokens, environment configurable. Reserve 8192 separately for each chat call and profile maximum input per embedding; atomically settle reliable actual usage and release only the difference.

## AC-0024-011: Unknown usage and day boundary

Timeout/restart leaves unknown usage reserved against its original UTC day. A new UTC day has a separate budget; previous ledger records remain. Concurrent admission cannot overdraw the day budget.

## AC-0024-012: Separate waiting flows

Background indexing waits on healthy capacity/budget without attempts or DLQ; stone creation still returns 201 after commit. Markdown imports wait within their existing overall deadline and fail without partial publication when it expires.

## AC-0024-013: Availability and replay

Actual Ollama/control outage closes send/history/replay until verified recovery, without fallback. Healthy admission rejections do not invalidate shared availability. Committed replay makes no new model call/reservation; existing ownership/turnId/history policies remain.

## AC-0024-014: Initial qualification and scope

Named basic structured-output/token/profile checks are required, not a broad quality/tuning suite. No semantic matching, care answers, external provider, extra wording call or native tooling is added; preserve the after-0029 follow-ups.

## Verification Evidence

No application checks or fresh environment readiness have been performed for
this feature in the documentation-synchronization task. Record actual evidence
only when the explicitly selected execution ticket has run.

Fresh environment readiness for the complete requested 0020–0024 batch: READY
on 2026-10-12; see tasks.md for components, preparation, exact covered IDs and
previously approved Ollama 0.40.3. Functional criteria are not yet executed.

### Initial qualification evidence (2026-10-12)

External prerequisites are freshly READY, but T0024-002 failed its functional
gate. Exact SEARCH and reported usage, malformed-output usage and a single
correctly configured SDK HTTP attempt were verified. The all-available fixture
still returned SEARCH after its one repair, and native-window measurement of
the existing maximum request timed out at 50 seconds; its complete prompt bound
is unproven. See tasks.md for the actual counts, corrected fixture issues and
focused wire-contract follow-up. AC-0024-001/007/008/014 are not declared complete;
AC-0024-002–013 implementation and the shared embedding retrofit remain pending.

### Authorized remediation evidence (supersedes the initial blockage)

T0024-008: StoneExactSearchQualificationTest, 4 tests, 0 failures/errors.
The production schema policy produces CLARIFY/two questions/no invented narrowing
for the explicit availability-only fixture; exact SEARCH, actual usage, malformed
output accounting and no hidden SDK retry pass. Full permitted input plus the
bounded repair instruction and output reservation has conservative bound 8151
within 8192, verified against the installed tokenizer profile/template.

This proves the bounded qualification correction, not completed feature
acceptance: T0024-002 provider artifacts and T0024-003–007 are still pending.
No catalog/query, shared guard/ledger, timeout lifecycle, replay/history or
embedding integration is claimed verified by these four qualification tests.

## Final Acceptance Verification (2026-10-13)

| Criteria | Focused evidence |
| --- | --- |
| AC-0024-001, AC-0024-014 | Seven distinct qualification checks: actual installed provider/Spring AI/JDK transport, SEARCH/BASALT/SMALL, availability-only CLARIFY, usage, full permitted-input bound, whole-pair trimming, single HTTP attempt and native response-body timeout. |
| AC-0024-002–006 | Twenty service/persistence checks: OR/AND groups, mandatory AVAILABLE, full enum groups not narrowing, true count/top five/order, UTC days/months/missing year, unsupported wishes, partial-filter follow-ups/reset, latest shown ordinals/page references and current availability, legacy/optional metadata. |
| AC-0024-007–008 | One repair/two calls; strict fields/enums/ranges/trailing JSON; no pair on invalid output; complete prompt/output bound 8151/8192; actual provider timeout retains permit until worker exit; entire-turn timeout prevents late persistence. |
| AC-0024-009–011 | Ten admission/PostgreSQL checks: shared N=1, immediate customer rejection, import waiting/deadline, atomic reservations, idempotent settlement, concurrent budget protection, original-day unknown usage/UTC rollover and control failure signaling. |
| AC-0024-012 | Fifty embedding/index/import checks: full profile reservation, actual/invalid-output usage, unknown usage retained, healthy background admission deferral without attempts/ack/DLQ, deadline-aware import and unchanged atomic publication/profile. Catalog creation code remains independent of inference and is unchanged. |
| AC-0024-013 | Thirty-two chat/service/HTTP checks: committed replay without new processing, healthy 503/429/502 leave shared readiness open, model/control and background failure close it until verified manual recovery, retention only after commitment, existing ownership/history shapes. |

The four focused architecture checks enforce layers, protocol-independent
exceptions, transactions, mapper contracts and data-type conventions. Generated
JSON/YAML OpenAPI includes the agreed 502/503/429 outcomes and unchanged success
DTOs. No full suite, broad quality campaign, live import/reindex, new model,
dependency version or frontend work was executed. Test databases/data are isolated
and cleaned; background project jobs are mocked in Spring fixtures.

All 123 distinct checks in the permitted selectors passed. Relevant checks were
revisited only after their inputs changed; prior failed fixture setup and cold
HTTP-client initialization were diagnosed and corrected without weakening
required behavior. No claim of live deployment is made: the existing backend
container was used for readiness, while the implementation was compiled and
verified in focused test contexts.
