# Feature 0025 Tasks

Status: COMPLETE — T0025-001–006 and focused acceptance review passed on
2026-10-13. One whole-feature commit is authorized; no push is authorized.
Continue the selected batch through 0029 after this feature commit.

- [x] T0025-001 — Establish and report fresh environment readiness for every
  ticket in the explicitly requested batch; record covered IDs and block the
  batch unless every required prerequisite passes.
- [x] T0025-002 — Qualify compact semantic interpretation/evidence formats,
  actual usage and bounded context/output with the installed selected models.
  Depends on T0025-001; failure blocks dependent application implementation.
- [x] T0025-003 — Implement settings, domain/shared retrieval types and bounded
  live-filtered card/document queries. Depends on T0025-001 and T0025-002.
- [x] T0025-004 — Implement structured evidence validation, guarded batch calls
  and response mapping. Depends on T0025-001 and T0025-003.
- [x] T0025-005 — Integrate semantic interpretation, candidate orchestration,
  exact alternatives, single refresh, history metadata and HTTP error mapping.
  Depends on T0025-001 and T0025-004.
- [x] T0025-006 — Requalify final production providers and review focused
  architecture/acceptance evidence. Depends on T0025-001 and T0025-005.

## Scope and Path Notation

Read [spec.md](spec.md), [acceptance.md](acceptance.md), [plan.md](plan.md),
[ADR-0025](../../docs/adr/0025-semantic-stone-candidate-search.md), the constitution
and relevant root/backend instructions. Before Java work also read the backend
AGENTS_CODE_CONVENTION.md. Do not execute unrequested subsequent tickets.

Paths below are exact, relative to the repository root:

- J = stone-shelter-api/src/main/java/lab/stoneshelter
- T = stone-shelter-api/src/test/java/lab/stoneshelter
- R = stone-shelter-api/src/test/resources
- F = specs/0025-semantic-stone-candidate-search

Every execution ticket may additionally update only these documentation files
for its factual progress/evidence: F/tasks.md, F/acceptance.md and
docs/adr/0025-semantic-stone-candidate-search.md. This common allowlist does not
authorize changing requirements, accepted exclusions or another feature's documents.
All unlisted files are read-only. A required additional write is a scope blocker,
not permission to add a wildcard allowlist or weaken tests. No root README edit.

New named source/test files below are planned artifacts, not files to create
until their ticket is requested and all prerequisites have passed. No dependencies,
framework configuration, migration or frontend sources are writable here.

## T0025-001 — Requested-Batch Readiness

Writable: common evidence documentation only. Application-file edits: none.
Permitted verification: targeted read-only runtime/configuration/endpoint checks;
routine startup of required existing services as separately reported preparation.
No application tests, inference, model download, source import or business mutation.

For all selected IDs, check prerequisites from plan.md, exact test context,
approved pinned tools, Maven JAVA_HOME, Docker, authenticated PostgreSQL/vector
state, required Redis/model/ledger connectivity and actual backend-reachable
endpoints. Verify installed models/profiles, not inference output. Supply known
authorized environment values to the agent's own commands; print no secret values.
Report other application/service states and impact without inferring health.

Record covered IDs, each expected/observed component state, preparation versus
read-only probes, READY/BLOCKED result and specific corrective actions. Prior
readiness never covers this new batch. Complete necessary permitted startup,
then recheck; do not bypass missing sources/models/schema or failed connectivity.
No implementation ticket starts until the entire requested batch is ready.

## T0025-002 — Basic Model/Context Qualification

Writable, in addition to common evidence documentation:

- T/StoneSemanticSearchQualificationTest.java
- R/semantic-search-qualification.json

Selector: `StoneSemanticSearchQualificationTest`.

Use a small fixed English fixture set and the existing transport against installed
qwen2.5:3b/qwen3-embedding:0.6b, with controlled admission/accounting and isolated
test data. Qualify semantic-only/mixed interpretation, partial/type-only support,
individual contradiction and unknown evidence, strict output/usage metadata and
compatible single query-vector reuse. Prove compact fresh-current-input capacity,
required-state failure handling and candidate/wish/source batching bounds under
8192/512; preserve the full current input domain, not a guessed characters/token
ratio. Timeouts remain failures, not evidence of a token-overflow diagnosis.

