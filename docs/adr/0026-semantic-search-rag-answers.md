# ADR 0026: Semantic Search and RAG Answers

Date: 2026-10-13

Status: Accepted for documentation preparation. All 19 presented questions have
answers. The specification, acceptance criteria, plan and tasks define the agreed
feature; implementation, model qualification and current readiness are not claimed.

Feature: [0026: Semantic Search and RAG Answers](../../specs/0026-semantic-search-rag-answers/spec.md).

## Context and Accepted Discussion Procedure

The user requested sequential discussion and documentation preparation for
features 0025 through 0027, without code. Questions are asked directly in chat;
each actual question, proposed alternatives, answer and resulting decision must
be recorded in detail. After all questions for a feature are settled, prepare
its specification, acceptance criteria, implementation plan and exact tasks.

The user's workflow instructions are recorded, with their translated text, in
[ADR-0025, D0025-001 and D0025-002](0025-semantic-stone-candidate-search.md).
The feature 0025 documentation is now prepared; this does not imply implementation
or runtime readiness. Its structured evidence/result contract is this feature's
planning input, not an already verified runtime dependency.

Relevant sources:

- [Feature 0026 draft](../../specs/0026-semantic-search-rag-answers/spec.md),
  [acceptance.md](../../specs/0026-semantic-search-rag-answers/acceptance.md),
  [plan.md](../../specs/0026-semantic-search-rag-answers/plan.md) and
  [tasks.md](../../specs/0026-semantic-search-rag-answers/tasks.md).
- [Feature 0025 specification](../../specs/0025-semantic-stone-candidate-search/spec.md)
  and [detailed ADR](0025-semantic-stone-candidate-search.md).
- [Shared discovery](../../specs/0015-llm-search-discovery/spec.md) and
  [ADR-0007](0007-llm-search-discovery.md).
- [Document indexing](../../specs/0017-markdown-document-indexing/spec.md).
- [Conversation history/context](../../specs/0016-llm-search-conversation-history-and-context/spec.md).

## Existing Baseline — Not New Answers

- Delivery is English only, with existing local model/provider and shared
  resource-control choices as the starting point.
- Feature 0025 owns bounded candidate retrieval, factual evidence checks,
  exact alternatives, partial-wish metadata and current catalog eligibility.
- Cards take priority for individual specimens; type-level encyclopedia
  knowledge supplements cards and cannot override an individual contradiction.
- Type knowledge does not guarantee an undocumented individual property.
- At most five selected stones are presented, with an additional-results notice
  when verified additional candidates exist. Showing the rest through a catalog
  view filtered by their IDs is deferred.
- Missing candidate evidence is not invented; partial matches identify
  unsupported wishes. Exact alternatives are distinguished from semantic matches.
- Feature 0026 owns substantive explanation/answer generation using the evidence;
  feature 0027 owns broader query-quality evaluation.
- Shared discovery allows general model knowledge for explanations, but not to
  establish undocumented individual properties. How this is represented and
  how insufficient/conflicting explanation evidence is handled still need discussion.

These requirements are inherited from the linked records, not reconstructed
historical question-and-answer transcripts. No runtime check, model call or
application test has been performed for this discussion.

## Discussion Log

### Q0026-001 — Should explanation also work without catalog selection?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Should feature 0026 support both explaining found stones and answering a
> question such as "How do I care for basalt?" without searching the catalog?
> I propose supporting both modes.

Reason: the draft names explanations and follow-ups but must distinguish a
catalog-selection request from a knowledge-only request. A knowledge question
need not produce candidate links or an arbitrary catalog selection. The proposal
does not establish routing actions, passage limits or generation-call bounds.

User answer, translated into English: "1: Yes, both modes."

Accepted decision D0026-001:

- Support explaining candidate-selection results and knowledge-only questions.
- A type-knowledge question does not require catalog selection or arbitrary
  stone links. Candidate eligibility/selection remains owned by 0025 when needed.
- Concrete routing and resource handling for both modes will be defined before
  the feature documentation is finalized.

### Q0026-002 — What explanation accompanies each presented stone?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> For selection results, should there be one overall explanation or a short
> explanation for each of the up to five displayed stones? I propose a short
> overall introduction and an individual explanation identifying why each stone
> fits and which wishes were not fulfilled.

Reason: per-candidate evidence is available in the planned 0025 contract, but
that does not yet determine answer structure, length or whether all five need
individual prose. Explanations must preserve candidate order, identities and
partial-match distinctions rather than generating new recommendations.

User answer, translated into English:

> 2: An explanation for each, and a short introduction.

Accepted decision D0026-002:

- Selection answers have a short introduction followed by an explanation for
  every presented stone, up to the accepted five-stone maximum.
- Each explanation identifies fit and unfulfilled wishes when applicable,
  preserving the structured candidate evidence, identities and order from 0025.
