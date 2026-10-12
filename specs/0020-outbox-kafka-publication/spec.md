# Feature 0020: Outbox Kafka Publication

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Deliver committed outbox events to Kafka in the background, preserving work during outages and supporting recovery.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Reuse the outbox persistence capability; do not publish uncommitted business changes.
- Define dispatcher ownership, delivery acknowledgement and restart recovery.
- Retain pending events across Kafka outages and retry delivery; duplicate publication must be accounted for by event contracts and consumers.

## Delivery Boundary to Refine

- Stone indexing events and their business triggers are owned by feature 0021.
- Embedding consumption is owned by feature 0022; analytics processing by feature 0028.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0019-outbox-event-persistence](../0019-outbox-event-persistence/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Polling/batch limits, concurrent dispatch ownership and retry policy.
- Publication acknowledgement, duplicate-delivery guarantees and completed-row retention.
- Shared transport envelope and ownership of domain AsyncAPI contracts.

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
