# ADR 0010: Stone-Card Embedding Decisions

Date: 2026-10-12

Status: Accepted scope and service contract; implementation planning documented.
Current runtime readiness and implementation verification remain unperformed.

## Context

[Feature 0018](../../specs/0018-stone-card-embedding/spec.md) will provide
supporting services and classes for stone-card embeddings. The user confirmed
that embedding input uses factual card fields and one vector per card. This
record distinguishes accepted scope decisions from their technical implementation
plan in the feature directory.

## Accepted Supporting Capability

- Provide text construction, embedding, saving and internal semantic retrieval
  as backend services/classes, without connecting them to card creation yet.
- Use one vector per card from name, type, size and biography only.
- For the same stoneId, replace the stored text/vector in one projection.
- Internal retrieval accepts query text and a result limit, returning IDs and
  similarity scores ordered by relevance.
- Use a minimum cosine similarity of 0.5 by default, configured through
  STONE_CARD_SEARCH_MIN_SIMILARITY. Include the threshold boundary and return
  an empty result when no candidates qualify. The value is an initial setting,
  not a universally qualified relevance cutoff.
- Functional acceptance includes one simplest basic query. Query-quality
  evaluation belongs to a separate feature.

## Technical Plan and Consequences

The [plan](../../specs/0018-stone-card-embedding/plan.md) reuses the existing
document embedding integration without renaming it or changing dependencies.
A separate table and primary-key upsert isolate the card projection. Embeddings
are prepared before a short write transaction; repository-owned cosine queries
apply the threshold and limit. No catalog foreign key, join or lifecycle hook
is introduced. Technical validation, profile guards and deterministic ordering
support this small internal contract rather than new product capabilities.

The [tasks](../../specs/0018-stone-card-embedding/tasks.md) begin with a fresh
batch-wide readiness gate and constrain edits/tests per ticket. Preparing these
documents authorizes no implementation, index execution, commits or pushes.

## Accepted Out-of-Scope Decisions

- OOS-0018-001: Automatic card-index updates through Kafka/outbox belong to
  separate event-publication and index-update features.
- OOS-0018-002: Chatbot integration is outside this feature; supporting services
  and classes are intended for subsequent implementation.
- OOS-0018-003: Encyclopedia enrichment and inferred properties are excluded.
  Card text uses only name, stone type, size and biography.
- OOS-0018-004: Photographs are excluded from card text and embedding input.
- OOS-0018-005: Additional handling of an empty biography is excluded. Leave it
  empty without generating replacement content.
- OOS-0018-006: Triggering indexing when a stone is added belongs to a separate
  task, which will connect the supporting capability to stone creation.
- OOS-0018-007: HTTP endpoints and bulk-indexing execution are excluded. Provide
  supporting text construction, embedding, persistence and internal retrieval
  capabilities without public routes or a bulk run.
- OOS-0018-008: Index-management operations other than saving are excluded.
  Do not add dedicated index deletion or direct index-record reading;
  internal semantic retrieval remains in scope.
- OOS-0018-009: Extended internal-search inputs and outputs are excluded. The
  initial contract accepts query text and a result limit, and returns stone IDs
  and similarity scores ordered by relevance. Exclude results below the
  similarity threshold; return an empty result when no candidates qualify.
- OOS-0018-010: Adoption-status filtering and live stone-existence checks belong
  to a future search feature.
- OOS-0018-011: Query-quality evaluation and representative query sets belong
  to a separate feature. Acceptance here only confirms that a simplest basic
  query works; focused implementation checks remain required.
- OOS-0018-012: Card-text splitting and new model-input length limits are
  excluded. Retain the existing 2048-character biography limit and existing
  embedding-provider validation and failure behavior without silent truncation.
  Do not add chunking or new input-length restrictions in this feature.

## Decision Recording

Duplicate every newly accepted scope exclusion in the
[central register](../out-of-scope-decisions.md) and this feature ADR. Preserve
matching decision IDs and meaning; proposals do not belong in either accepted
decision list. These records do not authorize feature implementation.
