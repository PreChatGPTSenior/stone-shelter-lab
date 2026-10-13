# Feature 0024 Tasks

Status: All T0024-001–008 tickets complete; focused acceptance/architecture passed. Final commit evidence follows.
The user explicitly authorized all tickets and one commit at the end of each feature.

## Mandatory First Ticket: Environment Readiness

- [x] T0024-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. Fresh batch checks are recorded below.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0024-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [x] T0024-008 — User-authorized bounded qualification remediation. Depends on a fresh T0024-001 check covering T0024-008 and T0024-002–007; precedes completion of T0024-002.
- [x] T0024-002 — Qualify structured output, usage and bounded context. Depends on T0024-001.
- [x] T0024-003 — Implement shared admission and durable daily accounting. Depends on T0024-001 and T0024-002.
- [x] T0024-004 — Implement validated exact interpretation and catalog execution. Depends on T0024-001 and T0024-003.
- [x] T0024-005 — Install shared guard across embedding and background paths. Depends on T0024-001 and T0024-004.
- [x] T0024-006 — Replace demo turn and extend shared chat availability. Depends on T0024-001 and T0024-005.
- [x] T0024-007 — Review focused acceptance and architecture. Depends on T0024-001 and T0024-006.

## Exact File Boundaries and Verification

Paths are repository-relative. Expand J to
stone-shelter-api/src/main/java/lab/stoneshelter, T to
stone-shelter-api/src/test/java/lab/stoneshelter and D to
stone-shelter-api/src/main/resources/db/changelog. Each named path below is an
individual allowlisted file; there are no directory/glob editing permissions.
New paths are planned artifacts, not files already implemented.

Each execution ticket may additionally update only this feature's tasks.md and
acceptance.md to record its actual status/evidence. No spec/ADR/other-feature
document edits are implicit during implementation. If another file is needed,
stop and revise the authorized ticket boundary first. Never modify applied
migrations; db.changelog-master.yaml may receive only the new named include.

### T0024-001

Writable: specs/0024-exact-llm-search/tasks.md and acceptance.md only.
Verification: read-only readiness probes and authorized routine startup described
in plan.md; no application test selector. Record corrective actions and affected
IDs on BLOCKED. Do not check any ticket complete from documentation alone.

### T0024-002: Qualify structured output, usage and bounded context

Acceptance references: AC-0024-001, AC-0024-007, AC-0024-008, AC-0024-010, AC-0024-014.

Writable:

- J/shared/StoneExactSearchDecision.java
- J/shared/StoneExactSearchFilters.java
- J/shared/StoneAdmissionDateRange.java
- J/shared/StoneExactSearchReference.java
- J/enums/StoneExactSearchAction.java
- J/enums/StoneExactSearchReferenceKind.java
- J/entities/StoneModelInvocationEntity.java
- J/entities/StoneModelResponseEntity.java
- J/services/StoneExactSearchProvider.java
- J/services/OllamaExactSearchProvider.java
- J/repositories/OllamaChatRepository.java
- J/services/StoneExactSearchSettings.java
- .env.example
- T/StoneExactSearchQualificationTest.java

Selector: `StoneExactSearchQualificationTest`.

Use only a small named set of English exact/clarification/malformed-output fixtures against installed qwen2.5:3b; prove schema/usage/prompt bounds and no hidden retries. Qualify existing Spring AI dependency behavior without adding/changing versions. Failure blocks dependent tickets; do not turn this into tuning or downloads.

### T0024-008: Bounded qualification remediation

Authorized by the user's affirmative reply to the separate remediation-scope
request. Diagnose the observed filterless classification and unmeasured maximum
input using the existing named qualification selector and installed model only.
Inspect complete decisions and actual usage/timing; correct prompt/schema and
qualification construction without weakening the required behavior. Reconcile
any necessary context/output parameter decision in the specification, plan,
acceptance criteria, ADR and central exclusion register before production use.
No model/runtime/dependency replacement, downloads, new public input restriction,
broad tuning campaign or feature 0025 implementation is authorized.

Remediation candidate under qualification: keep the schema in the API's format
parameter and use a real user/assistant clarification example instead of
duplicating the schema/example as system prose. Check a context of 8192 with
unchanged 512 output/50-second call/120-second turn limits. Prove the complete
2000-UTF-16-unit input domain using the installed qwen2/GPT2 byte-level tokenizer
profile and conservative UTF-8/ChatML bounds, not inference truncation or a
characters-per-token average. This is not yet a successful qualification or a
production setting change.

