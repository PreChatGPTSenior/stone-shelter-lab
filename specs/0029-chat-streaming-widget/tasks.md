# Feature 0029 Tasks

Status: T0029-001–T0029-006 complete. Controlled lifecycle/presentation checks pass;
0028 is delivered and actual API/mock browser checks pass. Recorded evidence
satisfies the acceptance scope amended on 2026-10-14; mock create/reservation
browser submissions are no longer required. The subsequent explicit completion/
commit request authorizes closure; fresh readiness and mandatory UI checks pass.
Local commits are authorized; no push is requested.

- [x] T0029-001 — Establish/report readiness for the entire explicitly requested
  batch, including delivered backend/browser prerequisites where applicable.
- [x] T0029-002 — Implement typed native stream parsing, API validation and finite
  transport handling. Depends on T0029-001.
- [x] T0029-003 — Preserve fully backend-free mock UI with matching mock chat
  stream/history interface and fixtures. Depends on T0029-001/002.
- [x] T0029-004 — Integrate provider state, captured payload, deduplicated recovery
  and reload/navigation lifecycle. Depends on T0029-001/003.
- [x] T0029-005 — Implement component presentation, mode labels, scroll and
  accessible announcements. Depends on T0029-001/004.
- [x] T0029-006 — Verify actual API/mock browser behavior and focused final
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

Verify the explicit developer-only mock mode with backend access absent/blocked,
following the revised browser/evidence scope in acceptance.md and retaining
existing automated creation/reservation coverage, without stopping unneeded user
services. In API mode, demonstrate actual stream/links/history/replay/
reload/navigation/cookie handoff where required. Missing necessary browser/backend/
fixture evidence is blocked/incomplete, not a mock substitute. Record accessibility
DOM/keyboard observations without claiming an unperformed screen-reader session.
Keep the required verified UI development server running afterward and report its
URL under UI instructions. Stop after requested execution; no automatic commit,
push, extra feature or definitive deferred UX/cookie review.

## Requested Batch Readiness — 2026-10-13

Authorization: the user explicitly requested all tickets in features 0025–0029,
continuous execution and one commit per completed feature, rather than one commit
per task. No push was requested. Earlier documentation-only authorization is
superseded for this selected batch; requirements and accepted exclusions are not
changed. Feature 0030 remains outside the requested batch.

Covered ticket IDs: T0025-001–T0025-006, T0026-001–T0026-006,
T0027-001–T0027-006, T0028-001–T0028-006 and T0029-001–T0029-006.

Result: **BLOCKED**. None of the five readiness tickets is complete. No
implementation ticket was started. The root environment rule requires readiness
for the entire selected batch before implementation; the unavailable browser
blocks T0029-006 and therefore entry into this batch. Current application schema
and embedding configuration also fail the application integration prerequisites.
Unperformed checks are not reported as ready.

