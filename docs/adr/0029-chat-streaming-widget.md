# ADR 0029: Chat Streaming Widget

Date: 2026-10-13

Status: Discussion complete; all 17 questions answered. Specification, acceptance,
plan and tasks prepared for the accepted baseline, including complete explicit
mock-UI operation and provisional interrupted-text presentation. No implementation,
readiness, application tests, browser qualification, new commit or push is claimed.

Feature: [0029: Chat Streaming Widget](../../specs/0029-chat-streaming-widget/spec.md).
Related [acceptance](../../specs/0029-chat-streaming-widget/acceptance.md),
[plan](../../specs/0029-chat-streaming-widget/plan.md) and
[tasks](../../specs/0029-chat-streaming-widget/tasks.md).

## Context and Accepted Discussion Procedure

The user's instruction to discuss 0028 then 0029, record the entire detailed
course of questions/answers/decisions in English ADRs and prepare each feature's
specification/acceptance/plan/tasks after discussion is translated in
[ADR-0028, D0028-001](0028-chat-streaming-api.md). Questions are asked directly
in chat. This remains documentation-only; earlier completed commit authorization
does not authorize new commits, tests, services or implementation.

## Inherited Requirements — Not New Answers

- [0028](../../specs/0028-chat-streaming-api/spec.md) provides an admitted POST
  stream, started/text-delta/completed/error, matching turn identity/sequence,
  literal checked saved text, references only on completed, replay without deltas
  and no automatic reconnect/resume or separate server cancellation.
- Answer prose starts only after the full response is checked and persisted;
  pre-generation model-token display is not the application behavior.
- A disconnect does not cancel the admitted backend turn. Authoritative history
  and explicit unchanged-turn replay recover the result without new model work.
- [0016](../../specs/0016-llm-search-conversation-history-and-context/spec.md)
  owns cookie-bound identity, initial restore, original payload/page context,
  conversation persistence across navigation/collapse, full-reload history-only
  restoration, manual recovery and no automatic send retry/mock fallback.
- After completed, reconcile history/cookie when dependencies permit under the
  narrow accepted 0028 cookie rule. Its full detailed redesign/discussion is
  deferred after phase-1 implementation under OOS-0028-004; do not reopen that
  deferred review during this widget discussion.
- Analytics 0030 is entirely deferred to phase 2. Prepared backend documents
  do not establish a ready endpoint for widget execution.

Read-only inspection: current chatbotApi returns one JSON answer; ChatSessionProvider
owns draft/binding/restore/send/retry state; StoneChatbot renders complete bubbles
and allows draft editing while sending is blocked. Preserve responsibilities,
UI conventions and the existing explicit API/mock boundary, rather than starting
a second conversation store. No package manifest/config/build output was read.

## Discussion Log

Questions are asked directly in chat on 2026-10-13 and translated below. Keep
actual answers and explicit amendments; proposals with pending answers are not
accepted decisions. Numbering continues across chat blocks.

### Q0029-001 — Default API transport and fallback

Question, translated into English:

> In API mode, use the 0028 stream for every send/retry. If it fails, show the
> error/recovery path without automatically switching to JSON or mock mode.
> Agree?

Reason/alternatives: route fallback could hide a protocol failure, duplicate
admission or create another request while the original turn continues. The JSON
backend remains available independently; explicit mock mode still needs its own
matching event/lifecycle behavior. No fallback exclusion is accepted yet.

User answer, translated into English:

> 1: Yes. Note separately that in real mode we never switch to mocks.

Accepted decision D0029-001 — Real API mode never becomes mock mode:

- API-mode sends/retries use the 0028 streaming endpoint. A failure does not
  trigger an automatic JSON resend or another transport processing attempt.
- Record the user's emphasis separately: API mode never transitions to mocks,
  for sending, retry, history restoration or recovery, under any failure condition.
  Missing/unavailable/malformed data cannot be replaced with mock output.
- An explicitly selected development/mock mode is a separate existing mode;
  this does not authorize a runtime fallback toggle within real API operation.
- Preserve errors/readiness/manual recovery and actual captured turn identity.
  OOS-0029-001 records this boundary in both ADR and central register.

### Q0029-002 — Waiting before the first checked fragment

Question, translated into English:

