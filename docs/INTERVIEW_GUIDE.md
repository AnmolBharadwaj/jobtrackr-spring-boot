# JobTrackr — Interview Preparation Guide

Use this guide to understand the code, not to memorize claims. Be ready to open the relevant classes and explain why each layer exists.

## 60-second project explanation

“I built JobTrackr, a Java 21 and Spring Boot REST API for tracking job applications through statuses such as Applied, Screening, Interview, Offer, Rejected, and Withdrawn. The API follows a layered design: the controller handles HTTP requests, the service owns business rules, Spring Data JPA handles persistence, and DTOs define the request and response contract. I added validation, duplicate-application checks, structured error responses, search and pagination, status statistics, automated tests, an H2 development setup, a PostgreSQL/Docker option, and a GitHub Actions workflow that runs the Maven tests.”

Only describe features you can personally demonstrate from the repository.

## Request lifecycle

1. The client sends JSON to `JobApplicationController`.
2. Spring MVC maps the request to a DTO; Bean Validation checks constraints.
3. The controller delegates to `JobApplicationService`.
4. The service applies business rules, including duplicate checks and not-found handling.
5. `JobApplicationRepository` uses Spring Data JPA/Hibernate to persist or query entities.
6. The service maps data to a response DTO.
7. Spring serializes the DTO to JSON; `GlobalExceptionHandler` maps expected errors to consistent HTTP responses.

## Design choices to explain

### Why separate controller, service, repository, entity, and DTO?
Each has a distinct responsibility. This keeps HTTP concerns separate from business rules and persistence details, makes the service easier to unit-test, and avoids exposing the database entity as the public API contract.

### Why Spring Data JPA?
It reduces boilerplate for common persistence operations. Repository method names can express common filters, while Hibernate implements the object-relational mapping. It does not remove the need to understand SQL, transactions, lazy/eager loading, indexes, or query performance.

### Why validate at the API boundary?
Invalid input should be rejected before it reaches business logic or the database. The global exception handler turns validation failures into readable field-level responses.

### Why store enum values as strings?
String values are readable in the database and avoid depending on enum ordinal positions.

### What does pagination solve?
It limits how much data a request returns and lets clients navigate larger result sets. In a production API, I would also enforce a maximum page size and review sorting fields.

## Likely interview questions

**Q: Is this microservices architecture?**  
A: No. It is a modular monolith with clear internal layers. That is simpler to run and appropriate for this project’s scope. Splitting it into microservices would add network, deployment, and consistency complexity without a demonstrated need.

**Q: Where should business logic live?**  
A: In the service layer, rather than in the controller or repository. The repository should focus on data access.

**Q: What is the difference between an entity and a DTO?**  
A: The entity models persistence and ORM mapping. A DTO models the API input/output contract. Keeping them separate lets either evolve without automatically changing the other.

**Q: How do you handle missing records?**  
A: The service throws a not-found exception and the global exception handler maps it to a 404 response.

**Q: How do you handle invalid requests?**  
A: Bean Validation checks the request DTO; the global handler returns a 400 response with field errors.

**Q: What is tested?**  
A: The repository includes service/controller tests using the Spring test stack and mocks where appropriate. Before an interview, run `mvn test`, inspect the test cases, and be able to describe what each test verifies. Do not claim a coverage percentage unless you measure it.

**Q: What are the limitations?**  
A: It is a learning/portfolio API, not a production-ready multi-user service. It currently lacks authentication, authorization, per-user data isolation, database migrations, rate limiting, and a deployed production environment.

**Q: What would you do next?**  
A: Add Spring Security with user-scoped authorization, Flyway migrations, OpenAPI/Swagger, integration tests against PostgreSQL using Testcontainers, stricter pagination limits, and observability.

## Before you discuss this project in an interview

- Run the app locally and exercise create, list, filter, update, delete, and stats endpoints.
- Run `mvn test` and read the test classes.
- Explain one validation error and one 404 case by tracing the code.
- Be ready to describe one bug or design decision you personally investigated.
- If asked whether this was built at work, say clearly that it is a personal portfolio project.