- The user accepted this layout rather than an overall explanation alone.
  Length/context/output settings have not been selected by this answer.

### Q0026-003 — Should encyclopedia provenance be visible in answers?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Should the answer visibly identify the source as the stone card or an
> encyclopedia section, or should provenance remain internal? I propose a short
> source attribution without technical IDs or similarity scores.

Reason: stored source provenance supports grounded explanations but does not
establish user-visible citation wording, links or the public response format.
This asks for a visibility preference, not permission to expose private source
locations or add arbitrary metadata to public success DTOs.

User answer, translated into English:

> 3: Do not show the source to the user at all. They should not know where
> the information comes from.

Accepted decision D0026-003:

- Do not display source attribution, document/section names, provenance IDs,
  source links, similarity scores or statements identifying a card/encyclopedia
  as the information source in the user-facing answer.
- The assistant's proposal for short visible source attribution is rejected.
- Keep source provenance internally for grounding, precedence checks and later
  qualification; this answer concerns user visibility, not removal of evidence.
- Stone links identify selected catalog items and remain part of the agreed
  answer; they are not document-source citations.
- Do not add public response/history provenance fields. The explanatory text
  must also avoid revealing provenance, not merely hide structured metadata.
- This visibility choice does not authorize inventing facts or concealing
  uncertainty as certainty. Type-level and individual-property distinctions
  still apply even without visible source attribution.

Accepted exclusion OOS-0026-001 is recorded below and in the central register.

### Q0026-004 — May an explanation change the selected candidate set?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose that feature 0026 explains the candidates selected by 0025 without
> adding other stones, changing their order or claiming that unmatched wishes
> were fulfilled. If evidence is insufficient for an individual property, the
> answer says it could not be confirmed without identifying the source. Agree?

Reason: generative wording must preserve backend-owned selection and factual
classification. Hidden provenance does not allow a new recommendation, changed
ranking or invented individual detail. This proposal concerns explanation, not
the already accepted general no-semantic-match/fallback outcome.

User answer, translated into English: "4: Yes."

Accepted decision D0026-004:

- Explanation must preserve backend-selected candidate identities and order.
  Do not add other stones or replace candidates through generated wording.
- Preserve matched/unmatched wishes from 0025. Do not turn partial support or
  an exact alternative into a claim that every semantic wish was satisfied.
- If an individual property lacks evidence, state that it could not be
  confirmed without identifying where information was sought.
- This invariant complements D0026-003: hiding provenance does not authorize
  invented certainty, and a generated explanation is not a second selection API.

### Q0026-005 — How should knowledge-only follow-ups resolve references?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> After a selection, should "How do I care for the second one?" answer about
> the second stone in the latest presented list, and "And what about basalt?"
> answer about the type? I propose reusing 0024's reference rules and asking for
> clarification when it is ambiguous; explanation alone does not start a new
> selection or clear the current search wishes.

Reason: inherited ordinal/page references and semantic search state need an
explicit explanation-mode handoff. Knowledge-only turns should not accidentally
replace the candidate list or reset narrowing conditions needed by a later
"now only small ones" search. Missing/unavailable references keep the existing
clarification rule rather than being silently substituted.

User answer, translated into English: "5: Yes."

Accepted decision D0026-005:

- "How do I care for the second one?" resolves to the second stone in the
  latest presented ordered list; "And what about basalt?" may address the type.
- Reuse inherited ordinal/page reference resolution and clarification for
  ambiguous, missing or no-longer-eligible specimen references.
- Explanation alone does not start a new specimen selection or clear effective
  exact constraints/semantic wishes. A later search follow-up retains them.
- A knowledge-only turn with no new displayed stones must not erase the latest
  earlier nonempty presented list used for references.

### Q0026-006 — Should an empty selection still trigger explanation generation?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> When no candidates were found and there is no exact fallback, I propose
> retaining the short absence-only response from 0025, without another model
> call to explain it or suggest changed conditions. Knowledge-only questions
> still use their own answer mode. Agree?

Reason: shared zero-result behavior already prohibits automatic relaxation
suggestions. Avoiding a generation call for an empty selection preserves that
behavior and resource budget without conflating it with a knowledge question.

User answer, translated into English: "6: Yes."

Accepted decision D0026-006:

- When selection produces no candidates and no exact alternatives, retain the
  short absence-only response and perform no answer-generation call.
- Do not generate reasons to relax conditions or propose changed conditions
  for that empty selection.
- Knowledge-only questions remain a distinct mode; an empty catalog-selection
  result is not required to answer a question about a type's care/properties.

### Q0026-007 — May answer generation use a separate larger output allowance?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> An introduction plus explanations for five stones may not fit the 512-token
> structured-output allowance. I propose a separate answer-generation cap,
> initially 1024 output tokens, retaining the 8192-token context and existing
> 120-second turn/50-second call limits. Is that suitable as a starting setting?