> Before the first text-delta, show the ordinary waiting indicator. Do not show
> intermediate search/source details or generated draft text. Agree?

Reason/alternatives: started/heartbeat signal transport, not substantive answers
or model progress. Preserve the accepted evidence-first boundary; exact status
wording/rendering is prepared later without implying a speed guarantee.

User answer, translated into English: "2: Yes."

Accepted decision D0029-002:

- Show ordinary waiting before the first checked text-delta.
- started/heartbeat never produce speculative prose, internal retrieval/source
  details or fabricated model progress. Keep sources hidden as in 0026/0028.
- Exact accessible/status wording is implementation preparation, not a promise
  that answer text arrives while generation is still running.

### Q0029-003 — One incremental assistant message and final references

Question, translated into English:

> Append fragments to one assistant message for the turn, not a new message per
> fragment. On completed, finalize that same message with the full text and only
> then add the ordered stone links. Agree?

Reason/alternatives: one turn has one completed pair. Replay/history reconciliation
must update that identity rather than duplicate a bubble. Protocol validation,
literal fragment/full-text consistency and out-of-order/malformed handling need
the following discussion block; finalization does not authorize accepting bad data.

User answer, translated into English: "3: Yes."

Accepted decision D0029-003:

- One assistant message per turn receives incremental fragments, not one bubble
  per fragment. A valid completed finalizes the same message with the full text.
- Add only completed's ordered stone references; no guessed/intermediate links.
- Replay/history reconciles the same turn identity without duplicate bubbles.
  Concrete protocol validation remains to be settled in the next block.

### Q0029-004 — Sending versus editing while a turn is active

Question, translated into English:

> Block send, quick prompts and manual resend while waiting/receiving or
> reconciling history after completed. Keep editing the draft available. Agree?

Reason/alternatives: UI prevents accidental overlapping sends without replacing
server conversation exclusion. Editing is separate from submitting; captured
original request/context must remain immutable for any later retry. Readiness/
rate failure and pending recovery still block sends under existing rules.

User answer, translated into English: "4: Yes."

Accepted decision D0029-004:

- Disable send, quick prompts and manual resend during waiting/delivery and the
  post-completion history reconciliation operation.
- Draft editing remains available; editing must not change the already submitted
  original message/context/turnId used for any later explicit retry.
- UI blocking complements existing server ownership/quota/conversation controls;
  it is not their replacement or permission to send during unavailable recovery.

### Q0029-005 — Navigation, collapse and temporary invisibility

Question, translated into English:

> Navigation, widget collapse and temporary invisibility preserve the conversation,
> draft and running stream. Returning shows the same current/completed answer
> without a new request. Agree?

Reason/alternatives: the existing shared provider survives route/component visibility
changes; render cleanup must not accidentally abort/restart the API stream.
Full-page reload is a different lifecycle to refine next. No server cancellation
or fragment-resumption contract is implied by preserving an active page session.

User answer, translated into English: "5: Yes."

Accepted decision D0029-005:

- Route navigation, collapse and temporary invisibility keep shared conversation,
  draft and in-flight stream state. Neither render cleanup nor returning restarts
  or resends the request.
- Full reload remains a separate lifecycle; accepted server disconnect completion
  does not imply client automatic reconnect or fragment resumption.

### Q0029-006 — Interrupted delivery before completed

Question, translated into English:

> If delivery breaks after some fragments but before completed, keep the visible
> partial text marked as not completed and offer manual history recovery.
> Reconcile by turnId without duplicate messages or automatic resend. Agree?

Reason/alternatives: received checked fragments can be retained locally without
pretending the client knows the whole answer. The backend may already have saved
the result. Clearing partial text is another presentation choice; neither choice
permits inventing completion or treating a failed history read as empty history.
Partial display is ephemeral client state, not a new saved transcript/pair.

User answer, translated into English:

> 6: Yes, but note in the ADR that the final decision is out of scope; this is
> an intermediate decision.

Accepted decision D0029-006 — Provisional interrupted-answer presentation:

- For this phase's baseline, keep received partial text after interruption before
  completed, visibly mark it incomplete and offer manual authoritative history
  recovery. Reconcile by turnId without duplicate messages or automatic resend.
- Partial text is ephemeral active-page UI state, not a persisted completed
  message or proof of full client receipt. It must not expose guessed links.
