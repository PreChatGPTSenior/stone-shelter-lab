# ADR 0025: Semantic Stone Candidate Search

Date: 2026-10-13

Status: Accepted for documentation preparation. Product questions Q0025-001
through Q0025-021 are answered; the specification, acceptance criteria, plan and
tasks define the resulting bounded feature. Implementation, model qualification
and current environment readiness have not been executed or established.

Feature: [0025: Semantic Stone Candidate Search](../../specs/0025-semantic-stone-candidate-search/spec.md).

## Context and Sources

The user requested continued discussion of features 0025 through 0027, beginning
with feature 0025. All three currently contain initial discussion drafts, pending
acceptance criteria and technical plans, and a first environment-readiness ticket.
No implementation batch was requested.

Relevant existing requirements are recorded in:

- [Shared discovery specification](../../specs/0015-llm-search-discovery/spec.md)
  and [ADR-0007](0007-llm-search-discovery.md).
- [Stone-card embeddings](../../specs/0018-stone-card-embedding/spec.md).
- [Exact LLM search](../../specs/0024-exact-llm-search/spec.md)
  and [ADR-0024](0024-exact-llm-search.md).
- The current feature's [acceptance.md](../../specs/0025-semantic-stone-candidate-search/acceptance.md),
  [plan.md](../../specs/0025-semantic-stone-candidate-search/plan.md)
  and [tasks.md](../../specs/0025-semantic-stone-candidate-search/tasks.md).

These documents establish the discussion baseline, not current operational
readiness. No application tests, model calls, service startup or imports have
been performed during this documentation discussion.

## Accepted Discussion Workflow

### D0025-001 — Discuss and prepare documentation before implementation

The user's instruction, translated into English, was:

> Our task is to continue the discussion, starting with
> specs/0025-semantic-stone-candidate-search through 27. Do not write code.
> For each feature, when we have discussed everything and no questions remain,
> fill in the specification, implementation plan and tasks. Record all accepted
> decisions and questions from every discussion in an ADR. The ADR must be as
> detailed as possible about which questions were asked and what answers were
> given. At the end of each feature discussion, its documentation must be ready.

Accepted working procedure:

1. Discuss features in order: 0025, then 0026, then 0027.
2. Record each actual question, the alternatives and reasoning presented, the
   user's answer when received, and the resulting decision in the relevant ADR.
3. Clearly distinguish existing accepted requirements, new accepted decisions,
   assistant proposals and unanswered questions. Do not reconstruct unavailable
   historical answers or treat silence as agreement.
4. When all questions for a feature are resolved, synchronize its specification,
   acceptance criteria, technical plan and task list. Acceptance criteria remain
   exclusively in acceptance.md.
5. Preserve the existing readiness ticket as the first task and existing task
   IDs. Future implementation tasks must identify exact writable files and
   permitted focused verification.
6. Record any newly confirmed scope exclusion in both the feature ADR and
   docs/out-of-scope-decisions.md, with consistent IDs and a feature link.
7. Documentation preparation does not authorize execution of implementation
   tickets, code changes, model calls, environment provisioning, commits or pushes.

### D0025-002 — Ask discussion questions directly in the chat

On 2026-10-13 the user requested, translated into English:

> Ask the questions we need to discuss for feature 25. You ask the questions
> here in the chat, and I answer.

Accepted procedure: present discussion questions in ordinary chat messages,
not through a separate question UI. Record the questions and subsequent answers
in this ADR. The assistant will use manageable question blocks, beginning with
requirements and candidate-selection behavior; this batching is an assistant
organization choice, not an accepted product requirement.

This procedure applies to the requested 0025–0027 discussion sequence. A draft
feature is not marked ready while its behavior or implementation boundaries
remain unresolved.

## Inherited Decisions — Not New Answers in This Discussion

The following baseline comes from the linked existing documents. It has not been
re-asked or newly confirmed during this session:

- Delivery is English only.
- Present at most five stones. When additional matches are found, explain that
  more exist and ask questions to narrow the request. A zero-match response only
  reports the absence of matches.
- Order semantic results by relevance. Exact results use admission date
  descending, with identifier ascending for ties.
- Returned stone links must refer to currently existing, AVAILABLE catalog
  stones. Delayed index updates are tolerated; an indexed identifier alone does
  not establish current eligibility.
- Existing exact filters cover type, size, availability and admission date.
  Values within a filter group combine with OR; different groups combine with
  AND. Availability alone does not justify an unfiltered search.
- Existing internal card retrieval accepts query text and a positive limit,
  returns identifiers and cosine scores, includes scores at or above the
  configured threshold, and orders score descending with identifier ascending
  for ties. Its initial minimum similarity is 0.5, not a qualified quality target.
- Card embedding text contains name, type, size and biography. It does not
  contain availability, admission date, photographs or encyclopedia knowledge.
  Existing retrieval does not join or validate the live catalog.
- Semantic found-count semantics concern retrieved candidates before the
  five-result presentation limit, not an exhaustive database count. The precise
  relationship to live filtering and a candidate cap remains to be decided.
- Provider or database failure must not be disguised as an ordinary empty result.
- Existing general knowledge must not establish undocumented properties of a
  specific stone.

## Discussion Log

### Q0025-001 — Is an explicitly requested color mandatory or a ranking preference?

Date asked: 2026-10-13.

Status: Answered on 2026-10-13; evidence implications require follow-up.

The initial question was presented through a separate question UI. Following
D0025-002, it is asked again directly in chat with the same unresolved choice:

> In "small red basalt for a shelf", type and size are mandatory exact
> constraints. Should "red" also be mandatory, supported by the card text,
> or only a ranking preference? If the request does not make strictness clear,
> should the bot clarify it?

Question presented to the user, translated into English:

> For feature 0025, how should we handle "small red basalt for a shelf"?
> I propose keeping BASALT type and SMALL size as mandatory filters, and always
> checking AVAILABLE status. Semantic search of the card alone does not prove
> that a stone is red. Should an explicitly named color be a mandatory condition
> supported by the card text, or a preference used for ranking?

Suggested answers presented:

1. Color is mandatory: information in the card is required.
2. Color is a preference: semantic similarity is sufficient.
3. When ambiguous, ask whether color is mandatory.

The UI also permits a free-text answer; these choices do not constrain the user
to accepting one of the proposals unchanged.

Reason for asking:

- Type and size are existing structured catalog constraints; color currently
  has no structured filter in feature 0024.
- A vector similarity score measures overall semantic proximity. The existing
  contract does not certify that every named property occurs in the card or is
  true of the individual stone.
- Treating color as mandatory requires an agreed evidence rule and behavior
  when evidence is absent, ambiguous or contradictory.
- Treating color as a preference permits candidates whose card is similar to
  the request without establishing that the individual stone is red; later
  wording must respect that distinction.
- Clarification makes strictness explicit but requires an agreed trigger and
  subsequent conversation behavior.

Assistant proposal originally presented:

- Preserve exact type and size constraints and mandatory live availability
  while discussing how nonstructured wishes affect semantic selection.
- Use this concrete example to decide whether explicit wishes are mandatory
  requirements, soft preferences or clarification triggers.

User answer, translated into English:

> A stone does not have a color. It might potentially be described in the
> encyclopedia documents handled by specs/0017-markdown-document-indexing,
> or it might not be there. It is a ranking preference. If we did not find red
> ones, we can offer a small basalt and say that there are no red ones.

Accepted decision D0025-003:

- Do not assume a structured color field exists on a stone.
- Treat color as a soft ranking preference, not a mandatory eligibility filter.
- Encyclopedia knowledge indexed by feature 0017 may contain relevant color
  information, but its presence is not guaranteed.
- The user permits offering a small basalt satisfying the exact constraints
  when the red preference cannot be satisfied, accompanied by an explanation.
