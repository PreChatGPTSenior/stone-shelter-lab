# ADR 0009: Markdown Document Indexing and Passage Boundaries

Date: 2026-10-12

Status: Accepted. Product policies and the execution plan are recorded;
functional model qualification, implementation and the focused acceptance audit
have passed, including the full predeclared retrieval fixture.
Performance analysis is explicitly out of scope.

## Functional Qualification Evidence

T0017-002 passed its separately selected installed-model qualification on
2026-10-12 using JDK 23.0.2 and Ollama 0.40.2. qwen3-embedding:0.6b produced finite
1024-dimensional document/query vectors with installed digest
ac6da0dfba84a81fdbfbaf330198c33cd77c4cdfc53e8bc50eb581914a15621d. All six
representative queries ranked their predefined source first among six passages;
the full 20-query encyclopedia evaluation remains T0017-008. Model metadata
advertises a 32768-token context and an oversized input was rejected with
truncate=false. Estimated/provider token counts and limitations are recorded
in the feature's acceptance.md; no timing measurements were taken.

The full T0017-008 pipeline subsequently passed on user-approved installed
Ollama 0.40.3 without updating the runtime. It read the fixed MinIO encyclopedia
and published 127 derived fragments into the isolated pinned test database.
All twenty predeclared queries met their relevant groups within the top five,
including Q03/Q04/Q09 and both comparison queries. Exact results and the final
AC-001 through AC-026 audit are recorded in acceptance.md. No speed measurement,
raw-source archive, working-database import, commit or push was performed.

## Context and Authority

Feature [0017](../../specs/0017-markdown-document-indexing/spec.md) follows the
Markdown-indexing scope in [0015](../../specs/0015-llm-search-discovery/spec.md)
and [ADR-0007](0007-llm-search-discovery.md). The initial encyclopedia contains
ten stone types, repeated topic subsections, shared rating scales, a comparison
table and general care rules. It also contains personality and ritual text.

The user first requested discussion only, then selected lifecycle and HTTP
behavior, accepted the recommended passage-processing and evaluation approach,
and instructed that alternatives and decisions be recorded in an ADR with
feature requirements and tasks. This authorizes documentation only; no code,
model invocation, imports, tests, commits or pushes have been performed.
Acceptance criteria live exclusively in
[0017 acceptance.md](../../specs/0017-markdown-document-indexing/acceptance.md).

## Discussed Alternatives

| Choice | Advantages discussed | Limitations and outcome |
| --- | --- | --- |
| One fragment for an entire stone type | Simple; complete description available together | Mixes geology, care, colors and personality in one vector; not selected |
| Fixed-size fragments | General-purpose processing independent of document layout | May break coherent sections, lists and tables; not selected as the primary strategy |
| One fragment per thematic Markdown subsection | Precise topic retrieval and direct section provenance | Needs explicit parent context; selected with heading context in embedding text |
| Merge adjacent subsections | More context, such as daily and monthly care together | Less precise topic boundaries and additional merge rules; not selected initially |
| HTTP endpoint, command or another manual interface | All permit explicit operator-triggered refresh | Open HTTP import endpoint selected; command/other interfaces not selected |
| Synchronous import or background jobs | Direct completion versus decoupled processing | Synchronous import with Idempotency-Key selected; no Kafka/job mechanism selected |
| PUT with version checking | Recover by reading the document and safely repeating replacement | Can repeat embedding work and needs protection against later changes; not selected as the retry-recovery mechanism |
| Synchronous import with Idempotency-Key | Recover the result of a specific attempt without repeating a committed publication | Requires durable request/result records and a retention policy; selected |
| Background operation with a status endpoint | Suitable for long-running work and status polling | Adds durable job execution/recovery and still needs retry identity; not selected |
| Document identity based on a path or a separate stable identifier | Both can associate replacement with an existing source | Explicit filename-based documentKey selected; stone-encyclopedia-en identifies the initial document internally |
| Upload a file or send Markdown in JSON | Both send content through the import request | Neither selected: the endpoint reads a configured MinIO object internally |
| Drop an expired key or retain a minimal used-key marker | Less state versus protection from late retries | Retain a marker and reject the expired key with 409; do not treat it as a new import |
| Queue or reject conflicting document operations | Eventual execution versus immediate feedback | Reject with 409 and an operation-already-running message; no queue |
| Separate or exclude fictional sections | Could distinguish physical facts from invented behavior | Rejected for this project: personalities and rituals are ordinary world knowledge |
| Replace incrementally or append new fragments | Less replacement work | Old/duplicate or partial content can remain; complete replacement selected |
| Remove the old index before processing the replacement | Simpler destructive sequence | Failure loses the working projection; retain the old version until atomic publication |
| Index or exclude the introductory fictional disclaimer | Full-source retrieval versus consistent world framing | Exclude the opening introduction and About This Guide from search; preserve the original source |
| Preserve Fictional Care Ritual in embedding context or normalize it | Exact source label versus the accepted world framing | Use Care Ritual in searchable heading context; preserve the original heading in source provenance |
| Keep an indexed raw-Markdown snapshot or use MinIO alone | A snapshot could preserve the exact earlier source after overwrite | Snapshot/copy persistence and source archives are out of scope; MinIO remains the sole raw source |
| Source hash or storage-specific version identifier | Content identity versus storage-dependent identity | SHA-256 of the source bytes selected for sourceVersion |
| Keep or expire minimal used-key markers | Permanent rejection versus bounded marker storage | Keep markers indefinitely, including after index deletion |
| Apply retry identity to DELETE or rely on repeated deletion | Preserve a specific deletion result versus operating on current state again | Apply Idempotency-Key to DELETE as well; old retries must not delete a newer index |
| Index or exclude Sources and Editorial Notes | Full-source coverage versus excluding editorial world framing | Exclude from retrieval; leave the MinIO source untouched |
| Measure speed before selecting timeouts or use conservative defaults | Measured tuning versus avoiding performance analysis now | Conservative configurable defaults selected; benchmarking and speed requirements are out of scope |