Reason: 512 remains the established interpretation/evidence cap; generation
needs its own declared allocation within the total model context. The proposed
1024 is an initial bound, not a demonstrated capacity or latency target. Basic
prompt/context/output qualification and ledger reservation remain required;
agreement does not authorize tuning, inference or changing the shared deadline.

User answer, translated into English: "7: Yes."

Accepted decision D0026-007:

- Introduce a separately configurable answer-generation output cap, initially
  1024 tokens. Keep interpretation/evidence outputs at their existing 512 cap.
- Keep an initial total context of 8192 tokens, including the generation output
  reservation, rather than adding 1024 on top of that context.
- Retain the 120-second turn deadline and 50-second maximum per call; generation
  shares the original turn deadline with interpretation/retrieval/evidence.
- A chosen cap is not evidence that every five-stone answer/context fits or
  completes within the deadline; basic qualification remains a future gate.

### Q0026-008 — Which evidence wins over general model knowledge?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> General model knowledge was allowed in the shared discussion. I propose
> individual-card facts first, encyclopedia knowledge next, and general model
> knowledge only as a supplement that does not contradict either. If information
> is insufficient for an answer, say so briefly without identifying sources.
> Do you agree with that priority?

Reason: allowing general knowledge does not select its precedence when the
project encyclopedia and model's generic knowledge disagree. This is especially
relevant to project-world type descriptions. Hidden attribution still permits
an honest insufficient-information outcome.

User answer, translated into English: "8: Yes."

Accepted decision D0026-008:

- Individual card facts take priority, then applicable encyclopedia knowledge,
  then general model knowledge as a supplement that contradicts neither.
- General knowledge does not establish undocumented individual properties.
- If the evidence/knowledge is insufficient to answer, state that briefly
  without exposing which source lacked information.
- The answer can describe a type generally without representing every type
  tendency as a confirmed property of the referenced specimen.

### Q0026-009 — How many corrections may generated answers receive?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If a generated answer contains an unknown stone ID, changes the selected
> order, reveals information sources or claims an unsupported individual
> property, I propose one corrective model call. If the correction is still
> invalid, fail without saving a completed conversation pair. Do you agree?

Reason: candidate selection remains backend-owned, but generation must satisfy
the accepted shape/content/source-visibility rules. A correction is an additional
charged invocation within the original deadline, not permission for an unbounded
retry or silently presenting an invalid answer. Concrete validators and failure
translation will be specified after the behavior is settled.

User answer, translated into English: "9: Yes."

Accepted decision D0026-009:

- Permit one corrective generation invocation when answer validation finds
  unknown IDs, changed selection order, user-visible source attribution or
  unsupported individual assertions.
- A correction consumes the same turn deadline, model admission and token
  budget; it is not a hidden provider retry.
- At this point the user accepted failure without a completed pair if the
  correction remains invalid. Q0026-012 later explicitly amends this for
  selection answers: valid candidates permit a clean backend template instead
  of the still-invalid prose. Knowledge-only failure remains an error.

### Q0026-010 — What happens when answer generation cannot complete?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If valid candidates were found but explanation generation fails, exceeds
> the remaining deadline or cannot obtain model/token admission, should we
> return an error without a completed turn, or return a simpler template
> selection instead? I propose an error so the selected explanation mode is
> not silently replaced with another response.

Reason: completed candidates do not by themselves constitute a completed
0026 answer. A template fallback would be a new accepted product behavior,
distinct from 0025's exact-candidate alternatives, and requires explicit selection.
Existing capacity/budget/dependency codes remain the starting point.

User answer, translated into English:

> 10: Just a template with the stones, without a polished answer.

Accepted decision D0026-010:

- When checked selection results exist but explanatory generation cannot
  complete, provide a backend template with those stones instead of requiring
  a polished generated explanation.
- The assistant's proposed mandatory error for every generation failure is
  rejected. Never display failed/unchecked generated prose as this fallback.
- Template results still obey real IDs, order, current availability, partial
  wishes, exact-alternative labeling and the five-stone/overflow behavior.
- This is answer-generation fallback, distinct from 0025's exact-candidate
  fallback. It does not permit returning candidates that failed selection or
  evidence validation.
- The question concerned already found candidates. It did not settle a
  knowledge-only generation failure with no candidate list.

Open at this point: whether a still-invalid corrective answer is also replaced
by a template; how a current valid template coexists with the 0024 shared
fail-closed provider policy; and how fallback can be finalized before the same
120-second turn deadline. Follow-up questions below address the first two;
deadline-aware execution needs reserved completion time in the final plan.
Q0026-012 through Q0026-015 below subsequently settle the policy overlaps.