Writable: the exact T0024-002 paths, this feature's spec.md, plan.md, tasks.md,
acceptance.md, docs/adr/0024-exact-llm-search.md and
docs/out-of-scope-decisions.md. Verification: StoneExactSearchQualificationTest
only (individual methods permitted for diagnosis). Dependent implementation
remains gated on successful T0024-002 qualification.

### T0024-003: Implement shared admission and durable daily accounting

Acceptance references: AC-0024-009 through AC-0024-013.

Writable:

- D/0024-create-model-token-budget.xml
- D/db.changelog-master.yaml
- J/entities/ModelTokenReservationEntity.java
- J/entities/ModelTokenBudgetDayEntity.java
- J/enums/ModelAdmissionMode.java
- J/repositories/ModelTokenBudgetRepository.java
- J/services/ModelInvocationAdmissionService.java
- J/services/ModelTokenBudgetTransactionService.java
- J/services/ModelInvocationSettings.java
- J/exceptions/ModelCapacityExceededException.java
- J/exceptions/ModelTokenBudgetExceededException.java
- J/exceptions/ModelAdmissionDeferredException.java
- .env.example
- T/ModelInvocationAdmissionTest.java
- T/ModelTokenBudgetTest.java

Selector: `ModelInvocationAdmissionTest,ModelTokenBudgetTest`.

Verify one shared permit, immediate customer rejection/deadline-aware waiting, permit retention until actual exit, atomic reservation/settlement, original-day unknown usage and UTC rollover with isolated PostgreSQL.

### T0024-004: Implement validated exact interpretation and catalog execution

Acceptance references: AC-0024-001 through AC-0024-008.

Writable:

- J/services/StoneExactSearchService.java
- J/services/StoneExactSearchValidationService.java
- J/exceptions/InvalidStoneExactSearchOutputException.java
- J/services/OllamaExactSearchProvider.java
- J/repositories/OllamaChatRepository.java
- J/criteria/StoneExactSearchCriteria.java
- J/repositories/StoneExactSearchRepository.java
- J/entities/StoneExactSearchResultEntity.java
- J/mappers/entities/StoneExactSearchResultEntityToStoneChatTurnEntityMapper.java
- J/mappers/entities/StoneExactSearchDecisionToStoneExactSearchCriteriaMapper.java
- J/entities/StoneChatTurnEntity.java
- J/repositories/StoneChatConversationRepository.java
- T/StoneExactSearchServiceTest.java
- T/StoneExactSearchPersistenceTest.java

Selector: `StoneExactSearchServiceTest,StoneExactSearchPersistenceTest`.

Verify no filterless query, supported/unsupported groups, UTC date ambiguity/ranges, follow-up metadata/backward-readable history, trusted references/current availability, true counts/order/templates and one repair with mocked model responses.

### T0024-005: Install shared guard across embedding and background paths

Acceptance references: AC-0024-009 through AC-0024-012, AC-0022-006.

Writable:

- J/repositories/OllamaEmbeddingRepository.java
- J/services/StoneDocumentEmbeddingService.java
- J/services/StoneDocumentPreparationService.java
- J/criteria/StoneDocumentEmbeddingCriteria.java
- J/services/StoneCardEmbeddingService.java
- J/services/StoneIndexProcessingService.java
- J/services/StoneIndexConsumerService.java
- T/ModelEmbeddingAdmissionTest.java
- T/StoneDocumentEmbeddingTest.java
- T/StoneDocumentIndexLifecycleTest.java
- T/StoneCardEmbeddingServiceTest.java
- T/StoneIndexRetryTest.java

Selector: `ModelEmbeddingAdmissionTest,StoneDocumentEmbeddingTest,StoneDocumentIndexLifecycleTest,StoneCardEmbeddingServiceTest,StoneIndexRetryTest`.

Verify all actual embedding calls reserve/settle one shared budget/slot, import deadline/atomic publication, consumer waits without attempts/DLQ and unchanged selected model/profile; preserve catalog 201 behavior. These cross-feature source edits are explicitly owned by this ticket.

### T0024-006: Replace demo turn and extend shared chat availability

Acceptance references: AC-0024-005 through AC-0024-009, AC-0024-013.

Writable:

- J/controllers/StoneChatbotController.java
- J/services/StoneChatbotService.java
- J/services/StoneChatAvailabilityService.java
- J/services/StoneChatContextSelector.java
- J/handlers/ApiExceptionHandler.java
- J/exceptions/InvalidStoneExactSearchOutputException.java
- T/StoneChatbotServiceTest.java
- T/StoneChatAvailabilityServiceTest.java
- T/StoneChatbotControllerTest.java
- T/StoneChatHistoryControllerTest.java

