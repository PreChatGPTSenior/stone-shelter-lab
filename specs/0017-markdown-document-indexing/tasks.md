# Feature 0017 Tasks

Status: All feature tickets T0017-001 through T0017-010 are complete.
The fresh readiness gate covered the entire final batch T0017-004 through
T0017-009. Focused tests passed; full real-source retrieval qualification
passed 20/20 queries, including mandatory controls, using installed Ollama 0.40.3
as explicitly approved by the user. Earlier blocked results remain historical
evidence in acceptance.md. The user authorized continuous execution of all
remaining tickets. No commits or pushes are authorized or were performed.

Requirements: [spec.md](spec.md). Decisions:
[ADR-0009](../../docs/adr/0009-markdown-document-indexing.md).
Acceptance criteria are maintained exclusively in [acceptance.md](acceptance.md).

## Mandatory First Ticket: Environment Readiness

- [x] T0017-010 — Check and report environment readiness before executing the
  remaining feature tickets T0017-002 through T0017-009. This ticket is listed
  first deliberately; existing task IDs and their completion history are preserved.
  Assess all required tools, services, configuration and source resources for
  the entire planned set, rather than declaring readiness from T0017-002 alone.
  Verify JDK 23.0.2 as used by Maven, the executable Maven wrapper, Docker and
  the pinned PostgreSQL/pgvector Testcontainers prerequisites, Ollama 0.40.2,
  installed qwen3-embedding:0.6b and its metadata, the existing repository
  encyclopedia, and MinIO connectivity/access to the configured
  stone-encyclopedia/stone-encyclopedia_en.md object needed by later tickets.
  Verify effective feature environment settings without exposing secrets.
  The agent supplies known, authorized environment values for its own commands,
  including JAVA_HOME and STONE_DOCUMENT_OLLAMA_BASE_URL; a missing inherited
  variable alone is not a blocker when its approved value can be supplied.
  Report unrelated application services separately, including their task impact.
  Output a ready/blocked report covering each ticket's prerequisites; identify
  affected task IDs and corrective actions for every blocker. Do not start
  the remaining tickets if any required prerequisite is unavailable or unverified.
  Recheck after correction and before any later requested execution batch.
  Preparation includes agent-managed startup of Docker and required existing
  project services using their approved configuration, as authorized by the user.
  It also permits provisioning the missing fixed MinIO bucket/object from the
  declared repository encyclopedia under the user's environment-setup
  authorization; preserve any existing object. Record this preparation separately
  from checks; it is not an index import or application implementation.
  Checks are read-only service/health/connectivity, tool/version, configuration
  and source/model metadata checks; no application test selector is permitted.
  Do not generate embeddings, run imports, change persistent configuration,
  download models or require unimplemented feature endpoints. Service startup
  is permitted preparation and must be recorded separately from read-only checks.
  Functional model/input/retrieval qualification remains T0017-002.
  Writable files: this tasks.md and its sibling acceptance.md, only to record
  actual readiness evidence and completion. No application files may be changed.
  Progress: read-only preflight executed at 2026-10-12 09:15 UTC; result BLOCKED.
  Explicit command-scoped JDK/endpoints/configuration, Maven, Docker, the pinned
  pgvector image, repository source and Ollama/model metadata passed their checks.
  That initial check was blocked by MinIO/source availability.
  Update at 2026-10-12 09:31 UTC: preparation and recheck completed; result READY.
  All six existing project containers are running/healthy; backend/UI/MinIO and
  Ollama endpoints return 200. The missing fixed bucket/object were provisioned
  from the approved source and authenticated object HEAD returns 200.
  No later ticket or application test was executed.
  Later continuous-execution update: the Docker API regressed during T0017-003
  verification. Automatic Desktop startup/restart and recovery of confirmed
  stalled Docker processes briefly restored HTTP 200, but the engine stopped
  again. This readiness gate is reopened; further implementation is blocked.
  See the [preparation and recheck report](acceptance.md#t0017-010--environment-preparation-and-recheck-2026-10-12).
  Resume recheck, 2026-10-12: BLOCKED for the next task T0017-003.
  Docker Desktop was started and the existing MinIO/PostgreSQL containers
  restored; authenticated source HEAD and database readiness passed.
  Ollama reports 0.40.3 instead of the approved 0.40.2. No implementation
  or test was resumed; see the latest acceptance readiness report.
  Subsequent user instruction approves using installed Ollama 0.40.3 without
  updating the runtime. Fresh readiness for the requested continuation batch
  T0017-003 passed; earlier blocked results remain historical evidence.
  Fresh batch readiness for T0017-004 through T0017-009: READY.

## Preparation

- [x] T0017-001 — Prepare the technical plan and execution tickets with the
  accepted policies as inputs: prepare the technical plan, persistence layout,
  concurrency/recovery mechanics, configuration and dependency-compatibility
  preparation. Raw-Markdown copies/snapshots/archives are explicitly out of scope;
  the MinIO source is read internally and sourceVersion is a content hash.
  Preserve the accepted UUID-v4 POST/DELETE retry contract, permanent used-key
  markers, terminal failure/restart policy, resource limits and editorial exclusions.
  The full 20-query fixture and mandatory controls are already in acceptance.md.
  Record exact writable file allowlists and permitted focused test selectors
  for every implementation task. Completed as documentation preparation only;
  no application code or runtime operation was executed.
- [x] T0017-002 — Qualify local qwen3-embedding:0.6b before schema implementation:
  verify actual dimensions, finite vectors, query instructions and consistent
  document/query profiles with representative source passages. Implement/qualify
  the minimal provider adapter using existing libraries, and verify model input
  capacity/token accounting with the agreed timeout defaults. Record functional
  evidence only; no duration benchmark or provider-timing analysis.
  Model download or host changes require separate authorization;
  do not assume availability or readiness.
  Progress: provider adapter implemented; StoneDocumentEmbeddingTest passed
  27 tests on JDK 23.0.2, including missing-model and changed-digest checks.
  StoneDocumentEmbeddingQualificationTest failed at
  the model-availability preflight (404); vector/input/retrieval qualification
  is pending. The model has since been installed and its metadata checked;
  Update: functional qualification passed with finite 1024-dimensional vectors,
  one consistent installed-model profile, six expected passages ranked first,
  provider token counts recorded and oversized input rejected without truncation.
  See [execution evidence](acceptance.md#execution-evidence).

## Implementation Sequence

- [x] T0017-003 — Add new Liquibase migrations and document/fragment persistence
  for the qualified pgvector profile, source hashes/provenance and complete
  projection publication with durable request identity/results and permanent
  used-key markers. Do not persist raw-source copies, snapshots or archives,
  and do not modify existing applied migrations.
  Progress: new migration, projection/fragment/operation entities and JDBC
  persistence code prepared. StoneDocumentIndexPersistenceTest could not complete
  because Docker first returned 503, then the restarted Testcontainer database
  became unreachable during application-context initialization. No successful
  persistence verification or completion is claimed; do not start T0017-004
  until the environment is stable and this focused test passes.
  Resume result: StoneDocumentIndexPersistenceTest passed all five tests on
  JDK 23.0.2 with the pinned PostgreSQL/pgvector Testcontainer. The repository
  now declares its invalid-vector IllegalArgumentException so Spring preserves
  that API exception instead of translating it. Tests were not modified.
- [x] T0017-004 — Implement deterministic Markdown processing: heading context,
  stone attributes, topic fragments, shared scales/rules, comparison rows,
  introduction/Contents/editorial exclusion, Care Ritual context normalization
  with original heading provenance, whole-sentence long-paragraph splitting,
  initial 500-token target/50-token intra-topic overlap and model-limit rejection.
- [x] T0017-005 — Integrate the qualified embedding provider for generated
  passages and read the configured stone-encyclopedia_en.md object from the
  stone-encyclopedia MinIO bucket. Retain profile/source metadata and handle
  source-read or generation failure without publishing a partial projection.
  Hash exact source bytes, enforce configurable source/fragment limits and keep
  any raw source read transient; no persistent Markdown copy is authorized.
- [x] T0017-006 — Implement complete document import/replacement publication
  and index removal using the prepared contract, services/repositories and
  required mappers. Commit projection and successful operation result atomically;
  replay retained committed requests without regenerating embeddings, publishing
  or deleting again; reject changed operation identity and prevent duplicate attempts.
  Preserve the previous working version on preparation failure/confirmed rollback;
  reconcile uncertain commit outcomes, apply three-day successful replay/expired-key
  rejection/permanent markers, terminal failure and restart reconciliation without
  automatic reimport. Do not restore raw MinIO source; allow no parallel mutations.
- [x] T0017-007 — Expose the finalized open HTTP operations through controllers
  and shared DTOs, including synchronous Idempotency-Key binding, replay/conflict
  and active-attempt responses, exception-handler translation and generated OpenAPI.
  Use bodyless POST/DELETE /api/stone-encyclopedia/index, the selected three import
  response fields, 201/200 creation/replacement, identifier-only 200 deletion
  including absent index, original replay statuses and agreed 400/404/409/422/503 errors.
- [x] T0017-008 — Provide internal semantic passage retrieval for qualification;
  execute the finalized retrieval-quality fixture against the imported encyclopedia.
  Do not measure import/query speed, impose performance targets, add a public
  search endpoint or integrate chatbot answers.

## Final Verification

- [x] T0017-009 — Verify the feature against acceptance.md with only the
  authorized focused checks: processing/provenance, generated HTTP contract,
  repeated import, removed sections, failure-safe publication, deletion and
  retrieval qualification, normalized ritual context, lost-response replay and
  uncertain-commit reconciliation, fixed MinIO source, retention/expired-key
  rejection and parallel-operation conflicts, raw-copy exclusion, content hashes,
  UUID-v4 DELETE replay, source limits,
  pre-admission key reuse, failed-key rejection, restart recovery and mandatory
  character/ritual/comparison controls. Record evidence and limitations;
  do not start stone-card indexing or another feature.

After each executed task, run only its permitted verification, record evidence
and report the result in one line. Runtime tasks remain blocked on missing
file allowlists or test selectors rather than permitting repository-wide edits
or a full test suite.

The environment ticket's writable files and permitted read-only checks are
defined inline above. Implementation file allowlists and focused test selectors
are defined per task in [plan.md](plan.md#task-file-allowlists-and-focused-checks). Read that task's
boundary before editing or testing; code changes beyond it require a separately
scoped instruction. No feature ticket remains pending. Any future requested
implementation batch requires a fresh readiness check and its own authorization.

Performance analysis, speed requirements, timing measurements and optimization
are out of scope. Waiting limits are configurable defaults: 30 seconds to connect,
5 minutes to read MinIO, 30 minutes per embedding request and 2 hours per import.
Functional dimension/retrieval, deadline/cancellation and ownership checks remain.
