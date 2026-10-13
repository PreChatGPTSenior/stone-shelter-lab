# ADR 0029: Chat Streaming Widget

Date: 2026-10-13

Status: All 17 original questions answered; implementation/verification history is
recorded below. D0029-018, accepted on 2026-10-14, clarifies developer-only mock
operation and removes mandatory mock creation/reservation browser submissions.
Related documents reflect the revised scope. The subsequent explicit completion/
commit request closes T0029-006 after fresh readiness and successful UI lint/all
235 tests/build. Local commits are authorized; no push is requested.

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

Accepted decision D0029-011 — Preserve a complete backend-free development UI
(developer-only scope clarified by D0029-018 on 2026-10-14):

- Keep the explicitly selected development mock mode usable for the whole UI,
  exclusively for developers' development/testing. Existing catalog/details/
  creation/reservation/navigation and chat/history flows remain usable with mock
  data without a running backend. The user-facing API mode requires the backend.
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
  The 2026-10-14 D0029-018 amendment removes mock creation/reservation browser
  submissions from mandatory verification while retaining existing automated
  coverage; acceptance.md owns the current observable evidence requirements.
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
| OOS-0029-005 | Mandatory browser submissions for mock stone creation and reservation in feature 0029 | Accepted on 2026-10-14 for [feature 0029](../../specs/0029-chat-streaming-widget/spec.md). Remove creating Widget 0029 browser fixture and submitting the Luna application from mandatory browser verification. Retain existing automated mock creation/reservation coverage and actual mock chat/history/navigation/reload browser evidence. Backend-free mock mode is exclusively for developers' development/testing; the user-facing API mode requires the backend. Existing creation/reservation functionality remains required. |

Inherited 0028 exclusions and deferred analytics/cookie-policy review retain
their accepted meaning; do not use the provisional UX note to reopen that review.
The full discussion is recorded and feature documents are prepared. No
application/UI code, tests, runtime operation, new commit or push is authorized.

## Execution Request and Blocked Readiness — 2026-10-13

The user requested execution of every task in features 0025–0029 without stopping
at individual task/feature boundaries, and one commit for each entire completed
feature rather than one per task. This is explicit implementation/test/feature-
commit authorization for that batch, superseding the earlier documentation-only
stop boundary. It does not authorize pushing, feature 0030, relaxing acceptance,
production fixes inside 0027 qualification, or replacing actual human review.
No new product question or exclusion was introduced by this execution request.

