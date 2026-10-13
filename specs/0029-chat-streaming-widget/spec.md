# Feature 0029: Chat Streaming Widget

Status: T0029-001–006 complete. Recorded evidence satisfies the acceptance scope
amended on 2026-10-14; mock create/reservation browser submissions are no longer
required. Fresh closure readiness and mandatory UI lint/all 235 tests/build PASS.
See [browser-verification.md](browser-verification.md). The user has authorized
completion and local commits of all current changes; no push is requested.

## Goal and References

Integrate the prepared 0028 stream into the existing React chat widget without
creating a second conversation store or changing backend search behavior. Present
checked saved response fragments in one message and add authoritative links only
when valid completed arrives. Keep the entire explicitly selected development UI
usable on mock data without a backend for developers' testing only. The user-facing
API mode requires a backend and never becomes mock mode.

- Full questions/answers/decisions: [ADR-0029](../../docs/adr/0029-chat-streaming-widget.md).
- Acceptance criteria exclusively: [acceptance.md](acceptance.md).
- Technical preparation: [plan.md](plan.md); tickets: [tasks.md](tasks.md).
- Streaming contract: [0028](../0028-chat-streaming-api/spec.md).
- History/session lifecycle: [0016](../0016-llm-search-conversation-history-and-context/spec.md).
- Existing UI architecture: [ADR-0005](../../docs/adr/0005-ui-mock-to-code.md).
- Pinned UI versions: [ADR-0002](../../docs/adr/0002-stack-ui-versions.md).

## Modes and API Boundary

In API mode, each intentional send and explicit retry uses POST
/api/v1/chat/messages/stream with the captured original request, cookies and
existing identity/context semantics. Never switch real sends, history or recovery
to mocks, and never automatically resend a failed stream through JSON. Keep the
ordinary backend JSON route independent; it is not a widget recovery fallback.

Preserve the existing explicitly selected mock mode for developers' development
and testing only, including catalog/details/create/reservation/navigation and
chat/history. Backend-free operation belongs exclusively to this mode; the
user-facing API mode requires the backend. Matching mock
stream/history adapters provide the same interface and state outcomes without
real backend, cookie-service, model or network synchronization dependencies.
API/domain DTOs remain in api/dto; enums in enums; fixtures/adapters in mocks.
Components do not import mock datasets, and real API failure never selects them.
No new mode-switch control or unrelated catalog/mock rewrite is introduced.

Under the accepted 2026-10-14 amendment, retain existing automated coverage for
mock creation/reservation. Actual mock browser verification focuses on the changed
chat/history/navigation/reload lifecycle; creating Widget 0029 browser fixture and
submitting the Luna application are excluded from mandatory browser verification.
This changes verification scope, not the existing creation/reservation behavior.
The observable evidence requirements remain exclusively in [acceptance.md](acceptance.md).

Generated backend OpenAPI remains the HTTP source of truth. Client typed event
models conform to 0028; do not create another handwritten HTTP contract or change
backend DTOs/routes to make UI fixtures convenient.

## Stream Validation and Display

Validate HTTP success/content type, named event structure, expected conversationId/
turnId, safe positive contiguous sequence, literal text and reference shapes.
Ignore keep-alive comments. SSE/UTF-8 parsing must tolerate network chunks that
split bytes, lines or frames; malformed named data is not a heartbeat.

Accept started first, then checked nonempty text-delta fragments and a valid
terminal completed or error. Retained replay may contain started/completed with
no deltas. If deltas exist, their exact concatenation must equal completed.text;
completed supplies full text and ordered safe-positive id/nonblank name links.
A foreign identity, missing/repeated sequence, malformed payload, inconsistent
text or EOF without a valid terminal success requires manual recovery, not guessed
completion or repaired content. Close/release the reader at terminal delivery.

Show ordinary waiting until the first fragment. Append fragments to one assistant
message identified by the turn, not one bubble per event. A valid completed
finalizes that same message with full text/references immediately. Keep literal
plain text/whitespace/Unicode and existing separate catalog links, without HTML/
Markdown rendering or typing/replay animation. No visible sources/internal stages.

Block send/quick prompts/manual send-retry while awaiting/receiving or synchronizing
history. Keep draft editing available; edits never mutate captured original retry
payload/context. Ordinary UI blocking supplements server admission, not replaces it.

## Recovery and Provisional Partial-Answer Rule

