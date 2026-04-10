# ADR-002: Apply Internal Layered Architecture with Ports and Adapters

## Status
Accepted

## Context and Problem Statement
Each microservice risks becoming a mini-monolith if no internal structure 
is enforced. Business logic could become polluted with JPA annotations or 
HTTP framework concerns, making services harder to test and evolve.

## Considered Options
- Layered architecture with ports and adapters
- Hexagonal architecture
- Clean architecture

## Decision Outcome
Each microservice internally follows the layered architecture established 
in the course: entrypoints (controllers) → business (services) → 
business/domain (pure Java) → repository (persistence).

### Consequences
- Domain model is clean and framework-independent
- Each layer can be tested independently
- Trade-off: extra boilerplate with explicit mapping