# Feature 0019 Technical Plan

Status: Prepared from accepted ADR-0019; implementation and runtime qualification pending.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [tasks.md](tasks.md).
This document describes implementation choices, not acceptance criteria or
verification evidence. Use only the named writable files and selectors.

## Internal API and Persistence

Use OutboxWriteRequest(eventId, eventType, schemaVersion, payload), a four-field
shared request record. OutboxService.save returns OutboxEventEntity. Entity
classes have private fields, no-argument constructors and accessors. An
OutboxWriteRequestToOutboxEventEntityMapper owns conversion; the service owns
extra validation and first-write metadata. Use an injected Clock.

Create outbox_event through 0019-create-outbox-event.xml: event_id UUID primary
key, event_type text, schema_version integer, occurred_at/created_at timestamptz,
payload jsonb and delivery_state initially PENDING. Enforce nonnull fields,
positive schema version and nonempty object representation in the database.
The fixed byte bound is checked before writing compact UTF-8 payload; PostgreSQL
jsonb's internal representation is not the byte measurement. No business FK.

OutboxService uses MANDATORY propagation, including the absence-of-transaction
error boundary. Validate every call before lookup. Insert with ON CONFLICT DO
NOTHING and then load the surviving row within the caller transaction; avoid
catching a unique-constraint exception that aborts PostgreSQL's transaction.
Compare only eventType/schemaVersion. Conflict and validation exceptions are
protocol independent and make the caller transaction unable to commit partial
business work. Matching duplicates return the original entity unchanged.

## Boundaries and Verification

Feature 0020 adds delivery lifecycle fields; 0021 invokes this API from catalog
transactions. No controller, Kafka send, payload-content equality or retention
job belongs here. Verify UTF-8 boundary values and meaningful rollback/concurrent
identity behavior with the named unit and PostgreSQL Testcontainers selectors.

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
