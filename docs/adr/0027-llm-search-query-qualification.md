# ADR 0027: LLM Search Query Qualification

Current commit-scope update (2026-10-14): the user explicitly requested completion
of the 0029 handoff and local commits of all current changes. This authorizes
committing the existing 0027 fixture/harness and preserved qualification evidence
as incomplete work, superseding the earlier completed-feature-only commit boundary.
It does not execute unfinished 0027 tasks, approve pending production corrections
or claim passed qualification. The 0029 submission requirement has been removed
under D0029-018/OOS-0029-005 and T0029-006 is closed; older cross-feature pending
submission references below are historical. No push is requested.

Date: 2026-10-13

Status: Accepted discussion; specification, acceptance, plan and tasks prepared.
The concrete fixture/oracle set is user-approved. No runtime readiness, fixture
provisioning, evaluation, implementation or answer-quality result is claimed.

Feature: [0027: LLM Search Query Qualification](../../specs/0027-llm-search-query-qualification/spec.md).

## Execution Readiness and Fixture Preparation — 2026-10-13

The user subsequently requested all 0025–0029 tickets, continuous execution,
whole-feature commits and necessary type changes. Earlier documentation-only
boundaries describe the discussion phase, not current execution authorization.
Predecessors 0025 (ee6d532) and 0026 (57c56ea) are delivered with focused evidence.
Fresh backend health UP, Redis PONG and both approved installed model digests
confirm the selected batch's external prerequisites. Command-scoped JDK/Maven
and runtime pins remain those in the recorded whole-batch readiness report.

Preparation, not a read-only probe: create a dedicated test-owned PostgreSQL/vector
container stone-search-qualification-0027 at 127.0.0.1:55427, with the approved
immutable image and a dedicated named volume. Reuse existing authorized datasource
credentials without printing values. Authenticated readiness confirms PostgreSQL
18.6 and database stone_search_qualification_0027. The production database has no
published host port and is not used or modified. Preserve this target's actual
usage ledger/reservations across fixture cleanup and future diagnostic commands;
do not use disposable prototype-ledger resets for real qualification.

The exact three T0027-002 paths transcribe the approved acceptance tables and
construct test-only application seams with separate date-interpretation and real
deadline/accounting clocks. No production changes or model calls in the harness
verification. Subsequent baseline is one original attempt per scenario; retain
partial failures and real usage, with actual human review still pending.

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


## T0027-002 — Harness Verified Without Inference

Final focused harness selector passes (one test). It applies existing migrations
to the verified dedicated target, confirms vector 0.8.6, transcribes and checks
every approved card field with generated ID mapping, exercises owned reserve/
delete mutations and cleans its records. Actual model reservation count remains
unchanged. Data/cohort completeness and independent positive/negative/priority
oracles pass. Model quality is not inferred from this check.

The test-only collector captures real private input/output, retrieval/provenance,
selected proofs/state, current eligibility and ledger evidence. Preserve original
results/review separately from conditional diagnostics; retain compatible vector
cache in machine evidence for unchanged fixture restoration. The harness requires
explicit failed/ambiguous IDs/hypothesis and rejects successful-case or third
repetitions. Baseline reports use fixed original denominators even when incomplete.
Next action is the single original real-model baseline; no correction or tuning.

## Baseline Startup Failure Before Any Query — 2026-10-13

The initial collector invocation stops in configure/availability.recover at the
first Redis probe. Tool-captured stack identifies connection acquisition consuming
the existing one-second Redis operation deadline; no user scenario, fixture
embedding, model call or reservation ran. The retained report has zero cases,
zero cached vectors and zero reservations. This is an incomplete setup attempt,
not an original semantic/answer score or permission to relax a timeout.

Necessary test-only setup refinement before starting T0027-003: add an opt-in
physical connection preparation method to the existing
stone-shelter-api/src/test/java/lab/stoneshelter/StoneChatRedisTestSupport.java,
called only by the new qualification harness. Establishing the client connection
is environment preparation; every subsequent application probe/operation still
uses the approved PT1S deadline. Existing subclasses' behavior stays unchanged.
The user's necessary-type authorization covers this additional test-support path.
Recheck the focused harness including a real bounded Redis probe, with no inference.

