# Feature 0027 Tasks

Status: Prepared tickets, all unexecuted. Fixture-design approval does not
authorize execution, application tests, model calls, commits or pushes.

- [ ] T0027-001 — Establish/report readiness for the entire explicitly requested
  batch and required delivered search/RAG capabilities.
- [ ] T0027-002 — Prepare approved fixture transcription and isolated test-only
  harness; verify without live inference. Depends on T0027-001.
- [ ] T0027-003 — Prepare compatible isolated embeddings and collect the original
  real-model baseline once. Depends on T0027-001/002.
- [ ] T0027-004 — Record actual user review of fixed-set answers and original
  scoring. Depends on T0027-001/003; human verdicts required.
- [ ] T0027-005 — Diagnose failed/ambiguous baselines with bounded explicitly
  selected repetitions and correction proposals. Conditional; depends on
  T0027-001/003 and relevant review evidence from T0027-004.
- [ ] T0027-006 — Review scope, acceptance and evidence; record final status.
  Depends on T0027-001/003/004 and any requested T0027-005 evidence.

## Exact Scope and Path Notation

Read constitution/local instructions, [spec.md](spec.md), [acceptance.md](acceptance.md),
[plan.md](plan.md) and [ADR-0027](../../docs/adr/0027-llm-search-query-qualification.md).
Before Java work read backend AGENTS_CODE_CONVENTION.md. Execute only requested
tickets; dependency ordering does not authorize subsequent tickets or missing
0025/0026 work. Do not automatically diagnose after a baseline failure.

Exact paths below are relative to repository root:

- T = stone-shelter-api/src/test/java/lab/stoneshelter
- R = stone-shelter-api/src/test/resources
- F = specs/0027-llm-search-query-qualification

Every ticket may additionally update only F/tasks.md, F/acceptance.md and
docs/adr/0027-llm-search-query-qualification.md for factual execution evidence.
This common allowlist cannot change approved requirements/oracles/exclusions.
Production source and unlisted files remain read-only. No wildcard write scope,
dependency/framework configuration, migration, frontend or root README.md edits;
no engineering-log access. Future names are not files to create now.

## T0027-001 — Requested-Batch Readiness

Writable: common evidence documentation only.
Verification: targeted read-only version/configuration/health/connectivity checks;
routine required existing-service startup is separately reported preparation.
No inference, application tests, fixture provisioning, business imports, downloads
or persistent configuration changes as readiness actions.

Check every selected ID's requirements: Java selected by command-scoped
JAVA_HOME/Maven, installed models/profiles, effective controls/endpoints, isolated
catalog/document/history target and actual usage ledger. Verify delivered required
0025/0026 seams/contracts and existing test support. Later standalone tickets
require predecessor evidence; within a selected sequential batch identify producer
tickets and enforce their completion before consumers. Do not claim future
artifacts already exist.

Report covered full IDs, expected/observed states, preparation versus checks,
READY/BLOCKED, affected tickets and corrections. Report application service states
including stopped backend/frontend with impact; start only needed existing
services and recheck connectivity. Missing implementation remains blocked rather
than being executed here. Supply known authorized values in the agent-controlled
environment; secrets are present/missing only. Preserve real usage/admission state.

## T0027-002 — Fixture Transcription and Test-Only Harness

Writable, in addition to common evidence documentation:

- T/StoneSearchQueryQualificationTest.java
- T/StoneSearchQualificationFixture.java
- R/search-query-qualification.json

Selector: `StoneSearchQueryQualificationTest#verifyFixtureAndHarness`.

Transcribe only approved acceptance fixture/scenario definitions. Add isolated
setup/cleanup, generated-ID mapping, independent automatic checks and report
serialization without production changes. Prepare controlled race/failure seams
and separate fixture calendar handling while keeping actual accounting/deadlines.
Do not clone the search implementation into expected gold.

The focused harness check verifies transcription, case/turn/cohort completeness,
isolation and report classification with controlled doubles and no live inference.
It cannot establish semantic/explanation quality. Use only isolated local test
fixtures, no import/mutation of user data. An unavailable required seam is a
reported blocker with a separately agreed fix, not permission to widen source scope.

## T0027-003 — Real-Model Original Baseline

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Selector: `StoneSearchQueryQualificationTest#qualifyApprovedBaseline`.
Test/harness/resources from T0027-002 are read-only for this execution ticket.

Verify isolated targets, prepare approved embeddings through existing guarded
capabilities and collect one original attempt per approved scenario. Natural
queries use installed models; designated controlled cases use test-owned seams.
Reset catalog/history per scenario while preserving compatible vectors/real usage.

Retain every case/turn, outcome, effective configuration/profile, usage, stage
evidence, displayed reply and automatic verdict; mark human review pending.
Outages/exhaustion/unexercised cases produce incomplete status, not smaller
denominators, overridden controls, deleted usage or automatic retries. Preserve
partial evidence before stopping; do not repeat unchanged successful cases.

## T0027-004 — Human Fixed-Output Review and Scoring

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Verification: inspect original evidence and record actual user review;
no model call or application test.

Present collected generated replies and the approved rubric. Record actual
verdicts/reasons/reviewer/date and pending items; never fabricate human approval
or replace it with a production checker. Score original outcomes using acceptance
cohorts, preserving template/incomplete distinctions.

This reviews the fixed set, not the deferred personal set. Do not request that
future set or claim post-implementation application acceptance. If verdicts are
not supplied, record review pending and stop; no final answer-quality pass.

## T0027-005 — Conditional Bounded Diagnosis

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Selector: `StoneSearchQueryQualificationTest#diagnoseApprovedCases`, with explicit
failed/ambiguous Q027 case IDs and hypotheses in the agent-controlled test
environment. The harness rejects absent/unapproved IDs, successful-case reruns
and attempts beyond the accepted bound. Test/resource code remains read-only.

Inspect retained stages before choosing a hypothesis. If explicitly requested,
repeat only selected failed/ambiguous cases from original state with unchanged
profiles/settings within the accepted bound. Preserve all attempts/original scores
and actual controls/usage; classify interruptions honestly.

Evidence-supported correction proposals state behavior, exact files, spec/
acceptance implications and focused checks for separate agreement. No production
fix, alternate-threshold trial/adoption, model replacement, gold relaxation or
benchmark is authorized by this ticket.

## T0027-006 — Final Evidence and Acceptance Review

Writable, in addition to common evidence documentation:

- F/qualification-results.json
- F/qualification-report.md

Verification: focused read-only artifact/scope/acceptance review; no application
test, full suite or repeated baseline.

Compare actual evidence with acceptance.md and exact selected scope. Record
pass/fail/review-pending/incomplete and profile/configuration limitations, never
unobserved readiness or behavior. Keep separately agreed correction work pending
rather than implementing it. Explain conditional diagnostics omitted as unnecessary
or unrequested. Distinguish fixed qualification from personal manual acceptance.

Stop after the requested ticket/batch. No commit, push, extra ticket or 0028 work.
