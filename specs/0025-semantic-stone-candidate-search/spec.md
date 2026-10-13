# Feature 0025: Semantic Stone Candidate Search

Status: Documentation prepared from the accepted discussion. Not implemented or
runtime-qualified; execution requires a separately requested batch and readiness.

## Goal and References

Combine factual card evidence, indexed encyclopedia evidence, exact constraints
and current catalog eligibility to produce bounded search candidates through the
existing English chat flow.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Detailed questions, answers and decisions: [ADR-0025](../../docs/adr/0025-semantic-stone-candidate-search.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).

## Request Interpretation and Conversation

- Preserve the exact type, size, availability and UTC admission-date rules from
  feature 0024, including OR within groups and AND between groups. AVAILABLE is
  mandatory. Trusted ordinal/page references remain references, not new general
  name/ID filters.
- Add semantic wishes such as color and intended use. A stone has no structured
  color field. Do not add one or infer individual properties from general knowledge.
- A meaningful wish such as "a stone for a shelf" permits semantic search without
  type/size filters. A generic "I want a stone" or availability-only request
  still requires clarification; no unfiltered selection is permitted.
- Retain unchanged wishes and exact constraints on follow-up. Replace explicitly
  changed wishes, and clear both for an explicit new search. Preserve negation
  in the meaning of a wish rather than silently interpreting it as a positive wish.
- Keep existing missing-year, ambiguous-reference and unavailable-reference
  clarification behavior. Pure care/geology explanation is handed to 0026;
  choosing a specimen by an intended-use wish is candidate selection here.
- Interpret each turn once, with at most one structured-output correction as in
  0024. Exact-only requests reuse exact execution without semantic embedding or
  evidence-check calls. Semantic routing must not silently drop wishes.

## Retrieval and Factual Evidence

- Reuse compatible 1024-dimensional query/card/document profiles and the selected
  embedding model/query instruction. Embed effective semantic wishes once per
  turn; pass that vector to both source queries and a permitted refresh.
- Apply current existence, mandatory AVAILABLE and all exact constraints before
  limiting candidates. A globally capped retrieval followed only by hard-filter
  rejection is insufficient. Delayed indexing remains tolerated.
- Use current name, type, size and biography as individual evidence. Indexed card
  text is a retrieval projection, not authority over a changed current card.
- Retrieve bounded encyclopedia passages already indexed by feature 0017, with
  source identity/version, section, fragment order and stone-type scope. Type
  information may qualify available specimens whose biographies are silent.
  Shared passages may supplement evidence for a known type; they must not invent
  a type association or an individual property.
- Candidate recall may originate from qualifying card similarity, applicable
  qualifying document similarity, or both. A document-supported candidate need
  not independently pass the card threshold. Missing card vectors can be tolerated
  for a document-supported candidate; do not generate per-card embeddings on demand.
- Scores are retrieval evidence, not proof of suitability. Check the bounded
  card/passages through structured model decisions for supported, unknown and
  contradicted wishes. Validate candidate IDs, wish keys, source applicability
  and cited text against backend-supplied inputs.
- The individual card always takes precedence. Any explicit card contradiction
  to an active wish excludes that candidate entirely, including from mixed-request
  exact alternatives. General type evidence cannot override it.
- A candidate without a contradiction may satisfy only part of the semantic
  wishes. Preserve matched and unmatched wishes for accurate presentation.
  At least one supported wish is required for a semantic candidate.
- If neither card nor applicable encyclopedia evidence supports a wish, do not
  invent support. If no semantic candidates qualify, say that suitable candidates
  were not found. This missing-information rule is general, not color-specific.
- Provider/database/control or incompatible-profile failure is an error, not
  missing information or a successful empty result.

## Ranking, Bounded Counts and Presentation

- Rank card-supported candidates before candidates supported only by type
  evidence. Within the first group use card similarity descending; within the
  second use the best supporting type-passage similarity descending. Use stone
  ID ascending for ties. A missing card score follows scored candidates in the
  card-supported group, then uses ID; a document score cannot displace card priority.
