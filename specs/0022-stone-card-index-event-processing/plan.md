# Feature 0022 Technical Plan

Status: Prepared from accepted ADR-0022; implementation and runtime qualification pending.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [tasks.md](tasks.md).
This document describes implementation choices, not acceptance criteria or
verification evidence. Use only the named writable files and selectors.

## Consumer and Durable State

Use one automatically started StoneIndexConsumerService, group stone-card-indexer,
earliest for a new group and manual acknowledgement after processing. Reuse
0021's contract and 0020's DLQ sender/format; disable automatic offset commits.

Create stone_card_index_state(stone_id primary key, source_version, deleted) and
stone_card_index_attempt in 0022-create-stone-card-index-processing.xml, without
catalog FKs. Attempt identity is source topic/partition/offset, with charged
attempt count, retry time, last failure, complete original record bytes and
DLQ-pending outcome. Keep those bytes durable until terminal handling, including
when the source Kafka record expires during transfer recovery. This is retry
state, not a registry of successfully processed event UUIDs. Persist attempts
in a short independent transaction even when projection work rolls back.

## Safe Embedding and Atomic Apply

Read version state and skip stale work before Ollama. Reuse the existing card
renderer/embedding path, but do not blindly call StoneCardEmbeddingService.save:
that method publishes its projection before the consumer version check. Extract
preparation that returns the prepared entity without persistence and call it
outside a DB transaction. StoneIndexProcessingTransactionService locks/creates
state, rechecks sourceVersion and atomically writes vector/text/version or
vector deletion plus tombstone. A stale result after preparation is discarded.
Acknowledge only after its commit; a crash before ack safely redelivers.

## Retries, Dependencies and DLQ

Persist event failures and retry the same partition head after 2s/4s, three total
attempts. Use consumer pause/resume/backoff rather than sleeping through a Kafka
poll timeout; maintain group membership while the partition remains blocked.
Dependency outages and, after 0024 integration, healthy admission waiting charge
no attempts. Source offset cannot advance while its result is unresolved.

Invalid contract bytes go directly to DLQ; preserve complete raw bytes when JSON
cannot represent them. Persist DLQ-pending state before sending, retry that
transfer after restart, and acknowledge the source only after DLQ ack. If the
DB cannot store required state, wait rather than losing ownership. An incompatible
embedding profile waits for correction. Topic expiry is repaired by explicit
0023 reindexing, not an automatic consumer scan.

Feature 0024 owns shared model admission/accounting installation and its named
consumer regression check; feature 0022 must expose a waiting outcome that cannot
be accidentally charged as an event-specific failure. No retry topic or management
API is introduced. ADR-0022 retains the rationale and alternative considered.

## Environment and Execution Gate

Execution requires an explicitly selected ticket batch and its first readiness
ticket, even when historical dependency tasks are complete. Read constitution,
current spec/acceptance, relevant local rules and the exact task allowlist. Use
existing pinned tools: Maven's JAVA_HOME must select JDK 23.0.2; verify approved
Docker images/runtime versions and effective command-scoped configuration.
Never print secrets or inspect private .env. Do not read build/dependency/framework
configuration outside the selected ticket's explicit scope.

Check actual authenticated PostgreSQL connectivity/server version (18.6) and,
when vectors are used, pgvector 0.8.6; actual Kafka broker metadata (4.2.2) for
Kafka work; Ollama version (0.40.2), selected model inventory/digests/profile for
model work; Redis, MinIO and application endpoints wherever the selected context
requires them. Use backend-reachable addresses from approved configuration,
not a substitute host address. Report backend/frontend states and task impact
even for a unit-only batch. Do not label unchecked components ready.

Supply known authorized JAVA_HOME, endpoints and necessary variables to the
agent's own commands. Start only required existing services using approved
configuration while preserving data, then recheck; record startup separately
from read-only checks. Missing/unhealthy/unverifiable prerequisites block their
entire affected batch. No business imports, inference or application tests are
readiness probes; model inference belongs only to its named qualification ticket.
Readiness reports identify covered IDs, component expectations/observations,
preparation, READY/BLOCKED result and corrective actions. No readiness is claimed
by this documentation update.

Tests use isolated PostgreSQL Testcontainers and clean up their own data. Mock
providers except explicitly named qualification checks. From stone-shelter-api,
run only ./mvnw -Dtest=<the selected ticket's exact selector> test with controlled
environment. No full suite or broad verify invocation. Do not rerun successful
checks unless their inputs changed. HTTP schema verification uses generated
/v3/api-docs and /v3/api-docs.yaml, never handwritten OpenAPI.
