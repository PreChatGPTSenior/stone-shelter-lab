# Feature 0024: Exact LLM Search

Status: Implemented; focused acceptance verified. See tasks.md and acceptance.md for evidence.

## References

- Feature decisions: [ADR-0024](../../docs/adr/0024-exact-llm-search.md).
- Shared discovery: [0015](../0015-llm-search-discovery/spec.md).
- Acceptance criteria: [acceptance.md](acceptance.md), exclusively.
- Technical decisions: [plan.md](plan.md).
- Exact execution boundaries: [tasks.md](tasks.md).

## Requirements

### Filter Vocabulary and Visibility

- Support stone type, stone size, availability and admission date. General
  name and ID filter additions were not selected; conversation/page references
  remain supported as described below.
- Permit multiple selected values within a field group, combined with OR.
  Combine different field groups with AND. Represent admission dates as
  ranges with an inclusive start and exclusive end, interpreted in UTC;
  combine multiple ranges with OR. A request for 1 October covers the start
  of 1 October through, but not including, the start of 2 October.
- Interpret today and yesterday using the current UTC date. A month with a
  specified year covers that entire month. Ask for the year when an explicit
  calendar date such as 1 October omits it; do not guess the year.
- Restrict returned stones to AVAILABLE. Availability conditions cannot
  expose RESERVED or ADOPTED stones.
- A request for all available stones without a narrowing filter must not
  execute a catalog search or display an unfiltered selection. Ask at least
  two specific clarification questions instead. Availability alone does not
  qualify as a narrowing filter.
- This clarification rule supersedes the earlier proposal to execute an
  explicit filterless request such as "Show all available stones".
- For filtered searches, preserve the agreed ordering by admissionDate
  descending with id ascending for ties, present at most five stones, explain
  additional matches and ask refinement questions when more exist, and use an
  absence-only response when no stones match. Use the exact database match
  count, not only the presented-list size.
- Check current existence and availability of presented stones against the
  catalog rather than treating historical references as proof of eligibility.

### Unsupported Wishes and Follow-ups

- Do not silently ignore unsupported requirements such as color or intended
  use. Explain the available exact-search conditions and request clarification;
  semantic interpretation belongs to feature 0025.
- Care and geological explanations are outside this exact-search stage and
  belong to the later answer feature 0026.
- A follow-up such as "now only small ones" retains the earlier type while
  replacing the size constraint. An explicit new-search request clears the
  earlier search constraints.
- Resolve references such as "the second stone" from stored ordered results
  and "this stone" from the submitted page context. These references are not
  general new name/ID filter capabilities.
- Use the latest presented list for ordinal references. If a reference is
  ambiguous, deleted or no longer AVAILABLE, explain and request clarification
  without automatically substituting another stone.

### Interpretation and Response Construction

- Initially use a structured model decision followed by backend validation
  and bounded catalog operations. Do not execute model-generated SQL or let
  the model call tools in this feature.
- Use the selected Spring AI 2.0.1 integration behind a provider interface,
  initially with local Ollama and qwen2.5:3b.
- Use SEARCH, CLARIFY and UNSUPPORTED as the structured interpretation actions.
  SEARCH supplies validated criteria for backend execution; CLARIFY supplies
  clarification questions without searching; UNSUPPORTED explains the current
  capabilities without searching. A filterless availability request must use
  clarification with at least two questions rather than unfiltered SEARCH.
- Render search-result text with backend templates. Obtain stone identities
  and names from catalog results, not generated result references.
- Permit one corrective model call for invalid structured output, with at
  most two model calls per exact-search turn. Disable additional automatic
  client retries that would exceed the selected limit.
- If the corrective attempt still fails validation, return HTTP 502
  ProblemDetail rather than a successful answer. Do not persist a completed
  conversation pair for that failed processing attempt.
- Keep the 120-second turn deadline from feature 0016 and limit each model
  call to 50 seconds, leaving time for catalog work and completed-turn storage.
- When model context does not fit, remove old whole history pairs while
  preserving the current request and system instructions. Keep full stored/UI
  history independently. Initially use 8192 tokens for the total model context
  and at most 512 output tokens, reserving room for output within that context.
  The user-authorized qualification remediation supersedes the initial 4096/512 choice.
- Mandatory readiness and basic functional verification remain in this
  feature. Dedicated context/output tuning is separate work, and broad
  query-quality evaluation belongs to feature 0027.

### Shared Capacity, Token Budget and Availability

- The shared invocation cap and daily token budget apply to all project model
  calls through the backend, including background embedding calls, not just
  chat generation. Start with N=1, configurable through environment settings.
- Use an initial shared daily budget of 100000 input/output tokens for all
  chats and embedding work, configurable through environment settings, with
  the day boundary in UTC.
- Store reliable usage accounting in PostgreSQL. Reserve before invocation
  and settle against actual reported usage; retain reservations for unknown
  usage until reconciled rather than blindly refunding them.
- Reserve the full configured upper bound separately for every model call:
  initially 8192 input/output tokens for each chat call, and the selected
  embedding profile's maximum input for each embedding call. Release the
  difference only after reliable actual-usage settlement.
- Unknown usage after a timeout or restart remains reserved against the UTC
  day of admission. The next UTC day starts a separate daily budget; preserve
  the previous day's accounting record rather than deleting or refunding it.
- Exhausted daily budget returns HTTP 429 for chat invocation admission, with
  retry guidance to the next UTC day. Occupied invocation capacity returns
  HTTP 503 CHAT_CAPACITY_EXCEEDED with a one-second retry hint. Healthy
  exhaustion/occupancy is distinct from a failed control component.
