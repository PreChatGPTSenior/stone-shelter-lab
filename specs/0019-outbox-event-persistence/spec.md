# Feature 0019: Outbox Event Persistence

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Persist outgoing events in PostgreSQL in the same transaction as the business change, providing a reusable durable handoff for later Kafka delivery.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Define a protocol-independent internal operation for storing an outgoing event in the caller's transaction.
- Add the outbox schema through new Liquibase migrations.
- Retain event identity, payload and delivery state needed by the next feature; a rolled-back business transaction must not retain its event.

## Delivery Boundary to Refine

- Kafka dispatch is owned by feature 0020.
- Stone mutation integration is owned by feature 0021; analytics integration by feature 0028.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0014-llm-search-infrastructure](../0014-llm-search-infrastructure/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Outbox row fields, event identity and payload representation.
- Transaction API, retention and lifecycle ownership shared with the dispatcher.

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
