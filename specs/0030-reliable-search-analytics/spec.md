# Feature 0030: Reliable Search Analytics Publication and Storage

Status: Entire feature deferred to phase 2; out of scope for the current
development phase. No implementation, contract preparation, qualification or
readiness execution is authorized. Former feature 0028 is now 0030.

## Accepted Phase Boundary

The user explicitly removed reliable search analytics from this development
phase. See [ADR-0030 in phase-2](../../docs/adr/phase-2/0030-reliable-search-analytics.md)
and [OOS-0030-001](../../docs/out-of-scope-decisions.md). Every part of this
feature is deferred, including analytics-specific contracts, events/publication,
storage/migrations, consumers, verification and unresolved design discussions.
The sections below retain the earlier future intent; they are not active scope
or finalized phase-2 requirements. Revisit them only on a separate future request.

## Goal and References

Capture completed search analytics through the shared outbox/Kafka mechanism and store them once in PostgreSQL.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Preserved Future Intent — Not Current-Phase Scope

- Define the separate analytics AsyncAPI contract before implementation.
- Publish query text, found count and only the stones actually presented, retaining their order.
- Use total database matches for exact counts and bounded found candidates before presentation for semantic counts.
- Preserve events during Kafka outages and consume duplicate deliveries without duplicate analytics records.
- Add analytics persistence through new Liquibase migrations.

## Delivery Boundary to Refine

- Reuse features 0019 and 0020 rather than another publication mechanism.
- Analytics dashboards and additional event fields have not been selected.
- Streaming delivery is now features 0028 and 0029. Neither requires analytics in this phase; future analytics may reuse their completed-turn behavior.

The entire feature exclusion is accepted under OOS-0030-001; the remaining design
notes are preserved proposals, not additional accepted product requirements.

## Dependencies

- [0020-outbox-kafka-publication](../0020-outbox-kafka-publication/spec.md).
- [0026-semantic-search-rag-answers](../0026-semantic-search-rag-answers/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Presented-stone IDs versus snapshots, event identity and retention.
- Which search/clarification/error outcomes produce events and when counts are finalized.
- Failure behaviour when the initial durable write fails, and its relationship to conversation completion.

## Future Contract and Implementation Preparation — Deferred

English-only delivery and existing pinned project versions remain the starting
point. Before implementation, settle this feature's behaviour and contracts,
fill acceptance.md and plan.md, and define exact writable files and focused test
selectors in tasks.md. HTTP contracts are generated from controllers/shared DTOs;
do not create handwritten OpenAPI. Define applicable AsyncAPI contracts before
Kafka implementation. Use new Liquibase migrations for schema changes.

No ticket in this feature is part of the current-phase execution batch. If phase-2
work is separately requested, first settle its requirements/acceptance/contracts,
prepare exact file scopes/selectors and establish fresh requested-batch readiness.
This retained document authorizes no implementation, provisioning, model calls,
business imports, tests, commits or pushes.