- The user explicitly labels this presentation rule intermediate. Do not call it
  the definitive UX decision or silently remove it from current implementation.
- Defer the definitive keep/clear/redesign presentation decision to a later
  discussion under OOS-0029-002. No date, phase assignment or replacement behavior
  was specified in this answer; do not invent one.
- Functional validation of this provisional baseline still belongs to 0029.
  The exclusion concerns a final policy decision, not the accepted current behavior.

### Q0029-007 — completed followed by failed history/cookie synchronization

Question, translated into English:

> If valid completed arrives but the later history/cookie synchronization fails,
> keep the full answer and links as completed. Show a separate recovery problem,
> block further sends until recovery and do not offer to resend that completed
> message. Agree?

Reason/alternatives: completed is confirmed saved content; a subsequent read error
cannot undo it. Preserve the accepted 0028 current-turn-template/provider-outage
case and manual recovery. Do not retry synchronization automatically in a loop or
reopen the deferred detailed cookie-policy discussion.

User answer, translated into English: "7–10: Yes."

Response-block note: the actual keyboard input was `7 -10 lf`. The assistant
understood `lf` as Russian-layout "yes" and explicitly stated that interpretation
in chat before recording the four answers. The following translated answers refer
to the same response, not four separately fabricated messages.

Accepted decision D0029-007:

- Valid completed keeps its full answer/references confirmed even if later
  history/cookie synchronization fails. Show a separate recovery error, not an
  interrupted-answer marker or a failed-send retry on that completed turn.
- Block further sends until successful manual verified reconciliation; retain
  visible content/draft and never automatically resend or switch to mocks.
- This preserves the 0028/0026 current-turn-template/provider-outage handoff and
  does not reopen the deferred cookie-rule redesign.

### Q0029-008 — Strict protocol identity/sequence/content validation

Question, translated into English:

> Invalid event structure, a foreign conversationId/turnId, missing/repeated
> sequence numbers or a mismatch between fragments and completed are a damaged
> stream. Stop accepting it and offer manual history recovery, without inventing
> missing content or marking it completed. Agree?

Reason/alternatives: UI must distinguish checked server content from unreadable/
inconsistent delivery. Validate required event types/fields/reference shape and
literal full text independently. Retained replay legitimately has no deltas;
that accepted case is not a missing-fragment error. Ignore technical keep-alive
comments, not malformed named events. Partial handling retains the provisional
Q0029-006 rule rather than settling its deferred definitive UX.

User answer, translated into English: "7–10: Yes." See the response-block note in Q0029-007.

Accepted decision D0029-008:

- Reject invalid named-event shape/type/identity/sequence or inconsistent final
  text instead of repairing/inventing missing delivery. Offer manual authoritative
  recovery; do not declare malformed completed a success.
- Technical heartbeat comments are not named answer events; legitimate no-delta
  retained replay remains supported. Only validated completed supplies links.
- Preserve the provisional partial-display rule without settling its deferred
  definitive UX. Parser/type details are prepared after accepted discussion.

### Q0029-009 — Full reload and a backend turn that finishes later

Question, translated into English:

> On a full reload, restore only saved history; clear draft/partial/pending/error
> UI state and start collapsed. If the backend's earlier turn completes later,
> recover it with manual history refresh, without automatic polling or resending
> the old request. Agree?

Reason/alternatives: inherited sessionStorage retains conversation binding, not
the old request payload. A successful early read may not yet contain an admitted
turn's eventual save; absence is not proof of rollback or authorization to rebuild
and resend its payload. New intentional sends retain new turn IDs/server busy
behavior. Manual history refresh must be available without fabricated pending
state. Automatic polling would be an additional policy, not an SSE requirement.

User answer, translated into English: "7–10: Yes." See the response-block note in Q0029-007.

Accepted decision D0029-009:

- Full reload restores authoritative saved history, starts collapsed and does
  not restore draft, partial answer, pending/error state or the prior request payload.
- Earlier admitted backend work may finish later; provide manual history refresh,
  not automatic polling/reconnection/resend or reconstructed old requests.
- A currently absent pair is not proof of rollback. New intentional messages
  still have new turn IDs and retain server busy/ownership rules.

