# Feature 0027 Tasks

Status: T0027-001/002/004/007 complete; 20 scenario identities recorded; 27/28 original turns submitted, 26 fully retained.
The baseline is incomplete after outages and a collector assertion failure; the
approved 200000 test quota removes the budget blocker; the remaining phase and
missing original evidence are still unresolved. Failed
ordinary answers remain failures.
Predecessors 0025/0026 are delivered. Continuous 0025–0029 execution and
whole-feature commits are authorized; user review is deferred; current assessment is autonomous.

- [x] T0027-001 — Establish/report readiness for the entire explicitly requested
  batch and required delivered search/RAG capabilities.
- [x] T0027-002 — Prepare approved fixture transcription and isolated test-only
  harness; verify without live inference. Depends on T0027-001.
- [ ] T0027-003 — Prepare compatible isolated embeddings and collect the original
  real-model baseline once. Depends on T0027-001/002.
- [x] T0027-004 — Record autonomous fixed-set answer assessment and original
  scoring without user input. Depends on T0027-001 and retained T0027-003 cohort
  evidence; incremental assessment does not bypass overall completeness.
- [ ] T0027-005 — Diagnose failed/ambiguous baselines with bounded explicitly
  selected repetitions and correction proposals. Conditional; depends on
  T0027-001/003 and relevant review evidence from T0027-004.
- [ ] T0027-006 — Review scope, acceptance and evidence; record final status.
  Depends on T0027-001/003/004 and any requested T0027-005 evidence.

- [x] T0027-007 — Configure the approved doubled test daily quota and classify
  token-quota interruptions as incomplete; verify controlled behavior. Depends on
  T0027-001/002. Exact paths: StoneSearchQueryQualificationTest.java, feature
  spec/acceptance/plan/tasks/reports, ADR-0027 and environment-setup.md; owned
  temporary launcher may use the same supplied environment setting.

## Exact Scope and Path Notation

Read constitution/local instructions, [spec.md](spec.md), [acceptance.md](acceptance.md),
[plan.md](plan.md) and [ADR-0027](../../docs/adr/0027-llm-search-query-qualification.md).
Before Java work read backend AGENTS_CODE_CONVENTION.md. Execute only requested
tickets; dependency ordering does not authorize subsequent tickets or missing
0025/0026 work. Do not automatically diagnose after a baseline failure.

Exact paths below are relative to repository root:

- T = stone-shelter-api/src/test/java/lab/stoneshelter
- R = stone-shelter-api/src/test/resources
- F = specs/0027-llm-search-query-qualification

Every ticket may additionally update only F/tasks.md, F/acceptance.md and
docs/adr/0027-llm-search-query-qualification.md for factual execution evidence.
This common allowlist cannot change approved requirements/oracles/exclusions.
Production source and unlisted files remain read-only. No wildcard write scope,
dependency/framework configuration, migration, frontend or root README.md edits;
no engineering-log access. Future names are not files to create now.

## T0027-001 — Requested-Batch Readiness

Writable: common evidence documentation only.
Verification: targeted read-only version/configuration/health/connectivity checks;
routine required existing-service startup is separately reported preparation.
No inference, application tests, fixture provisioning, business imports, downloads
or persistent configuration changes as readiness actions.

Check every selected ID's requirements: Java selected by command-scoped
JAVA_HOME/Maven, installed models/profiles, effective controls/endpoints, isolated
catalog/document/history target and actual usage ledger. Verify delivered required
0025/0026 seams/contracts and existing test support. Later standalone tickets
require predecessor evidence; within a selected sequential batch identify producer
tickets and enforce their completion before consumers. Do not claim future
artifacts already exist.

Report covered full IDs, expected/observed states, preparation versus checks,
READY/BLOCKED, affected tickets and corrections. Report application service states
including stopped backend/frontend with impact; start only needed existing
services and recheck connectivity. Missing implementation remains blocked rather
than being executed here. Supply known authorized values in the agent-controlled
environment; secrets are present/missing only. Preserve real usage/admission state.

## T0027-002 — Fixture Transcription and Test-Only Harness

Writable, in addition to common evidence documentation:

- T/StoneSearchQueryQualificationTest.java
- T/StoneSearchQualificationFixture.java
- R/search-query-qualification.json

Selector: `StoneSearchQueryQualificationTest#verifyFixtureAndHarness`.

Transcribe only approved acceptance fixture/scenario definitions. Add isolated
setup/cleanup, generated-ID mapping, independent automatic checks and report
serialization without production changes. Prepare controlled race/failure seams
and separate fixture calendar handling while keeping actual accounting/deadlines.
Do not clone the search implementation into expected gold.

The focused harness check verifies transcription, case/turn/cohort completeness,
isolation and report classification with controlled doubles and no live inference.
It cannot establish semantic/explanation quality. Use only isolated local test
fixtures, no import/mutation of user data. An unavailable required seam is a
reported blocker with a separately agreed fix, not permission to widen source scope.

