# Feature 0020 Technical Plan

Status: Prepared from accepted ADR-0020; implementation and runtime qualification pending.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [tasks.md](tasks.md).
This document describes implementation choices, not acceptance criteria or
verification evidence. Use only the named writable files and selectors.

## Delivery Schema and Routing

Add 0020-add-outbox-delivery.xml: attempt_count default 0, next_attempt_at,
last_error, published_at and dead_lettered_at; extend state constraints with
PUBLISHED, DEAD_LETTER_PENDING and DEAD_LETTERED. Index state/next_attempt_at for
eligible polling. Preserve applied 0019 changesets and all existing rows.

OutboxDeliverySettings reads OUTBOX_POLL_INTERVAL=PT2S, OUTBOX_BATCH_SIZE=20,
OUTBOX_DEAD_LETTER_RETENTION=PT24H and OUTBOX_ROUTES from the environment.
OUTBOX_ROUTES is a JSON map from eventType to topic and optional keyField; no
keyField means eventId. A configured payload-field key is serialized as a string;
missing/invalid routes or keys are event-specific failures. Broker addresses
reuse the approved backend Kafka configuration. Configure acks=all and producer
idempotence without changing pinned dependencies.

## Dispatcher and Failure State Machine

Use one automatically scheduled OutboxDispatcherService. Read eligibility in a
short transaction, perform publication through OutboxKafkaRepository outside
it, then apply delivery results through OutboxDeliveryTransactionService. There
is no distributed claiming: only one backend dispatcher is supported.

Acknowledge normal success before recording PUBLISHED. Charge only classified
event-specific failures, persist their counts/backoff and move to
DEAD_LETTER_PENDING on the third failure. Check broker reachability when sending
fails: unavailable broker suspends normal sends rather than charging every event.
Recheck recovery on polling; do not add a permanent-outage terminal policy.
Other problematic but broker-reachable events do not block the batch.

Persist destination state before DLQ sending. DEAD_LETTER_PENDING is eligible for
transfer indefinitely, across restarts, with no return to original publication.
After ack, commit DEAD_LETTERED/deadLetteredAt. A send-success/DB-write failure can
redeliver; producer idempotence does not promise transactional exactly-once.

## Contract and Topic Ownership

asyncapi.json owns the common envelope and dead-letter structure. Use existing
KafkaAdmin facilities from a narrowly scoped OutboxKafkaTopicService to explicitly
provision outbox-dead-letter and its retention. Feature 0021 provisions the
indexing topic. Do not add controllers or consume DLQ. Tests use mocked producer
acknowledgements and isolated PostgreSQL; a live broker is a readiness dependency,
not permission to send business events during readiness.

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
