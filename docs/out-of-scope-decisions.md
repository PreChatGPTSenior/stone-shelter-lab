# Accepted Out-of-Scope Decisions

This register contains only explicitly accepted scope exclusions. Whenever a
new exclusion is accepted, record it both here and in the corresponding feature
ADR. Keep the two entries consistent and link them to the feature specification.
Do not record proposals, unanswered questions or assumed exclusions as decisions.
This register does not authorize implementation or changes to feature scope.

## Feature 0027: LLM Search Query Qualification

Accepted on 2026-10-13. Feature: [specification](../specs/0027-llm-search-query-qualification/spec.md).
Decision record: [ADR-0027](adr/0027-llm-search-query-qualification.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0027-001 | Automatic production-code corrections during qualification | A poor result triggers additional diagnosis and a concrete correction proposal. Agree the correction behavior, exact writable files and verification separately before code changes; qualification alone does not authorize production fixes or threshold adoption. |
| OOS-0027-002 | Performance measurements, benchmarks and speed optimization | Evaluate correctness and selection/explanation quality without latency/throughput, percentile timing, CPU/memory or model-speed analysis. Existing timeout/resource-limit correctness and reporting of failures remain required. |
| OOS-0027-003 | Manual application acceptance against the user's personal scenario set | The user will collect a separate personal set during development and manually test the application after implementation. That unsupplied set and its execution are deferred from current 0027 qualification; no submission is required now. The approved fixed 20-scenario qualification and human review of its generated answers remain in scope. |

## Feature 0026: Semantic Search and RAG Answers

Accepted on 2026-10-13. Feature: [specification](../specs/0026-semantic-search-rag-answers/spec.md).
Decision record: [ADR-0026](adr/0026-semantic-search-rag-answers.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0026-001 | User-visible information-source attribution | Do not show source citations, document/section names, provenance IDs/links/scores or prose identifying the information source. Retain provenance internally; selected-stone catalog links remain part of the answer. |

## Feature 0025: Semantic Stone Candidate Search

Accepted on 2026-10-13. Feature: [specification](../specs/0025-semantic-stone-candidate-search/spec.md).
Decision record: [ADR-0025](adr/0025-semantic-stone-candidate-search.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0025-001 | Offering or opening a catalog view filtered by the found candidate IDs | For now, show at most five stones in chat and state that more were found when applicable. A future agent-assisted offer to view the found IDs in the catalog is deferred; no catalog-navigation integration or additional-results UI is implemented in this feature. |
| OOS-0025-002 | Substantive evidence-based explanation and model-written answers | Feature 0025 selects/checks candidates and provides minimal truthful backend-template text. Feature 0026 owns substantive explanations using the structured card/document evidence, as accepted in Q0025-008 and Q0025-016. |
| OOS-0025-003 | Broad query-quality evaluation and threshold tuning | Use the agreed initial source thresholds/limits and mandatory basic functional qualification here. Feature 0027 owns the broader representative-query quality evaluation, as accepted in Q0025-021; no tuning campaign is part of 0025. |

## Feature 0024: Exact LLM Search

Accepted on 2026-10-12. Feature: [specification](../specs/0024-exact-llm-search/spec.md).
Decision record: [ADR-0024](adr/0024-exact-llm-search.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0024-001 | Native model tool calling | Use structured interpretation followed by backend execution. After feature 0030, check whether tool calling was covered by another discussed feature; if not, discuss a separate tooling feature. |
| OOS-0024-002 | Additional model invocation for result wording | Render exact-search result text with backend templates. After feature 0030, check whether model-generated wording was covered elsewhere; if not, discuss it separately. |
| OOS-0024-003 | Semantic matching of unsupported properties and intended uses | Do not drop unsupported wishes or infer exact properties; semantic matching belongs to feature 0025. |
| OOS-0024-004 | Care and geological explanations | This stage provides exact catalog search; substantive care/geological answers belong to feature 0026. |
| OOS-0024-005 | Dedicated evaluation of context and output-token sizing | Start with an 8192-token total context and a 512-token output cap; defer detailed parameter evaluation and tuning. Mandatory readiness and basic functional verification remain in feature 0024, with broad query-quality evaluation in feature 0027. |
| OOS-0024-006 | General name and ID search-filter additions | Limit new exact-search filters to type, size, availability and admission date. Stored-result and page-context references remain supported. |
| OOS-0024-007 | External-provider implementation in the initial delivery | Prepare a provider interface while running only local Ollama initially; no external provider connection or automatic fallback is selected here. |
| OOS-0024-008 | Queuing customer chat requests for model admission | Customer chat requests receive an immediate busy/capacity or token-budget rejection. Background indexing waits without consuming event-specific processing attempts or entering DLQ. Markdown imports wait within their existing overall deadline and fail without partial publication if it expires. |

## Feature 0023: Stone-Card Initial Indexing and Reindexing

Accepted on 2026-10-12. Feature: [specification](../specs/0023-stone-card-reindexing/spec.md).
Decision record: [ADR-0023](adr/0023-stone-card-reindexing.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0023-001 | Changes to the embedding model, profile or dimensionality | Reindex only with the current agreed embedding profile. Do not switch models even at the same dimensionality, migrate vector dimensions or implement profile/dimension migration in this feature. |
| OOS-0023-002 | Job cancellation | Provide no job-cancellation operation in the initial scope. |
| OOS-0023-003 | Job-history expiry and automatic cleanup | Retain job history and errors indefinitely; defer decisions about when to clean them and any cleanup implementation. |
| OOS-0023-004 | End-to-end index-completion tracking | Initially report durable event enqueueing as ENQUEUED. Revisit tracking actual consumer completion and final index readiness in a separate discussion. |
| OOS-0023-005 | Scheduled recurring reindexing | Use explicit operator-triggered initial indexing and recovery, without daily or other scheduled recurring runs. |

## Feature 0022: Stone-Card Index Event Processing

Accepted on 2026-10-12. Feature: [specification](../specs/0022-stone-card-index-event-processing/spec.md).
Decision record: [ADR-0022](adr/0022-stone-card-index-event-processing.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0022-001 | Separate processed-event UUID registry | Skip duplicate and stale work using per-stone sourceVersion state; do not add a separate registry of successfully processed UUIDs. Durable attempt tracking remains required. |
| OOS-0022-002 | Automatic model switching and incompatible-profile index rebuilding | Stop processing until configuration is corrected; do not change the embedding model or rebuild automatically. |
| OOS-0022-003 | Automatic recovery of events expired from Kafka | Restore missing index work through explicit reindexing in feature 0023 rather than automatic gap recovery in feature 0022. |
| OOS-0022-004 | Extended consumer observability and management interfaces | Keep logs, required processing state and DLQ error information. Do not add dedicated metrics, new HTTP operations or a management UI. |

## Feature 0021: Stone-Card Index Event Publication

Accepted on 2026-10-12. Feature: [specification](../specs/0021-stone-card-index-event-publication/spec.md).
Decision record: [ADR-0021](adr/0021-stone-card-index-event-publication.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0021-001 | Reindexing for photograph or admission-date changes | Publish update intentions for name, stoneType, stoneSize and biography; photograph and admission-date changes do not trigger indexing. |
| OOS-0021-002 | Adoption-status-driven index changes and availability filtering | Adoption status does not change indexing behavior in this feature. Availability and adoption filtering belong to future search work. |
| OOS-0021-003 | Embedding generation, event consumption and projection deletion | Feature 0022 processes indexing events, generates embeddings and deletes projections; feature 0021 only persists publication intentions. |
| OOS-0021-004 | Explicit reindexing and automatic publication for existing cards | Initialize existing source versions without emitting events. Feature 0023 owns explicit reindexing of existing cards. |
| OOS-0021-005 | Adding event or source-version fields to successful catalog HTTP responses | Preserve the existing successful response contracts; event identity and source versions are internal event data. |

## Feature 0020: Outbox Kafka Publication

Accepted on 2026-10-12. Feature: [specification](../specs/0020-outbox-kafka-publication/spec.md).
Decision record: [ADR-0020](adr/0020-outbox-kafka-publication.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0020-001 | Processing and replaying dead-letter events | Transfer exhausted events reliably to outbox-dead-letter, then do not consume, repair or replay them in this feature. |
| OOS-0020-002 | Concurrent dispatchers across multiple backend instances | Support one running backend instance and one dispatcher; do not implement distributed publication ownership in this feature. |
| OOS-0020-003 | Detailed analysis of Kafka never becoming available | Keep pending events durable without consuming the event-specific attempt limit during broker unavailability. Special escalation, terminal-outage policies and a detailed permanent-outage analysis are deferred. |
| OOS-0020-004 | Detailed diagnostic-field analysis and expanded observability | Keep the agreed logs and database fields with their basic semantics. Defer full analysis of those fields, dedicated metrics, new HTTP management operations and a management UI. |

## Feature 0019: Outbox Event Persistence

Accepted on 2026-10-12. Feature: [specification](../specs/0019-outbox-event-persistence/spec.md).
Decision record: [ADR-0019](adr/0019-outbox-event-persistence.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0019-001 | Support for empty event payloads | Reject null, empty objects, arrays and scalar payloads. Only nonempty JSON objects are accepted. |
| OOS-0019-002 | Automatic event expiry and cleanup | Retain events indefinitely, including pending and successfully dispatched events. This feature does not delete them through a retention policy or cleanup job. |
| OOS-0019-003 | Configurable and event-specific payload-size limits | Enforce a fixed maximum of 256 KiB (262144 bytes) of compact JSON payload encoded in UTF-8, excluding the event envelope. Custom limit configuration and different limits per event type are excluded. |
| OOS-0019-004 | Comparing payload content on repeated event IDs | Validate every invocation first. For an existing eventId with valid input, compare only eventType and schemaVersion. Matching values produce a successful no-op regardless of the supplied payload; a mismatch raises an error. Preserve the original payload, timestamps and delivery state. The caller must ensure that one UUID always identifies one immutable event. |

## Feature 0018: Stone-Card Embeddings

Accepted on 2026-10-12. Feature: [specification](../specs/0018-stone-card-embedding/spec.md).
Decision record: [ADR-0010](adr/0010-stone-card-embedding.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0018-001 | Automatic card-index updates through Kafka/outbox | Separate features implement event publication and automatic index updates. |
| OOS-0018-002 | Chatbot integration | This feature provides supporting services and classes for subsequent implementation. |
| OOS-0018-003 | Enrichment of card text from the encyclopedia or inferred properties | Embed only factual card fields: name, stone type, size and biography. |
| OOS-0018-004 | Embedding photographs | Photographs are excluded from the card text and embedding input. |
| OOS-0018-005 | Additional handling of an empty biography | An empty biography remains empty; do not generate replacement content. |
| OOS-0018-006 | Triggering indexing when a stone is added | A separate task will connect the supporting capability to stone creation. |
| OOS-0018-007 | HTTP endpoints and bulk-indexing execution | Provide supporting text construction, embedding, persistence and internal retrieval capabilities without public routes or a bulk run. |
| OOS-0018-008 | Index-management operations other than saving | Dedicated index deletion and direct index-record reading are excluded; internal semantic retrieval remains in scope. |
| OOS-0018-009 | Extended internal-search inputs and outputs | The initial contract accepts query text and a result limit, and returns stone IDs and similarity scores ordered by relevance. Results below the similarity threshold are excluded; no qualifying candidates means an empty result. |
| OOS-0018-010 | Adoption-status filtering and live stone-existence checks | A future search feature will apply these checks. |
| OOS-0018-011 | Query-quality evaluation | A separate feature will test query quality and representative query sets. Acceptance here only confirms that a simplest basic query works; focused implementation checks remain required. |
| OOS-0018-012 | Card-text splitting and new model-input length limits | Retain the existing 2048-character biography limit and existing embedding-provider validation and failure behavior without silent truncation. Do not add chunking or new input-length restrictions in this feature. |
