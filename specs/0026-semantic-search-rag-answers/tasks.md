# Feature 0026 Tasks

Status: Prepared tickets, all unexecuted. Documentation preparation does not
authorize ticket execution, model calls, application tests, commits or pushes.

- [ ] T0026-001 — Establish/report fresh readiness for every ticket in the
  explicitly requested batch, including delivered 0025 contracts where required.
- [ ] T0026-002 — Qualify routing/generation/checking formats, usage and 8192/1024
  context/output packing with installed models. Depends on T0026-001.
- [ ] T0026-003 — Implement settings, execution state, shared contracts,
  knowledge retrieval and decision-driven 0025 reuse. Depends on T0026-001/002
  and delivered required 0025 contracts.
- [ ] T0026-004 — Implement guarded answer generation, structured grounding,
  bounded correction and ready response mapping. Depends on T0026-001/003.
- [ ] T0026-005 — Integrate both/mixed modes, templates, final eligibility,
  selection references, safe current-turn completion and HTTP/history behavior.
  Depends on T0026-001/004.
- [ ] T0026-006 — Requalify final providers and review focused architecture/
  acceptance evidence. Depends on T0026-001/005.

## Exact Scope and Path Notation

Read constitution/local instructions, [spec.md](spec.md), [acceptance.md](acceptance.md),
[plan.md](plan.md) and [ADR-0026](../../docs/adr/0026-semantic-search-rag-answers.md).
Before Java work read backend AGENTS_CODE_CONVENTION.md. Execute only explicitly
requested tickets; readiness does not authorize later tickets or missing 0025 work.

Exact paths below are relative to repository root:

- J = stone-shelter-api/src/main/java/lab/stoneshelter
- T = stone-shelter-api/src/test/java/lab/stoneshelter
- R = stone-shelter-api/src/test/resources
- F = specs/0026-semantic-search-rag-answers

Every ticket may additionally update only F/tasks.md, F/acceptance.md and
docs/adr/0026-semantic-search-rag-answers.md for factual execution evidence.
This common documentation allowlist does not permit changing requirements or
accepted boundaries. Unlisted files remain read-only; no wildcard scope, dependency/
framework configuration, migration or frontend edits. Never modify root README.md.
New names are future artifacts, not files to create before readiness/execution.

## T0026-001 — Requested-Batch Readiness

Writable: common evidence documentation only. No application edits.
Permitted verification: targeted read-only runtime/configuration/health/connectivity
checks and separately reported routine startup of required existing services.
No inference, application test, business import, host download or persistent setup.

Check every selected ID's plan/file/test prerequisites, command-scoped JAVA_HOME
and actual required DB/vector/ledger/Redis/model endpoints. For integration batches,
verify delivered 0025 classes/contract signatures and prior focused evidence;
documents alone do not establish their availability. Report version/profile/
configuration mismatches and absent secrets only as present/missing.

Record covered IDs, expected/observed components, preparation versus read-only
checks, READY/BLOCKED, affected IDs and concrete corrections. Report backend/
frontend and other project service states with impact. Prepare permitted existing
services independently and recheck; block the whole affected batch if a required
component/contract is absent, unhealthy or unverifiable. Do not execute 0025
tickets, imports or model inference as a readiness workaround.

## T0026-002 — Basic Model and Packing Qualification

Writable, in addition to common evidence documentation:

- T/StoneRagAnswerQualificationTest.java
- R/rag-answer-qualification.json

Selector: `StoneRagAnswerQualificationTest`.

Use a small fixed English test-only prototype with existing guarded transport,
installed qwen2.5:3b/qwen3-embedding:0.6b and isolated test data. Qualify routing,
intro/per-stone and knowledge schemas, hidden provenance, partial-wish/card
precedence, final-check structured verdicts, usage and 8192 context with 1024
generation/512 checking allocations. Establish fresh current-input capacity and
explicit mandatory-state/source packing failures, without truncation or guessed
average tokens. No production integration/provider readiness is claimed by prototypes.

No production source edit, model download, production source import/catalog
mutation, tuning, broad query evaluation or timing benchmark. Failed qualification
blocks dependent implementation; report the cause without changing accepted limits.

## T0026-003 — Settings, Shared State and Knowledge Retrieval

Writable, in addition to common evidence documentation:

- J/services/StoneRagSettings.java
- J/services/StoneRagKnowledgeService.java
- J/services/StoneChatReferenceService.java
- J/services/StoneSemanticSearchService.java
- J/services/StoneSemanticCandidateService.java
- J/services/StoneSemanticEvidenceService.java
- J/criteria/StoneRagAnswerCriteria.java
- J/criteria/StoneRagKnowledgeCriteria.java
- J/entities/StoneRagExecutionEntity.java
- J/entities/StoneRagKnowledgeResultEntity.java
- J/entities/StoneRagAnswerResultEntity.java
- J/entities/StoneRagPreparedTurnEntity.java
- J/entities/StoneRagTurnStateEntity.java
- J/enums/StoneRagMode.java
- J/enums/StoneRagCompletionKind.java
- J/repositories/StoneRagKnowledgeRepository.java
- J/shared/StoneRagDecision.java
- J/shared/StoneRagKnowledgeRequest.java
- J/shared/StoneRagKnowledgeResponse.java
- J/shared/StoneRagGenerationRequest.java
- J/shared/StoneRagCandidateRequest.java
- J/shared/StoneRagSourceRequest.java
- J/shared/StoneRagGeneratedAnswerResponse.java
- J/shared/StoneRagStoneExplanationResponse.java
- J/shared/StoneRagAnswerResponse.java
- J/mappers/entities/StoneRagDecisionToStoneRagAnswerCriteriaMapper.java
- J/mappers/dtos/StoneRagKnowledgeResultEntityToStoneRagKnowledgeResponseMapper.java
- J/mappers/dtos/StoneRagAnswerCriteriaToStoneRagGenerationRequestMapper.java
- T/StoneRagKnowledgeTest.java
- environment-setup.md