- This answer does not authorize adding color to entities, DTOs or card vectors,
  and does not turn type-level evidence into an established individual property.

At the time of the first answer, document-assisted selection, missing-evidence
wording and threshold fallback remained open. Subsequent answers to
Q0025-007 through Q0025-010 settle those boundaries below; the original
uncertainty is retained here as discussion history, not a current blocker.

### Q0025-002 — What should happen when a card lacks evidence for a mandatory wish?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If a wish is mandatory but the card does not state the relevant property,
> should we exclude that stone or show it with an explicit warning that the
> property is unknown? I recommend exclusion for mandatory conditions.

Reason for asking: the existing index contains factual card text but its
similarity score does not establish missing individual properties. This choice
determines whether uncertain candidates may satisfy a strict request and what
evidence the candidate result must carry for later answer generation.

The recommendation was an assistant proposal. Missing information is distinct
from contradictory information; the treatment and verification of both remain
to be settled if strict nonstructured requirements are selected.

User answer, translated into English: "2: Exclude."

Accepted decision D0025-004: exclude candidates when a genuinely mandatory
condition cannot be established. Color is a soft preference under D0025-003,
so this answer must not be applied to exclude every card without color evidence.
No new class of mandatory inferred properties or validation mechanism is
authorized by this answer; existing exact constraints remain mandatory.

### Q0025-003 — Is a semantic wish sufficient to start a search?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Is "a stone for a shelf" sufficient to start semantic search without a
> specified type or size, or must the bot first ask for clarification? I propose
> searching when there is a meaningful wish, and clarifying a request such as
> "I want a stone" without one.

Reason for asking: feature 0024 requires narrowing and does not consider
availability alone sufficient. Feature 0025 must define whether an actual
semantic preference provides narrowing, without enabling unfiltered catalog
selection for a generic request.

User answer, translated into English: "3: Yes, I agree."

Accepted decision D0025-005: a meaningful semantic wish, such as choosing a
stone for a shelf, is sufficient to initiate semantic search without exact type
or size constraints. A generic request such as "I want a stone" still requires
clarification rather than an unfiltered selection. Availability alone does not
become narrowing.

### Q0025-004 — How should follow-ups change semantic wishes?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> After "a red stone for a shelf", should "now only small ones" retain color
> and intended use, while "instead for the garden" replaces intended use but
> retains color and size? I propose retaining conditions that the user has not
> changed, and clearing all conditions for an explicit new search.

Reason for asking: exact-criteria inheritance already exists in feature 0024,
but semantic wishes need an explicit retention/replacement rule. This example
does not settle ambiguous follow-ups, explicit removal of a preference or
negation; those may require subsequent questions.

User answer, translated into English: "4: Yes, I agree."

Accepted decision D0025-006: retain unchanged exact conditions and semantic
preferences in follow-ups; replace the preference explicitly changed by the
user; clear both for an explicit new search. In the example, "now only small
ones" retains red and shelf preferences, and "instead for the garden" replaces
shelf with garden while preserving color and size. Handling of ambiguous
references remains governed by clarification, not invented constraints.

### Q0025-005 — Should exact and live eligibility constraints precede the candidate cap?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> For "small basalt for a shelf", should we first restrict to currently
> AVAILABLE small basalt stones and then choose the most semantically relevant,
> or retrieve the strongest semantic candidates from all cards and discard
> those failing the constraints afterwards? I recommend the first approach so
> unrelated cards do not occupy the retrieval limit.

Reason for asking: filtering only an already bounded global result can discard
all of its candidates even when eligible cards exist outside that bound. The
existing feature 0018 operation has no live catalog join. The selected behavior
will need a concrete repository/service contract and file allowlist before
implementation; it is not an authorization to extend that operation now.

User answer, translated into English: "5: Yes."

Accepted decision D0025-007: apply exact constraints and current AVAILABLE
eligibility before the semantic candidate limit. Unrelated or ineligible cards
must not occupy the bounded result set and hide eligible candidates. The live
catalog supplies eligibility, not the asynchronously updated vector projection.
The concrete operation and consistency mechanism remain implementation-plan
work; no application edit is authorized here.

### Q0025-006 — What bounded candidate set should precede presentation?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> We show at most five stones; how many eligible candidates should we retain
> internally? I propose an initial cap of 20, using their count as the count
> found in this bounded search, not as the count of every suitable catalog stone;
> if at least six exist in that set, the bot can say more were found. Does that
> suit you, or do you want a different limit?

Reason for asking: the candidate limit bounds work and later context assembly,
and must remain distinct from the five-stone presentation limit. Shared
discovery already distinguishes a semantic candidate count from an exhaustive
database count. The proposed value 20 has not been benchmarked or qualified;
agreement on a starting limit will not establish quality or performance.

User answer, translated into English:

> 6: Collect up to 10, and control that number through configuration. If we
> find many and show only five, we can say there are more. This functionality
> is outside scope now, but later the agent could offer to show them in the
> catalog by the found IDs rather than in the chat. That is for later; for now,
> only five and the phrase that there are more.

Accepted decision D0025-008:

- Initial internal candidate limit: 10, configurable rather than hardcoded.
  The assistant's proposed initial value 20 is superseded by this answer.
- Display at most five candidates. When additional eligible candidates were
  found, state that there are more. Do not claim an exhaustive semantic count
  of every suitable stone in the database.
- For this semantic overflow presentation, the user's "for now, only five and
  the phrase" selects the additional-results notice rather than adding an
  automatic refinement-question requirement from the earlier shared proposal.
  Clarification for insufficient requests is still required by D0025-005.
- Offering or opening a catalog view filtered by found IDs is deferred, as
  recorded in OOS-0025-001. It is a future discussion idea, not an executable
  task or a scheduled checkpoint.

Open at the time of that answer: configuration validation bounds, candidate
count in a fallback result and handling of eligibility changes after retrieval.
Subsequent answers and the separately identified technical preparation choices
below resolve those details; choosing a default of 10 alone did not select them.

### Q0025-007 — How should the answer distinguish unknown color from no match?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If neither the card nor the encyclopedia contains color information, I
> propose saying "I could not confirm the red color" rather than "There are
> no red stones". If the search did not find a match, say "No red matches were
> found". Do you agree with distinguishing these situations?

Reason: absent evidence and a bounded retrieval miss do not establish that red
stones do not exist in the catalog. The user's requested explanation should
retain that distinction while permitting the accepted soft-preference fallback.

User answer, translated into English:

> 7: If some information is absent from both the encyclopedia and the card,
> say that suitable candidates were not found. This is a general rule, not
> only about color.

Accepted decision D0025-009:

- Apply the missing-evidence rule to semantic wishes generally, not only color.
- If neither the relevant card data nor encyclopedia evidence supports the
  requested wish, report that suitable candidates were not found.
- The assistant's proposed distinct wording "I could not confirm the red color"
  was not selected. Use the user's general suitable-candidates outcome rather
  than inventing unsupported properties or declaring that a property cannot
  exist in the catalog.
- This outcome concerns semantic suitability. Q0025-009 separately permits
  offering exact-filter matches as alternatives; those must not be represented
  as confirmed semantic matches.

The rule does not establish that every retrieval miss proves source information
is absent. Retrieval/evidence quality and classification still require a bounded
design and later qualification.

### Q0025-008 — Does encyclopedia-assisted selection belong in feature 0025?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Should feature 0025 already use encyclopedia passages indexed by feature
> 0017 to select or rank suitable stone types for color and intended-use wishes?
> I propose yes, while feature 0026 owns the explanation based on those passages;
> type-level information does not guarantee an individual stone's property.

Reason: current card embeddings do not contain encyclopedia knowledge. The
initial feature drafts assigned document-assisted answers to 0026, so the
selection boundary must be agreed rather than inferred from the user's mention
of 0017. A positive answer will require a defined type/document/card ranking
contract before the final plan is written.

