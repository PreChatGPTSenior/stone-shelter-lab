# Feature 0029 Technical Plan

Status: Prepared, all execution NOT RUN. Read [spec.md](spec.md),
[acceptance.md](acceptance.md), [tasks.md](tasks.md) and
[ADR-0029](../../docs/adr/0029-chat-streaming-widget.md). Acceptance criteria and
focused scenario oracles belong exclusively in acceptance.md. These technical
choices are preparation within the accepted baseline, not extra user answers.

## Existing Responsibilities and Delivery Dependency

Read-only inspection found chatbotApi's JSON send/history boundary and ChatApiError,
ChatSessionProvider's binding/draft/request/recovery state and StoneChatbot's
complete-bubble rendering. Existing mockChatbotApi stores complete pairs/replay in
sessionStorage. stonesApi selects existing mock catalog/details/create/reservation
adapters only when the explicit application mode is mock. Preserve those boundaries
and modes; do not create another provider/store or refactor unrelated catalog APIs.

0028 documents the future backend transport, not a delivered endpoint. Actual API
execution requires its delivered route, generated contract and accepted history/
cookie amendment, plus actual reachable dependencies. Do not implement missing
backend tickets under this frontend feature or certify readiness from documents.

## Native Transport and Typed Boundary

Use native fetch POST with credentials: include, original captured request and
existing configured API base/origin. Keep the old JSON utility independent while
adding the stream entrypoint to chatbotApi. Choose explicit mock dispatch before
network work; API errors never import/select a mock or issue a JSON resend.

