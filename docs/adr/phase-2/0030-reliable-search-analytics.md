# ADR 0030: Defer Reliable Search Analytics to Phase 2

Date: 2026-10-13

Status: Accepted phase boundary and documentation renumbering. The entire feature
is outside the current development phase; no phase-2 design or execution is approved.

Feature: [0030: Reliable Search Analytics](../../../specs/0030-reliable-search-analytics/spec.md).
Related documents: [acceptance](../../../specs/0030-reliable-search-analytics/acceptance.md),
[plan](../../../specs/0030-reliable-search-analytics/plan.md),
[tasks](../../../specs/0030-reliable-search-analytics/tasks.md).

## Context

Before this correction, feature 0028 covered reliable search analytics publication
and PostgreSQL storage, 0029 the streaming API and 0030 the streaming widget.
Feature 0027 qualification had already been discussed and documented, including
its approved synthetic scenarios and separate personal manual-testing exclusion.
No implementation was authorized.

The following record preserves the ambiguous instruction, the assistant's
clarification and the user's correction; it does not fabricate a design discussion
or treat an unanswered proposal as a decision.

## Discussion Record

### Initial instruction — 2026-10-13

User instruction, translated into English:

> Remove this entirely and record it in an ADR as feature 30. Make the two
> features that were previously 29 and 30 into 28 and 29. Feature 30 is entirely
> out of scope for this development phase. Put its ADR in a separate phase-2 folder.

The referred feature was initially ambiguous after discussion of personal manual
testing. The assistant checked the actual feature documents and did not delete
or change feature 0027 while awaiting clarification.

### Q0030-001 — Which feature is being moved?

Question asked directly in chat, translated into English:

> Currently 0028 is search analytics, 0029 is the streaming API and 0030 is the
> streaming widget. Should search analytics or manual acceptance against your own
> set move into feature 0030 in a separate phase-2 ADR folder?

User clarification, translated into English:

> Stop. Feature 28 should have been removed, not 27. Restore 27.

Accepted decision D0030-001:

- The removal targets reliable search analytics, formerly 0028. It does not target
  0027 qualification or the personal manual-testing note associated with 0027.
- Feature 0027 and its approved specification/acceptance/plan/tasks/ADR stay intact.
  They were never removed during this correction, so no reconstruction is needed.
- Remove analytics from the current-phase scope and preserve its future intent
  as feature 0030. The entire feature is deferred; no analytics-specific contract,
  code, migration, consumer, qualification or further design preparation is part
  of this phase.
- Store this ADR in docs/adr/phase-2, an English folder name matching repository
  artifact-language rules. The archived feature documents explicitly state the
  deferral rather than implying that a draft authorizes work.

### D0030-002 — Renumber the two streaming features

The renumbering was explicitly requested in the initial instruction and remains
applicable after the user identified analytics as the removed feature.

| Previous feature | Current feature | Documentation path | Readiness placeholder |
| --- | --- | --- | --- |
| 0028 reliable search analytics | 0030, phase 2 | specs/0030-reliable-search-analytics | T0028-001 becomes T0030-001, deferred |
| 0029 streaming API | 0028 | specs/0028-chat-streaming-api | T0029-001 becomes T0028-001 |
| 0030 streaming widget | 0029 | specs/0029-chat-streaming-widget | T0030-001 becomes T0029-001 |

Update headings, task IDs, feature references and dependencies with the moved
feature identities. These drafts have no executed tickets; renumbering does not
create implementation authority or finalize their open product questions.

### D0030-003 — Preserve the current-phase dependency boundary

- Streaming API 0028 uses completed search/conversation behavior without requiring
  deferred analytics publication/storage. Widget 0029 depends on API 0028.
- Remove analytics-specific completion/failure work from current streaming drafts;
  future analytics integration remains part of separately requested phase-2 work.
- Shared indexing outbox/Kafka capabilities retain their original scope. Deferring
  analytics does not remove the publication mechanism needed for card indexing.
- Synchronize the discovery roadmap/current-scope notes without erasing historical
  analytics intent or claiming unasked analytics design decisions are settled.
- Existing follow-up checkpoints tied to the former final streaming widget use
  its new number 0029; this is an identity-preserving reference update, not new
  implementation or a scheduled activity.

## Accepted Out-of-Scope Decision

Matching entry: [central register](../../out-of-scope-decisions.md).

| ID | Accepted exclusion | Scope boundary |
| --- | --- | --- |
| OOS-0030-001 | Entire reliable search analytics feature in the current development phase | Defer all analytics-specific discussion/preparation, contracts, event publication, storage/migrations, consumers and verification to phase 2 as feature 0030. Current-phase streaming API 0028 and widget 0029 do not depend on analytics. Existing indexing outbox/Kafka work and feature 0027 qualification remain unchanged. |

## Consequences and Execution Boundary

Current development does not wait for analytics availability or produce analytics
events/records as an implicit streaming requirement. Future design still needs
separate questions, contracts, acceptance, file scopes and fresh readiness if
phase 2 is requested. No timing, API schema, retention or storage design is settled
by this deferral.

This change is documentation-only. No runtime checks, application tests, source
edits, data operations, commits or pushes are authorized or claimed. Stop after
the requested documentation correction; do not begin streaming implementation
or discussion of its unresolved requirements automatically.
