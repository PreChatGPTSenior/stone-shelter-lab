# Feature 0029 Browser Verification

Local execution date: 2026-10-14 (Asia/Tbilisi). Actual provider accounting uses UTC.
Status: Recorded API/mock evidence satisfies the acceptance scope amended on
2026-10-14. The two prepared mock submissions remain unperformed and are no longer
required; their earlier permission blocker is historical. T0029-006 is now closed
after the subsequent explicit completion/commit request, fresh readiness and
mandatory UI checks. Local commits are authorized; no push is requested.
Original fixed-set qualification 0027 remains separate.

## Environment and Isolation

Chrome extension browser, existing approved profile. Browser interactions use the
connected CUA API. Node 24.21.0/npm 11.19.0; Vite 8.3.2. Built full backend uses
Boot 4.1.1/MVC 7.0.9/Tomcat 11.0.24, approved Java/model/runtime profiles and
production defaults: four streams, 10-second saved-result delivery/write bounds,
15-second preparation heartbeat, 256-code-point chunks, inherited model controls.

- Actual API: `stone-qualification-0029.localhost:5175`, explicit API mode,
  proxy to the owned API on 8082. Full application health UP; generated OpenAPI
  exposes the real `/api/v1/chat/messages/stream` route.
- Owned data: `stone_search_qualification_0027`, declared owner-labelled container,
  approved 15-card/seven-passage fixture and unchanged verified real vector cache.
  No business import, new embeddings, schema/model/threshold/resource change or
  user-catalog mutation. Temporary fixture cleanup verifies each storage UUID and
  removes only owned rows; actual ledger/reservations remain.
- Browser host names isolate cookie/tab identity. No user session storage/cookie
  inspection or browser-state injection is used.
- Explicit developer-only mock: `stone-mock-0029.localhost:5174`, API base deliberately points to
  the verified unreachable local fault target `127.0.0.1:18029`.
- Main backend/frontend are restored on their original configuration/storage and
  8080/5173 after isolated execution; the updated image contains delivered 0028.

## Actual API Observations

One intentional selection request, `Show available small obsidian stones.`,
shows waiting, blocks sending/history refresh and allows a new editable draft.
Collapse and navigation to Obsidian 01 details preserve the active request/draft.
The resulting confirmed response is:

> Found 1 matching available stones.
> Obsidian 01: It fits the small size requirement.

One canonical reference links Obsidian 01 to `/stone-shelter/stones/150`, resolved
from the actual owned fixture. It opens the correct details page. No Demo/Mock,
source attribution, HTML/Markdown rendering or duplicate assistant bubble appears.
Manual history synchronization retains one pair and the draft. Full reload starts
collapsed, clears the draft, restores the saved pair and does not repeat the live
completion announcement. Back/Forward and Escape preserve the conversation and
return keyboard focus to the launcher.

A second intentional request, `What size is this stone?`, is sent from its details
page. Reload while waiting restores only the earlier saved pair, without the old
pending payload or another POST. Manual Refresh history later obtains exactly one
new saved pair. Its actual business output is a clarification:

> Which stone type would you like? What size or admission date do you prefer?

This proves late-result history handoff, not correct size-question routing or an
additional knowledge-quality success. These browser queries never replace 0027
baseline outcomes or human judgments.

After both saves, stopping only the owned API makes manual history synchronization
fail. Both confirmed pairs and the catalog link remain; sending is blocked, a
separate recovery alert/Restore history action appears and no send-retry/mock
fallback is offered. Restart, health UP and manual Restore history recover the
same two pairs without a new POST or duplicate content.

## Explicit Mock Observations

With the API fault target unreachable, the mock catalog has 30 cards. Chat succeeds
and synchronizes locally, with ordered Mars/Luna references and explicit Mock
assistant/replies labels. Luna's link opens its mock details; edited draft and
conversation remain. Catalog page size 12, A–Z sorting, page 2 and Small filtering
work; Small resets to page 1 with ten matches. Reload starts collapsed and restores
only the saved mock pair with empty draft/no repeated live announcement.

Historical preparation, before the approved verification-scope amendment:

- Create `Widget 0029 browser fixture`, BASALT/SMALL, optional biography
  `Temporary mock-only fixture for feature 0029 browser verification.`
- Luna application: `Widget 0029 verification / widget-0029@example.invalid`.

Automatic approval review rejected Submit application because broad UI-testing
permission did not specifically authorize that consequential submission even in
mock mode. No workaround or indirect submission was performed. Permission was
pending at that point; on 2026-10-14 the user instead removed both submissions from
mandatory browser verification. Neither submission was performed or authorized by
that amendment. Existing automated creation/reservation coverage remains the
required evidence for those flows.

