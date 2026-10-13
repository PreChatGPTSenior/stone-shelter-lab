# Feature 0028 Technical Plan

Status: Prepared, all execution NOT RUN. Read [spec.md](spec.md),
[acceptance.md](acceptance.md), [tasks.md](tasks.md) and
[ADR-0028](../../docs/adr/0028-chat-streaming-api.md). Acceptance criteria live
exclusively in acceptance.md; these are technical preparation choices within the
accepted baseline, not additional user answers.

## Existing Code and Required Delivery

Read-only inspection found the delivered 0024 flow: StoneChatbotService performs
session authorization, committed lookup, conversation admission, processing and
atomic append before returning its mapped response. StoneChatbotController writes
its new-commit cookie through an onCommitted callback. StoneChatBindingInterceptor
covers chat paths. ApiExceptionHandler currently owns public chat status/code/
Retry-After mapping. No existing streaming implementation is assumed.

0025/0026 documents define future semantic/RAG behavior and the trusted current-
turn template exception. Production streaming integration requires those delivered
contracts and focused evidence; documentation alone is insufficient. This feature
must not implement missing search/RAG tickets or change their rules.

## Shared Turn Admission and Execution

Refactor the common orchestration into an admitted-turn handle and execution/
completion operations used by both JSON and streaming. Reuse original request
mapping, session quota/ownership, retained lookup/payload conflict, raced replay,
conversation lease, context, original deadline and confirmed pair/result append.
Do not call the existing complete send operation twice or double-charge admission.

Streaming reserves its zero-wait transport slot after existing identity/quota
checks and before opening/model work; replay also needs a slot. New work obtains
its existing conversation ownership and starts a bounded worker independently of
HTTP writes. Only admitted started is eligible for transport. JSON retains its
synchronous mapped response and normal new-commit cookie callback.

Use StoneChatAdmittedTurnEntity/StoneChatStreamEntity for private state, ownership
and immutable saved-result readiness; domain types do not depend on HTTP/shared
DTO types. Preserve actual-exit lease/permit lifetimes. A disconnect affects
transport only; processing can finish under the same deadline/fence. Confirmed
commit or retained replay supplies one immutable snapshot. Unknown acknowledgement
is an exception/reconciliation state, never an answer source.

## MVC Transport Boundary and Ready DTOs

Controller returns a ready shared StoneChatStreamResponse handle through the
stream service, applying route/status/headers/cookie decisions only. A dedicated
StoneChatStreamReturnValueHandler handles that type through Servlet async I/O,
SSE framing, bounded writer lifecycle and terminal mapping. Register it in a
small dedicated MVC configuration; do not add WebFlux or return SseEmitter from
application services. Internal handle fields are not a public JSON response.

Services obtain ready responses through mappers. Normal events use
StoneChatStreamEventEntityToStoneChatStreamEventResponseMapper; the stream handle
uses StoneChatStreamEntityToStoneChatStreamResponseMapper. Shared event response
variants expose exactly the fields specified in spec.md. The five-field completed
variant is a class with private fields, no-argument constructor and getters/
setters; at-most-four-field variants may be records. Mappers read entity getters,
return transformations and use inherited DTO null-source validation.

Post-open business failures are mapped by the handler-owned
StoneChatProblemDetailFactory, also reused by ApiExceptionHandler, retaining
existing codes/status/retry policy including delivered semantic/RAG errors.
A four-field shared StoneChatStreamErrorContext provides identity/sequence and
mapped ProblemDetail to its DTO mapper. Services/entities/custom exceptions never
import HTTP status, ProblemDetail, SseEmitter or handler classes.

The custom handler sends the ready typed data with exact event names, counts
sequence per stream, skips sequence for heartbeat comments, flushes bounded frames
and closes terminal transport once. Runtime carrier and event variants are
explicitly documented in generated annotations so OpenAPI describes actual SSE
payloads, not a JSON internal handle. No handwritten OpenAPI.

## Resource and Deadline Design

Settings use the five named environment variables/defaults in spec.md, validated
before admission. At most one frame may be pending per stream. Partition the
saved text lazily by Unicode code points without changing whitespace, with no
typing animation. Keep full final text for completed, not a retained fragment log.

