# Feature 0028 Acceptance Criteria

Status: Accepted baseline implemented and verified under the requested 0025–0029
batch. Evidence below distinguishes actual native transport, controlled business
outcomes and the separate real-model qualification in 0027.

| ID | Required observable outcome and evidence |
| --- | --- |
| AC-0028-001 | Fresh readiness covers every explicitly requested full ticket ID, actual tools/settings/required endpoints and delivered dependencies; blocked requirements prevent the affected batch from starting. |
| AC-0028-002 | The test-only prototype exercises a real pinned Servlet/MVC HTTP transport and proves bounded delivery/actual writer cleanup, disconnect continuation, heartbeat behavior and stream-cap admission; mocks/timer callbacks alone do not prove blocking-I/O cleanup. |
| AC-0028-003 | Generated OpenAPI JSON/YAML describe the separate POST route, existing request constraints, text/event-stream lifecycle, named shared payloads and pre-open ProblemDetail errors; JSON/history payload shapes remain compatible. |
| AC-0028-004 | Rejected Origin/ownership/input/readiness/quota/active-turn requests never emit started or start new model work; JSON/SSE share effective gate/counter/turn protections without double charging or route bypass. |
| AC-0028-005 | Findings/evidence, complete prose checking and final eligibility follow delivered 0025/0026 rules; only a confirmed atomically saved full pair/result can supply any text-delta or completed. |
| AC-0028-006 | Valid successful streams use exact names/IDs/contiguous sequence, preserve UTF-8/Unicode/literal text and ordered final references, and have one terminal event when deliverable; concatenated deltas equal completed/saved text, without source leakage or artificial pacing. |
| AC-0028-007 | Exact/semantic, knowledge/mixed, clarification/absence and legitimate template results retain the same business/history outcome through JSON and SSE; streaming introduces no additional interpretation/retrieval/generation or wording invocation. |
| AC-0028-008 | Identical retained-turn replay across either route returns the same saved text/reference order without new work/pair/TTL activity; SSE replay emits started/completed only, while changed payload and active work retain conflict/busy behavior. |
| AC-0028-009 | Disconnect before result completion closes transport/heartbeat only; admitted processing follows original deadline/fencing/actual-exit rules and saves at most once. Lost completed delivery recovers the saved result without regeneration or rollback. |
| AC-0028-010 | Lost persistence acknowledgement produces no answer fragments/success; error is delivered only when possible, authoritative recovery distinguishes saved/unsaved/unknown, and no automatic duplicate POST/append or assumed rollback occurs. |
| AC-0028-011 | Separate configurable stream cap initially 4 includes replay; overflow returns pre-open 503 CHAT_STREAM_CAPACITY_EXCEEDED/Retry-After 1 with no queue/model work, while transport-only saturation does not close healthy JSON/history readiness. |
| AC-0028-012 | Processing/commit stays within inherited 120 seconds; saved-result delivery is separately bounded initially at 10 seconds. Heartbeat initially every 15 seconds changes no TTL/deadline, queueing is bounded and blocked transport does not stop processing/lease renewal or release live work resources. |
| AC-0028-013 | Post-commit catalog mutation does not alter saved fragments/completed/history/replay. Pre-commit mutation retains 0025/0026 final subset/refresh/veto limits. Post-open error never fabricates success, and post-commit I/O failure never deletes the saved result. |
| AC-0028-014 | Newly admitted streams renew only browser cookie before opening; denied/replay attempts have no early renewal. Only completed saves extend server retention; healthy authorized history GET synchronizes to remaining server lifetime without server extension or lost-owner transfer, and failed sync preserves completed content for recovery. |
| AC-0028-015 | Scoped architecture/diff review confirms ready mapped responses, thin controllers, handler-owned HTTP/SSE/ProblemDetail logic and protocol-independent services/entities; no UI/analytics/cancellation/resume/schema/dependency change or deferred detailed cookie-policy redesign enters this feature. |

## Focused Execution Oracles

Use isolated self-owned history/catalog fixtures and controlled provider outcomes
for integration: same literal completed text/order via both routes; a normal
selection, knowledge/mixed answer, clarification, zero result and permitted
selection template. A knowledge-only generation failure is an error, not a
selection template. Real-model quality is not certified by controlled outcomes.

Use real socket delivery for transport checks, including Unicode/whitespace
fragment boundaries, ordinary completion, replay without deltas, pre-open gate
failures, four held streams/fifth rejection, stalled started/heartbeat writes,
slow readers, disconnect before save and after save, and a lost commit
acknowledgement. Assert actual resource exit/remaining ownership, not just a
scheduled timeout or an emitter callback. Short isolated test overrides may
exercise deadlines; verify production default mappings separately. No benchmark.

Cookie cases cover new admitted stream, rejected request, replay, processing
failure after opening, newly confirmed save, healthy history sync with decreasing
remaining lifetime, expiry/foreign owner and sync failure after a legitimate
current-turn template. Real Redis verifies server expiry is not refreshed by
GET/replay. Earlier 0016 results do not count as this amendment's evidence.

## Evidence and Deferred Follow-Up

AC-0028-001–015: PASS for the accepted streaming baseline. Readiness covers the
requested batch; the native prototype passes; actual production HTTP/Redis/socket
integration passes ten cases plus an additional foreign/expired-cookie case, and generated JSON/YAML/architecture/history checks
pass five additional cases. Final grouped-resource recheck passes ten native and
two architecture cases. Common PostgreSQL/RAG integration passes 13 cases,
semantic conversation integration eight, chatbot three, stream state five and
session storage one. No full backend suite or new inference campaign was run.

Evidence includes literal Unicode/whitespace, acknowledged pair before deltas,
shared JSON/SSE replay, payload conflict, Origin/input rejection, post-open error,
lost commit acknowledgement/recovery, client-disconnect continuation, four streams
and fifth rejection, no ghost owner on overflow, slow socket closure, expired
saved-readiness delivery and independent watchdog closure while a live writer
retains its slot. Actual Redis proves GET/replay do not extend server TTL; the
accepted remaining-lifetime cookie amendment has separate functional proof.

The full application image builds; isolated actual API health is UP, generated
OpenAPI exposes the stream, and 0029 browser smoke receives a saved checked answer
and restores it through real history/reload. This functional smoke does not certify
0027's fixed-set model-quality thresholds. Detailed execution is in ADR-0028.

OOS-0028-004 defers full detailed cookie-rule review until after implementation
of phase 1. It does not remove AC-0028-014 or move 0028/0029 out of this phase.
Record actual baseline verification independently from that future discussion.
