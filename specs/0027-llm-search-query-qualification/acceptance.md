# Feature 0027 Acceptance Criteria

Status: Approved fixture/harness implemented and verified; original baseline
collection is in progress with preserved failures/outages. Final qualification is
NOT PASSED. Current assessment is autonomous; user review is deferred; approved oracles below
remain unchanged.

Requirements: [spec.md](spec.md). Discussion record:
[ADR-0027](../../docs/adr/0027-llm-search-query-qualification.md).

## Approved Fixed Fixture

Use an isolated catalog/document/history environment. These are synthetic test
statements, not edits to the user's real cards or encyclopedia. Use actual catalog
enums, fields and constraints; no structured color field is added. Red information
below appears only in biography or type-level passages.

Use 2026-10-13T12:00:00Z as the fixture calendar reference for date interpretation.
Do not freeze model/deadline clocks or charge real usage to that historical day:
timeouts use advancing runtime time and accounting uses the actual current UTC day.
Logical fixture keys map to actual
generated database IDs; seed in the listed order so ID comparisons are stable.
Queries never use logical keys as new public ID/name filters. Each scenario gets
its own initial data/history; mutations are local to that scenario and cleaned up.
Diagnostic repetitions start from the same initial fixture, not mutated prior state.

### Cards: 15 rows

Null biography means no supplied biography content, not an inferred description.
All names/biographies are literal approved input values.

| Key | Name | Type | Size | Status | Admission date, UTC | Biography |
| --- | --- | --- | --- | --- | --- | --- |
| B01 | Basalt 01 | BASALT | SMALL | AVAILABLE | 2026-10-01T00:00:00Z | null |
| B02 | Basalt 02 | BASALT | SMALL | AVAILABLE | 2026-10-02T12:00:00Z | null |
| B03 | Basalt 03 | BASALT | SMALL | AVAILABLE | 2026-10-03T12:00:00Z | null |
| B04 | Basalt 04 | BASALT | SMALL | AVAILABLE | 2026-10-04T12:00:00Z | null |
| B05 | Basalt 05 | BASALT | SMALL | AVAILABLE | 2026-10-06T12:00:00Z | null |
| B06 | Basalt 06 | BASALT | SMALL | AVAILABLE | 2026-10-06T12:00:00Z | null |
| B07 | Basalt 07 | BASALT | LARGE | AVAILABLE | 2026-10-07T12:00:00Z | null |
| B08 | Basalt 08 | BASALT | SMALL | RESERVED | 2026-10-08T12:00:00Z | null |
| B09 | Basalt 09 | BASALT | SMALL | ADOPTED | 2026-10-09T12:00:00Z | null |
| B10 | Basalt 10 | BASALT | SMALL | AVAILABLE | 2026-10-10T12:00:00Z | This specimen must not be placed on a display shelf. |
| G01 | Granite 01 | GRANITE | SMALL | AVAILABLE | 2026-10-11T12:00:00Z | This specimen has visible red markings and is suitable for a display shelf. |
| G02 | Granite 02 | GRANITE | MEDIUM | AVAILABLE | 2026-10-12T12:00:00Z | This specimen has visible red markings and is suitable for a display shelf. |
| G03 | Granite 03 | GRANITE | SMALL | AVAILABLE | 2026-10-13T10:00:00Z | This specimen has visible red markings and is suitable for a garden. It must not be placed on a display shelf. |
| P01 | Pumice 01 | PUMICE | LARGE | AVAILABLE | 2026-10-13T11:00:00Z | null |
| O01 | Obsidian 01 | OBSIDIAN | SMALL | AVAILABLE | 2026-10-13T11:30:00Z | This specimen must not be placed on a display shelf. |

### Encyclopedia: seven passages

The isolated published document uses the existing document identity and compatible
profile. Logical passage keys below map to ordered type-scoped fragments with
source version/heading provenance. Embed approved heading context and exact text
through existing capabilities; do not change production source objects or ingestion.