Place the byte/line/frame decoder and event validator in api/chatbotStream.ts.
Use incremental UTF-8 decoding, CR/LF framing, multiline data and comments under
[WHATWG SSE](https://html.spec.whatwg.org/multipage/server-sent-events.html).
Do not assume a network chunk is an event, call response.json on a successful
stream or buffer its entire body. Native [Fetch](https://fetch.spec.whatwg.org/)
permits controlled POST/read/abort behavior without EventSource reconnect policy.
No new reader/rendering dependency is needed.

API-owned typed variants in api/dto conform to generated 0028 fields; event-name
enum lives in enums. Validate expected identities, safe positive contiguous
sequence, required fields/terminal state and id/name references. Ignore technical
comments, never unreadable named data. Preserve exact text; no-delta replay is
valid, and a fragment-bearing completed must match collected text literally.
Cancel/release the reader at a valid terminal; EOF alone is not completed.

Keep existing ChatApiError and preserve its public import compatibility. Parse
pre-open ProblemDetail and post-open error.problem into the same status/code/
retry guidance, accepting valid positive Retry-After for recognized 429/503
admission outcomes. Missing/inconsistent required guidance or invalid protocol is
uncertain recovery, not an invented delay. Transport/protocol failures use a
client diagnostic code with no fabricated server HTTP result.

A finite whole-attempt timer initially uses VITE_CHAT_STREAM_TIMEOUT_MS=135000,
including fetch/read, and never resets on heartbeat. Apply finite request handling
to history synchronization/recovery too. Abort closes client transport, not server
work. Frame/active-text buffers are each bounded to the initial 1 MiB policy in
spec.md; validate compatibility with maximum delivered responses. No silent
truncation, hidden retries or expansion of server limits.

## Provider State and Reconciliation

Keep one feature-owned provider plus a small reducer for meaningful transitions:
RESTORING, IDLE, WAITING, RECEIVING, SYNCHRONIZING and RECOVERY_REQUIRED. Declare
frontend phase/delivery enums in their own enums files; they are client state,
not new backend DTO fields. Bind one captured request and attempt identity to its
callbacks, separate from editable next-message draft.

One deliberate send generates one turnId/user entry. Fragments create/update one
assistant bubble; valid completed makes it confirmed and supplies links. Repeated
render/effect/route changes cannot send another request. Ignore stale callbacks
from an already closed attempt; protocol errors within the active attempt are
failures, not silently ignored alternate results.

After completed, request history through the selected source. Reconcile by role/
turnId, replacing matching local messages rather than appending duplicates; do
not overwrite the draft edited during processing. Completion sync expects the
confirmed pair/content; a missing/inconsistent result keeps the completed answer
and enters recovery. Initial/manual history restoration still applies 0016's
healthy authoritative empty/expiry/ownership rules without assuming rollback or
reconstructing old payloads.

Before completed, interruption keeps ephemeral partial text marked incomplete
under the explicitly provisional decision. Manual restore/retry preserves original
payload/turn identity while relevant, and never automatically resends. Full reload
retains conversation binding only, clears draft/partial/pending/error state, starts
collapsed and loads saved history; late backend completion is retrieved manually.
Provide one existing-style history action when idle, labeled Refresh history when
healthy and Restore history when recovery is required. No background polling.

Separate healthy rate/busy/model/stream-capacity cooldown from actual unavailable/
unknown control. Keep server retry guidance; no global outage claim from every 503.
Local invalid-output/limit errors retain existing contracts rather than implying
storage failure. A confirmed answer with failed follow-up sync has no send-retry
control; only verified history recovery enables further sending.

## Complete Explicit Mock UI

Keep import.meta.env.MODE === 'mock' as the existing explicit mode selection; no
new runtime fallback switch. Reuse other mock adapters and their data/operations.
Chat adds a mock stream adapter with the same typed events and history interface,
using existing mock saved-pair/replay semantics and fixture IDs/names that resolve
in mock details. Prepare controlled success/interruption/error scenarios at the
adapter/test boundary, never in component fixtures or real-mode fallback paths.

Default mock completion commits its complete simulated pair before text fragments,
replay emits no deltas and history reads do not extend simulated retention. Mock
post-completion synchronization uses only mock history, not real cookies/API.
Preserve full catalog/details/create/reservation/navigation functionality while
backend access is absent; do not rewrite unrelated features or simulate an actual
semantic engine/model-quality guarantee. Report unlisted defects as concrete
additional scope instead of silently editing other adapters.

## Presentation and Accessibility

Update the existing component/CSS, not the page/router layout. Show normal waiting,
one incremental assistant bubble, provisional incomplete marker, separate recovery
problem and final links. Retain Escape/launcher focus and editable draft; block
send/quick prompts/retry during active processing or sync. No stop button.

Render React text with preserved whitespace and existing separate links, without
HTML/Markdown/typing animation. API labels use the existing brand/assistant role
without Demo assistant/Demo replies; explicit mock mode is identified. Keep 2000
characters. Selected mode metadata is read-only display information from the API
boundary, not a selector to recover from an outage.

Track whether the user was within 80 CSS pixels of the bottom before content
changes, and follow only then. Keep older reading position during fragments;
normal React/browser batching is not artificial pacing. Use a separate polite
status region keyed to waiting/completed turn identity, without per-fragment or
replay/sync repetition, while the transcript remains non-live. Preserve keyboard
and error/status semantics under [WAI-ARIA](https://www.w3.org/TR/wai-aria-1.2/#status).
DOM tests establish live-region behavior, not a claim of actual screen-reader use.

## Environment, Verification and Stop Boundary

T0029-001 covers every explicitly selected ID, with expected/observed values,
preparation versus read-only checks and READY/BLOCKED/corrections. Verify Node
24.21.0/npm 11.19.0 and installed approved UI tooling from ADR-0002 using focused
runtime/installed-tool metadata, not manifest/build-output/config scans. Supply
known authorized PATH/API-base/client-timeout values to the agent's own command
context; secrets/cookie values are never printed.

Prototype/controlled frontend checks do not need a real model/backend, but report
backend/frontend/service states with impact. Actual API/browser batches require
delivered 0028, the real UI origin/API route, generated contract and verified
backend-reachable services/control settings. Effective browser transport/deadline
settings must match approved setup. Mock readiness needs its own frontend/mode/
fixture resources and no backend dependency; do not infer a larger batch ready
from a successful mock check. Start only required existing services and verify
actual health/connectivity. Tests/business imports/models are not preflight checks.

Use exact task test files, lint/build and actual browser checks, with self-owned
isolated data and no user's catalog mutation or unrequested backend fault route.
Controlled fetch fixtures prove error cases independently of actual backend
success/replay/navigation evidence. Missing required backend/browser fixtures
block that part rather than substituting mocks as proof. Preserve real usage if
approved API smoke invokes existing models; no broad model campaign/benchmark.

Only an explicitly authorized later frontend commit triggers the local mandatory
lint/full-test/build pre-commit checks; ordinary tickets do not authorize a full
suite. Keep the needed verified UI development server running after actual future
verification under frontend instructions. No runtime operation is performed now.

The definitive interrupted-text UX and full detailed cookie-rule review remain
accepted deferred discussions, separate from baseline testing. No new dependency,
backend/schema/analytics change, commit or push. Finish current documentation,
then stop without automatically executing its tickets or starting another feature.
