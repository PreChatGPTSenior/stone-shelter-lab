# Feature 0028: Chat Streaming API

Status: Discussion complete; specification, acceptance, plan and tasks prepared.
All 23 questions are answered. Runtime readiness, implementation, prototype and
integration verification are NOT RUN.

## Goal and References

Add a separate SSE response transport for existing completed chat turns. Find and
check relevant data, generate/validate the grounded response, check final eligible
stones and atomically persist the complete pair before emitting answer-text
fragments. Streaming delivers the saved response; it starts no speculative answer
before the application knows what may be shown.

- Detailed questions/answers/decisions: [ADR-0028](../../docs/adr/0028-chat-streaming-api.md).
- Acceptance criteria, exclusively: [acceptance.md](acceptance.md).
- Technical preparation: [plan.md](plan.md); tickets: [tasks.md](tasks.md).
- Shared history/security: [0016](../0016-llm-search-conversation-history-and-context/spec.md).
- Search/answer behavior: [0024](../0024-exact-llm-search/spec.md),
  [0025](../0025-semantic-stone-candidate-search/spec.md) and
  [0026](../0026-semantic-search-rag-answers/spec.md).
- Widget handoff: [0029](../0029-chat-streaming-widget/spec.md).

## Shared Turn Behavior

Keep the ordinary JSON endpoint and history response shapes. Both transports use
one request interpretation/search/answer/commit pipeline, ownership, original page
context, request quotas, turn identity, conversation exclusion, model controls and
original processing deadline. Switching routes cannot bypass those controls.

Exact/semantic selections, knowledge/mixed answers, clarification, absence and
legitimate backend templates use the same successful stream lifecycle. Streaming
adds no model call for wording/animation. Preserve 0025/0026 evidence precedence,
truthful unmatched wishes, hidden source attribution and current-turn template
exception; do not weaken checks or globally reopen chat after a dependency failure.

Perform final existence/AVAILABLE checks and permitted refresh/subset handling
before persistence. After confirmed persistence, the response is immutable for
history, fragments and retained replay, even if the catalog subsequently changes.
New intentional turns still check current catalog data.

## HTTP Requirements — Generated OpenAPI Remains Authoritative

Add POST /api/v1/chat/messages/stream, consuming application/json and producing
text/event-stream on admitted streaming delivery. Reuse StoneChatMessageRequest:
required UUID conversationId/turnId, nonblank message at most 2000 characters,
nullable context with the existing safe-positive stoneId limits. Preserve literal
message/context, session cookie, allowed Origin and existing validation/gate order.
No new public input field, GET subscription, cancellation or receipt endpoint.

HTTP 200 opens a transport; it does not by itself prove a completed history pair.
Before opening, failures use existing HTTP statuses/ProblemDetail/Retry-After.
Add HTTP 503 CHAT_STREAM_CAPACITY_EXCEEDED with Retry-After: 1 for full transport
capacity; it appends no pair, starts no model work and does not invalidate healthy
shared readiness. Retained replay consumes transport capacity too.

After opening, processing failures use a terminal error event with mapped public
ProblemDetail/status/code/detail and optional positive retryAfterSeconds. Its
status describes the business failure, not a replacement for already sent HTTP
headers. No completed follows error. Unknown persistence is CHAT_UNAVAILABLE,
not proof of rollback. Delivery failure after confirmed persistence cannot erase
or rewrite the saved result; close failed delivery and reconcile authoritative
history. Do not promise an error event when the socket is already unusable.

Set UTF-8 event-stream framing and Cache-Control: no-store at the HTTP boundary;
flush bounded frames without waiting for the entire body. Never expose session
credentials, evidence/source IDs, scores, checker verdicts or fallback metadata.
Generate JSON/YAML OpenAPI from route annotations and shared event DTOs, describing
named events and their lifecycle; maintain no separate handwritten OpenAPI.

## Named Event Payloads

SSE event names are exactly started, text-delta, completed and error; data is JSON.
Each named event has conversationId, turnId and sequence, a positive integer
starting at 1 and increasing by one within that HTTP stream. Heartbeat comments
have no sequence. A replay stream starts its own sequence for the same turn.
No SSE id/retry field or fragment-offset resume contract is introduced.

| Event | Additional data | Meaning |
| --- | --- | --- |
| started | None | Request/replay is admitted; no answer/persistence claim |
| text-delta | text, a nonempty literal fragment | Fragment of the confirmed saved answer |
| completed | text and ordered stones using existing id/name shapes | Full confirmed saved response; terminal success |
| error | problem, the public ProblemDetail with code and optional retryAfterSeconds | Terminal processing failure/uncertainty; no fabricated rollback |

