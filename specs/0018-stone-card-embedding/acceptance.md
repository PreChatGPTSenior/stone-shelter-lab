# Feature 0018 Acceptance Criteria

Status: T0018-001 through T0018-006 complete. All focused acceptance checks passed
on JDK 23.0.2, pinned PostgreSQL/pgvector and approved Ollama 0.40.3.

## Card Text and Embedding

- AC-001: Card text contains exactly the supplied name, stone type, size and
  biography with English field labels. Identifier, status, admission date, photos
  and encyclopedia/inferred knowledge are absent from embedding input.
- AC-002: Each card yields one vector. Null/empty biography adds no generated
  content; supplied nonempty biography is preserved. No splitting, silent
  truncation or new model-input length restriction is introduced.
- AC-003: Save rejects invalid snapshot fields before provider work, following
  the input constraints in spec.md. Reused Ollama integration provides finite,
  nonzero vectors of 1024 dimensions with the selected model/profile; queries
  use the existing retrieval instruction. This feature does not modify provider
  transport or document-index behavior.

## Save and Search Correctness

- AC-004: Saving stores one projection per stoneId. A second save replaces the
  text, vector and profile together without a duplicate. Concurrent saves preserve
  uniqueness and a complete row, without promising source-version ordering.
- AC-005: Embedding/preparation failure performs no write. A confirmed database
  rollback preserves the prior row. Save reports failure when persistence fails
  and returns an identifier-only response only after successful saving.
- AC-006: Internal search accepts text and a positive result limit and returns
  a non-null matches list of stoneId/score pairs. Scores are cosine similarities,
  ordered descending with stoneId ascending for ties. Only qualifying results
  count toward the limit; an empty index or no qualifying candidates gives an
  empty matches list.
- AC-007: An absent threshold setting uses 0.5. An explicit finite value in
  [-1, 1] overrides it. Invalid supplied values fail explicitly. A score exactly
  at the threshold is included and a score below it is excluded.
- AC-008: Invalid query/limit fails before provider work. Incompatible profiles,
  provider failures and database failures do not become successful empty results.
- AC-009: Persistence and retrieval operate independently of live catalog
  existence/status and do not add HTTP routes, card lifecycle calls, bulk runners,
  Kafka consumers, chatbot integration or index-management APIs.
- AC-010: New persistence uses a new Liquibase changeset; applied migrations
  are unchanged. Services remain independent of controllers, HTTP/JPA query APIs;
  repositories build queries and return entities, and mappers build responses.
  Types with more than four fields are classes using the required constructors
  and accessors. Focused ArchUnit checks cover the new dependency boundaries.

## One Basic Real-Model Check

- AC-011: In an isolated pinned pgvector Testcontainer, save snapshot stoneId
  18001, name `Basalt Buddy`, type BASALT, size SMALL, biography
  `Basalt Buddy is a small basalt stone.` using the real installed model.
  Search `Find the small basalt stone named Basalt Buddy.` with limit 1 and
  the default threshold 0.5. The result contains stoneId 18001 with a finite
  score at least 0.5. The test creates and removes its own projection and never
  indexes the real catalog. A failed check is reported, not fixed by lowering
  the threshold or changing expected results without revisiting the decision.
  This is a basic functional check, not query-quality qualification.

## Verification Boundaries and Evidence

Use only the selectors in plan.md for the requested task. Database checks use
the existing pinned IntegrationTest/Testcontainers setup, never H2 or the live
catalog. Deterministic vectors verify threshold/order/persistence semantics;
only AC-011 needs real embedding calls. Do not add representative query sets,
retrieval benchmarks, quality scores or performance targets here.

Readiness evidence is recorded below. Before any new execution batch, record its covered ticket IDs,
expected/observed component states, preparation actions and overall ready/blocked
result here. Record actual per-task checks and limitations after execution;
do not mark criteria satisfied from documentation alone.

## T0018-001 Readiness Report — 2026-10-12

Coverage: T0018-002 through T0018-006. Overall: READY.