Preserve the failed startup report under setupAttempts inside the same allowed
machine evidence. Restart is permitted only when both retained and actual ledger
have zero reservations, zero cases and zero vectors; any attempted user/model
work prevents this path. Do not overwrite an original attempted baseline or delete
usage. No production code, model/threshold/resource setting or gold changes.

## T0027-003 — Original Baseline Incomplete and Concrete Correction Proposal

The original baseline prepares all 22 real embeddings, then Q027-001 fails before
answer generation. Retain the single original user turn, zero completed pairs,
24 reservations and 974 actual charged tokens. Other scenarios are not attempted.
Collector success is not qualification success: INCOMPLETE_CONTROL_OUTAGE. The
previous zero-query Redis setup failure stays preserved under setupAttempts.

Stages show correct exact filters and total/order 8/B10/B07/B05/B06/B04; the
interpreter additionally emits topic=geology and a supplementary embedding settles.
No generation or passage result is recorded. Inspection identifies a blocking
integration defect: OllamaEmbeddingRepository returns the established base profile
followed by / and the actual digest; StoneSemanticSearchRepository.requireProfile
compares only with the bare base. Real fixture cards use the returned profile but
published document metadata incorrectly uses the bare profile. This is not evidence
that 0.5 is a bad threshold. Cause frames were not retained, so do not claim this
inspection proves the defect is the sole original runtime cause.

Concrete correction proposed for separate agreement under OOS-0027-001:

1. StoneSemanticSearchRepository.java recognizes/validates the existing complete
   digest-qualified model profile while retaining exact query/card/document
   equality and compatible-vector checks. No new profile, wrong-digest acceptance
   or production reindex/import.
2. StoneSearchQualificationFixture.java uses actual full profiles consistently
   for card/document seed/cache/restore. Its real accounting survives cleanup.
3. Existing controlled semantic/RAG retrieval fixtures use the actual producer
   profile shape and test incompatible/bare/mismatched rejection without changing
   any candidate/order/quality oracle.
4. StoneSearchQueryQualificationTest.java captures safe cause classes/frames and
   fixed-stage diagnosis; original Q027-001 remains failed, not replaced. SQL DATE
   report display uses the actual date string without moving accounting clocks/rows.
5. Focused verification uses semantic retrieval/knowledge/controlled integration
   selectors plus one real-profile query/SQL stage check. No full suite or changed
   models/thresholds/resources/gold/public contracts.

No proposed production correction has been executed. No partial completed-feature
0027 commit or push. Actual human review has no generated reply yet. Independent
0028 transport prototype has no search/model dependency; its preparation may
continue while search-coupled integration waits for this defect's resolution.

## Separately Authorized Correction — 2026-10-13

The user answered “yes, do it” and authorized necessary type changes within
features 0025–0029. This accepts the concrete full-profile integration correction
above; it does not approve threshold/model/resource/gold changes. Original failed
evidence and accounting remain preserved. The original SQL DATE display was
timezone-shifted during JSON serialization; subsequent metadata uses SQL date
strings without modifying ledger rows or accounting clocks.

The continuation is explicit and single-use: preserve the complete prior machine
evidence/readable report, run one Q027-001 correction diagnostic with an explicit
profile hypothesis, and collect only previously unattempted original Q027-002–020
turns. The diagnostic never replaces original Q027-001 or improves its answer score.
Compatible unchanged real vectors are restored with the verified actual digest;
there is no repeated embedding preparation or usage deletion. New outages still
stop collection as incomplete. Safe cause classes/application frames exclude
exception messages/credentials. The original hard-gate failure remains visible.

## Correction Diagnostic Outage and Remaining Original Cases

