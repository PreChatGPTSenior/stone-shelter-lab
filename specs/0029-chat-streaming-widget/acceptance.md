# Feature 0029 Acceptance Criteria

Status: Accepted current baseline; every execution criterion is NOT RUN.
Read [spec.md](spec.md), [plan.md](plan.md), [tasks.md](tasks.md) and
[ADR-0029](../../docs/adr/0029-chat-streaming-widget.md). Design/documentation
approval is not environment, test, browser or implementation evidence.

| ID | Required observable outcome and evidence |
| --- | --- |
| AC-0029-001 | A fresh report covers every requested full ticket ID, approved UI runtimes/settings, required delivered backend/browser prerequisites and expected/observed health; blocked prerequisites prevent the affected batch from executing. |
| AC-0029-002 | API sends/retries exclusively use the 0028 route/interface and never invoke mock data/history or automatically resend through JSON under failure, absence, malformed data or recovery. |
| AC-0029-003 | Incremental parsing preserves UTF-8, line/frame splits, comments and literal whitespace; valid identities/sequence/payloads/text/references are checked, damaged streams require recovery and no-delta retained replay is valid. |
| AC-0029-004 | Waiting reveals no answer/source/progress draft; fragments update one turn's assistant bubble and only valid completed supplies full final text and ordered links, without duplicate bubbles or artificial pacing. |
| AC-0029-005 | Send/quick prompts/send-retry stay disabled during waiting/delivery/history sync, draft edits remain available and cannot modify captured original message/context/turnId. |
| AC-0029-006 | Interruption before completed retains only ephemeral partial text marked incomplete, no guessed links/success, and manual history recovery reconciles the same identity without automatic resend; its definitive presentation decision remains deferred. |
| AC-0029-007 | Valid completed stays confirmed with full text/links when subsequent history/cookie sync fails; a separate recovery problem blocks sends, preserves draft/content and never exposes resend for that completed turn. |
| AC-0029-008 | History/replay matches role/turn identity without duplicating local user/assistant messages; unknown/foreign/expired state follows 0016 and missing data cannot become invented rollback, mocks or reconstructed automatic requests. |
| AC-0029-009 | Recognized quota/busy/model/stream capacity errors preserve original payload and positive retry guidance for explicit retry, without classifying a healthy rejection as global outage; actual unavailable/protocol uncertainty retains verified recovery. |
| AC-0029-010 | Navigation/collapse/invisibility retain running stream/conversation/draft; full reload restores only authoritative history, collapsed/empty draft, with manual late-result refresh and no persisted partial/pending/error payload or automatic polling. |
| AC-0029-011 | Explicit mock mode preserves complete catalog/details/create/reservation/navigation/chat/history operation without backend network/model/cookie-service requirements; chat events/replay/failure fixtures use shared API types and components import no datasets. |
| AC-0029-012 | API completed uses real authorized history/cookie synchronization; mock completed uses mock history only. Sync never extends server retention, resets a completed answer to failed-send or reopens the deferred cookie-policy redesign. |
| AC-0029-013 | Scrolling follows new fragments only near the bottom; earlier reading/focus is preserved. Accessible waiting/ready announcements occur once per turn, not per fragment or replay/sync, with keyboard-accessible errors/links. |
| AC-0029-014 | API mode has no misleading demo labels, mock mode is identified, the 2000-character input rule remains, and literal text/links render without HTML/Markdown/typing animation or a separate stop control. |
| AC-0029-015 | Exact focused tests, lint/build and actual API/mock browser evidence demonstrate these outcomes, reader cleanup/time/buffer bounds and mode separation; mocks are not claimed as real transport/model proof and no unrequested broad evaluation/full suite/commit occurs. |

## Focused Browser and Regression Oracles

Use self-owned isolated data/identities, with catalog IDs resolved from the actual
fixture; never assume production IDs equal mock IDs. Browser checks use the actual
delivered 0028 route for normal API completion, references, replay/history,
navigation and reload. Controlled native-fetch fixtures cover fault payloads and
partial-frame delivery independently; they do not claim production-backend proof.
If required isolated fixtures/backend/browser tools are absent, record blocked/
incomplete evidence rather than using the user's catalog or changing the scope.

| Case | Required focused scenario |
| --- | --- |
| B029-001 | API: waiting, several literal Unicode/whitespace fragments and completed produce one final message and correct ordered links; no mock or JSON fallback request. |
| B029-002 | Native reader: split UTF-8/CRLF/frame boundaries, comments, malformed data, foreign identity, sequence gap/duplicate, inconsistent completed and premature EOF exercise validation/recovery. |
| B029-003 | Interrupt before completed, then manual history restore finds the saved pair: provisional partial bubble becomes one confirmed answer without duplicate user entry or model resend. |
| B029-004 | completed followed by failed history/cookie sync retains confirmed full content/links; successful manual verified recovery re-enables sending without replaying that completed POST. |
| B029-005 | Same-turn retained replay contains started/completed only; quota/busy/model/stream rejection and cooldown retain original context/identity without automatic send or duplicate UI messages. |
| B029-006 | Route navigation, Back/Forward, collapse/invisibility during delivery preserve stream/draft; full reload during processing restores saved history only and a later saved pair is fetched manually. |
| B029-007 | A reader viewing older messages is not forced down; near-bottom following, keyboard focus/Escape, once-per-turn live-status content and final links remain usable. |
| B029-008 | Explicit mock UI with backend access blocked: catalog/filter/paging/details, creation/reservation and chat/history/navigation/reload remain usable through existing adapters; mock replay/controlled failure lifecycle matches the stream interface. |
| B029-009 | Actual API/mock mode labels and plaintext rendering match the specification; frame/answer bounds and whole-attempt timeout close readers without resetting the deadline or implying server cancellation. |

Fault cases use approved self-owned test fixtures, not new production debug routes
or deliberate mutation/outage of the user's catalog/services. Required observations
may be split between controlled focused tests and actual browser evidence; record
which source proves each guarantee. DOM live-region checks do not claim an actual
screen reader was run unless such evidence was collected. No benchmark or new
model-quality evaluation; preserve real usage accounting if approved API smoke
invokes the existing model.

## Execution Evidence and Deferred Decisions

All AC-0029-001–015 and B029-001–009: NOT RUN. Exact future selectors/file scopes
are in tasks.md. No application checks or live browser/service operation occurred
while preparing these documents.

OOS-0029-002 leaves the definitive interrupted-text UX decision for later; the
current provisional rule remains testable. OOS-0028-004 leaves full detailed cookie
policy review until after phase-1 implementation; its accepted baseline remains
part of this feature's handoff, not an unimplemented acceptance exemption.