| Component | Expected | Observed | Impact |
| --- | --- | --- | --- |
| Maven JDK / wrapper | JDK 23.0.2, Maven 3.9.9 | Explicit JAVA_HOME=/usr/local/opt/openjdk/libexec/openjdk.jdk/Contents/Home; executable wrapper reports both versions; wrapper properties confirm 3.3.4 | Ready |
| Docker / database image | Reachable daemon, pinned pgvector digest | Docker 29.8.1; pinned digest present | Ready for Testcontainers |
| PostgreSQL / vector | Authenticated connection, 18.6 / 0.8.6 | Read-only authenticated SQL returns both versions | Ready |
| Ollama / embedding model | Approved 0.40.3, selected installed model, 1024 dimensions | HTTP version/tags return 200; qwen3-embedding:0.6b digest ac6da0dfba84a81fdbfbaf330198c33cd77c4cdfc53e8bc50eb581914a15621d, 1024 dimensions | Ready |
| Effective card settings | Authorized endpoint/timeouts/threshold | Commands explicitly supply localhost:11434, PT30S/PT30M; default threshold 0.5 | Ready |
| MinIO | Service health reported | localhost:9000 health returns 200 | Not a direct card dependency |
| Backend / frontend | Report actual configured endpoint states | localhost:8080/5173 unreachable | Stopped; tests use isolated contexts, no live application calls |
| Redis / Kafka | Report service state and impact | No running project containers; no eager connection in inspected chat service/repository wiring | Stopped; not used by focused card tests |

Initial sandbox denied Docker/local-network checks; the authorized read-only
checks passed outside that sandbox. No services needed startup, no embeddings
were generated, and no application data or persistent configuration was changed.
Secrets were not printed. Isolated tests supply database credentials via the
existing Testcontainers service connection; no document source access is needed.

T0018-002: StoneCardMappingTest passed (2 tests); factual rendering, empty/null
biography, mapper null behavior and threshold settings verified.

T0018-003: StoneCardEmbeddingPersistenceTest passed (2 tests) against pinned
pgvector Testcontainers; replacement, rollback, concurrency and independence verified.

T0018-004: StoneCardEmbeddingServiceTest passed (3 tests) after correcting a
mock re-stubbing setup error. Snapshot validation, provider transaction suspension,
separate committed writes, replacement and failure propagation verified.
Readiness recheck before T0018-005: Docker, authenticated PostgreSQL and approved
Ollama version remain reachable; batch gate remains READY.

T0018-005: StoneCardRetrievalTest and StoneCardEmbeddingArchitectureTest passed
(6 tests total); inclusive threshold, ties/limit, empty results, profile/error
handling, query validation and dependency boundaries verified.
T0018-006 readiness recheck: Ollama 0.40.3 and the previously recorded model
digest/1024 dimensions confirmed through version/tags; Docker remains available.

## T0018-006 Final Acceptance Review

StoneCardEmbeddingBasicQueryTest passed (1 test). The exact AC-011 fixture
returned stoneId 18001 at cosine similarity 0.85282516479514 with threshold 0.5,
using the real installed model and an isolated pinned pgvector Testcontainer.
The fixture projection was cleaned up. No live catalog was indexed or modified.

| Criteria | Result | Focused evidence |
| --- | --- | --- |
| AC-001–AC-002 | Passed | StoneCardMappingTest: exact factual text and biography preservation |
| AC-003 | Passed | StoneCardEmbeddingServiceTest input validation; reused provider exercised by basic real-model test |
| AC-004–AC-005 | Passed | Persistence and save-service tests: replacement, uniqueness, rollback, transaction separation, dependency failure |
| AC-006 | Passed | StoneCardRetrievalTest: cosine ranking, tie order, limit and empty results |
| AC-007 | Passed | Mapping settings checks and exact 0.5 inclusion/exclusion retrieval fixture |
| AC-008 | Passed | Retrieval validation, profile mismatch and dependency-error checks |
| AC-009 | Passed | Independent persistence fixture and focused code review: no catalog joins, lifecycle/API/Kafka calls or index-management methods |
| AC-010 | Passed | New Liquibase changeset, focused architecture checks and mapper/DTO review |
| AC-011 | Passed | StoneCardEmbeddingBasicQueryTest, score recorded above |

Six permitted focused test classes passed, 14 tests total. A mock re-stubbing
setup error was corrected before the save-service class passed. Successful
checks were not repeated without changed relevant inputs. No full suite, query
quality evaluation, performance measurements, model download, host version
change, live-database feature migration, commits or pushes were performed.
Future catalog presentation still needs the explicitly deferred existence and
status checks; these internal candidates are not a public search endpoint.
