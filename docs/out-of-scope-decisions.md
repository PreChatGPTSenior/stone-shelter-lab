# Accepted Out-of-Scope Decisions

This register contains only explicitly accepted scope exclusions. Whenever a
new exclusion is accepted, record it both here and in the corresponding feature
ADR. Keep the two entries consistent and link them to the feature specification.
Do not record proposals, unanswered questions or assumed exclusions as decisions.
This register does not authorize implementation or changes to feature scope.

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
