# ADR 0027: LLM Search Query Qualification

Date: 2026-10-13

Status: Accepted discussion; specification, acceptance, plan and tasks prepared.
The concrete fixture/oracle set is user-approved. No runtime readiness, fixture
provisioning, evaluation, implementation or answer-quality result is claimed.

Feature: [0027: LLM Search Query Qualification](../../specs/0027-llm-search-query-qualification/spec.md).

## Context and Accepted Discussion Procedure

The user requested sequential discussion of features 0025–0027 without code,
with detailed question/answer/decision records and completed documentation after
each discussion. Questions are asked directly in chat. The actual workflow
instructions are translated and recorded in
[ADR-0025, D0025-001 and D0025-002](0025-semantic-stone-candidate-search.md).

Features 0025 and 0026 now have prepared specification/acceptance/plan/tasks and
detailed ADRs. They are planning inputs, not evidence of delivered runtime
capabilities. Feature 0027 must define a user-reviewed evaluation set, outcomes,
quality gates and correction/tuning boundaries before execution.

### D0027-001 — Stop after completing feature 0027 discussion and documentation

On 2026-10-13 the user instructed, translated into English:

> After the complete discussion through the end of feature 27, stop.

Accepted boundary: finish the requested discussion and documentation for 0027,
then stop. Do not begin feature 0028, implement any feature, run evaluation or
treat prepared tickets as permission to execute them. The earlier documentation-
only instruction persists.

Sources:

- [Current 0027 draft](../../specs/0027-llm-search-query-qualification/spec.md),
  [acceptance.md](../../specs/0027-llm-search-query-qualification/acceptance.md),
  [plan.md](../../specs/0027-llm-search-query-qualification/plan.md) and
  [tasks.md](../../specs/0027-llm-search-query-qualification/tasks.md).
- [0025 candidate selection](../../specs/0025-semantic-stone-candidate-search/spec.md)
  and [ADR-0025](0025-semantic-stone-candidate-search.md).
- [0026 explanations](../../specs/0026-semantic-search-rag-answers/spec.md)
  and [ADR-0026](0026-semantic-search-rag-answers.md).
- [Shared discovery](../../specs/0015-llm-search-discovery/spec.md) and
  [ADR-0007](0007-llm-search-discovery.md).

## Inherited Baseline — Not New Answers

- Delivery and evaluation queries are English only, with the approved local
  model/provider profiles and shared resource controls as the starting point.
- The initial card/document similarity thresholds are 0.5; candidate/passages
  default to separate limits of 10. These are initial settings, not quality proof.
- Exact eligibility/filtering, current AVAILABLE, card contradiction veto,
  partial-wish truthfulness, selected ID/order/reference rules and hidden user
  provenance are requirements of the preceding features, not tunable preferences.
- General knowledge may supplement type explanations without contradicting
  card/encyclopedia facts or inventing individual properties.
- Selection-generation failures may have a legitimate checked-stones template;
  knowledge-only generation failures are errors. A template's validity is not
  automatically evidence of satisfactory generated-explanation quality.
- Broad representative-query qualification is explicitly assigned here; basic
  functional qualifications remain owned by preceding implementation features.
- No measurements, latency targets, tuning results, test execution or current
  readiness are established by the documentation.

## Discussion Log

### Q0027-001 — Should evaluation use a fixed isolated fixture set?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose a fixed test set of cards and encyclopedia passages in an isolated
> environment, so results do not depend on the changing catalog. Before running,
> I will show you the data, queries and expected outcomes for approval. Agree?

Reason: stable approved evidence separates retrieval/interpretation failures
from changes to adoption state or card content. This proposal does not authorize
creating production stones, importing documents, modifying the user's catalog
or running models during discussion.

User answer, translated into English: "1: Yes."

Accepted decision D0027-002:

- Use fixed synthetic test cards and encyclopedia passages in an isolated
  environment rather than the changing user's catalog.
- Show the concrete data, queries and expected outcomes to the user for review
  before execution. Agreement with isolation does not approve an unseen dataset.
- No production catalog mutation, document import or model call is authorized
  by agreeing to prepare the fixture proposal during discussion.

### Q0027-002 — How large should the initial representative query set be?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Start with 20 English scenarios covering exact filters, semantic wishes,
> paraphrases, partial/no matches, card contradictions, clarification, follow-ups
> and explanations? I propose this small initial set before expanding it.