The separately agreed single correction diagnostic stopped during interpretation
at the unchanged PT50S call deadline; safe cause frames show TimeoutException in
StoneStructuredModelService. No passage/evidence/generation stage ran. Preserve
unknown usage as the reserved 8192 tokens, giving 9166 charged total; do not refund
or reset the ledger. This does not prove a semantic threshold or sole original
profile failure. The current local provider's read-only metadata remains reachable
and shows the approved chat digest/context; no inference is a readiness probe.

The diagnostic overlapped independent UI verification; this is a recorded execution
fact, not a proven timing cause or performance claim. Further real collection does
not overlap UI suites/backend builds. After fresh bounded dependency recovery,
continue only original cases never attempted. Never retry Q027-001, replace its
score or silently raise call/turn/resource limits. Every continuation preserves
its prior report, run identity, accounting and previously recorded cases. Another
control outage still stops that invocation as incomplete.

## Original Q027-002 After Recovery

Only previously unattempted Q027-002 was submitted. Interpretation succeeds and
exact retrieval returns seven in the correct presented order B10/B05/B06/B04/B03.
The current-turn selection template is acknowledged as one saved pair, with no
automatic hard violations, while a provider failure closes later operations.
This ordinary expected-generation scenario fails A rather than being counted as
quality success. The report remains INCOMPLETE_UNATTEMPTED_CASE_CONTINUATION_OUTAGE;
no Q027-003–020 or human generated-answer review is claimed. Preserved real ledger
total is 17761 charged tokens on the actual UTC day.

The opt-in physical Redis-connection preparation helper is also required by
0028's native HTTP/Redis fixture. Its shared test-support implementation is included
in the whole-feature 0028 commit, establishing a committed prerequisite for this
qualification harness. Actual subsequent application operations retain PT1S.
The retained real-profile SQL stage passes after its fixture supplies the mandatory
meaningful shelf wish; this is a technical cached-vector check, not another
natural-query attempt or favorable replacement score.

## Fresh Continuation Readiness — 2026-10-14 Local Date

Covered remaining qualification tickets: T0027-003–T0027-006 (human review remains
separate). Updated main API health UP, original API-mode frontend HTTP 200,
Ollama 0.40.3 metadata reachable, owned qualification database has zero cards/
zero published indexes after browser-fixture cleanup. Its actual UTC 2026-10-13
ledger retains 19655 tokens, including separately identified 0029 API smoke; no
reservation/day/accounting reset. Read-only real-profile SQL stage is PASS with
unchanged compatible cached vectors. Current original cases Q027-001/002 remain
unchanged, including failed diagnostics/template. Continue only Q027-003 onward;
no UI suite/backend build overlaps inference. A new outage still stops as incomplete.
The actual browser smoke is not fixed-set scoring or human answer review.


## Original Q027-003 and Concrete Grounding Correction Proposal

Local date 2026-10-14; actual ledger day 2026-10-13. Original Q027-003
retrieves B05/B06 correctly and saves a clean selection template. Ordinary
answer quality is FAIL, not generated success. The retained real ledger is 31147
tokens, including separately identified 0029 browser smoke; no accounting reset.

Successful captured generation incorrectly calls the supplied cards' admission
date unconfirmed. Inspection localizes a concrete missing-fact seam:
StoneRagAnswerCriteriaToStoneRagGenerationRequestMapper copies id/name/type/size/
biography, but omits the existing admissionDate/adoptionStatus. A captured checker
verdict also incorrectly demands the name and source of the admission date. The
backend already adds names and accepted user-visible provenance is forbidden.
The checker validates remaining explanations even after an earlier verdict rejects
the draft; its later repair/check sequence exhausts the original work deadline.
These observations do not prove a threshold defect or guarantee that one patch
will repair all natural-query quality. Original outcomes remain unchanged.