## T0027-003 — Real-Model Original Baseline

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Selector: `StoneSearchQueryQualificationTest#qualifyApprovedBaseline`.
Test/harness/resources from T0027-002 are read-only for this execution ticket.

Verify isolated targets, prepare approved embeddings through existing guarded
capabilities and collect one original attempt per approved scenario. Natural
queries use installed models; designated controlled cases use test-owned seams.
Reset catalog/history per scenario while preserving compatible vectors/real usage.

Retain every case/turn, outcome, effective configuration/profile, usage, stage
evidence, displayed reply and automatic verdict; mark human review pending.
Outages/exhaustion/unexercised cases produce incomplete status, not smaller
denominators, overridden controls, deleted usage or automatic retries. Preserve
partial evidence before stopping; do not repeat unchanged successful cases.

## T0027-004 — Autonomous Fixed-Output Assessment and Scoring

Writable: F/qualification-results.json, F/qualification-report.md and common
evidence documentation. Necessary test-only writer status changes are authorized
in StoneSearchQueryQualificationTest.java under the existing type-scope approval.

Verification: inspect retained original evidence and record independent automatic/
agent assessments against acceptance.md; no user verdict or model call required.
Label reviewer/date/reasons, preserve original outputs/cohorts/scores and
template/budget/incomplete distinctions. Inspect the retained A cohort
incrementally; overall collection completeness remains a T0027-003/006 gate.
Do not equate production grounding with independent assessment or invent human
approval. User review is deferred under OOS-0027-005 and is not a stop condition.
Focused writer-policy selector: StoneSearchQueryQualificationTest#verifyAutonomousReviewPolicy.

## T0027-005 — Conditional Bounded Diagnosis

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Selector: `StoneSearchQueryQualificationTest#diagnoseApprovedCases`, with explicit
failed/ambiguous Q027 case IDs and hypotheses in the agent-controlled test
environment. The harness rejects absent/unapproved IDs, successful-case reruns
and attempts beyond the accepted bound. Test/resource code remains read-only.

Inspect retained stages before choosing a hypothesis. If explicitly requested,
repeat only selected failed/ambiguous cases from original state with unchanged
profiles/settings within the accepted bound. Preserve all attempts/original scores
and actual controls/usage; classify interruptions honestly.

Evidence-supported correction proposals state behavior, exact files, spec/
acceptance implications and focused checks for separate agreement. No production
fix, alternate-threshold trial/adoption, model replacement, gold relaxation or
benchmark is authorized by this ticket.

## T0027-006 — Final Evidence and Acceptance Review

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Verification: focused read-only artifact/scope/acceptance review; no application
test, full suite or repeated baseline.

Compare actual evidence with acceptance.md and exact selected scope. Record
pass/fail/review-pending/incomplete and profile/configuration limitations, never
unobserved readiness or behavior. Keep separately agreed correction work pending
rather than implementing it. Explain conditional diagnostics omitted as unnecessary
or unrequested. Distinguish fixed qualification from personal manual acceptance.

Stop after the requested ticket/batch. No commit, push, extra ticket or 0028 work.

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


## T0027-002 — Actual Outcome

PASS: final verifyFixtureAndHarness selector, one test, no inference. Approved
15 cards / seven passages / 20 scenarios / 28 turns / R=9 / A=12 transcribed
from acceptance tables. Actual isolated PostgreSQL 18.6/vector 0.8.6 schema uses
existing Liquibase migrations; generated IDs and every literal card field, owned
reservation/deletion seams, ledger non-modification and fixture cleanup verified.
Independent allowed-order/priority/negative candidate oracles are exercised.

The baseline collector retains literal inputs, current criteria/state/proofs,
retrieval scores/provenance, complete private model inputs/outputs and actual
usage. Reports distinguish original outcomes, templates, controlled hooks and
pending human review. Conditional diagnostic validation rejects unknown/successful
cases and more than two explicitly selected hypothesis repetitions; unchanged
real vectors can be restored from original report evidence without re-embedding.
The actual ledger volume survives cleanup. No production correction, dependency,
threshold/model/resource change or model call in this ticket.

Proceed once with qualifyApprovedBaseline under explicit original-baseline mode,
JDK/endpoints and owned target credentials. Never rerun/replace original evidence.

## T0027-003 — Actual Incomplete Outcome

The original Q027-001 attempt has correct exact count/order, then fails before
answer generation, saving no pair. All 22 fixture embeddings are real; 24 total
reservations / 974 charged tokens survive cleanup. INCOMPLETE_CONTROL_OUTAGE,
with unchanged 20/28/9/12 denominators; collector test success is not qualification
success. T0027-003–006 remain incomplete. The producer/repository full-profile
mismatch and fixture inconsistency are proposed for separate correction agreement
in ADR-0027. Original evidence is retained; no production correction, threshold,
model, resource or gold change and no completed-feature 0027 commit.


## Current Ticket Evidence — 2026-10-14 Local Date

