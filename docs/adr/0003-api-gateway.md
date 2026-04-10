# ADR-003: Introduce an API Gateway as the Single Entry Point

## Status
Accepted

## Context and Problem Statement
With multiple independent microservices each running on different ports, 
clients would need to know every service address. Cross-cutting concerns 
such as authentication and logging would need to be duplicated in every service.

## Considered Options
- Option 1: API Gateway pattern (Spring Boot Gateway)
- Option 2: Client-side routing with service discovery
- Option 3: Direct microservice endpoints

## Decision Outcome
A dedicated Spring Boot API Gateway runs on port 8080 and routes all 
incoming requests to the appropriate microservice based on the URL path.

## Consequences
- Single entry point simplifies client interaction
- Cross-cutting concerns centralized in one place
- Trade-off: single point of failure at prototype scale