Reviewable proposal: /private/tmp/stone-rag-grounding-correction.patch. Exact
production files are StoneRagCandidateRequest,
StoneRagAnswerCriteriaToStoneRagGenerationRequestMapper, OllamaRagAnswerProvider,
OllamaRagGroundingProvider and StoneRagGroundingService, in their existing shared/
mappers.dtos/services packages. Carry existing date/status facts internally;
clarify known UTC dates/status versus unmatched wishes and forbidden attribution;
stop checking an already rejected draft before the existing single correction.
Every accepted draft still requires all applicable prose/source checks.
No HTTP/schema/dependency/profile/threshold/resource/gold change is proposed.
Focused verification: StoneRagAnswerTest, with actual fact propagation and rejected
draft short-circuit regression coverage, followed by one separately approved
failed Q027-003 diagnostic under unchanged bounds. Any original-case continuation
submits only never-attempted cases and preserves all previous evidence.

Assistant question: does the user accept these five concrete production changes,
the focused tests and one failed-case diagnostic? Suggested answers: approve;
continue remaining originals only; retain proposal without changes. Actual answer:
PENDING. OOS-0027-001 requires separate concrete agreement before production edits;
this proposal is not an accepted scope exclusion or implemented correction.

Fresh read-only readiness for T0027-003–006: JAVA_HOME selects Java 23.0.2/Maven
3.9.9; main API health UP; API 5173/mock 5174 HTTP 200; Redis PONG; owned PostgreSQL
18.6/vector 0.8.6 authenticated, owner confirmed, zero remaining cards/indexes;
Ollama 0.40.3 and both unchanged approved digests installed. Main MinIO/Kafka/
PostgreSQL/Redis containers remain healthy. No startup/provisioning/inference was
a readiness action. Overall external environment READY; correction agreement and
actual fixed-output human review remain separate pending gates.


## Additional Original Cases During Pending Correction Agreement

Only previously unattempted originals are collected. Q027-004–006 complete with
no automatic hard violations: prohibited statuses return no candidates, generic
availability asks at least two questions, and the missing year is clarified.
Q027-007 returns no candidates: the fixed threshold admits only E07/Obsidian 01,
whose explicit card shelf contradiction is correctly vetoed. The relevance oracle
fails; no alternative threshold has been tried or adopted.
Q027-008 returns B05/B06/B04/B03/B02, without the required card-supported granite
member. Its interpreted exact constraints include an unasked filter, independently
failing the hard rule UNASKED_EXACT_FILTER. Its ordinary explanation is a template,
not answer-quality success. These cases remain original failures even if a later
separately agreed diagnostic improves. Continue collection while shared controls
remain healthy; a new outage stops the invocation.

Questions on the concrete grounding patch and two mock form submissions remain
pending. No source correction under OOS-0027-001, changed thresholds/gold/limits,
actual human quality judgments or successful form submissions are assumed.


## Continuation Through Q027-012: Incomplete After Model Closure

The continuation attempts only new original Q027-004–012. Twelve original cases
now have retained evidence; Q027-013–020 remain unattempted. Its Maven collector
passes (one test), which certifies evidence collection only, not qualification.
Q027-009 fails strict semantic-output validation after the allowed repair: the
retained final UNKNOWN verdict incorrectly includes a quote instead of null.
Q027-010/011 return no candidates and fail their relevance oracles, without
automatic hard violations. Q027-012 returns the expected exact alternatives
B10/B05/B06/B04/B03, with no automatic hard violations; ordinary generation
is a template, hence FAIL. Successful stages include one draft and two grounding
responses before model-control closure, not a completed generated answer.

Result: INCOMPLETE_UNATTEMPTED_CASE_CONTINUATION_OUTAGE. Actual UTC-day ledger
2026-10-13 retains 53814 tokens. Unknown calls retain conservative reservations;
no deletion/refund/reset or limit change. Source correction approval is still
pending; no favorable repetition is substituted. Independent frontend verification
runs only after this inference process has exited. Continue never-attempted
originals only after fresh dependency/control recovery.


## Collector Failure and Budget Gate — 2026-10-14 Local Date