This test-only prototype uses explicit internal model schemas in its fixture;
it is not a handwritten HTTP contract or a final production-provider pass.
No production source edit, host download, prompt tuning campaign, benchmark,
encyclopedia import or real catalog mutation. Report a failed qualification and
stop dependent implementation instead of changing resource/model choices.

## T0025-003 — Settings and Bounded Repository Queries

Writable, in addition to common evidence documentation:

- J/services/StoneSemanticSearchSettings.java
- J/criteria/StoneSemanticWish.java
- J/criteria/StoneSemanticSearchCriteria.java
- J/criteria/StoneSemanticRetrievalCriteria.java
- J/criteria/StoneSemanticPassageCriteria.java
- J/entities/StoneSemanticCandidateEntity.java
- J/entities/StoneSemanticRetrievalResultEntity.java
- J/entities/StoneSemanticSearchResultEntity.java
- J/entities/StoneSemanticSearchStateEntity.java
- J/enums/StoneSemanticSearchMode.java
- J/enums/StoneSemanticSupportSource.java
- J/repositories/StoneSemanticSearchRepository.java
- J/shared/StoneSemanticWishRequest.java
- J/shared/StoneSemanticSearchRequest.java
- J/shared/StoneSemanticSearchResponse.java
- J/shared/StoneSemanticCandidateResponse.java
- J/shared/StoneSemanticWishResponse.java
- J/shared/StoneSemanticSourceResponse.java
- J/shared/StoneSemanticSearchStateResponse.java
- J/mappers/dtos/StoneSemanticSearchResultEntityToStoneSemanticSearchResponseMapper.java
- T/StoneSemanticRetrievalTest.java
- environment-setup.md

Selector: `StoneSemanticRetrievalTest`.

Implement the exact initial settings and internal result contracts from spec/plan.
Use current catalog predicates before candidate limits, compatible source queries,
inclusive independent thresholds, provenance and deterministic bounded recall.
Reuse existing tables and source models; keep existing supporting retrieval APIs
intact. Test SQL/settings/mapping with isolated pinned PostgreSQL/pgvector data
and synthetic vectors, including filtered-out higher-score candidates and missing
card vectors supported by documents. No model calls in this ticket.

environment-setup.md may document the new variable names/defaults only; do not
persist local configuration, edit root README or start imports/reindexing.

## T0025-004 — Evidence Providers, Validation and Batching

Writable, in addition to common evidence documentation:

- J/services/StoneSemanticEvidenceService.java
- J/services/StoneSemanticEvidenceProvider.java
- J/services/OllamaSemanticEvidenceProvider.java
- J/services/StoneSemanticEvidenceValidationService.java
- J/shared/StoneSemanticEvidenceRequest.java
- J/shared/StoneSemanticEvidenceCandidateRequest.java
- J/shared/StoneSemanticEvidenceSourceRequest.java
- J/shared/StoneSemanticEvidenceResponse.java
- J/shared/StoneSemanticEvidenceCandidateResponse.java
- J/shared/StoneSemanticWishEvidenceResponse.java
- J/entities/StoneSemanticEvidenceResultEntity.java
- J/mappers/dtos/StoneSemanticEvidenceResultEntityToStoneSemanticEvidenceResponseMapper.java
- J/exceptions/InvalidStoneSemanticSearchOutputException.java
- J/exceptions/StoneSemanticSearchLimitExceededException.java
- T/StoneSemanticEvidenceTest.java

Selector: `StoneSemanticEvidenceTest`.

