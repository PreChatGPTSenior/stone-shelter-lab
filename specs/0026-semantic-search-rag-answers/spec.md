# Feature 0026: Semantic Search and RAG Answers

Status: Implemented; focused final-provider, controlled integration and architecture
verification completed. Final acceptance evidence is recorded below and in ADR-0026;
broader fixed-query/human answer qualification remains feature 0027.

## Goal and References

Answer English requests using eligible stone candidates, reference passages and the existing conversation flow.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Detailed questions, answers and decisions: [ADR-0026](../../docs/adr/0026-semantic-search-rag-answers.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Modes and Request Routing

- Support selection explanations and knowledge-only questions about properties,
  care and stone types. Knowledge questions do not require catalog selection.
- Support mixed requests such as "Find a small basalt and tell me how to care
  for it" in one response. Distinguish requested explanatory topics from actual
  exact constraints or semantic selection wishes; do not silently add filters.
- Interpret and route each turn once through a strict structured decision, allowing
  at most the existing single interpretation correction. Reuse decision-driven
  0025 execution rather than invoking a second interpreter for selection.
- Preserve inherited missing-year/reference/generic-request clarification where
  applicable. A knowledge question about a type is not an unfiltered specimen search.
- Exact and semantic selection use 0025/0024 eligibility, order, counts, partial
  wishes, exact alternatives and zero-result behavior. Explanation cannot add
  candidates, reorder them or relabel unfulfilled wishes as satisfied.
- A selection with no candidates or exact alternatives returns the short
  absence-only text without answer-generation or condition-relaxation suggestions.
  Knowledge-only questions remain independently answerable.

## Evidence, General Knowledge and Visibility

- Individual card facts take priority, then applicable encyclopedia information,
  then general model knowledge as a supplement contradicting neither.
- Use current card facts for individual specimens and type-scoped/shared indexed
  passages for general knowledge. Preserve source profile/provenance internally.
  General/type knowledge cannot establish an undocumented individual property.
- If information is insufficient, say so briefly without naming the missing
  source. A valid insufficient-information response differs from a runtime error.
- Normal selection answers contain a short introduction and an explanation for
  every presented stone, at most five. State why it fits and which wishes did
  not fit; clearly distinguish exact alternatives from semantic matches.
- Do not show citations, document/section names as attribution, source URLs/IDs,
  similarity scores or prose identifying where information came from. Catalog
  links identify specimens and remain allowed; they are not provenance citations.
- Backend validation owns selected IDs/order and final links. Validate generated
  structure and the displayed prose; hidden structured citations alone are
  insufficient if the prose reveals a source or invents individual claims.
- Include a structured grounding/visibility check of the generated text against
  permitted facts/wishes, source hierarchy and attribution rules. This uses the
  existing shared evidence-call allowance, not an unaccounted model invocation.

## Retrieval, Context and Shared Limits

- Reuse relevant checked evidence from 0025. For a new explanatory topic or a
  knowledge-only question, retrieve compatible indexed passages, initially up
  to 10 at an inclusive 0.5 threshold. Reuse the independently configurable
  STONE_SEMANTIC_SEARCH_DOCUMENT_LIMIT and
  STONE_SEMANTIC_SEARCH_DOCUMENT_MIN_SIMILARITY settings.
- A supplementary topic may compute a second query embedding. At most two query
  embeddings are permitted across the turn; no per-stone, repair or refresh
  embeddings. Reuse unchanged query/profile vectors and applicable snapshots.
- Keep the original 120-second turn deadline, including interpretation, source
  queries, 0025 evidence, generation, checking and finalization. Calls are bounded
  by remaining time and at most 50 seconds. No stage starts a new turn deadline.
- Reserve the last five seconds for final eligibility/template/history work.
  Clip model deadlines to this earlier work deadline; the reserve is a technical
  completion margin, not extra time or a guarantee that persistence always finishes.
- Initial total model context remains 8192 tokens. Interpretation/evidence output
  remains 512; answer generation uses CHAT_RAG_OUTPUT_TOKENS, initially 1024,
  reserved within the total context. Validate a positive output below context.
- CHAT_RAG_MAX_GENERATION_CALLS initially permits four calls per turn, configurable
  from one to four. A selection permits one main/corrective cycle per original
  and permitted refreshed pool. Knowledge-only has one cycle, no selection refresh.
- Permit one correction per invalid generated answer. Corrections consume the
  same generation counter, deadline and budget; no hidden transport retries.
- Share the existing initial 20-call evidence counter across selection and final
  prose checks, including corrective/refreshed-answer checks. Never reset it on
  route changes or reuse it as an additional independent allowance.
- Every model invocation uses existing CUSTOMER admission, initially N=1, UTC
  daily token budget initially 100000 and reliable reservation/settlement. Chat
  reservations cover the full configured context; query embedding retains its
  qualified bound. Unknown usage remains reserved; permits last until actual exit.
- Fit current input, required state, evidence and output allocation using the
  qualified tokenizer/template bound. Trim optional old whole history pairs,
  not current input. Segment/check evidence without silent truncation. A context,
  counter or deadline failure must not expose unchecked generated prose.

## Follow-ups and Catalog References

- Knowledge-only turns preserve current exact criteria and semantic wishes.
  They do not reset a search merely by explaining its results or another type.
- Resolve "the second stone" from the last actual nonempty selection, and page
  references through inherited trusted context. Ask for clarification when the
  reference is ambiguous, missing or no longer eligible; do not substitute a stone.
- An explanation about a referenced current specimen includes one catalog link.
  A general type answer contains no specimen links. This single convenience link
  does not replace the selection list used by later ordinals or change the route
  into selection for failure/fallback purposes.
- Store route mode and selection-reference identity as optional internal completed
  metadata. Legacy nonempty turns without that metadata remain selection references.
- A referenced specimen becoming unavailable before a knowledge answer returns
  receives inherited reference clarification; no arbitrary replacement selection.

## Late Eligibility, Refresh and Final Text

- Recheck current existence/AVAILABLE after generation and before returned links.
  Remove vanished selection candidates and their explanations. If any survive,
  show the smaller generated subset without another search or generated refill.
- Rebuild introduction/count framing for survivors; do not retain text referring
  to vanished stones or an obsolete count. Backend-controlled labels/counts must
  remain consistent with the final checked pool.
- If all selection candidates disappear, use 0025's single turn-wide retrieval
  refresh only if unspent; explain the new eligible results under the same limits.
  A refresh already used before generation cannot be used again afterwards.
- Initial zero/evidence rejection and partial late survival do not trigger a
  refresh. If no eligible results remain after the allowed opportunity, return
  the suitable-candidates-not-found outcome without another generation loop.

## Failure and Template Completion

- When selection/evidence is valid but final generation/checking cannot complete
  because of admission, budget, context/call bounds, timeout, provider failure or
  still-invalid corrected prose, discard that prose and use a backend stones
  template. It retains current links/order, partial-wish/alternative labels and
  five-stone/overflow rules; polished per-stone explanations are not required.
- This is explanatory fallback, distinct from exact alternatives. It cannot
  rescue failed interpretation, candidate selection, missing evidence checks,
  invalid IDs or unavailable/uncertain database/history controls.
- If Ollama fails only during final explanation of an already checked selection,
  that current admitted turn may complete its template while later send/history/
  replay remain fail-closed until verified recovery. This is the explicitly
  accepted narrow exception to 0024's global model-availability assertions.
- A template must still pass healthy ledger/database/history controls, current
  ownership, serialization/fencing and the original deadline. Do not globally
  reopen chat, expose a bypass parameter or commit after expiry. If safe template
  completion fails, return the inherited explicit error and retain reconciliation.
- Successful generated or template answers commit one completed pair atomically.
  No failed generated text is persisted as the answer. Unknown model usage is
  not refunded because a template succeeded or a cancellation was requested.
- Knowledge-only generation/checking failure returns an error without a completed
  pair, even when a referenced specimen has a convenience link. A legitimate
  insufficient-information answer is a successful pair, not a generation failure.

## HTTP and Internal Contract Requirements

Keep POST /api/v1/chat/messages, existing history routes, ownership, input limits,
turnId replay, public successful text/reference fields and retention. Internal
route/evidence/provenance/counter/template metadata must not become new HTTP or
history fields. Generated /v3/api-docs and /v3/api-docs.yaml remain the HTTP source
of truth; maintain no handwritten OpenAPI document.

The internal generation request supplies mode/question, known ordered candidates,
permitted evidence and the shared execution bounds. Its structured draft contains
an introduction, keyed per-stone text and/or knowledge text. Backend code validates
and maps ready results to existing public DTOs; the model never chooses final
catalog links or creates a new search tool/SQL operation.

- Successful generated/eligible template responses use HTTP 200.
- Knowledge-only invalid output after correction uses HTTP 502
  CHAT_INVALID_MODEL_OUTPUT.
- Local generation/context exhaustion uses HTTP 503 CHAT_RAG_LIMIT_EXCEEDED;
  shared evidence exhaustion retains the existing semantic-limit outcome when
  it cannot be handled by a valid selection template.
- Capacity rejection and daily-budget exhaustion retain existing HTTP 503/429
  codes and retry hints. They may be handled by a valid prepared selection
  template only at final explanation, not initial request admission.
- Actual provider/control unavailability or unsafe completion uses existing
  HTTP 503 CHAT_UNAVAILABLE and verified recovery. Local invalid output/limits
  do not by themselves close unrelated healthy history/replay.

ApiExceptionHandler owns RFC 9457 ProblemDetail/status/header translation.
Services/custom exceptions remain protocol-independent. Committed replay performs
no new retrieval, interpretation, embedding, generation or reservation, and follows
the accepted shared availability policy rather than invoking the template exception.

## Boundaries and Execution Gate

Candidate selection remains 0025; document ingestion remains 0017; broader
query/answer quality qualification is 0027. Basic functional real-model prompt,
output/usage and packing qualification remains mandatory here. No analytics,
streaming, external-source browsing, schema/dependency change or frontend source
work is introduced by this plan.

OOS-0026-001 excludes user-visible source attribution, as recorded in ADR-0026
and the [central register](../../docs/out-of-scope-decisions.md). Hidden provenance
does not remove internal grounding or uncertainty obligations.

## Dependencies

- [0025-semantic-stone-candidate-search](../0025-semantic-stone-candidate-search/spec.md).
- [0017-markdown-document-indexing](../0017-markdown-document-indexing/spec.md).
- [0016-llm-search-conversation-history-and-context](../0016-llm-search-conversation-history-and-context/spec.md).
- [0024-exact-llm-search](../0024-exact-llm-search/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

Read constitution/local instructions and this feature's acceptance/plan/tasks/ADR
before execution. T0026-001 must cover every explicitly requested ticket; then
T0026-002 qualifies the new model contracts before dependent implementation.
Require delivered 0025 contracts before integrating them; its prepared documents
are not implemented classes or runtime evidence. Never execute missing 0025
tickets implicitly. Report qualification/dependency/scope blockers.

This preparation authorizes documentation only. No application edit, inference,
service startup, download, import, test, commit or push has been performed or
authorized by finalizing these documents.