| Component | Expected | Fresh observed state / impact |
| --- | --- | --- |
| Maven / Java | Maven 3.9.9; JAVA_HOME selects JDK 23.0.2 | Default Maven uses Java 11.0.26. An explicit command-scoped JAVA_HOME=/usr/local/Cellar/openjdk/23.0.2/libexec/openjdk.jdk/Contents/Home reports Maven 3.9.9 and Java 23.0.2. No persistent environment change. |
| Docker / test image | Reachable daemon; approved immutable PostgreSQL/vector image | Docker 29.8.1 reachable with authorized socket access. Exact pgvector/pgvector:0.8.6-pg18-trixie@sha256:78bf48b801e792f99e3ac62b5036fd3876e9be48afda16c1e331af1c75ceb2ff is installed. No application test or Testcontainers lifecycle was run. |
| PostgreSQL | Authenticated connectivity, PostgreSQL 18.6 / vector 0.8.6; required existing schema | Authenticated TCP query at postgres:5432 succeeds and confirms 18.6 / 0.8.6. Current public schema contains catalog/photo/reservation and Liquibase tables only. stone_card_embedding, stone_document_index, stone_document_fragment, model_token_budget_day and model_token_reservation are absent. Integration/model-ledger readiness is BLOCKED. |
| Redis | Redis 8.2.10 reachable | Existing Redis responds PONG; server metadata confirms 8.2.10. Current backend uses redis:6379, database 0. Full current application ownership/history/control readiness is unverified. |
| Ollama / chat profile | Previously approved 0.40.3; installed qwen2.5:3b, 8192 configured context / 512 output | Host and existing backend endpoint return 0.40.3. Installed qwen2.5:3b digest 357c53fb659c5076de1d65ccb0b397446227b71a42be9d1603d46168015c9e4b; metadata reports native context 32768 and qwen2 byte-level tokenizer. No inference. Current application controls/output configuration are not proven by model metadata. |
| Embedding profile | Installed qwen3-embedding:0.6b / 1024; configured actual embedding endpoint | Installed digest ac6da0dfba84a81fdbfbaf330198c33cd77c4cdfc53e8bc50eb581914a15621d; metadata reports context 32768 and embedding length 1024. STONE_DOCUMENT_OLLAMA_BASE_URL is missing in the running backend. Published example also lacks this setting. Current projection/profile state cannot be verified because the tables are absent. |
| Backend | Current delivered 0024 application and healthy dependencies | Existing container is healthy and /actuator/health returns UP, but generated OpenAPI exposes only /api/v1/chat/messages among chat paths and the schema is stale. Liveness does not establish current chat/control readiness. Datasource credentials are present (values not reported). Document source settings are missing in this container. |
| MinIO / Kafka | Report existing service states and task impact | MinIO readiness returns HTTP 200. Kafka metadata succeeds at kafka:9092 and the running image is apache/kafka:4.2.2. These checks do not prove document import/indexing or application business processing, and no such operation was run. |
| UI runtime / tools | Node 24.21.0, npm 11.19.0, pinned installed tools | Node 24.21.0, npm 11.19.0, Vite 8.3.2, Vitest 5.0.3, TypeScript 6.0.3 and ESLint 10.12.0 confirmed by CLI metadata. Remaining package compatibility and application verification are unperformed. |
| Frontend | Reachable API-mode development server for the later browser ticket | Port 5173 was initially stopped. Started the existing npm run dev:api server with API_PROXY_TARGET=http://localhost:8080, empty VITE_API_BASE_URL, host 127.0.0.1 and strict port 5173; catalog URL returns HTTP 200. Server remains running at http://127.0.0.1:5173/stone-shelter/catalog. No UI test was run. |
| Browser | Enabled browser usable for actual T0029-006 verification | Computer-use inventory contains no enabled browsers. Attempts to open the local UI with both iab and chrome return Browser is not available. BLOCKED; controlled/mocked checks cannot replace the accepted actual browser verification. |
| Within-batch implementation dependencies | Earlier selected producer tickets complete before their consumers | 0025/0026/0028 planned contracts are future outputs of this authorized batch, not existing delivered resources. Their absence is not treated as an external readiness defect; consumers must wait for their producer tickets and qualification. |

Preparation, distinct from read-only probes: started the existing UI development
server. Built the already implemented backend source through its existing
Dockerfile with tests skipped, using a context containing only backend Dockerfile,
.mvn, mvnw, pom.xml and src. Build succeeded; image
stone-shelter-api:0024-readiness is available. The old running backend was not
replaced, and no migrations/imports/business mutations were performed. The build
is preparation of the preceding delivery, not implementation or acceptance evidence
for 0025–0029. Existing services and volumes were preserved.

Pin clarification: environment-setup.md / ADR-0001 still contain historical Ollama
0.40.2 text. The selected plans inherit the explicit approval of installed 0.40.3
recorded in T0024 batch evidence (and previously approved in 0017/0018). This check
uses that existing approval; no runtime/model version was changed.

Concrete corrections and resume gate:

1. Connect an enabled computer-use browser so the agent can perform actual
   T0029-006 verification. No enabled browser is currently exposed to these tools.