Use existing guarded transport/model settings, no new client. Implement strict
candidate/wish/source/quote validation, card precedence, aggregate partial/unknown
support, complete-source segmentation, output/context packing and turn-wide call
accounting. One corrective attempt per malformed batch consumes the same cap.
Exercise request wire shape, guard/usage settlement and actual-exit deadlines with
controlled provider responses, not real inference. Do not skip unprocessed data
or return success on counter/context/budget exhaustion. Pure explanation remains 0026.

## T0025-005 — Search Flow, Chat, History and Errors

Writable, in addition to common evidence documentation:

- J/services/StoneSemanticSearchService.java
- J/services/StoneSemanticCandidateService.java
- J/services/StoneSemanticSearchProvider.java
- J/services/OllamaSemanticSearchProvider.java
- J/services/StoneSemanticSearchValidationService.java
- J/services/StoneExactSearchService.java
- J/services/StoneChatbotService.java
- J/services/StoneChatAvailabilityService.java
- J/shared/StoneSemanticSearchDecision.java
- J/entities/StoneExactSearchPreparationEntity.java
- J/entities/StoneChatTurnEntity.java
- J/repositories/StoneChatConversationRepository.java
- J/mappers/dtos/StoneSemanticSearchDecisionToStoneExactSearchDecisionMapper.java
- J/mappers/entities/StoneSemanticSearchDecisionToStoneSemanticSearchCriteriaMapper.java
- J/mappers/dtos/StoneSemanticSearchCriteriaToStoneSemanticSearchRequestMapper.java
- J/mappers/entities/StoneSemanticSearchResponseToStoneChatTurnEntityMapper.java
- J/mappers/entities/StoneExactSearchResultEntityToStoneChatTurnEntityMapper.java
- J/handlers/ApiExceptionHandler.java
- J/controllers/StoneChatbotController.java
- T/StoneSemanticChatIntegrationTest.java
- T/StoneExactSearchServiceTest.java
- T/StoneChatbotServiceTest.java

Selectors: `StoneSemanticChatIntegrationTest,StoneExactSearchServiceTest,StoneChatbotServiceTest`.

Wire single interpretation, effective wishes, reusable exact preparation,
CUSTOMER query embedding, source/evidence processing, accepted group order,
bounded exact alternatives, final whole-pool eligibility and exactly one refresh
only after total disappearance. Retain the same turn counter/deadline/vector.
Implement minimal truthful templates, bounded count/hasMore and optional internal
history metadata, without public DTO/UI changes or substantive generation.

Translate semantic 502/503 failures and preserve healthy history/replay for local
limits. Keep dependency outages fail-closed and failed-turn atomicity. Controller
edits are only generated-contract annotations; no business/query/mapping logic.
Focused tests use controlled providers, isolated test-owned database/Redis data
and explicit change-before-final-check cases. Inspect generated JSON/YAML OpenAPI
in the integration test. Modify existing test wiring only for the new delegate;
retain their exact-search behavioral assertions. No production inference here.

## T0025-006 — Final Provider Qualification and Focused Review

Writable, in addition to common evidence documentation:

- T/StoneSemanticSearchQualificationTest.java
- T/StoneSemanticSearchArchitectureTest.java

Selectors: `StoneSemanticSearchQualificationTest,StoneSemanticSearchArchitectureTest`.

Use the now-final production providers for the small previously defined real-model
fixture set and final prompt/usage/context proof. Repeating qualification is
justified because prototype inputs/providers changed; do not expand into 0027's
broad query suite. Check responsibility/mapping/JPA/protocol/record boundaries.
Review AC-0025-001–015 against prior focused evidence and final qualification,
including the actual generated HTTP contract evidence from T0025-005.

No production edits in this review ticket. If a failure requires correction,
report the defect and affected exact allowlist; do not add an unrequested fix task,
loosen a test, run a full suite or mark acceptance complete. No commits/pushes.

## Execution Evidence

T0025-001–005: COMPLETE under the user's explicitly authorized whole batch.
T0025-006: COMPLETE; final changed evidence and focused acceptance review pass. Historical failed
prototype/production attempts below are retained and are not current pass claims.
No complete-feature commit has been made. Continue the selected batch through
0029; do not stop at individual task/feature boundaries.

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


