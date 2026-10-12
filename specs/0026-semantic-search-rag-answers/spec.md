# Feature 0026: Semantic Search and RAG Answers

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Answer English requests using eligible stone candidates, reference passages and the existing conversation flow.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Combine candidate retrieval with indexed Markdown reference passages.
- Use cards for specific specimens and documents for type knowledge; general model knowledge must not invent individual properties.
- Provide explanations, clarifications and follow-ups through the existing JSON chat endpoint.
- Preserve the agreed five-stone, result-ordering, zero-result and live-link behaviour.

## Delivery Boundary to Refine

- Candidate eligibility is owned by feature 0025; document indexing remains feature 0017.
- Systematic query evaluation is owned by feature 0027; analytics and streaming by features 0028–0030.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0025-semantic-stone-candidate-search](../0025-semantic-stone-candidate-search/spec.md).
- [0017-markdown-document-indexing](../0017-markdown-document-indexing/spec.md).
- [0016-llm-search-conversation-history-and-context](../0016-llm-search-conversation-history-and-context/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Retrieval routing, passage limits, prompt/context budget and insufficient-evidence behaviour.
- Conflicting card/document/general-knowledge evidence and visible attribution.
- Follow-up reference resolution and shared exact/semantic response representation.

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