Q027-013 passes its unsupported-turquoise oracle. Q027-014/015 fail semantic
output validation on their first turns and return no expected selection on their
second turns. Q027-016 returns no expected first selection and fails its second
reference turn. Q027-017/018 exercise the controlled final-eligibility races and
pass their automatic hard oracles; neither establishes ordinary generation quality.

Q027-019 has real generated care on turn 1, failed mixed selection on turn 2,
an unlinked unknown-care answer on turn 3, clarification on turn 4, and an
unexpected Basalt 07 selection on turn 5. Its original scenario cannot pass:
turn 2 is incomplete, expected references are absent, and turn 5 drops the expected
date/size context. The user's actual human review of generated prose is pending.

Actual displayed replies:
1. BASALT can be cared for by a dry soft brush once a week.
3. The second stone's care instructions are not provided in the available information.
5. Found 1 suitable candidates in the checked set.
   Basalt 07: Only large Basalt 07 stones are available; the requested color is unconfirmed.

Assistant review question quotes turns 1/3, their literal questions and approved
expected care/reference behavior, and asks about accuracy/relevance/clarity/
invention/hidden attribution. Suggested answers: first fits/third does not; neither
fits; both fit. Actual user answer PENDING. No human verdict is fabricated.
The unexpected fifth-turn generation also remains review-pending, with automatic
hard failures already recorded; approval of isolated prose cannot pass the scenario.

On original Q027-020 turn 1, the collector reaches its following-history closure
assertion, which fails because the operation returns normally. AssertionError
bypasses catch(Exception), preventing persistence of the in-memory turn. Maven
FAIL: expected a throwable. The request was actually submitted and at least one
completed pair was read, but its literal response/stages are no longer recoverable
from permitted retained evidence. Do not invent them or rerun this original turn.
Record a failed original with explicitly unavailable payload, preserving accounting.
The second independent controlled phase remains unattempted.

Necessary test-only corrections: existing StoneSearchQueryQualificationTest.java
records a false closure as a hard failure rather than aborting before evidence
persistence; its outer evidence boundaries also preserve AssertionError before
rethrowing. Add focused controlled closure-classification regression coverage,
without weakening the closure oracle. No application source or approved gold changes.
Existing named harness checks require isolated PostgreSQL/Redis and no inference;
those prerequisites remain ready after cleanup (zero cards/indexes). Java/Docker
profiles remain unchanged; the last actual model process has exited.

Fresh affected-prerequisite result for T0027-003: BLOCKED. Actual UTC 2026-10-13
charged total is 68958, leaving 31042 of the unchanged 100000. Existing embedding
admission requires MAX_INPUT_TOKENS=32768 even for a short query; remaining budget
cannot admit the necessary knowledge-phase query. No model call is used to probe
this known rejection. Preserve usage/reservations and do not raise limits, refund
unknown calls, reuse a different ledger or move accounting clocks. Resume only
after the actual new UTC day and fresh readiness, with explicit treatment of the
unattempted independent phase and retained missing-original evidence.
The separately proposed production grounding correction and mock submissions are
still awaiting actual user answers; no accepted exclusion is inferred.


## Oracle Transcription Correction: Q027-019 Turn 5

The accepted last query is "Now only large ones." Inspection finds a test-only
transcription bug: the hard checker recognizes LARGE only for Q027-014 turn 2,
and incorrectly labels returned large B07 as WRONG_SIZE on Q027-019 turn 5.
Correct the checker to the already approved LARGE condition, without changing the
fixture/gold or production output. Keep original raw verdicts separately visible.
B07 still violates the required retained date and zero-result/reference oracle;
this correction does not turn the original scenario into a pass. Focused harness
coverage verifies both that B07 is not falsely wrong-size on the large turn and
that it remains wrong-size on a small-reference turn, retaining its true date
violation. No inference, original rerun, threshold or resource change.


Final controlled follow-up: verifyClosedOperationClassification and
verifyFixtureAndHarness PASS (two tests, zero failures/errors), without inference.
The approved LARGE correction retains actual date/reference violations and its
original false-positive flag. Final current qualification is incomplete/failed,
not a completed-feature commit. Actual human review, concrete production patch
agreement and original second controlled phase remain pending/blocked as recorded.


