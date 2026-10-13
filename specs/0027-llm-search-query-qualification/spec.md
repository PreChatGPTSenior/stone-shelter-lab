# Feature 0027: LLM Search Query Qualification

Status: Discussion complete; documentation prepared. The concrete set was
user-approved on 2026-10-13. Runtime readiness, implementation and qualification
results are NOT RUN.

## Goal and References

Qualify representative English search conversations and explanations against a
fixed reviewed set, separately from basic indexing/search functional checks.
Produce evidence of behavior and quality, and diagnose failures before proposing
a separately agreed correction.

- Shared requirements: [LLM Search Discovery](../0015-llm-search-discovery/spec.md).
- Shared decisions: [ADR-0007](../../docs/adr/0007-llm-search-discovery.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical decisions: [plan.md](plan.md).
- Task preparation: [tasks.md](tasks.md).
- Questions, actual answers and decisions: [ADR-0027](../../docs/adr/0027-llm-search-query-qualification.md).
- Candidate behavior: [0025](../0025-semantic-stone-candidate-search/spec.md).
- Answer/reference behavior: [0026](../0026-semantic-search-rag-answers/spec.md).

## Qualification Scope

Use the approved synthetic catalog and type-scoped encyclopedia passages in an
isolated test environment. The set covers exact filters/dates, semantic wishes
and paraphrases, missing evidence, partial/zero matches, contradictions,
clarification, follow-ups/new searches, references, mixed/knowledge-only answers,
late eligibility changes and final-generation failures. Concrete fixture values,
scenario oracles and scoring definitions belong exclusively in acceptance.md.

Natural query cases use installed approved models through existing guarded
capabilities. Controlled race/failure cases use test-only instrumentation without
claiming ordinary generation quality from controlled answers. Do not create
public debug routes or production test hooks. The fixture calendar affects date
interpretation only; real usage accounting and deadlines retain advancing clocks.

Automatic candidate/hard checks use reviewed fixture oracles; the user reviews
fixed-set generated replies against the agreed rubric. Production grounding
verdicts are evidence, not the sole independent oracle. Semantic candidate quality
and ordinary generated-answer quality are assessed separately. A valid selection
template preserves its functional meaning without hiding failed normal generation.

## Evidence and Diagnostic Policy

Preserve original baseline attempts, effective data/profiles/settings and every
accepted bounded diagnostic repetition. Only failed/ambiguous scenarios can be
repeated for an explicit instability hypothesis, from original state. A favorable
repeat does not replace the original score; do not silently change the gold.

Separate interpretation, retrieval, evidence, generation/checking, reference/
eligibility and shared-control causes. A retrieval miss does not prove that the
threshold is wrong. Poor results trigger additional checks and a concrete
correction proposal with behavior, exact files and verification for separate
agreement. No alternate-threshold range, trial campaign or adoption is approved.

Keep existing model admission, actual usage accounting, concurrency and deadlines
for fixture embeddings, baseline and diagnostics. Interrupted/incomplete runs
retain evidence and actual status; they cannot pass using smaller denominators,
fabricated human review or overridden controls.

## Confirmed Scope Boundaries

Accepted exclusions are recorded with matching IDs in ADR-0027 and the
[central register](../../docs/out-of-scope-decisions.md):

- OOS-0027-001: automatic production corrections or threshold adoption.
- OOS-0027-002: performance measurements, benchmarks and speed optimization.
- OOS-0027-003: the user's separately collected personal manual application-
  acceptance set, to be used after implementation. It need not be supplied now;
  the approved fixed-set qualification and its human answer review remain in scope.

## Dependencies

- [0026-semantic-search-rag-answers](../0026-semantic-search-rag-answers/spec.md).

Existing capabilities must pass fresh readiness checks; their documentation or
prior verification is not evidence of current runtime readiness.

## Discussion Completion

No product questions remain open. Future observed failures may need separately
agreed corrections; actual human review requires produced answers. Neither blocks
completing the documentation now. Design approval is not a qualification result.

## Contract and Implementation Preparation

English-only delivery and approved pinned versions/profiles remain unchanged.
No new public route/DTO, Kafka contract, schema, dependency or frontend change is
planned. Generated OpenAPI remains the HTTP source of truth; no handwritten
OpenAPI. Existing AsyncAPI remains authoritative; no Kafka implementation is
part of qualification. Private executable fixtures/reports are test artifacts.

The first ticket establishes readiness for the entire explicitly requested batch;
tasks.md defines exact future writable paths and focused selectors. Delivered
0025/0026 capabilities are required; their prepared documents are not runtime
evidence. Current authorization is documentation only: no ticket execution,
application tests, fixture provisioning, model calls/downloads, business imports,
commits or pushes. Stop after completing 0027; do not begin 0028.
