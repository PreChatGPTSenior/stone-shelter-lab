# ADR 0028: Chat Streaming API

Date: 2026-10-13

Status: Discussion complete; all 23 questions answered. Specification, acceptance,
plan and tasks are prepared. The narrow cookie rule is accepted for phase 1;
its full detailed policy review is deferred until after phase-1 implementation.
No implementation, runtime readiness, prototype or integration verification run.

Feature: [0028: Chat Streaming API](../../specs/0028-chat-streaming-api/spec.md).
Related documents: [acceptance](../../specs/0028-chat-streaming-api/acceptance.md),
[plan](../../specs/0028-chat-streaming-api/plan.md),
[tasks](../../specs/0028-chat-streaming-api/tasks.md).

## Context and Accepted Discussion Procedure

### D0028-001 — Continue detailed discussion and documentation for 0028/0029

User instruction on 2026-10-13, translated into English:

> Two features remain, 28 and 29. Discuss them the same way as the previous
> features. Write and record everything: the entire detailed course of the
> discussion in the ADR, then the specification, tasks and implementation plan
> after the discussion.

Accepted procedure:

- Discuss backend feature 0028 first and widget feature 0029 afterward; questions
  are asked directly in chat and the user answers there.
- Record actual questions, rationale, alternatives, translated answers and
  accepted decisions in the corresponding English ADR. Preserve amendments and
  distinguish proposals, inherited requirements and technical preparation choices.
- After each feature's questions are resolved, prepare its specification,
  acceptance.md, implementation plan and task list. Acceptance criteria remain
  exclusively in acceptance.md; the first future ticket is batch readiness.
- Continue the previously agreed documentation-only boundary. No application
  implementation, application test, model call, service preparation or fixture
  provisioning is authorized by this discussion. The preceding request to create
  commits was fulfilled; do not infer authorization for new discussion commits.

### D0028-002 — Preserve the confirmed feature/phase identities

Inherited accepted boundary, not a new question:

- Backend streaming API is 0028; widget integration is 0029.
- Entire reliable analytics feature 0030 is deferred to phase 2; it is not a
  prerequisite or an implicit streaming task. See
  [phase-2 ADR-0030](phase-2/0030-reliable-search-analytics.md).
- Feature 0027 qualification and its approved documentation remain intact.

## Inherited Requirements and Concrete Design Tension

- [0016](../../specs/0016-llm-search-conversation-history-and-context/spec.md)
  defines authorized anonymous session/conversation binding, original page context,
  required turnId, atomic completed-pair persistence, retained-result replay,
  manual retry/reconciliation, rate limits and actual-work lease/permit lifetimes.
  Navigation/collapse do not cancel an existing in-flight request. JSON send is
  POST /api/v1/chat/messages and retains text/ordered stones.
- [0026](../../specs/0026-semantic-search-rag-answers/spec.md) requires structured
  grounding/source-visibility validation of displayed prose, truthful card/type
  evidence, final existence/AVAILABLE checks, bounded corrections/templates and
  the original turn deadline. Unchecked generated text cannot be exposed.
- Incremental transport delivery and display during model generation are different
  behaviors. A validated complete answer can be sent in chunks after generation;
  raw generation deltas would require explicitly revisiting the existing validation
  boundary. A later rejection cannot erase text already shown to the user.
- Search logic, source hiding, references and shared resource controls must be
  accounted for in both transports. Exact streaming lifecycle decisions are open.

