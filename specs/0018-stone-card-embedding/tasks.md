# Feature 0018 Tasks

Status: T0018-001 through T0018-006 complete. All permitted focused checks passed.
Scope: [spec.md](spec.md). Acceptance: [acceptance.md](acceptance.md).
Exact file allowlists and permitted selectors: [plan.md](plan.md#exact-task-file-allowlists-and-focused-checks).

## Mandatory First Ticket

- [x] T0018-001 — Prepare and check environment readiness for the entire explicitly
  requested batch before executing any implementation ticket. Record the covered
  IDs, tools/runtime pins, command-scoped JAVA_HOME and effective authorized
  endpoints/settings, Docker/pinned database image and required service connectivity,
  installed model/version/digest when needed, expected/observed states, startup
  actions and overall READY/BLOCKED result. Report all stopped application services
  with task impact. Read-only checks and routine startup follow plan.md; no business
  tests, embeddings, imports, model downloads or persistent configuration changes.
  Unavailable required prerequisites block the entire requested batch: identify
  affected ticket IDs/corrective actions and stop before implementation. Recheck
  after correction and before resuming or extending a batch. Writable files:
  this tasks.md and acceptance.md only. Current result: READY for T0018-002 through T0018-006 (2026-10-12).

## Implementation Tickets

- [x] T0018-002 — Define the snapshot/save DTOs, deterministic text/entity mapping,
  identifier response mapping and configurable similarity threshold using existing
  dependencies. Add only the threshold entry to .env.example. Verify with
  StoneCardMappingTest. Depends on fresh T0018-001 readiness.
- [x] T0018-003 — Add the separate pgvector card projection through a new Liquibase
  migration and atomic repository save/upsert. Verify with
  StoneCardEmbeddingPersistenceTest. Depends on T0018-002.
- [x] T0018-004 — Implement the internal save service using the existing embedding
  integration and a separate short transaction service. Verify with
  StoneCardEmbeddingServiceTest. Depends on T0018-003.
- [x] T0018-005 — Implement internal thresholded cosine retrieval, response mapping
  and focused dependency-boundary checks. Verify with StoneCardRetrievalTest and
  StoneCardEmbeddingArchitectureTest. Depends on T0018-004.
- [x] T0018-006 — Verify one basic real-model query in an isolated pgvector database
  and review acceptance against recorded focused evidence. Verify only with
  StoneCardEmbeddingBasicQueryTest. Depends on T0018-005 and fresh model readiness.

Execute only the requested ticket(s), after readiness covers their entire batch.
When a prerequisite implementation ticket is incomplete, report it rather than
silently executing it. After each task, record actual focused verification and
report its outcome in one line. Stop after the requested batch. Do not create
commits or push without explicit authorization. Do not run a full suite, bulk
index the catalog or proceed into lifecycle/chatbot/query-quality features.

Completion: six focused test classes passed (14 tests total). One real-model
query passed at the unchanged 0.5 threshold; no query-quality suite, bulk import,
live catalog mutation, commits or pushes were performed.
