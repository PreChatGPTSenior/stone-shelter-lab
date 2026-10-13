# Feature 0026 Technical Plan

Status: Prepared from the accepted discussion. No implementation, model
qualification or current environment readiness has been executed.

Read [spec.md](spec.md), [acceptance.md](acceptance.md), [tasks.md](tasks.md)
and [ADR-0026](../../docs/adr/0026-semantic-search-rag-answers.md).
Acceptance criteria live exclusively in acceptance.md. Future writable files and
test selectors are exact in tasks.md; their existence is not execution authority.

## Existing Code and Planned Dependency

The inspected current code is the 0024 flow: StoneChatbotService delegates to
StoneExactSearchService and asserts global chat availability before/after history
append and response completion. StoneChatAvailabilityService exposes a global
ready flag and invalidates on provider/control failures. These checks cannot
simply remain unchanged for the accepted generation-only template exception.

Feature 0025's semantic pipeline, structured evidence DTOs and optional history
state are prepared contracts, not inspected implemented classes. Batches integrating
them require their separately authorized delivery first. Do not run 0025 tickets
implicitly or infer readiness from the prepared documents.

Reuse existing OllamaChatRepository, StoneDocumentEmbeddingService, model settings,
guard/token ledger, serialization/history and public response mappers. Reuse 0025
candidate/evidence services through decision-driven and caller-execution-state
overloads. Leave their independent entry points and legacy pure-exact behavior
intact. No dependency, schema, document import or frontend change is planned.

## Routing and Shared Execution State

StoneRagConversationService completes a turn through one strict structured
StoneRagDecision and the provider boundary StoneRagRoutingProvider. It selects
SELECTION, KNOWLEDGE, CLARIFY or UNSUPPORTED, carrying a selection decision where
needed, explanatory question and trusted-reference intent. A mixed request is
SELECTION with an explanatory topic, not a second user turn or another interpreter.

Validate action/field consistency and map via dedicated named mappers. Reuse exact
date/reference preparation, effective wishes and one interpretation correction.
Delegate the validated selection decision directly to the 0025 pipeline; do not
call its ordinary model-interpreting entry point again. Knowledge-only skips
catalog selection while resolving an explicit current specimen reference.

StoneRagExecutionEntity is internal mutable turn state with the original deadline,
earlier model-work deadline, query/vector cache, interpretation/evidence/generation/
embedding counts and spent-refresh flag. Domain entities/criteria do not depend
on shared DTOs. Extend 0025 service overloads to use its shared counters without
resetting them; standalone 0025 entry points preserve their established defaults.

StoneRagSettings reads CHAT_RAG_OUTPUT_TOKENS=1024 and
CHAT_RAG_MAX_GENERATION_CALLS=4. Validate positive output below context and a call
cap from one to four. Context/call/turn settings retain existing approved values;
document settings reuse 0025's cap/threshold. Use a five-second internal completion
reserve; model work ends before original deadline-5s. If insufficient time remains
to start a stage, select its valid fallback/error route rather than restart time.

## Knowledge Retrieval and Evidence Preparation

StoneRagKnowledgeService accepts a dedicated request and returns a mapped ready
response containing bounded internal evidence and optional referenced specimen.
StoneRagKnowledgeRepository owns live-card lookup and query predicates. Reuse
0025 passage retrieval over existing tables; no MinIO read/import at answer time.

For selection explanation, reuse checked card/type evidence where it answers the
topic. When a care/property topic differs, embed the relevant explanatory query
once and retrieve type-applicable/shared passages under the configured cap/threshold.
Knowledge-only ordinarily does this once. Share a two-query-embedding turn cap,
compatible profile checks and CUSTOMER admission; refresh/correction reuses query
cache. All embeddings reserve/settle through the existing ledger and clipped deadline.

Use immutable current-card and indexed-passage snapshots with internal provenance.
Do not use stale card-vector text as factual authority, and do not keep a DB
transaction open during model inference. Missing evidence may be supplemented by
noncontradictory general type knowledge; unavailable/incompatible dependencies are
errors. A current individual property still needs individual evidence.

Prepare a bounded fact/wish/evidence inventory with card > encyclopedia > general
precedence, preserving type-versus-individual distinctions. General model knowledge
cannot erase a project-world description or supply missing specimen properties.
Only source-free prose and allowed catalog links reach the user.

## Generation and Final-Text Checking

StoneRagAnswerService calls StoneRagAnswerProvider/OllamaRagAnswerProvider through
existing guarded transport. Its internal schema has a short introduction, keyed
per-stone explanations and/or knowledge text. No generated catalog URL or new
candidate identity is authoritative. Use ready catalog names/IDs in final mapping.

Keep generation output within 1024 initially, including JSON overhead, reserved
inside 8192 context. Qualify the compact prompt with required current input/state
and bounded evidence; remove only optional older whole history pairs. Do not use
approximate embedding estimates as chat capacity proof. A mandatory input segment
that cannot fit produces explicit failure/template routing, not truncation.

StoneRagAnswerValidationService first enforces strict JSON, required candidate
coverage/order, permitted IDs and response shape. StoneRagGroundingService then
uses a structured provider to check rendered prose against permitted facts/wishes
and internal provenance: unsupported individual claims, contradicted wishes and
visible attribution are failures. General type statements may be approved only
without conflicts and without pretending to be individual evidence.

Check bounded draft/evidence segments with sufficient source context; aggregate
all required verdicts before accepting prose. Checker calls and their at most
one structured-output correction per invalid batch use the existing shared
20-call evidence counter. They are distinct from generation's four-call counter.
An invalid answer receives at most one generator correction per pool. If any
required verification cannot complete, use the selected fallback/error policy.

