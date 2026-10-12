# Feature 0024 Technical Plan

Status: Prepared from accepted ADR-0024; implementation and runtime qualification pending.

Read [spec.md](spec.md), [acceptance.md](acceptance.md) and [tasks.md](tasks.md).
This document describes implementation choices, not acceptance criteria or
verification evidence. Use only the named writable files and selectors.

## Structured Decision and Exact Query

Use shared StoneExactSearchDecision (action, criteria, resetSearch, reference,
questions, reason), StoneExactSearchFilters and StoneAdmissionDateRange. Types
with more than four fields are classes. Validate a strict JSON schema and known
catalog enums; reject unexpected fields/output before backend query execution.
A StoneExactSearchProvider interface initially delegates to the existing pinned
Spring AI 2.0.1 Ollama integration, qwen2.5:3b, with hidden retries disabled.
No dependency/version change is planned.

Use backend criteria independent of shared DTOs. StoneExactSearchRepository
builds predicates/count/order and resolves trusted history/page IDs; it always
ANDs AVAILABLE. Existing public catalog day-range semantics remain unchanged:
new multiple ranges and availability groups are internal exact-chat capabilities.
Services do not build JPA predicates. Map results through a dedicated mapper to
a completed chat turn; existing response/history mappers retain public shapes.

Store validated effective filters as optional internal metadata on completed
StoneChatTurnEntity instances so bounded prompt trimming does not lose inherited
constraints. Old stored turns without metadata remain readable; no UI field or
Redis TTL change. Ordinals use the latest shown result list. Resolve today/
yesterday with an injected UTC Clock; a missing explicit year, unavailable
reference or unsupported wish produces clarification, not invented criteria.

## Qualification, Context and Timeouts

T0024-002 qualifies structured decisions, actual usage metadata and bounded prompt
construction with the installed model before production integration. Prove that
system instructions, schema, current request and output reservation fit 4096
for the existing permitted input domain; trim only old complete pairs. Existing
approximate embedding text estimates are not proof of exact chat token capacity.
If the selected integration cannot establish the bound or structured contract,
block dependent implementation and report the concrete qualification failure;
do not invent a new input limit, change models or bypass readiness.

Set CHAT_MODEL_CONTEXT_TOKENS=4096, CHAT_MODEL_OUTPUT_TOKENS=512 and
CHAT_MODEL_CALL_TIMEOUT=PT50S; retain the 120s turn deadline. A repair is a second
independent invocation/reservation with at most two calls per turn. Charge usage
for failed/invalid outputs too. No broad tuning/quality campaign is part of this
basic qualification.

## One Shared Model Guard and Daily Ledger

Reuse Resilience4j SemaphoreBulkhead qualified in 0016, N=1 through
MODEL_MAX_CONCURRENT_CALLS, across all backend inference paths. Keep a permit
until execution actually exits, including timeouts/cancellation; database work
and healthy background waiting hold neither a model permit nor a DB transaction.
Customer admission has no queue. Background indexing waits interruptibly;
Markdown import waits only within its existing overall deadline.

Create model_token_budget_day and model_token_reservation through
0024-create-model-token-budget.xml. Reserve atomically under the original UTC-day
row lock with MODEL_DAILY_TOKEN_BUDGET=100000. Each chat call reserves 4096;
each embedding call reserves its validated profile maximum input (currently
32768 in the existing document/card profile). Chat actual usage includes input
and output; embeddings charge reported input usage, not vector dimensions.
Settlement is idempotent by invocation UUID and frees only confirmed surplus.
Unknown usage remains reserved against its original day after restart. Create
a new day's separate row, preserving previous records. Never refund a call merely
because its HTTP client timed out. Invalid settings/profile or untrustworthy
control state block admission; provider usage exceeding its qualified bound is
a control failure, not permission to silently overspend.

Install guard/ledger at both the chat provider and existing embedding provider
boundary, including document/card/query embeddings, so there are no unaccounted
backend inference calls. Acquire permit and reserve only immediately before a
call; release any pre-call reservation only if invocation definitely never started.
The guard exposes immediate customer rejection and deadline-aware index waiting
without converting healthy contention into dependency failure or charged retries.

## Chat Integration and Failure Handling

Replace completeDemoTurn while retaining 0016 ownership, request quotas,
conversation serialization, committed replay, history atomicity and recovery.
Capacity/budget/invalid-model-output exceptions are ordinary explicit outcomes
for StoneChatAvailabilityService.execute; they must not be caught as a generic
control outage. Actual Ollama or ledger/control failure invalidates shared chat
availability. Recovery checks Redis controls, ledger access, model metadata and
profile before reopening, using existing manual recovery flow; no fallback.
ApiExceptionHandler maps the specified 503/429/502 responses and retry hints.

Use backend templates and DB identities. Exact-search work runs outside long
transactions; repositories own read-only queries, ledger updates are separate
short transactions. T0024-005 retrofits shared admission into 0017/0018/0022
without changing their selected models, vector schemas or import publication
atomicity. Changes are explicitly owned/allowlisted by this feature's task.

## Sources and Follow-up

The contract format follows [AsyncAPI 3.0.0](https://www.asyncapi.com/docs/reference/specification/v3.0.0).
Provider usage/profile qualification must verify the installed runtime against
[Ollama embedding metadata](https://docs.ollama.com/api/embed), not infer reliability
from documentation alone. After-0030 tooling/wording checkpoints stay in ADR-0024;
semantic search, document answers and broad quality work remain later features.

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