### Q0026-011 — What if every candidate disappears during generation?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Recheck current AVAILABLE status immediately before returning the generated
> answer. If some candidates disappear, remove their links/explanations and
> keep survivors. If all disappear, I propose using the one retrieval refresh
> allowed by 0025 and generating explanations for the new results; if that
> refresh was already used, do not search again. Do you agree?

Reason: candidate eligibility checked before a model invocation may change
while generation runs. A turn-wide refresh limit must remain shared across
selection and generation, not become a second independent retry allowance.
Stale explanation text and aggregate count claims also need to be removed or
rendered consistently with surviving candidates. New generation consumes the
same remaining deadline, capacity and token budget.

User answer, translated into English: "11: Yes."

Accepted decision D0026-011:

- Recheck current existence/AVAILABLE after generation and before preparing
  final links/text. Remove ineligible candidates and their explanations.
- If some generated candidates survive, present the remaining subset; do not
  run another search merely to fill it or silently preserve vanished references.
  This final-generation-stage rule differs from 0025's pre-generation replacement
  from an existing candidate pool, which remains valid before prose is generated.
- If all disappear, use the one turn-wide retrieval refresh agreed in 0025,
  if it has not already been used. Generate explanations for new eligible results.
- No independent 0026 refresh allowance exists. A refresh already spent during
  0025 selection cannot be spent again after generation; after complete loss with
  no refresh remaining, return the suitable-candidates-not-found outcome.
- Keep aggregate introductions/counts consistent with survivors; do not leave
  vanished stones mentioned in otherwise retained prose.

### Q0026-012 — Does an invalid corrected answer use a template or fail?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> In answer 9 we chose an error after an invalid correction, while answer 10
> chose a template when generation fails. Should a still-invalid corrected
> explanation also be replaced by a template, or should it retain the error
> outcome? I propose a template when the selected candidates themselves are
> checked; invalid generated prose is discarded completely.

Reason: these are two accepted answers whose overlap must be resolved explicitly.
The proposal does not weaken candidate evidence checks or permit the invalid
answer to be saved, and would apply to selection answers with valid candidates.

User answer, translated into English: "12: Yes."

Accepted decision D0026-012:

- A selection explanation that is still invalid after its one correction is
  discarded and replaced with a backend template if the candidates themselves
  have passed the required checks.
- This explicitly supersedes the selection-error part of D0026-009. Preserve
  the single corrective allowance; do not keep retrying because fallback exists.
- No failed generated text is saved or exposed. A successfully finalized
  template is a completed answer, subject to existing history atomicity.
- Invalid selection/evidence results are not rescued by this template policy.

### Q0026-013 — May a current template finish after provider failure closes new chat work?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Feature 0024 closes chat operations when Ollama becomes unavailable. If it
> fails only during final explanation after candidates were checked, may the
> current turn still finish with a template, while later chat operations stay
> closed until recovery? I propose yes, provided database/history controls remain
> healthy and the template can be committed within the original deadline.

Reason: a generation-only provider failure is different from failed candidate
selection, and current-turn fallback otherwise conflicts with inherited shared
availability assertions. This is a proposed narrow current-turn completion rule,
not reopening new sends/history/replay using an unhealthy model dependency or
ignoring database/Redis/control failure.

User answer, translated into English: "13: Yes."

Accepted decision D0026-013:

- If Ollama fails during final explanation after the current turn's selection
  evidence is checked, that current turn may still finalize a backend template.
- This is a narrow explicit current-turn exception to inherited 0024 fail-closed
  processing. It does not reopen later sends/history/replay until verified recovery.
- Required database/history/request-ownership controls must remain healthy;
  the original turn deadline and serialization/fencing must still permit commit.
- Do not apply this exception when initial request admission, interpretation,
  candidate selection or evidence checking failed, or when persistence/control
  state is unavailable or uncertain.
- Unknown model usage remains reserved. Completing a template does not refund
  a timed-out invocation or release an executing model's permit prematurely.

Reserve completion time in the execution plan so generation stops before the
120-second deadline; this decision does not authorize committing after expiry.

### Q0026-014 — What is fallback for a knowledge-only generation failure?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> For a knowledge-only question with no selected stones, a stones template
> is unavailable. If generation fails there, I propose an error without a
> completed conversation pair. Do you agree?

Reason: absence of a generated answer due to a runtime failure is not the same
as an honest successful answer saying information is insufficient. The user
selected a stones template for selection failure, not an empty template presented
as a completed knowledge answer.

User answer, translated into English: "14: Yes."

Accepted decision D0026-014:

- A knowledge-only generation failure returns an explicit error and appends
  no completed pair. Do not substitute a stones template or invent an answer.
- This differs from a valid answer that information is insufficient, which
  is a legitimate completed response under D0026-008.