Technical reference checked during preparation:
[WHATWG server-sent events](https://html.spec.whatwg.org/multipage/server-sent-events.html).
It defines text/event-stream framing and named events. SSE framing and the POST
route are accepted in Q0028-001; the browser client and delivery/reconnection
details remain to be settled. No installed-library/runtime support is qualified.

## Discussion Log

Questions are asked directly in chat on 2026-10-13 and translated below. Preserve
their proposed alternatives and the actual answers; a pending proposal is not
an accepted decision. Numbering continues across chat blocks.

### Q0028-001 — Transport and separate route

Question, translated into English:

> I propose a separate POST /api/v1/chat/messages/stream route using SSE
> (text/event-stream), with the existing message, context, conversationId and
> turnId request fields. Keep the ordinary JSON route. Agree?

Rationale/alternatives: preserve original payload and request protection while
adding one-way incremental response delivery. Another transport or a separate
create-then-subscribe lifecycle would need an explicit contract choice. SSE
framing does not itself select native EventSource or automatic reconnection.

User answer, translated into English: "1: Yes."

Accepted decision D0028-003:

- Add POST /api/v1/chat/messages/stream using text/event-stream framing.
- Reuse message/context/conversationId/turnId request fields and their existing
  validation, identity and ownership meaning; retain the ordinary JSON route.
- This does not itself approve a browser EventSource client, automatic retries,
  new input fields, a second search engine or an analytics dependency.

### Q0028-002 — When may answer text become visible?

Question, translated into English:

> To preserve the 0026 checks, I propose first generating/checking the entire
> answer and then sending the checked text in chunks. This means text starts
> arriving after generation/checking, not immediately as the model generates it.
> Is that behavior suitable, or do you need text during generation?

Rationale/alternatives: complete validation preserves current grounding, source
visibility, correction/template and stale-candidate rules. Showing model output
as it is produced changes those boundaries and needs a separate discussion of
which checks occur before each visible fragment, their budgets and failure behavior.
Do not infer permission to show unchecked drafts from the word streaming.

User answer, translated into English:

> 2: Yes, text after we know what to show. Note this separately: simply generating
> an answer without any already checked data makes no sense at all, because we
> are essentially answering questions based on what we find, not general questions.

Accepted decision D0028-004 — Evidence first, complete checked text before delivery:

- Determine the request's meaning, find/check relevant data and establish what
  may be shown before generating substantive wording. Generation expresses a
  grounded answer; it is not a parallel speculative answer started before search.
- Preserve 0025/0026 source precedence, selected identity/order, supported/unmatched
  wishes and grounding/source-visibility validation. Complete generation/checking
  and final eligible response preparation before visible answer-text chunks.
- Incremental delivery is of the checked final text after generation/checking;
  it is not user-visible raw token delivery while the model is still deciding.
- The user's emphasis applies to the factual purpose of the application, not
  permission to invent findings. Existing 0026 bounded general knowledge and
  legitimate insufficient-information responses remain governed by that feature;
  this answer does not silently prohibit its already accepted knowledge-only mode.
- Preserve the full translated explanation above, rather than recording only
  "yes". OOS-0028-001 records the visible-unverified-generation exclusion.

### Q0028-003 — Events, authoritative completion and history

Question, translated into English:

> I propose started, text-delta, completed and error events. completed carries
> the full final text and ordered stone references and is sent only after the
> completed user/assistant pair is saved atomically. Store no partial answer in
> history. Agree?

Rationale/alternatives: explicit success distinguishes a normal completed turn
from an interrupted stream. Final content can reconcile chunks/history. Exact
payloads, identity/sequence metadata, persistence relative to the first text chunk
and the handling of an unknown commit acknowledgement need later refinement;
approval of this question alone does not settle those details.

User answer, translated into English: "3: Yes."

Accepted decision D0028-005:

- Use started, text-delta, completed and error events.
- completed contains final full text and ordered stone references, and follows
  confirmed atomic persistence of the completed user/assistant pair.
- Do not store partial generated/displayed answers as completed history.
- Event payload/sequence fields and persistence relative to the first text delta
  remain open for the next block; this answer settles the completion boundary only.

### Q0028-004 — Network disconnection

Question, translated into English:

> On a network disconnection, I propose completing the admitted turn within its
> original deadline and storing the result, so the UI can recover it from history.
> Do not start a second model run automatically. Agree?

Rationale/alternatives: finishing avoids losing already admitted work and permits
history reconciliation when delivery fails. Cancelling uncommitted work is another
possible policy, but neither disconnect nor a cancellation request establishes
that actual provider work has exited or permits may be released. Explicit user
stop, slow readers and resource bounds need distinct follow-up decisions.

User answer, translated into English: "4: Yes."

Accepted decision D0028-006:

- Network disconnection does not cancel the admitted turn. Complete safely under
  the original deadline/ownership/accounting rules and persist its result.
- Recover a completed result through authoritative history; do not automatically
  run the model a second time because transport delivery was interrupted.
- A dependency/ownership/deadline failure can still prevent safe completion.
  Explicit user cancellation and bounded delivery resources remain separate topics.

### Q0028-005 — Replay and retry identity across transports

Question, translated into English:

> A repeat with the same turnId and unchanged message/context should replay the
> retained completed result without a new model call or history pair; if that
> turn is still active, return the existing busy outcome. Share that identity
> between JSON and streaming requests. Agree?

Rationale/alternatives: transport changes must not multiply completed turns or
model charges. Payload-conflict rejection and retained-result identity are inherited
requirements; cross-transport replay representation and UI reconciliation need
explicit streaming details. Partial-stream resumption is not approved implicitly.

User answer, translated into English: "5: Yes."

Accepted decision D0028-007:

- JSON/streaming share retained turn identity and completed-result replay.
- Unchanged authorized turnId/message/context replays the exact saved result
  without new model processing or another history pair; active work is busy.
- Preserve inherited changed-payload conflict and retention-bounded guarantee.
  Replay event representation and partial resumption remain to be specified.

### Q0028-006 — Error representation before/after stream opening

Question, translated into English:

> Before the stream opens, return the existing HTTP error with ProblemDetail;
> after it opens, send an error event with the error code/message and end the
> stream without completed. Agree?

Rationale/alternatives: once streaming response headers are sent, an in-band
terminal failure needs a defined event rather than an unrelated replacement JSON
response. A delivery failure or unknown persistence outcome does not prove that
history is empty; reconciliation and commit/delivery races remain to be refined.
Application services retain protocol-independent exceptions; handlers own mapping.

User answer, translated into English: "6: Yes."

Accepted decision D0028-008:

- Before stream opening, return the existing HTTP error/ProblemDetail mapping.
- After opening, represent a processing failure with error and close without
  completed; retain stable public code/message meaning and hidden internals.
- Unknown persistence outcomes and delivery failure after a confirmed commit
  need explicit reconciliation semantics; error is not proof of rollback.

### Q0028-007 — Persist before the first answer-text fragment?

Question, translated into English:

> I propose atomically saving the entire checked answer before the first
> text-delta; started can arrive earlier. Then every delivered answer fragment
> already belongs to a saved result. Agree?

Rationale/alternatives: commit before text avoids visible answer prose whose
complete pair later fails persistence. Persisting only before completed satisfies
Q0028-003 but would need extra pending-prose/rollback semantics. Checking/final
eligibility still precede commitment; delivery itself introduces no artificial
typing delay or new model invocation. Commit acknowledgement uncertainty must
not be treated as confirmed persistence.

User answer, translated into English: "7: Yes."

Accepted decision D0028-009:

- Confirm atomic persistence of the full checked pair/result before the first
  answer-text delta. started may precede processing/persistence after admission.
- Final eligibility and checked response construction precede persistence;
  failed/unknown persistence emits no answer text or completed success.
- Streaming sends the persisted text; it does not weaken the 0026 validation
  boundary or manufacture an earlier token-generation experience.

### Q0028-008 — Lost delivery after persistence

Question, translated into English:

> If the answer was saved but the connection broke before completed arrived,
> retain it in history and recover it by history read or same-turnId replay;
> do not regenerate or delete it because delivery failed. Agree?

Rationale/alternatives: persistence and client receipt are separate facts. The
retained response remains authoritative even if catalog state subsequently
changes; replay is not a new selection. An unknown acknowledgement requires
reconciliation, not fabricated success or a blind duplicate append. No exactly-
once client delivery claim follows from an atomic history commit.

User answer, translated into English: "8: Yes."

Accepted decision D0028-010:

- A confirmed saved result remains in history if delivery/completed receipt fails.
- Recover it through authorized history or identical retained-turn replay without
  re-generation, deletion, another pair or repeated model charging.
- Delivery failure does not roll back confirmed persistence; no exactly-once
  client receipt guarantee is implied. Unknown persistence remains reconciliation.

### Q0028-009 — Separate server cancellation in this phase?

Question, translated into English:

> I propose no separate server-cancellation operation in this phase. Stopping
> display/closing a connection does not cancel work; the admitted turn can finish
> and be recovered from history. Agree, or is actual generation cancellation needed?

Rationale/alternatives: this matches accepted disconnect completion and avoids
assuming the provider stopped when a client closes its stream. True explicit
cancellation needs ownership/commit-race/actual-exit/usage semantics and a distinct
contract. Any widget stop control is discussed in 0029; it is not created merely
by this backend proposal. No cancellation exclusion is accepted until answered.

User answer, translated into English:

> 9: No separate server cancellation is needed. Mark it as out of scope.

Accepted decision D0028-011:

- Exclude a separate server-cancellation operation/endpoint from this phase.
- Closing/stopping transport display is not cancellation of the admitted turn;
  preserve accepted disconnect completion and history recovery.
- No widget stop control is inferred; that UX belongs to feature 0029 discussion.
- This does not prohibit internal termination on deadline, dependency failure,
  lease loss or other inherited safety/control failure. Actual-work permit and
  usage rules remain in force; a requested stop is never proof of actual exit.

Explicit exclusion OOS-0028-002 is recorded below and in the central register.

### Q0028-010 — Processing versus delivery deadlines

Question, translated into English:

> Preserve the existing 120-second processing/saving deadline. For delivery of an
> already saved answer, I propose a separate configurable 10-second limit; a slow
> reader does not hold model capacity or change the saved result. Agree?

Rationale/alternatives: after a confirmed completed turn, transport cleanup is
distinct from model processing/commit. Bound delivery rather than creating an
unlimited-lived writer or extending the business deadline. Actual model work still
owns permits until actual exit; slow transport never proves provider exit. The
delivery cap is an initial setting, not a benchmark or latency guarantee. Resource
admission/queue limits still require technical preparation.

User answer, translated into English: "10: Yes."

Accepted decision D0028-012:

- Retain the original 120-second processing/checked-result persistence deadline
  and inherited per-call/control bounds; heartbeat/delivery do not extend it.
- Delivery of a confirmed saved result has a separate configurable initial
  10-second cap. It cannot allow a late business commit or alter a saved result.
- Slow transport does not retain model capacity after actual invocation exit;
  provider permits remain owned until actual work exits, regardless of transport.
- Technical configuration names and qualification of delivery resource bounds
  will be prepared later; no benchmark or latency guarantee is established.

### Q0028-011 — Representation of retained-result replay

Question, translated into English:

> For replay of an already saved turn, I propose started followed immediately
> by completed with the full saved answer, without repeating text-delta chunks.
> Agree?

Rationale/alternatives: replay serves authoritative content with no re-generation
or artificial playback. The client reconciles by turnId. Replaying all old chunks
or resuming from a fragment offset requires additional retained transport state,
which is not approved by accepting retained-response identity alone.

User answer, translated into English: "11: Yes."

Accepted decision D0028-013:

- Retained-result streaming replay emits started followed by completed with the
  exact saved text/ordered references, without replaying text-delta events.
- No model call, extra history pair or artificial text playback. The client
  reconciles by turn identity; the replay is not a new catalog eligibility search.
- Partial fragment resumption/automatic reconnect remain undecided topics rather
  than an implicit consequence of SSE framing.

### Q0028-012 — Waiting-stage heartbeat

Question, translated into English:

> While preparing the answer, send only started and a technical keep-alive every
> 15 seconds, configurable; no intermediate model text, sources or search details.
> Agree?

Rationale/alternatives: keep-alive comments maintain transport activity without
inventing answer content or exposing internal evidence. Detailed stage/progress
events are another product choice, not implied by SSE or the selected event names.
Heartbeat must stop with disconnection/terminal closure and must not refresh the
turn deadline or history/session retention. Exact timer/control scope is technical
preparation, not a model-speed promise.

User answer, translated into English: "12: Yes."

Accepted decision D0028-014:

- While preparing a result, emit started and configurable keep-alive comments,
  initially every 15 seconds, without intermediate model text/search/source detail.
- Heartbeats are transport activity, not answer/progress messages or persisted
  history, and do not refresh business deadlines or retention.
- Stop heartbeat activity on disconnect/terminal closure; admitted processing can
  continue independently under the accepted completion policy.

### Q0028-013 — Event identity, order and final references

Question, translated into English:

> I propose conversationId, turnId and an increasing sequence number on every
> named event; text-delta also has its text fragment, completed has full text and
> ordered stones, and error has the existing public error code/message/retry hint.
> Send stone references only in completed. Agree?

Rationale/alternatives: transport identity/order supports deterministic UI
reconciliation without exposing evidence metadata or allowing fragments to choose
stones. Sequence is local to the HTTP stream, starts at 1 and excludes heartbeat
comments; replay starts a new stream sequence with the same turn identity.
Exact shared DTO composition/error mapping is technical preparation. No new
source/score/checker metadata is included in the public payload proposal.

User answer, translated into English: "13: Yes."

Accepted decision D0028-015:

- Named events identify conversationId/turnId and an increasing stream-local
  sequence; heartbeat comments are not answer events.
- text-delta contains a text fragment; completed contains the full saved text
  and ordered stones; error preserves public code/message/retry guidance.
- Only completed supplies stone references. Do not include sources, scores,
  checker/evidence metadata or speculative IDs in intermediate events.
- Concrete DTO composition and HTTP-versus-event error representation are
  technical preparation within these accepted payload requirements.

### Q0028-014 — Admission and protection across both routes

Question, translated into English:

> Before started, perform the existing validation, session/Origin checks, rate
> limits, readiness and turn admission. JSON and streaming share counters and
> the same active-turn exclusion; switching routes cannot bypass protection. Agree?

Rationale/alternatives: separate transport is not separate ownership/quota/model
capacity. Preserve the inherited gate ordering and completed-replay path rather
than charging replay as a new model turn or acquiring a duplicate conversation
permit. This proposal applies existing readiness/template exceptions faithfully;
it does not globally reopen history/replay after a dependency failure.

User answer, translated into English: "14: Yes."

Accepted decision D0028-016:

- Perform inherited validation, identity/Origin, readiness/rate and applicable
  turn admission before started. Preserve existing gate ordering/replay semantics.
- JSON/streaming share ownership, turn identity, quota counters, conversation
  exclusion and actual model capacity/accounting; route switching bypasses none.
- Readiness failures retain the accepted fail-closed policy and the narrow 0026
  current-turn template exception, without a public bypass or global reopening.

### Q0028-015 — Unknown persistence acknowledgement

Question, translated into English:

> If persistence confirmation is lost, send no text-delta/completed; report an
> error if transport permits. After recovery, check authoritative history by
> turnId first, rather than assuming rollback and running the model again. Agree?

Rationale/alternatives: an atomic write may have succeeded without its caller
receiving acknowledgement. Preserve inherited fail-closed recovery, reconciliation
and retained-turn deduplication; neither a lost connection nor an error proves
absence. There is no fabricated success or automatic second append. Full reload/
retention expiry retain 0016's limitations and do not reconstruct a lost payload.

User answer, translated into English: "15: Yes."

Accepted decision D0028-017:

- An unknown persistence acknowledgement emits no text-delta/completed.
  Deliver the mapped error only if the transport can still deliver it.
- Preserve uncertainty and fail-closed recovery. Check authoritative retained
  history/turn identity after recovery before deciding whether a manual retry
  is needed; never blindly regenerate, duplicate append or claim rollback.
- History expiry/data loss and full-reload limitations remain as in 0016.

### Q0028-016 — Fragment resumption and automatic reconnect

Question, translated into English:

> I propose no automatic reconnect or resumption from the last fragment in this
> phase. Recover from history or an explicit same-turn retry that returns the
> complete saved result. Agree?

Rationale/alternatives: retained business response replay does not require stored
fragment logs, offset subscriptions or automatic repeated sends. SSE framing alone
does not establish a resume contract. If agreed, record this specific exclusion
in both ADR/register; no exclusion is accepted while the question is pending.
Opening a new intentional turn still uses a new turnId under existing rules.

User answer, translated into English: "16: Yes."

Accepted decision D0028-018:

- No automatic reconnect or continuation from a last text fragment in this phase.
- Recovery is authoritative history or explicit identical-turn replay of the
  whole saved response, not a retained fragment subscription/log.
- SSE framing does not authorize an automatic resend policy. A new intentional
  send still gets a new turnId; accepted manual retry preserves the old payload.

Exclusion OOS-0028-003 is recorded below and in the central register.

### Q0028-017 — Independent limit on open streams

Question, translated into English:

> I propose a separate configurable limit of four open streaming responses per
> backend initially. If exhausted, reject with 503 before opening the stream,
> without a queue or new model work. Agree?

Rationale/alternatives: waiting/delivery connections consume transport resources
independently of actual LLM invocation concurrency. Replay also occupies an open
stream slot; ordinary history/JSON operations do not consume that slot, while
their inherited limits still apply. Close/release transport resources independently
of actual processing so disconnect does not release live provider/conversation
permits. The initial cap is not measured hardware capacity or a multi-backend
guarantee. Exact public error code/configuration names require preparation.

User answer, translated into English: "17: Yes."

Accepted decision D0028-019:

- Introduce a separate configurable initial cap of four open streaming responses
  per backend, distinct from shared LLM invocation capacity.
- When full, return HTTP 503 before opening a stream, without a waiting queue
  or new model work. Ordinary JSON/history retain their own existing controls.
- Retained-result replay also uses transport capacity. Stream-slot release must
  reflect actual resource cleanup rather than freeing live blocked writers.
- This is an initial single-backend setting, not measured hardware capacity or
  certified multi-backend exclusion. Public code/configuration names are prepared
  later and do not change this accepted meaning.

### Q0028-018 — Slow/non-reading client and bounded buffering

Question, translated into English:

> Do not accumulate unlimited fragments for a slow/non-reading client; bound
> buffering/writing and close the connection when delivery fails. The admitted
> turn continues under its original rules and any saved result remains recoverable.
> Agree?

Rationale/alternatives: only a bounded checked complete answer needs transmission;
no raw-token producer or unlimited replay log is needed. A blocked started/
heartbeat writer must not stall processing/lease renewal. Heartbeat must not queue
unboundedly behind blocked text writes. Deadline enforcement and transport-slot
cleanup need focused runtime qualification; interruption is not proof an underlying
write/provider call exited. Do not add artificial typing delays or roll back the
saved answer because the reader is slow.

User answer, translated into English: "18: Yes."

Accepted decision D0028-020:

- Bound transport buffering/writing; do not accumulate unlimited fragments or
  heartbeat tasks for a slow/non-reading client. Close failed delivery.
- A blocked writer must not stall admitted processing or lease renewal. Disconnect
  cleanup and continued turn execution have distinct ownership/resource lifetimes.
- Continue admitted processing within inherited deadlines/controls and retain
  saved results for recovery. Transport failure never rolls back a saved answer.
- Qualify actual timeout/cleanup behavior; requesting interruption is not proof
  that a blocked write or model invocation has exited. No artificial typing delay.

### Q0028-019 — Final eligibility and the saved-result boundary

Question, translated into English:

> Check stones' existence/AVAILABLE before saving the final response, following
> 0025/0026. Once saved, deliver/replay that exact response without recalculating
> the list if the catalog changes during delivery. Agree?

Rationale/alternatives: the persisted completed pair, fragments and completed
payload must have one authoritative text/reference identity. Rechecking while
delivering could make history, already visible prose and final links disagree or
create a second refresh budget. A later catalog change does not rewrite an earlier
retained turn or turn its replay into a new search; future intentional searches
still perform fresh eligibility checks.

User answer, translated into English: "19: Yes."

Accepted decision D0028-021:

- Final existence/AVAILABLE checks and permitted 0025/0026 refresh/subset behavior
  occur before saving the checked response.
- Once saved, that completed response is immutable for text delivery/history/
  retained replay. Do not select/reorder/remove stones again during delivery.
- A later catalog change does not rewrite the earlier turn; new intentional
  turns still make fresh eligibility decisions under the inherited rules.

### Q0028-020 — All successful reply modes use the same transport

Question, translated into English:

> Use the same stream format for exact/semantic selections, knowledge-only and
> mixed answers, clarification, no-results and permitted backend templates.
> Streaming adds no model call just to make wording nicer. Agree?

Rationale/alternatives: streaming wraps existing completed-turn behavior instead
of introducing a second interpretation/search/generation path. Clarification/
absence/template outcomes retain their original meaning and ordered references;
errors are not successful answers. No source attribution or internal fallback
metadata is exposed merely because the transport changes.

User answer, translated into English: "20: Yes."

Accepted decision D0028-022:

- All successful exact/semantic, knowledge/mixed, clarification, absence and
  legitimate backend-template replies use the same streaming event lifecycle.
- Reuse existing interpretation/retrieval/generation and response mapping. Add
  no model call solely for transport wording, animation or a separate SSE answer.
- Preserve ordinary outcome/error distinctions and hidden provenance; the
  transport exposes no internal fallback/evidence metadata.

### Q0028-021 — Exact text and delivery without artificial pacing

Question, translated into English:

> Concatenating all text-delta fragments must exactly reproduce the saved text;
> completed repeats that full text. Send fragments as transport permits, without
> artificial typing delays or changing the text. Agree?

Rationale/alternatives: fragments transport one checked response rather than
rewriting/reformatting its prose. Encoding/chunk boundaries must preserve Unicode
and literal whitespace; a truncated stream is not a completed answer. Backend
delivery pacing should not consume the separately accepted delivery deadline as
a typing animation. Visual treatment belongs to feature 0029.

User answer, translated into English: "21: Yes."

Accepted decision D0028-023:

- Concatenated text-delta text equals the saved response literally; completed
  repeats the full same text and ordered references.
- Preserve Unicode/whitespace and do not rewrite or truncate a successful answer.
- Deliver as transport permits, with no artificial typing delays. A truncated
  connection is not a completed answer; widget rendering is discussed in 0029.

### Q0028-022 — Focused qualification before and after integration

Question, translated into English:

> Prepare an environment-readiness ticket first, then a test-only SSE prototype
> before production implementation. Check real transport delivery/timeout/cleanup,
> disconnects, slow readers and the stream cap; after integration, verify the
> final protocol, shared admission, replay and uncertain persistence with focused
> tests, without a full suite or a new broad model-quality campaign. Agree?

Rationale/alternatives: actual transport behavior must support the promised bounds;
an emitter method returning or a timer firing does not prove a blocked writer
exited. Prototype evidence does not prove final controller/integration behavior.
Delivered 0025/0026 dependencies and actual services must be ready for the selected
batch; prototype setup does not authorize executing their implementation tickets.
Test-only controlled outcomes cannot claim real model-quality acceptance.
Exact file scopes/selectors and contract generation follow after this final block.

User answer, translated into English: "22: Yes."

Accepted decision D0028-024:

- Prepare readiness first for each entire explicitly requested ticket batch.
- Require a test-only real-transport prototype before production source changes
  for delivery, timeout/actual cleanup, disconnect/slow reader and stream capacity.
- After integration, perform focused final-contract/shared-admission/replay/
  persistence-uncertainty checks. Prototype evidence alone is not integration proof.
- No full test suite or new broad model-quality campaign is selected. Feature
  0027 owns the latter; all checks remain within future exact ticket selectors.
- This answer approves preparation of execution documentation, not running tests,
  models, services or implementation during the discussion.

### Q0028-023 — Cookie renewal after streaming headers are sent

Date: 2026-10-13. This additional question arose from a concrete dependency
conflict during technical preparation, after the proposed final product block.
It is not a request to repeat approval of the previous 22 answers.

Evidence:

- Current [StoneChatbotController](../../stone-shelter-api/src/main/java/lab/stoneshelter/controllers/StoneChatbotController.java)
  passes an onCommitted cookie-writing callback to the shared chatbot service.
  The JSON response can include the renewed cookie after a confirmed save.
- [0016](../../specs/0016-llm-search-conversation-history-and-context/spec.md)
  aligns browser cookie/server session/ownership with newly completed-turn activity,
  and disallows refreshing retention on history reads or retained replay.
- This feature sends started/keep-alive during processing. The HTTP headers are
  already sent before that later save, so a new Set-Cookie cannot be added there.
  [Jakarta Servlet HttpServletResponse](https://jakarta.ee/specifications/servlet/6.1/apidocs/jakarta.servlet/jakarta/servlet/http/HttpServletResponse.html)
  documents that setting/adding headers has no effect after response commitment.
- [Spring MVC async documentation](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-ann-async.html)
  also establishes that MVC writes can block. Actual writer cleanup will need
  the accepted prototype qualification; no timeout/cancellation proof is claimed.

Question, translated into English:

> For a newly admitted streaming turn, I propose renewing only the browser cookie
> before opening the stream; server session/history retention still extends only
> after a completed pair is saved. After completed, the widget reads history and
> that GET synchronizes the cookie with the remaining actual server-session lifetime,
> without extending server retention. Agree to this narrow change to 0016?

Rationale and precise proposed boundary:

- Early cookie renewal applies only to a newly admitted stream, after identity/
  request/turn and transport admission; not a denied or retained-replay attempt.
  If later processing fails, browser cookie renewal is not authorization to keep
  expired server state, recreate ownership or bypass fail-closed readiness.
- Authoritative server history/session/ownership expiry retains the existing
  completed-turn-only rule. GET/replay are not server activity and never extend
  that authoritative expiry; an expired binding retains existing handling.
- A successful healthy authorized history read may synchronize the browser's
  cookie expiry to the remaining existing session expiry, not a fresh full TTL.
  This is a concrete amendment to the old blanket GET-cookie-refresh prohibition,
  not permission for polling/replay to retain conversations indefinitely.
- Widget follow-up history retrieval occurs after confirmed completion when
  dependencies permit. It reconciles the turn by identity without repeating POST;
  history/replay failures still preserve content and require verified recovery.
- JSON's normal cookie-at-new-commit path stays as before. Cookie remains server-
  issued HttpOnly/host-only/SameSite/path/Secure under existing rules; do not expose
  the session cookie/token in SSE payloads or let the browser assign it.
- If accepted, update affected 0016 spec/acceptance/ADR alongside 0028 preparation,
  include precise future cookie/service/controller test scopes, and carry this
  handoff into 0029. No change is treated as accepted while this answer is pending.

Alternatives considered during preparation, not user-approved:

- Waiting to send all stream headers until persistence would keep JSON cookie
  timing but remove the accepted waiting-stage started/heartbeat behavior.
- Merely calling the existing onCommitted header callback after stream opening
  silently fails to renew the browser cookie.
- A new receipt/token-renewal route introduces another contract and is not
  presumed necessary or approved. Do not implement it as an unasked workaround.

User answer, translated into English:

> Yes, let's do that, but record separately in the ADR that a full and detailed
> discussion of this streaming rule will take place after implementation of the
> first phase. Put this note in both the ADR and out of scope.

Accepted decision D0028-025 — Narrow phase-1 cookie/header amendment:

- The user accepts the proposed limited cookie sequence: renew the browser cookie
  before opening a newly admitted stream; only a saved completed pair extends
  authoritative server retention; a healthy authorized history GET synchronizes
  browser expiry with the remaining server-session lifetime without extending it.
- No early renewal on denied or retained-replay attempts. A later processing
  failure cannot give a stale cookie authority over expired Redis/session/owner
  state. Preserve cookie secrecy/attributes and normal JSON commit-time renewal.
- Carry post-completion history synchronization into feature 0029. No new receipt/
  token-renewal/cancellation endpoint, session token in SSE or automatic POST retry.
- Amend 0016 specification/acceptance and ADR for this precise exception, but do
  not mark the amendment implemented based on earlier 0016 verification.

### D0028-026 — Separate detailed review after phase-1 implementation

This is the explicit additional instruction in the answer to Q0028-023, not an
invented follow-up question.

- After the first development phase is implemented, hold a full detailed
  discussion of this streaming cookie/session-lifetime rule and any further policy
  changes. Keep that future review visible under OOS-0028-004.
- For this phase, use only the narrow accepted baseline in D0028-025. This
  deferral does not move all streaming work to phase 2, undo the preceding
  22 answers, or remove mandatory functional qualification of the baseline.
- No additional cookie-policy alternatives, broad redesign or hypothetical future
  lifecycle expansion are settled or implemented now. A later review is not an
  automatically scheduled action or an assertion that the rule is already tested.

## Technical Preparation Choices — Not Additional User Answers

- Reuse the Spring MVC/Servlet stack and delivered shared turn pipeline. Application
  services remain protocol-independent; a dedicated MVC return-value handler owns
  SSE framing/I/O, and controllers own route/headers and return ready shared DTOs.
- Shared admission separates an admitted-turn handle from execution so both JSON
  and streaming use the same replay/lease/deadline/context/commit rules, without
  double quota charges or starting work before admitted started can be emitted.
- Named event payloads use their accepted fields; completed has five fields and
  therefore is a class. Use mappers for ready responses and a shared handler-owned
  error factory for HTTP/event consistency, not HTTP types in services/entities.
- Initial transport settings are max streams 4, saved-result delivery 10 seconds,
  heartbeat 15 seconds, write-operation cap 10 seconds, chunks 256 Unicode code
  points and at most one pending outbound frame per stream. The latter write/chunk/
  frame details are technical preparation within the approved bounded-I/O rule,
  not measurements or user answers; all remain subject to prototype qualification.
- Keep processing/lease/model permits separate from actual writer/stream resources.
  No unbounded executor/heartbeat queue or fake release of blocked writers. If the
  pinned Servlet stack cannot establish the agreed bounds, stop dependent tickets
  and propose a concrete correction; do not quietly replace the stack or weaken
  the rules.
- Prepare six unexecuted tickets: readiness, transport prototype, shared stream
  types/settings/admission, common turn/cookie integration, HTTP/SSE handler and
  final qualification/architecture review. Exact paths/selectors live in tasks.md.
- Generated OpenAPI remains authoritative; no handwritten OpenAPI, new dependency,
  schema, Kafka/analytics change or frontend implementation belongs to 0028.
- Reuse known approved tool/service/model pins for readiness; documentation is not
  evidence that 0025/0026 are implemented or currently reachable.

## Discussion Resolution and Handoff

No unanswered product question remains for the current 0028 baseline. The user
accepted the concrete cookie exception while explicitly deferring a fuller policy
review until after implementation of phase 1. Prepare the documents and 0016
amendment, then discuss widget 0029 using this contract. Runtime qualification,
delivery evidence and implementation remain NOT RUN, not silently satisfied.

## Confirmed Exclusions and Completion Boundary

Confirmed entries are maintained in the
[central register](../out-of-scope-decisions.md) and linked to this feature.

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0028-001 | User-visible streaming of unverified generation | Find/check the relevant data and prepare the complete validated answer before sending answer-text fragments. Do not expose speculative model prose or raw generation tokens before those checks; generation expresses what the application has established may be shown. Existing 0026 knowledge-only and insufficient-information rules remain applicable. |
| OOS-0028-002 | Separate server-side turn cancellation | Do not add a separate cancellation operation or endpoint in this development phase. Closing/stopping transport display does not cancel an admitted turn; preserve completion/history recovery under existing deadlines and controls. Internal termination on deadline, ownership loss or dependency failure and actual-work permit/accounting rules remain applicable. |
| OOS-0028-003 | Automatic stream reconnection and fragment-offset resumption | Do not automatically reconnect/resend or resume delivery from a last fragment in this phase. Recover through authoritative history or an explicit unchanged-turn retry that returns the whole saved result; do not retain a fragment replay log or create a resume subscription. |
| OOS-0028-004 | Full detailed review of the streaming cookie/session-lifetime rule | Defer the full detailed discussion and any broader redesign of this rule until after implementation of the first development phase. Use the narrowly accepted Q0028-023 baseline in this phase and qualify it normally; this deferral does not postpone features 0028/0029, change server completed-turn-only retention or authorize unagreed policy changes. |

Existing analytics exclusion OOS-0030-001 remains in its phase-2 ADR and register.
The entire discussion and answers are recorded; feature documents are prepared.
No implementation, readiness, application tests, new commit or push is claimed.
