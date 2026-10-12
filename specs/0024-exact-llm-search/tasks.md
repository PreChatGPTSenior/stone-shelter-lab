# Feature 0024 Tasks

Status: Execution tickets prepared from accepted ADR-0024; all remain unexecuted.
Documentation preparation does not authorize application implementation.

## Mandatory First Ticket: Environment Readiness

- [ ] T0024-001 — Establish and report fresh readiness for every explicitly
  requested ticket in the batch before executing any of them. Check this feature
  and prerequisite features at the endpoints actually used, pinned tools/models,
  effective configuration and focused-test prerequisites. Supply known authorized
  command-scoped values including JAVA_HOME; report secrets only as present/missing.
  Start only required existing services when authorized, preserve data and recheck.
  Record covered IDs, expected/observed components, preparation and READY/BLOCKED.
  A missing/unverified prerequisite blocks the entire affected batch. No tests,
  inference or business imports are readiness checks. No checks have run here.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [plan.md](plan.md).
Preserve T0024-001 first; its success covers only the batch recorded at execution.
For unit-only tickets check tools/sources and report all application service states;
for persistence verify Docker/PostgreSQL; for Kafka verify broker metadata;
for model qualification verify installed Ollama/models before the qualification
selector executes. Redis/MinIO/backend dependencies are required where the chosen
Spring/application context uses them. Do not infer readiness from old evidence.

## Planned Execution Sequence

- [ ] T0024-002 — Qualify structured output, usage and bounded context. Depends on T0024-001.
- [ ] T0024-003 — Implement shared admission and durable daily accounting. Depends on T0024-001 and T0024-002.
- [ ] T0024-004 — Implement validated exact interpretation and catalog execution. Depends on T0024-001 and T0024-003.
- [ ] T0024-005 — Install shared guard across embedding and background paths. Depends on T0024-001 and T0024-004.
- [ ] T0024-006 — Replace demo turn and extend shared chat availability. Depends on T0024-001 and T0024-005.
- [ ] T0024-007 — Review focused acceptance and architecture. Depends on T0024-001 and T0024-006.

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
- J/services/OllamaExactSearchProvider.java
- J/repositories/OllamaChatRepository.java
- J/criteria/StoneExactSearchCriteria.java
- J/repositories/StoneExactSearchRepository.java
- J/entities/StoneExactSearchResultEntity.java
- J/mappers/entities/StoneExactSearchResultEntityToStoneChatTurnEntityMapper.java
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
