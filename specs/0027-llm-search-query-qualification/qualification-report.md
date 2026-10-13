# Feature 0027 Original Qualification Evidence

Status: INCOMPLETE_CONTROL_OUTAGE. Human review: PENDING.

Run: ad97a99b-03bf-4a90-95dd-0cbaa87fe080; fixture: baa9c12c25f92d27c65a1cb8d6d2738322091988f1e3f1eb7afb83e343ec3024.
Original semantic outcomes: 0/9; answer denominator: 12; required turns: 28.
No favorable repetition replaces the original result. No performance measurement.

## Q027-001

User: Show available basalt stones.

Outcome: ERROR; StoneChatUnavailableException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Human review rubric

Review permitted content and card precedence, relevance, individual-property truthfulness, unmatched/alternative labels, brief introduction and per-stone coverage, and no visible provenance. Actual verdicts and reasons are pending; templates are original answer-quality failures. Free-text hard truthfulness also requires review rather than accepting the production checker alone.

## Blocking Profile Correction Proposed

Original Q027-001 failure and 24 reservations / 974 charged tokens are retained.
The real embedding producer appends the model digest to its base profile; the
semantic repository only accepts the bare base. Fixture document metadata also
needs the actual complete profile. This is a contract mismatch, not threshold
quality evidence. No production correction or baseline rerun was made.

Proposed: validate the existing full profile, keep exact source/query compatibility
and all candidate oracles, align fixture metadata, add safe cause/stage capture and
run focused compatibility checks. Model, threshold, limits, original scores and
actual ledger stay unchanged. Details: ADR-0027, T0027-003 correction proposal.
Human review is pending; this incomplete original attempt generated no reply.

# Approved Correction Continuation (Original Failure Retained)

# Feature 0027 Original Qualification Evidence

Status: INCOMPLETE_CORRECTION_CONTROL_OUTAGE. Human review: PENDING.

Run: e3c47e1a-3a73-4189-be87-b9e8b7b4fbec; fixture: baa9c12c25f92d27c65a1cb8d6d2738322091988f1e3f1eb7afb83e343ec3024.
Original semantic outcomes: 0/9; answer denominator: 12; required turns: 28.
No favorable repetition replaces the original result. No performance measurement.

## Q027-001

User: Show available basalt stones.

Outcome: ERROR; StoneChatUnavailableException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Human review rubric

Review permitted content and card precedence, relevance, individual-property truthfulness, unmatched/alternative labels, brief introduction and per-stone coverage, and no visible provenance. Actual verdicts and reasons are pending; templates are original answer-quality failures. Free-text hard truthfulness also requires review rather than accepting the production checker alone.

# Approved Correction Continuation (Original Failure Retained)

# Feature 0027 Original Qualification Evidence

Status: INCOMPLETE_UNATTEMPTED_CASE_CONTINUATION_OUTAGE. Human review: PENDING.

Run: 498ffaf2-553b-4668-bd70-7e63083e18c2; fixture: baa9c12c25f92d27c65a1cb8d6d2738322091988f1e3f1eb7afb83e343ec3024.
Original semantic outcomes: 0/9; answer denominator: 12; required turns: 28.
No favorable repetition replaces the original result. No performance measurement.

## Q027-001

User: Show available basalt stones.

Outcome: ERROR; StoneChatUnavailableException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Q027-002

User: Show small available basalt stones.

Assistant:

Found 7 matching available stones. More candidates were found.

Links: [B10, B05, B06, B04, B03].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Human review rubric

Review permitted content and card precedence, relevance, individual-property truthfulness, unmatched/alternative labels, brief introduction and per-stone coverage, and no visible provenance. Actual verdicts and reasons are pending; templates are original answer-quality failures. Free-text hard truthfulness also requires review rather than accepting the production checker alone.

## Real Profile SQL Stage

PASS: restored actual digest-qualified card/document vectors accept an unchanged real E01 query and return its source at cosine similarity above 0.999. No new inference, cohort score change or ledger deletion.

# Approved Correction Continuation (Original Failure Retained)

# Feature 0027 Original Qualification Evidence

Status: INCOMPLETE_UNATTEMPTED_CASE_CONTINUATION_OUTAGE. Human review: PENDING.

