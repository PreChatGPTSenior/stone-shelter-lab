# 🪨 Stone Shelter Lab
## Postmortem Driven Development 🍿
### An Experiment in AI-Assisted Software Engineering

**The goal is not simply to make an application work.**

The goal is to find out **how much engineering supervision AI-generated code actually needs, and whether better rules, automated quality gates, and multi-agent workflows produce better software.**

And, occasionally, to enjoy the results with popcorn. 🍿

---

### 📦 Deliverables

At the end of every Phase, I'll record a video reviewing the final implementation and walking through:

- What went wrong and why.
- Which mistakes were made by the agent.
- Which mistakes were mine.
- What actually worked well.
- Where the agent genuinely did a good job.
- Where my engineering decisions helped or made things worse.
- What I learned from the experiment.

And yes, the original code will be preserved. Future archaeologists deserve access to the evidence.

---
## 🧪 The Experiment at a Glance

|                        | Phase 1 🍿              | Phase 2 🔧              | Phase 3 🤖               |
|------------------------|-------------------------|-------------------------|--------------------------|
| **Approach**           | Vibe Coding             | Controlled Refactoring  | Multi-Agent Development  |
| **Starting Point**     | Initial implementation  | Phase 1 codebase        | Specifications only      |
| **Architecture Rules** | Suggestions and prayers | Enforced where possible | Enforced from day one    |
| **Quality Gates**      | None                    | Automated               | Automated                |
| **Code Review**        | Human observation       | AI-assisted review      | Independent AI reviewers |
| **Human Role**         | Observer with popcorn   | Engineering supervisor  | Experiment designer      |
| **Main Question**      | What could go wrong?    | Can we fix it?          | Can we prevent it?       |


---
## 🎢 Phase 1: Vibe Coding, Unsupervised

**Status:** 🟢 In Progress

**The experiment:** Let an AI coding agent implement the application with minimal human intervention.

**No enforced quality gates. No systematic automated architecture checks. No human refactoring.**

**NOTHING. JUST VIBE CODING. 🍿**

Just an AI agent, a codebase, a set of expectations, and an increasingly impressive collection of technical debt.


### 🔬 Questions to Investigate

**What happens when someone with only vibe-coding experience lets AI build an entire application?**

There is, admittedly, one minor flaw in the experimental design: I actually know how to code and cannot unlearn software engineering.

### 👩‍💻 My Responsibilities

- Collaborate with AI agents on requirements and specifications.
- Observe the implementation without manually correcting the code.
- Record architectural violations, questionable decisions, unnecessary complexity, and recurring patterns in my **secret day-by-day engineering disaster log**.
- Occasionally add rules to the project's *constitution* to discover **how creatively the agent will ignore them**.
- Resist the overwhelming temptation to fix everything myself.

**The goal is not to prove that AI writes bad code. It's to observe what actually happens when engineering supervision is deliberately reduced.**

---

## 🔧 Phase 2: Quality Gates & Controlled Refactoring

**Status:** ⚪ Planned

**The experiment:** Can engineering constraints and automated verification turn the Phase 1 codebase into something maintainable?

The Phase 1 implementation will be preserved in a dedicated branch and tagged before refactoring begins.

This time, the constitution will have actual enforcement mechanisms.

### 🛠️ Introducing Engineering Discipline

- A more comprehensive project constitution and explicit coding rules.
- **Checkstyle** and automated code formatting.
- **PMD** for static analysis and **CPD** for duplication detection.
- **ArchUnit** for enforceable architectural boundaries.
- Additional automated quality gates where appropriate.
- AI-assisted code review and controlled refactoring workflows.

**The focus is refactoring, not rewriting the application from scratch.** After stabilization, a small number of deliberately selected feature changes may be introduced to observe how well the improved codebase accommodates evolution.

### 🔬 Questions to Investigate

- Can AI agents reliably refactor their own code?
- How many architectural problems can be automated quality gates detect?
- Does explicit enforcement improve compliance with project rules?
- Does the resulting architecture become easier to extend?
- How much human supervision is still required?
- Can we turn *"Please follow the architecture"* into *"The build fails if you don't"*?

---

## 🤖 Phase 3: Multi-Agent Development From Scratch

**Status:** ⚪ Planned

**The experiment:** What happens when AI agents start from the same specifications, but with engineering constraints, automated checks, and independent code review built into the process from day one?


### 📁 Starting Materials

It will contain only:

- The application specifications prepared during the experiment.
- The architectural constitution and coding standards.
- The defined engineering constraints and acceptance criteria.

**No backend code. No frontend code. No implementation copied from previous phases.**


### 🧠 Multi-Agent Development

Development will be organized around specialized AI roles, orchestrated through a multi-agent workflow with MCP integrations where appropriate.
Each multi-agent team will start in a separate repository, with different combinations of models and capability levels, to compare how team composition affects code quality and development outcomes.

Potential roles include:

- **Business Analyst:** Requirements, specifications, and acceptance criteria.
- **Developer:** Implementation and tests.
- **Reviewer:** Independent code and architecture review.
- **Quality Gates:** Automated verification and rule enforcement.


### 🔬 Questions to Investigate

- Does a multi-agent workflow produce better code than a single coding agent?
- How do different combinations of AI models and capability levels affect code quality,

---
## 🪨 A Pet Project. Not a Toy Engineering Experiment.

**Yes, this is a pet project about a shelter for stones.**

Stones have profiles. Stones can be adopted. Stones deserve love, apparently.

But while the business domain is intentionally absurd, the engineering challenges are not.

Stone Shelter Lab is designed as a full-stack application with a deliberately ambitious technology stack, covering persistence, transactional consistency, distributed messaging, object storage, resilience, AI integration, and automated testing.

The idea is simple:

**Keep the business domain ridiculous enough to be entertaining, but the technical challenges realistic enough to expose actual engineering problems.**

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

*The stack includes both implemented infrastructure and capabilities planned in the specifications. Dependency declarations are not proof of completed functionality.*

### 🎯 Why So Much Engineering for a Stone Shelter?

Because another simple CRUD application would tell us relatively little about the limits of AI-assisted software engineering.

The experiment needs opportunities for agents to encounter real architectural decisions:

- Transaction boundaries and data consistency.
- Distributed workflows, retries, and partial failures.
- Event publishing and idempotency.
- Persistence and object storage lifecycle management.
- API contracts and validation.
- AI integration and search infrastructure.
- Architectural boundaries and maintainability.
- Automated testing and enforcement of engineering standards.

These are exactly the areas where generating working code and designing reliable software can become two very different activities.

**The stones may be fictional. The technical debt is unfortunately very real.** 🍿

