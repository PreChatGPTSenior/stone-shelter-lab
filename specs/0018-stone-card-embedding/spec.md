# Feature 0018: Stone-Card Embeddings

Status: Documentation prepared; implementation and current environment readiness
have not been verified. Execution requires a separate user instruction.

## Goal and References

Provide backend services and classes that turn factual English stone-card data
into one embedding per card, save it in PostgreSQL/pgvector and support internal
semantic retrieval. Subsequent features will connect these capabilities to card
creation, automatic index updates and chatbot search.

- Shared scope: [LLM Search Discovery](../0015-llm-search-discovery/spec.md#3-stone-card-embeddings).
- Existing embedding capability: [Feature 0017](../0017-markdown-document-indexing/spec.md).
- Decisions: [ADR-0010](../../docs/adr/0010-stone-card-embedding.md).
- Accepted exclusions: [central register](../../docs/out-of-scope-decisions.md#feature-0018-stone-card-embeddings).
- Acceptance criteria: [acceptance.md](acceptance.md).
- Implementation boundaries: [plan.md](plan.md).
- Execution sequence: [tasks.md](tasks.md).

## Card Input and Text

The internal save operation accepts a caller-supplied snapshot containing
`stoneId`, `name`, `stoneType`, `stoneSize` and nullable `biography`. The caller
owns obtaining the snapshot; this feature does not load a stone from the catalog
or verify its current existence or adoption status.

Use only name, type, size and biography in the embedding text. Do not include
the identifier, adoption status, admission date, photos or encyclopedia knowledge.
Keep the existing catalog field constraints: positive persisted identifier,
nonblank name up to 120 characters, supported type/size and biography up to
2048 characters. Do not synthesize biography content. Null or empty biography
contributes no content; whitespace and other supplied biography text are not
rewritten. One card produces one vector, with no chunking or new length limits.
Use the existing provider's validation/failure behavior without silent truncation.

## Embedding and Saving

Reuse the existing local Ollama integration with `qwen3-embedding:0.6b`, finite
nonzero 1024-dimensional vectors and compatible card/query embedding profiles.
Reuse its query instruction, model identity/digest checks and bounded timeouts.
Do not change the document embedding workflow or introduce another provider.

Save exactly one projection per `stoneId`, containing its rendered text, embedding
profile and vector. Saving the same identifier replaces all three together;
it does not append a second record. Return an identifier-only internal save
response after the database save succeeds. Compute the new embedding before
the short database write transaction. A preparation failure or confirmed write
rollback preserves the previous projection. Concurrent saves cannot produce
duplicate records; source-version ordering and automatic lifecycle coordination
belong to the later update workflow, with no ordering guarantee here.

Saving is an explicitly callable supporting capability. It is not invoked by
stone creation/update, startup, HTTP endpoints, Kafka or a bulk indexing runner.
No dedicated index deletion or direct index-record read operation is provided.

## Internal Semantic Retrieval

Accept query text and a positive integer result limit. Return a dedicated
response containing matches with only `stoneId` and cosine similarity `score`.
Sort by score descending, with `stoneId` ascending to make ties deterministic.
Apply the threshold before the limit; return an empty matches list when none
qualify. The limit bounds retrieval and does not imply chat presentation behavior.

The minimum cosine similarity defaults to `0.5`, configured through
`STONE_CARD_SEARCH_MIN_SIMILARITY`. Include scores greater than or equal to the
configured threshold. The setting must be finite and within the cosine range
`[-1, 1]`; an absent setting uses the default, and an invalid supplied setting
fails explicitly rather than silently falling back. This default is an initial
setting, not a universally qualified relevance cutoff.

Do not compare incompatible embedding profiles. An incompatible stored profile
is an explicit dependency/configuration failure, not an ordinary zero-match
response. Do not join the live catalog or filter by adoption status. Returned
IDs are internal candidates; future search integration must validate them before
presenting links. Reject null/blank queries and nonpositive limits before provider
work. Provider and database failures must propagate as failures, not empty results.

## Contracts and Scope

Internal application services use dedicated shared Request/Response DTOs and
mapper boundaries as defined in the constitution. No HTTP route is introduced
or changed, so no new HTTP contract or OpenAPI schema is required. Generated
OpenAPI remains the HTTP source of truth; do not create handwritten OpenAPI.
There is no Kafka implementation or new event contract in this feature.

The accepted out-of-scope decisions are recorded together in ADR-0010 and the
central register. Query-quality evaluation belongs to a separate feature;
functional acceptance here includes only one basic real-model query, alongside
focused correctness checks. No performance measurement or optimization work is
part of this implementation plan.

## Delivery Gate

The first task is environment readiness for the entire explicitly requested
batch. No previous feature's readiness report establishes current readiness.
Each implementation task has an exact writable-file allowlist and focused test
selector in plan.md. Do not execute unrequested tasks, run bulk indexing,
modify application data, create commits or push through this documentation work.