## Accepted Decisions and Rationale

1. Use explicit HTTP import without authentication or authorization in this
   feature. Automatic folder watching remains excluded. Import transport does
   not imply Kafka, background jobs or a public retrieval endpoint.
2. Replace all fragments of one document together and avoid duplicates on
   repeated imports. Prepare the new projection before atomic publication;
   preparation failure or confirmed rollback leaves the previous working
   version available. Lost acknowledgement after commit is an uncertain result,
   not evidence of rollback.
3. Split by stone type and thematic subsection. Include document title, stone
   name and topic in the embedding input so passages retain meaning when
   detached from their parent document. Keep introductory stone attributes
   separately and preserve short subsections intact.
4. Split oversized subsections at meaningful text boundaries, starting around
   a 500-token target including context. Split long paragraphs between whole
   sentences; use an initial 50-token overlap only within a split topic. Do not
   overlap independent topics. Reject an indivisible block above the qualified
   model input limit rather than truncating it.
5. Keep shared scales/rules scoped as shared information. Represent each quick
   comparison row with column names and table context; the original complete
   table remains in the MinIO object, without an additional saved copy.
6. Treat geological properties, characters and rituals as equally indexable
   knowledge of the fictional world. Exclude the opening guide introduction
   and its fictional disclaimer from search and fragment context. Exclude the
   table of contents as navigation duplication and Sources and Editorial Notes
   as editorial context. Do not rewrite the source or
   introduce reality/fiction classification. Use `Care Ritual` instead of
   `Fictional Care Ritual` in searchable heading context, while preserving the
   original heading in source provenance.
7. Qualify the selected local embedding model with source-passage retrieval,
   including paraphrases, world knowledge, shared sections and multi-stone
   comparisons. Functional vector and retrieval checks remain required.
   Speed measurements and numerical performance requirements are excluded by
   the later accepted scope change. Criteria and fixtures belong in acceptance.md.
8. Execute imports synchronously with a client-supplied `Idempotency-Key`.
   Publish the document version, fragments and successful operation result in
   one PostgreSQL transaction. Replay the saved result for the same key/request;
   do not regenerate embeddings or publish again after committed success.
   Reject changed operation identity under the same key with 409. An active
   attempt must not start a second concurrent attempt for its retry. The import
   has no body; changes to MinIO content are not changed HTTP parameters and do
   not turn a retained committed key into a new attempt.
9. Resolve lost HTTP responses and uncertain commit outcomes using durable
   operation state. A committed projection remains published; an HTTP failure
   does not restore the old version. Request identity distinguishes a retry
   from a new intentional import. After restart, first reconcile authoritative
   commit state and confirm the previous attempt stopped. Keep confirmed success;
   mark a stopped, uncommitted attempt failed without automatic reimport.
10. Keep the successful operation's status and response for three days after
    commit. After that window, retain a minimal used-key marker and reject a
    retry with 409, requiring a new key for a new import. Keep minimal used-key
    markers indefinitely, including after index removal.
11. Use the dedicated MinIO bucket `stone-encyclopedia` and fixed object
    `stone-encyclopedia_en.md` as the source. The backend determines the stable
    `documentKey`, initially `stone-encyclopedia-en`, from its source definition;
    clients do not submit a key, file or Markdown body. They still supply the
    agreed `Idempotency-Key` header. MinIO is the sole raw document source;
    persisting raw-source copies, snapshots or archives is out of scope.
12. Start import with `POST /api/stone-encyclopedia/index`, without a body.
    Return `documentKey`, `sourceVersion` and `indexedFragments`: the document
    identity, published source version and indexed-fragment count. Return 201
    for first creation and 200 for replacement. A retained committed-key replay
    returns its original status and response even if the index later changed.
13. Remove the index with `DELETE /api/stone-encyclopedia/index`. Do not alter
    the MinIO source. Allow only one active import or index-removal operation
    for this document. Reject a conflicting request, including an active import's
    retry, with 409 ProblemDetail and detail
    `This operation is already running.` Do not queue conflicting operations.
