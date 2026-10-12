# Accepted Out-of-Scope Decisions

This register contains only explicitly accepted scope exclusions. Whenever a
new exclusion is accepted, record it both here and in the corresponding feature
ADR. Keep the two entries consistent and link them to the feature specification.
Do not record proposals, unanswered questions or assumed exclusions as decisions.
This register does not authorize implementation or changes to feature scope.

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
