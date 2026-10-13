# Feature 0029 Tasks

Status: Prepared, all unexecuted. Discussion/documentation approval authorizes no
source change, application/browser test, service/model operation, commit or push.

- [ ] T0029-001 — Establish/report readiness for the entire explicitly requested
  batch, including delivered backend/browser prerequisites where applicable.
- [ ] T0029-002 — Implement typed native stream parsing, API validation and finite
  transport handling. Depends on T0029-001.
- [ ] T0029-003 — Preserve fully backend-free mock UI with matching mock chat
  stream/history interface and fixtures. Depends on T0029-001/002.
- [ ] T0029-004 — Integrate provider state, captured payload, deduplicated recovery
  and reload/navigation lifecycle. Depends on T0029-001/003.
- [ ] T0029-005 — Implement component presentation, mode labels, scroll and
  accessible announcements. Depends on T0029-001/004.
- [ ] T0029-006 — Verify actual API/mock browser behavior and focused final
  acceptance/evidence. Depends on T0029-001/005 and delivered 0028.

## Exact Scope and Path Notation

Read constitution/local instructions, [spec.md](spec.md), [acceptance.md](acceptance.md),
[plan.md](plan.md), [ADR-0029](../../docs/adr/0029-chat-streaming-widget.md) and UI
AGENTS.md/ADR-0002/ADR-0005. Execute only explicitly requested tickets; dependencies
and readiness do not authorize later or missing backend implementation tickets.

Exact paths relative to repository root:

- U = stone-shelter-ui/src
- F = specs/0029-chat-streaming-widget

Every ticket may additionally update only F/tasks.md, F/acceptance.md and
docs/adr/0029-chat-streaming-widget.md for factual evidence. Do not change
accepted requirements/exclusions under that evidence scope. Unlisted source is
read-only; no wildcard, backend, manifest/lockfile, framework property/proxy,
schema or root README edits. Never access engineering-log. Future names are not
files to create before an authorized execution request/readiness.

Source tickets use only listed Vitest files plus npm run lint and npm run build,
from the UI module with controlled approved runtime/environment. Do not run the
full test suite for an ordinary ticket. If a later frontend commit is explicitly
requested, UI AGENTS.md additionally requires successful lint/full-test/build;
that requirement is not current commit authority.

## T0029-001 — Requested-Batch Readiness

Writable: common evidence documentation only.
Checks: targeted read-only runtime/settings/health/connectivity/tool availability;
needed existing-service startup is separately reported preparation. No application
or browser tests, model call, business import, download or persistent setup as
readiness. Routine tool setup follows approved sources and actual requested scope.

Verify all selected IDs' source/fixture/tool prerequisites, Node/npm/installed
approved UI tools, effective mode/API origin/client bounds and enabled browser
availability. Actual API batch requires delivered/qualified 0028 route/contracts,
healthy backend dependencies and self-owned isolated fixture target; documentation
or mock evidence is insufficient. Pure controlled/mock tickets have no model/
backend dependency but still report application states with impact.

Supply known authorized environment values to the agent's own commands, never ask
for another terminal's exports or invent secrets. Report full covered IDs,
expected/observed components, preparation/checks, READY/BLOCKED, affected IDs and
concrete corrections. Start only needed existing services, recheck actual endpoint
health and stop the affected batch when required resources cannot be verified.

## T0029-002 — Typed Native Reader and API Boundary

Writable, besides common evidence:

- U/enums/ChatStreamEventType.ts
- U/api/dto/StoneChatStreamEvent.ts
- U/api/chatbotStream.ts
- U/api/chatbotStream.test.ts
- U/api/chatbotApi.ts
- U/api/chatbotApi.test.ts
- environment-setup.md

Test selector: npm run test -- --run src/api/chatbotStream.test.ts src/api/chatbotApi.test.ts.
Also: npm run lint; npm run build.

Implement native credentialed POST, bounded incremental UTF-8/SSE parsing, typed
identity/sequence/terminal/text/reference validation, whole-attempt timeout and
finite history handling. Reuse ChatApiError/public API imports, known HTTP/in-band
errors and retry guidance. Keep JSON compatibility independent; no EventSource
reconnect, automatic resend or API-to-mock fallback. Test controlled split frames/
malformed data/replay/EOF/bounds and classify failures honestly. Validate that
client limits fit delivered valid maximum responses before integrated claims.
Document only the new client-timeout setup in root environment-setup.md, no README,
manifest/config or global environment changes. No real model or catalog mutation.

## T0029-003 — Complete Explicit Mock-Mode Chat Lifecycle

Writable, besides common evidence:

- U/api/chatbotApi.ts
- U/api/chatbotApi.test.ts
- U/mocks/api/mockChatbotApi.ts
- U/mocks/api/mockChatbotStream.ts
- U/mocks/api/mockChatbotStream.test.ts
- U/mocks/data/chatStreamScenarios.ts