Reason: the proposed number bounds local model work and human review without
claiming universal coverage. A scenario may include a conversation sequence;
its concrete turns and outcomes must be reviewed, not invented as accepted
fixtures by selecting a count.

User answer, translated into English: "2: Yes."

Accepted decision D0027-003:

- Start with 20 English control scenarios covering the proposed exact, semantic,
  paraphrase, partial/zero, contradiction, clarification, follow-up and explanation
  categories. A multi-turn scenario is one scenario with explicit constituent turns.
- Concrete fixtures/expected outcomes remain subject to D0027-002's review.
- Do not automatically enlarge the baseline set because a failure occurs;
  diagnostics are recorded separately from baseline scoring.

### Q0027-003 — What quality gates should block acceptance?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose 100% compliance with hard rules: exact filters, availability, no
> card contradictions, correct references and no visible information sources.
> For semantic selection and explanation quality, use an initial target of
> at least 80% of relevant control scenarios meeting their reviewed expected
> outcomes. Are these starting gates acceptable, or do you want another target?

Reason: a semantic-quality percentage must not permit forbidden catalog results
or source leakage. The proposed 80% needs defined eligible denominators and
per-scenario checks, not an unspecified subjective pass. Functionally valid
templates and inability to run due to controls must also be classified explicitly.

User answer, translated into English: "3: Yes."

Accepted decision D0027-004:

- Require 100% compliance with hard behavioral rules, including exact filters,
  current availability, card-contradiction veto, reference identity and hidden
  information-source attribution.
- Require at least 80% successful reviewed scenarios separately for semantic
  candidate quality and explanation quality. Do not combine them into a single
  average that hides a failed cohort.
- Hard-rule violations cannot be tolerated within a 20% quality allowance.
- Per-scenario scoring, eligible denominators, fallback classification and human
  assessment details remain to be agreed in the next question block.

### Q0027-004 — May qualification recommend a different similarity threshold?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> If the initial 0.5 threshold performs poorly, should this feature only record
> the result, or also compare bounded configuration-only threshold alternatives?
> I propose allowing that comparison and recommending a setting; code fixes
> remain a separately agreed scope rather than an automatic part of evaluation.

Reason: changing a threshold is not equivalent to replacing a model, ignoring
hard constraints or rewriting selection behavior. Trial settings, comparison
bounds, final adoption and documentation updates must be agreed before any
runtime configuration or implementation change.

User answer, translated into English:

> 4: If the similarity threshold works poorly, perform additional checks and
> agree on code corrections.

Accepted decision D0027-005:

- A poor threshold/query-quality result triggers additional diagnosis and an
  explicitly agreed correction scope, not automatic production edits.
- Diagnose the failure stage before treating a retrieval miss as proof that the
  numeric threshold is wrong; interpretation, evidence, scope and generation may
  also be responsible.
- The assistant's proposed configuration-only comparison/adoption is not treated
  as accepted by this answer. No threshold range or persistent setting change
  has been approved.
- Record evidence and propose exact correction files/behavior/verification for
  agreement before code changes. Current discussion still authorizes no code.

Accepted exclusion OOS-0027-001 records the automatic-correction boundary below.

### Q0027-005 — What initial/repeated-run policy should apply?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose running each of the 20 scenarios once initially, then up to two
> additional diagnostic repetitions of failed or ambiguous scenarios to check
> reproducibility. Record every attempt; a lucky repeat does not replace the
> original score. Agree?

Reason: local generation can vary, but an unbounded repeat-until-pass policy
would distort qualification and consume unapproved resources. Repetitions test
an explicit instability hypothesis and must remain within the same control/budget
rules; they are distinct diagnostic evidence, not new baseline scenarios.

User answer, translated into English: "5: Yes."

Accepted decision D0027-006:

- Run each of the 20 baseline scenarios once initially.
- Permit at most two additional diagnostic repetitions per failed/ambiguous
  scenario, for an explicit reproducibility/instability hypothesis.
- Preserve every attempt and its effective input/configuration/profile. Do not
  replace a failed baseline score with a favorable repeat or repeat until green.
- Diagnostics remain bounded by model admission, token accounting and deadlines.