2. Prepare and start the current already implemented backend using the approved
   endpoint/source configuration and existing Liquibase migrations; preserve the
   database and volumes. The newly built image alone is not a running current
   application. Recheck the required vector/index/usage-ledger tables, profile
   compatibility, application control readiness and backend-reachable embedding
   endpoint before implementation. Do not substitute missing tables with empty
   matches or bypass model accounting.
3. Recheck the whole selected batch after correction. Keep the command-scoped
   JDK and endpoints explicit. No inference, import or application test is an
   acceptable substitute for preflight. All shared limits and later stream/RAG
   settings must be verified in their actual execution context before consumption.
4. After readiness passes, execute producers and consumers in dependency order.
   T0027-004 still requires actual user verdicts on concrete generated baseline
   answers; the execution request does not fabricate or waive that human review.

No implementation tests, inference, fixture provisioning, production-code edits,
commits or pushes occurred. Documentation evidence is the only repository change.
All ticket checkboxes remain unchecked; do not create a completed-feature commit
from this blocked readiness attempt.

## Fresh Batch Readiness After Browser Connection — 2026-10-13

Covered IDs: T0025-001–T0025-006, T0026-001–T0026-006,
T0027-001–T0027-006, T0028-001–T0028-006 and T0029-001–T0029-006.
Result: **READY for the requested sequential batch's external environment
prerequisites**. Earlier BLOCKED evidence is retained as historical evidence,
not the current result. Within-batch producer deliveries and each mandatory
prototype/final qualification remain gates before their consuming tickets.

| Component | Expected | Fresh observed result |
| --- | --- | --- |
| Java/Maven | JAVA_HOME-selected JDK 23.0.2 / Maven 3.9.9 | Wrapper reports 23.0.2 / 3.9.9 with JAVA_HOME=/usr/local/Cellar/openjdk/23.0.2/libexec/openjdk.jdk/Contents/Home. Host defaults are not used. |
| Docker/Testcontainers | Reachable daemon and approved pinned image | Docker 29.8.1 available through authorized socket access; exact approved PostgreSQL/vector digest installed. Existing focused test support supplies isolated PostgreSQL/Redis targets during execution. No application test was used as preflight. |
| PostgreSQL/vector/schema | Authenticated 18.6 / 0.8.6 and existing required tables | Actual postgres:5432 connection confirms versions. Starting the current preceding backend applied existing Liquibase migrations: stone_card_embedding, stone_document_index, stone_document_fragment, model_token_budget_day and model_token_reservation now exist. No migration file was changed. |
| Projection compatibility / ledger | Compatible existing profiles and healthy actual controls | Read-only incompatible-profile counts are zero for card vectors and published document profiles. Actual UTC-day charged total is zero at this check. No usage was deleted/reset. Empty projections do not require a production import; later tickets own their approved isolated fixtures. |
| Redis / chat controls | Redis 8.2.10 and healthy current history | Redis responds PONG. Actual correctly routed GET /api/v1/chat/conversations/{UUID}/messages returns HTTP 200 with empty messages for a new test-owned identifier. No message was sent, no completed pair was created and no model was invoked. |
| Backend / effective settings | Current approved preceding delivery, actual connectivity and approved limits | stone-shelter-batch-api is running, /actuator/health is UP. Actual runtime explicitly supplies both chat and embedding endpoints http://host.docker.internal:11434, N=1, daily budget 100000, context 8192, interpretation output 512, call bound PT50S and turn bound PT120S. Required datasource/source credentials are present; values are never printed. |
| Ollama/models | Approved 0.40.3 and unchanged selected profiles | Host/backend reachability confirmed; 0.40.3. qwen2.5:3b digest 357c53fb659c5076de1d65ccb0b397446227b71a42be9d1603d46168015c9e4b and qwen3-embedding:0.6b digest ac6da0dfba84a81fdbfbaf330198c33cd77c4cdfc53e8bc50eb581914a15621d remain installed, native context 32768, embedding length 1024. No inference in preflight. |
| Installed backend tools | Approved existing libraries, no new dependency | Cached Spring Boot 4.1.1, Spring AI Ollama 2.0.1, Testcontainers 2.0.5 and JDBC 42.7.13 artifacts confirmed by targeted file metadata. Pinned current backend build previously succeeded with tests skipped. MVC/Tomcat runtime guarantees remain subject to the explicitly selected transport qualification, not assumed from cache presence. |
| Node/npm/UI | Node 24.21.0/npm 11.19.0 and approved tools | Explicit /usr/local/bin path gives 24.21.0/11.19.0. A non-login shell can otherwise select Node 24.13.0: all UI execution must supply the controlled PATH. React/DOM 19.3.0, Vite 8.3.2, Vitest 5.0.3, TypeScript 6.0.3 and ESLint 10.12.0 checked with installed runtime/CLI metadata. |
| UI origin/mode | Reachable API-mode local server | API-mode server remains reachable at http://127.0.0.1:5173/stone-shelter/catalog, HTTP 200; proxy uses backend http://localhost:8080 and empty VITE_API_BASE_URL. Both local UI origins are explicitly supplied to current backend admission. |
| Browser | Connected browser and usable local page | User connected Chrome. Computer-use inventory now exposes the Chrome extension; claimed existing Stone Shelter tab renders the catalog and Open chat control. Browser prerequisite is READY. This is not T0029 browser acceptance. |
| Other services | Report actual state and impact | PostgreSQL, Redis, Kafka and MinIO remain running/healthy; prior actual Kafka metadata and MinIO HTTP readiness passed. No unrelated service was started, no encyclopedia import/reindex or business mutation was performed. |
| Future capabilities | Earlier selected producers before consumers | 0025/0026/0028 source contracts and 0027/0029 fixtures are outputs of this selected batch. They are not claimed delivered now. Enforce producer and qualification completion before consumer execution; human T0027-004 review remains required. |

