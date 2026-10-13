# Feature 0028 Tasks

Status: T0028-001–T0028-006 complete. Final production native transport, common pipeline,
cookie amendment, generated contracts and architecture are verified. One whole-
feature commit is authorized; no push.

- [x] T0028-001 — Establish/report readiness for the entire explicitly requested
  batch, including delivered search/RAG dependencies where required.
- [x] T0028-002 — Qualify a test-only real HTTP/Servlet transport prototype and
  actual bounded-resource cleanup. Depends on T0028-001.
- [x] T0028-003 — Implement stream settings/admission/state and mapped shared
  response types. Depends on T0028-001/002.
- [x] T0028-004 — Integrate shared turn admission/execution, confirmed saved-result
  readiness and the narrow cookie/history amendment. Depends on T0028-001/003
  and delivered required 0025/0026 behavior.
- [x] T0028-005 — Add MVC route/handler/framing, shared public error mapping and
  generated contract. Depends on T0028-001/004.
- [x] T0028-006 — Qualify final native transport and review focused integration/
  architecture/acceptance evidence. Depends on T0028-001/005.

## Exact Scope and Notation

Read constitution/local instructions, [spec.md](spec.md), [acceptance.md](acceptance.md),
[plan.md](plan.md), [ADR-0028](../../docs/adr/0028-chat-streaming-api.md) and backend
AGENTS_CODE_CONVENTION.md. Execute only requested tickets; readiness/dependencies
are not authorization to execute other features or subsequent tickets.

Exact paths relative to repository root:

- J = stone-shelter-api/src/main/java/lab/stoneshelter
- T = stone-shelter-api/src/test/java/lab/stoneshelter
- F = specs/0028-chat-streaming-api

Every ticket may additionally update only F/tasks.md, F/acceptance.md and
 docs/adr/0028-chat-streaming-api.md for factual execution evidence. No changing
approved requirements/exclusions. Unlisted files remain read-only; no wildcard
scope, dependency manifest/property configuration, schema, frontend or root README
edits. Never access engineering-log. Future filenames do not authorize creation now.

## T0028-001 — Requested-Batch Readiness

Writable: common evidence documentation only.
Checks: focused read-only versions/effective settings/health/connectivity; report
routine startup of needed existing services separately as preparation. No model
call, application test, fixture import, download or persistent setup as preflight.

Cover full requested IDs, prototype versus integration requirements and delivered
contracts. Supply known authorized JAVA_HOME/endpoints/settings in agent-controlled
commands, secrets present/missing only. Report application service states including
stopped backend/frontend with impact. Required unhealthy/missing/unknown components
block the whole affected batch; prepare authorized existing services and recheck.
Do not run missing 0025/0026 tickets or infer readiness from their documents.

## T0028-002 — Real Transport Prototype

Writable, besides common evidence:

- T/StoneChatStreamTransportQualificationTest.java

Selector: StoneChatStreamTransportQualificationTest#qualifyPrototype.

Use a test-owned real embedded pinned MVC/Servlet endpoint with controlled ready
responses and independent processing/writers. Establish SSE framing/UTF-8, actual
flush/error behavior, stalled started/heartbeat writes, slow readers, disconnect
completion, finite delivery/write limits and four-slot/no-queue behavior. Track
actual writer/worker exit and cleanup, not just a timer callback. Isolate fixture
state, no real model, production data, source modification or version change.

Record concrete transport guarantees/limitations before dependent source work.
Failure blocks dependent tickets; propose exact correction separately rather than
widening scope, weakening bounds or installing another server/library.

## T0028-003 — Stream Settings, Admission and Shared Types

Writable, besides common evidence:

- J/services/StoneChatStreamSettings.java
- J/services/StoneChatStreamAdmissionService.java
- J/entities/StoneChatAdmittedTurnEntity.java
- J/entities/StoneChatStreamEntity.java
- J/entities/StoneChatStreamEventEntity.java
- J/enums/StoneChatStreamEventType.java
- J/shared/StoneChatStreamResponse.java
- J/shared/StoneChatStreamEventResponse.java
- J/shared/StoneChatStreamStartedResponse.java
- J/shared/StoneChatStreamTextDeltaResponse.java
- J/shared/StoneChatStreamCompletedResponse.java
- J/shared/StoneChatStreamErrorResponse.java
- J/shared/StoneChatStreamErrorContext.java
- J/mappers/dtos/StoneChatStreamEntityToStoneChatStreamResponseMapper.java
- J/mappers/dtos/StoneChatStreamEventEntityToStoneChatStreamEventResponseMapper.java
- J/mappers/dtos/StoneChatStreamErrorContextToStoneChatStreamErrorResponseMapper.java
- J/exceptions/StoneChatStreamCapacityExceededException.java
- T/StoneChatStreamStateTest.java

Selector: StoneChatStreamStateTest.