### Q0027-006 — How should a valid selection template affect explanation quality?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> A clean template with correct stones is permitted behavior under 0026.
> I propose counting it as functionally valid, but not as a successful quality
> explanation in an ordinary scenario expecting generated per-stone prose.
> Deliberately simulated-failure scenarios can expect a template and are scored
> separately. Agree?

Reason: accepting runtime fallback should not let every generation failure pass
the explanation-quality target. A valid template may still preserve candidate
quality; exact outcome cohorts and external-control interruptions must be reported
without misrepresenting a partial run as full qualification.

User answer, translated into English: "6: Yes."

Accepted decision D0027-007:

- A correct checked-stones template is functionally valid under 0026.
- In an ordinary scenario expecting generated explanation, a template does
  not satisfy the explanation-quality outcome, even if candidate selection passes.
- Explicit injected-failure scenarios may expect templates and are scored
  separately from the ordinary explanation-quality cohort.
- Neither a valid template nor a diagnostic repeat can conceal a hard-rule
  violation or turn an incomplete run into a complete qualification pass.

### Q0027-007 — Are performance measurements wanted in this feature?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> Do you want speed measurements, or should we limit this feature to correctness
> and answer/selection quality without benchmarks? I propose no performance
> measurements for now; existing timeout/resource limits still apply.

Reason: resource/deadline correctness is already required, but latency/throughput
benchmarking and targets are an additional scope choice. No measurements or
optimization work has been selected during discussion.

User answer, translated into English: "7: Yes."

Accepted decision D0027-008:

- Limit this feature to correctness and selection/explanation quality, without
  performance measurements, benchmarks or optimization targets.
- Existing timeout/capacity/token/deadline correctness still applies. Recording
  a timeout as a failure is not a latency benchmark or speed qualification.
- Do not add throughput, percentile timing, CPU/memory or model speed analysis
  implicitly during query-quality work.

Accepted exclusion OOS-0027-002 is recorded below and in the central register.

### Q0027-008 — Who judges generated explanation quality?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I propose automatic checks against approved IDs/conditions for selection,
> and your review of explanations against a short checklist: correctness,
> relevance to the question, no invented individual properties and clear
> identification of unmatched wishes. Wording need not match a fixed string.
> Is that scoring procedure suitable?

Reason: the production grounding model is not an independent quality oracle
for its own answers. Human review against a predefined rubric can assess free
English wording without bending tests or silently redefining expected outcomes.
Automated hard checks still apply independently, including visible attribution.

User answer, translated into English: "8: Yes."

Accepted decision D0027-009:

- Check candidate identities/conditions automatically against approved fixture
  oracles; keep independent automatic hard checks.
- The user reviews generated explanations against the agreed short rubric:
  correct content, relevance to the question, no invented individual properties
  and clear unfulfilled wishes when applicable.
- Do not require fixed wording or use the production model's own checker verdict
  as the sole independent quality oracle.
- Record actual review outcomes before claiming the explanation-quality gate.

### Q0027-009 — Are the concrete fixture proposal and scenario oracles approved?

Date asked: 2026-10-13, directly in chat.

Question, translated into English:

> I prepared the concrete proposal in acceptance.md: 15 synthetic cards, seven
> encyclopedia passages and 20 scenarios with 28 user turns, including controlled
> late-change/failure cases. It also defines separate quality cohorts and the
> human-review rubric. Please review and approve the set, or identify changes.

Reason: D0027-002 requires review of actual data/expectations, not approval of a
future unseen fixture. Fixture text and scenario oracles are kept exclusively
in acceptance.md; this ADR records the question and response without duplicating
acceptance criteria. The proposal provisions no resources and runs no models.

Proposal: [Concrete fixture and scenario draft](../../specs/0027-llm-search-query-qualification/acceptance.md).

User answer, translated into English:

> Yes, the set is excellent. Mark my manual user testing against my own set as
> out of scope for now. I will not provide that set now; I will collect it in
> the process, as a separate part of application acceptance after implementation.

Accepted decision D0027-010:

- The user approves the concrete synthetic cards, passages, queries, expected
  outcomes and scoring/review set linked above. Preparing documentation may now
  complete without another fixture-approval question.
- This is approval of the evaluation design, not evidence of a successful run,
  completed readiness, provisioned resources or reviewed generated answers.
- Preserve the original baseline and its approved oracles when diagnosing failures.

### D0027-011 — Defer the user's separately collected manual acceptance set