- A referenced specimen's convenience link, if selected later, does not turn
  a knowledge-only question into a selection eligible for template fallback.

### Q0026-015 — What total generation-call allowance covers repair and refresh?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose a configurable maximum of four answer-generation calls per turn:
> a main call plus one correction for the first pool, and the same for the one
> permitted refreshed pool. They all share the 120-second turn deadline and
> token budget, so four is a ceiling rather than a promise to run all four.
> Does that starting bound suit you?

Reason: the earlier single-correction and single-refresh choices allow up to
two generation cycles. Their cap must not be confused with 0025's separate
20-call evidence counter or reset by a refresh. Knowledge-only answers have
one main/corrective cycle and no candidate-loss refresh.

User answer, translated into English: "15: Yes."

Accepted decision D0026-015:

- Initial answer-generation call cap: four per turn, configurable.
- Permit one main/corrective cycle for the first selection and another for
  the single refreshed selection, each with at most one corrective call.
- Knowledge-only generation has one cycle, without a candidate-loss refresh.
- Share the original 120-second deadline and token budget; four is a ceiling,
  not a guarantee that all four calls can run. The counter never resets on refresh.
- This is separate from interpretation's existing two-call maximum and the
  agreed 20-call evidence counter. All model invocations remain accounted.

### Q0026-016 — Should mixed selection and explanation requests use one answer?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> For "Find a small basalt and tell me how to care for it", I propose selecting
> candidates through 0025 and answering the requested care question in the same
> response, keeping the short introduction and per-stone explanations. Agree?

Reason: both separate modes are accepted, but a user may ask for both in one
turn. This proposal does not turn a requested explanatory topic into a mandatory
selection constraint or permit unsupported individual care claims.

User answer, translated into English: "16: Yes."

Accepted decision D0026-016:

- Support selection and an explicit explanatory question in one turn/response.
- Candidate selection remains with 0025; answer the requested topic in the
  accepted short-introduction/per-stone format.
- Distinguish an explanatory question such as care instructions from a selection
  wish such as low care demand. Do not silently change exact filters or candidate
  eligibility merely because the user requested an explanation.

### Q0026-017 — May a new explanation topic use another bounded document query?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose using up to 10 encyclopedia passages at the initial 0.5 threshold
> for explanation, with configurable limits. Reuse evidence retrieved by 0025
> when it answers the question; if a different topic such as care needs another
> document query, allow a second query embedding, at most two per turn. Agree?

Reason: a selection query about color/use and a requested care explanation may
need different passage relevance. The existing one-vector 0025 rule must not
be silently expanded into unbounded explanation embeddings. Knowledge-only
requests ordinarily need one query embedding; a supplementary query shares
profile validation, CUSTOMER admission, remaining deadline and token accounting.

User answer, translated into English: "17: Yes."

Accepted decision D0026-017:

- Initial explanatory retrieval uses up to 10 encyclopedia passages and an
  inclusive 0.5 threshold, controlled through configuration.
- Reuse relevant evidence from 0025 rather than reretrieve solely to explain it.
- A genuinely different explanation topic may use a supplementary document
  query with its own query embedding. At most two query embeddings are permitted
  across the entire turn, including selection, knowledge and refresh handling.
- Knowledge-only work ordinarily needs one query vector. Do not compute one
  vector per stone, retry, passage or correction.
- The second embedding uses compatible profiles, CUSTOMER admission and the
  existing reservation/settlement, remaining-time and 50-second call bounds.

The plan reuses the existing separately configurable document cap/threshold
settings from 0025 instead of adding an unrelated new ingestion contract.

### Q0026-018 — Should a referenced specimen have a convenience link in a knowledge answer?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> When answering "How do I care for the second stone?", I propose including
> one link to that current specimen; a question about basalt generally has no
> specimen links. Ordinal references should still use the last actual selection
> rather than replacing that list with the single explanatory link. Agree?

Reason: convenience links are distinct from source citations, which are excluded.
A linked knowledge answer must remain a knowledge-only route under the accepted
failure policy and must not distort later "second stone" resolution.

User answer, translated into English: "18: Yes."

Accepted decision D0026-018:

- A knowledge answer about a referenced current specimen includes one catalog
  link to that specimen. A general type question includes no specimen links.
- This is a convenience reference, not visible provenance or a new selection.
- Later ordinals still resolve from the last actual nonempty selection, not
  from the knowledge answer's single link. Explicitly distinguish selection
  and knowledge turns in internal history/reference resolution.
- The knowledge route retains its knowledge-only error policy; a convenience
  link does not authorize selection-template fallback on generation failure.

### Q0026-019 — Should the generated text receive a structured grounding check?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose a structured check of the finished generated text against the
> permitted facts/wishes before display, including unsupported individual claims
> and exposed source attribution. This check uses the existing turn-wide maximum
> of 20 evidence-check calls; it is not an unaccounted invocation. Include it?