- Collect at most the configured stone candidate limit per retrieval pass,
  initially 10. Evidence rejection does not authorize scanning the entire catalog
  or repeatedly refilling a semantically rejected pool.
- Present at most five eligible, checked candidates. If only two qualify, show
  two. Do not fill a nonempty semantic list with exact-only alternatives.
- If more than five checked eligible candidates remain, show five and state
  that more were found. For this semantic overflow, no automatic refinement
  question or further-results operation is required.
- Count only checked candidates surviving the final live eligibility check,
  before presentation is capped at five. This is a bounded found count, never
  an exhaustive count of all semantically suitable catalog stones. A sixth
  surviving candidate establishes the additional-results notice.
- Provide minimal backend-template text identifying unmatched wishes on partial
  results. Substantive explanation from retrieved evidence belongs to 0026.

## Exact Alternatives and Eligibility Refresh

- Only when the qualifying semantic set is empty, and usable exact narrowing
  exists, obtain bounded exact alternatives satisfying those conditions.
  Order admissionDate descending and ID ascending for ties; retain current
  AVAILABLE and the card-contradiction veto. Unknown soft properties do not
  exclude an otherwise valid exact alternative.
- Explain that suitable semantic candidates were not found and identify these
  as exact alternatives, without claiming they satisfy the unconfirmed wishes.
  Semantic-only requests never fall back to an arbitrary AVAILABLE selection.
- Keep bounded exact-alternative counts distinct from semantic counts. Unchanged
  pure exact search still uses its exhaustive database match count; mixed-request
  alternatives do not inherit that claim after bounded evidence checking.
- Recheck existence/AVAILABLE for the entire candidate pool before counting or
  returning links. Remove ineligible stones and take subsequent survivors in
  the selected order. A nonempty smaller pool is presented without another search.
- If a previously nonempty pool loses every candidate at this late check, repeat
  retrieval at most once, reusing interpretation and the query embedding.
  Check new evidence where necessary. The deadline and call counter are unchanged.
- An initially empty result, a pool rejected for evidence, or a partial surviving
  pool does not trigger this refresh. The repeated pass may use the same agreed
  semantic/exact-alternative rules; if it also has no eligible results, report
  that suitable candidates were not found. No second refresh is permitted.

## Initial Configuration and Model Bounds

| Setting | Initial value | Validation |
| --- | --- | --- |
| STONE_SEMANTIC_SEARCH_CANDIDATE_LIMIT | 10 | Integer >= 6; leaves room to verify overflow beyond five |
| STONE_CARD_SEARCH_MIN_SIMILARITY | 0.5 | Existing setting; finite within [-1, 1] |
| STONE_SEMANTIC_SEARCH_DOCUMENT_LIMIT | 10 | Positive integer |
| STONE_SEMANTIC_SEARCH_DOCUMENT_MIN_SIMILARITY | 0.5 | Finite within [-1, 1] |
| STONE_SEMANTIC_SEARCH_MAX_EVIDENCE_CALLS | 20 | Positive integer; per turn, including corrections/refresh |

Include source scores equal to their threshold. Invalid supplied configuration
fails explicitly; do not silently substitute a default. These initial values are
not qualified optimal settings; broader quality evaluation belongs to 0027.

Retain qwen2.5:3b, the initial 8192-token context, 512-token output, 50-second
single-call bound and 120-second overall turn deadline. Query embedding uses the
remaining turn deadline and at most 50 seconds; background embedding settings
are unchanged. Reuse qwen3-embedding:0.6b and existing profile/digest validation.

Evidence checks may use bounded candidate/wish/source batches. Their total
invocation counter includes document applicability, correction attempts, exact
alternative checks and refreshed-pool checks. Permit at most one correction per
malformed batch, within the same configured total cap. Split evidence without
silently truncating it; aggregate required segments before classifying a wish
as unknown. If an indivisible required input cannot fit, fail explicitly.