### Q0029-010 — Healthy rate/busy/capacity rejection versus outage

Question, translated into English:

> For recognized rate/busy/model-capacity/stream-capacity rejections, preserve
> the original request and respect Retry-After for manual retry. Do not treat
> those healthy admission rejections as a general backend outage or resend
> automatically. Agree?

Reason/alternatives: classify stable codes rather than every 503 as a storage/
provider failure. Actual unavailable/unknown-control outcomes retain verified
manual recovery; incomplete/malformed retry guidance cannot become a guessed
cooldown. Only the unchanged original request may be retried, without another
local user entry. Clarify finite known local errors separately in later preparation.

User answer, translated into English: "7–10: Yes." See the response-block note in Q0029-007.

Accepted decision D0029-010:

- Recognized rate/busy/model-capacity/stream-capacity rejections retain captured
  original payload/turnId and use valid Retry-After for explicit manual retry.
- These healthy admission rejections do not imply a global backend/storage outage.
  Actual unavailable/unknown state still requires readiness-verified recovery.
- No automatic resend, invented cooldown, new local user bubble, route fallback
  or mock transition. Local finite-error classification follows existing contracts.

### Q0029-011 — Explicit mock mode uses the same stream interface

Question, translated into English:

> Keep the explicitly selected development mock mode, using the same event/
> history interface and controlled success/interruption/error/replay scenarios.
> Components do not import fixtures directly. Agree?

Reason/alternatives: exercise UI state without the backend through the existing
API/data-source boundary. This mode is not selected by an API failure, and no
test/demo artifact is substituted into real mode. Mock checks do not establish
real backend readiness, browser transport or model quality.

User answer, translated into English:

> 11: The development UI must still be able to work entirely on mock data.

Accepted decision D0029-011 — Preserve a complete backend-free development UI:

- Keep the explicitly selected development mock mode usable for the whole UI,
  not merely a mocked chat answer. Existing catalog/details/navigation and chat/
  history flows remain usable with mock data without a running backend.
- The new chat streaming lifecycle must have a matching mock adapter/interface
  for success, history, replay and controlled failure/interruption checks.
- Do not require a real SSE route, session-cookie service, model or post-completion
  network history call to use this explicitly selected mock mode.
- Keep shared API/domain DTOs owned by the API boundary and fixtures/adapters in
  mocks; components never directly import mock datasets. Preserve existing other
  mock adapters rather than replacing unrelated UI features.
- This explicit development mode does not relax D0029-001: real API mode never
  transitions into mocks, including during failures/recovery.
- Mock verification does not establish real transport/provider readiness. If an
  unrelated mock defect requires unlisted changes, report concrete additional
  scope rather than silently expanding this feature's future writable files.

### Q0029-012 — Separate stop-display button in this phase?

Question, translated into English:

> I propose no separate stop button in this phase. Existing widget collapse
> remains available and does not stop the stream, and server cancellation is
> already excluded. Agree, or do you need a separate stop-display control?

Reason/alternatives: a stop-display operation is distinct from closing the widget
and from cancelling server work. Its existence is not implied by streaming or
the server-cancellation exclusion. Do not fabricate such a UI control or treat
it as an accepted exclusion until the user answers.

User answer, translated into English: "12: Yes."

Accepted decision D0029-012:

- Do not add a separate stop-display/stop-generation button in this phase.
- Existing widget collapse remains available and preserves the in-flight stream;
  it is not a cancellation/resumption control.
- Explicit backend cancellation remains excluded under 0028. Internal cleanup
  for transport error, page exit or invalid stream is not a new user stop action.

Accepted exclusion OOS-0029-003 is recorded below and in the central register.

### Q0029-013 — Scrolling while the user reads earlier messages

Question, translated into English:

> Scroll automatically for arriving text only while the user is at the bottom
> of the conversation. If they scroll up to read earlier messages, do not force
> them back down on every fragment. Agree?

Reason/alternatives: current code scrolls on every message-state change, which
would repeatedly move a reader during incremental rendering. Preserve scroll
position and the existing route/collapse lifecycle; exact near-bottom tolerance
and rendering batching are technical preparation, not new UX widgets.

User answer, translated into English: "13: Yes."

Accepted decision D0029-013:

- Follow incoming text automatically only while the user is at the conversation
  bottom; do not repeatedly move someone reading older messages.
