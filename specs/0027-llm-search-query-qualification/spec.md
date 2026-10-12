# Feature 0027: LLM Search Query Qualification

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Evaluate representative English queries separately from the basic functional checks in indexing and search implementation features.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Define a user-reviewed set of cards, queries and expected search/answer outcomes.
- Cover exact filters, paraphrases, semantic preferences, clarification, follow-ups and stale/deleted/unavailable candidates.
- Evaluate card retrieval and the completed search flow, including the initial 0.5 similarity threshold.
- Record evidence and limitations; distinguish retrieval errors from interpretation or answer errors.

## Delivery Boundary to Refine

- This feature owns the query-quality work explicitly deferred by feature 0018.
- Infrastructure replacement and unrelated search functionality require separate decisions rather than implicit evaluation fixes.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0026-semantic-search-rag-answers](../0026-semantic-search-rag-answers/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Fixtures, expected relevance and acceptable outcomes/quality thresholds.
- Whether threshold tuning is documentation/configuration only or includes a separately scoped correction task.
- Whether any performance checks are wanted; no measurements or speed targets selected here.

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