Final affected-readiness report: T0027-003 BLOCKED on embedding admission budget,
68958 charged/31042 remaining versus required 32768, actual UTC 2026-10-13.
Controlled test-only follow-up PASS and fixture cleanup confirmed zero cards/
indexes; ledger unchanged. Main API health UP and main API-mode 5173/mock 5174
HTTP 200. Original Compose API remains stopped because the running batch API
replaces it on the original 8080 configuration/storage. Temporary isolated API
and its 5175 frontend are stopped/removed after their approved browser checks;
this has no impact on main services or the two pending mock forms.
Overall remaining model batch BLOCKED, not all unchecked components ready;
production correction agreement and actual human review remain pending. No
partial completed-feature 0027/0029 commit or push is made.


## Q0027-010 / D0027-011: Configurable Doubled Test Quota

User question: the test run being blocked until the next UTC day despite remaining
tokens is the problem under discussion. Assistant explains that the natural-query
campaign shares actual daily test accounting with preparation/diagnostics/browser
smoke and the embedding reserve exceeds the remaining allowance; propose explicit
test configuration and incomplete classification distinct from functional failure.
Actual user instruction, translated: "Let's revise and increase the budgets; if
we exceed token budgets, verification is incomplete; the daily quota must be
configurable; double it for test runs." This is accepted configuration scope,
not permission for unrelated pending production changes.

Decision: use existing MODEL_DAILY_TOKEN_BUDGET, qualification default 200000,
positive explicit override supported. Application configuration already supports
the same key with its unchanged 100000 default. Remove test/temporary-launcher
fixed 100000 assignments and report effective per-attempt configuration. Preserve
68958 existing charged tokens, earlier reservations, actual UTC clock and original
results; future allowance is 131042 under the approved 200000 limit. The old
31042/32768 blocker is historical evidence, not the new configured readiness.

Daily token exhaustion produces INCOMPLETE_BUDGET_EXHAUSTED, including a clean
saved selection template whose private RAG failure is quota exhaustion. Observe
that private cause only inside the test harness; do not add user-visible fields
or alter production completion. Actual responses/hard findings remain retained,
while interrupted ordinary answer quality is ungraded and no automatic retry
or favorable re-scoring occurs. Genuine independent failures remain failures.
Do not increase context/output/call/evidence/deadline/concurrency/threshold limits.
Controlled quota-boundary tests are separate from natural-query quality results.

Exact change: existing StoneSearchQueryQualificationTest.java, spec/acceptance/
plan/tasks/evidence, ADR-0027, environment-setup.md and owned temporary launcher.
No new dependency/migration/configuration file or production source is needed.
Fresh T0027-007 readiness READY as recorded in tasks; controlled selectors need
no model call. Commit authorization remains one whole completed feature; no
partial feature commit or push is implied by this correction.


D0027-011 implementation and verification: existing quota configuration validated
and test default raised to 200000. Command-scoped explicit value preserved by
owned launcher; future settings/diagnostic metadata use effective dailyBudget.
Test-only observed answer service detects private quota failures without changing
ready HTTP DTOs, then incomplete classification stops original/diagnostic loops.
Wrapped daily rejection and quota TEMPLATE are ungraded incomplete; actual
COMPLETED/ERROR delivery and genuine independent hard findings remain.
Two controlled tests PASS (zero failures/errors), no model calls. One missing
new-test import caused initial compilation failure and was corrected; no unchanged
model attempt was repeated. Ledger/owned-data readback: 68958, zero cards/indexes.
Historical 100000 snapshots and failed natural-query scores remain intact; new
configuration metadata separately records 200000/131042 and READY budget status.
Previous budget blocker is resolved; missing original payload, unattempted phase,
pending production correction, actual human review and mock permission are not
claimed resolved by increasing the quota. No partial-feature commit or push.