User answer, translated into English:

> 8: Yes, and leave the explanation for 26.

Accepted decision D0025-010:

- Feature 0025 uses the existing indexed encyclopedia passages from feature
  0017 to determine suitable stone types and inform selection/ranking for
  semantic preferences, alongside factual card data.
- Feature 0026 owns substantive explanation based on those passages.
- Type-level evidence is not a guarantee of an individual specimen's property.
- This refines the initial draft boundary: document-assisted candidate selection
  belongs to 0025 rather than deferring every use of documents to 0026.
- Do not modify document ingestion, enrich persisted card embeddings with
  encyclopedia content or add catalog properties implicitly. The feature plan
  will define a bounded reuse contract for the existing projections.

Open at the time of that answer: type-only qualification, source conflict
precedence, combined ranking and document limits. These are subsequently resolved
by Q0025-011 through Q0025-021 and the technical preparation choices below.

### Q0025-009 — What happens when no semantic candidate reaches the threshold?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If no candidate reaches the semantic threshold but some available stones
> satisfy the exact filters, should we offer those exact matches, ordered by
> newest admission, and explain that the semantic wish was not confirmed? If
> the request has only semantic wishes and no exact narrowing, I propose not
> substituting random stones and returning the absence-of-matches response.

Reason: the user permits small-basalt fallback for a soft color wish. The
existing semantic retrieval excludes scores below the configured threshold,
so exact fallback must be defined as a separate outcome, not silently treated
as a qualifying semantic match or a lowered threshold. Its count and history
representation will require follow-up if selected.

User answer, translated into English: "9: Yes."

Accepted decision D0025-011:

- When no qualifying semantic candidates remain and the request contains usable
  exact narrowing conditions, permit available catalog matches satisfying those
  conditions as a separate exact fallback.
- Order fallback by admission date descending, with identifier ascending for
  ties, reusing the accepted exact-search order.
- Explain that suitable semantic candidates were not found; do not imply that
  fallback stones meet the unconfirmed semantic wish.
- When a request contains only semantic wishes and no usable exact narrowing,
  return the absence-of-matches response instead of substituting an unfiltered
  AVAILABLE catalog selection.
- Do not lower the semantic threshold or relabel below-threshold results as
  semantic matches to obtain fallback.

The semantic candidate count and exact fallback count are separate semantics;
their concrete internal response representation remains plan work.

### Q0025-010 — Should partial semantic results be filled with weaker alternatives?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If only two good semantic candidates were found, should we show those two,
> or fill the list to five with stones matching only the exact filters? I
> propose showing two, using exact fallback only when there are no qualifying
> semantic candidates at all.

Reason: the accepted five-result maximum is not a requirement to always show
five. Mixing weaker alternatives changes ranking, explanations and counting;
it must not be introduced implicitly by the zero-semantic-result fallback.

User answer, translated into English:

> 10: Show two. Replace with exact results only when semantic matches are
> completely absent.

Accepted decision D0025-012:

- A presentation maximum of five does not require filling the list to five.
- Present the qualifying semantic set, up to five, even if fewer were found.
- Do not mix exact-only alternatives into a nonempty semantic result set.
- Exact fallback is permitted only for an empty semantic set under D0025-011.

### Q0025-011 — May type-level evidence qualify a candidate with no card match?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If the encyclopedia says that a type suits a shelf, but an individual card's
> biography says nothing about a shelf, should available stones of that type
> qualify as semantic candidates? I propose yes, based on general type evidence,
> without claiming that the individual property was confirmed.

Reason: the accepted document-assisted selection must specify whether
type-level evidence can qualify a candidate independently of its card score.
Otherwise the card-only threshold could eliminate every document-supported
candidate. Inclusion would still require exact filters and live availability.

User answer, translated into English: "11: Yes."

Accepted decision D0025-013: available stones may qualify through relevant
encyclopedia evidence about their type when their individual card is silent
about the semantic wish. Keep exact constraints mandatory. Type-level support
does not become a confirmed individual property, and an explicit conflicting
card statement takes precedence under D0025-014.

### Q0025-012 — Which source wins a conflict about an individual stone?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If the encyclopedia describes a type as suitable for a shelf, but a specific
> card explicitly says that the stone is unsuitable for a shelf, should the
> card take precedence and that candidate be excluded for this wish? I propose
> giving the individual card precedence over the general type description.

Reason: absence of individual evidence differs from explicit contradiction.
Combining independently relevant sources must not erase a known individual
exception. This asks about contradiction handling for the active wish, not
adding a structured safety or suitability field to the catalog.

User answer, translated into English: "12: The card always has priority."

Accepted decision D0025-014: the individual stone card takes precedence over
general encyclopedia evidence. Do not override an explicit individual exception
using a favorable general type description. For the example, the card's explicit
unsuitability prevents treating the stone as a suitable semantic candidate for
that wish. The user's wording generalizes source precedence beyond this example;
it does not require every silent card to exclude type-supported candidates,
which are accepted by D0025-013.

### Q0025-013 — Which relevance score determines combined ordering?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> When candidates are supported by cards and by encyclopedia passages, I
> propose using the higher of the card similarity and the best relevant
> type-specific passage similarity as the candidate's relevance score, with
> identifier ascending for ties. Do you agree, or should one source always
> have priority? Keep 0.5 as the initial threshold and qualify its quality in
> feature 0027.

Reason: the accepted general relevance order needs a deterministic combined
rule. Taking a maximum is a proposed simple initial rule, not evidence that
card and passage scores are equally calibrated. Both contributing scores must
use compatible query/source embedding profiles. Type passage applicability,
contradictions and missing evidence still constrain qualification; a high score
alone cannot certify an unsupported individual property.

User answer, translated into English:

> 13: The card has priority; the encyclopedia supplements it.

Accepted decision D0025-015:

- Card evidence is primary; encyclopedia evidence is supplementary.
- Do not use the assistant's proposed maximum-of-two-scores rule, which could
  let a strong document score override the card's priority.
- At this point the deterministic ranking rule still needed confirmation;
  Q0025-015 below records that confirmation.
- The user answered the source-priority choice, not the proposed threshold.
  The existing card threshold remains the baseline; no new document threshold
  is treated as accepted by this response.

### Q0025-014 — Should candidates be refilled after a late eligibility change?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If a retrieved candidate is deleted or stops being AVAILABLE before the
> response is prepared, I propose removing it and selecting replacements from
> the already collected set of up to 10, without another search or model call.
> If fewer than five remain, show fewer. Does that suit you?

Reason: exact/live prefiltering is accepted, but asynchronous changes may occur
after retrieval. A bounded local replacement rule avoids exposing stale links
or implying an unbounded refill loop. The initial 10 remains configurable; this
question does not define a lock or promise continuous eligibility after response.

User answer, translated into English:

> 14: If a stone stops being AVAILABLE, remove it and take the next one. If
> fewer than five remain, show those. If suddenly none remain at all, only
> in that case run the search again.

Accepted decision D0025-016:

- Recheck current eligibility before preparing returned references. Remove
  candidates no longer AVAILABLE; deleted candidates likewise fail the
  inherited current-existence rule.
- Replace removed candidates from the already collected eligible candidate
  pool, preserving the selected order.
- If fewer than five survive but at least one remains, present that smaller
  list without rerunning retrieval.
- If no eligible candidates survive at all, rerun the search. This replaces
  the assistant's proposed prohibition on any repeated search.
- This refresh is distinct from ordinary exact fallback for an initially empty
  semantic result. Repetition bounds, reuse of query interpretation/embedding
  and treatment of a second empty pool are not selected by this answer.

