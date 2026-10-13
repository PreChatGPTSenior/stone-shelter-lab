# Feature 0028 Tasks

Status: Prepared, all tickets unexecuted. Documentation approval does not authorize
source changes, tests, service setup, models, commits or pushes.

- [ ] T0028-001 — Establish/report readiness for the entire explicitly requested
  batch, including delivered search/RAG dependencies where required.
- [ ] T0028-002 — Qualify a test-only real HTTP/Servlet transport prototype and
  actual bounded-resource cleanup. Depends on T0028-001.
- [ ] T0028-003 — Implement stream settings/admission/state and mapped shared
  response types. Depends on T0028-001/002.
- [ ] T0028-004 — Integrate shared turn admission/execution, confirmed saved-result
  readiness and the narrow cookie/history amendment. Depends on T0028-001/003
  and delivered required 0025/0026 behavior.
- [ ] T0028-005 — Add MVC route/handler/framing, shared public error mapping and
  generated contract. Depends on T0028-001/004.
- [ ] T0028-006 — Qualify final native transport and review focused integration/
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
- J/services/StoneChatStreamingService.java
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

- J/controllers/StoneChatStreamingController.java
- J/controllers/StoneChatStreamWebConfiguration.java
- J/handlers/StoneChatStreamReturnValueHandler.java
- J/handlers/StoneChatProblemDetailFactory.java
- J/handlers/ApiExceptionHandler.java
- J/services/StoneChatStreamingService.java
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

Selectors: StoneChatStreamTransportQualificationTest#qualifyFinalTransport,StoneChatStreamArchitectureTest,StoneChatStreamHttpTest,StoneChatStreamIntegrationTest.

Requalify changed final transport against prototype guarantees, actual writer/
worker cleanup and configured bounds. Review focused final integration, generated
contract, source roots/responsibility packages and acceptance.md evidence. Do not
repeat unchanged model qualification or run a full suite. Record READY/BLOCKED
and actual results honestly; failed bounds cannot be hidden by test relaxation.
Keep 0029 frontend handoff and deferred detailed cookie review visible, then stop
at the requested ticket/batch. No automatic commits, pushes or extra tasks.