- Preserve route/collapse/draft/focus behavior. Exact near-bottom tolerance and
  batching are technical preparation, not a typing animation or new UX control.

### Q0029-014 — Screen-reader announcements

Question, translated into English:

> For a screen reader, announce waiting and the ready answer once, rather than
> announcing every fragment. Keep errors/statuses and final stone links accessible
> by keyboard. Agree?

Reason/alternatives: an aria-live region tied to every fragment would repeatedly
announce unfinished content. Preserve focus/collapse behavior, normal accessible
status/error semantics and completed-answer identity through history sync/replay.
Detailed design remains technical preparation rather than reworking all UI access.

User answer, translated into English: "14: Yes."

Accepted decision D0029-014:

- Announce waiting and the confirmed ready answer once, not every text fragment.
- Errors/statuses remain accessible; final links and existing controls remain
  keyboard reachable. History reconciliation/replay does not announce the same
  completed turn repeatedly or steal composer focus.
- This is focused streaming accessibility work, not a redesign of unrelated UI.

### Q0029-015 — Demo labels in real versus explicit mock mode

Question, translated into English:

> Remove Demo assistant and Demo replies labels from real API mode; keep the
> development/mock mode clearly identified. Keep the existing 2000-character
> message limit. Agree?

Reason/alternatives: current labels describe the old demo irrespective of mode.
Real model-backed API operation should not be mislabeled as mock output. Mode
identification is not information-source attribution; no provenance is revealed.
Do not invent broader copy/design changes or new routes from this label update.

User answer, translated into English: "15: Yes."

Accepted decision D0029-015:

- Remove old Demo assistant/Demo replies labels from real API operation and keep
  the explicit mock/development mode visibly identified.
- Retain the 2000-character input limit and existing validation/context capture.
- Mode labels do not reveal evidence/provenance or imply API-to-mock fallback.

### Q0029-016 — Plain text and delivery without typing animation

Question, translated into English:

> Keep plain text without HTML/Markdown rendering, and show fragments as they
> arrive without artificial typing animation; completed's full answer appears
> immediately. Agree?

Reason/alternatives: preserve the existing plain-text response and literal
fragment/complete content rules. Rich markup or artificial replay pacing is
additional presentation scope, not required by streaming. This does not affect
the existing ordered catalog links. No rendering dependency is presumed.

User answer, translated into English: "16: Yes."

Accepted decision D0029-016:

- Render literal plain text, no HTML/Markdown response rendering, preserving
  whitespace/Unicode and existing ordered catalog links as separate UI elements.
- Apply incoming fragments without artificial typing delays; valid completed
  supplies the full answer immediately, including no-delta retained replay.
- No rich-rendering/animation dependency or new replay pacing is selected.

Accepted exclusion OOS-0029-004 is recorded below and in the central register.

### Q0029-017 — Focused verification in both modes

Question, translated into English:

> Prepare readiness first, then focused adapter/parser/provider/component checks,
> lint/build and real-browser checks in API and explicit mock modes. Include
> fragments/completion, interruption/recovery, replay, reload/navigation, cookie-
> sync failure and a fully mock UI without backend; no new broad model evaluation
> or full test suite unless separately required for an authorized commit. Agree?

Reason/alternatives: parser tests alone do not establish actual browser streaming,
cookie/Origin or route-lifecycle behavior. Delivered 0028 and actual services are
prerequisites for API smoke; mocks can validate UI without certifying backend
behavior. Use exact future file selectors and self-owned data; respect frontend
pre-commit checks if a later commit is explicitly requested. Documentation does
not authorize checks or code now. After this block, finalize docs and stop.

User answer, translated into English: "17: Yes."

Accepted decision D0029-017:

- First establish whole-requested-batch readiness, then use focused adapter/
  parser/provider/component checks, lint/build and actual browser verification.
- Cover API and explicitly selected complete mock UI, interruption/recovery,
  replay/navigation/reload, final content and failed post-completion history sync.
- API browser evidence requires delivered 0028 and actual healthy dependencies;
  mock tests never certify the real transport or model-quality gate.
- Do not run a new broad model evaluation or full suite in ordinary tickets.
  A later authorized frontend commit still requires its explicit local pre-commit
  lint/full-test/build checks; this discussion authorizes no such commit or checks.