Both providers use qwen2.5:3b, existing model probes, finite remaining-time/50s
deadlines and reliable 8192 context reservations. Keep failed/unknown usage and
actual-exit permit handling. No unaccounted model call, automatic transport retry
or permit held between invocations. A four-call maximum does not promise enough
time or daily budget for every permitted cycle.

## Eligibility, Text Assembly and Generation Fallback

Keep a backend-owned selection snapshot and template-ready result independently
of generated prose. Generation cannot alter candidate order, wishes, mode or count.
After checking, recheck current existence/AVAILABLE and remove vanished explanations.
Partial survival produces fewer items with backend-rebuilt framing, not another
generated refill. Complete loss may consume the one shared retrieval refresh;
recheck new evidence and generate under the remaining shared limits. No retry
for initial zero or an already spent refresh.

Selection generation/checking failure, including an invalid correction, may use
the checked snapshot's minimal template. Do not commit failed draft text. Templates
retain semantic/partial/exact-alternative and overflow framing from 0025, with
catalog identities. Zero selection returns the existing short absence text with
no generator call. Knowledge-only has no selection-template fallback, even with
a convenience link; unsafe/missing reference clarifies instead of substituting.

Use a domain StoneRagAnswerResultEntity and dedicated DTO mapper for a ready
internal StoneRagAnswerResponse. A named entity mapper updates/returns the completed
turn. Services never construct/populate Response DTOs directly. Controllers retain
binding/status/OpenAPI responsibilities and existing public shapes.

## Current-Turn Template Exception and Safe Commit

Introduce a backend-only StoneRagPreparedTurnEntity identifying a completed strict
answer or a validated selection template eligible for current-turn completion.
This artifact is produced after successful initial admission/selection/evidence;
it is not a model-supplied flag or a public request field.

Replace the indiscriminate post-generation global-ready assertions only for this
trusted template path. StoneChatAvailabilityService separately verifies reliable
ledger/database/history controls, current ownership and the active serialization
fence; StoneChatbotService and history append enforce the original processing
deadline. Narrowly allow model-only generation failure while the global gate
stays closed. Never call recover or flip global ready just to commit a template.

Keep strict availability for initial send, history and committed replay. A
failure/unknown state in ledger, Redis, fencing, selection or history commit is
not generation-only eligibility and cannot bypass it. Bind the exception to the
current admitted turn and the ready checked snapshot; successful replay later
still follows the shared closed/recovered gate and performs no new inference.

Reserve completion time and ensure history's atomic append cannot publish a
late/cancelled worker result after the processing deadline/fence expires. Preserve
commit-outcome reconciliation: a lost acknowledgement is not proof of rollback.
Do not release a provider's actual-exit lease or refund unknown usage to obtain
template success. Atomic successful template completion saves exactly one pair.

## Selection References and Internal History

StoneChatReferenceService resolves ordinals from the last nonempty actual selection.
Skip KNOWLEDGE turns with a convenience link; legacy nonempty turns without new
route metadata count as selections. Store optional StoneRagTurnStateEntity with
route/template/selection-reference metadata and preserve effective 0025 search state.

A referenced specimen knowledge answer has one current link; a type answer has
none. The link does not change failure mode or last-selection identity. Current
invalid/unavailable references keep clarification, including late knowledge
reference loss; no selection refresh is created for knowledge-only explanation.
Existing public history mappers expose text/links only, not internal state/sources.

## Errors, Qualification and Environment

Use protocol-independent InvalidStoneRagOutputException and
StoneRagAnswerLimitExceededException; ApiExceptionHandler owns the specified
502 CHAT_INVALID_MODEL_OUTPUT and 503 CHAT_RAG_LIMIT_EXCEEDED mappings. Preserve
existing shared capacity/budget/evidence/unavailable outcomes and retry guidance.
Local failures do not invalidate healthy history/replay; actual dependency/control
failure remains closed apart from the narrowly qualified current template.

T0026-001 checks every ticket in the explicitly requested batch. Verify approved
Maven 3.9.9/JAVA_HOME JDK 23.0.2, Docker/Testcontainers, required PostgreSQL
18.6/pgvector 0.8.6, Redis 8.2.10 and reliable ledger/control endpoints. Verify
previously approved Ollama 0.40.3 and installed selected chat/embedding digests/
profiles; metadata checks are not inference. Use actual backend-reachable addresses
and authorized command-scoped values; never print secrets or inspect private .env.

For production tasks T0026-003–006 require the delivered 0025 source contracts and
focused verification, not just its task list. A standalone test-only qualification
can use documented prototypes with existing transport/guard, but does not establish
that production dependencies exist. Missing required contracts block the entire
affected requested batch; never implement the missing feature without instruction.

Report actual backend/frontend and other project service states with ticket impact;
MinIO/Kafka are not answer-time dependencies of existing PostgreSQL projections.
Start required existing services only as routine preparation, distinguish startup
from read-only probes and recheck. No inference/import/application test is readiness.
No source provisioning/download or persistent configuration change is authorized here.

T0026-002 qualifies a small fixed schema/context/usage set before dependent code;
T0026-006 rechecks final production providers after their inputs change. Controlled
providers and isolated test-owned PostgreSQL/Redis data cover other focused tests.
Run only exact ticket selectors with controlled environment, never a full suite.
No performance targets, model tuning or broad query campaign; those require 0027's
separately discussed scope. Generated JSON/YAML OpenAPI is checked in integration,
not replaced with a handwritten contract. Preserve responsibility packages,
mapper contracts and at-most-four-field records throughout.