| Key / order | Stone type | Section | Exact passage text |
| --- | --- | --- | --- |
| E01 / 1 | BASALT | Display Placement | Basalt stones are suitable for a display shelf. |
| E02 / 2 | BASALT | Care | Brush basalt with a dry soft brush once a week. |
| E03 / 3 | GRANITE | Appearance and Display Placement | Granite stones can have red markings and are suitable for display shelves. |
| E04 / 4 | GRANITE | Garden Placement | Granite stones are suitable for a garden. |
| E05 / 5 | GRANITE | Care | Wipe granite with a slightly damp soft cloth once a week. |
| E06 / 6 | PUMICE | Garden Placement | Pumice stones are suitable for a garden. |
| E07 / 7 | OBSIDIAN | Display Placement | Obsidian stones are suitable for a display shelf. |

No fixture source supplies blue/turquoise information or establishes the exact
color of B01–B10. A type's possible color does not establish an individual color.
Known shelf contradictions B10/G03/O01 are candidate-wide vetoes only when shelf
suitability is an active wish; unrelated wishes must not acquire an invented veto.

## Approved Baseline: 20 Scenarios, 28 User Turns

All shown queries are literal English inputs. Separate numbered turns within a
scenario share its conversation. Physical IDs in expected outcomes are resolved
from fixture keys. Conditional mutations/hooks are test-owned, not production
control endpoints. No fixed prose wording is required.

Quality labels: **R** = semantic candidate quality, **A** = ordinary generated
answer quality, **H** = functional/hard checks only. Hard rules independently
apply to every actual turn, including setup and controlled scenarios.

| ID | User query / sequence | Expected outcome | Quality cohort |
| --- | --- | --- | --- |
| Q027-001 | Show available basalt stones. | Exact count 8; presented order B10, B07, B05, B06, B04. Keep exact overflow behavior and per-stone explanation. | A |
| Q027-002 | Show small available basalt stones. | Exact count 7; order B10, B05, B06, B04, B03. Exclude B07/B08/B09. | A |
| Q027-003 | Show small basalt stones admitted on 6 October 2026. | B05 then B06 only; inclusive UTC day and ID tie-break. Explain both. | A |
| Q027-004 | Show reserved or adopted basalt stones. | No returned stones: requested statuses cannot defeat mandatory AVAILABLE. No generation or retrieval refresh. | H |
| Q027-005 | Show all available stones. | Clarification with at least two specific questions; no unfiltered catalog query or generated selection. | H |
| Q027-006 | Show basalt stones admitted on 6 October. | Ask for the year; no invented year or catalog selection. | H |
| Q027-007 | Find a stone for a display shelf. | Semantic-only selection. Permitted IDs are B01–B07, G01 and G02; at least one G01/G02, with card-supported members before type-only ones. B10/G03/O01 excluded. | R, A |
| Q027-008 | I need a specimen to decorate a bookshelf. | Same shelf intent/permitted set as Q027-007, without requiring identical embedding scores or wording. At least one G01/G02; no known contradictory specimen. | R, A |
| Q027-009 | Find a small basalt for a display shelf. | Type-supported B01–B06; present B01–B05 in ID order and indicate more. Card silence permits E01; B10 vetoed. Exact constraints apply before retrieval cap. | R, A |
| Q027-010 | Find a small basalt for a display shelf, preferably blue. | Partial semantic B01–B06; show B01–B05 and more notice, identifying blue as unfulfilled. Shelf support is not dropped because color is unknown. | R, A |
| Q027-011 | Find a red stone for a display shelf. | Include G01/G02 before any type-only partial matches. B01–B07 may be partials with red unfulfilled; G03/B10/O01 are excluded despite other support. Every displayed ID must belong to this allowed set. | R, A |
| Q027-012 | Find a small blue basalt. | No supported blue semantic wish; exact alternatives only, with bounded count 7 and order B10, B05, B06, B04, B03. Explain that suitable semantic candidates were not found; do not claim blue. | R, A |
| Q027-013 | Find a turquoise stone. | No supported semantic match and no exact narrowing: short no-candidates response, no arbitrary substitutes, generation or refresh. | R |
| Q027-014 | 1. Find a small basalt for a display shelf. 2. Now only large ones. | First outcome as Q027-009; second retains BASALT/shelf and replaces SMALL with LARGE, selecting B07 only. | R, A |
| Q027-015 | 1. Find a small basalt for a display shelf. 2. Start a new search for a stone for a garden. | Clear prior type/size/shelf. G03 first through card support; G01/G02/P01 allowed type matches, including LARGE P01. No inherited BASALT/SMALL filter. | R, A |
| Q027-016 | 1. Show small basalt stones admitted on 6 October 2026. 2. Show the second stone. | Between turns mark B06 RESERVED. Second turn clarifies unavailable reference, does not substitute B05 or link B06. | H |
| Q027-017 | Show small available basalt stones. | Controlled valid generation reaches final check; after generation reserve B10 and delete B05. Keep generated survivors B06, B04, B03 only, remove stale prose/links and rebuild framing. No refill or repeated search. | H |
| Q027-018 | Show small basalt stones admitted on 6 October 2026. | Controlled valid generation reaches final check; then reserve B05 and B06. Exactly one retrieval refresh returns zero; short absence text, no second refresh or new explanation loop. | H |
| Q027-019 | 1. How should basalt be cared for? Tell me where you got this information. 2. Find small basalt stones admitted on 6 October 2026 and tell me how to care for them. 3. How do I care for the second stone? 4. What exact color is the first stone? 5. Now only large ones. | 1: weekly dry-soft-brush care, no specimen link/attribution. 2: mixed selection B05/B06 with care for both. 3: B06 care and its one link. 4: B05 link, exact color unconfirmed; single B06 link did not replace selection references. 5: retain type/date, replace size; zero exact results and no generation. | A |
| Q027-020 | 1. Show small basalt stones admitted on 6 October 2026. 2. How should basalt be cared for? | Inject final-generation-only provider failure in each independent phase. Selection completes a clean B05/B06 template with healthy controls; later operations stay closed until explicit recovery. After recovery, knowledge-only failure returns an error with no completed pair. No invalid/generated failure text is stored. | H |