### Q0025-015 — How should card priority become deterministic ranking?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> To make card priority precise, I propose placing candidates supported by
> their own cards first, followed by candidates supported only by encyclopedia
> information about their type. Within each group, order by the relevant
> source's similarity score, with identifier ascending for ties. Does that
> express what you want?

Reason: a statement that cards have priority settles source precedence but
does not alone define order across card-supported and type-only candidates.
The proposed groups preserve type-only qualification without letting a higher
document score outrank individual card support. Evidence conflicts are resolved
before ranking, not by this score rule.

User answer, translated into English: "15: Yes."

Accepted decision D0025-017:

- Rank candidates with supported wishes in their individual cards ahead of
  candidates supported only through encyclopedia evidence about their type.
- Within the card-supported group, order by card similarity descending.
- Within the type-only group, order by relevant type-passage similarity
  descending. Use the best applicable passage score for that type rather than
  an unrelated passage's score.
- Use stone identifier ascending for ties within a group.
- Resolve individual contradictions before treating a wish as supported.

This is the accepted deterministic initial order, not proof that the embedding
scores measure satisfaction of every wish or that source quality is qualified.

### Q0025-016 — May semantic suitability be checked by a bounded model decision?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Similarity finds related text but does not prove that a wish is supported.
> I propose an additional structured model check of the bounded retrieved
> card/document evidence: it identifies supported wishes and contradictions,
> while the backend owns IDs, exact constraints and availability. Shall we
> include this check in 0025? Explanatory answer generation remains in 0026.

Reason: the accepted absence-of-information and card-precedence rules require
more than treating a high embedding score as a factual assertion. This is a
proposed evidence classifier, not a model-generated SQL operation, catalog tool
call or answer-writing invocation. The shared deadline, model admission and
token accounting must apply; the invocation/correction limit is a separate
question if this stage is selected.

User answer, translated into English: "16: Yes."

Accepted decision D0025-018:

- Include structured model-based checking of the bounded retrieved card and
  encyclopedia evidence in feature 0025.
- The check identifies supported wishes, missing information and explicit
  contradictions; it does not create catalog facts or decide live availability.
- Backend code owns candidate identifiers, exact filtering, live eligibility,
  result ordering and bounded selection. Never accept invented candidate IDs
  or model-generated database operations.
- Substantive explanation and answer generation remain in feature 0026.
- Shared invocation admission, daily token accounting and turn deadline apply
  to evidence checks as to other model work.

The concrete evidence DTO, supported/unsupported-wish representation, context
packing and invocation/correction bounds remain technical preparation work.

### Q0025-017 — How many zero-survivor refreshes are allowed?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose allowing one repeated search when every candidate becomes
> ineligible, reusing the interpreted request and its query embedding. If that
> second attempt also leaves no eligible candidates, report that suitable
> candidates were not found. Do you agree with a single repeat?

Reason: the user's accepted zero-survivor rerun needs a finite bound under the
existing turn deadline. Reusing interpretation and embedding avoids charging
another query-model call solely for a catalog eligibility race. A changed pool
still needs applicable evidence handling and a final live check.

User answer, translated into English:

> 17: Yes, repeat only once, and only if the previous candidates disappeared.
> If the previous search returned zero, do not repeat it.

Accepted decision D0025-019:

- Permit at most one retrieval refresh per turn.
- It is triggered only when a previously nonempty candidate set loses every
  candidate through a subsequent current-existence/AVAILABLE check.
- An initially empty retrieval or semantic set does not trigger this refresh.
  Apply the selected ordinary absence/exact-fallback behavior instead.
- Reuse the interpreted request and compatible query embedding for the refresh;
  do not repeat model interpretation or compute another embedding solely for it.
- If the refresh likewise leaves no eligible candidates, report the
  suitable-candidates-not-found outcome; no second refresh is permitted.
- Partial survival produces a smaller presentation or replacements from the
  existing pool, not a refresh.

Evidence for new candidate/source content still requires applicable validation;
reuse does not authorize applying another stone's previous evidence decision.

### Q0025-018 — What do multiple semantic wishes mean?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> For "red for a shelf", may a candidate satisfy only part of these soft
> wishes, with the unsatisfied part made explicit in the later explanation?
> I propose yes; exact conditions remain mandatory. Or should a semantic
> candidate have supporting evidence for every wish?

Reason: treating color as a ranking preference allows alternatives, but does
not yet settle selection for several semantic wishes at once. A partial match
must remain distinct from no supported wishes and from an exact-only fallback.
Card contradictions still require the agreed individual source precedence.

User answer, translated into English:

> 18: Allow a semantic candidate with a description of which part did not fit.

Accepted decision D0025-020:

- A candidate may qualify semantically by satisfying part of the user's soft
  wishes. It need not have support for every wish.
- Retain which wishes are supported and which are not, so the answer can state
  which part did not fit. Do not present a partial match as satisfying all wishes.
- Feature 0025 owns the structured evidence/result metadata needed for this
  statement. Feature 0026 owns substantive explanatory wording, as already
  agreed; a basic 0025 presentation must still avoid falsely claiming full fit.
- Exact constraints and current availability remain mandatory.
- A candidate with no supported wishes is not a partial semantic match. It may
  only enter a separately identified exact fallback when the semantic set is empty.

The distinction open at this point was that the card-conflict example described exclusion for
the contradicted wish; partial matching now requires deciding whether another
supported wish can preserve that stone in the candidate set. Q0025-019 asks this
explicitly rather than silently changing the earlier decision.

### Q0025-019 — May another supported wish preserve a card-conflicted candidate?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> A request asks for red and suitable for a shelf; the card supports red but
> explicitly says this stone is unsuitable for a shelf. Should it remain a
> partial candidate with that mismatch stated, or should the explicit
> contradiction exclude it entirely? I propose keeping it as a partial match:
> the card wins over the encyclopedia, and we do not claim shelf suitability.

Reason: card precedence and partial semantic matching are both accepted, but
neither alone settles whether an explicit contradiction is a candidate-wide
veto or just prevents a particular wish from being supported. The proposed
example refers to voluntarily supplied card text, not a new color field.

User answer, translated into English:

> 19: Exclude. The card must never contradict.

Accepted decision D0025-021:

- An explicit contradiction in an individual card is a candidate-wide veto,
  even when the same card supports another soft wish.
- Apply this veto to semantic candidates and exact alternatives for the mixed
  request. A fallback does not authorize recommending a known contradictory
  specimen.
- An unknown or unsupported wish is different: partial candidates remain allowed
  when another wish is supported and the card contains no contradiction.
- The assistant's proposal to retain a contradicted candidate as a partial
  match is rejected. This answer settles the ambiguity left by D0025-020.

### Q0025-020 — May evidence checks use bounded batches within existing limits?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Up to 10 cards plus document passages may not fit in one model context.
> May we check them in several small batches, preserving the 120-second turn
> deadline, 50-second maximum per chat-model call and shared daily budget?
> I propose an initial configurable cap of 20 evidence-check calls per turn,
> including corrective attempts and checks after the one allowed refresh, with
> failure reported if the deadline or budget is exhausted rather than presenting
> unchecked results as successful matches.

Reason: selecting an additional evidence-classification stage does not enlarge
the model context, invocation allowance or runtime deadline automatically.
The proposed cap covers at most 10 candidate-specific checks for each of two
retrieval pools when single-candidate batches are necessary; corrective attempts
also consume this cap, so it does not promise successful completion of every
possible input. Larger fitting batches can use fewer invocations. Batching
avoids silently truncating individual evidence or dropping candidates
only because the combined prompt is too large. Exact bounds and failure
translation must be recorded before implementation.

User answer, translated into English: "20: Yes."

Accepted decision D0025-022:

- Evidence classification may use several bounded batches rather than assuming
  all cards and passages fit one prompt.