Run: 835a2da2-6552-49a5-ac15-785f61e797fa; fixture: baa9c12c25f92d27c65a1cb8d6d2738322091988f1e3f1eb7afb83e343ec3024.
Original semantic outcomes: 0/9; answer denominator: 12; required turns: 28.
No favorable repetition replaces the original result. No performance measurement.

## Q027-001

User: Show available basalt stones.

Outcome: ERROR; StoneChatUnavailableException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Q027-002

User: Show small available basalt stones.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-003

User: Show small basalt stones admitted on 6 October 2026.

Assistant:

Found 2 matching available stones.

Links: [B05, B06].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Human review rubric

Review permitted content and card precedence, relevance, individual-property truthfulness, unmatched/alternative labels, brief introduction and per-stone coverage, and no visible provenance. Actual verdicts and reasons are pending; templates are original answer-quality failures. Free-text hard truthfulness also requires review rather than accepting the production checker alone.

# Approved Correction Continuation (Original Failure Retained)

# Feature 0027 Original Qualification Evidence

Status: INCOMPLETE_UNATTEMPTED_CASE_CONTINUATION_OUTAGE. Human review: PENDING.

Run: 0cf1a818-eb2c-45f6-89f2-0dec869142c5; fixture: baa9c12c25f92d27c65a1cb8d6d2738322091988f1e3f1eb7afb83e343ec3024.
Original semantic outcomes: 0/9; answer denominator: 12; required turns: 28.
No favorable repetition replaces the original result. No performance measurement.

## Q027-001

User: Show available basalt stones.

Outcome: ERROR; StoneChatUnavailableException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Q027-002

User: Show small available basalt stones.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-003

User: Show small basalt stones admitted on 6 October 2026.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-004

User: Show reserved or adopted basalt stones.

Assistant:

No available stones match these conditions.

Links: [].

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-005

User: Show all available stones.

Assistant:

What is the size of the stones that are available? What is the type of the stones that are available?

Links: [].

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-006

User: Show basalt stones admitted on 6 October.

Assistant:

Which year do you mean? Which stone type would you like? What size or admission date do you prefer?

Links: [].

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-007

User: Find a stone for a display shelf.

Assistant:

Suitable candidates were not found.

Links: [].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-008

User: I need a specimen to decorate a bookshelf.

Assistant:

Suitable semantic candidates were not found. Here are exact alternatives; semantic wishes remain unconfirmed.
Basalt 05 — unconfirmed wishes: shelf.
Basalt 06 — unconfirmed wishes: shelf.
Basalt 04 — unconfirmed wishes: shelf.
Basalt 03 — unconfirmed wishes: shelf.
Basalt 02 — unconfirmed wishes: shelf.
More candidates were found.

Links: [B05, B06, B04, B03, B02].

Automatic violations: [UNASKED_EXACT_FILTER]. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-009

User: Find a small basalt for a display shelf.

Outcome: ERROR; InvalidStoneSemanticSearchOutputException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Q027-010

User: Find a small basalt for a display shelf, preferably blue.

Assistant:

Suitable candidates were not found.

Links: [].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-011

User: Find a red stone for a display shelf.

Assistant:

Suitable candidates were not found.

Links: [].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-012

User: Find a small blue basalt.

Assistant:

Suitable semantic candidates were not found. Here are exact alternatives; semantic wishes remain unconfirmed.
Basalt 10 — unconfirmed wishes: BLUE.
Basalt 05 — unconfirmed wishes: BLUE.
Basalt 06 — unconfirmed wishes: BLUE.
Basalt 04 — unconfirmed wishes: BLUE.
Basalt 03 — unconfirmed wishes: BLUE.
More candidates were found.

Links: [B10, B05, B06, B04, B03].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Human review rubric

Review permitted content and card precedence, relevance, individual-property truthfulness, unmatched/alternative labels, brief introduction and per-stone coverage, and no visible provenance. Actual verdicts and reasons are pending; templates are original answer-quality failures. Free-text hard truthfulness also requires review rather than accepting the production checker alone.

# Approved Correction Continuation (Original Failure Retained)

# Feature 0027 Original Qualification Evidence

Status: BASELINE_CONTINUATION_IN_PROGRESS. Human review: PENDING.

Run: 0fa5d20b-cac5-4255-ad1a-39af21ea0467; fixture: baa9c12c25f92d27c65a1cb8d6d2738322091988f1e3f1eb7afb83e343ec3024.
Original semantic outcomes: 1/9; answer denominator: 12; required turns: 28.
No favorable repetition replaces the original result. No performance measurement.