## T0025-002 — Actual Qualification Evidence

Created only the allowed test-only artifacts:
StoneSemanticSearchQualificationTest.java and semantic-search-qualification.json.
The first compilation attempts found mapper/vector/primitive-array/checked-
exception mistakes in the new test. Those test-code defects were corrected within
this ticket's allowed file; those attempts invoked no models. No existing test or
production source was changed.

Actual selected command: ./mvnw -B -Dtest=StoneSemanticSearchQualificationTest test,
from stone-shelter-api, with explicit JAVA_HOME JDK 23.0.2, approved Docker socket,
CHAT_MODEL_OLLAMA_BASE_URL=http://localhost:11434,
STONE_DOCUMENT_OLLAMA_BASE_URL=http://localhost:11434, N=1 and budget 100000.
A test-owned pinned PostgreSQL container ran the existing Liquibase migrations
and actual reservation/settlement services; no user catalog/document/history
record was inserted or removed. No production usage ledger was reset.

Result: **4 tests, 2 failures, 0 errors, 0 skipped**; build failed.

- PASS: compatible real 1024-dimensional query/card/document vectors. One query
  vector was reused to calculate both source similarities; observed card score
  0.7736960136250176 and passage score 0.7890359526051032. This is prototype
  profile/reuse evidence, not production repository/ranking acceptance.
- PASS: byte-level current-input/context upper bound 7843 including 512 output
  and 200 repair allowance, within 8192. Required oversized state/evidence fails
  explicitly instead of truncating. This is not final provider packing evidence.
- FAIL: first semantic-only interpretation input, Find a stone for a shelf.,
  returned CLARIFY twice instead of SEARCH and invented additional preferences/
  dates. Mixed and availability-only examples were not reached; they are untested.
- FAIL: first partial-card evidence fixture produced an invented source id url,
  though its source list was empty. The single correction still invented url
  and falsely represented blue support. Later type-only/contradiction/unknown
  fixtures were not reached and must not be described as qualified.

Observed chat attempts and reliable actual usage (input/output): 287/183,
309/232, 237/135 and 259/134. Four chat calls plus three embeddings created seven
reservations; final isolated ledger charged 1840 tokens on actual UTC day
2026-10-13. No allowance/resource/model/threshold was increased; no model retry
beyond each malformed example's one correction was made. The isolated test
infrastructure was closed after reporting its ledger; this is not 0027's persistent
baseline/accounting target or a completed acceptance run.

No production source ticket may proceed while qualification is failed. A narrow
shared-transport correction is proposed in ADR-0025 for separate agreement:
OllamaChatRepository.java and StoneModelInvocationEntity.java are not in this
ticket's writable list, so no correction to them has been applied. Do not weaken
the test/gold, change model/limits or repeat unchanged successful checks.


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


### Authorized correction attempt 1 — retained outcome

The two reviewed transport changes were applied with includeExactSearchExample
true by default, false for new semantic prototype calls. Dynamic evidence schema
IDs/source cardinality now reflect supplied inputs. Focused rerun: three controlled
exact-search methods PASS; changed semantic capacity PASS (upper bound 7780);
interpretation/evidence methods FAIL. Six tests total, two failures, no errors.
The unchanged successful vector qualification was not repeated.

Shelf-only SEARCH and partial-card support now pass. Mixed small red basalt for
a shelf still omits color on both attempts. Type-only support still copies the
document quote into cardVerdict SUPPORTED on both attempts; the card is silent.
Later generic/veto/unknown cases were not reached. Actual chat usages were
215/105, 225/110, 247/110, 233/53, 235/58 and 257/58: six reservations,
1906 charged tokens in the isolated actual UTC-day ledger. Prior failed evidence
is retained and requirements/gold/resource settings are unchanged.