## Evidence Matrix

| Case | Actual evidence / current status |
| --- | --- |
| B029-001 | PASS: actual API waiting/confirmed result, one bubble/ordered canonical link; literal Unicode/fragments and wire sequence additionally qualified by native HTTP/reader tests. |
| B029-002 | PASS: focused incremental native-reader tests cover split bytes/lines/frames, malformed names/data, identities, sequence, text mismatch, EOF and bounded cleanup. |
| B029-003 | PASS: controlled callback/native-reader/mock interruption plus authoritative-history tests; actual backend disconnect continuation separately qualified in 0028. No claim of a deliberately interrupted browser fragment session. |
| B029-004 | PASS: controlled immediate completion/sync failure and actual browser retained-pair outage/manual recovery; no resend option for confirmed turns. |
| B029-005 | PASS: actual native HTTP/Redis replay across routes and controlled frontend replay/rejection/cooldown tests; completed UI intentionally offers no replay-send control. |
| B029-006 | PASS: actual navigation/collapse, Back/Forward, reload during processing and later manual history retrieval; controlled provider also tests hidden routes/StrictMode/stale callbacks. |
| B029-007 | PASS: actual focus/Escape/canonical links and controlled 80-pixel scroll/once-per-turn live-region tests. No unperformed screen-reader session claimed. |
| B029-008 | PASS under the 2026-10-14 amendment: actual developer-only backend-free mock chat/history/navigation/reload and details links pass; additional catalog/filter/paging observations remain recorded. Existing automated tests cover creation/reservation; the two browser submissions remain unperformed and are no longer required. |
| B029-009 | PASS: actual mode labels/plaintext, controlled timeout/frame/text bounds and cleanup; no server cancellation claim. |

## Verification and Accounting

Mandatory frontend pre-commit checks pass: lint, all 235 UI tests (one worker),
production build. Two earlier concurrent catalog tests timed out; they pass
independently (18 application-page/six filter-label tests) with unchanged tests and
no raised timeout. Source callback lifecycle changes prompted the complete recheck.

Provider usage remains in the owned persistent ledger. Before browser smoke:
17761 charged tokens; after its first completed request/history/reload:
19160 charged tokens, 31 reservations, actual UTC day 2026-10-13. Subsequent usage
and cleanup are captured in the owned operational manifest and final ADR update.
No model-quality/performance benchmark or personal-set acceptance is claimed.

Local proof screenshots: `/private/tmp/stone-q029-api-success.png`,
`/private/tmp/stone-q029-mock-create.png`, `/private/tmp/stone-q029-mock-reservation.png`.


Historical form handoff: source HMR cleared the earlier prepared fields/dialog;
the same synthetic values were restored without submission. Both tabs were left
available for permission at that time; the subsequent amendment removes the
submission requirement. Updated screenshots from that handoff:
/private/tmp/stone-q029-mock-create-final.png and
/private/tmp/stone-q029-mock-reservation-final.png.
Temporary API frontend 5175 was stopped after isolated cleanup; main API/UI 8080/
5173 and mock UI 5174 remained reachable at the recorded handoff. No new UI source
changes after final lint/235-test/build verification. Current service/browser
states have not been rechecked by this documentation amendment.

## Approved Verification-Scope Amendment — 2026-10-14

The user clarified that backend-free operation is exclusively the developers'
mock development/testing mode and explicitly removed creating Widget 0029 browser
fixture and submitting the Luna application from mandatory browser verification.
See D0029-018/OOS-0029-005 in [ADR-0029](../../docs/adr/0029-chat-streaming-widget.md)
and the revised criteria in [acceptance.md](acceptance.md).

The existing automated mock creation/reservation evidence and actual mock chat/
history/navigation/reload observations satisfy the revised B029-008 scope.
AC-0029-011/015 are no longer partial because of those unperformed submissions.
This is a requirements/evidence amendment, not a new browser or test run, permission
to submit the forms, ticket execution/closure or a commit.

## Final Evidence Review and T0029-006 Closure — 2026-10-14

After the documentation amendment, the user explicitly requested completion and
committing all current changes. Fresh closure readiness is READY as recorded in
tasks.md. Main API/UI and developer mock UI are reachable, the delivered stream
route is present and Chrome remains connected. No service startup was needed.

Mandatory frontend checks were rerun successfully: lint, all 235 UI tests across
28 files with one worker, and build. No application source changed, so prior actual
browser observations and native/controlled evidence remain applicable. The revised
AC-0029-001–015 and B029-001–009 are satisfied; T0029-006 is complete. Neither
prepared form was submitted, and no new browser/model result is claimed.
