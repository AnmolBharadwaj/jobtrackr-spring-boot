# JobTrackr — Spring Boot Learning Project

A real-world, beginner-friendly REST API for tracking job applications. The project is intentionally built around the same concepts taught in the Telusko Java Spring Framework / Spring Boot path: Java + OOP, Maven, Spring IoC/DI, Spring MVC, REST APIs, Hibernate/JPA, Spring Data JPA, validation, exception handling, testing, and a first step toward Docker/PostgreSQL.

## Why this project?

A tiny `Hello World` app teaches syntax. A small but complete backend teaches how the pieces actually connect.

JobTrackr gives you a portfolio project you can explain from request → controller → service → repository → database and back again.

## Tech stack

- Java 21
- Spring Boot 3.5.16
- Spring Web / REST
- Spring Data JPA + Hibernate
- H2 for zero-setup local development
- PostgreSQL profile for a more production-like database
- Jakarta Bean Validation
- JUnit 5 + Mockito + Spring MVC Test
- Maven
- Docker + Docker Compose
- GitHub Actions CI

Spring Boot 3.5.16 is used here as the final open-source release in the 3.5.x generation.

## Architecture

```text
HTTP Client (Postman / curl)
          |
          v
   JobApplicationController
          |
          v
     JobApplicationService
          |
          v
   JobApplicationRepository
          |
          v
   H2 / PostgreSQL Database
```

The DTOs keep the API contract separate from the JPA entity. The service owns business rules. The repository owns persistence queries.

## Run locally with H2

Requirements:

- JDK 21
- Maven 3.9+

Run:

```bash
mvn spring-boot:run
```

The API starts on `http://localhost:8080`.

H2 console: `http://localhost:8080/h2-console`

Use:

```text
JDBC URL: jdbc:h2:file:./data/jobtrackr
User: sa
Password: <empty>
```

## Run with PostgreSQL + Docker

```bash
docker compose up --build
```

The app will be available at `http://localhost:8080` and PostgreSQL at port `5432`.

## API endpoints

### Create

`POST /api/applications`

```json
{
  "companyName": "Acme Technologies",
  "role": "Java Backend Developer",
  "location": "Gurugram",
  "status": "APPLIED",
  "source": "LinkedIn",
  "appliedDate": "2026-10-07",
  "interviewDate": null,
  "notes": "Resume submitted."
}
```

### List

`GET /api/applications`

Pagination and sorting are supported through Spring Data's `Pageable`.

Examples:

```text
GET /api/applications?page=0&size=5
GET /api/applications?status=INTERVIEW
GET /api/applications?q=java&page=0&size=10
```

### Get one

`GET /api/applications/{id}`

### Update

`PUT /api/applications/{id}`

### Delete

`DELETE /api/applications/{id}`

### Stats

`GET /api/applications/stats`

Returns total applications plus counts grouped by status.

## Business rules implemented

1. Company name and role are mandatory.
2. Applied date cannot be in the future.
3. Duplicate company + role applications are rejected.
4. Missing IDs return a clean `404` response.
5. Invalid request bodies return a structured `400` response with field-level errors.
6. Empty optional strings are normalized to `null`.

## Learning map

| Course concept | Where it appears |
|---|---|
| Core Java / OOP | Entities, services, enums, records, collections, streams |
| Maven | `pom.xml` |
| Spring Boot | `JobTrackrApplication` |
| IoC / Dependency Injection | Constructor injection in controller/service |
| Spring MVC | `@RestController`, mappings, request parameters |
| REST | CRUD endpoints + HTTP status codes |
| JPA / Hibernate | `JobApplication` entity + annotations |
| Spring Data JPA | `JobApplicationRepository` derived queries |
| Validation | `@Valid`, `@NotBlank`, `@NotNull`, `@PastOrPresent` |
| Exception handling | `GlobalExceptionHandler` |
| JUnit / Mockito | Unit tests in `src/test` |
| H2 | Default local database |
| PostgreSQL | `postgres` profile + Docker Compose |
| Docker | `Dockerfile`, `docker-compose.yml` |
| CI | `.github/workflows/ci.yml` |

The project deliberately follows the course progression from Java and Maven into Spring Boot, REST, JPA/Hibernate, and Spring Data JPA before moving toward security, Docker, cloud, and Spring AI.

## What to learn next

This project intentionally stops short of JWT/security and AI so the fundamentals stay clear. Once the basics in the course feel comfortable, the natural next branch is:

```text
JobTrackr v1
  -> Spring Security
  -> JWT authentication
  -> User accounts / per-user applications
  -> PostgreSQL + migrations
  -> OpenAPI / Swagger
  -> Docker deployment
  -> Spring AI assistant for job-search insights
```

## Interview one-liner

> “I built a layered Spring Boot REST API called JobTrackr to manage job applications. It uses Spring MVC for the API layer, a service layer for business rules, Spring Data JPA/Hibernate for persistence, H2/PostgreSQL for storage, Bean Validation and global exception handling for API robustness, plus JUnit/Mockito tests and Docker support.”

## License

MIT