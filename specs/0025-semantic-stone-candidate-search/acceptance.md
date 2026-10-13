# Feature 0025 Acceptance Criteria

Status: AC-0025-001–015 verified through the focused evidence below on 2026-10-13.

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

Initial preparation evidence status was NOT RUN; actual results follow below. Documentation consistency and
whitespace checks are not runtime readiness, model qualification or acceptance.
Append actual selector outcomes and criterion evidence only after authorized
execution; do not replace planned expectations with invented pass claims.


### Basic qualification evidence — 2026-10-13

AC-0025-015: external requested-batch readiness and small test-only prototype
qualification are complete; final production-provider/architecture acceptance
remains pending. Interpretation: shelf-only/mixed/generic cases; source evidence:
partial/type-only/card veto/unknown cases; compatible real vectors and complete
fresh-input bound (8063) passed across the documented changed-format rechecks.
Three controlled exact transport regressions passed. Original failed outputs and
actual usage are retained in tasks/ADR; no broader query-quality claim is made.
AC-0025-001–014 production integration evidence remains NOT RUN.


### Retrieval evidence — T0025-003

StoneSemanticRetrievalTest: five focused tests pass with isolated pinned PostgreSQL
and synthetic vectors. Covers the repository portions of AC-0025-003/004/010/011,
explicit settings/profile failure and internal mapping. Full evidence aggregation,
conversation/HTTP/atomicity/ranking behavior and final acceptance remain pending.
No complete feature acceptance is inferred from this focused result.

### Evidence and integration — T0025-004/005

Seven controlled evidence tests and eight actual isolated PostgreSQL/Redis/native
HTTP integration tests pass. Sixteen exact preparation and three chatbot
regressions pass. Coverage: AC-0025-001/002/005–014 through source validation,
card veto/partial aggregation, ranking/counts/fallback/late refresh, strict state,
no-model replay, local-limit atomicity and dependency closure; generated JSON and
YAML retain public fields and specify semantic limit/error outcomes. The initial
fixture/wiring failures and corrections remain in tasks/ADR. Final production
model/context qualification and architecture review remain pending; these results
do not establish broad model quality or user-reviewed 0027 acceptance.

### Final criterion review — T0025-006

| Criteria | Verified evidence |
| --- | --- |
| AC-0025-001/002 | Final real interpretation fixtures and controlled integration; exact-only/no generic selection, inherited/replaced/reset state and strict Redis/replay behavior. Missing dates/references retain the 16 exact preparation regressions. |
| AC-0025-003/004 | Five isolated PostgreSQL/vector retrieval tests; live hard predicates before caps, inclusive independent source bounds, document-only recall, explicit profile/settings failures. Real compatible vectors and single-query refresh/cache integration pass. |
| AC-0025-005/006/007 | Seven controlled evidence tests and final real mapped-card/source fixture; strict pair/quote/identity validation, card veto, partial/type-only and UNKNOWN evidence. Required null-biography omission and one precise correction are verified. |
| AC-0025-008/009/010 | Controlled live integration: card-first/score/ID order, bounded count/overflow/five links, partial wishes, no filling semantic results, labeled ordered exact alternatives only at semantic zero. |
| AC-0025-011 | Controlled current-pool reservation/deletion, whole-pool refill, one refresh with unchanged query vector, no initial-zero/evidence-rejection/partial-survival refresh or second loop. |
| AC-0025-012 | Controlled context/output/complete segmentation/cache/cap/repair/actual-exit accounting checks; real final guarded calls and usage, full current-input bound 8083, mandatory-state overflow before reservation. |
| AC-0025-013/014 | Real isolated Redis atomic pair/replay/healthy local limit/closed dependency behavior; three chatbot regressions; native HTTP 502/503/429 codes and retry/cookie headers. Generated JSON and YAML retain success/history fields and documented semantics. |
| AC-0025-015 | External whole-batch readiness, recorded basic prototype and final changed-provider gates, two focused architecture tests and no dependency/schema/ingestion/Kafka/UI changes. |

All criteria PASS at this agreed focused verification level. This is basic
functional/model/architecture acceptance, not a claim of broad model quality,
optimal thresholds or 0027's required human-reviewed fixed-set acceptance. Failed
prototype/production attempts, diagnosis, corrections and actual token charges
remain recorded in tasks/ADR; expected semantics were never relaxed.