Test selector: npm run test -- --run src/api/chatbotApi.test.ts src/mocks/api/mockChatbotStream.test.ts.
Also: npm run lint; npm run build.

Select mock dispatch only through the existing explicit mock application mode;
use shared API types and existing mock history/replay/retention/data. Match
stream callback/interface with controlled success/interruption/error fixtures,
valid mock stone links and no backend/cookie/model request. Preserve other mock
catalog/details/create/reservation adapters; do not implement another search engine
or edit unrelated fixtures. API-mode failures must never select mock output.
Unlisted defects require a concrete separately agreed correction scope.

## T0029-004 — Provider State, Recovery and Lifecycle

Writable, besides common evidence:

- U/enums/ChatStreamPhase.ts
- U/enums/ChatMessageDeliveryState.ts
- U/features/stone-chatbot/state/ChatSessionProvider/chatSessionState.ts
- U/features/stone-chatbot/state/ChatSessionProvider/chatSessionState.test.ts
- U/features/stone-chatbot/state/ChatSessionProvider/ChatSessionProvider.tsx
- U/features/stone-chatbot/state/ChatSessionProvider/ChatSessionProvider.test.tsx

Test selector: npm run test -- --run src/features/stone-chatbot/state/ChatSessionProvider/chatSessionState.test.ts src/features/stone-chatbot/state/ChatSessionProvider/ChatSessionProvider.test.tsx.
Also: npm run lint; npm run build.

Integrate one active attempt/request identity, one user/assistant bubble, immutable
captured retry payload versus editable draft, terminal/history reconciliation and
known rejection versus unavailable recovery. Preserve confirmed answers after sync
failure and provisional incomplete text before completed. Deduplicate by role/
turnId; no replay/route/StrictMode duplicate POST or draft overwrite.

Full reload restores saved history only, without persisted pending payloads; expose
manual idle history refresh/recovery without polling. Route/collapse/invisibility
keep active streams; actual page exit/error cleanup may close readers but cannot
claim server cancellation. Both explicit sources use the same state transitions;
mock synchronization never invokes real GET. Use controlled callback/history/
clock fixtures, not real backend/model work.

## T0029-005 — Presentation, Scroll, Labels and Accessibility

Writable, besides common evidence:

- U/features/stone-chatbot/components/StoneChatbot/StoneChatbot.tsx
- U/features/stone-chatbot/components/StoneChatbot/StoneChatbot.css
- U/features/stone-chatbot/components/StoneChatbot/StoneChatbot.test.tsx
- U/features/stone-chatbot/components/StoneChatbot/StoneChatbotNavigation.test.tsx
- U/features/stone-chatbot/state/ChatSessionProvider/ChatSessionProvider.tsx

Test selector: npm run test -- --run src/features/stone-chatbot/components/StoneChatbot/StoneChatbot.test.tsx src/features/stone-chatbot/components/StoneChatbot/StoneChatbotNavigation.test.tsx.
Also: npm run lint; npm run build.

Render waiting, one plain-text fragment message, provisional incomplete marker,
separate recovery and final ordered links with existing components. Preserve draft
editing while sends/prompts/retries are blocked; add the agreed manual history
action and mode-appropriate labels. Follow only near-bottom scrolling, preserve
keyboard/focus/collapse and once-per-turn accessible announcements. No markdown,
typing animation, stop control, new UI library or page/router refactor.

## T0029-006 — Focused Final Browser and Acceptance Evidence

Writable, besides common evidence:

- F/browser-verification.md

Automated selectors, existing files read-only: npm run test -- --run src/api/chatbotStream.test.ts src/api/chatbotApi.test.ts src/mocks/api/mockChatbotStream.test.ts src/features/stone-chatbot/state/ChatSessionProvider/chatSessionState.test.ts src/features/stone-chatbot/state/ChatSessionProvider/ChatSessionProvider.test.tsx src/features/stone-chatbot/components/StoneChatbot/StoneChatbot.test.tsx src/features/stone-chatbot/components/StoneChatbot/StoneChatbotNavigation.test.tsx.
Also: npm run lint; npm run build; focused actual browser scenarios from acceptance.md.

Record actual modes/versions/origins/fixtures and B029 evidence, distinguishing
controlled native-reader fault tests from real delivered backend browser behavior.
Use only self-owned isolated approved API data/identities; fixture preparation is
execution work, not readiness or permission to mutate the user's catalog. Preserve
real model usage/accounting if approved API smoke invokes models. No new backend
debug/fault route, broad quality campaign, benchmark or full suite.

Verify full explicit mock UI with backend access absent/blocked without stopping
unneeded user services. In API mode, demonstrate actual stream/links/history/replay/
reload/navigation/cookie handoff where required. Missing necessary browser/backend/
fixture evidence is blocked/incomplete, not a mock substitute. Record accessibility
DOM/keyboard observations without claiming an unperformed screen-reader session.
Keep the required verified UI development server running afterward and report its
URL under UI instructions. Stop after requested execution; no automatic commit,
push, extra feature or definitive deferred UX/cookie review.
