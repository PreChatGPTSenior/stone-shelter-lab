# Feature 0017: Markdown Document Indexing

Status: Planned; documentation only. Implementation and runtime qualification
have not started.

## Goal and References

Turn English Markdown reference documents about stone types into searchable
passages in the existing PostgreSQL/pgvector setup. Keep the original source
available independently of the vector projection.

- Shared scope: [LLM Search Discovery](../0015-llm-search-discovery/spec.md#2-markdown-document-indexing).
- Decisions and alternatives: [ADR-0009](../../docs/adr/0009-markdown-document-indexing.md).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Technical plan and task boundaries: [plan.md](plan.md).
- Execution sequence: [tasks.md](tasks.md).
- Initial source: [stone-encyclopedia_en.md](../../stone-rules/stone-encyclopedias/stone-encyclopedia_en.md).

## Scope

Provide explicit synchronous HTTP import with `Idempotency-Key`, complete
replacement of one document's indexed fragments, and removal of its vector
projection. The import endpoint is open:
no authentication, authorization or session ownership is required in this
feature. Do not couple document operations to chat conversation controls.

Index reference knowledge about types, including their personalities and
rituals as facts of the project's fictional world. Do not classify or separate
passages into real and fictional categories. Stone-card embeddings, live
catalog matching, chatbot answer generation, Kafka/outbox processing, automatic
folder watching, UI controls and Russian-language support are outside this
feature. Internal passage retrieval for embedding qualification is in scope;
a public semantic-search endpoint has not been selected.

Persisting raw-Markdown copies, snapshots, source-version archives and restoring
overwritten MinIO contents are explicitly out of scope. Derived passage text,
provenance, source hashes and durable operation records remain in scope.
Performance measurement, speed analysis, latency/throughput acceptance targets
and performance optimization are also out of scope. Functional model/vector
validation and retrieval-quality qualification remain required.

## Configured MinIO Source

The initial source is the fixed object `stone-encyclopedia_en.md` in the dedicated
MinIO bucket `stone-encyclopedia`. Import reads that object internally; the HTTP
request does not upload a file, send Markdown or select a source path.

Use an explicitly defined stable filename-based `documentKey`, initially
`stone-encyclopedia-en`, within the backend source definition. Clients do not
provide documentKey. MinIO is the authoritative document source and is not
rewritten or deleted by index operations. Supply source-location settings through
the established environment configuration convention. The repository Markdown
link is the initial example, not a runtime local-file import endpoint.

## Source and Fragment Lifecycle

Associate imports and replacements with a stable document identity. A repeat
import replaces all fragments belonging to that document; it does not append
another copy. A replacement also removes fragments for sections no longer
present in the new source.

Prepare the complete new fragment set and its embeddings before publishing
the replacement. Until successful publication, keep the previous indexed source
hash, metadata and searchable projection available. Publish the new source
hash and all its fragments atomically; failure must not leave a partial
replacement. Preparation failure or a confirmed publication rollback preserves
the previous working version. Loss of an HTTP response or commit acknowledgement
does not prove rollback: reconcile the durable operation result before any
repeat publication. Removing an indexed document removes its searchable fragments,
without deleting or rewriting the user's original Markdown file.

Keep original Markdown independently of the derived fragments in MinIO only.
Set `sourceVersion` to the SHA-256 digest of the exact imported source bytes;
the same bytes have the same version. Retain source
identity, source version, language, section provenance, applicable stone type
and fragment order. Do not persist an additional raw-Markdown copy or snapshot.
Overwriting the MinIO object does not change the published index until successful
explicit import. Failure preserves the prior projection and its source hash;
it does not restore or guarantee access to earlier raw MinIO contents.

### Retry Identity and Commit Outcomes

The client supplies a new UUID v4 `Idempotency-Key` for each intentional import
or index deletion and
reuses it with the same request after a timeout or lost response. Bind the key
to the operation and internally selected document identity. There is no request
body or client-supplied source selection. Replaying a committed key must not read
new MinIO contents to start another import or classify them as changed request
parameters: a new key explicitly starts a new import of the configured source.
Persist the successful operation result, including the published source version,
in the same transaction as the document and fragments.

For three days after commit, return a committed operation's saved HTTP status
and response without computing embeddings or publishing/deleting again. This
applies to POST and DELETE. This result describes that
operation, not necessarily the document's latest state: a later replacement or
index deletion must not cause an old retry to republish the earlier projection.
Reject reuse of the same key with changed operation/document identity using
409 Conflict. A retry while its original attempt is active receives the same
409 operation-already-running response as other conflicting document operations.
Keys are unique across this index's import and deletion operations; a POST key
cannot be reused for DELETE. After the three-day replay window, retain a minimal
used-key marker indefinitely and reject the expired key with 409, requiring a
new key for a new operation. Index deletion must not remove used-key markers.

If commit acknowledgement is lost, determine the operation outcome from
authoritative PostgreSQL state before repeating changes. An absent result while
the original transaction may still be active is not proof of rollback. Do not
claim that the previous index survived merely because the client saw an error.
Rejection of a new request before execution starts, such as validation or busy
admission rejection, does not consume that new key. A definitively failed started
attempt is terminal; reject a retry of its used key with 409 and require a new key.
Keep minimal terminal used-key state indefinitely; do not add automatic reimport.

On restart, first reconcile commit outcome and verify the prior worker stopped.
Return a retained committed result or reject its expired key. If the stopped
attempt did not commit, mark it failed and require a new key. While execution or
commit outcome is uncertain, do not admit another document mutation or report
rollback as established. Guard mechanics are technical planning work.

### Conflicting Document Operations

Allow only one active import or index-removal operation for this document.
Reject a conflicting request with 409 ProblemDetail whose detail is
`This operation is already running.` Do not queue conflicting requests. A
committed-key replay is a read of a historical result, not a new document mutation.
Finalize guard lifecycle/recovery without assuming that a timeout proves the
previous worker has stopped.

## Markdown Processing

- Use the Markdown heading hierarchy. Each thematic `###` subsection under a
  stone's `##` heading is a separate fragment.
- Include the document title, stone name and topic in the actual text sent for
  embedding, not only in database metadata. For example:
  `Stone Encyclopedia > Basalt > Monthly Care`, followed by the source text.
- Keep each stone's introductory attributes, such as archetype, fragility and
  care demand, in a separate fragment associated with that stone.
- Preserve short subsections as complete fragments. Split oversized sections
  at paragraph or complete list-item boundaries, starting with an approximate
  target of 500 tokens including heading context and overlap. Split long
  paragraphs between whole sentences; do not cut sentences or list items
  arbitrarily. Reject an indivisible block above the qualified model input limit
  with 422 instead of truncating it. Qualify token measurement against the model.
- Do not overlap independent subsections. Use an initial 50-token overlap only
  within further splits of one oversized topic, respecting meaningful boundaries.
  Qualify the initial token settings without claiming a universal optimum.
- Keep rating scales and general care rules as separately scoped shared
  fragments, without assigning them to one stone type. Preserve table headings
  and explanatory text when processing scale tables.
- Split `Quick Comparison` into one fragment per stone row, carrying column
  names and the table's introductory qualification. Preserve the original
  complete table in the MinIO source; do not store a separate raw copy.
- Exclude the opening introduction and `About This Guide` from searchable
  fragments, including the statements describing personalities and rituals as
  fictional. Keep this text in the original source. Do not inject the excluded
  introduction into fragment context.
- Exclude `Contents` from searchable fragments as navigation duplication.
  Preserve original source headings in provenance. Normalize the searchable
  heading context `Fictional Care Ritual` to `Care Ritual`, without changing
  the original Markdown or its section provenance. Do not add a fictional-content
  flag or filter to personality and ritual fragments.
- Exclude `Sources and Editorial Notes` from search and embedding context,
  leaving it in the MinIO source.
- Never access or index `engineering-log/`, directly or through aliases,
  symlinks, copies or attachments. Import design must respect the repository's
  human-only-directory prohibition.

## Embedding and Qualification Approach

Start with local Ollama `qwen3-embedding:0.6b` and 1024-dimensional vectors,
as selected in discovery. Verify the actual model output and passage retrieval
before establishing the pgvector schema. Use compatible document and query
embedding profiles; apply the Qwen retrieval instruction on the query side.
Do not automatically download models or change the host runtime.

Evaluate retrieved source passages against English requests covering exact
topics, paraphrases, personality/ritual knowledge, common rules and comparisons.
Do not benchmark import/query duration or analyze provider timing fields in this
feature. Use the accepted conservative configurable timeout defaults below;
these are waiting limits, not performance promises for every machine. Exact
query fixtures, functional criteria and evidence belong in acceptance.md.

## Resource Limits

Start with configurable limits of 1 MiB (1,048,576 bytes) per source and 2000
generated fragments. Treat an oversized source, excessive fragment count,
invalid source text, no useful passages after exclusions or an indivisible block
above model input capacity as unindexable source (422); publish nothing.
Use these initial configurable timeout defaults without preliminary measurements:

| Boundary | Default |
| --- | --- |
| Establishing a remote connection | 30 seconds |
| Reading the source from MinIO | 5 minutes |
| One Ollama embedding request | 30 minutes |
| Complete import operation | 2 hours |

The overall deadline limits remaining work even when a dependency's own timeout
is longer. Stop progression and request cancellation on timeout; keep ownership
until the worker exits and reconcile any uncertain publication outcome. HTTP
clients and any proxy used for this endpoint must allow the overall import
waiting limit with a response margin. Do not add automatic business-operation
retries. Timeout enforcement is functional verification, not benchmarking.

## HTTP Contract Requirements

Generate the HTTP contract from Spring MVC controllers and shared DTOs through
`/v3/api-docs` and `/v3/api-docs.yaml`; do not create handwritten OpenAPI.

- `POST /api/stone-encyclopedia/index` has no request body and requires a UUID v4
  `Idempotency-Key` header. It imports the internally configured MinIO source;
  there is no client file, Markdown content or documentKey parameter.
- Full replacement targets that same identity and reports success only after
  the complete new projection and operation result have been committed. The
  original request waits for completion; no background job or 202 job contract
  is introduced. Within three days after commit, a retry returns its saved
  status and response.
- Successful import returns a dedicated response DTO with exactly `documentKey`,
  `sourceVersion` and `indexedFragments` in JSON and the generated response schema.
  Nonserialized domain outcome metadata may support controller-owned status
  selection without adding a wire property. Return 201 Created for first creation
  of the index and 200 OK for replacement. Replay preserves the original status.
- `DELETE /api/stone-encyclopedia/index` removes this document's index. Its
  body is empty and it requires a UUID v4 Idempotency-Key with the same three-day
  successful replay and indefinite used-key markers. Return 200 with a dedicated
  identifier-only response `{"id":"stone-encyclopedia-en"}`, including when the
  index is absent. A replay of an earlier successful deletion must not remove a
  subsequently created index.
- Active conflicting operations return 409 ProblemDetail with detail
  `This operation is already running.` Expired keys and changed operation identity
  also return 409, as do retries of definitively failed used keys. Use distinct
  explanatory English details for these conflicts.
- Return 400 for a missing/invalid key or nonempty request body; 404 for a missing
  MinIO object; 422 for an unindexable source; 503 for an unavailable required
  dependency. Shared DTOs, controller/service/repository and mapper boundaries follow the
  constitution; do not introduce an empty body DTO for these bodyless routes.
- Errors use RFC 9457 ProblemDetail through the exception handler; failed
  imports/replacements must not be reported as successful.
- Controllers own the selected HTTP statuses. Document source selection and
  indexing behavior belong outside controllers.

## Delivery Preparation Still Required

Accepted product policies and conservative timeout defaults are recorded above;
source-copy persistence and performance analysis are out of scope. The technical
plan defines persistence, guard/recovery mechanics and task boundaries. Functional
model/token qualification remains the first development prerequisite to schema
implementation; do not claim runtime qualification before executing that task.
The full predeclared retrieval fixture and mandatory controls live in acceptance.md.
Execution remains synchronous; Kafka and a job API are not selected.

Before each implementation task, define its exact writable file allowlist and
permitted focused test selectors. Recording these tasks does not authorize
running them, modifying application code, importing documents, committing or
pushing.