Before completed, interrupted delivery retains received text in active-page state
with an incomplete marker and manual history recovery, without links or a completion
claim. This is explicitly provisional under OOS-0029-002. Definitive keep/clear/
redesign policy is deferred to a later discussion; no date/phase is assumed.
The provisional behavior still needs implementation and verification now.

Reconcile authoritative history by turnId/role, update existing matching messages
instead of duplicating them and preserve the captured original request while a
manual retry remains relevant. Never infer rollback from absent current history
or automatically resend. Retention loss/ownership/empty history follow 0016.

Once valid completed arrives, the response is confirmed even if the later history/
cookie synchronization fails. Retain full answer/links, show a separate recovery
problem and block further sends until successful verified recovery. Do not offer
send retry for that completed turn. A post-completion history read must match the
just-confirmed pair; a missing/inconsistent pair is a reconciliation issue, not
permission to erase success or fabricate rollback. Initial/manual restore retains
0016's authoritative expiry/ownership handling and never reconstructs lost work.

Recognized rate/busy/model-capacity/stream-capacity rejections preserve the original
payload and valid Retry-After cooldown for manual retry, without declaring a healthy
backend globally unavailable. Actual CHAT_UNAVAILABLE, unknown control/delivery or
malformed protocol requires manual authoritative recovery. Local model-output/
limit errors retain their existing contract distinction; do not guess retry times
or classify all 503 responses alike. A retry keeps one local user entry and uses
the exact original turnId/message/context. No automatic polling/reconnect/resume.

## Navigation, Reload and Cookie Handoff

Navigation, Back/Forward, collapse and temporary invisibility preserve conversation,
draft and the active stream through shared provider state. Render cleanup must not
abort/restart the request. Separate stop-display/generation UI is excluded.

Full reload restores saved history only, starts collapsed and does not persist/
restore old draft, pending/error/partial state or request payload. Backend work may
finish later; make manual history refresh available when idle, without automatic
polling or rebuilding the old request. New intentional sends use new turn IDs.

After completed, reconcile history when dependencies permit, using real GET only
in API mode and the matching mock history operation in mock mode. The narrow 0028
cookie baseline synchronizes browser expiry without extending server TTL; its
full detailed redesign/discussion is deferred after phase-1 implementation under
OOS-0028-004. Sync failure never regenerates a completed response or triggers mocks.

## Presentation and Accessibility

Follow incoming content automatically only while the user is at the conversation
bottom. Preserve older-message reading position; an initial 80 CSS-pixel near-bottom
tolerance is a technical preparation choice. Keep existing focus/Escape/collapse
behavior. Announce waiting and a newly completed answer once per turn, not every
fragment or repeated history reconciliation. Errors/statuses and final links remain
accessible through existing keyboard/live-region semantics.

API mode removes Demo assistant/Demo replies labels; explicit mock mode is clearly
identified. Preserve the existing 2000-character limit and plain-text input rules.
Mode identification is not evidence-source attribution.

## Technical Client Bounds

Initial VITE_CHAT_STREAM_TIMEOUT_MS=135000 is a positive finite integer, at most
2147483647 milliseconds to fit the browser timer range, bounding
one request/read attempt, not reset by started/heartbeat. It does not extend backend
processing, authorize automatic retry or cancel server work. API history operations
used for synchronization/recovery also receive a finite request bound. Mock mode
has no backend dependency.

Bound an unfinished encoded SSE frame and active streamed answer text initially
to 1 MiB each; parse complete frames incrementally rather than buffering the entire
response. These are technical client limits, not new backend search/model limits.
Confirm compatibility with valid maximum backend responses before integrated
execution; incompatibility is a blocker/correction proposal, not silent truncation.
Expired reads abort/release transport and use the accepted recovery state.

## Boundaries and Completion

OOS-0029-001–005 are identically recorded in ADR-0029 and the
[central register](../../docs/out-of-scope-decisions.md): API fallback to mocks/
automatic JSON resend, definitive interrupted-answer UX, separate stop UI and
rich markup/artificial typing. The existing analytics feature is
[0030, entirely phase 2](../0030-reliable-search-analytics/spec.md).

No library/version/schema/backend contract change or new model-quality campaign.
API execution requires delivered 0028 and fresh prerequisites; prepared documents
are not an available endpoint. No baseline product question remains open. Current
authorization is documentation only; stop after preparation/verification, without
code, application tests, browser operations, service startup, commits or pushes.
