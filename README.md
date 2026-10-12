# JobTrackr — Spring Boot REST API

[![Java CI](https://github.com/AnmolBharadwaj/jobtrackr-spring-boot/actions/workflows/ci.yml/badge.svg)](https://github.com/AnmolBharadwaj/jobtrackr-spring-boot/actions/workflows/ci.yml)

JobTrackr is a portfolio project for managing job applications through a REST API. It demonstrates a layered Java backend, persistence with Spring Data JPA/Hibernate, request validation, consistent API errors, filtering, pagination, automated tests, and a CI workflow.

> **Project status:** Learning/portfolio application. It is not a production-ready multi-user service and is not currently advertised as a deployed hosted API.

## Features

- CRUD endpoints for job applications
- Search by company or role, status filtering, pagination, and sorting
- Summary statistics grouped by application status
- DTOs to separate the API contract from JPA entities
- Bean Validation and structured field-level error responses
- Duplicate company/role detection and clean 404 handling
- H2 for quick local development; PostgreSQL profile and Docker Compose option
- JUnit/Spring tests and GitHub Actions CI (`mvn -B test`)

## Tech stack

Java 21 · Spring Boot 3.5.x · Spring MVC · Spring Data JPA · Hibernate · Jakarta Bean Validation · H2 · PostgreSQL · Maven · JUnit 5 · Mockito · Docker · GitHub Actions

## Architecture

```text
HTTP client (curl / Postman)
          |
          v
JobApplicationController  -> HTTP mapping + request validation
          |
          v
JobApplicationService     -> business rules + orchestration
          |
          v
JobApplicationRepository  -> Spring Data JPA
          |
          v
H2 / PostgreSQL           -> persistence
```

DTOs keep the request/response contract separate from persistence entities. The service layer owns business rules; the repository layer focuses on data access.

## Run locally

Requirements: JDK 21 and Maven 3.9+.

```bash
git clone https://github.com/AnmolBharadwaj/jobtrackr-spring-boot.git
cd jobtrackr-spring-boot
mvn test
mvn spring-boot:run
```

The API starts at `http://localhost:8080`. The default profile uses H2.

## Run with PostgreSQL and Docker

With Docker installed and running:

```bash
docker compose up --build
```

This starts the API and PostgreSQL service. Configuration is in `docker-compose.yml` and the PostgreSQL Spring profile. Do not use the sample setup as-is for production credentials or deployment.

## API overview

Base URL: `http://localhost:8080`

| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/applications` | Create an application |
| GET | `/api/applications` | List, search, filter, paginate, sort |
| GET | `/api/applications/{id}` | Get one application |
| PUT | `/api/applications/{id}` | Update an application |
| DELETE | `/api/applications/{id}` | Delete an application |
| GET | `/api/applications/stats` | Summary counts by status |

Status values: `APPLIED`, `SCREENING`, `INTERVIEW`, `OFFER`, `REJECTED`, `WITHDRAWN`.

Examples:

```text
GET /api/applications?page=0&size=5
GET /api/applications?status=INTERVIEW
GET /api/applications?q=java&page=0&size=10
GET /api/applications?sort=appliedDate,desc
```

See [API Reference](docs/API.md) for request/response examples and [Smoke Test Checklist](docs/SMOKE_TEST.md) for runnable curl commands.

## Business rules

1. Company name and role are required.
2. Applied date cannot be in the future.
3. Duplicate company + role applications are rejected.
4. Missing IDs return a structured 404 response.
5. Invalid request bodies return a structured 400 response with field-level errors.
6. Empty optional strings are normalized to null.

## Tests and CI

Run all automated tests with:

```bash
mvn test
```

GitHub Actions runs the Maven test suite on pushes to `main` and pull requests targeting `main`. Check the badge above for the current status.

## Interview preparation

- [Interview Guide](docs/INTERVIEW_GUIDE.md) — request flow, design choices, likely questions, and limitations
- [Interview Notes](docs/INTERVIEW_NOTES.md) — concise explanations of key Spring concepts
- [API Reference](docs/API.md) — endpoints and error format
- [Smoke Test Checklist](docs/SMOKE_TEST.md) — local test steps

## Known limitations and next steps

This project does not yet implement authentication, authorization, per-user data isolation, database migrations, rate limiting, or a deployed production environment. Sensible next improvements include Spring Security, Flyway migrations, OpenAPI documentation, integration tests against PostgreSQL/Testcontainers, observability, and stricter pagination limits.

## License

MIT