Selector: `StoneChatbotServiceTest,StoneChatAvailabilityServiceTest,StoneChatbotControllerTest,StoneChatHistoryControllerTest`.

Verify model-backed exact turns, no pair on failure, no inference/reservation on committed replay, ordinary 503/429/502 not global outages, actual Ollama/control outage closing all chat operations and verified recovery; inspect generated chat OpenAPI.

### T0024-007: Review focused acceptance and architecture

Acceptance references: AC-0024-001 through AC-0024-014.

Writable:

- T/StoneExactSearchArchitectureTest.java

Selector: `StoneExactSearchArchitectureTest`.

Verify new types/layer/mapper conventions and review prior evidence for exact search plus embedding guard impact. Do not run a broad quality suite, real import, live reindex or feature 0025.

## Execution Boundary

Run only the selected ticket's named selector with controlled environment,
including JAVA_HOME, using ./mvnw -Dtest=<selector> test from stone-shelter-api.
No full suite, broad verify, business imports or unrequested later tickets.
Verification fixtures create/clean their own data and use isolated databases.
After the requested ticket(s), report results and stop; no commits or pushes
without explicit user authorization. No feature 0025 execution is authorized.

## Batch Readiness Evidence (2026-10-12)

### Authorized remediation readiness recheck

Covered IDs: T0024-008 and T0024-002–007. Result: READY. Fresh probes confirmed
JAVA_HOME-selected JDK 23.0.2/Maven 3.9.9, the immutable pgvector test image,
authenticated PostgreSQL 18.6/vector 0.8.6, Kafka metadata at kafka:9092,
Redis PONG, backend health UP, MinIO ready/source authenticated HEAD 200
(46347 bytes; credentials present only), Ollama 0.40.3 from host and backend,
and the unchanged selected chat/embedding model digests and native 32768 context.
All required application containers remain healthy. Frontend is stopped and
unused by the named backend selectors. No startup, inference or business
mutation occurred in this recheck. Commands explicitly selected JAVA_HOME and
the approved Docker socket/service endpoints.

User requested continuous execution of every feature 0020–0024 ticket, committing
at each completed feature before the next. Covered implementation IDs: T0020-002–T0020-005; T0021-002–T0021-004; T0022-002–T0022-005; T0023-002–T0023-005; T0024-002–T0024-007.
Result: READY for the entire requested batch's environment prerequisites.

| Component | Expected | Observed |
| --- | --- | --- |
| Java/Maven | JDK 23.0.2 via JAVA_HOME; Maven 3.9.9 | Fresh java and wrapper checks report exact selected versions. |
| Docker/database | Reachable daemon; approved immutable image; PostgreSQL 18.6/vector 0.8.6 | Docker 29.8.1 reachable; selected pgvector digest installed; authenticated read-only query at backend-network postgres:5432 succeeds and confirms 18.6/0.8.6. |
| Kafka | 4.2.2 and actual broker metadata | Existing broker started; CLI version 4.2.2; metadata from kafka:9092 succeeds; published host listener localhost:29092 reachable. |
| Redis | 8.2.10 and connectivity | Existing container started; PONG and exact server version confirmed. |
| MinIO/source | Ready server and fixed source | Ready HTTP 200; authenticated HEAD of stone-encyclopedia/stone-encyclopedia_en.md is HTTP 200, 46347 bytes. Contents not read or rewritten. Credentials checked as present only. |
| Ollama/models | Previously user-approved installed runtime/profile | 0.40.3 confirmed at host and from backend container; qwen2.5:3b and qwen3-embedding:0.6b installed with digests; metadata reports 32768 input context and embedding profile length 1024. No inference. |
| Existing SDKs | Selected existing dependencies | Spring AI Ollama 2.0.1, Kafka clients 4.2.1 and Resilience4j 2.4.0 cached; no dependency changes. |
| Backend/frontend | Actual endpoints and task impact | Existing backend started, health HTTP 200/UP; frontend remains stopped and is outside these backend/HTTP/model selectors. |

Preparation: started only existing Redis, Kafka and backend containers, preserving
volumes/data. PostgreSQL and MinIO were already running; no model/runtime update,
download, import, application test or business mutation was used as readiness.
The generic plan's historical Ollama 0.40.2 text is superseded by the user's
explicit approval of installed 0.40.3 in feature 0017 and its reuse in feature
0018's plan; this batch reuses that authorization rather than changing runtime.