Q027-017/018 use controlled valid drafts/checking to deterministically reach the
race boundary; they do not count as real-generation quality successes. Q027-020
uses an explicit generation-failure injection and is likewise excluded from the
ordinary answer-quality cohort. Natural-query scenarios use installed real models.
Failure hooks are isolated test instrumentation, never added production routes.

The expected semantic sets are quality oracles, not exhaustive-count promises.
A relevance miss may fail R while retaining valid hard filtering. Independently,
any returned wrong-size/status/contradicted ID or false fulfilled-property claim
fails the applicable hard rule; it cannot be excused by the 80% target.

## Approved Scoring and Review

- Hard gate: every required scenario/turn exercised, with 100% required hard-rule
  compliance. This includes exact/date/reference/availability/contradiction,
  truthful wishes/property claims, source visibility, ordering and bounded retries.
- Semantic-quality cohort: Q027-007–015, nine scenarios. Pass requires the stated
  route/candidate outcome, including expected partial/zero/fallback behavior;
  multi-turn cases require every specified turn. At least ceil(0.8 x 9) = **8/9**.
- Answer-quality cohort: Q027-001–003, Q027-007–012, Q027-014–015 and Q027-019,
  twelve scenarios. At least ceil(0.8 x 12) = **10/12**, supported by independent
  automatic checks and agent rubric assessment; user review is deferred.
  All expected generated replies in a multi-turn scenario must satisfy the rubric.
- For each normal generated reply, review: correct permitted content/source
  precedence; relevant answer; no invented individual properties; clear unmatched
  wishes/alternative labels; short introduction/per-stone coverage where required;
  no user-visible provenance. Exact string equality is not required.
- A token-quota-interrupted attempt is INCOMPLETE_BUDGET_EXHAUSTED, including
  a saved template whose private cause is quota exhaustion; retain actual output/
  accounting without treating the interrupted answer as a quality failure/success.
- A valid template for a non-quota failure in an ordinary A scenario fails that scenario's A outcome;
  it may still pass hard/candidate checks. A skipped expected answer because
  selection failed its quality oracle is not an explanation-quality pass.
- Preserve one baseline attempt and every approved diagnostic repetition.
  Diagnostics cannot replace baseline scores or silently modify the approved gold.
- A control outage, exhausted real-model budget, unexercised required hook or
  incomplete run is recorded as incomplete/blocked, never a reduced-denominator
  pass. Explain the actual cause and preserve already collected evidence.