Evidence-supported next correction within the user's broader necessary-type
approval: retain the optional exact example; make the compact interpretation
example include simultaneous color/use wishes; classify individual-card and
type-source text in distinct bounded source batches with supplied-ID constraints.
Backend aggregation retains candidate/wish/source coverage and card precedence.
No quote can acquire card authority merely because the document text was present
in the same prompt. This changes internal prototype packing, not a public HTTP
contract or expected outcome. Requalify changed formats and capacity; do not rerun
unchanged successful exact regression or vector checks.


### Authorized correction attempt 2 — retained outcome

Separate source classification now passes partial-card, type-only, individual
contradiction and unknown evidence, with verbatim source checks and unchanged
expected verdicts. Capacity passes (7880 upper bound). Interpretation fails at
shelf-only because the model adds a color wish whose text is the literal string
null; one repair repeats that invalid invented wish. Three tests, one failure;
unchanged vectors/exact transport were not rerun. Actual usages: 207/73,
193/35, 182/37, 201/49, 182/37, 189/35, 176/34, 257/61, 279/61;
nine reservations charged 2288 tokens in the isolated actual UTC-day ledger.

The remaining defect is variable wish-array shape/absence representation. The
next necessary internal-format correction uses explicit nullable color/use slots
and an additional keyed-wish list, then maps only present wishes to canonical
key/text criteria. This does not add a structured color to a catalog stone or
relax the independent expected wished-key oracle. JSON null means absence;
literal null/empty placeholders remain invalid. Requalify only the changed
interpretation/capacity inputs; evidence classification is unchanged and is not
repeated. No public route, model/profile, resource bound or requirement changes.


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


## T0025-003 — Actual Outcome

PASS: StoneSemanticRetrievalTest, five tests, zero failures/errors. Approved
isolated PostgreSQL 18.6/pgvector 0.8.6, existing migrations and synthetic vectors;
no model inference or user-data mutation. Live AVAILABLE/type/size/UTC dates
precede candidate caps; threshold equality, document prefiltering/limit, no/low
card vectors with document recall, deterministic exact-alternative order, late
live filtering, explicit profile/configuration failures and DTO mapping pass.
The first run exposed an invalid source-version in the new test fixture; it was
corrected to the existing 64-hex source identity contract, without changing a
migration, expected behavior or assertion. Retrieval uses the approved encyclopedia
identity. Initial variable names/defaults only were added to environment-setup.md.
Production chat/evidence integration and final provider/architecture acceptance
remain pending; no completed-feature commit is claimed.

## T0025-004 — Necessary Type Scope Before Execution

Under the user's approved necessary-type scope, add these focused implementation
paths to the prepared ticket: services/StoneStructuredModelService.java,
entities/StoneSemanticSearchExecutionEntity.java,
enums/StoneSemanticEvidenceVerdict.java and
mappers/dtos/StoneSemanticCandidateEntityToStoneSemanticEvidenceCandidateRequestMapper.java,
relative to J. They implement shared guarded transport reuse, turn-wide counters/
cache and explicit source-level verdicts established by prototype qualification;
no new client/library or HTTP route. Existing candidate domain/response/result-
mapper types may gain internal checked-evidence fields needed by 0026. These
changes are necessary type delivery, not additional product functionality.
The existing planned evidence types retain candidate/wish/source coverage through
separately packed card/type source statements, with backend aggregation and strict
scope/quote validation. Selector remains StoneSemanticEvidenceTest; controlled
providers/transport only, no real inference in this ticket.

## T0025-004 — Actual Outcome

PASS: StoneSemanticEvidenceTest, seven tests; live-row mapping recheck
StoneSemanticRetrievalTest, five tests. Twelve tests, no failures/errors.
Controlled transport/providers only, no inference. Strict complete source/wish
pairs and verbatim quotes, separate card/type authority, current-card veto,
partial/unknown aggregation, complete sentence segmentation, cache invalidation,
context/output packing, one repair and whole-turn cap, actual usage settlement
and permit retention until actual worker exit passed. Short source aliases and
48-character verbatim quote bounds keep the qualified 512-token output batches
bounded without truncating evidence. Actual storage UUID/photo are mapped from
current rows. No feature completion/commit is claimed.

