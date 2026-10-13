# Feature 0028: Chat Streaming API

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Add a separate backend streaming endpoint that shares completed-turn search behaviour with JSON chat.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Define the generated HTTP contract and streaming event payloads before implementation.
- Deliver answer text incrementally and stone references with the completed answer.
- Reuse conversation ownership, context and exact/semantic behaviour rather than building a second search engine. Analytics is deferred to phase 2 and is not required for this phase's streaming delivery.
- Define completion, failure, cancellation, disconnect and persistence boundaries.

## Delivery Boundary to Refine

- Browser widget integration is owned by feature 0029.
- Do not replace the existing JSON endpoint or redefine search logic as part of streaming delivery.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0026-semantic-search-rag-answers](../0026-semantic-search-rag-answers/spec.md).
- [0016-llm-search-conversation-history-and-context](../0016-llm-search-conversation-history-and-context/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- SSE or another selected transport, event names/payloads and completion identity.
- Disconnect/cancellation, retry/deduplication and partial answer persistence.
- Request protection and consistency with completed conversation turns.

## Phase Boundary and Renumbering

Former feature 0029 is now 0028; its readiness ticket is T0028-001.
Reliable search analytics is entirely outside this development phase as
[0030](../0030-reliable-search-analytics/spec.md), recorded in
[the phase-2 ADR](../../docs/adr/phase-2/0030-reliable-search-analytics.md).
Do not implement analytics events, publication, storage or analytics-specific
completion/failure handling here, or make analytics delivery a prerequisite.
Existing indexing outbox/Kafka functionality retains its own feature boundaries.

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
