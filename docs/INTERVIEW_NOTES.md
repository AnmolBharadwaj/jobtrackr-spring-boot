# Interview Notes — JobTrackr

## Explain the request flow

1. A client sends an HTTP request to the controller.
2. The controller validates the request body and maps HTTP concepts to Java objects.
3. The controller calls the service. It should not contain business rules.
4. The service applies rules such as duplicate detection and delegates persistence.
5. The repository uses Spring Data JPA to talk to the database.
6. The service converts the entity into a response DTO.
7. Spring serializes the DTO as JSON and sends the HTTP response.

## Why constructor injection?

The dependencies are explicit, the class can be instantiated easily in tests, and required dependencies cannot be silently omitted.

## Why use a DTO?

The persistence entity represents database structure. The DTO represents the API contract. Keeping them separate reduces coupling and prevents accidental exposure of persistence details.

## Why `@Enumerated(EnumType.STRING)`?

It stores readable status values such as `INTERVIEW` instead of numeric ordinal values. Reordering enum constants therefore does not silently change existing database meanings.

## Why a service layer?

The service layer is a natural boundary for business rules and transaction handling. It prevents controllers from becoming a dump of database calls and makes the business logic easier to test.

## What does Spring Data JPA provide?

`JpaRepository` gives common CRUD operations and supports derived query methods. In this project, methods such as `findByStatus(...)` are converted into SQL/JPA queries by Spring Data.

## What would you improve for production?

- Add authentication and authorization with Spring Security + JWT.
- Add database migrations with Flyway or Liquibase instead of relying on `ddl-auto=update`.
- Add structured logging and correlation IDs.
- Add OpenAPI documentation.
- Add stricter pagination limits.
- Add integration tests against PostgreSQL/Testcontainers.
- Add observability and health checks.
- Add per-user data isolation.