Fresh batch preflight is BLOCKED, as recorded in
[the feature task evidence](../../specs/0029-chat-streaming-widget/tasks.md#requested-batch-readiness--2026-10-13).
The unavailable browser prevents required actual T0029-006 verification. Existing
backend liveness is healthy but the current vector/index/usage-ledger schema and
embedding endpoint configuration are absent in that running deployment. Maven's
Java mismatch was resolved for checks by supplying the installed JDK 23.0.2
explicitly. Installed Ollama 0.40.3 remains covered by prior approval.

Preparation started the existing API-mode UI server and successfully built the
preceding implemented backend image with tests skipped. No application deployment,
schema migration, source import, inference or application test followed. No
implementation ticket started, no feature is accepted and no commit was created.
This is environment evidence, not an accepted out-of-scope decision. All selected
readiness prerequisites must be rechecked after correction before execution.

## Browser Connection and Readiness Recovery — 2026-10-13

The user reported completing browser setup. Fresh inventory and an actual local
page observation establish connected Chrome access; the earlier browser blocker
is resolved. The agent also started the current preceding backend with approved
command-scoped settings. Its existing Liquibase migrations applied, required
vector/index/usage tables now exist, profile mismatch checks return zero and the
actual history GET is healthy. No feature requirement or exclusion changed.

The entire requested 0025–0029 external environment batch is READY, with exact
components, covered IDs, preparation and command requirements recorded in
[the current task readiness evidence](../../specs/0029-chat-streaming-widget/tasks.md#fresh-batch-readiness-after-browser-connection--2026-10-13).
Mandatory model/transport qualification and within-batch producer deliveries are
still gates; this readiness result does not claim their future acceptance. The
previous blocked report remains historical evidence. User authorization remains
continuous execution and one commit per completed feature, with no push.


## Shared Producer Qualification Gate — 2026-10-13

Chrome connection and current external environment readiness were confirmed.
T0025-002 then failed actual prototype qualification: semantic-only shelf input
was classified CLARIFY instead of SEARCH; evidence output invented a source ID
not supplied by the backend, including after one correction. Detailed original
outputs, successful checks and a pending exact correction proposal are retained
in [ADR-0025](0025-semantic-stone-candidate-search.md#t0025-002--actual-prototype-qualification-failure).

Dependent implementation remains unexecuted under the mandatory qualification
gate. No feature is complete and no feature commit/push was created. This is
execution evidence, not a new product exclusion or approval of a correction.


## Authorized Necessary Type Changes — 2026-10-13

The user explicitly approved the pending T0025-002 shared-prompt correction and
broadened source-type scope for the selected batch, translated: "Yes, do it; if
a task requires changes within features 25 through 29, all types may be modified."
Necessary related production/test type changes for the accepted 0025–0029 behavior
are therefore authorized even if a prepared ticket's original list omitted them.
Record exact additional paths, reasons and focused checks before each such change;
this approval supersedes the original type-file isolation blocker, not acceptance
or unrelated scope. No dependency/version change, feature 0030, engineering-log
access, push, gold relaxation, or fabricated human review is authorized.

For the already reviewed T0025-002 correction, additional writable types are:
stone-shelter-api/src/main/java/lab/stoneshelter/entities/StoneModelInvocationEntity.java
and stone-shelter-api/src/main/java/lab/stoneshelter/repositories/OllamaChatRepository.java.
Make the exact-only example optional with its original default preserved. Update
the authorized semantic prototype/fixture to use its own format and supplied-ID
constraints. Focused verification adds the three existing controlled exact-search
qualification methods from the reviewed proposal; they perform no live inference.
Recheck changed interpretation/evidence/capacity tests, retaining the earlier
failed evidence and not repeating unchanged successful embedding qualification.

Continuous execution and one commit per entire completed feature remain requested.
The original task lists remain the implementation guide; broad type permission
is not permission to invent behavior or bypass prerequisite qualification.


## Basic Producer Qualification Recovered — 2026-10-13

The accepted necessary-type correction preserved default exact-search prompts,
removed their injection from new formats, made wish absence explicit through
nullable slots and classified each evidence source in its own authority scope.
Actual requalification now covers every small fixed interpretation/evidence
case, compatible vectors and complete fresh-input capacity. Three existing
controlled exact transport regressions passed. All earlier failures and usage
remain recorded in ADR-0025/tasks; they were not erased or replaced by lucky
repetition. No model/profile/limit/gold change occurred.

T0025-002 is complete as a basic prototype gate. T0025-003 onward remains the
requested production implementation sequence; final providers and feature
acceptance are unverified. No feature commit/push has been created yet.

## Independent T0029-002 Readiness and Maximum-Response Proof

READY for controlled client parsing/API tests within the authorized 0025–0029
batch: Node 24.21.0/npm 11.19.0, installed Vite 8.3.2/Vitest 5.0.3/TypeScript
6.0.3/ESLint 10.12.0. The API-mode UI is actually HTTP 200 at 127.0.0.1:5173;
served public module metadata reports mode=api. A sandbox-restricted localhost
probe failed and a duplicate-start attempt found the existing listener; authorized
network verification confirms it was not a stopped service. No second server or
persistent configuration change. Actual backend integration/browser remains gated
by delivered 0028 and the separately proposed source-profile correction.

Read-only approved qwen2.5:3b verbose tokenizer metadata reports gpt2, 151936
vocabulary entries, maximum decoded UTF-8 token expansion 128 bytes. Decode the
byte-level vocabulary with conservative per-invalid-byte replacement; no inference,
model download or tuning. Under verified context/check/generation values 8192/
512/1024 and five displayed stones, generated encoded text/frame bound is
1024*128+8192 = 139264 bytes. A conservative template bound includes all inherited
mandatory state (at most 8192 bytes by the existing packing guard), new interpretation
output and five repetitions, plus 8192 bytes for escaped names/labels/IDs/framing:
5*(8192+512*128)+8192 = 376832 bytes. Both fit the initial 1048576-byte frame/text
limits. This proof concerns the checked initial settings, not arbitrary future
raised output/context configurations. Repeat compatibility review if those change.

Additional necessary type path: src/api/ChatApiError.ts extracts the existing error
class and re-exports it through chatbotApi, avoiding a parser/API circular import
while preserving existing consumers. The user's necessary-type authorization
covers it. API-only native streaming is implemented first; explicit mock dispatch
is the next ticket and existing mock JSON UI stays usable until provider integration.
No live browser/model/catalog operation or missing backend implementation here.

## T0029-002 — Actual Controlled Client Outcome

PASS: chatbotStream.test.ts / chatbotApi.test.ts, 35 tests; lint and build pass.
Native credentials POST, exact original payload, no JSON/mock resend, split UTF-8/
CRLF/bare-CR/multiline JSON/comments, typed identity/sequence/text/reference checks,
replay, EOF/invalid UTF-8, byte bounds, known retry hints and terminal reader
cancel/release are covered. Fetch/history attempts remain finite even when the
controlled fetch ignores its abort signal. No real backend/model proof is claimed.

Additional necessary T0029-003 enum type: src/enums/ChatStreamMockScenario.ts keeps
private fixture scenario values in the required separate enum directory. Default
mock events use existing stored pairs/real mock IDs; controlled interruption and
error belong to the adapter fixture boundary, not components or API fallback.

## T0029-003/004 Controlled Lifecycle Work

Mock streams use the existing atomic history adapter before exposing fragments.
Replay has no deltas; interruption retains the saved pair for manual reconciliation.
API mode never chooses this adapter on failure. The extra named enum
ChatStreamMockScenario follows the frontend enum convention.

Provider actions require the additional ChatSessionActionType enum path, authorized
by the user’s necessary-type scope. One reducer owns provisional versus confirmed
messages, immutable retry payloads and authoritative history reconciliation.
Confirmed answers survive failed synchronization without a resend option. A restored
failed turn must match both its original user text/context and complete pair; a reused
ID alone cannot prove successful reconciliation. A new intentional request clears
the previous confirmation marker. Waiting and completed announcements are tracked
per turn; fragments never change the live announcement.

T0029-005 uses a shared test-only callback/history helper at
features/stone-chatbot/state/ChatSessionProvider/chatStreamTestSupport.ts to keep
component/navigation tests on the same confirmed-pair oracle. This necessary
additional path is within the user's authorized type scope. Presentation uses
explicit mock-only labels, provisional incomplete markers, confirmed-only links,
a separate history synchronization/recovery action and an 80-pixel near-bottom
scroll tolerance. No per-fragment announcement or typing animation is introduced.

Controlled T0029-003–T0029-005 verification: 50 tests passed across the API/mock, reducer,
provider, component and navigation selectors. Lint and TypeScript/production build
pass. Evidence includes incomplete literal fragments, final-link isolation,
unchanged retry payload/cooldown, saved-answer retention after sync failure,
manual-only recovery and older-message scroll preservation. This is controlled
DOM/keyboard evidence, not a performed screen-reader session or real backend proof.

A closed attempt now invalidates its callback identity, and completed ignores any
late fragment callback while history synchronization runs. Additional controlled
provider tests exercise StrictMode's single restoration/intentional POST and stale
callbacks after terminal completion. This closes a real lifecycle gap rather
than relying only on the native parser's terminal reader cleanup. Full UI checks
are rerun because the provider changed after its previous successful 232-test run.

Full UI pre-commit verification after the final callback changes: lint passes,
all 234 tests pass with one worker, and production build passes. The two catalog
timeouts in the earlier concurrent verification pass independently (18 application
page tests and six filter-label tests), without catalog edits, timeout changes
or relaxed assertions. Single-worker sequencing changes only verification
execution and avoids competing compilation/test work; no performance result is
claimed. Actual API/browser verification remains separate.

Browser execution preparation reuses the approved 0027 fixture and its compatible
real vector cache in the explicitly owned isolated database. There is no business
import, schema/model/threshold/resource change or new embedding preparation.
Real provider usage during actual API smoke remains charged in that target's
existing ledger; it never changes original 0027 scores. A separate uniquely named
localhost browser origin isolates browser cookie/tab identity. Temporary local
API/frontend processes use command-scoped settings and explicit API mode.
The explicit mock process points its API base at an intentionally unreachable
local fault target, proving whole-UI independence without a real-mode fallback.

## Actual API Browser Outcome and Mock Approval Boundary — 2026-10-14

The isolated API process uses the built full application and owned PostgreSQL
fixture; its health is UP and generated OpenAPI exposes the real stream route.
The initial health probe reached the port during startup before a response existed;
a fresh post-start probe succeeds. This was startup preparation, not a source fix.
The preceding batch API is intentionally stopped temporarily to keep the fixture
process isolated; the original main storage/configuration will be restored with
the updated image after verification. Both frontend processes respond HTTP 200.

On stone-qualification-0029.localhost:5175, a single intentional request,
“Show available small obsidian stones.”, produces a checked saved response with
Obsidian 01's canonical /stone-shelter/stones/150 link. Waiting blocks sending but
permits draft edits. Collapse/navigation to details preserve the stream and draft.
Manual Refresh history leaves one pair, then full reload starts collapsed, restores
that pair, clears the draft and leaves the live announcement empty. API labels
contain no Demo/Mock or source attribution. This functional browser request is not
an approved 0027 baseline repetition or a replacement quality score.

On stone-mock-0029.localhost:5174, API base is the deliberately unavailable
127.0.0.1:18029 test target. Mock chat completes and synchronizes locally with ordered
Mars/Luna links, explicit Mock labels, working details and preserved navigation draft.
The complete mock catalog initially contains 30 stones.

Automatic approval review rejects clicking Submit application, stating that broad
UI-testing authorization does not specifically authorize consequential reservation
submission, even in mock mode. Do not bypass this through another execution path.
Prepared only: Luna's local mock application with synthetic identity
Widget 0029 verification / widget-0029@example.invalid, and a separate mock create
form for Widget 0029 browser fixture, BASALT/SMALL, optional temporary-test biography.
Both have reviewable screenshots and remain unsubmitted.

The assistant asks for explicit permission for those two concrete mock submissions,
with options allowing both, only the application, or neither yet. Actual user answer
is PENDING; no accepted scope exclusion or successful browser submission is assumed.
Unaffected checks continue.

API reload during processing is exercised with a second original request,
“What size is this stone?”, captured on Obsidian 01's actual details page. Reload
displays only the previous acknowledged pair, collapsed/empty draft, without the
old pending payload or a new POST. Manual Refresh history later obtains exactly
one additional saved pair. The model chose a clarification asking type/size/date
rather than answering the size question; record this actual business output and
do not claim a new knowledge-quality success from this lifecycle test.

Real shared accounting after the first API request and history/reload operations:
31 reservations, 19160 charged tokens on actual UTC 2026-10-13, versus preserved
qualification total 17761. Browser local date is 2026-10-14; do not move accounting
clocks/rows to the local date. Subsequent usage is retained separately.

The owned API is deliberately stopped after both turns are acknowledged. Manual
Refresh history fails at the unavailable proxy, but both confirmed pairs and the
Obsidian link remain; sending is blocked, only Restore history is offered and no
Mock label/output appears. Restart the same owned API, verify health UP and click
Restore history: the two pairs recover without a new POST or duplicate message.
This is an actual browser dependency-outage/recovery observation, distinct from
the controlled immediate-completed-then-sync-failure tests.

The independent mock browser also verifies page size 12, A–Z ordering, page 2,
Small filter resetting to page 1 with ten matches, and collapsed reload followed
by locally restored chat with empty draft/no repeated live announcement.
The two submitted-form operations remain pending explicit permission after the
automatic-review rejection; automated mock adapter/component coverage remains
separate and is not claimed as successful browser submission.


## Final Source Review: Canonical UUID Identity

The native parser accepts UUID identity by value, including letter case, while
the provider compared event identifiers case-sensitively and returned an accepted
uppercase saved conversation identifier unchanged. Spring's UUID serialization
uses canonical lowercase; an otherwise valid restored identity could therefore
become a false foreign-event/history rejection. Necessary changes are confined to
existing ChatSessionProvider.tsx and ChatSessionProvider.test.tsx: canonicalize
valid saved conversation UUIDs and compare callback UUID values case-insensitively.
New sends still use browser-generated UUIDs, frozen payloads and independent turn
identities; genuinely foreign IDs remain rejected. This repairs the accepted
identity contract without changing product scope. Verify the focused provider
regression and repeat mandatory lint/full UI tests/build after inference ends.


The canonical UUID regression passes all 14 provider tests. After that final
source change, mandatory lint PASS, full 235 UI tests PASS (one worker) and build
PASS. No backend qualification inference overlaps these commands. Actual pending
mock forms remain unsubmitted; T0029-006 is still incomplete, with no scope waiver.


After the final provider source reload, development HMR clears the prepared form
fields/dialog. The assistant restores the same previously reviewed synthetic
values through visible UI fields, without submitting either action, and leaves
both tabs marked for handoff. Updated proof screenshots:
/private/tmp/stone-q029-mock-create-final.png and
/private/tmp/stone-q029-mock-reservation-final.png.
The submission question remains unanswered; automatic-review rejection is
explained separately in chat. No browser application/create mutation is claimed.
235 UI tests/lint/build remain PASS; no subsequent UI source change.


## User Question About Automatic Browser Approval

On 2026-10-14, the user asks what automatic check rejected the mock application
and what they need to do, explicitly expressing confusion. The assistant explains
that the prior rejection is a tool-action permission review before clicking Submit
application; it is not a Stone Shelter test/backend error. In this session eligible
requests use automatic review; the actual rejection says broad UI-test permission
does not specifically authorize consequential application submission even in
mock mode. Official OpenAI permission/computer-use guidance supports the general
permission distinction; specific reason comes from the actual tool result.

Reviewable proposed user authorization: allow creating Widget 0029 browser fixture
(BASALT/SMALL) and submitting the Luna application with Widget 0029 verification /
widget-0029@example.invalid only on local mock origin
http://stone-mock-0029.localhost:5174, using synthetic data. This is an example the
user may expressly approve; the present explanatory question is not permission
to submit. Do not bypass a rejection or assume a settings change is needed.
Both prepared-form actions remain unsubmitted pending actual authorization.
Sources: https://learn.chatgpt.com/docs/permission-modes and
https://developers.openai.com/api/docs/guides/tools-computer-use.

## D0029-018 — Developer-Only Mock Mode and Focused Browser Scope — 2026-10-14

The user clarified that only the developers' development/testing mode works
without a backend, then explicitly accepted removing creation of Widget 0029
browser fixture and submission of the Luna application from mandatory browser
verification and requested corresponding requirement/specification updates.

Accepted decision:

- Backend-free mock operation is exclusively for developers' development/testing.
  The user-facing API mode requires the backend and never falls back to mocks.
- Preserve existing mock creation/reservation behavior and its automated coverage.
  Remove their two form submissions from mandatory browser verification in 0029;
  actual mock browser verification remains focused on the changed chat/history/
  navigation/reload lifecycle. Acceptance criteria remain exclusively in
  [acceptance.md](../../specs/0029-chat-streaming-widget/acceptance.md).
- Record the accepted verification exclusion as OOS-0029-005 here and in the
  [central register](../out-of-scope-decisions.md), linked to the specification.
- Existing evidence satisfies the revised AC-0029-011/015 and B029-008 scope.
  The two submissions remain unperformed; the earlier permission blocker is
  historical and no longer blocks these criteria. This scope change does not
  authorize submission or retroactively claim successful browser mutations.

This request changes documentation only. No application code, HTTP/AsyncAPI
contract, dependency, runtime configuration or test changes are required. No
service startup, fresh readiness claim, application/browser execution, ticket
closure, commit or push is performed. Historical execution/approval records above
are preserved; their pending-submission status is superseded by this decision.

## Final T0029-006 Review and Local Commit Authorization — 2026-10-14

The user subsequently requested completion and committing all current changes.
Fresh readiness for T0029-001/T0029-006 closure and mandatory frontend pre-commit
checks is READY; exact expected/observed components are recorded in tasks.md.
Existing services remained running, with no startup, download or persistent setup.

Lint PASS, all 235 UI tests in 28 files PASS (one worker), build PASS and scoped
git diff --check PASS. Application source was not changed during closure. Existing
browser/native/controlled evidence satisfies the revised acceptance scope without
the two excluded mock form submissions; T0029-006 is complete.

All current changes may now be committed locally. Commit the 0029 widget/requirements
and the existing 0027 harness/evidence separately under their exact feature-directory
prefixes. The latest explicit all-changes commit request permits recording incomplete
0027 work without claiming successful qualification. No unfinished 0027 task is
executed/closed, and no push is requested/performed.
