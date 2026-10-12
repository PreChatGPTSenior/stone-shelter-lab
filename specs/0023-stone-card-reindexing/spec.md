# Feature 0023: Stone-Card Initial Indexing and Reindexing

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Provide an explicit way to index existing cards and repair or rebuild the card projection without relying on new stone creation.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Define an operator-triggered initial indexing and repeat reindexing operation.
- Reuse existing rendering, embedding and durable update capabilities.
- Coordinate the selected card snapshot with concurrent updates/deletions so a rebuild does not overwrite newer state.
- Make progress, failures and repeat execution understandable to the operator.

## Delivery Boundary to Refine

- Automatic card event handling remains owned by features 0021 and 0022.
- Chat search and query-quality evaluation are owned by features 0024–0027.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0022-stone-card-index-event-processing](../0022-stone-card-index-event-processing/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- HTTP versus CLI/internal operator entry point; synchronous versus job execution.
- Selected IDs versus all cards, batch bounds and resume/retry semantics.
- Concurrent mutation strategy and behaviour when an embedding profile changes.

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
