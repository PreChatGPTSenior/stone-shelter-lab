# Feature 0025 Technical Plan

Status: Implemented and focused verification completed on 2026-10-13. Actual
provider-format corrections and retained failed attempts are recorded in ADR-0025.

Read [spec.md](spec.md), [acceptance.md](acceptance.md),
[tasks.md](tasks.md) and [ADR-0025](../../docs/adr/0025-semantic-stone-candidate-search.md).
This plan describes technical choices; acceptance criteria remain exclusively
in acceptance.md. Exact future writable-file allowlists and selectors are in
tasks.md. Documentation preparation does not authorize their execution.

## Existing Integration Points

- StoneChatbotService currently delegates completed turns to StoneExactSearchService.
  Retain its conversation serialization, turn deadline, committed replay and
  atomic history append; change the search delegate rather than duplicating them.
- StoneExactSearchService already owns effective exact criteria, UTC dates,
  trusted references and clarification. Add a reusable decision-driven preparation
  path and an overload consuming an already validated exact decision, preserving
  its existing entry point and exact-only behavior. The semantic coordinator must
  not trigger a second interpretation to execute a plain exact request.
- OllamaChatRepository accepts a supplied structured schema, bounds prompt size
  and returns usage. Reuse it with existing model settings, provider probes and
  shared ModelInvocationAdmissionService; do not add another client/library.
- StoneDocumentEmbeddingService accepts an explicit deadline/admission mode.
  New query work must use CUSTOMER, not the existing supporting retrieval paths'
  default background admission. Reuse its query instruction/profile and accounting.
- StoneCardEmbeddingRepository.search returns globally capped card IDs/scores
  without live filtering. StoneDocumentIndexRepository.search currently accepts
  only one to five passages. Add new bounded semantic query operations in a
  separate repository instead of changing those established supporting contracts.
- Current StoneEntity has factual biography/type/size but no color field.
  StoneDocumentFragmentEntity already has text, type scope and source provenance.
  StoneChatTurnEntity can hold optional internal metadata independently of public DTOs.

No ingestion, reindexing, event-processing, schema or version change is necessary.
Existing models, vector tables and DTO conventions remain the starting point.

## Interpretation, Effective State and Routing

Introduce StoneSemanticSearchProvider/OllamaSemanticSearchProvider and a strict
StoneSemanticSearchDecision containing the existing action/filter/reset/reference/
question/reason concepts plus effective semantic wishes. Wishes have stable
backend-validated keys and text; they preserve user meaning rather than adding
inferred properties. The model receives current input and necessary effective
state; optional older whole history pairs may be removed to fit, never current
input or silently required effective state.

Use StoneSemanticSearchValidationService for strict field sets, duplicate/trailing
JSON handling, enums, ranges, wish keys and decision consistency. Reuse exact
validation/preparation via explicit mapping; do not loosen the original exact
schema. Interpret with at most two calls, including its single repair. Give
clarification/unsupported decisions no catalog retrieval.

StoneSemanticSearchService coordinates a turn. Exact-only decisions go through
the decision-driven exact path; semantic decisions map to a domain criteria object
and a dedicated StoneSemanticSearchRequest for StoneSemanticCandidateService.search.
Trusted reference IDs are resolved by the backend before this request. Keep date,
reference and field-group behavior centralized in existing exact preparation.

Store effective wishes and result metadata on completed turns as an optional
StoneSemanticSearchStateEntity. Empty wishes after an explicit reset must be
distinguishable from absent metadata in old turns, so older wishes are not
accidentally resurrected. Preserve ordered presented references for ordinal
resolution. No Redis TTL, ownership or public request/response shape change.

## One Query Embedding and Bounded Source Queries

Render the effective semantic wishes deterministically for query embedding,
including their negation/meaning; exact constraints are SQL predicates rather
than substitutes for semantic wishes. Invoke the existing embedding service
once with query=true, CUSTOMER admission and deadline=min(turn deadline, now+50s).
Keep the ready vector/profile local to the search execution for reuse.

StoneSemanticSearchRepository owns vector SQL, joins, date/group predicates,
profile checks and database ordering. It consumes domain criteria and returns
entities, never Request/Response DTOs. Use parameterized queries over existing
stone, stone_card_embedding, stone_document_index and stone_document_fragment.

1. Retrieve configured top passages with compatible published profile and
   inclusive document threshold. Where exact type narrowing exists, include
   applicable type-scoped/shared passages before the passage cap. Order score
   descending, then document identity/fragment order for deterministic ties.
2. Build applicable type evidence from returned metadata; shared evidence is
   context for a known candidate type, never a fabricated type association.