Normally: started, zero or more keep-alive comments while preparing, saved-answer
text-delta fragments, completed, close. Concatenated fragments reproduce saved
text exactly, including Unicode/whitespace; no artificial typing delays. References
appear only in completed. Exactly one terminal named event is permitted when
terminal delivery is possible; EOF alone does not indicate success.

Retained identical-turn replay uses started then completed, without text-delta,
new retrieval/model work, duplicate history or server-retention refresh. A changed
payload preserves conflict rejection; active work preserves busy rejection.

## Persistence, Disconnect and Resource Lifetimes

Confirm atomic full-pair/identity/result persistence before the first answer delta.
Failed or unknown acknowledgement emits no text-delta/completed; retain uncertainty
and require readiness recovery/authoritative history before a manual retry. Store
no partial draft, heartbeat, stream error or transport event as a history message.

A client disconnect stops transport activity, not an admitted turn. Processing
continues within its original deadline/ownership/availability rules and may save
its result. Never automatically repeat model work, refund unknown usage, release
live provider/conversation permits or append a second pair because delivery failed.

Transport slots/writer resources are independent of actual model/turn permits.
No unbounded fragment/executor/heartbeat queue. A blocked writer must not stall
processing or lease renewal; release writer/slot ownership only on actual resource
exit. Failure to qualify these bounds blocks dependent production tickets.

## Initial Transport Configuration

Technical settings within the accepted resource policy; none is runtime-qualified:

| Setting | Initial value | Validation / scope |
| --- | --- | --- |
| CHAT_STREAM_MAX_CONCURRENT | 4 | Positive integer; per backend, includes replay |
| CHAT_STREAM_DELIVERY_TIMEOUT | PT10S | Positive finite duration; starts at confirmed saved-result readiness, including replay |
| CHAT_STREAM_HEARTBEAT_INTERVAL | PT15S | Positive finite duration; preparation-stage comments only |
| CHAT_STREAM_WRITE_TIMEOUT | PT10S | Positive finite duration no greater than delivery timeout; clipped to applicable remaining deadline |
| CHAT_STREAM_CHUNK_CODE_POINTS | 256 | Positive integer; literal Unicode-safe partitioning |

Keep at most one pending outbound frame per stream; coalesce/drop pending
heartbeats rather than queueing them. Heartbeats do not reset any deadline/TTL.
Processing/saving retains the existing 120-second deadline and 0026 completion
margin; delivery's separate bound cannot authorize a late business commit.
No waiting queue on full transport capacity or automatic reconnect/resumption.

## Narrow Cookie Rule for This Phase

For a newly admitted stream only, renew the browser cookie before headers open;
no early renewal on denied or retained-replay attempts. Server session/owner/
history retention still extends only after a new completed pair is saved.
A stale cookie never recreates ownership or extends expired server authority.

After completed, widget 0029 reads history when dependencies permit. A healthy
authorized history GET may synchronize the browser cookie with the remaining
actual server-session lifetime, without extending server TTL. Preserve cookie
secrecy/attributes and JSON's normal new-commit renewal. History synchronization
failure retains the completed response and uses verified recovery; it never
converts a completed turn into a fresh automatic POST.

This accepted baseline amends 0016 and remains subject to normal functional
qualification. Full detailed discussion/redesign of the cookie rule is explicitly
deferred until after phase-1 implementation, not an open blocker for this baseline.

## Boundaries, Dependencies and Current Authorization

OOS-0028-001–004 are recorded in ADR-0028 and the
[central register](../../docs/out-of-scope-decisions.md): unverified visible
production text, separate server cancellation, automatic reconnect/fragment
resumption and the later full detailed cookie-policy review.

Widget source belongs to 0029. Entire analytics feature 0030 is deferred to phase
2 under its [ADR](../../docs/adr/phase-2/0030-reliable-search-analytics.md); it is
no streaming dependency. No new library, schema, Kafka contract or model profile.
Broad model-quality evaluation remains 0027's separate scope.

Required 0025/0026 capabilities must be delivered and freshly checked for the
selected batch; prepared documents do not prove runtime availability. No unresolved
product question remains for the current baseline. Current authorization is
preparing documentation only: no task execution, source edits, application tests,
models, provisioning, commits or pushes.