Date: 2026-10-13. This was an explicit additional instruction in the answer to
Q0027-009, not an invented follow-up question or an inferred exclusion.

Accepted meaning:

- The user will collect a personal set during development and manually test the
  application against it after implementation as a separate acceptance activity.
- That personal set is not supplied now, need not be supplied for this feature,
  and is outside the current qualification tasks. No contents, size, rubric or
  execution date for it are assumed or fabricated.
- This deferral does not remove the approved fixed-set qualification or the
  user's review of its generated answers accepted in Q0027-008. Reviewing the
  fixed outputs and exploring a future personal set are distinct activities.
- Do not label the later application acceptance completed merely because the
  current fixed qualification set eventually passes.

Accepted exclusion OOS-0027-003 is recorded below and in the central register.

## Technical Preparation Choices and Consequences

These are the assistant's implementation-preparation choices within the accepted
evaluation scope, not additional questions or user answers:

- Use test-only fixture/harness code and explicit resources, with production
  application source read-only. Exercise existing delivered 0025/0026 behavior;
  their prepared documents cannot substitute for delivered code at readiness.
- Keep the approved fixture and outcome definitions in acceptance.md; a future
  executable resource transcribes them rather than silently creating a new gold.
  Controlled race/failure hooks are test instrumentation, never public endpoints.
- Prepare six separately requested tickets: readiness, fixture/harness preparation,
  real-model baseline, human fixed-output review, conditional bounded diagnostics
  and final evidence review. Exact future writable paths/selectors are in tasks.md.
- Maintain a machine-readable report and a readable review/report document in
  this feature directory. Retain IDs/configuration/profile/attempts, model usage,
  control failures and stage evidence without secrets or speed measurements.
- Separate date-interpretation reference from advancing runtime/accounting clocks.
  Isolate fixture/history records without deleting actual provider-usage accounting
  or resetting daily budget/concurrency/deadline controls to complete the set.
- Preserve pinned settings and existing generated HTTP contracts. No new dependency,
  schema, Kafka/HTTP contract, frontend or production threshold change is planned.
- A failure can lead to a concrete separately agreed correction proposal; it cannot
  authorize implementation, threshold adoption or a repeat-until-green campaign.

## Accepted Out-of-Scope Decisions

Matching accepted entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0027-001 | Automatic production-code corrections during qualification | A poor result triggers additional diagnosis and a concrete correction proposal. Agree the correction behavior, exact writable files and verification separately before code changes; qualification alone does not authorize production fixes or threshold adoption. |
| OOS-0027-002 | Performance measurements, benchmarks and speed optimization | Evaluate correctness and selection/explanation quality without latency/throughput, percentile timing, CPU/memory or model-speed analysis. Existing timeout/resource-limit correctness and reporting of failures remain required. |
| OOS-0027-003 | Manual application acceptance against the user's personal scenario set | The user will collect a separate personal set during development and manually test the application after implementation. That unsupplied set and its execution are deferred from current 0027 qualification; no submission is required now. The approved fixed 20-scenario qualification and human review of its generated answers remain in scope. |

Only confirmed exclusions are recorded; unanswered proposals are not decisions.

## Completion Gate

All nine asked questions have answers; the concrete set and the additional manual-
acceptance deferral are recorded. Specification, acceptance, plan and tasks are
prepared with no unresolved product questions. Runtime evidence remains NOT RUN.
No implementation, model calls, business import, fixture provisioning, application
tests, commits or pushes are authorized merely by this discussion record.
Stop after completing this documentation, as required by D0027-001.

## Execution Request and Blocked Readiness — 2026-10-13

The user requested execution of every task in features 0025–0029 without stopping
at individual task/feature boundaries, and one commit for each entire completed
feature rather than one per task. This is explicit implementation/test/feature-
commit authorization for that batch, superseding the earlier documentation-only
stop boundary. It does not authorize pushing, feature 0030, relaxing acceptance,
production fixes inside 0027 qualification, or replacing actual human review.
No new product question or exclusion was introduced by this execution request.

Fresh batch preflight is BLOCKED, as recorded in
[the feature task evidence](../../specs/0027-llm-search-query-qualification/tasks.md#requested-batch-readiness--2026-10-13).
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
[the current task readiness evidence](../../specs/0027-llm-search-query-qualification/tasks.md#fresh-batch-readiness-after-browser-connection--2026-10-13).
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
