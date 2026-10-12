# Feature 0030: Chat Streaming Widget

Status: Initial draft for discussion. Not ready for implementation.

## Goal and References

Integrate the backend streaming contract into the existing React chat widget.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Initial Scope

- Render answer text incrementally and expose stone links after completion.
- Preserve conversation navigation/reload behaviour from feature 0016.
- Handle stream errors, cancellation and reconnect/retry according to the backend contract.
- Verify browser state against real API behaviour as well as focused UI tests.

## Delivery Boundary to Refine

- Use the completed feature 0029 contract; do not invent another stream protocol in the UI.
- Backend search/indexing/analytics changes remain in their respective features.

These boundaries organize discussion; they do not establish new accepted product
exclusions. Record newly accepted out-of-scope decisions in the feature ADR and
[central register](../../docs/out-of-scope-decisions.md) when confirmed.

## Dependencies

- [0029-chat-streaming-api](../0029-chat-streaming-api/spec.md).
- [0016-llm-search-conversation-history-and-context](../0016-llm-search-conversation-history-and-context/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Open Decisions

- Pending/partial/error UI states and retry interaction.
- Navigation, reload and duplicate-completion reconciliation.
- Exact frontend file allowlist, focused selectors and browser smoke scenarios.

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