Agent commands explicitly set JAVA_HOME to the installed Homebrew 23.0.2 path,
DOCKER_HOST to the Desktop socket and TESTCONTAINERS_DOCKER_SOCKET_OVERRIDE to
/var/run/docker.sock. Relevant tests use isolated databases and mocked providers,
except the explicitly named T0024-002 qualification. Known declared host/source
endpoints are supplied per command where absent from inherited configuration;
no private .env is read. Model behavior/context/usage qualification remains work
for T0024-002, not an inference-based readiness check. Future feature prerequisites
within this batch are produced by its earlier completed tickets; external resource
readiness covers the whole batch and is rechecked when moving between features.

### Feature 0024 readiness recheck

External environment prerequisites for T0024-002–007: READY. Actual backend/DB,
Kafka metadata, Redis PONG, healthy MinIO and authenticated source HEAD
(HTTP 200, 46347 bytes), Ollama 0.40.3/backend-reachable endpoint, installed
model inventory/digests and qwen2.5 native context 32768 freshly confirmed.
Frontend remains stopped and is unused. JDK 23.0.2 and explicit service endpoints
were supplied to the agent commands. These probes performed no inference.

### Historical T0024-002 qualification result: BLOCKED (superseded below)

The named StoneExactSearchQualificationTest ran against installed qwen2.5:3b
through the existing Spring AI 2.0.1 integration; no dependencies, model versions,
runtime settings, input limits or persistent configuration were changed.
The last complete selector result: 4 cases, 1 failure and 1 error (2 passing).

- Basic exact request produced SEARCH/BASALT/SMALL with actual reported usage
  (659 input, 48 output tokens in the final run).
- The explicit all-available request produced SEARCH with no clarification
  questions; one corrective call still produced SEARCH. The assertion requires
  CLARIFY and at least two questions and was not weakened.
- A deliberately one-token output was malformed while retaining input/output
  usage, confirming that invalid output still needs accounting.
- The existing 2000-character HTTP input boundary was tested with an English
  command and a quoted Unicode note. Diagnostic counting in the installed
  native 32768 window timed out after the selected 50-second limit. Its actual
  token count is unknown; this is not evidence that it fits or exceeds 4096.
  Full system/current-input/output fit for the permitted domain is unproven.
- A subsequent isolated method check of the same permitted qualification class
  passed: one mock HTTP request has the correct system/user roles, model,
  stream=false, num_ctx=4096, num_predict=512 and schema allowing CLARIFY.
  HTTP 500 causes no hidden retry. This excludes the tested SDK wire-settings
  mismatch; it does not resolve the live classification/context failures.

Earlier fixture-only reflection/mock-server errors were corrected before the
reported complete selector. No further inference/tuning loop was run after
the qualification failure and focused wire-contract diagnosis.

At that point T0024-002 remained unchecked and T0024-003–007 were blocked by the explicit plan gate:
"If the selected integration cannot establish the bound or structured contract,
block dependent implementation and report the concrete qualification failure."
No guard/ledger/exact-search/chat integration is claimed implemented; the
accepted 0022 shared-admission retrofit consequently remains pending.
The user subsequently authorized T0024-008; the historical continuation requirement was an explicitly agreed bounded remediation scope; any
change to the accepted model/context/deadline/input contract must first update
the authorized feature decisions/specification and file boundaries. Do not
silently raise limits, replace models, narrow input or bypass this gate.

### T0024-008 remediation result: PASSED

The final permitted qualification selector ran 4 cases with 0 failures/errors
against the unchanged installed model and SDK. Exact input returned
SEARCH/BASALT/SMALL (309 input/47 output); explicit all-available input returned
CLARIFY with two size/date questions and empty type/size/date arrays
(308 input/74 output). A one-token malformed response retains usage. The mock
HTTP 500 receives exactly one correctly configured SDK request.

The complete permitted current-input domain, system/example/ChatML content and
bounded corrective instruction have an input upper bound of 7639; the 512 output
reservation gives 8151, within the newly documented 8192 context. This is a
conservative byte-level bound, not measured token usage or an inference-speed
claim. The installed GPT2/qwen2 profile and exact ChatML template SHA-256
(eb4402837c7829a690fa845de4d7f3fd842c2adee476d5341da8a46ea9255175)
are checked before relying on it. Preserve full current input, trim only whole
old pairs and keep a maximum 200-byte corrective-instruction allowance.

