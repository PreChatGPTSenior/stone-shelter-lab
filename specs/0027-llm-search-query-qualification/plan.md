# Feature 0027 Technical Plan

Status: Prepared for future explicitly requested tickets. No environment check,
fixture provisioning, model call, baseline or human answer review has run.

Start with [spec.md](spec.md), [acceptance.md](acceptance.md),
[ADR-0027](../../docs/adr/0027-llm-search-query-qualification.md) and
[tasks.md](tasks.md). Acceptance criteria and literal fixture/scenario/oracle
definitions live only in acceptance.md. These technical preparation choices
are not additional user answers or permission to execute.

## Test and Production Boundaries

Keep production source read-only. Reuse delivered 0024–0026 entrypoints,
request/response contracts, guarded provider transport and actual shared controls.
Use a test-owned fixture helper and one focused qualification test class, with
resources transcribing the approved data. Existing test/serialization/JDBC
capabilities cover the need; no dependency change is planned.

Do not recreate the search algorithm in the oracle. Compare observed IDs,
conditions, route, evidence/contradiction decisions, references, counters and
response properties with reviewed outcomes. Actual vector scores are diagnostic
evidence rather than fabricated deterministic scores. Human-reviewed free text
provides answer-quality evidence independent of production checker verdicts.

Controlled valid drafts/checking and generation failures use test-owned provider
wrappers/overrides at existing seams; coordinate late fixture mutations there.
No public debug route or production test hook. If a delivered seam cannot reach
a required boundary, report the limitation and propose a separately agreed fix.

Apply backend conventions to support code: records only for at most four fields;
larger carriers use private fields/no-argument constructor/accessors. Preserve
existing service/repository/DTO/mapper responsibilities; add no application types.

## Fixture Preparation and Isolation

The resource transcribes approved cards/passages, literal inputs, case keys,
cohorts and mutations. Compare it with acceptance.md before calling models.
Maintain logical-key/generated-ID mapping, seed order and equal admission-time
ties. Do not add a public name/ID/color filter for fixture convenience.

Use dedicated test storage/history identifiers under existing schema and document
identity/profile rules. Require a verified isolated target; never truncate or
seed the user's catalog or replace the production encyclopedia. Cleanup touches
only records/source objects created by this run, including vectors/history.
Actual provider usage/reservations and active permits survive cleanup; fixture
isolation does not exempt model usage from the real shared controls.

Provision compatible real card/document embeddings through existing guarded
capabilities only in the explicitly requested baseline ticket. This is isolated
test preparation, not a readiness business import or unrelated indexing campaign.
Reuse verified compatible unchanged vectors; reset approved catalog/history state
per scenario and before diagnostic repetitions. Never reuse altered conversation
state or delete usage to recover budget.

Set the fixture calendar at the date-interpretation clock seam only. Keep model
deadlines advancing and charge usage to the actual current UTC day. If no separate
calendar seam exists, identify the precise blocker; do not freeze all clocks or
edit production source without separately agreed scope.

## Environment and Effective Settings

Readiness covers the entire explicitly selected batch and reports covered IDs,
expected/observed components, READY/BLOCKED and corrections. Check delivered
0025/0026 contracts/seams, actual service connectivity, isolated source resources
and effective settings. Prepared documentation proves none of these.

Supply authorized values in the agent's command/process environment. Check Maven's
JAVA_HOME, not only java on PATH. Report secrets only as present/missing.
Inherited documented profiles: JDK 23.0.2, Maven 3.9.9, PostgreSQL 18.6 with
pgvector 0.8.6, Redis 8.2.10, approved Ollama 0.40.3, qwen2.5:3b and
qwen3-embedding:0.6b with 1024 dimensions. Verify approved pins, installed model
digests and compatible embedding metadata. Report conflicts with older documents
or observed versions rather than silently changing pins. Do not inspect dependency
manifests or framework configuration for this no-dependency-change feature.

Record effective inherited shared concurrency, 100000-token UTC daily ledger,
8192 context, interpretation/checking 512 output and generation 1024 output,
per-call/turn deadlines, candidate/document limits, source thresholds and
evidence/generation/query-embedding/refresh bounds from 0025/0026. Use approved
baseline values; introduce no alternative setting. The 0026 completion margin
stays inside the original deadline.

Report backend/frontend and other project service states with ticket impact.
Start only existing services required for the selected execution and verify
health/connectivity after startup. Startup is preparation, not a read-only check.
No inference, application test, fixture import, download, persistent configuration
change or missing feature implementation is a readiness workaround.

## Execution and Evidence

Verify fixture transcription/harness without live inference first; then collect
the original baseline with installed models and designated controlled cases.
Human review records actual fixed-output verdicts. Conditional diagnosis is a
separately selected ticket, never an automatic next step after failure.

Write evidence to the exact report paths in tasks.md rather than scanning build
outputs or system logs. The machine-readable report retains:

- Fixture/resource revision/run identity, approved case/turn keys, logical/physical
  IDs, model digest/profile and effective settings without secrets.
- Baseline/diagnostic attempt identity, literal inputs, relevant prior selection,
  interpreted mode/filters/wishes and clarification decisions.
- Bounded retrieved candidates/passages/scores, support/unmatched/contradiction
  decisions, selected order, draft/checker/template outcome and final reference/
  eligibility/refresh facts.
- Actual usage/reservations, admission/call outcomes and functional timeout
  failures without timing distributions, CPU/memory or model-speed analysis.
- Automatic verdicts, displayed answer/links, actual or pending human judgments,
  original scores, diagnostic hypotheses and limitations.

The readable report summarizes evidence, review and status. Baseline collected/
review pending is distinct from final qualification. Fixed denominators and
original scores remain visible alongside repeats. Required incomplete cases or
control failures produce incomplete/blocked status; do not raise limits or bypass
admission to finish. Actual human review cannot be replaced with assumed approval.

The future personal manual test set is deferred under OOS-0027-003. It is neither
a prerequisite nor a replacement for the approved fixed-set human review.

## Diagnosis and Correction Boundary

Localize failure to interpretation, retrieval, evidence, generation/checking,
reference/eligibility or shared controls before choosing an instability hypothesis.
Accepted bounded repetitions start fresh and preserve all outcomes/settings.
Other focused stage checks remain read-only within approved cases/settings and
the selected selector.

If evidence suggests a code/threshold problem, record proposed behavior, exact
files, spec/acceptance impact and focused verification for separate agreement.
No automatic fix, alternative-threshold campaign/adoption, model replacement,
gold relaxation or repeat-until-green. Later agreed corrections may justify new
qualification without erasing original failures; no such work is authorized now.

## Completion and Verification

Use ticket-specific selectors, no full suite or unchanged successful model reruns.
Review retained evidence against acceptance.md, exact file scope and unchanged
public contracts. Record pass/fail/review-pending/incomplete honestly. Prepared
documentation is separate from delivered implementation, qualification and the
deferred manual application acceptance. No commits/pushes or 0028 work.
