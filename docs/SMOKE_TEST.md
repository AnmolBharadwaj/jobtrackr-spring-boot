# Local Smoke Test Checklist

Run these checks after starting JobTrackr. They verify the API manually; they are not a substitute for automated tests.

## 1. Start the application

Requirements: JDK 21 and Maven 3.9+.

```bash
mvn test
mvn spring-boot:run
```

Default base URL: `http://localhost:8080`.

## 2. Create an application

```bash
curl -i -X POST http://localhost:8080/api/applications \
  -H "Content-Type: application/json" \
  -d '{
    "companyName": "Example Systems",
    "role": "Java Backend Developer",
    "location": "Gurugram",
    "status": "APPLIED",
    "source": "Company website",
    "appliedDate": "2026-10-12",
    "interviewDate": null,
    "notes": "Smoke test record"
  }'
```

Expected: `201 Created`, a JSON response with an ID, and a `Location` header.

## 3. List, search, filter, and paginate

```bash
curl "http://localhost:8080/api/applications"
curl "http://localhost:8080/api/applications?q=Example"
curl "http://localhost:8080/api/applications?status=APPLIED"
curl "http://localhost:8080/api/applications?page=0&size=5&sort=appliedDate,desc"
```

Expected: JSON results; pagination metadata is supplied by Spring Data.

## 4. Check status statistics

```bash
curl "http://localhost:8080/api/applications/stats"
```

Expected: total count and count grouped by application status.

## 5. Check error handling

Try an invalid body with a missing company name or role. Expected: `400 Bad Request` with field-level validation errors.

Try a non-existent ID, for example:

```bash
curl -i "http://localhost:8080/api/applications/999999"
```

Expected: `404 Not Found`.

## 6. Run with PostgreSQL (optional)

```bash
docker compose up --build
```

This requires Docker to be installed and running. Stop the services with `docker compose down`.

## 7. Before publishing or interviewing

- Run `mvn test` and confirm it passes locally.
- Verify the API examples still match the current code.
- Do not paste real personal data or credentials into example requests.
- Do not claim a deployed URL unless you actually deploy and test one.