3. Retrieve a deduplicated candidate pool that satisfies current AVAILABLE and
   every exact predicate before its configured cap. Eligibility for recall is
   qualifying card score or applicable qualifying document evidence.
4. Fetch current factual card fields with those candidates; retain scores and
   exact evidence provenance. Do not trust stale projection text for support.
   Document-supported candidates may lack a card vector; do not embed them on demand.
5. Use card score/ID ordering for card-derived recall, then applicable passage
   score/ID for document-derived recall. After evidence checks, apply the final
   accepted support-group order to this bounded pool. No exhaustive recall claim.

Use short read-only snapshots for consistent passage text/profile/provenance.
Do not keep a database transaction open during model calls. Incompatible profiles
and query failures propagate dependency failure, not empty matches. Missing
source content supplies no evidence; it is distinct from an unavailable database
or incompatible published profile. Tolerated indexing lag may affect recall.

## Evidence Classification and Context Packing

StoneSemanticEvidenceService supplies bounded StoneSemanticEvidenceRequest batches
to StoneSemanticEvidenceProvider/OllamaSemanticEvidenceProvider. These use the
same chat transport/settings/guard, with a separate compact classification schema.
Candidate input contains backend IDs, current factual card text, known type,
active wish keys/text and only applicable retrieved source segments.

The schema describes per-wish support source, unknown evidence or contradiction.
Supply stable source-reference IDs and require cited text to exist in the supplied
source. StoneSemanticEvidenceValidationService rejects unknown/duplicate/missing
IDs, irrelevant type references, fabricated quotes, contradictory result fields
and structurally invalid output. Treat supplied card/document content as data,
not instructions to change schema, IDs, filters or backend policy.

Aggregate evidence with explicit card precedence. Any card contradiction vetoes
the whole candidate. Otherwise, support from a card gives the first rank group;
only type support gives the second. One supported wish suffices; retain every
other unmatched wish. A card silence does not veto applicable type support.

Reuse the qualified byte/token bound and model-template validation; do not use
the embedding service's approximate text estimate as proof of chat capacity.
Fit system/instructions/source data/schema overhead and output allowance inside
the existing 8192/512 settings. Segment overlarge evidence at complete meaningful
boundaries with preserved source IDs, then batch candidates/wishes and aggregate
all required segments. Missing support may be concluded only after required
segments are processed. An indivisible required segment or mandatory state that
cannot fit produces explicit local context failure; no silent truncation.

Use a turn-wide evidence invocation counter, initially capped at 20. It covers
all batches, applicability work, at most one correction per malformed batch,
fallback checks and refreshed-pool checks. The interpreter retains its separate
two-call maximum; one query embedding is separate and charged normally. No
hidden transport retries. Check the same absolute deadline before each operation;
every call is bounded by remaining time and the 50-second maximum.

Each chat invocation acquires CUSTOMER capacity/reserves the existing context
bound, settles reliable reported input/output usage, and retains unknown usage
on timeout. Query embedding retains its existing 32768-token reservation bound.
Do not hold permits between calls. Reuse existing actual-exit/cancellation behavior
and avoid wrapping model invocation in a long service transaction.

## Results, Exact Alternatives and Refresh

After classification, sort by support group, source score and ID as specified.
For missing card scores in the card-supported group, place them after scored
members and use ID. Reject duplicates; do not fill a nonempty semantic set with
exact-only stones or scan beyond the bounded pool to replace evidence rejections.

If no semantic candidates qualify and effective exact narrowing exists, fetch
up to the candidate cap of current exact alternatives, newest admission then ID.
Check their current card evidence for contradiction using the same evidence
budget. They may lack wish support, but may not contradict a wish. No exact
fallback for a semantic-only request. This separate alternative pool is bounded;
do not infer a full post-classification database count from an exact SQL total.

Recheck current existence/AVAILABLE for the whole final pool in one bounded read.
Keep surviving order and use up to five. Derive foundCount/hasMore only from
survivors. A completely disappeared previously nonempty pool permits one full
retrieval pass with the same interpretation/vector and remaining evidence budget.
Classification of unchanged source snapshots may be reused only for the same
candidate/wishes/source content; new content must be checked. Initial empty or
evidence-rejected results do not refresh. The second pass cannot refresh again.

StoneSemanticSearchResponse is an internal class containing mode, bounded count,
hasMore, template text, candidates and effective state/evidence. It does not
replace the public StoneChatMessageResponse. Use explicit shared nested types
for candidate/wish/source metadata. Repository/evidence processing produces
domain entities; dedicated DTO mappers assemble ready responses. A named entity
mapper builds the completed turn from a domain-only prepared selection and returns
the result. This internal handoff supports 0026 without reverse DTO-to-domain
conversion; the mapped dedicated internal search response remains available.
Persist only displayed candidate IDs, effective wishes and checked proofs, not
JPA associations, projection vectors or execution caches.

