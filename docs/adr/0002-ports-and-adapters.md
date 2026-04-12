# ADR-002: Enforce Internal Layered Architecture with Ports and Adapters

## Status
Accepted

## Context and Problem Statement
Each microservice risks becoming a mini-monolith if no internal structure 
is enforced. Business logic could become polluted with JPA annotations or 
HTTP framework concerns, making services harder to test and evolve.

## Considered Options
- Layered architecture with Ports and Adapters
- Hexagonal architecture
- Clean architecture

## Architectural Drivers
Technology Constraint driver and Independent Deployability driver. The team 
has been trained on the clean layered architecture throughout the course labs. 
Each service must be independently evolvable, which requires clean internal 
boundaries to prevent changes in one layer from affecting others.

## Decision Outcome
Each microservice internally follows the layered architecture established 
in the course: entrypoints (controllers) → business (services) → 
business/domain (pure Java, no framework dependencies) → infrastructure 
(JPA entities and adapters). A port interface is defined in the business 
layer and implemented by a JPA adapter in the infrastructure layer.

## Consequences
- Domain model is clean and free from JPA and HTTP framework concerns
- Persistence can be swapped without affecting business logic
- Each layer can be tested independently
- Trade-off: extra boilerplate with explicit mapping between domain and entity