## Technical Preparation Choices — Not Additional User Answers

- Native fetch POST with credentials and incremental UTF-8/SSE parsing implements
  the 0028 route without EventSource reconnect semantics or a new library.
  API DTOs/enums remain in api/dto and enums; mocks own no domain/API types.
- Keep one shared ChatSessionProvider and a small feature-owned state reducer
  for active-turn transitions. Immutable captured request/retry data stays separate
  from the editable draft. Reconcile history by role/turn identity, not array index.
- Preserve received incomplete text only in active-page state under D0029-006;
  the final UX choice remains deferred. No pending payload/partial transcript is
  restored from local storage after full reload.
- Technical reader bounds: initial VITE_CHAT_STREAM_TIMEOUT_MS=135000, a positive
  finite whole-request/read deadline not refreshed by heartbeat; pending SSE JSON
  event bytes and active answer text are each bounded initially to 1 MiB. Confirm
  compatibility with valid maximum backend responses in focused tests/readiness.
  These are defensive client bounds, not server deadline extensions, measurements
  or user-approved new backend limits. Abort is transport cleanup, not server cancel.
- The exact near-bottom tolerance is initially 80 CSS pixels. Browser painting/
  React batching may coalesce updates without adding typing delays. Announcements
  are keyed to once-per-turn completion independently of visible fragment updates.
- Existing mock catalog/details/create/reservation flows remain owned by their
  existing adapters; verify preservation without speculative rewrites. Only chat
  gains the matching event/history adapter and controlled fixtures here.
- Prepare six unchecked tickets: readiness, API parser/types, complete mock-mode
  chat lifecycle, provider state/recovery, component presentation/accessibility and
  focused final browser/evidence review. Exact paths/selectors are in tasks.md.

Primary technical references checked during preparation:
[WHATWG SSE framing](https://html.spec.whatwg.org/multipage/server-sent-events.html),
[Fetch](https://fetch.spec.whatwg.org/) and
[WAI-ARIA status](https://www.w3.org/TR/wai-aria-1.2/#status).
They guide parser/transport/status choices, not proof of installed runtime behavior.

## Discussion Resolution

No unanswered question remains for this phase's baseline. All 17 actual answers
and explicit amendments are recorded, including the keyboard-layout interpretation
of the combined 7–10 response. Specification/acceptance/plan/tasks are prepared.
Definitive interrupted-answer UX and the full cookie-rule review remain their
separately accepted deferred decisions; neither is silently marked completed.

Stop after documentation verification. Do not implement, start services/models,
run application/browser tests, create new commits or begin analytics/another
feature merely because these prepared tickets exist.

## Scope and Completion Boundary

Accepted entries are kept identically in the
[central register](../out-of-scope-decisions.md) and linked to this feature.

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0029-001 | API-mode transition to mock data or automatic JSON fallback | Real API mode never switches to mocks for sends, retries, history or recovery, including failures or missing/malformed data; explicitly selected development mock mode is separate. Streaming failure does not automatically resend through JSON. |
| OOS-0029-002 | Definitive UX decision for interrupted partial-answer presentation | Use the accepted provisional behavior in this phase: retain received partial text as incomplete and offer manual authoritative history recovery without duplicates or automatic resend. Defer the final keep/clear/redesign decision to a later discussion; no date or phase assignment is assumed. Provisional behavior still requires implementation and verification. |
| OOS-0029-003 | Separate stop-display or stop-generation UI control | Do not add a separate stop button in this phase. Existing collapse keeps the stream/conversation alive; backend cancellation remains excluded by 0028. Technical cleanup on page exit, transport failure or invalid protocol is not a user cancellation feature. |
| OOS-0029-004 | Rich response markup and artificial typing animation | Render response text literally without HTML/Markdown formatting or artificial typing/replay delays. Show arriving fragments and valid completed content directly; existing ordered stone links remain separate UI elements. No rich-rendering or animation dependency is selected. |

Inherited 0028 exclusions and deferred analytics/cookie-policy review retain
their accepted meaning; do not use the provisional UX note to reopen that review.
The full discussion is recorded and feature documents are prepared. No
application/UI code, tests, runtime operation, new commit or push is authorized.