Separate processing workers/lease renewal from fixed bounded transport writers
and one bounded/coalesced heartbeat schedule. Never use an unbounded async-executor
or heartbeat queue. A stalled started/heartbeat write cannot block result creation.
One stream admission covers its writer and pending-frame capacity; actual blocked
writer exit is required before reusing that resource slot. New streams fail 503
rather than accumulating writer tasks. No request-disconnect capacity fiction.

The processing/commit deadline is inherited and monotonic; preserve the 0026
completion margin. Preparation writes are clipped to remaining processing time;
saved-response/replay delivery is bounded from result readiness, including writer
wait/serialization, not restarted at the first successful fragment. Each write
also has its finite cap. Heartbeat updates no deadline/retention or sequence.

[Spring MVC async documentation](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-ann-async.html)
explains that individual MVC writes can block; this supports requiring actual
transport qualification, not assuming an emitter timeout proves cleanup. The
prototype must establish cancellation/connection cleanup on the pinned container.
If that stack/seam cannot meet the accepted bounds, stop dependent source tickets
and propose exact corrected scope. Do not silently weaken the limits, release
live writers or introduce another server/library.

## Cookie Amendment and Widget Handoff

New admitted streams return a ready early-cookie instruction for the HTTP boundary
before any started/heartbeat headers are flushed. Denied/replay paths cannot get
that instruction. No post-open onCommitted Set-Cookie call. JSON keeps its original
new-save header timing; server retention extends only inside a new completed save.

Expose a service/repository scalar query for the existing session's remaining
lifetime, without changing server TTL or exposing the session token in response
bodies. On successful healthy authorized history retrieval, the HTTP boundary
can synchronize browser cookie expiry to that remaining lifetime. Missing/expired/
foreign/unknown state follows existing binding/fail-closed rules, not a guessed
full TTL or renewed ownership. Expiry data is validated/read-only and must not
claim that the client clock can extend server authority.

0029 consumes completed then reconciles history when dependencies permit. If the
current-turn template completed while provider readiness became closed, that GET
may fail; retain the completed answer and use manual verified recovery. Never
regenerate or mark an authoritative completed answer uncommitted due to sync failure.

The accepted baseline is recorded in 0016 documents/ADR; their earlier verification
does not prove this amendment. OOS-0028-004 defers full detailed discussion/redesign
until after phase-1 implementation. It does not remove baseline qualification or
approve further cookie policy now.

## Environment and Verification Preparation

T0028-001 checks the entire explicitly requested batch and reports expected/
observed states, full IDs, preparation versus read-only checks, READY/BLOCKED and
corrections. Check command-scoped JAVA_HOME JDK 23.0.2/Maven 3.9.9, approved Boot
4.1.1/managed MVC/Servlet version metadata and Docker/Testcontainers where needed.
For integration, verify actual isolated PostgreSQL 18.6/pgvector 0.8.6, Redis
8.2.10, approved Ollama 0.40.3 and installed qwen2.5:3b/qwen3-embedding:0.6b
profile/digests plus effective ledger/control settings. Obtain approved endpoint/
configuration values from authorized setup/session sources; never invent secrets.
Do not inspect manifests/build outputs/framework property files or private .env.

The isolated transport prototype has no model/search dependency and uses prepared
controlled outcomes; identify those differences in readiness/reporting. A larger
requested integration batch requires all its delivered dependencies. Report actual
backend/frontend/service states with impact; start only needed existing services,
verify real health/connectivity and report startup as preparation. No test/model
inference/business import/download is a readiness check.

Prototype real HTTP/socket tests qualify stream resources before source changes;
MockMvc alone is insufficient for slow-reader/blocked-writer claims. Integration
uses isolated Redis/PostgreSQL and controlled providers to verify final business/
contract coupling. Requalify final native transport after integration, not by
blindly repeating unchanged successful model work. Exact selectors are in tasks.md;
no full suite or new broad model campaign. Read generated JSON/YAML contract from
actual focused integration, not by reading stale build outputs.

## Scope and Execution Boundary

No frontend implementation, Kafka/analytics contract, schema, new dependency or
provider/profile tuning. Common turn/cookie changes are explicitly allowlisted in
0028 tickets; missing delivered source is a blocker, not extra implementation.
All tickets remain unchecked. Documentation authorizes no tests, service startup,
models, commits or pushes. After preparation, discuss 0029 without expanding the
deferred cookie-policy review or analytics feature.
