# Feature 0025 Acceptance Criteria

Status: Criteria prepared; not executed or accepted as implemented.

Requirements: [spec.md](spec.md). Discussion: [ADR-0025](../../docs/adr/0025-semantic-stone-candidate-search.md).
Execution boundaries and selectors: [tasks.md](tasks.md).

## Functional and Failure Criteria

- **AC-0025-001 — Interpretation and narrowing.** A meaningful semantic-only
  wish starts bounded semantic search. Generic/availability-only requests clarify
  without searching. Mixed requests retain all exact constraints; exact-only
  turns perform no query embedding/evidence classification and retain 0024 behavior.
- **AC-0025-002 — Conversation state.** Follow-ups retain unchanged exact/wish
  conditions, replace explicit changes and clear both on new search. Stored
  effective state survives model-history trimming; old history remains readable.
  Missing years and invalid ordinal/page references still clarify.
- **AC-0025-003 — Prefiltering.** Fixtures with globally higher-scoring wrong-type,
  wrong-size, unavailable/deleted cards cannot fill the retrieval cap and hide
  eligible exact-filter matches. Date boundaries match 0024 UTC rules.
- **AC-0025-004 — Source thresholds and bounds.** Inclusive configured thresholds
  precede source limits; document and stone caps are independent. Document-only
  qualification does not require passing the card threshold. Compatible query
  embedding is computed once and reused across sources/refresh. Invalid settings
  and incompatible profiles fail explicitly.
- **AC-0025-005 — Structured evidence.** Supplied card/type/shared-source scopes
  constrain decisions. Unknown/duplicate/omitted candidate or wish identifiers,
  invented source references/quotes and malformed JSON cannot become accepted
  support. A high score without supported wishes does not qualify by itself.
- **AC-0025-006 — Card precedence.** A silent biography permits applicable type
  evidence. An explicit current-card contradiction excludes the whole candidate,
  including from exact alternatives, even when another wish is supported and
  the encyclopedia is favorable. Stale vector text cannot override that card.
- **AC-0025-007 — Missing and partial evidence.** A noncontradictory candidate
  supported for at least one wish may be returned with matched/unmatched wishes
  identified. No source support for any wish produces the general
  suitable-candidates-not-found outcome, never invented color/use properties.
- **AC-0025-008 — Ranking.** Card-supported candidates precede type-only ones
  even when document scores are higher. Each group uses its specified descending
  source score and ID ascending ties; missing card scores follow scored members
  in that group. Deduplicate candidates supported by several passages.
- **AC-0025-009 — Counts and presentation.** Defaults permit up to 10 internal
  candidates and at most five shown. A two-candidate semantic set stays two.
  Only final eligible/evidence-checked candidates count; six establish hasMore
  and the additional-results notice. No exhaustive semantic count, automatic
  overflow refinement, extra-results chat UI or catalog-ID navigation is produced.
- **AC-0025-010 — Exact alternatives.** Only an empty qualifying semantic set
  with usable exact narrowing may return exact alternatives, ordered newest
  admission then ID. Preserve the contradiction veto; label alternatives and
  unfulfilled semantic wishes. Semantic-only zero matches return no arbitrary
  catalog selection. Bounded fallback counts are distinct from pure exact totals.
- **AC-0025-011 — Late availability and refresh.** Remove deleted/non-AVAILABLE
  stones before links/counts, replace from the existing ordered pool and show
  fewer if any survive. Complete disappearance of a previously nonempty pool
  permits exactly one refresh with the same interpretation/vector. Initial zero,
  evidence rejection and partial survival permit none; a second disappearance
  cannot loop. Calls/deadline retain their original turn-wide accounting.
- **AC-0025-012 — Batching and resource control.** Every required evidence segment
  is checked before support/unknown aggregation. Context/output bounds include
  system/schema/message overhead and output reservation. No source truncation,
  unchecked success or hidden retry bypasses 8192/512, the 20-call initial cap,
  50-second call or 120-second turn limits. One permitted batch repair consumes
  the same counter. Admission/reservation/settlement charge all attempts and
  preserve unknown usage; no model permit or DB transaction is held while idle.
- **AC-0025-013 — Failures and atomicity.** Verify the specified 429/503/502
  ProblemDetail codes and retry guidance. Local limits/admission rejection do
  not close healthy history/replay; actual dependency/control failure retains
  shared fail-closed recovery. No failed attempt appends a completed turn pair;
  committed replay performs no new inference/embedding/reservation.
- **AC-0025-014 — HTTP/history contract.** Generated JSON and YAML OpenAPI keep
  current routes/success DTO fields and describe semantic/fallback/limit outcomes.
  Stored evidence/criteria do not appear as new HTTP success/history fields.
  Public references use real catalog IDs/names and the stored displayed order.
- **AC-0025-015 — Qualification and architecture.** Fresh requested-batch readiness
  precedes basic real-model prompt/usage qualification and production integration.
  Focused architecture checks enforce DTO/mapping, responsibility packages,
  service/protocol/JPA boundaries and at-most-four-field records. No unplanned
  dependency, schema, ingestion, Kafka or UI change is included.

## Permitted Verification and Evidence

Run only the exact selectors in the requested ticket, with its controlled
environment after T0025-001. PostgreSQL checks use isolated pinned Testcontainers
and test-owned data; nonqualification provider cases use controlled doubles.
Real installed-model calls are confined to T0025-002 and its explicitly named
final recheck in T0025-006. These are basic functional checks, not 0027's broad
query-quality campaign or performance measurements.

Evidence status: **NOT RUN** for every criterion. Documentation consistency and
whitespace checks are not runtime readiness, model qualification or acceptance.
Append actual selector outcomes and criterion evidence only after authorized
execution; do not replace planned expectations with invented pass claims.