Reason: 0025 validates selection evidence, but new prose can introduce claims
not present in that validated result. Backend ID/order/shape checks remain
mandatory independently; a semantic model check supplements them rather than
choosing new candidates. Checks of corrected/refreshed prose consume the same
remaining evidence counter/deadline, and selection failure uses the accepted
clean template when checked candidates exist.

User answer, translated into English: "19: Yes."

Accepted decision D0026-019:

- After generation, include a structured grounding/visibility check of the
  finished text before it becomes a displayed or saved answer.
- Backend checks independently enforce IDs, order, result shape and live links;
  the additional semantic check addresses unsupported individual claims,
  contradicted wishes and exposed information-source attribution.
- All checking attempts share the existing turn-wide evidence cap, initially 20.
  Do not create an unaccounted checker call or reset the counter after correction
  or retrieval refresh.
- Invalid prose may receive its one correction; unchecked prose is never used.
  Selection can use the accepted clean template if generation/checking fails
  while selected evidence remains valid. Knowledge-only failure remains an error.

## Technical Preparation Choices Within the Accepted Behavior

These are implementation choices selected by the assistant, not additional
user answers or evidence that the feature already runs.

- Interpret and route each turn once, with a structured selection/knowledge/
  clarification/unsupported decision and at most the existing one interpretation
  correction. Add decision-driven reuse of the 0025 pipeline rather than invoking
  its model interpreter again after route selection.
- Use shared turn execution state for absolute deadline, one retrieval-refresh
  flag, two-query-embedding cap, 20-checking-call cap and separate four-generation
  cap. Reuse vectors/source snapshots when the query/profile is unchanged.
- Keep the existing STONE_SEMANTIC_SEARCH_DOCUMENT_LIMIT and
  STONE_SEMANTIC_SEARCH_DOCUMENT_MIN_SIMILARITY settings for explanation retrieval.
  Add CHAT_RAG_OUTPUT_TOKENS (1024) and CHAT_RAG_MAX_GENERATION_CALLS (4).
  Generation output must be positive and below the total context; generation
  calls are configurable from one through four, with no extra repair/refresh cycle.
- Reserve five seconds of the original turn for final eligibility checks,
  backend templates and fenced history completion. All model-stage deadlines
  are clipped to that earlier work deadline and the single-call bound. This is
  a completion margin, not a new 125-second turn or a latency promise.
- Generate an internal structured draft with introduction, keyed stone
  explanations and knowledge text. Backend code owns final IDs/names/links,
  ordering, result counts and overflow/alternative labels. Only final text and
  existing catalog-reference fields reach HTTP/history DTOs.
- Keep provenance and checking verdicts internal. Validate both structured
  fields and displayed text; source visibility is not satisfied merely by
  omitting a citations array.
- For a partial late selection loss, discard vanished explanations and rebuild
  the introduction/count framing for survivors without another generated answer.
  Do not trust a model-written aggregate count that no longer matches the pool.
- Store knowledge-versus-selection mode and the actual selection-reference
  identity as optional completed-turn metadata. Legacy nonempty result lists
  without that metadata remain valid selection references.
- A current-turn template exception is backend-owned, associated with an
  admitted turn and checked selection. Validate healthy ledger/database/history
  controls, request ownership, active serialization fence and original deadline
  before/after commit; do not add a public bypass parameter or globally reopen chat.
- Failed selection, missing control state, expired fencing or uncertain history
  commit is not generation-only fallback. Preserve existing reconciliation and
  fail-closed behavior rather than returning an invented successful template.
- Every generation/checking attempt uses existing local providers, shared N=1
  admission, UTC daily accounting and unknown-usage retention. The 8192-token
  reservation covers input plus the generation output allocation. Provider
  cancellation does not free its permit until execution actually exits.
- For knowledge-only invalid output after correction use HTTP 502
  CHAT_INVALID_MODEL_OUTPUT; for local generation/context exhaustion use HTTP 503
  CHAT_RAG_LIMIT_EXCEEDED; retain existing 429 budget/503 capacity/unavailable
  mappings. Selection template completion is HTTP 200 only if every required
  finalization condition is met.
- Use prototype qualification before dependent implementation and final
  production-provider requalification. No source/model import, schema, dependency,
  framework configuration, Kafka or frontend change is planned.

## Resolution and Handoff

All 19 questions have recorded answers. No unanswered product question remains
for preparing the 0026 documentation. Runtime qualification may still expose a
blocker: report it and obtain an agreed correction scope rather than changing
accepted behavior or executing another feature implicitly.

Feature 0027 will discuss the user-reviewed query/fixture set, measurable quality
outcomes, evidence and any explicitly authorized correction/tuning boundary.
No broad quality evaluation or performance result is inferred from these initial
generation settings.