14. Use UUID v4 Idempotency-Key values, unique across import/deletion operations
    for this index. Reject a key reused for another operation with 409. DELETE
    requires the same retry mechanism and three-day successful replay as import.
    Return 200 with an identifier-only response even when the index is absent;
    response requirements belong in the feature specification.
15. Rejection before starting a new attempt does not consume its key. A started
    attempt that fails definitively requires a new key for another attempt;
    never silently rerun a failed or recovered unfinished attempt under its old key.
16. Use SHA-256 of the imported source bytes as sourceVersion. Keep only derived
    passages and metadata in the projection, plus durable operation state/results.
    Failure-safe replacement protects the old index and its source hash, not a
    restorable earlier Markdown version after an external MinIO overwrite.
17. Use initial configurable source/fragment limits and the agreed error policy
    defined in the specification. Use conservative configurable waiting limits:
    30 seconds to connect, 5 minutes to read MinIO, 30 minutes per embedding
    request and 2 hours per import. These are initial defaults, not a guarantee
    for all hardware. Do not benchmark speed or make it an acceptance requirement.
18. Complete the predeclared English retrieval fixture and require the selected
    character, ritual and comparison control cases in addition to aggregate
    quality. Query definitions and all acceptance thresholds remain exclusively
    in acceptance.md.
19. Keep the existing MinIO SDK, OkHttp and JSON support; plan no new library.
    Use PostgreSQL vector columns with repository-owned JDBC queries. Keep remote
    I/O outside long database transactions and publication/result persistence
    inside one short transaction. The technical plan records exact task files,
    focused checks and the ownership/recovery mechanism.
20. Treat timeout enforcement as functional correctness. A caller timeout is
    not proof that a worker stopped or a transaction rolled back. Retain active
    ownership through worker exit and reject stale publication. HTTP client/proxy
    waiting limits must accommodate the chosen synchronous import deadline.

## Consequences and Remaining Preparation

The first delivery builds a document vector projection rather than card
embeddings or chatbot RAG answers. Every passage retains source provenance.
Excluding introductory/editorial material affects search only; index operations
leave the MinIO object untouched. Its owner may replace or remove it independently;
the feature does not retain or restore earlier raw source versions. Preparing a
complete replacement requires holding or staging derived passages before atomic
publication, not persisting a raw-Markdown snapshot.

Import/deletion routes, the fixed MinIO source, empty import body, successful
response fields/statuses, three-day successful replay and conflict rejection
are selected, together with key validation/indefinite markers, failed-attempt
recovery, DELETE replay, content-hash versions, raw-copy exclusion, initial
resource limits and editorial exclusion. The
[technical plan](../../specs/0017-markdown-document-indexing/plan.md) now defines
implementation boundaries and focused selectors. Performance analysis, source
copies and raw-source recovery are excluded. Remaining execution starts with
functional model qualification and then the planned implementation tasks.
No inference quality or runtime behavior is
claimed verified by the user's acceptance of these decisions.

## Primary References Used in Discussion

- [Microsoft: chunking documents](https://learn.microsoft.com/en-us/azure/search/vector-search-how-to-chunk-documents):
  structural boundaries, heading context and corpus-specific size/overlap tuning.
- [Anthropic: contextual retrieval](https://www.anthropic.com/engineering/contextual-retrieval):
  preserving a passage's meaning outside its original document. Deterministic
  heading context is selected here; LLM-generated contextualization, BM25 and
  reranking are not part of this feature.
- [Qwen3-Embedding-0.6B model card](https://huggingface.co/Qwen/Qwen3-Embedding-0.6B):
  embedding dimensions and query-side retrieval instructions, not proof of local
  Ollama qualification.
- [AWS: making retries safe with idempotent APIs](https://aws.amazon.com/builders-library/making-retries-safe-with-idempotent-APIs/):
  caller-provided request identity, atomic mutation/result recording and handling
  mismatched parameters and late retries.
- [Stripe: idempotent requests](https://docs.stripe.com/api/idempotent_requests):
  saved-result replay after connection failure; its retention settings are not
  adopted automatically for this project.
- [RFC 9110: idempotent methods](https://www.rfc-editor.org/rfc/rfc9110.html#section-9.2.2):
  the PUT alternative; [If-Match](https://www.rfc-editor.org/rfc/rfc9110.html#section-13.1.1)
  addresses accidental overwrites by concurrent updates.
- [Microsoft: asynchronous request-reply](https://learn.microsoft.com/en-us/azure/architecture/patterns/asynchronous-request-reply):
  the unselected background-operation/status alternative.
- [AWS: timeouts and retries](https://aws.amazon.com/builders-library/timeouts-retries-and-backoff-with-jitter/):
  finite remote-call waiting limits and uncertain side effects; the numeric
  defaults above are project choices, not measured or prescribed by AWS.
- [Ollama embedding API](https://docs.ollama.com/api/embed),
  [MinIO Java object reading](https://docs.min.io/aistor/developers/sdk/java/api/#getobjectgetobjectargs-args)
  and [PostgreSQL nonblocking advisory locks](https://www.postgresql.org/docs/current/functions-admin.html#FUNCTIONS-ADVISORY-LOCKS):
  technical API references used for the execution plan, not runtime qualification.
