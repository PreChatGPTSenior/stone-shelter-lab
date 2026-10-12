# Feature 0021 Technical Plan

Status: Prepared from accepted ADR-0021; implementation and runtime qualification pending.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [tasks.md](tasks.md).
This document describes implementation choices, not acceptance criteria or
verification evidence. Use only the named writable files and selectors.

## Concrete Index Contract

asyncapi.json pins STONE_CARD_INDEX_UPDATED and STONE_CARD_INDEX_DELETED at
schemaVersion 1. Update payload is the full six-field snapshot/version class;
delete payload has stoneId/sourceVersion. Envelope fields reference 0020.
StoneIndexEventService creates UUIDs and delegates outbox persistence. Entity-to-
shared-payload conversion belongs in StoneEntityToStoneIndexUpdatePayloadMapper.
Use a configured keyField of stoneId for both routes.

## Durable Source Versions and Catalog Transactions

Create stone_index_source_version in 0021-create-stone-index-source-version.xml
with stone_id as a unique key and nonnegative source_version, without a catalog
FK. Seed existing IDs with 0 and emit no events. Keeping the row after deletion
preserves the maximum assigned version for recovery and delayed work.

StoneIndexSourceVersionRepository allocates versions under a per-stone row lock
in the same transaction as the snapshot/catalog mutation and outbox write. New
cards allocate 1; ordinary relevant changes/deletion allocate current+1. Never
use event schemaVersion as the source counter. serialize concurrent allocations;
lock live catalog rows consistently before their source-version rows.

Creation wiring belongs in StoneCreationService.create, whose transaction
contains the stone insert. StoneService.create currently delegates there and
performs the existing photo workflow afterward; do not put the outbox write in
that nontransactional wrapper. Update captures the old indexed values before
mapping, then compares the final snapshot. Delete records its next-version event
before removing the catalog row. Existing photo cleanup/adoption behavior stays
owned by its current feature. Translate outbox/version failure in ApiExceptionHandler
as 500 ProblemDetail; preserve successful catalog DTOs.

## Topic and Consumer Boundary

StoneIndexKafkaTopicService explicitly provisions the single-partition,
replication-factor-1 topic with 24-hour retention and registers the two routes.
No embedding runs during these transactions. Source versions remain required
because dispatcher retries can reorder publication; topology/no-op/key rationales
remain in ADR-0021. The 0022 consumer owns projection apply and deletion.

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