## Q027-001

User: Show available basalt stones.

Outcome: ERROR; StoneChatUnavailableException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Q027-002

User: Show small available basalt stones.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-003

User: Show small basalt stones admitted on 6 October 2026.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-004

User: Show reserved or adopted basalt stones.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-005

User: Show all available stones.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-006

User: Show basalt stones admitted on 6 October.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-007

User: Find a stone for a display shelf.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-008

User: I need a specimen to decorate a bookshelf.

Outcome: COMPLETED; null.

Automatic violations: [UNASKED_EXACT_FILTER]. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-009

User: Find a small basalt for a display shelf.

Outcome: ERROR; InvalidStoneSemanticSearchOutputException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

## Q027-010

User: Find a small basalt for a display shelf, preferably blue.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-011

User: Find a red stone for a display shelf.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-012

User: Find a small blue basalt.

Outcome: COMPLETED; null.

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-013

User: Find a turquoise stone.

Assistant:

Suitable candidates were not found.

Links: [].

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-014

User: Find a small basalt for a display shelf.

Outcome: ERROR; InvalidStoneSemanticSearchOutputException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

User: Now only large ones.

Assistant:

No available stones match these conditions.

Links: [].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-015

User: Find a small basalt for a display shelf.

Outcome: ERROR; InvalidStoneSemanticSearchOutputException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

User: Start a new search for a stone for a garden.

Assistant:

Suitable candidates were not found.

Links: [].

Automatic violations: []. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

## Q027-016

User: Show small basalt stones admitted on 6 October

Assistant:

Which year do you mean? Which stone type would you like? What size or admission date do you prefer?

Links: [].

Automatic violations: [EXACT_OR_REFERENCE_ORACLE]. Answer quality: NOT_IN_COHORT.

User: 2. Show the second stone.

Outcome: ERROR; InvalidStoneRagOutputException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: NOT_IN_COHORT.

## Q027-017

User: Show small available basalt stones.

Assistant:

Found 5 matching available stones.
Basalt 06: It matches the requested basalt type and small size.
Basalt 04: It matches the requested basalt type and small size.
Basalt 03: It matches the requested basalt type and small size.

Links: [B06, B04, B03].

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-018

User: Show small basalt stones admitted on 6 October 2026.

Assistant:

No available stones match these conditions.

Links: [].

Automatic violations: []. Answer quality: NOT_IN_COHORT.

## Q027-019

User: How should basalt be cared for? Tell me where you got this information.

Assistant:

BASALT can be cared for by a dry soft brush once a week.

Links: [].

Automatic violations: []. Answer quality: HUMAN_REVIEW_PENDING.

User: Find small basalt stones admitted on 6 October 2026 and tell me how to care for them.

Outcome: ERROR; InvalidStoneRagOutputException.

Automatic violations: [REQUEST_DID_NOT_COMPLETE]. Answer quality: FAIL_MISSING_GENERATION.

User: How do I care for the second stone?

Assistant:

The second stone's care instructions are not provided in the available information.

Links: [].

Automatic violations: [EXACT_OR_REFERENCE_ORACLE]. Answer quality: HUMAN_REVIEW_PENDING.

User: What exact color is the first stone?

Assistant:

Which stone type would you like? What size or admission date do you prefer?

Links: [].

Automatic violations: [EXACT_OR_REFERENCE_ORACLE]. Answer quality: FAIL_TEMPLATE_OR_MISSING_GENERATION.

User: Now only large ones.

Assistant:

Found 1 suitable candidates in the checked set.
Basalt 07: Only large Basalt 07 stones are available; the requested color is unconfirmed.

Links: [B07].

Automatic violations: [WRONG_SIZE:B07, WRONG_DATE_OR_REFERENCE:B07, EXACT_OR_REFERENCE_ORACLE]. Answer quality: HUMAN_REVIEW_PENDING.

## Human review rubric

Review permitted content and card precedence, relevance, individual-property truthfulness, unmatched/alternative labels, brief introduction and per-stone coverage, and no visible provenance. Actual verdicts and reasons are pending; templates are original answer-quality failures. Free-text hard truthfulness also requires review rather than accepting the production checker alone.


# Original Collection: Current Incomplete Result