Selector: `StoneRagKnowledgeTest`.

Define bounded contracts and caller-shared execution-state/validated-decision
overloads while preserving standalone 0025 APIs. Implement current knowledge
reference lookup, compatible bounded passage reuse, query cache and counter
contracts with synthetic vectors/controlled providers. Repository owns queries,
services own normalization and mappers produce ready DTOs. Do not start an
unfiltered specimen selection for a knowledge topic. Document new environment
variable names/defaults only; persist no local values or source import.

0025 source paths above are required delivered dependencies. If their agreed
contracts are absent/different, report the scope prerequisite; do not create an
unrequested implementation of 0025 inside this ticket.

## T0026-004 — Generation, Grounding and Bounded Repair

Writable, in addition to common evidence documentation:

- J/services/StoneRagAnswerService.java
- J/services/StoneRagAnswerProvider.java
- J/services/OllamaRagAnswerProvider.java
- J/services/StoneRagAnswerValidationService.java
- J/services/StoneRagGroundingService.java
- J/services/StoneRagGroundingProvider.java
- J/services/OllamaRagGroundingProvider.java
- J/shared/StoneRagGroundingRequest.java
- J/shared/StoneRagGroundingResponse.java
- J/entities/StoneRagGroundingResultEntity.java
- J/mappers/dtos/StoneRagGroundingResultEntityToStoneRagGroundingResponseMapper.java
- J/mappers/dtos/StoneRagAnswerResultEntityToStoneRagAnswerResponseMapper.java
- J/exceptions/InvalidStoneRagOutputException.java
- J/exceptions/StoneRagAnswerLimitExceededException.java
- T/StoneRagAnswerTest.java

Selector: `StoneRagAnswerTest`.

Reuse guarded transport/profile/settings, implement compact generation and
structured finished-text checking, strict IDs/order/coverage and source invisibility.
Enforce context/output, separate generation/shared-check counters, one correction,
remaining-time/50s bounds, reliable accounting and actual-exit cancellation.
Controlled provider fixtures exercise attribution/claim failures and safe ready
result/template decisions; no real inference or production data in this ticket.
Do not create another model client, background queue or unchecked success path.

## T0026-005 — Conversation, Safe Template Completion and HTTP

Writable, in addition to common evidence documentation:

- J/services/StoneRagConversationService.java
- J/services/StoneRagRoutingProvider.java
- J/services/OllamaRagRoutingProvider.java
- J/services/StoneRagDecisionValidationService.java
- J/services/StoneChatbotService.java
- J/services/StoneChatAvailabilityService.java
- J/services/StoneChatReferenceService.java
- J/services/StoneExactSearchService.java
- J/services/StoneSemanticSearchService.java
- J/entities/StoneChatTurnEntity.java
- J/repositories/StoneChatConversationRepository.java
- J/mappers/entities/StoneRagAnswerResponseToStoneChatTurnEntityMapper.java
- J/handlers/ApiExceptionHandler.java
- J/controllers/StoneChatbotController.java
- T/StoneRagChatIntegrationTest.java
- T/StoneChatbotServiceTest.java
- T/StoneChatAvailabilityServiceTest.java

Selectors: `StoneRagChatIntegrationTest,StoneChatbotServiceTest,StoneChatAvailabilityServiceTest`.

Wire one interpretation, selection/knowledge/mixed flows, template fallback and
late eligibility under shared counters. Add optional route/reference metadata;
knowledge convenience links must not replace the latest actual selection for
ordinals. Preserve effective wishes, history atomicity/replay and existing success
DTO shapes. Update exact/semantic reference delegation without changing filter rules.

Implement the trusted current-turn template exception while keeping new requests/
history/replay closed after model outage. Validate healthy ownership/control/
ledger/fencing and original deadline at commit/response, without global recovery
or bypass parameters. Failed selection, unsafe/uncertain history and expired
workers cannot be salvaged. Use isolated PostgreSQL/Redis and controlled failure/
clock/model-worker fixtures; no real inference. Preserve existing assertions
while updating dependency wiring for the new delegate.

Translate specified errors and inspect generated JSON/YAML OpenAPI in integration.
Controller source edits are annotation/contract descriptions only; no business
logic/mapping or new public evidence fields. No frontend or handwritten HTTP contract.

## T0026-006 — Final Provider Qualification and Acceptance Review

Writable, in addition to common evidence documentation:

- T/StoneRagAnswerQualificationTest.java
- T/StoneRagAnswerArchitectureTest.java

Selectors: `StoneRagAnswerQualificationTest,StoneRagAnswerArchitectureTest`.

Recheck the fixed functional fixture through final production routing/generation/
grounding providers and final prompt/usage/context bounds. This repeat is justified
by changed prototype-to-production inputs, not permission for a broad query set.
Verify responsibility/mappers/DTO/protocol/JPA/record boundaries and review
AC-0026-001–015 against prior focused/generated-contract evidence.

No production edit in this review ticket. A defect requiring changes is a
reported correction-scope blocker; do not loosen tests, add an unrequested fix,
run a full suite, import production sources or claim acceptance while blocked.

## Execution Evidence

T0026-001: NOT EXECUTED; no requested batch/current readiness result.
T0026-002–006: NOT EXECUTED; no implementation/model/test evidence.
All tasks remain unchecked. When requested, run only the exact selector from
stone-shelter-api using ./mvnw -Dtest=<selector> test with explicit authorized
JAVA_HOME/endpoints, report one-line outcomes and stop at the requested boundary.