## T0025-005 — Necessary Types Before Execution

Under the user's approved 0025–0029 necessary-type scope, add
entities/StoneSemanticPreparedTurnEntity.java and
mappers/entities/StoneSemanticPreparedTurnEntityToStoneChatTurnEntityMapper.java
for a domain-only prepared selection handoff and mapped completed turn;
mappers/dtos/StoneSemanticSearchCriteriaToStoneSemanticSearchStateResponseMapper.java
for mandatory prompt state; and internal evidence fields on StoneChatTurnEntity
for optional persisted checked facts. The semantic raw decision uses nullable
color/use slots qualified by T0025-002 and mapped keyed wishes. The exact
preparation API is reused after the single semantic interpretation, without a
second model call or soft-wish rejection. Existing focused selectors are retained.

T0025-005 additionally requires entities/StoneSemanticCheckedCandidateEntity.java:
compact displayed-candidate ID/wishes/checked-proof metadata avoids persisting
JPA associations, full vector projections or per-turn execution state in history.
It remains optional internal metadata and does not add HTTP fields.

T0025-005 also updates StoneChatbotControllerTest's unused search mock bean
to the new semantic delegate type so existing controller/history/transport test
contexts retain their original HTTP assertions. The new focused integration
selector reuses that native HTTP context for generated JSON/YAML and semantic
error contracts, alongside actual isolated PostgreSQL and Redis flow fixtures.

## T0025-005 — Actual Outcome

PASS: StoneSemanticChatIntegrationTest, eight tests; exact preparation and chatbot
regressions, 19 tests. Real isolated PostgreSQL/Redis, native HTTP generated
JSON/YAML, controlled inference/embeddings. Covers single interpretation, exact-only
no semantic calls, current-card priority/veto, partial/overflow counts, exact
alternatives only at semantic zero, partial late removal and exactly one shared-
vector refresh, state retention/replacement/reset, persisted compact proof, replay
without new calls/cookie, healthy local-limit history and fail-closed dependency
errors. Initial integration setup failed due to an incorrect embedding accessor,
spying a Spring transaction proxy, and restubbing a throwing/default-answer mock;
fixed only test wiring, retaining assertions. No production inference occurred.

The internal domain prepared-turn handoff avoids reverse DTO mapping and persists
only displayed IDs/wishes/proofs; the dedicated ready response API remains mapped
and available. The updated technical plan records this implementation choice.
T0025-006 final production qualification/architecture is still pending.

## T0025-006 — First Final Qualification and Necessary Correction

Final production check failed: interpretation generated an unsolicited invalid
date D2026-10-12T00:00:00Z after its one correction; the first partial-card fixture
classified absent blue information as CONTRADICTED instead of UNKNOWN. Compatible
real vectors and the complete initial-state current-input capacity bound 8045
passed. Isolated actual UTC-day ledger: six reservations, 1406 charged tokens.
No acceptance/commit or dependent feature implementation is claimed.

The architecture fixture initially used a domain-package wildcard that also
matched mappers.entities, and treated a settings Component as an application
Service. Correct its selectors to actual domain packages and Service annotations;
responsibility and transaction requirements remain unchanged.

The user's explicit necessary-type correction approval covers the required
0025 production prompt corrections: OllamaSemanticSearchProvider's compact full
JSON example/empty date groups and OllamaSemanticEvidenceProvider's explicit
absence-versus-contradiction example. This reopens only the affected T0025-004/005
provider inputs. Preserve the independent fixture expectations, profile, 8192/512,
50/120-second bounds and all counters/thresholds. Recheck changed interpretation,
evidence and prompt capacity plus corrected architecture; retain the successful
unchanged vector evidence instead of repeating its inference.

Final correction attempt 1: card partial support and shelf/mixed interpretation
now pass; capacity 8083 passes and both architecture tests pass. Generic explicit
availability still produces SEARCH rather than CLARIFY; the type-only card
classification twice cites a quote that is not acceptable verbatim evidence.
Six reservations charged 2218 actual tokens. Unchanged vectors were not repeated.