- Start with a configurable maximum of 20 evidence-check invocations per turn.
  Corrective calls, document applicability checks and checks of a refreshed
  candidate pool consume the same counter; it is not reset on retrieval refresh.
- Retain the 120-second overall turn deadline and 50-second single-call bound,
  with the remaining turn time taking precedence.
- Retain shared model capacity and the UTC daily token budget. The call cap is
  a ceiling, not a guarantee that the deadline or available daily budget allows
  every configured call.
- Exhausted capacity, call/context bounds, deadline or token budget must not
  produce a successful answer containing unchecked candidates or an ordinary
  zero-match result. Preserve explicit failure and no completed conversation pair.

Context packing, bounded structured-output repair and protocol translation are
technical choices recorded below and in plan.md, not additional product features.

### Q0025-021 — What initial bounded configuration should document retrieval use?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose keeping the initial similarity threshold at 0.5 for cards and
> using a separately configurable initial 0.5 for encyclopedia passages;
> retrieve up to 10 passages per search, also configurable. Do you agree with
> those starting settings, leaving quality evaluation to feature 0027?

Reason: the existing card threshold does not establish a document threshold or
document-retrieval cap. Candidate count and passage count are distinct limits.
These are proposed initial bounds, not universal quality claims or permission
to tune, benchmark or invoke the models during discussion.

User answer, translated into English: "21: Yes."

Accepted decision D0025-023:

- Initial card minimum similarity remains 0.5 through the existing card setting.
- Initial encyclopedia minimum similarity is separately configurable and also
  starts at 0.5. Include scores equal to the applicable threshold.
- Retrieve at most 10 encyclopedia passages per retrieval pass initially,
  controlled separately from the initial 10-stone candidate cap.
- Initial values are not a qualified optimum. Broader retrieval/answer-quality
  evaluation belongs to feature 0027; basic functional qualification remains
  mandatory before feature 0025 production integration.

## Technical Preparation Choices Within the Accepted Behavior

These are assistant-selected implementation details, recorded separately from
the user's actual answers. They implement the agreed behavior without authorizing
execution or claiming qualification.

- Use a semantic interpreter and evidence-provider boundary alongside existing
  exact search; reuse its date, reference, exact-criteria and guard behavior.
  Exact-only turns need no evidence calls or query embedding. Do not interpret
  the same turn twice merely to route between exact and semantic execution.
- Embed the effective semantic wishes once per turn, using the existing query
  instruction/profile and CUSTOMER admission. Pass the prepared vector to both
  card and passage queries and reuse it on the one allowed retrieval refresh.
- Add an isolated bounded semantic repository over the existing stone/card/
  document tables. Existing card retrieval lacks live filters and existing
  document retrieval allows only one to five passages; leave those supporting
  contracts intact rather than changing ingestion or qualification semantics.
- Read current factual card fields for evidence. Delayed vector text must not
  override a contradictory current card. Source/profile provenance travels with
  evidence; no current-model general knowledge may certify a candidate wish.
- Candidate and passage limits apply per pass. The evidence-call counter and
  turn deadline apply to the entire turn, including exact alternatives and a
  refresh. Do not continue retrieval until an exhaustive match set is discovered.
- Keep internal semantic and bounded exact-alternative counts distinct from the
  exhaustive database count used by unchanged pure exact search. Only final
  eligible, evidence-checked candidates count toward the overflow notice.
- Use configuration names STONE_SEMANTIC_SEARCH_CANDIDATE_LIMIT (10),
  STONE_SEMANTIC_SEARCH_DOCUMENT_LIMIT (10),
  STONE_SEMANTIC_SEARCH_DOCUMENT_MIN_SIMILARITY (0.5), and
  STONE_SEMANTIC_SEARCH_MAX_EVIDENCE_CALLS (20). Reuse
  STONE_CARD_SEARCH_MIN_SIMILARITY (0.5). Counts are positive integers; candidate
  capacity must be at least six so a sixth result can establish overflow.
  Similarity settings are finite and within [-1, 1]. Invalid configuration fails
  explicitly. No persistent environment change is performed during planning.
- Keep the existing initial 8192-token chat context and 512-token output cap.
  Qualify compact interpretation and evidence prompts before integration. Split
  evidence into complete-source segments and bounded candidate/wish batches;
  aggregate every required segment before calling a wish unsupported. Never
  silently truncate a source or convert unprocessed evidence into missing facts.
- Permit at most one corrective attempt for a malformed structured batch,
  consuming the same total evidence-call cap. A still-invalid decision fails
  with HTTP 502 CHAT_INVALID_MODEL_OUTPUT. Backend validation checks the supplied
  candidate/wish/source IDs and actual quoted evidence, not just valid JSON.
- Local call/context exhaustion uses a protocol-independent limit exception,
  translated by ApiExceptionHandler to HTTP 503 CHAT_SEMANTIC_LIMIT_EXCEEDED.
  It does not invalidate healthy history or replay. Actual dependency/control
  failures retain the existing shared fail-closed policy; budget/capacity
  rejections retain HTTP 429/503 and their existing retry guidance.
- Backend templates provide the basic absence, partial-match, exact-alternative
  and overflow text in 0025. There is no substantive model-written explanation
  in this feature; structured evidence is the handoff to 0026.
- Store effective semantic wishes and result evidence as optional internal
  completed-turn metadata; do not change public success/history DTO fields or
  existing conversation identity, TTL, replay or failed-turn atomicity.
- No PostgreSQL schema change, dependency/version change, Kafka implementation
  or frontend source edit is needed by this plan. Generated OpenAPI remains the
  HTTP source of truth; descriptive contract requirements are in spec.md.

## Resolution and Handoff

All 21 presented feature questions have recorded answers. There are no remaining
unanswered product questions for preparing feature 0025 documentation. Future
implementation must first run the requested batch's readiness ticket, then its
basic qualification gate. A qualification failure is a blocker to report, not
permission to change accepted behavior, models, resource limits or ticket scope.

Feature 0026 will discuss substantive explanation, answer context and its own
generation limits, using this feature's structured candidate/evidence result.
No 0026 answers or implementation are inferred from the 0025 discussion.

## Accepted Out-of-Scope Decisions

Matching accepted entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0025-001 | Offering or opening a catalog view filtered by the found candidate IDs | For now, show at most five stones in chat and state that more were found when applicable. A future agent-assisted offer to view the found IDs in the catalog is deferred; no catalog-navigation integration or additional-results UI is implemented in this feature. |
| OOS-0025-002 | Substantive evidence-based explanation and model-written answers | Feature 0025 selects/checks candidates and provides minimal truthful backend-template text. Feature 0026 owns substantive explanations using the structured card/document evidence, as accepted in Q0025-008 and Q0025-016. |
| OOS-0025-003 | Broad query-quality evaluation and threshold tuning | Use the agreed initial source thresholds/limits and mandatory basic functional qualification here. Feature 0027 owns the broader representative-query quality evaluation, as accepted in Q0025-021; no tuning campaign is part of 0025. |

Only confirmed boundaries are recorded here and in the central register.
Unanswered proposals and hypothetical additional exclusions are not decisions.

## Documentation Completion Gate

The feature's specification, acceptance criteria, implementation plan and task
list are prepared from the recorded answers and technical choices. All
implementation tasks remain unchecked. This ADR is the detailed discussion
record, not implementation evidence; acceptance criteria live exclusively in
acceptance.md. Documentation checks establish consistency only, not current
environment readiness, model qualification or feature acceptance.

## Execution Request and Blocked Readiness — 2026-10-13

The user requested execution of every task in features 0025–0029 without stopping
at individual task/feature boundaries, and one commit for each entire completed
feature rather than one per task. This is explicit implementation/test/feature-
commit authorization for that batch, superseding the earlier documentation-only
stop boundary. It does not authorize pushing, feature 0030, relaxing acceptance,
production fixes inside 0027 qualification, or replacing actual human review.
No new product question or exclusion was introduced by this execution request.