T0027-002 follow-up: named verifyClosedOperationClassification and
verifyFixtureAndHarness PASS, two tests, zero failures/errors. The collector
records a false following-history closure as a hard failure and preserves outer
AssertionError before rethrowing. Approved LARGE transcription is corrected,
with both genuine wrong-size and date/reference negative checks retained. No
model call, original rerun, application source/gold/threshold/resource change.

T0027-003 INCOMPLETE/BLOCKED: 20 scenario identities, 27 original submissions,
26 fully retained turn outcomes. Original Q027-020 turn 1 fails its following-
history assertion with unavailable response/stage payload; never repeat it as an
original. Its second independent phase is unattempted. UTC 2026-10-13 ledger is
68958/100000; remaining 31042 cannot reserve the required 32768 query embedding.
Retain all usage. Resume only with fresh actual budget/day readiness and an
explicit independent-phase continuation, never generic first-turn replay.

T0027-004 PENDING: actual user verdicts for displayed Q027-019 generated replies.
Question/answers pending are recorded in ADR-0027. Templates and failed required
turns already prevent every ordinary answer scenario from passing; no fabricated
review or reduced denominator. Original R=2/9; existing hard failures remain.

T0027-005 PARTIAL: agreed profile correction/SQL checks and failed diagnostic
retained. Concrete date/status/grounding correction patch is reviewable and
awaits separate agreement; no production edit or extra model repetition.

T0027-006 INCOMPLETE: honest artifact/acceptance review retained in the report/ADR.
No whole-feature 0027 commit/push or completed checkboxes from partial evidence.
Feature 0030 and later personal manual application acceptance remain out of scope.


## T0027-007 Requested Readiness — 2026-10-14

Explicit current request: configurable daily quota, doubled to 200000 for test
runs, token exhaustion classified as verification incomplete. Covered ticket
T0027-007 including controlled test execution; no natural-query repetition.
Existing first readiness ticket T0027-001 retains first position and ID.
Fresh read-only readiness READY: Java selected by explicit JAVA_HOME is
23.0.2/Maven 3.9.9; Docker reachable; owned isolated PostgreSQL 18.6/vector 0.8.6
authenticated, zero cards/indexes; actual ledger retains 68958 on UTC 2026-10-13.
Approved test setting 200000 leaves 131042, above 32768 admission reserve. Redis
PONG; main API health UP and API/mock frontend HTTP 200; Ollama 0.40.3 and both
unchanged digests installed. Main MinIO/Kafka/PostgreSQL/Redis remain healthy.
Original Compose API is stopped and replaced by the running batch API; temporary
8082/5175 test services are stopped after earlier cleanup, with no ticket impact.
No startup, inference, import, reset or application test was a readiness check.
Known endpoints/JDK/budget are supplied command-scoped; credentials present only.
Independent pending correction/review/form questions are not approved here.


T0027-007 complete: MODEL_DAILY_TOKEN_BUDGET controls qualification, default
200000; temporary launcher preserves explicit overrides. Effective new limits
are recorded separately from historical snapshots. Direct/wrapped quota and
private quota-caused selection templates are INCOMPLETE_BUDGET_EXHAUSTED;
baseline/diagnostic loops stop without automatic retry or quality re-scoring.
verifyTokenBudgetConfiguration + verifyQuotaInterruptionClassification PASS:
two tests, zero failures/errors, no inference. Initial compilation detected one
missing import; corrected before the successful focused run. Actual ledger remains
68958, owned fixture zero cards/indexes; diff check PASS. AC-0027-016/017 PASS
through controlled evidence. No additional source/model/gold correction, original
repetition, human verdict, mock submission or partial whole-feature commit.


## D0027-013 Current Readiness and Assessment Scope

Covered T0027-004, necessary T0027-002 writer follow-up and T0027-006 factual
review; no original-model repetition. T0027-001 remains the first ticket.
Fresh readiness READY as recorded in ADR-0027: explicit JDK/Maven, owned
PostgreSQL/vector/Redis, main API/frontend and installed approved model metadata
verified; 68958 actual tokens preserved under current 200000 test setting.
User verdicts are no longer required; preserve task IDs and existing quality
targets/oracles. Earlier human-pending execution notes are historical.


T0027-004 current autonomous assessment COMPLETE under D0027-013. Retained
original evidence covers all 12 A scenarios sufficiently to record their failures
without user input: 0/12 original A, 2/9 original R; targets/denominators unchanged.
Individual Q027-019 generated turn 1 passes the agent rubric; turns 3/5 fail
required content/reference/date behavior, with reasons/reviewer in JSON. Historical
human-pending flags remain preserved separately; current human review is deferred.
Overall T0027-003/005/006 remain open for collection gaps, agreed diagnosis and
final review; no completed-feature acceptance is inferred from task 004.
verifyAutonomousReviewPolicy PASS: one controlled test, zero failures/errors,
no model call or original repeat. Human review cannot block future harness status.
