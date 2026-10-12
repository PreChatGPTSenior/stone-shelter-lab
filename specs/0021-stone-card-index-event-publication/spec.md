# Feature 0021: Stone-Card Index Event Publication

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Publish card-indexing intentions reliably when a stone is created, relevant fields change or a stone is deleted.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Define the indexing AsyncAPI contract before Kafka-related implementation.
- Write the stone change and outbox event in one database transaction.
- Return the existing HTTP operation after commit without waiting for publication or embeddings.
- Include enough identity/version information for the consumer to handle delayed, duplicated or out-of-order work.

## Delivery Boundary to Refine

- Event delivery uses feature 0020 rather than another dispatcher.
- Consumer processing and projection deletion are owned by feature 0022; explicit reindexing by feature 0023.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0020-outbox-kafka-publication](../0020-outbox-kafka-publication/spec.md).
- [0018-stone-card-embedding](../0018-stone-card-embedding/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Exact relevant-change triggers, including adoption status and fields excluded from embedding text.
- Snapshot versus identity payload, source versions and deletion representation.
- Behaviour when the durable event write fails and verification of existing HTTP responses.

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