Fresh batch preflight is BLOCKED, as recorded in
[the feature task evidence](../../specs/0025-semantic-stone-candidate-search/tasks.md#requested-batch-readiness--2026-10-13).
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
[the current task readiness evidence](../../specs/0025-semantic-stone-candidate-search/tasks.md#fresh-batch-readiness-after-browser-connection--2026-10-13).
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


## T0025-002 — Actual Prototype Qualification Failure

The user's browser connection removed the environmental browser blocker. The
agent prepared the current preceding backend and verified fresh full-batch
external readiness before creating the two explicitly allowed qualification
artifacts. Only the named class was executed. Four tests ran: compatible vectors
and full fresh-input capacity passed; interpretation and evidence failed. Final
production providers and broad 0027 quality were not exercised.

The first test-source compile failures were agent mistakes: the existing mapper
requires both settings and Clock, the embedding entity exposes vector(), primitive
double-array assertions require a stream finite-value check, and Liquibase setup
throws a checked exception. Those defects were corrected in the new test before
any live model execution. They are not model qualification failures.

### Retained original and permitted corrective outputs

Input: Find a stone for a shelf. Expected: SEARCH with the explicit use wish,
no invented color or date. Original response:

```json
{"action":"CLARIFY","criteria":{"stoneTypes":[],"stoneSizes":[],"availability":["AVAILABLE"],"admissionDateRanges":[{"startInclusive":"2026-10-13","endExclusive":"2026-10-14"}]},"resetSearch":false,"reference":null,"questions":["What color do you prefer for the stone on a shelf?","Is there a specific use in mind for this stone?"],"reason":"More specific criteria are needed to proceed.","wishes":[{"key":"use","text":"shelf"},{"key":"color","text":""}]}
```

After the single permitted correction:

```json
{"action":"CLARIFY","criteria":{"stoneTypes":[],"stoneSizes":[],"availability":["AVAILABLE"],"admissionDateRanges":[{"startInclusive":"2026-10-13","endExclusive":"2026-10-14"}]},"resetSearch":false,"reference":null,"questions":["What color do you prefer for the stone on a shelf?","Is there a specific use in mind for this stone?"],"reason":"More specific preferences are needed to narrow the search.","wishes":[{"key":"color","text":"neutral"},{"key":"use","text":"decorative"}]}
```

First evidence fixture: B11, current biography This stone is suitable for a shelf.,
wishes suitable for a shelf and blue, no supplied document sources. Expected:
card-supported use, unknown blue, no source ID. Original response:

```json
{"candidates":[{"id":11,"wishes":[{"key":"use","cardVerdict":"SUPPORTED","cardQuote":"Name: B11. Type: BASALT. Size: SMALL. Biography: This stone is suitable for a shelf.","sources":[{"id":"url","verdict":"SUPPORTED","quote":"Name: B11. Type: BASALT. Size: SMALL. Biography: This stone is suitable for a shelf."}]},{"key":"color","cardVerdict":"CONTRADICTED","cardQuote":"blue","sources":[]}]}]}
```

After the single permitted correction:

```json
{"candidates":[{"id":11,"wishes":[{"key":"use","cardVerdict":"SUPPORTED","cardQuote":"Name: B11. Type: BASALT. Size: SMALL. Biography: This stone is suitable for a shelf.","sources":[{"id":"url","verdict":"SUPPORTED","quote":"Name: B11. Type: BASALT. Size: SMALL. Biography: This stone is suitable for a shelf."}]},{"key":"color","cardVerdict":"SUPPORTED","cardQuote":null,"sources":[{"id":"url","verdict":"SUPPORTED","quote":"Name: B11. Type: BASALT. Size: SMALL. Biography: This stone is suitable for a shelf."}]}]}]}
```

Strict independent assertions rejected these outputs. Interpretation mixed/
availability examples and later type-only/veto/unknown evidence examples were not
reached; this run provides no positive qualification evidence for them. Actual
chat input/output usages were 237/135, 259/134, 287/183 and 309/232. Seven actual
reservations (four chat, three embedding) charged 1840 tokens in the isolated
PostgreSQL ledger on actual UTC day 2026-10-13. No production usage was deleted.
Successful vectors and capacity checks are recorded in tasks.md. No dependent
implementation, completed feature commit or push followed the failed gate.

### Concrete pending correction proposal — not approved or applied

Read-only source evidence: OllamaChatRepository.prompt unconditionally inserts
an exact-search-only availability clarification pair before every supplied model
request, including these semantic/evidence requests. Its assistant example has
no wishes or evidence schema. The prototype additionally permits arbitrary source
IDs in its evidence JSON schema. These are observed prompt/schema weaknesses;
the claim that the injected example caused the model failure is a hypothesis,
not a proven root cause or an assertion that qwen2.5:3b cannot handle the feature.

Proposed smallest transport change for review:

1. Add an explicit internal opt-out flag to StoneModelInvocationEntity, default
   true, so existing exact-search callers keep their currently qualified prompt.
2. Wrap only the two exact-only example messages in OllamaChatRepository.prompt
   in that flag; preserve system/history/current input, byte-bound accounting,
   probe, transport, retries, usage validation and all resource limits.
3. The test-only semantic prototype opts out and uses a compact example matching
   its own schema. Constrain evidence IDs/keys/source IDs/cardinality to the
   actual supplied inputs (empty sources means maxItems 0), while retaining
   independent backend-like quote, scope, unknown and contradiction assertions.
4. Requalify changed inputs only with the same approved models, 8192/512 limits,
   original per-example deadline and one correction. Retain this failed run;
   do not replace it with a claim that the first qualification succeeded.
5. Since the shared transport changes, rerun focused existing exact-provider
   prompt/wire assertions without repeating its already-successful real-model
   examples or running a full suite.

Proposed code shape (documentation only):

```java
// StoneModelInvocationEntity: default preserves every existing exact caller.
private boolean includeExactSearchExample = true;
public boolean getIncludeExactSearchExample() { return includeExactSearchExample; }
public void setIncludeExactSearchExample(boolean value) { includeExactSearchExample = value; }

// OllamaChatRepository.prompt: only the existing example pair is conditional.
if (invocation.getIncludeExactSearchExample()) {
    messages.add(new UserMessage("Show all available stones."));
    messages.add(new AssistantMessage(CLARIFICATION));
}
```

Additional exact writable paths requiring agreement before this correction:

- stone-shelter-api/src/main/java/lab/stoneshelter/entities/StoneModelInvocationEntity.java
- stone-shelter-api/src/main/java/lab/stoneshelter/repositories/OllamaChatRepository.java

Existing authorized test/resource/common evidence files remain in T0025-002.
Proposed regression selector, existing class read-only:
StoneExactSearchQualificationTest#failedSdkInvocationHasNoHiddenRetry+
providerTrimsOnlyWholePairsPreservingCurrentRequestAndUtcInstructions+
productionJdkTransportBoundsHeadersAndBodyAndDoesNotRetry.

These two production paths and regression selectors are outside T0025-002's
current exact allowlist/selector. No permission is inferred from the failure;
await explicit agreement, update task execution scope first, and then apply the
reviewed correction. Requirements, thresholds, deadlines, budget and acceptance
gold remain unchanged. This pending proposal is not an accepted exclusion and
therefore does not add an out-of-scope-register entry.


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


### Authorized correction attempt 1 — retained outcome

The two reviewed transport changes were applied with includeExactSearchExample
true by default, false for new semantic prototype calls. Dynamic evidence schema
IDs/source cardinality now reflect supplied inputs. Focused rerun: three controlled
exact-search methods PASS; changed semantic capacity PASS (upper bound 7780);
interpretation/evidence methods FAIL. Six tests total, two failures, no errors.
The unchanged successful vector qualification was not repeated.

Shelf-only SEARCH and partial-card support now pass. Mixed small red basalt for
a shelf still omits color on both attempts. Type-only support still copies the
document quote into cardVerdict SUPPORTED on both attempts; the card is silent.
Later generic/veto/unknown cases were not reached. Actual chat usages were
215/105, 225/110, 247/110, 233/53, 235/58 and 257/58: six reservations,
1906 charged tokens in the isolated actual UTC-day ledger. Prior failed evidence
is retained and requirements/gold/resource settings are unchanged.

Evidence-supported next correction within the user's broader necessary-type
approval: retain the optional exact example; make the compact interpretation
example include simultaneous color/use wishes; classify individual-card and
type-source text in distinct bounded source batches with supplied-ID constraints.
Backend aggregation retains candidate/wish/source coverage and card precedence.
No quote can acquire card authority merely because the document text was present
in the same prompt. This changes internal prototype packing, not a public HTTP
contract or expected outcome. Requalify changed formats and capacity; do not rerun
unchanged successful exact regression or vector checks.


### Authorized correction attempt 2 — retained outcome

Separate source classification now passes partial-card, type-only, individual
contradiction and unknown evidence, with verbatim source checks and unchanged
expected verdicts. Capacity passes (7880 upper bound). Interpretation fails at
shelf-only because the model adds a color wish whose text is the literal string
null; one repair repeats that invalid invented wish. Three tests, one failure;
unchanged vectors/exact transport were not rerun. Actual usages: 207/73,
193/35, 182/37, 201/49, 182/37, 189/35, 176/34, 257/61, 279/61;
nine reservations charged 2288 tokens in the isolated actual UTC-day ledger.

The remaining defect is variable wish-array shape/absence representation. The
next necessary internal-format correction uses explicit nullable color/use slots
and an additional keyed-wish list, then maps only present wishes to canonical
key/text criteria. This does not add a structured color to a catalog stone or
relax the independent expected wished-key oracle. JSON null means absence;
literal null/empty placeholders remain invalid. Requalify only the changed
interpretation/capacity inputs; evidence classification is unchanged and is not
repeated. No public route, model/profile, resource bound or requirement changes.


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


## Bounded Retrieval Delivery — T0025-003

Implemented the planned settings/domain/shared carriers, live-filtered SQL
repository and response mapper. Five focused isolated PostgreSQL tests pass;
no model calls or user catalog/source mutations occurred. Invalid test source-
version was corrected to the existing immutable-source SHA-256 contract, and
queries remain scoped to the approved encyclopedia document. The initial new
settings/defaults alone were documented in environment-setup.md. No acceptance
gold, existing migration or dependency was changed.

Next, T0025-004 needs a shared structured-model invocation service, a turn-wide
execution/counter/cache entity, a source-level verdict enum and an input mapper
in addition to the originally prepared types. Exact additions/reasons/selector
are recorded in tasks.md under the user's necessary-type approval. Source
classification preserves the independently qualified card/type authority boundary.
Production integration, final qualification and feature commit remain pending.

## T0025-004 — Actual Outcome

PASS: StoneSemanticEvidenceTest, seven tests; live-row mapping recheck
StoneSemanticRetrievalTest, five tests. Twelve tests, no failures/errors.
Controlled transport/providers only, no inference. Strict complete source/wish
pairs and verbatim quotes, separate card/type authority, current-card veto,
partial/unknown aggregation, complete sentence segmentation, cache invalidation,
context/output packing, one repair and whole-turn cap, actual usage settlement
and permit retention until actual worker exit passed. Short source aliases and
48-character verbatim quote bounds keep the qualified 512-token output batches
bounded without truncating evidence. Actual storage UUID/photo are mapped from
current rows. No feature completion/commit is claimed.

## T0025-005 — Necessary Types Before Execution

Under the user's approved 0025–0029 necessary-type scope, add
entities/StoneSemanticPreparedTurnEntity.java and
mappers/entities/StoneSemanticPreparedTurnEntityToStoneChatTurnEntityMapper.java
for a domain-only prepared selection handoff and mapped completed turn;
mappers/dtos/StoneSemanticSearchCriteriaToStoneSemanticSearchStateResponseMapper.java
for mandatory prompt state; and internal evidence fields on StoneChatTurnEntity
for optional persisted checked facts. The semantic raw decision uses nullable
color/use slots qualified by T0025-002 and mapped keyed wishes. The exact
preparation API is reused after the single semantic interpretation, without a
second model call or soft-wish rejection. Existing focused selectors are retained.

T0025-005 additionally requires entities/StoneSemanticCheckedCandidateEntity.java:
compact displayed-candidate ID/wishes/checked-proof metadata avoids persisting
JPA associations, full vector projections or per-turn execution state in history.
It remains optional internal metadata and does not add HTTP fields.

T0025-005 also updates StoneChatbotControllerTest's unused search mock bean
to the new semantic delegate type so existing controller/history/transport test
contexts retain their original HTTP assertions. The new focused integration
selector reuses that native HTTP context for generated JSON/YAML and semantic
error contracts, alongside actual isolated PostgreSQL and Redis flow fixtures.

## T0025-005 — Actual Outcome

PASS: StoneSemanticChatIntegrationTest, eight tests; exact preparation and chatbot
regressions, 19 tests. Real isolated PostgreSQL/Redis, native HTTP generated
JSON/YAML, controlled inference/embeddings. Covers single interpretation, exact-only
no semantic calls, current-card priority/veto, partial/overflow counts, exact
alternatives only at semantic zero, partial late removal and exactly one shared-
vector refresh, state retention/replacement/reset, persisted compact proof, replay
without new calls/cookie, healthy local-limit history and fail-closed dependency
errors. Initial integration setup failed due to an incorrect embedding accessor,
spying a Spring transaction proxy, and restubbing a throwing/default-answer mock;
fixed only test wiring, retaining assertions. No production inference occurred.

The internal domain prepared-turn handoff avoids reverse DTO mapping and persists
only displayed IDs/wishes/proofs; the dedicated ready response API remains mapped
and available. The updated technical plan records this implementation choice.
T0025-006 final production qualification/architecture is still pending.

## T0025-006 — First Final Qualification and Necessary Correction

Final production check failed: interpretation generated an unsolicited invalid
date D2026-10-12T00:00:00Z after its one correction; the first partial-card fixture
classified absent blue information as CONTRADICTED instead of UNKNOWN. Compatible
real vectors and the complete initial-state current-input capacity bound 8045
passed. Isolated actual UTC-day ledger: six reservations, 1406 charged tokens.
No acceptance/commit or dependent feature implementation is claimed.

The architecture fixture initially used a domain-package wildcard that also
matched mappers.entities, and treated a settings Component as an application
Service. Correct its selectors to actual domain packages and Service annotations;
responsibility and transaction requirements remain unchanged.

The user's explicit necessary-type correction approval covers the required
0025 production prompt corrections: OllamaSemanticSearchProvider's compact full
JSON example/empty date groups and OllamaSemanticEvidenceProvider's explicit
absence-versus-contradiction example. This reopens only the affected T0025-004/005
provider inputs. Preserve the independent fixture expectations, profile, 8192/512,
50/120-second bounds and all counters/thresholds. Recheck changed interpretation,
evidence and prompt capacity plus corrected architecture; retain the successful
unchanged vector evidence instead of repeating its inference.

Final correction attempt 1: card partial support and shelf/mixed interpretation
now pass; capacity 8083 passes and both architecture tests pass. Generic explicit
availability still produces SEARCH rather than CLARIFY; the type-only card
classification twice cites a quote that is not acceptable verbatim evidence.
Six reservations charged 2218 actual tokens. Unchanged vectors were not repeated.

Next necessary correction reuses the existing qualified deterministic
StoneExactSearchSettings.schemaFor availability-only constraints. Its pure schema
helper and availability matcher become static (no configuration/behavior change),
so the semantic provider can add its wish grammar to that already constrained
schema while respecting inherited narrowing/wishes. Include
services/StoneExactSearchSettings.java in the necessary type scope.

Add raw production evidence diagnostics to the test-only qualification wrapper
to distinguish unsupported verdicts from malformed quotes before further fixes.
Repeat only failed semantic interpretation/evidence cases as required by changed
inputs; retain successful unchanged capacity/architecture/vector evidence.

Final correction attempt 2: all three interpretation fixtures PASS, including
forced clarification for explicit fresh availability-only. Raw evidence confirms
the remaining defect: the silent B12 card is classified SUPPORTED with quote
suitable for a shelf, borrowed from the prompt/request rather than its current
text; the generic one repair repeats it. Six reservations, 2243 charged tokens.

Necessary evidence correction removes that overlapping positive-shelf example
and clearly distinguishes a requested wish from source facts. Carry a bounded
internal validation correction through InvalidStoneSemanticSearchOutputException
and StoneSemanticEvidenceService's one allowed repair, identifying a nonverbatim
quote by supplied alias/key without exposing source text through public errors.
Exception messages remain generic; the counter, one-repair policy and strict
quote oracle are unchanged. Recheck controlled evidence and only the remaining
real evidence fixture. Successful unchanged interpretation/capacity/vector/
architecture evidence remains retained.

Evidence correction attempt 3: seven controlled evidence tests PASS, but real
silent-card classification still copies the wished phrase suitable for a shelf
as a quote (CONTRADICTED) on both original and precise correction. Three
reservations charged 975 tokens. Nothing invalid was accepted; no thresholds,
gold expectations, limits or successful feature acceptance were changed.

The observed confusion is between two text fields in the wire input: desired
wish text and supplied source text. The next necessary compact-wire correction
encodes desired wishes as a key-to-desire map, separate from the source text
objects. Shared application DTOs and output/provenance contracts remain unchanged.
This preserves all wishes/source text and reduces prompt bytes; no evidence is
skipped. Verify this wire distinction through controlled SDK assertions, then
repeat only the changed real evidence fixture.

Evidence correction attempt 4: controlled SDK/guard/evidence checks PASS. Real
wire separation removes the borrowed quote, but the silent card is incorrectly
CONTRADICTED using none, and the positive type passage is also CONTRADICTED.
Three reservations, 952 actual tokens. The independent semantic oracle correctly
rejects this result. The new request assertion initially used the wrong
MockRestServiceServer API; corrected its technical API without altering assertions.

Restore the source-specific format already qualified in T0025-002: one source
object plus the wish array and the qualified concise instructions. Do not send
redundant stone-type metadata to the classifier: backend candidate/source scope
validation owns applicability before invocation, and the actual text remains
complete. Pack wishes/meaningful source segments within the same 512 bound, with
one source per model batch. This is a compact format choice, not a scope/ranking/
resource change; all required sources are still processed with the same 20-call
cap, cache, one repair and deadline. Full multi-source arrays were not qualified
on the installed small model and are not claimed delivered. Recheck controlled
packing/transport/integration, changed real evidence, and final architecture.

Evidence correction attempt 5: controlled evidence, integration and architecture
pass (17 tests). Real source-specific classification still assigns CONTRADICTED
to both the silent card and positive type passage, using valid but irrelevant
quotes. Three reservations, 805 tokens. This remains a semantic qualification
failure and blocks completed-feature acceptance; no commit is made.

Before another production edit, reopen the test-only T0025-002 format gate in
the existing qualification class: qualify two explicit boolean questions,
supported/contradicted, mapped to the same SUPPORTED/UNKNOWN/CONTRADICTED domain
verdicts. Both true is invalid; both false is UNKNOWN/null. Use the unchanged
four-case source/wish/quote oracle, short known aliases, existing guarded
transport and unchanged resource limits. This tests whether the selected model
can reliably express the accepted semantics without conflating the verdict
labels. It does not loosen the gold set or claim final production readiness.

Binary test-only gate failed on the first partial-card fixture: absent blue is
marked contradicted=true with null quote. One reservation, 344 tokens. No binary
production format was implemented.

A final targeted diagnosis replays the exact original T0025-002 qualified
source-specific protocol: fixture instructions, full supplied source identifiers
and the original quote schema, rather than newly introduced aliases/grammar
constraints. Keep the same independent oracle and at most its original single
correction. This separates production-format differences from a failure to
reproduce the earlier model qualification. No additional query set or resource
change is authorized; if this reproduction fails, dependent delivery remains
blocked rather than claiming that the selected model has been qualified.

Original-protocol reproduction also fails: the model treats Biography: none.
as an explicit contradiction on initial and corrective calls. Three reservations,
776 tokens. This rules out newly added aliases alone as the cause.

Inspection identifies a concrete source-preparation defect: the production input
mapper invents the technical text Biography: none. for a null biography. That is
absence metadata, not a factual negative statement. Correct
StoneSemanticCandidateEntityToStoneSemanticEvidenceCandidateRequestMapper to omit
only that absent field; preserve every supplied current name/type/size/biography
fact. No actual source text or wish is truncated or reclassified by code.

Final qualification must exercise that actual production card mapper, not the
prototype's legacy textual absence sentinel. Decode the fixture's existing
explicit card fields, with its none sentinel meaning null biography, and render
through the mapper. Keep the original source/wish/quote/UNKNOWN oracle unchanged.
The final four acceptance methods remain; diagnostic-only original/binary
experiments are recorded here and removed after diagnosis, rather than adding
failed experimental protocols to the production acceptance suite. No accepted
requirement/assertion, selected model, limit or profile is removed or changed.

Actual mapped-card final evidence PASS: all four fixed cases, eight real calls
including one permitted precise quote correction, eight reservations charged
2071 tokens. Silent card remains UNKNOWN, type passage SUPPORTED; explicit card
veto and general absent blue remain correct. Controlled evidence seven tests and
isolated live/Redis/native-HTTP integration eight tests also PASS (16 together).
Original failed protocols and charges remain recorded; none were accepted.

Before final acceptance, extend existing focused integration assertions to
exercise inherited native HTTP capacity/budget/dependency codes and retry/cookie
headers, and actual deletion alongside reservation in the late eligibility
fixture. Recheck only those changed methods plus final architecture. No new
selector class, inference, requirement, dependency or feature scope is added.

## T0025-006 — Final Outcome and Feature Review

PASS: final mapped-card/source production evidence (four fixed cases, eight actual
calls including one bounded repair, 2071 tokens); final interpretation (three
fixed inputs, already passed after availability-schema reuse); compatible real
vectors and complete fresh input/required-state capacity bound 8083 retain their
unchanged passes. Final architecture: two tests, no failures. The extended native
HTTP/late-deletion methods: two tests, no failures. No unchanged successful
inference was needlessly repeated, no full backend suite was run.

Focused controlled evidence seven tests, retrieval five, integration eight,
exact preparation 16 and chatbot atomicity three passed. Changed integration
methods were rechecked as documented. Generated JSON/YAML expose the inherited
public shapes and semantic/invalid/capacity/budget/dependency outcomes; native
retry and failed-response cookie assertions pass. AC-0025-001–015 review is
recorded in acceptance.md. No broad 0027 quality/human acceptance is inferred.

Earlier failures remain historical evidence; the final format is one source
with bounded wish batches, short source aliases, complete meaningful segments,
strict verbatim 48-unit quotes and precise one-repair feedback. Missing null
biography is omitted, not turned into a negative source assertion. No model,
threshold, resource, HTTP success-field, dependency or schema change was made.
All T0025 tickets are complete; no push is authorized.