## Q0027-011: Smaller Embedding Reservation — Proposal, Not Accepted

Actual user question, translated: "The reserve of 32768 for any short embedding
request should also be reconsidered separately. What do you propose?" This asks
for discussion, not an implementation instruction; no acceptance is inferred.

Read-only inspection confirms StoneDocumentEmbeddingService acquires the full
MAX_INPUT_TOKENS=32768 before each call. The mapper sends the exact source text,
or QUERY_INSTRUCTION plus query text. OllamaEmbeddingRepository uses one input,
truncate=false and known prompt_eval_count for actual settlement. The current
regex/rough character-based estimateTokens is not a proven accounting upper bound.
Installed profile metadata reports qwen3 context 32768, GPT2/Qwen2 byte-level
tokenization, add_bos=false and add_eos=true; no inference was performed.
Official API: https://docs.ollama.com/api/embed documents prompt_eval_count and
non-truncating over-capacity errors. Upstream tokenizer metadata is supporting
reference, not a substitute for the installed digest-qualified runtime profile.

Assistant proposal: reserve a proven upper bound for the actual full input,
including the query instruction. For the verified byte-level profile, derive it
from UTF-8 bytes plus verified special-token/preprocessing overhead, bounded by
the existing 32768 maximum for accepted inference. Confirm the bound against
the pinned tokenizer/runtime behavior before adoption; sample tests alone cannot
prove a universal bound. Do not guess a lower constant or use chars/4 for quota.
Keep actual prompt_eval_count settlement; on unknown usage retain that request's
conservative reserve. Keep the full conservative reserve for an unverified
compatible profile, or refuse unsupported profile/capacity rather than assume
the small bound applies. Preserve truncate=false, dimensions, query/profile
semantics, guards and prior accounting. No automatic retry or quota bypass.

A later exact matching local tokenizer is another possible approach, with
additional implementation/dependency maintenance cost; it is not the initial
recommended change. Proposed focused checks cover Unicode/emoji/special tokens,
query prefix, long inputs, profile mismatch, known settlement and unknown usage,
without model-speed benchmarking. Existing 32768 capacity remains distinct from
a short request's admission reserve.

Actual user answer: PENDING. No production code, new dependency, model call,
configuration change, accepted exclusion or original score modification occurs.


## D0027-012 / OOS-0027-004: Defer Smaller Embedding Reservations

Follow-up user instruction, translated: "Let's put this solution out of scope
after all; what is left across the features?" This explicitly defers the preceding
Q0027-011 smaller-reservation proposal. The earlier PENDING note is historical;
the current answer is accepted deferral, not implementation approval.

Accepted exclusion OOS-0027-004: request-sized embedding reservation, exact/local
tokenizer work, byte-derived/special-token allowance and related profile-bound
reservation redesign are outside the current 0025–0029 delivery scope. Retain
the existing conservative 32768 reservation per embedding call. Revisit only as
separately scoped future work; no new feature number or automatic phase-2 ticket
is assigned by this instruction. No production code/model/profile/dependency
or prior-accounting change is made.

D0027-011 remains delivered: configurable test daily quota defaults to 200000,
with INCOMPLETE_BUDGET_EXHAUSTED classification and actual usage retained.
Deferring request-sized reservations does not revert that accepted quota change
or waive the remaining fixed-set quality/human-review/browser obligations.
The matching exclusion is recorded in docs/out-of-scope-decisions.md and linked
from the feature spec/plan. This is a documentation-only accepted scope decision.

Remaining-ticket review: 0025/0026/0028 task lists are complete and their separate
whole-feature commits exist. 0027 has T0027-003–006 open: 27 original submissions,
26 fully retained of 28; missing original payload/unattempted independent phase,
failed quality/hard outcomes, actual human review and final evidence review.
The date/status/grounding correction proposal still awaits separate agreement;
semantic/filter failures need diagnosis, not assumed threshold changes.
T0027-007 quota amendment is complete. 0029 has T0029-006 open: prepared local
mock create/reservation submissions await specific permission after automatic
approval rejection, then final browser/acceptance review and whole-feature commit.
Its lint/build/all 235 UI tests already pass. No pending ticket is marked complete
by this deferral, and no partial feature commit/push is performed.


