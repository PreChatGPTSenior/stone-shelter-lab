# Feature 0024: Exact LLM Search

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Turn an English conversation request into validated catalog filters and return exact search results through the existing JSON chat flow.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Use local qwen2.5:3b and verify its basic structured-output/tool behaviour.
- Interpret wishes through bounded application operations; do not execute model-generated SQL.
- Use the existing bounded conversation context for clarification and follow-ups.
- Preserve agreed exact-search ordering, up to five presented stones, refinement when more exist and absence-only responses when no stones match.
- Check that presented stones exist and are currently available.

## Delivery Boundary to Refine

- Semantic candidate retrieval is owned by feature 0025; document-grounded answers by feature 0026.
- Broader query-quality qualification is owned by feature 0027; analytics and streaming by features 0028–0030.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0016-llm-search-conversation-history-and-context](../0016-llm-search-conversation-history-and-context/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Structured interpretation contract and supported filter vocabulary.
- Clarification versus execution, invalid model output and dependency failures.
- Model-call capacity, context limits and request deadlines left to model integration by feature 0016.
- Provider-boundary implementation, including the unresolved Spring AI choice.

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