## Accepted Out-of-Scope Decisions

Matching accepted entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0026-001 | User-visible information-source attribution | Do not show source citations, document/section names, provenance IDs/links/scores or prose identifying the information source. Retain provenance internally; selected-stone catalog links remain part of the answer. |

Only confirmed exclusions are recorded. Proposals and unanswered questions
are not accepted exclusions.

## Completion Gate

Specification, acceptance criteria, plan and tasks are prepared from the answers
above; all execution tickets remain unchecked. Current environment readiness,
model qualification and implementation acceptance are unverified. This discussion
authorizes documentation only, not application edits, inference, provisioning,
business imports, tests, commits or pushes.

## Execution Request and Blocked Readiness — 2026-10-13

The user requested execution of every task in features 0025–0029 without stopping
at individual task/feature boundaries, and one commit for each entire completed
feature rather than one per task. This is explicit implementation/test/feature-
commit authorization for that batch, superseding the earlier documentation-only
stop boundary. It does not authorize pushing, feature 0030, relaxing acceptance,
production fixes inside 0027 qualification, or replacing actual human review.
No new product question or exclusion was introduced by this execution request.

Fresh batch preflight is BLOCKED, as recorded in
[the feature task evidence](../../specs/0026-semantic-search-rag-answers/tasks.md#requested-batch-readiness--2026-10-13).
The unavailable browser prevents required actual T0029-006 verification. Existing
backend liveness is healthy but the current vector/index/usage-ledger schema and
embedding endpoint configuration are absent in that running deployment. Maven's
Java mismatch was resolved for checks by supplying the installed JDK 23.0.2
explicitly. Installed Ollama 0.40.3 remains covered by prior approval.

Preparation started the existing API-mode UI server and successfully built the
preceding implemented backend image with tests skipped. No application deployment,
schema migration, source import, inference or application test followed. No
implementation ticket started, no feature is accepted and no commit was created.
This is environment evidence, not an accepted out-of-scope decision. All selected
readiness prerequisites must be rechecked after correction before execution.

## Browser Connection and Readiness Recovery — 2026-10-13

The user reported completing browser setup. Fresh inventory and an actual local
page observation establish connected Chrome access; the earlier browser blocker
is resolved. The agent also started the current preceding backend with approved
command-scoped settings. Its existing Liquibase migrations applied, required
vector/index/usage tables now exist, profile mismatch checks return zero and the
actual history GET is healthy. No feature requirement or exclusion changed.

The entire requested 0025–0029 external environment batch is READY, with exact
components, covered IDs, preparation and command requirements recorded in
[the current task readiness evidence](../../specs/0026-semantic-search-rag-answers/tasks.md#fresh-batch-readiness-after-browser-connection--2026-10-13).
Mandatory model/transport qualification and within-batch producer deliveries are
still gates; this readiness result does not claim their future acceptance. The
previous blocked report remains historical evidence. User authorization remains
continuous execution and one commit per completed feature, with no push.


## Shared Producer Qualification Gate — 2026-10-13

Chrome connection and current external environment readiness were confirmed.
T0025-002 then failed actual prototype qualification: semantic-only shelf input
was classified CLARIFY instead of SEARCH; evidence output invented a source ID
not supplied by the backend, including after one correction. Detailed original
outputs, successful checks and a pending exact correction proposal are retained
in [ADR-0025](0025-semantic-stone-candidate-search.md#t0025-002--actual-prototype-qualification-failure).

Dependent implementation remains unexecuted under the mandatory qualification
gate. No feature is complete and no feature commit/push was created. This is
execution evidence, not a new product exclusion or approval of a correction.


## Authorized Necessary Type Changes — 2026-10-13

The user explicitly approved the pending T0025-002 shared-prompt correction and
broadened source-type scope for the selected batch, translated: "Yes, do it; if
a task requires changes within features 25 through 29, all types may be modified."
Necessary related production/test type changes for the accepted 0025–0029 behavior
are therefore authorized even if a prepared ticket's original list omitted them.
Record exact additional paths, reasons and focused checks before each such change;
this approval supersedes the original type-file isolation blocker, not acceptance
or unrelated scope. No dependency/version change, feature 0030, engineering-log
access, push, gold relaxation, or fabricated human review is authorized.

For the already reviewed T0025-002 correction, additional writable types are:
stone-shelter-api/src/main/java/lab/stoneshelter/entities/StoneModelInvocationEntity.java
and stone-shelter-api/src/main/java/lab/stoneshelter/repositories/OllamaChatRepository.java.
Make the exact-only example optional with its original default preserved. Update
the authorized semantic prototype/fixture to use its own format and supplied-ID
constraints. Focused verification adds the three existing controlled exact-search
qualification methods from the reviewed proposal; they perform no live inference.
Recheck changed interpretation/evidence/capacity tests, retaining the earlier
failed evidence and not repeating unchanged successful embedding qualification.

Continuous execution and one commit per entire completed feature remain requested.
The original task lists remain the implementation guide; broad type permission
is not permission to invent behavior or bypass prerequisite qualification.


## Basic Producer Qualification Recovered — 2026-10-13

The accepted necessary-type correction preserved default exact-search prompts,
removed their injection from new formats, made wish absence explicit through
nullable slots and classified each evidence source in its own authority scope.
Actual requalification now covers every small fixed interpretation/evidence
case, compatible vectors and complete fresh-input capacity. Three existing
controlled exact transport regressions passed. All earlier failures and usage
remain recorded in ADR-0025/tasks; they were not erased or replaced by lucky
repetition. No model/profile/limit/gold change occurred.

T0025-002 is complete as a basic prototype gate. T0025-003 onward remains the
requested production implementation sequence; final providers and feature
acceptance are unverified. No feature commit/push has been created yet.

## Delivered Producer and T0026-002 Entry

Feature 0025 completed and committed as ee6d532 with focused final real-model,
retrieval/evidence/Redis/native-HTTP/architecture acceptance. Required semantic
preparation, private execution counters/caches, evidence and current-card mapping
types are delivered; source signatures and focused results are available rather
than inferred from prepared documentation.

Rechecked external prerequisites for the continuous T0025-001–T0029-006 batch:
backend health UP, Redis PONG, actual backend-reachable Ollama 0.40.3. Previously
verified unchanged pinned models/JAVA_HOME/Docker/database/ledger/browser/UI
remain the batch context; the selected basic qualification uses explicit approved
JDK 23.0.2 and host endpoints. No service was newly started, source imported or
model downloaded in this recheck. Result READY for T0026-002. Browser/stack
qualification of the final 0029 UI remains pending, not claimed by preflight.

Proceed only with the small test-only routing/generation/grounding/context
prototype under existing CUSTOMER admission, 8192 context, 1024 generation,
512 routing/checking, 50-second calls and original 120-second bounds. No
dependent 0026 application implementation before this format gate passes.

## T0026-002 — First Prototype Outcome and Correction

Four tests: grounding and capacity pass; generation fails after one permitted
correction by filling knowledge text during selection and omitting explanations;
nested routing times out at the unchanged 50-second call bound. Six reservations
charge 9810 tokens, including the unknown routing reservation (not refunded).
The timeout is a timeout, not evidence of context overflow. No dependent 0026
application implementation or completed feature commit is claimed.

Necessary test-only format correction flattens route fields onto the already
qualified exact/wish interpretation grammar, replacing action with route mode
and adding explanatory topic; knowledge type scope is interpreted only for the
knowledge operation and never changes inherited selection state. Do not nest
an entire decision in an optional selection union. Generation grammar explicitly
requires knowledge=null for selection and empty stones for knowledge, with the
complete source-free explanation instructions for every valid partial candidate.
Keep independent route/ID/order/partial/source-visibility expectations, selected
model, 8192/1024/512, single repair and 50/120-second bounds. Recheck only changed
routing/generation and their capacity proof; retain unchanged grounding evidence.

Prototype correction 1: mode-constrained generation now keeps knowledge=null,
but emits only candidate names instead of per-stone explanations. Flattened
routing again times out at 50 seconds. Capacity 8025 passes; three reservations
charge 8913 tokens including the unknown routing reservation. Grounding remains
unchanged and passed; no dependent implementation starts.

Targeted schema diagnosis: flattening appended mode after the full criteria
properties, so the constrained decoder encounters arrays before the route. Put
mode first and include one compact complete routing example, retaining all
accepted meanings. Rename the ambiguous per-stone model text field to explanation
and explicitly require a sentence instead of a name; this is an internal format
choice, with unchanged per-ID/order/partial/source-free assertions and resources.
Recheck only changed routing/generation/capacity.

Prototype correction 2: route-first schema removes the timeout and correctly
routes shelf selection and basalt knowledge; mixed care still acquires an
unasked use wish. Generation now returns sentences but copies its generic
example without identifying shelf use. Capacity 8037 passes; six reservations,
2128 actual tokens. No dependent implementation starts.

Next correction uses a complete mixed-request example with absent selection
use/color, and explicitly requires explanations to mention matched shelf use.
Align the prototype's lexical assertion with the actual requirement: requested
color identifies the sole active color wish just as red does; the spec requires
truthful identification, not a literal red token. Accept red/color plus the
unchanged uncertainty marker, while adding required shelf coverage. This does
not accept missing wish explanations, invented matches or changed fixed-case
meaning. Keep all route/ID/order/grounding/visibility oracles and approved limits.
