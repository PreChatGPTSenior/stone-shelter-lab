# Feature 0023 Technical Plan

Status: Prepared from accepted ADR-0023; implementation and runtime qualification pending.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [tasks.md](tasks.md).
This document describes implementation choices, not acceptance criteria or
verification evidence. Use only the named writable files and selectors.

## HTTP and Persistence

Implement the spec's POST/GET job operations in StoneCardReindexController with
shared request/response classes and response mappers; generated OpenAPI is the
only HTTP schema artifact. Use HTTP 201 for the durable job resource and no
Location header. ApiExceptionHandler translates protocol-independent selection,
conflict, missing-job and storage exceptions.

Create stone_card_reindex_job, stone_card_reindex_item and one
stone_card_reindex_control row in 0023-create-stone-card-reindex-jobs.xml. Store
selection, counts, times and per-item outcome/error. Item uniqueness is jobId plus
stoneId/work kind; a restart never enqueues a completed item twice. The control
row enforces one active job and identifies whether creation is blocked.

Start holds the control row for update, validates the explicit selection and
captures a durable item manifest; for ALL, capture current catalog IDs plus
orphan projections. This setup transaction contains no embedding or per-card
processing. The automatic runner processes at most STONE_REINDEX_BATCH_SIZE=20
items per pass, with independent short per-item transactions.

## Creation Gate and Races

StoneCreationService takes a shared lock on the control row before creating a
stone. An active ALL job rejects the operation; an ALL start waits for in-flight
creation commits before capturing its manifest. The persisted gate survives
restart and is released atomically with ENQUEUED. Targeted jobs do not block
creation. Do not prohibit ordinary updates/deletions.

For each live item, lock the card, allocate the next durable 0021 source version,
write a fresh full snapshot to outbox, and mark the item/counts in one transaction.
Skip a disappeared item. Record a card-specific failure independently and move
on; storage unavailability pauses the whole job rather than recording every card
as failed. Forced reindex bypasses ordinary unchanged-snapshot suppression.

For an orphan projection, allocate a deletion version above both the durable
source counter and processed projection state, preserve that counter and enqueue
0021's deletion event in the same item transaction. Existing assigned versions
remain covered by the durable counter. This repairs missed deletions without
clearing valid vectors before replacement and without resurrecting old events.
ENQUEUED waits for these intentions to be durable, not for the consumer to apply
them. Preserve current profile, history and errors; no cancellation or cleanup.

## Follow-up

Actual end-to-end consumer completion remains OOS-0023-004 and requires a separate
discussion. Do not reinterpret any HTTP status/counter as proof of final vectors.

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
