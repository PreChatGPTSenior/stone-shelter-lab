# 🪨 Stone Shelter Lab
## Postmortem Driven Development 🍿
### An Experiment in AI-Assisted Software Engineering

**The goal is not simply to make an application work.**

The goal is to find out **how much engineering supervision AI-generated code actually needs, and whether better rules, automated quality gates, and multi-agent workflows produce better software.**

And, occasionally, to enjoy the results with popcorn. 🍿

---

## 🎢 Phase 1: Vibe Coding, Unsupervised

**Status:** 🟢 In Progress

**The experiment:** Let an AI coding agent implement the application with minimal human intervention.

**No enforced quality gates. No systematic automated architecture checks. No human refactoring.**

**NOTHING. JUST VIBE CODING. 🍿**

Just an AI agent, a codebase, a set of expectations, and an increasingly impressive collection of technical debt.

### 🔬 Questions to Investigate

**What happens when someone with only vibe-coding experience lets AI build an entire application?**

There is, admittedly, one minor flaw in the experimental design: I actually know how to code.

Unfortunately, I cannot unlearn software engineering for the sake of science.

But I'm doing my best to breathe, resist the urge to intervene, and limit my involvement to collaboratively writing specifications with the agent.

The architecture may suffer. The scientific curiosity must prevail.

### 👩‍💻 My Responsibilities

- Collaborate with AI agents on requirements and specifications.
- Observe the implementation without manually correcting the code.
- Record architectural violations, questionable decisions, unnecessary complexity, and recurring patterns in my **secret day-by-day engineering disaster log**.
- Occasionally add rules to the project's *constitution* to discover **how creatively the agent will ignore them**.
- Resist the overwhelming temptation to fix everything myself.

### 📦 Deliverables

At the end of Phase 1, I'll record a video reviewing the final implementation and walking through:

- What went wrong and why.
- Which mistakes were made by the agent.
- Which mistakes were mine.
- What actually worked well.
- Where the agent genuinely did a good job.
- Where my engineering decisions helped or made things worse.
- What I learned from the experiment.

**The goal is not to prove that AI writes bad code. It's to observe what actually happens when engineering supervision is deliberately reduced.**

And yes, the original code will be preserved. Future archaeologists deserve access to the evidence.

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

**The focus is refactoring, not rewriting the application from scratch.**

After stabilization, a small number of deliberately selected feature changes may be introduced to observe how well the improved codebase accommodates evolution.

For example, introducing a new domain attribute that was intentionally left out of the initial implementation.

Because maintaining existing software is where architectural decisions become particularly entertaining.

### 🔬 Questions to Investigate

- Can AI agents reliably refactor their own code?
- How many architectural problems can automated quality gates detect?
- Does explicit enforcement improve compliance with project rules?
- Does the resulting architecture become easier to extend?
- How much human supervision is still required?
- Can we turn *"Please follow the architecture"* into *"The build fails if you don't"*?

### 📦 Deliverables

A refactored application, quality reports, an architectural assessment, and a tagged Phase 2 snapshot.

**Same codebase. Same requirements. Fewer opportunities for creative architectural interpretation.**

At least, that's the hypothesis.

---

## 🤖 Phase 3: Multi-Agent Development From Scratch

**Status:** ⚪ Planned

**The experiment:** What happens when AI agents start from the same specifications, but with engineering constraints, automated checks, and independent code review built into the process from day one?

A completely new repository will be created.

### 📁 Starting Materials

It will contain only:

- The application specifications prepared during the experiment.
- The architectural constitution and coding standards.
- The defined engineering constraints and acceptance criteria.

**No backend code. No frontend code. No implementation copied from previous phases.**

No inherited technical debt.

No mysterious legacy decisions.

Just specifications, rules, and several AI agents with strong opinions about software engineering.

What could possibly go wrong?

### 🧠 Multi-Agent Development

Development will be organized around specialized AI roles, orchestrated through a multi-agent workflow with MCP integrations where appropriate.

Potential roles include:

- **Business Analyst:** Requirements, specifications, and acceptance criteria.
- **Developer:** Implementation and tests.
- **Reviewer:** Independent code and architecture review.
- **Quality Gates:** Automated verification and rule enforcement.

The objective is to evaluate whether multi-agent collaboration, explicit contracts, and continuous verification can prevent the problems observed during the initial vibe-coding phase.

Or whether three agents can simply agree that everything looks good.

### 🔬 Questions to Investigate

- Does a multi-agent workflow produce better code than a single coding agent?
- Are architectural rules followed more consistently when independent reviewers are involved?
- Can automated checks prevent technical debt rather than merely detect it afterward?
- How does the result compare with refactoring an unconstrained AI-generated codebase?
- What is the cost of additional engineering control in iterations, time, and tokens?
- **Can multiple AI agents collectively demonstrate better engineering judgment, or will they simply form a committee to approve each other's mistakes?**

### 📦 Deliverables

A fresh implementation, quality reports, and a comparative assessment of all three phases.

---

## 🧪 The Experiment at a Glance

| | Phase 1 🍿 | Phase 2 🔧 | Phase 3 🤖 |
|---|---|---|---|
| **Approach** | Vibe Coding | Controlled Refactoring | Multi-Agent Development |
| **Starting Point** | Initial implementation | Phase 1 codebase | Specifications only |
| **Architecture Rules** | Suggestions and prayers | Enforced where possible | Enforced from day one |
| **Quality Gates** | None | Automated | Automated |
| **Code Review** | Human observation | AI-assisted review | Independent AI reviewers |
| **Human Role** | Observer with popcorn | Engineering supervisor | Experiment designer |
| **Main Question** | What could go wrong? | Can we fix it? | Can we prevent it? |

Each phase will be preserved using Git branches and tags.

Phase 3 will use a completely separate repository to avoid carrying over implementation code from previous phases.

---

## 🎯 Final Thought

**AI can generate code. Engineering is still responsible for making that code trustworthy.**

Working code is not automatically good code.

Passing tests is not automatically good architecture.

And putting *"Follow clean architecture principles"* in a Markdown file does not magically make an AI agent follow them.

This experiment is about discovering which parts of software engineering can be delegated, which can be automated, and which still require an experienced engineer.

It is an exploratory engineering case study, not a controlled scientific benchmark.

**Pre-ChatGPT Senior™. Post-ChatGPT Agent Wrangler.**

*Powered by specifications, architectural skepticism, and popcorn.* 🍿