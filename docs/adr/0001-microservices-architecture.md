# ADR-001: Adopt Microservices Architecture Over Layered Monolith

## Status
Accepted

## Context and Problem Statement
The Smart Campus Platform must support a large number of concurrent users 
during peak periods such as course registration week. The system must also 
remain available if individual features fail, and allow independent deployment 
of separate business capabilities.

## Considered Options
- Layered Monolithic Architecture
- Microservices Architecture
- Serverless Architecture

## Decision Outcome
We adopt a Microservices Architecture, decomposing the system into 5 independent 
Spring Boot services: Student Service, Course Service, Enrollment Service, 
Payment Service, and Notification Service, all accessed through an API Gateway.

### Consequences
- Each service can be scaled independently
- Fault isolation prevents cascading failures
- Trade-off: higher deployment complexity compared to a monolith