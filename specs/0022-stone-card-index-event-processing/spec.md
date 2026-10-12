# Feature 0022: Stone-Card Index Event Processing

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Consume indexing events and maintain the card projection reliably using the embedding services from feature 0018.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Process creation, relevant changes and deletion against the card vector projection.
- Handle duplicate delivery and source-version ordering without restoring stale or deleted cards.
- Define retry and recovery when Ollama, PostgreSQL or Kafka is unavailable.
- Reuse one-vector-per-card rendering and compatible embedding profiles.

## Delivery Boundary to Refine

- Stone event production is owned by feature 0021.
- Initial/bulk reindexing is owned by feature 0023; live candidate eligibility by feature 0025.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0021-stone-card-index-event-publication](../0021-stone-card-index-event-publication/spec.md).
- [0018-stone-card-embedding](../0018-stone-card-embedding/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Consumer acknowledgement boundary, deduplication and version/tombstone persistence.
- Retry bounds, terminal failures and operator recovery.
- Concurrent processing and model-profile incompatibility handling.

## Contract and Implementation Preparation

English-only delivery and existing pinned project versions remain the starting
point. Before implementation, settle this feature's behaviour and contracts,
fill acceptance.md and plan.md, and define exact writable files and focused test
selectors in tasks.md. HTTP contracts are generated from controllers/shared DTOs;
do not create handwritten OpenAPI. Define applicable AsyncAPI contracts before
Kafka implementation. Use new Liquibase migrations for schema changes.

The first execution ticket must establish readiness for the entire requested
batch. This draft authorizes no implementation, source provisioning, model
calls/downloads, business imports, commits or pushes.