Preparation: user connected the browser; the agent stopped the old backend process
and started its already-built current preceding delivery with command-scoped
approved values, using existing runtime credentials without printing them.
Liquibase applied existing migrations as part of routine startup. Repository
configuration files, private .env, existing business records and service volumes
were preserved. This preparation is not a read-only probe. No new models/runtime
versions, business import, test, inference or feature implementation was used to
establish readiness.

Agent test commands must supply JAVA_HOME, DOCKER_HOST=unix:///Users/viktoryia/.docker/run/docker.sock,
TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE=/var/run/docker.sock, host chat/embedding
endpoints http://localhost:11434 and the approved limits explicitly. UI commands
must supply PATH beginning /usr/local/bin, API proxy/base and relevant accepted
client bound. Isolated fixtures and future settings are verified in their own
producer tickets; do not bypass a failed qualification or fabricate user review.


## Current Implementation Gate — 2026-10-13

External batch environment readiness remains READY. T0025-002 real-model prototype
qualification failed; its required producer gate blocks dependent implementation
in this selected sequence. This is a model-format/behavior qualification failure,
not a renewed browser or infrastructure outage. No completed-feature commit is
permitted from partial readiness/test work. No application production source was
changed and no tasks beyond readiness/T0025-002 were executed.


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


## T0025-002 Gate Passed After Authorized Corrections — 2026-10-13

Changed nullable-wish interpretation and capacity tests PASS: two tests, zero
failures/errors. Shelf-only, mixed small/red/basalt/shelf and generic availability
all produced the required action/effective-wish shape. Original source-classification
checks from correction attempt 2 remain valid and passed all four fixed evidence
cases. Unchanged real vectors and three controlled exact transport checks retain
their earlier successful evidence; no redundant inference was performed.

Latest interpretation actual usages: 295/56, 297/61 and 293/87; three reservations,
1089 charged tokens in the isolated actual UTC-day ledger. Capacity upper bound
8063 including output/repair remains within 8192. No resource/model/threshold was
changed. Prior failed attempts are retained, not rewritten as successes. Basic
prototype formats are now qualified; final production providers still require
T0025-006 and no 0025 feature acceptance/commit is claimed.

Proceed with the requested production tickets in dependency order under the
existing whole-batch external readiness and user's necessary-type scope approval.