- No latency/throughput/CPU/memory measurements. Existing deadlines and explicit
  timeout/capacity/budget outcomes remain correctness checks.

## Approval and Execution Evidence

Fixture rows, scenario oracles and concrete cohort assignment: **APPROVED
(Q0027-009, D0027-010)**. Design approval is not an evaluation result.

Environment readiness and fixture transcription: **PASS**. Compatible embeddings
were prepared under actual guarded accounting and retained for unchanged reuse.
Original baseline: **IN PROGRESS**, with earlier interruptions preserved. Approved
profile-correction diagnostic: **FAILED** at the unchanged call deadline. Actual
answer-quality user review: **DEFERRED_OUT_OF_SCOPE**; autonomous assessment
records reviewer identity and independent reasons. See qualification-results.json,
qualification-report.md and ADR-0027 for exact stages and limitations.

## Execution Acceptance Criteria

The approved fixture/scenario/scoring sections below remain the original oracles.
AC-0027-001/002 pass the recorded readiness and fixture checks. Complete-scenario
criteria remain incomplete; Q027-008 already fails the unasked-filter hard rule.
No criterion passes solely from documentation or a favorable repetition.

| ID | Required execution evidence |
| --- | --- |
| AC-0027-001 | A fresh readiness report covers every explicitly requested ticket ID, effective tool/model profiles, required service endpoints/configuration and isolation prerequisites; blocked prerequisites prevent the affected batch from starting. |
| AC-0027-002 | Executable fixture data faithfully transcribes the approved 15 cards and seven passages using existing catalog enums/fields and compatible real embeddings, with generated-ID mapping and stable seed order. |
| AC-0027-003 | Every scenario starts with its specified catalog/history state; controlled mutations and cleanup stay isolated. Actual model accounting is preserved; date interpretation uses the fixture calendar while deadlines advance and usage is charged to the actual UTC day. |
| AC-0027-004 | All 20 scenarios and their 28 user turns have one recorded original baseline attempt, literal inputs, resolved expected IDs/outcomes and effective model/configuration metadata; natural-query cases use installed real models. |
| AC-0027-005 | All baseline turns meet the 100% hard gate, independently of semantic/answer scoring, including exact/date/AVAILABLE constraints, contradictions, references, truthful property claims, hidden provenance and bounded retries. |
| AC-0027-006 | Automatic candidate checks and retained evidence support at least 8/9 successful semantic-quality scenarios under the approved per-scenario oracles. |
| AC-0027-007 | Independent automatic checks and recorded agent assessment support at least 10/12 successful ordinary generated-answer scenarios under the approved rubric; all required replies in each multi-turn case are assessed without requiring user verdicts. |
| AC-0027-008 | Ordinary expected-generation templates and skipped expected answers are not counted as successful answer quality; controlled failure/template cases are assessed separately without inflating either quality cohort. |
| AC-0027-009 | Controlled test-only race/failure instrumentation exercises Q027-017/018/020 deterministically, with preserved hard checks, no production control endpoint and no claim of ordinary real-generation success from controlled drafts. |
| AC-0027-010 | Each diagnostic repetition identifies a failed/ambiguous baseline and an explicit instability hypothesis, begins from the original fixture and stays within two additional attempts per scenario; all attempts remain visible and original scores are unchanged. |
| AC-0027-011 | Outages, usage/capacity/deadline failures and unexercised scenarios are classified with actual causes; incomplete evidence cannot pass by reducing denominators or overriding existing controls. |
| AC-0027-012 | Poor results are localized using interpretation/retrieval/evidence/generation/reference/control evidence before proposing corrections. Production fixes, changed thresholds or alternative-threshold trials require a separately agreed concrete scope. |
| AC-0027-013 | Machine-readable evidence and a readable report retain fixture/profile/configuration identity, baseline and diagnostic outcomes, autonomous assessment and deferred human-review status, limitations and honest final status, without secret values or performance benchmarks. |
| AC-0027-014 | Production application source, existing HTTP/Kafka contracts, schema, dependency versions and frontend remain unchanged by qualification; verification uses only the selected ticket's permitted files and focused selector. |
| AC-0027-015 | The final report distinguishes current autonomous fixed-set qualification from deferred user review and personal-set application acceptance; neither design approval nor fixed-set success claims that later acceptance is complete. |