Status: INCOMPLETE_HARNESS_ASSERTION_FAILURE_AND_EMBEDDING_BUDGET.
Twenty scenario identities recorded; 27 original user turns were submitted,
26 have full retained outcome evidence. Q027-020 turn 1 was submitted and reached
a failed following-history assertion, but its response/stages were not saved.
Its unknown payload is explicitly unavailable, not reconstructed or repeated.
Q027-020 turn 2 remains unattempted. Fixed denominators stay 20/28, R=9, A=12.
No qualification pass: R=2/9; original hard failures already prevent the hard gate,
and every A scenario has an unmet required turn. Actual human prose verdicts remain
pending, including generated Q027-019 turns 1/3/5.

Q027-017/018 pass their controlled race checks; Q027-020 cannot claim its control
hook/phase completion from missing evidence. Maven collector FAIL due to the
AssertionError; no green qualification is inferred. Subsequent test-only collector
preservation changes do not repair or rerun the original request.

Actual UTC 2026-10-13 ledger: 68958 charged, 31042 remaining of 100000.
Existing query embedding requires a 32768 admission reservation, so the remaining
knowledge phase cannot proceed under unchanged controls. Preserve ledger/unknown
usage; do not change budget/clock/source/model/threshold to finish. Recheck after
the actual new UTC day; partial original continuation requires an explicit
independent-phase path, not replaying turn 1 through the generic scenario collector.

Pending concrete agreements: proposed date/status/grounding production patch;
actual fixed-output human judgments; separately prepared 0029 mock submissions.
Feature 0030/personal manual application acceptance remain outside this batch.


Oracle transcription correction: Q027-019 turn 5 already requests LARGE. Its
original WRONG_SIZE:B07 is a false positive and remains preserved in the JSON
originalAutomaticHardViolations field; current hard findings retain the genuine
WRONG_DATE_OR_REFERENCE:B07 and EXACT_OR_REFERENCE_ORACLE. Production reply,
fixture, approved gold and failed scenario/quality scores remain unchanged.


# Approved Test Quota Amendment (D0027-011)

MODEL_DAILY_TOKEN_BUDGET is configurable; the qualification default is now
200000, doubled by explicit user request. Production default remains 100000.
Old attempts retain settings.dailyBudget=100000 because that was their effective
limit; current configuration is separate in testQuotaConfiguration.

Current actual UTC 2026-10-13 usage remains 68958; future approved allowance is
131042, above the 32768 embedding reservation. The earlier budget blocker is
historical, not current readiness. No ledger/reservation/model/source reset or
new inference; existing failure/gap/review/denominator evidence remains unchanged.
Current baseline is INCOMPLETE_ORIGINAL_EVIDENCE_AND_UNATTEMPTED_PHASE. Raising
the quota does not repair the missing response or repeat its original request.

Controlled verification PASS: verifyTokenBudgetConfiguration and
verifyQuotaInterruptionClassification, two tests, zero failures/errors. Defaults,
positive override, invalid limits, wrapped quota error and actual private RAG
quota-caused template are checked without model calls. Budget interruptions are
INCOMPLETE_BUDGET_EXHAUSTED, without grading the missing ordinary answer as a
quality failure/success; independent functional findings are preserved. Non-quota
invalid-output failures remain failures. Baseline/diagnostic collection stops
on this explicit resource classification even if application controls stay healthy.


# Current Autonomous Assessment — D0027-013

User review is DEFERRED_OUT_OF_SCOPE (OOS-0027-005), not a stop condition.
Reviewer: Codex agent, 2026-10-14; independent fixed-fixture/retained-output
rubric assessment, not the production grounder alone. No new model call or
human approval. Previous pending-human annotations remain historical.

All twelve original A scenarios have at least one failed required turn: current
A score 0/12 (required 10/12); R remains 2/9 (required 8/9). No smaller denominator
or favorable repetition. In Q027-019, generated turn 1 passes the care/content/
visibility rubric; turn 3 fails required care/reference behavior after the failed
selection; turn 5 fails retained date/zero-selection behavior and adds unrequested
color framing. Turn 2 already fails to complete; isolated turn 1 success cannot
pass the multi-turn scenario. Exact original responses and reasons remain in JSON.

Current qualification remains incomplete/failed because original evidence and an
unattempted controlled phase are unresolved and known hard/quality failures remain;
absence of user verdicts is removed from the blockers. Later manual assessment
is a separate stage and has not been performed or certified here.
