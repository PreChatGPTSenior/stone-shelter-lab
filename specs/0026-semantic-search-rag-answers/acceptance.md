# Feature 0026 Acceptance Criteria

Status: Criteria prepared. Implementation and verification have not been executed.

Requirements: [spec.md](spec.md). Detailed discussion:
[ADR-0026](../../docs/adr/0026-semantic-search-rag-answers.md).
Permitted execution/verification: [tasks.md](tasks.md).

## Functional and Failure Criteria

- **AC-0026-001 — Routes and mixed requests.** Selection, knowledge-only and
  mixed requests route through one bounded interpretation. Knowledge-only never
  starts arbitrary selection; explanatory care topics do not become selection
  conditions. Clarification/unsupported and missing-year/reference behavior
  remains appropriate to the requested operation.
- **AC-0026-002 — Explanation format and selection invariants.** Normal selection
  has a short introduction and text for every presented stone, in the selected
  order and at most five. No new/reordered IDs or invented full-wish matches.
  Partial/alternative and additional-result labels remain truthful.
- **AC-0026-003 — Source precedence and insufficiency.** Card facts override
  contradictory encyclopedia/general knowledge; type knowledge does not certify
  individual properties. General knowledge may supplement nonconflicting type
  answers. Insufficient information yields a brief source-free answer, distinct
  from a runtime failure.
- **AC-0026-004 — No visible provenance.** Normal, repaired, template and replay/
  history outputs expose no provenance fields/citations or prose identifying
  cards/documents/sections as information sources. Internal evidence remains
  available for checking. Allowed catalog links contain real selected/referenced
  specimen IDs/names and do not function as document-source attribution.
- **AC-0026-005 — Retrieval reuse and bounds.** Reuse relevant 0025 evidence;
  a new topic may retrieve up to the configured initial 10 passages at inclusive
  0.5. No more than two compatible query embeddings per turn or per-stone/retry
  embeddings. Source/profile/DB failure is not empty evidence.
- **AC-0026-006 — Generated-text checking.** Strict schema/ID/order checks reject
  invalid drafts; structured semantic checking catches controlled unsupported
  individual assertions, contradicted wishes and source disclosure fixtures.
  One correction consumes the same caps; unchecked prose never becomes success.
  General type claims and individual claims are distinguished by the checker.
- **AC-0026-007 — Counters, context and time.** Initial 8192 context includes
  1024 generation output; interpretation/checking retain 512. Four-generation,
  20-checking, two-embedding, one-refresh and interpretation limits are shared
  per turn with no resets. Context/output packing preserves required input,
  no silent source truncation, 50-second calls and a 120-second total deadline,
  including the five-second finalization margin. Invalid settings fail explicitly.
- **AC-0026-008 — Admission/accounting and cancellation.** All calls use existing
  CUSTOMER admission/reservation/settlement. Failed/corrected calls are charged,
  unknown usage remains reserved and actual-exit permit lifetime is preserved.
  No hidden retry, long DB transaction during inference or admission queue appears.
- **AC-0026-009 — Selection template fallback.** Checked candidates survive
  generation/check failure, admission/budget/local limits, timeout/provider outage
  or a still-invalid correction via a clean backend template. Failed prose is
  absent from output/history. Template retains actual ordered links, partial/
  exact-alternative labels and overflow, without requiring polished explanations.
- **AC-0026-010 — Narrow current-turn availability exception.** A generation-only
  Ollama failure may complete that already admitted/checked selection template
  with healthy persistence/control/fencing before expiry. Subsequent send/history/
  replay remain closed until verified recovery. Initial/selection/evidence/
  ledger/Redis/ownership/uncertain-commit failures cannot use the exception.
- **AC-0026-011 — Knowledge failures and empty selection.** Knowledge generation/
  checking failure returns the specified error without a completed pair, even
  with a convenience link. A no-candidate/no-alternative selection uses its short
  template and makes no generation call or condition-relaxation suggestion.
- **AC-0026-012 — Follow-ups and link identity.** Explanation preserves effective
  search conditions. Referenced-specimen knowledge has one live link; type knowledge
  has none. A later ordinal resolves the last actual selection, bypassing knowledge
  convenience links. Legacy turns remain usable; unavailable references clarify.
- **AC-0026-013 — Late eligibility and refresh.** After generation, partial loss
  removes affected prose/links and updates framing without refill. Complete loss
  permits only the unspent 0025 turn-wide refresh, with vector/counter/deadline
  reuse and new-answer checking. Already spent, initial-zero and repeated-zero
  cases cannot loop or preserve stale references.
- **AC-0026-014 — Persistence, replay and HTTP.** Successful generated/template
  answers append exactly one completed pair; errors append none. Replay adds
  no inference/reservation. Generated JSON/YAML OpenAPI preserves current routes,
  success shapes and source-free history while documenting 200/502/503/429 outcomes.
  Internal route/template/evidence metadata does not leak through public DTOs.
- **AC-0026-015 — Qualification and architecture.** Fresh readiness covers the
  requested batch; required 0025 contracts exist before integration. Basic real
  model schema/usage/packing qualification and final provider recheck pass before
  acceptance. Responsibility, mapper, DTO, protocol/JPA and record constraints
  hold; no unplanned dependency/schema/ingestion/Kafka/frontend work appears.

## Permitted Verification and Actual Evidence

Use only the named selectors in the requested ticket with controlled JAVA_HOME
and required endpoints after T0026-001. Database/history fixtures use isolated
pinned Testcontainers and clean up their own data. Controlled providers cover
failure/cancellation/concurrency cases; real selected-model calls are confined
to T0026-002 and the explicitly named T0026-006 production recheck.

All AC-0026-001–015: **NOT RUN**. Documentation consistency/whitespace checks
do not establish runtime readiness, model quality or implementation acceptance.
Append factual evidence only after authorized execution; broader user-reviewed
query/answer qualification is feature 0027, not an implicit test expansion here.