Minimal English templates distinguish no matches, exact alternatives, partial
wishes and an additional-results notice. They do not assert color/use facts
absent from evidence or generate substantive explanations. Candidate links/names
come from the catalog, not the model. The structured handoff is ready for 0026.

## Configuration and Error Integration

StoneSemanticSearchSettings reads the four new environment settings in spec.md
and reuses StoneCardSearchSettings for the card threshold. Validate counts,
minimum-six candidate capacity and finite cosine-range thresholds explicitly.
Document names/defaults in environment-setup.md in T0025-003; do not edit root
README.md or persist local environment values.

Use InvalidStoneSemanticSearchOutputException for invalid semantic decisions and
StoneSemanticSearchLimitExceededException for local call/context exhaustion.
ApiExceptionHandler translates them to the specified 502/503 ProblemDetail codes.
StoneChatAvailabilityService must treat local limits/invalid output as ordinary
failed turns, preserving healthy history/replay. Actual provider/control failures
keep the existing shared fail-closed recovery. Capacity and budget exceptions
retain their existing mappings and retry guidance.

Keep controller responsibilities limited to routing/binding/validation and
generated OpenAPI annotations. Services/exceptions remain protocol-independent;
repositories own query APIs. Records have at most four fields; larger types are
classes with no-argument constructors/private fields/accessors. Named mappers
follow AbstractEntityMapper/AbstractDtoMapper contracts and returned transformations.

## Qualification, Ticket Sequence and Readiness

T0025-001 precedes every requested batch and checks every selected ticket.
T0025-002 uses the installed models for a small fixed functional set before
dependent application implementation. Qualify the semantic decision/evidence
formats, usage, prompt/output packing and profile behavior without tuning or
changing models. Production prompt/provider changes require the named final
recheck in T0025-006; success with prototypes is not final provider acceptance.

Then implement repository/settings (003), bounded evidence checking (004), and
chat orchestration/history/error integration (005), before the focused review (006).
Each ticket has exact paths and selectors in tasks.md. Stop at the requested
ticket boundary; existence of the sequence is not permission to execute it.

Readiness checks use authorized command-scoped values and endpoints actually
used by the selected runtime/tests. Verify Maven 3.9.9 with JAVA_HOME selecting
JDK 23.0.2, approved Docker/Testcontainers support, PostgreSQL 18.6/pgvector 0.8.6,
Redis 8.2.10 where history/control is used, and effective model/ledger settings.
Verify the previously approved Ollama 0.40.3 runtime recorded in feature 0024,
installed qwen2.5:3b and qwen3-embedding:0.6b identities/digests/profiles. Report a
pin/configuration discrepancy rather than substituting an unverified runtime.

Check authenticated connectivity, vector extension and required existing table/
profile state for actual application use. Test fixtures use isolated pinned
Testcontainers and own all inserted data; they do not require a production import.
Document retrieval reads PostgreSQL projections; it does not require a live MinIO
fetch or Kafka round-trip. Report existing MinIO/Kafka/backend/frontend states and
their selected-task impact; do not start irrelevant services or claim unchecked
components ready. Start required installed/existing services as routine preparation
and distinguish startup from read-only probes. Missing models/schema/configuration
or unhealthy required endpoints block the entire affected batch.

Do not read private .env, build outputs, dependency manifests or framework config
for speculative changes. Approved environment-setup instructions and session
configuration supply known values; secrets are reported only present/missing.
No inference, business import or application test is a readiness probe.

Verification runs only the named selector using ./mvnw -Dtest=<selector> test
from stone-shelter-api with explicit controlled JAVA_HOME/endpoints. No full suite,
benchmark, broad query set, host model download, production import, commit or push.
Generated HTTP contract checks inspect both JSON and YAML OpenAPI, never a
handwritten document. Qualification blockers require a reported agreed correction,
not changes outside the task's exact writable paths.

## Qualified Implementation Details

Use one source per classification call, packing supported wish groups within
512 and complete meaningful source segments within 8192. The source-specific
format uses short aliases validated back to canonical provenance and strict
verbatim quotes up to 48 UTF-16 units. One precise bounded repair consumes the
same turn-wide cap. Backend aggregation applies current-card precedence. Omit a
null biography from factual text instead of creating a Biography: none sentinel;
all actual supplied card facts remain intact. Full-source arrays/other diagnostic
protocols were not claimed qualified and their failures remain in ADR evidence.