Next necessary correction reuses the existing qualified deterministic
StoneExactSearchSettings.schemaFor availability-only constraints. Its pure schema
helper and availability matcher become static (no configuration/behavior change),
so the semantic provider can add its wish grammar to that already constrained
schema while respecting inherited narrowing/wishes. Include
services/StoneExactSearchSettings.java in the necessary type scope.

Add raw production evidence diagnostics to the test-only qualification wrapper
to distinguish unsupported verdicts from malformed quotes before further fixes.
Repeat only failed semantic interpretation/evidence cases as required by changed
inputs; retain successful unchanged capacity/architecture/vector evidence.

Final correction attempt 2: all three interpretation fixtures PASS, including
forced clarification for explicit fresh availability-only. Raw evidence confirms
the remaining defect: the silent B12 card is classified SUPPORTED with quote
suitable for a shelf, borrowed from the prompt/request rather than its current
text; the generic one repair repeats it. Six reservations, 2243 charged tokens.

Necessary evidence correction removes that overlapping positive-shelf example
and clearly distinguishes a requested wish from source facts. Carry a bounded
internal validation correction through InvalidStoneSemanticSearchOutputException
and StoneSemanticEvidenceService's one allowed repair, identifying a nonverbatim
quote by supplied alias/key without exposing source text through public errors.
Exception messages remain generic; the counter, one-repair policy and strict
quote oracle are unchanged. Recheck controlled evidence and only the remaining
real evidence fixture. Successful unchanged interpretation/capacity/vector/
architecture evidence remains retained.

Evidence correction attempt 3: seven controlled evidence tests PASS, but real
silent-card classification still copies the wished phrase suitable for a shelf
as a quote (CONTRADICTED) on both original and precise correction. Three
reservations charged 975 tokens. Nothing invalid was accepted; no thresholds,
gold expectations, limits or successful feature acceptance were changed.

The observed confusion is between two text fields in the wire input: desired
wish text and supplied source text. The next necessary compact-wire correction
encodes desired wishes as a key-to-desire map, separate from the source text
objects. Shared application DTOs and output/provenance contracts remain unchanged.
This preserves all wishes/source text and reduces prompt bytes; no evidence is
skipped. Verify this wire distinction through controlled SDK assertions, then
repeat only the changed real evidence fixture.

Evidence correction attempt 4: controlled SDK/guard/evidence checks PASS. Real
wire separation removes the borrowed quote, but the silent card is incorrectly
CONTRADICTED using none, and the positive type passage is also CONTRADICTED.
Three reservations, 952 actual tokens. The independent semantic oracle correctly
rejects this result. The new request assertion initially used the wrong
MockRestServiceServer API; corrected its technical API without altering assertions.

Restore the source-specific format already qualified in T0025-002: one source
object plus the wish array and the qualified concise instructions. Do not send
redundant stone-type metadata to the classifier: backend candidate/source scope
validation owns applicability before invocation, and the actual text remains
complete. Pack wishes/meaningful source segments within the same 512 bound, with
one source per model batch. This is a compact format choice, not a scope/ranking/
resource change; all required sources are still processed with the same 20-call
cap, cache, one repair and deadline. Full multi-source arrays were not qualified
on the installed small model and are not claimed delivered. Recheck controlled
packing/transport/integration, changed real evidence, and final architecture.

Evidence correction attempt 5: controlled evidence, integration and architecture
pass (17 tests). Real source-specific classification still assigns CONTRADICTED
to both the silent card and positive type passage, using valid but irrelevant
quotes. Three reservations, 805 tokens. This remains a semantic qualification
failure and blocks completed-feature acceptance; no commit is made.

Before another production edit, reopen the test-only T0025-002 format gate in
the existing qualification class: qualify two explicit boolean questions,
supported/contradicted, mapped to the same SUPPORTED/UNKNOWN/CONTRADICTED domain
verdicts. Both true is invalid; both false is UNKNOWN/null. Use the unchanged
four-case source/wish/quote oracle, short known aliases, existing guarded
transport and unchanged resource limits. This tests whether the selected model
can reliably express the accepted semantics without conflating the verdict
labels. It does not loosen the gold set or claim final production readiness.

