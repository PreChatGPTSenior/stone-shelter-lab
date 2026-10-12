# Feature 0025: Semantic Stone Candidate Search

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Combine card relevance with exact constraints and current catalog eligibility to produce bounded internal search candidates.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Reuse feature 0018 query embeddings, similarity scores and configurable threshold.
- Apply exact constraints together with semantic preferences.
- Check live existence and AVAILABLE status before returning eligible candidates; tolerate delayed index updates.
- Define deterministic ranking, candidate counts and the evidence needed to distinguish more than five results.

## Delivery Boundary to Refine

- Answer generation and use of encyclopedia passages are owned by feature 0026.
- Query-set evaluation and threshold qualification are owned by feature 0027.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0023-stone-card-reindexing](../0023-stone-card-reindexing/spec.md).
- [0024-exact-llm-search](../0024-exact-llm-search/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Whether/how semantic preference and hard filters combine.
- Candidate cap, live filtering before/after retrieval, refill and count semantics.
- Tie-breaking and minimum-similarity handling for the bounded candidate set.

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