Every call uses shared CUSTOMER admission, N=1 initially, the existing UTC daily
budget initially 100000, reservation/settlement and unknown-usage accounting.
Evidence chat calls reserve the configured chat bound; query embedding retains
its existing qualified reservation bound. No customer model-admission queue,
unaccounted call or hidden provider retry is introduced.

## Internal and HTTP Contract Requirements

Internal search consumes validated effective exact filters, trusted reference,
semantic wishes and a deadline. Its ready response contains a mode
(SEMANTIC, EXACT_FALLBACK or NO_MATCH), bounded counts/hasMore, ordered real
candidates, matched/unmatched wishes and applicable evidence provenance.
Evidence classifications are structured and constrained to supplied candidate,
wish and source identifiers; a free-form model answer is not the selection API.

Keep POST /api/v1/chat/messages and the existing conversation-history endpoint,
ownership, input limits, turnId replay, successful DTO fields and ordered links.
Store effective wishes and result evidence as optional internal completed-turn
metadata. Old history without this metadata remains readable. No new public
semantic endpoint, candidate-score field or evidence-history field is introduced.

Use backend templates for this feature's basic text. Failed processing must not
append a completed pair; committed replay performs no new model call/reservation
and retains the existing availability checks. Generated /v3/api-docs and
/v3/api-docs.yaml remain the source of truth; no handwritten OpenAPI is maintained.

- Capacity rejection: existing HTTP 503 CHAT_CAPACITY_EXCEEDED, Retry-After: 1.
- Daily budget exhaustion: existing HTTP 429 CHAT_TOKEN_BUDGET_EXCEEDED and
  next-UTC-day retry guidance.
- Invalid structured interpretation/evidence after permitted correction:
  HTTP 502 CHAT_INVALID_MODEL_OUTPUT.
- Local evidence-call/context exhaustion: HTTP 503 CHAT_SEMANTIC_LIMIT_EXCEEDED;
  fail the turn without closing otherwise healthy history/replay.
- Actual dependency/control failure or turn/call timeout: existing HTTP 503
  CHAT_UNAVAILABLE and shared recovery policy. No unchecked success is returned.

ApiExceptionHandler owns RFC 9457 ProblemDetail translation; services and custom
exceptions remain protocol-independent. Existing rate, ownership and busy-turn
outcomes remain part of the inherited contract.

## Delivery Boundaries

Feature 0025 owns candidate selection, structured evidence and minimal template
presentation. Feature 0026 owns substantive answer generation/explanation; 0027
owns the broader user-reviewed retrieval/answer qualification. Basic functional
model qualification and focused correctness checks remain required here.

OOS-0025-001 defers offering/opening a catalog view filtered by found IDs;
OOS-0025-002 assigns substantive explanation to 0026; OOS-0025-003 assigns broad
query-quality evaluation to 0027. For now, show at most five in chat and an
additional-results notice when applicable; see the accepted entries in ADR-0025 and the
[central register](../../docs/out-of-scope-decisions.md).

## Dependencies

- [0023-stone-card-reindexing](../0023-stone-card-reindexing/spec.md).
- [0024-exact-llm-search](../0024-exact-llm-search/spec.md).
- [0017-markdown-document-indexing](../0017-markdown-document-indexing/spec.md).
- [0018-stone-card-embedding](../0018-stone-card-embedding/spec.md).
- [0016-llm-search-conversation-history-and-context](../0016-llm-search-conversation-history-and-context/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Execution Gate

Read the constitution, this spec, acceptance.md, plan.md, tasks.md, ADR-0025 and
relevant local instructions. T0025-001 is first and covers every ticket in the
explicitly requested batch; readiness cannot be inferred from past features.
T0025-002 then qualifies the new structured prompts/usage/context before dependent
application implementation. Stop on failure; do not change models, budgets or
behavior to conceal a qualification blocker.

No new schema, dependency, Kafka contract or frontend implementation is planned.
If implementation demonstrates a necessary change outside the exact allowlists,
stop for an agreed scope/specification update; do not edit existing migrations
or introduce undocumented functionality. No application edits, inference,
downloads, provisioning, tests, imports, commits or pushes have been performed
or authorized merely by preparing this documentation.