Binary test-only gate failed on the first partial-card fixture: absent blue is
marked contradicted=true with null quote. One reservation, 344 tokens. No binary
production format was implemented.

A final targeted diagnosis replays the exact original T0025-002 qualified
source-specific protocol: fixture instructions, full supplied source identifiers
and the original quote schema, rather than newly introduced aliases/grammar
constraints. Keep the same independent oracle and at most its original single
correction. This separates production-format differences from a failure to
reproduce the earlier model qualification. No additional query set or resource
change is authorized; if this reproduction fails, dependent delivery remains
blocked rather than claiming that the selected model has been qualified.

Original-protocol reproduction also fails: the model treats Biography: none.
as an explicit contradiction on initial and corrective calls. Three reservations,
776 tokens. This rules out newly added aliases alone as the cause.

Inspection identifies a concrete source-preparation defect: the production input
mapper invents the technical text Biography: none. for a null biography. That is
absence metadata, not a factual negative statement. Correct
StoneSemanticCandidateEntityToStoneSemanticEvidenceCandidateRequestMapper to omit
only that absent field; preserve every supplied current name/type/size/biography
fact. No actual source text or wish is truncated or reclassified by code.

Final qualification must exercise that actual production card mapper, not the
prototype's legacy textual absence sentinel. Decode the fixture's existing
explicit card fields, with its none sentinel meaning null biography, and render
through the mapper. Keep the original source/wish/quote/UNKNOWN oracle unchanged.
The final four acceptance methods remain; diagnostic-only original/binary
experiments are recorded here and removed after diagnosis, rather than adding
failed experimental protocols to the production acceptance suite. No accepted
requirement/assertion, selected model, limit or profile is removed or changed.

Actual mapped-card final evidence PASS: all four fixed cases, eight real calls
including one permitted precise quote correction, eight reservations charged
2071 tokens. Silent card remains UNKNOWN, type passage SUPPORTED; explicit card
veto and general absent blue remain correct. Controlled evidence seven tests and
isolated live/Redis/native-HTTP integration eight tests also PASS (16 together).
Original failed protocols and charges remain recorded; none were accepted.

Before final acceptance, extend existing focused integration assertions to
exercise inherited native HTTP capacity/budget/dependency codes and retry/cookie
headers, and actual deletion alongside reservation in the late eligibility
fixture. Recheck only those changed methods plus final architecture. No new
selector class, inference, requirement, dependency or feature scope is added.

## T0025-006 — Final Outcome and Feature Review

PASS: final mapped-card/source production evidence (four fixed cases, eight actual
calls including one bounded repair, 2071 tokens); final interpretation (three
fixed inputs, already passed after availability-schema reuse); compatible real
vectors and complete fresh input/required-state capacity bound 8083 retain their
unchanged passes. Final architecture: two tests, no failures. The extended native
HTTP/late-deletion methods: two tests, no failures. No unchanged successful
inference was needlessly repeated, no full backend suite was run.

Focused controlled evidence seven tests, retrieval five, integration eight,
exact preparation 16 and chatbot atomicity three passed. Changed integration
methods were rechecked as documented. Generated JSON/YAML expose the inherited
public shapes and semantic/invalid/capacity/budget/dependency outcomes; native
retry and failed-response cookie assertions pass. AC-0025-001–015 review is
recorded in acceptance.md. No broad 0027 quality/human acceptance is inferred.

Earlier failures remain historical evidence; the final format is one source
with bounded wish batches, short source aliases, complete meaningful segments,
strict verbatim 48-unit quotes and precise one-repair feedback. Missing null
biography is omitted, not turned into a negative source assertion. No model,
threshold, resource, HTTP success-field, dependency or schema change was made.
All T0025 tickets are complete; no push is authorized.
