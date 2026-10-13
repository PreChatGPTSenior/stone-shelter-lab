# Feature 0025 Tasks

Status: Prepared implementation tickets, all unexecuted. Documentation preparation
does not authorize executing any ticket, model call, application test, commit or push.

- [ ] T0025-001 — Establish and report fresh environment readiness for every
  ticket in the explicitly requested batch; record covered IDs and block the
  batch unless every required prerequisite passes.
- [ ] T0025-002 — Qualify compact semantic interpretation/evidence formats,
  actual usage and bounded context/output with the installed selected models.
  Depends on T0025-001; failure blocks dependent application implementation.
- [ ] T0025-003 — Implement settings, domain/shared retrieval types and bounded
  live-filtered card/document queries. Depends on T0025-001 and T0025-002.
- [ ] T0025-004 — Implement structured evidence validation, guarded batch calls
  and response mapping. Depends on T0025-001 and T0025-003.
- [ ] T0025-005 — Integrate semantic interpretation, candidate orchestration,
  exact alternatives, single refresh, history metadata and HTTP error mapping.
  Depends on T0025-001 and T0025-004.
- [ ] T0025-006 — Requalify final production providers and review focused
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

T0025-001: NOT EXECUTED; no batch requested or current readiness claimed.
T0025-002–006: NOT EXECUTED; no implementation/model/test evidence.
All checkboxes remain unchecked. Documentation validation alone does not change
these statuses. Record one-line outcomes after each separately authorized task
and stop at the requested batch boundary.
