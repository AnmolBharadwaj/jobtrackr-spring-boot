# API Reference

Base URL: `http://localhost:8080`

## Application status values

`APPLIED`, `SCREENING`, `INTERVIEW`, `OFFER`, `REJECTED`, `WITHDRAWN`

## POST /api/applications

Creates a job application.

### Request

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

### Success: `201 Created`

The response contains the generated ID and timestamps. The `Location` header points to `/api/applications/{id}`.

## GET /api/applications

Optional parameters:

- `status`: exact enum status
- `q`: search company name or role
- `page`: zero-based page index
- `size`: page size
- `sort`: Spring Data sort expression, for example `appliedDate,desc`

Examples:

```text
/api/applications?status=SCREENING
/api/applications?q=backend
/api/applications?page=0&size=20&sort=companyName,asc
```

## GET /api/applications/{id}

Returns one application or `404 Not Found`.

## PUT /api/applications/{id}

Replaces the editable fields of the application and updates `updatedAt`.

## DELETE /api/applications/{id}

Deletes the application. Returns `204 No Content`.

## GET /api/applications/stats

Returns:

```json
{
  "totalApplications": 12,
  "byStatus": {
    "APPLIED": 5,
    "SCREENING": 2,
    "INTERVIEW": 3,
    "OFFER": 1,
    "REJECTED": 1,
    "WITHDRAWN": 0
  }
}
```

## Validation error shape

```json
{
  "timestamp": "2026-10-07T01:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/applications",
  "fieldErrors": {
    "companyName": "Company name is required",
    "role": "Role is required"
  }
}
```