Implement validated settings, zero-wait transport capacity, explicit ownership/
terminal/saved-result state and mapped DTOs with bounded pending frames. Keep
entities/services protocol-independent and obey type-size/mapper conventions.
Focused controlled tests verify race-safe slot ownership, invalid settings,
terminal consistency and literal Unicode partitioning; no live inference.

## T0028-004 — Shared Turn and Cookie Integration

Writable, besides common evidence:

- J/services/StoneChatbotService.java
- J/services/StoneChatStreamService.java
- J/services/StoneChatAdmissionService.java
- J/services/StoneChatStreamAdmissionService.java
- J/entities/StoneChatAdmittedTurnEntity.java
- J/entities/StoneChatStreamEntity.java
- J/entities/StoneChatStreamEventEntity.java
- J/repositories/StoneChatSessionRepository.java
- J/controllers/StoneChatBindingInterceptor.java
- J/controllers/StoneChatHistoryController.java
- T/StoneChatStreamIntegrationTest.java
- T/StoneChatbotServiceTest.java
- T/StoneChatSessionRepositoryTest.java
- T/StoneChatHistoryControllerTest.java
- specs/0016-llm-search-conversation-history-and-context/acceptance.md
- docs/adr/0008-chat-memory-and-request-protection.md

Selectors: StoneChatStreamIntegrationTest,StoneChatbotServiceTest,StoneChatSessionRepositoryTest,StoneChatHistoryControllerTest.

Reuse delivered turn processing, admission/replay/fencing/context/atomic commit
for both routes without duplicate quota/model charges. Start independent admitted
processing, emit no answer before confirmed save, preserve disconnect completion,
unknown-commit recovery and current-turn template exception. Query authoritative
remaining session lifetime read-only; supply early-cookie instruction only for new
admitted streams and synchronize successful authorized history headers without
server TTL extension. Preserve async-dispatch protection against double admission.

Use isolated owned PostgreSQL/Redis and controlled providers/clock/mutation/
acknowledgement fixtures; no real inference. Historical 0016 evidence does not
verify this amendment. No broader cookie-policy review/redesign or UI implementation.

## T0028-005 — HTTP Route, SSE Handler and Generated Contract

Writable, besides common evidence:

- J/controllers/StoneChatStreamController.java
- J/controllers/StoneChatStreamWebConfiguration.java
- J/handlers/StoneChatStreamReturnValueHandler.java
- J/handlers/StoneChatProblemDetailFactory.java
- J/handlers/ApiExceptionHandler.java
- J/services/StoneChatStreamService.java
- T/StoneChatStreamHttpTest.java
- T/StoneChatbotControllerTest.java

Selectors: StoneChatStreamHttpTest,StoneChatbotControllerTest.

Add the separate POST with original DTO validation and ready mapped stream handle;
headers/cookie/protocol stay at the HTTP boundary. Register the custom handler,
exact typed events/sequence, bounded writers/heartbeat/fragments, replay and
terminal behavior. Reuse handler-owned public error mapping with existing retry
hints and the new stream-capacity code. Generate and inspect actual JSON/YAML
OpenAPI; no handwritten contract. Exercise exact pre-/post-open, lost-delivery and
cookie boundaries with controlled providers and real wire where required.

## T0028-006 — Final Qualification and Evidence Review

Writable, besides common evidence:

- T/StoneChatStreamTransportQualificationTest.java
- T/StoneChatStreamArchitectureTest.java
- specs/0016-llm-search-conversation-history-and-context/acceptance.md
- docs/adr/0008-chat-memory-and-request-protection.md

Selectors: StoneChatStreamIntegrationTest#qualifyFinalTransport,StoneChatStreamArchitectureTest,StoneChatStreamHttpTest,StoneChatStreamIntegrationTest.

Requalify changed final transport against prototype guarantees, actual writer/
worker cleanup and configured bounds. Review focused final integration, generated
contract, source roots/responsibility packages and acceptance.md evidence. Do not
repeat unchanged model qualification or run a full suite. Record READY/BLOCKED
and actual results honestly; failed bounds cannot be hidden by test relaxation.
Keep 0029 frontend handoff and deferred detailed cookie review visible, then stop
at the requested ticket/batch. No automatic commits, pushes or extra tasks.

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


T0028-002: PASS, qualifyPrototype (one aggregate native HTTP test), including
normal/replay/terminal-error, Unicode, four-slot rejection, actual nonreading
preparation and delivery backpressure/connection close/callback exit, saved-origin
expiry before first write, and disconnect-before/after-save continuation. See
ADR-0028 for scope/short functional bounds and actual cleanup evidence. No inference.


T0028-003: PASS, StoneChatStreamStateTest (five), settings/ownership/no-queue,
immutable confirmed snapshot, literal Unicode, replay/error/empty terminal states,
no cancellation on transport exit and mapped exact payload/null validation. No
search/model/HTTP integration in this independent ticket. T0028-004–006 remain
pending the separately proposed producer profile correction and subsequent native
integration qualification. No partial-feature commit or push.
