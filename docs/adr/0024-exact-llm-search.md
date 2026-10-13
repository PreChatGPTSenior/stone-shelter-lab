# ADR 0024: Exact LLM Search

Date: 2026-10-12

Status: Accepted and implemented; focused verification recorded in feature tasks/acceptance.

Feature number: 0024. The ADR number and filename prefix match the feature
specification directory number.

## Context

Feature [0024: Exact LLM Search](../../specs/0024-exact-llm-search/spec.md)
replaces the fixed chat answer with English request interpretation and bounded
catalog search. It inherits conversation ownership, history, replay and request
protection from
[feature 0016](../../specs/0016-llm-search-conversation-history-and-context/spec.md)
and [ADR-0008](0008-chat-memory-and-request-protection.md).

## Accepted Decisions

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

## Accepted Out-of-Scope Decisions

Matching entries are maintained in the
[central register](../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0024-001 | Native model tool calling | Use structured interpretation followed by backend execution. After feature 0029, check whether tool calling was covered by another discussed feature; if not, discuss a separate tooling feature. |
| OOS-0024-002 | Additional model invocation for result wording | Render exact-search result text with backend templates. After feature 0029, check whether model-generated wording was covered elsewhere; if not, discuss it separately. |
| OOS-0024-003 | Semantic matching of unsupported properties and intended uses | Do not drop unsupported wishes or infer exact properties; semantic matching belongs to feature 0025. |
| OOS-0024-004 | Care and geological explanations | This stage provides exact catalog search; substantive care/geological answers belong to feature 0026. |
| OOS-0024-005 | Dedicated evaluation of context and output-token sizing | Start with an 8192-token total context and a 512-token output cap; defer detailed parameter evaluation and tuning. Mandatory readiness and basic functional verification remain in feature 0024, with broad query-quality evaluation in feature 0027. |
| OOS-0024-006 | General name and ID search-filter additions | Limit new exact-search filters to type, size, availability and admission date. Stored-result and page-context references remain supported. |
| OOS-0024-007 | External-provider implementation in the initial delivery | Prepare a provider interface while running only local Ollama initially; no external provider connection or automatic fallback is selected here. |
| OOS-0024-008 | Queuing customer chat requests for model admission | Customer chat requests receive an immediate busy/capacity or token-budget rejection. Background indexing waits without consuming event-specific processing attempts or entering DLQ. Markdown imports wait within their existing overall deadline and fail without partial publication if it expires. |

## Follow-up Checkpoint After Feature 0029

Numbering update on 2026-10-13: the former final streaming widget 0030 is
now 0029. These checkpoints keep the same feature identity; analytics is now
0030 and entirely deferred to phase 2. See
[ADR-0030](phase-2/0030-reliable-search-analytics.md).

- FUP-0024-001 corresponds to OOS-0024-001: review the discussed features after
  0029 for native tool calling. If none covers it, open a separate discussion
  so tooling is addressed rather than silently forgotten.
- FUP-0024-002 corresponds to OOS-0024-002: perform the same review for an
  additional model call that formulates result text.

These are conditional discussion follow-ups, not implementation tickets or
scheduled automations. Coverage in another feature must be explicitly agreed;
do not infer it from that feature's title.

## Delivery Preparation and Readiness

The remaining clarification answers are accepted. The feature specification,
acceptance criteria, technical plan and task boundaries record the structured
DTOs, date rules and resource accounting. Fresh environment readiness and basic qualification are recorded in tasks.md;
full implementation is pending and numeric settings alone do not establish readiness.

## Preparation Boundary

Use the synchronized specification, acceptance criteria, technical plan and
exact implementation-task boundaries when execution is explicitly requested.
Account for the shared-control impact on existing embedding workflows when
defining the permitted ticket batch and readiness prerequisites. Generated
OpenAPI remains the HTTP source of truth; do not maintain handwritten OpenAPI.
Acceptance criteria belong exclusively in acceptance.md. This ADR authorizes
no model calls/downloads, environment changes, code, application tests, commits
or pushes.

## Authorized Qualification Remediation

The user approved a separate bounded correction scope after the initial
qualification failed. Retain qwen2.5:3b/Spring AI 2.0.1, the existing 2000-UTF-16
HTTP input, 512 output tokens, 50-second call and 120-second turn limits.
Use an 8192-token context and reserve 8192 per chat call. The complete permitted
input domain has a conservative bound of 7639 input tokens plus 512 output
with the qualified compact prompt, including ChatML overhead and a 200-byte
corrective-instruction allowance (8151 total). The former 4096
setting does not establish this bound. The choice preserves the public input
contract without truncating the current request; it is a necessary qualification
correction, not a broad tuning campaign. OOS-0024-005 remains deferred tuning
with the new initial setting and matches the central register.

The installed profile reports GPT2 byte-level tokenization with qwen2
pretokenization. UTF-8 byte count is used as a conservative upper bound, not an
average characters/token estimate; BPE merges cannot increase the byte-token
count. Maximum valid input contributes at most 6000 bytes for 2000 UTF-16 units.
The bound includes all system/example/current message content and a conservative
32 tokens per ChatML message plus 32 for the assistant header. Confirm the
installed profile/template before relying on this bound. The qualified template
SHA-256 is eb4402837c7829a690fa845de4d7f3fd842c2adee476d5341da8a46ea9255175. Background history is
trimmed as whole pairs within the remaining capacity.
Primary tokenizer reference: [Qwen2 tokenizer](https://huggingface.co/docs/transformers/v4.45.1/en/model_doc/qwen2).

Keep the JSON schema in the API format parameter, with compact instructions and
a real user/assistant clarification example. Explicit availability-only requests
without inherited narrowing use a restricted schema: CLARIFY, at least two
questions and empty type/size/date arrays. This enforces the existing backend
policy at the structured boundary and prevents repair from inventing criteria
to enable SEARCH. Generic decisions still receive strict backend validation.
No SQL or catalog query is authorized by malformed or ungrounded criteria.

The maximum Unicode diagnostic timed out at both 32768 and 8192. This is not
proof of a token-count overflow or a promise that every permitted request
finishes inference within 50 seconds. Qualification separates complete-input
capacity proof from execution deadlines; actual timeout keeps unknown usage
reserved and does not append a completed turn. Numeric bounds and a working
basic fixture do not establish a general query-quality evaluation.

## Delivery Verification

The feature implementation and focused acceptance review are complete. Evidence
and exact selectors are recorded in the feature's tasks.md and acceptance.md;
acceptance criteria are not duplicated here. The provider, PostgreSQL ledger,
exact query, embedding retrofit, chat integration and generated HTTP contract
retain the accepted scope. Verification uses isolated data and the installed
qualified model. Local deployment and broad quality evaluation are not claimed.