Actual dynamic UTC dates, selected defaults and availability-only schema policy
live in StoneExactSearchSettings; the qualification uses that production policy.
The feature specification, plan, ADR, acceptance document, environment example
and OOS-0024-005 register agree on 8192/512, with unchanged 50s/120s deadlines.
No model/runtime/dependency replacement, downloads, input restriction, deployed
service change, commit or push occurred during this bounded remediation.

T0024-008 is complete. The former functional qualification blockers are resolved.
T0024-002 remains unchecked because its provider/DTO/repository artifacts are
still planned; the admission ledger, exact-search execution, embedding retrofit
and chat integration are not implemented or accepted by these four checks.

### Continuous completion readiness recheck

Covered batch: T0024-002–007, authorized for continuous completion and one final
feature commit. READY: JAVA_HOME-selected JDK 23.0.2/Maven 3.9.9; existing healthy
backend/PostgreSQL/MinIO/Redis/Kafka; backend health UP; authenticated PostgreSQL
18.6/vector 0.8.6; Kafka metadata at kafka:9092; Redis PONG; MinIO ready and
signed source HEAD 200/46347 bytes (credentials present, never printed); Ollama
0.40.3 at host and actual backend endpoint; unchanged model digests and native
32768 context, embedding dimension 1024, qualified chat template fingerprint.
Pinned SDKs and immutable pgvector Testcontainers image are cached. Frontend is
stopped and unused by these selectors. No startup, inference, import or business
mutation was used as readiness. Commands supply JAVA_HOME, Docker socket and
approved service endpoints explicitly. Historical qualification is not treated
as a fresh readiness probe.

T0024-002 complete: 5 qualification checks pass, including the actual repository no-retry wire path and provider whole-pair trimming/current-input preservation. Provider/DTO artifacts are present; shared admission/deadline lifecycle is installed by subsequent tickets before final acceptance.

T0024-003 complete: 9 focused admission/ledger checks passed using isolated PostgreSQL and the new Liquibase migration. T0024-004 also owns the already-planned invalid-output exception because validation needs it before chat integration; feature behavior/scope is unchanged.

T0024-004: 16 focused service/SQL/history checks passed; actual shared-guard provider whole-pair check also passed after the retrofit. Ledger failure notifications are wired for shared fail-closed availability in T0024-006.

T0024-006 also owns StoneChatbotController.java solely to synchronize generated OpenAPI annotations with the already specified model-backed operation and HTTP 502/503/429 outcomes. No HTTP route or successful DTO change is introduced.

T0024-005 complete: 50 focused checks passed (45 in the first run; the isolated consumer fixture was corrected to register its mocked required guard, then its 5 checks passed). No assertion or production behavior was weakened.

T0024-006: 29 focused checks passed, including generated JSON/YAML OpenAPI with 502/503/429 and unchanged successful DTOs. Final focused acceptance adds actual provider timeout/lease lifecycle, shared failure-listener and failed-turn persistence checks before the feature commit.

Final T0024-004 review moves structured decision-to-domain conversion into its dedicated named mapper, as required by project conventions. The added mapper path is part of the same exact-search behavior; no requirement/dependency change. Full enum groups alone are not narrowing conditions.

## Final Delivery Evidence (2026-10-13)

T0024-002: 7 distinct qualification checks passed (the final full five-case
selector plus the subsequent native body-timeout and actual provider/usage cases).
T0024-003: final selector 10/10 passed.
T0024-004: final selector 20/20 passed.
T0024-005: 50/50 distinct checks passed; the two Spring integration contexts were
rechecked after chat recovery/dependency wiring changes and passed 14/14.
T0024-006: final selector 32/32 passed.
T0024-007: focused architecture verification and acceptance review; no broad suite.

Source paths remain within the exact ticket allowlists, including the documented
mandatory criteria mapper and generated-contract annotation additions. All new
Java artifacts are in module source roots. New Liquibase changeset only; the
master receives its include and no applied changeset is modified. Model callbacks
retain shared permits until actual execution exits; unknown usage is never
blindly refunded. Native Spring JDK HTTP timeout covers headers/body, and the
complete turn processing/commit stage has a bounded deadline. Short ledger/read
transactions have explicit SQL timeouts.

Model quality/tuning and all after-0029 tooling/wording checkpoints remain outside
scope with unchanged IDs. No feature 0025, push or live deployment is performed.
The user authorized one final feature commit after successful verification.

T0024-007 complete: 4/4 architecture checks passed with both required mappers and the final deadline/transaction implementation. All AC-0024-001–014 reviewed against focused evidence in acceptance.md; total 123 distinct checks passed.