- Extend the shared fail-closed chat policy to Ollama unavailability: close
  send, history and committed replay until verified recovery. Do not keep
  those operations open using unchecked model availability or a fallback.
- Use separate admission outcomes for customer chat and indexing. A customer
  request does not wait for a model slot or renewed token budget: return the
  agreed immediate capacity/budget rejection with a busy or temporarily
  unavailable chat message. Preserve the previously selected HTTP 503 for
  occupied capacity and HTTP 429 for exhausted daily token budget.
- Indexing waits when model capacity is occupied or token budget is exhausted.
  These admission conditions do not consume its three event-specific attempts
  and must not move its work to DLQ.
- Markdown imports from feature 0017 also wait for admission, within their
  existing overall import deadline. Deadline expiry fails the import without
  partial publication; this HTTP import does not inherit the event-consumer
  retry/DLQ lifecycle.
- Healthy capacity occupancy or budget exhaustion is not a broken model
  dependency. Actual Ollama unavailability still triggers the shared
  fail-closed policy above.

## Stone Creation and Background Indexing

The user explicitly confirmed that stone creation returns HTTP 201 immediately
after the stone and outbox event commit. It does not wait for model capacity,
Kafka publication or embedding completion. The background indexing consumer
waits separately when model capacity or token budget is unavailable. This
preserves the creation contract already recorded in ADR-0021; no synchronous
model wait is added to the creation HTTP operation.

## Internal Interpretation Contract

The model returns one JSON decision with action, criteria, resetSearch,
reference, questions and reason. Criteria contain stoneTypes, stoneSizes,
availability values and admissionDateRanges (inclusive UTC start/exclusive end).
Only known catalog enum values are valid. A reference is either a one-based
ordinal in the latest shown list or PAGE_CONTEXT; resolve its ID in the backend.
Missing/ambiguous references and missing calendar years require clarification.
Validated reference lookup narrows the request; literal name/ID filters are absent.

SEARCH requires usable narrowing criteria or a resolved reference. CLARIFY
executes no catalog query; filterless availability requests ask at least two
specific questions. UNSUPPORTED executes no catalog query and explains supported
conditions. Contract-invalid output may receive one repair call; genuinely
ambiguous user input is clarification, not an output-validation failure.
Explicit availability-only requests without inherited narrowing use a schema
restricted to CLARIFY, at least two questions and empty type/size/date arrays;
backend validation continues to prohibit unfiltered queries for all decisions.
Use UTF-8 byte bounds for the qualified qwen2 byte-level tokenizer, including
ChatML overhead, to preserve the full permitted current request and output.
The user-authorized bounded remediation and its reasoning are recorded in ADR-0024.
Backend code resolves relative dates against an injected UTC clock and validates
ranges. Follow-ups retain effective criteria unless resetSearch selects a new
search. Requested availability is ANDed with mandatory AVAILABLE visibility.

## HTTP and History Contract Requirements

Keep the feature 0016 chat routes, ownership, turnId replay, request protection
and successful response/history DTOs. Return English template text and at most
five real catalog references. Internal decisions/effective filters do not become
public response fields. Store completed turn pairs only; repair/provider failures
must not append a completed pair. Committed replay performs no new inference or
token reservation, but follows the shared availability and request-rate rules.

For new model work, reject occupied capacity with HTTP 503 ProblemDetail carrying
CHAT_CAPACITY_EXCEEDED and Retry-After: 1. Budget exhaustion is HTTP 429 with
Retry-After until the next UTC day. Invalid output after repair is HTTP 502.
Actual model/control unavailability is shared fail-closed HTTP 503 for send,
history and replay until verified recovery. Ordinary capacity/budget rejection
must not close unrelated healthy history/replay operations.

## Shared Embedding Impact and Follow-ups

The feature owns the guard/accounting retrofit for existing 0017/0018 embedding
paths and the 0022 consumer. Background indexing waits; Markdown import waits
only within its existing overall deadline, preserving all-or-nothing publication.
No synchronous model wait is added to stone creation.

FUP-0024-001 and FUP-0024-002 remain discussion checkpoints after feature 0029
for native tools and model-generated result wording, unless already explicitly
covered by another discussed feature. No feature 0025 work is requested here.

## Accepted Exclusions

OOS-0024-001, OOS-0024-002, OOS-0024-003, OOS-0024-004, OOS-0024-005, OOS-0024-006, OOS-0024-007, OOS-0024-008 are recorded with their authoritative meaning in
[ADR-0024](../../docs/adr/0024-exact-llm-search.md) and the
[central register](../../docs/out-of-scope-decisions.md).
No unanswered proposal is an accepted exclusion.

## Dependencies and Delivery Gate

- [0016](../0016-llm-search-conversation-history-and-context/spec.md).
- [0017](../0017-markdown-document-indexing/spec.md).
- [0018](../0018-stone-card-embedding/spec.md).
- [0022](../0022-stone-card-index-event-processing/spec.md).

Reuse existing pinned versions and capabilities. Prior task completion is not
current runtime readiness. The first ticket in tasks.md checks the entire
explicitly requested batch, with exact file allowlists and focused selectors.
No implementation is authorized by documentation synchronization. Do not run
application tests, inference, business imports, commits or pushes as part of
this documentation work. New schema changes use new Liquibase changesets;
generated OpenAPI remains the HTTP source of truth and AsyncAPI the Kafka
source of truth. Feature 0025 is not part of this review or preparation.
