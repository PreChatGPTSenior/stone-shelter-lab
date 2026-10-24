# 🪨 Stone Shelter Lab
## Postmortem Driven Development
### An Experiment in AI-Assisted Software Engineering

**The goal is not simply to make an application work.**

The goal is to find out **how much engineering supervision AI-generated code actually needs, and whether better rules, automated quality gates, and multi-agent workflows produce better software.**

And, occasionally, to enjoy the results with popcorn. 🍿

---

### 🚦 STATUS
---

✅ **Phase 1: Vibe Coding, Unsupervised**

**SCOPE:** An AI coding agent implements the application with minimal human intervention. Just an AI agent, a codebase, a set of expectations, and an increasingly impressive collection of technical debt. Occasionally, rules are added to the project's *constitution* to discover **how creatively the agent will ignore them**. Architectural violations, questionable decisions, unnecessary complexity, and recurring patterns are recorded in my **secret day-by-day engineering disaster log**.

**CODE FREEZE:** 2026-10-14

**🔗 [Stone Shelter Lab — Phase 1 (HERE)](https://github.com/PreChatGPTSenior/stone-shelter-lab)**

---

🔄 **Phase 2: Quality Gates & Guardrails**

**STATUS:** PREPARATION IN PROGRESS

**SCOPE:** Engineering Rules · Templates · Agent Skills · Agentic Workflows · Code & Style Standards · Code Review Guidelines

**🔗 [Stone Shelter Lab — Phase 2](https://github.com/PreChatGPTSenior/stone-shelter-spec)**

---

## 🧪 The Experiment at a Glance

| | Phase 1 🎢 | Phase 2 🔬 |
|---|---|---|
| **Approach** | Vibe Coding | Multi-Agent Development |
| **Starting Point** | Initial implementation | Specifications only (OpenSpec) |
| **Architecture Rules** | Suggestions and prayers | Enforced from day one |
| **Quality Gates** | None | Automated |
| **Code Review** | Human observation | Independent AI reviewers |
| **Human Role** | Observer with popcorn | Experiment designer |
| **Main Question** | What could go wrong? | Can we prevent it? |

---

## 🪨 A Pet Project. Not a Toy Engineering Experiment.

**Yes, this is a pet project about a shelter for stones.**

## 🎯 Why So Much Engineering for a Stone Shelter?

**Keep the business domain ridiculous enough to be entertaining, but the technical challenges realistic enough to expose actual engineering problems.**

Because another simple CRUD application would tell us relatively little about the limits of AI-assisted software engineering.

The experiment needs opportunities for agents to encounter real architectural decisions.

---

### 🛠️ Technology Stack

**Backend**
- Java 23, Spring Boot 4, Spring MVC
- Spring Data JPA, Hibernate
- REST APIs, OpenAPI / Swagger, Jakarta Bean Validation
- Maven

**Data & Storage**
- PostgreSQL 18 with pgvector
- Liquibase database migrations
- Redis
- MinIO object storage

**Messaging & Distributed Systems**
- Apache Kafka 4.x
- Transactional Outbox Pattern
- Asynchronous event processing and recovery
- Idempotency, retries, and failure handling

**AI & Search**
- Spring AI
- Ollama for local LLM integration
- Vector embeddings and semantic search
- Conversational search and context management

**Resilience & Rate Limiting**
- Resilience4j
- Bucket4j
- Timeout, concurrency, and failure-handling policies

**Frontend**
- React 19
- TypeScript 6
- Vite
- ESLint

**Testing & Infrastructure**
- JUnit / Spring Boot Test
- Testcontainers
- ArchUnit
- Vitest and React Testing Library
- Docker and Docker Compose

---
**The stones may be fictional. The technical debt is unfortunately very real.** 🍿

- Transaction boundaries, data consistency, and distributed system reliability.
- Distributed workflows, retries, recovery, and partial failure handling.
- Event publishing, idempotency, and transactional outbox patterns.
- Persistence, object storage lifecycle, and API contract validation.
- AI integration, RAG, vector search, and context management.
- Architecture governance, maintainability, and specification-driven development.
- OpenSpec workflows, structured acceptance criteria, and requirements traceability.
- Multi-agent orchestration, specialized developer and reviewer agents, parallel execution, and context coordination.
- Deterministic quality gates with PMD, Checkstyle, ESLint, automated tests, and static analysis.
- Independent AI code review, architectural validation, and automated feedback loops.
- Agent execution observability, structured logging, tracing, and execution history.
- End-to-end traceability from requirements and tasks to agent actions, code changes, tests, and results.
- Multi-model execution through OpenRouter, per-agent token accounting, cost tracking, budget limits, and execution stop conditions.
- Workflow verification, execution state persistence, failure recovery, and reproducible development processes.