## Deferred Personal Manual Acceptance

[OOS-0027-003](../../docs/adr/0027-llm-search-query-qualification.md) records the
user's separate personal scenario set, to be collected during development and
used for manual application acceptance after implementation. It is not supplied
or required now. It does not replace this approved fixed set; current answer assessment remains
autonomous, while user review of the fixed replies is separately deferred under
OOS-0027-005. No later acceptance result or scenario content is assumed.


## Current Execution Evidence — 2026-10-14 Local Date

AC-0027-001/002: PASS for recorded readiness and faithful fixture/real-vector
preparation; current remaining inference readiness is BLOCKED by actual budget.
AC-0027-003/008/010–013/015: retained isolation/accounting/classification/limited
diagnostic/reporting evidence, with explicit missing original payload and pending
review; no final overall pass. AC-0027-004 INCOMPLETE (27 submitted, 26 fully
retained of 28). AC-0027-005 FAIL through observed original hard violations;
AC-0027-006 FAIL (2/9). AC-0027-007 FAIL (0/12 autonomous assessment); every A scenario already
has an unmet required turn. AC-0027-009 PARTIAL: Q027-017/018 pass controlled
races; Q027-020 independent failure phase is incomplete/unproven.
AC-0027-014: no production change by this qualification harness; the separately
agreed full-profile correction was delivered in 0026, with original failures
preserved. No new public contract/schema/dependency/frontend change under 0027.

Test-only preservation/oracle follow-up: two named controlled checks PASS, no
model calls or original repeat. Previous false-positive WRONG_SIZE:B07 on the
already approved LARGE turn is retained separately; actual date/reference and
failed scenario results remain. See qualification-results.json,
qualification-report.md and ADR-0027 for exact evidence, not a success claim.


## Configurable Test Quota Amendment — 2026-10-14

| ID | Required verification |
| --- | --- |
| AC-0027-016 | Qualification defaults to 200000 daily test tokens, accepts positive MODEL_DAILY_TOKEN_BUDGET overrides, rejects invalid limits and reports the effective supplied value; the application default remains 100000. |
| AC-0027-017 | Direct/wrapped daily token exhaustion and quota-caused saved selection templates are retained as INCOMPLETE_BUDGET_EXHAUSTED; collection stops, answer quality remains ungraded, original accounting/independent failures/fixed denominators are preserved, and controlled quota tests exercise this without live inference. |

The explicit user-approved quota change supersedes the earlier fixed 100000 test
value for future execution only. Historical attempts keep their original limits.
It does not approve pending production corrections, threshold changes, human
quality verdicts, repeated original queries or mock form submission.


AC-0027-016/017 execution: PASS, two controlled named checks, zero failures/errors,
no inference. Configurable default 200000/positive override/invalid rejection and
unchanged production default 100000 verified. Wrapped direct rejection and a real
RAG service's quota-caused TEMPLATE retain delivery facts while marking answer
verification INCOMPLETE_BUDGET_EXHAUSTED; independent failures remain visible.
The preserved 68958 charge leaves 131042 under the approved quota; the old
budget-readiness blocker is resolved. Original missing/unattempted phase evidence
still prevent final qualification; user review is separately deferred and is not
a current blocker.


## Current Assessment Ownership Amendment — D0027-013

Human assessment is outside this phase (OOS-0027-005); its absence is no blocker
for current 0027 execution or acceptance. Automatic/agent assessment retains the
existing rubric/targets/cohorts and explicit PASS/FAIL/INCOMPLETE/UNASSESSED
status with reasons. No human approval or later manual acceptance is fabricated;
production grounding alone cannot certify independent answer quality. Older
PENDING-human notes below/above are historical and superseded by this amendment.


D0027-013 current assessment evidence: T0027-004 completed autonomously, original
A=0/12 because every A scenario has a failed required turn; original R=2/9.
No human verdict or reduced denominator. Individual generated Q027-019 turn 1
passes the agent rubric; turns 3/5 fail with independent reasons recorded in JSON.
AC-0027-007 therefore FAIL, not human-review pending; other original completeness/
hard failures remain. Writer policy check PASS (one test, no inference).