## Q0027-012 / D0027-013 / OOS-0027-005: Defer User Answer Review

Actual user instruction, translated: "Do 27 without my assessment of answers;
I will do it later as a separate stage; we will not assess manually now; keep
assessment without my involvement and remove this item." The same message asks
what automatic approval rejected the mock submission and what the user must do.
This explicitly supersedes the original current user-review gate; it is not a
fabricated human verdict or waiver of automatic acceptance/quality checks.

Current 0027 assessment is autonomous: independent fixture/hard/cohort checks and
agent assessment of retained free text against the accepted rubric. Preserve
reviewer identity and reasons; production grounding alone is not an independent
review. Human review is DEFERRED_OUT_OF_SCOPE under OOS-0027-005, to a later
separate user stage. No user's verdict is requested or required to continue/close
current assessment. Keep the 20/28 and 9/12 cohorts, 8/9 and 10/12 targets,
original outputs/failures/incomplete distinctions, actual model usage and budget.
Do not relabel autonomous evidence as human-approved or later app acceptance.

Preserve T0027-004 ID, replacing its user-review activity with autonomous original
assessment/scoring. It may assess retained cohort evidence incrementally while
T0027-003 overall collection remains incomplete; final completeness gates remain.
Future harness/report wording changes only assessment ownership/status, without
model calls or production behavior changes. Existing original HUMAN_REVIEW_PENDING
markers are preserved as historical metadata when current evidence is annotated.
OOS-0027-003 remains the deferred personal set; remove its obsolete implication
that fixed-set human review is required in the current phase and link OOS-0027-005.

Current requested scope/readiness: T0027-004 and necessary T0027-002 report-marker
follow-up, plus T0027-006 factual documentation. First T0027-001 remains first.
Read-only READY: explicit JAVA_HOME selects Java 23.0.2/Maven 3.9.9, Docker/owned
PostgreSQL/vector/Redis available, owner and zero cards/indexes confirmed, ledger
68958 preserved, test quota 200000. Main API UP, API/mock frontends HTTP 200,
Ollama 0.40.3 and unchanged chat/embedding digests installed. Original Compose
API is stopped and replaced by healthy batch API; temporary browser test services
remain intentionally removed. No startup/provisioning/inference as readiness.
Focused selector: verifyAutonomousReviewPolicy; no model call or original rerun.

Browser explanation uses OpenAI Docs and the actual prior tool rejection. It is
a tool-action permission review before submission, not a Stone Shelter test or
backend error. Prior rejection specifically says broad UI-test permission does
not authorize the consequential application submit even in mock mode. Clear user
authorization naming the local mock origin, synthetic fixture/contact and two
operations would authorize a subsequent attempt; do not promise automatic review
will accept or bypass it if rejected again. The present request asks for an
explanation and does not itself authorize clicking either form.
Official guidance: https://learn.chatgpt.com/docs/permission-modes and
https://developers.openai.com/api/docs/guides/tools-computer-use.


D0027-013 delivered: source writer reports DEFERRED_OUT_OF_SCOPE human review
and AUTONOMOUS_REVIEW_PENDING for newly generated A replies; no manual-input
completion condition remains. verifyAutonomousReviewPolicy PASS (one controlled
test, zero failures/errors), preserving non-quota template failures and incomplete
quota classification. No model call or changed application behavior.
Current T0027-004 complete: every original A scenario already fails a required
turn, giving 0/12; R remains 2/9. Independent agent assessment of generated
Q027-019 turn 1 PASS, turns 3/5 FAIL, with specific reasons/reviewer and no human
approval. Historical outputs/flags/limits/accounting preserved. Overall collection
gap and final qualification failures remain; deferred user review is no blocker.
No partial-feature commit or push.