T0029-002: PASS, 35 focused parser/API tests plus lint/build on approved runtime.
Read-only native tokenizer maximum 128 decoded bytes supports the conservative
376832-byte template/frame bound under current initial settings, below 1 MiB.
No real-model/browser/API-delivery claim; independent controlled reader only.
Existing error type extracted to api/ChatApiError.ts to prevent circular imports.

## Approved Documentation Amendment — 2026-10-14

The user explicitly removed creating Widget 0029 browser fixture and submitting
the Luna application from mandatory browser verification, and clarified that
backend-free mock operation is for developers' development/testing only.
Requirements and evidence are aligned with D0029-018/OOS-0029-005. Existing
automated creation/reservation coverage remains; the earlier submission-permission
blocker no longer applies. This request updates documentation only: no ticket
execution, readiness claim, application change, test/browser action, commit or
push is performed, and the T0029-006 checkbox is not changed.

## Final T0029-006 Closure Readiness and Verification — 2026-10-14

The user subsequently requested completion and committing all current changes.
Covered batch: T0029-001 readiness for T0029-006 final evidence review, mandatory
frontend pre-commit verification and local commits. This does not execute unfinished
0027 qualification tasks or repeat real-model/browser business operations;
previously recorded evidence is reused under the approved scope.

| Component | Expected | Fresh observed result / impact |
| --- | --- | --- |
| UI runtime/tools | Approved pinned versions | Node 24.21.0, npm 11.19.0, Vite 8.3.2, Vitest 5.0.3, TypeScript 6.0.3 and ESLint 10.12.0 match. Command-scoped PATH uses /usr/local/bin; JAVA_HOME selects installed JDK 23.0.2. |
| API UI | Reachable explicit API mode | localhost:5173 catalog/module HTTP 200; served module reports MODE=api, empty VITE_API_BASE_URL and default 135000-ms timeout. |
| Developer mock UI | Reachable explicit mock mode | stone-mock-0029.localhost:5174 catalog/module HTTP 200; MODE=mock, API base=http://127.0.0.1:18029, timeout=135000. No backend dependency. |
| Backend/contract | Healthy delivered backend/0028 route | localhost:8080/actuator/health HTTP 200/UP; generated OpenAPI exposes /api/v1/chat/messages/stream. |
| Infrastructure | Existing dependencies reachable | Docker 29.8.1 reachable; PostgreSQL pg_isready accepts connections, Redis PONG, Kafka metadata succeeds at kafka:9092, MinIO readiness HTTP 200. |
| Provider/resources | Approved provider/models installed | Host Ollama 0.40.3; qwen2.5:3b and qwen3-embedding:0.6b installed. Backend document endpoint is http://host.docker.internal:11434; datasource/storage secrets present, values not printed. No new inference is required. |
| Browser/evidence | Connected browser/retained acceptance evidence | Chrome extension available with existing main/mock tabs; browser-verification.md covers revised criteria. No new form submission or browser smoke is required. |
| Temporary isolated services | No new isolated API run required | Owned API/UI smoke targets were deliberately stopped after cleanup; not prerequisites for this evidence review. |

Overall result: READY for this exact closure/commit batch. No service startup or
persistent configuration change was needed. Initial sandbox connectivity denial
was resolved with approved local read-only probes; Vite JSON parsing and Python
.localhost resolution issues were corrected without application changes.

Verification with explicit JAVA_HOME/PATH, empty VITE_API_BASE_URL,
API_PROXY_TARGET=http://localhost:8080 and VITE_CHAT_STREAM_TIMEOUT_MS=135000:
npm run lint PASS; npm run test -- --run --maxWorkers=1 PASS (28 files, 235 tests);
npm run build PASS; scoped git diff --check PASS. No backend full suite or new
inference was run. Existing automated creation/reservation coverage satisfies the
amended scope; AC-0029-001–015/B029-001–009 are satisfied by recorded evidence.

T0029-006: PASS, final evidence reviewed and checkbox closed. No application
source changed during closure. The 0027 evidence is committed separately with
its incomplete status preserved; no unfinished 0027 task is executed/closed.
Commit messages use the applicable full feature-directory prefix. No push.
