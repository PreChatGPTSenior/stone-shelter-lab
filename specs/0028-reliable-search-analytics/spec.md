# Feature 0028: Reliable Search Analytics Publication and Storage

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Capture completed search analytics through the shared outbox/Kafka mechanism and store them once in PostgreSQL.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Define the separate analytics AsyncAPI contract before implementation.
- Publish query text, found count and only the stones actually presented, retaining their order.
- Use total database matches for exact counts and bounded found candidates before presentation for semantic counts.
- Preserve events during Kafka outages and consume duplicate deliveries without duplicate analytics records.
- Add analytics persistence through new Liquibase migrations.

## Delivery Boundary to Refine

- Reuse features 0019 and 0020 rather than another publication mechanism.
- Analytics dashboards and additional event fields have not been selected.
- Streaming delivery remains features 0029 and 0030; analytics must remain reusable by both transports.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0020-outbox-kafka-publication](../0020-outbox-kafka-publication/spec.md).
- [0026-semantic-search-rag-answers](../0026-semantic-search-rag-answers/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Presented-stone IDs versus snapshots, event identity and retention.
- Which search/clarification/error outcomes produce events and when counts are finalized.
- Failure behaviour when the initial durable write fails, and its relationship to conversation completion.

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
