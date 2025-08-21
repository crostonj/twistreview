# twistreview

A Java-based REST Web API using Spring Boot.

## Features
- Spring Boot REST API
- Sample controller with health check endpoint

## Getting Started

### Prerequisites
- Java 17 or later
- Maven 3.6+

### Build and Run

```
mvn spring-boot:run
```

Base URL (context path): `http://localhost:8080/reviews`

API endpoints:
- GET    `/reviews/api/reviews/{productId}` — list reviews for a product
- POST   `/reviews/api/reviews/{productId}` — add a review
- PUT    `/reviews/api/reviews/{productId}/{reviewId}` — update a review
- DELETE `/reviews/api/reviews/{productId}/{reviewId}` — delete a review

### Health Check

```
GET http://localhost:8080/reviews/actuator/health
```

Readiness/Liveness:
- `GET http://localhost:8080/reviews/actuator/health/readiness`
- `GET http://localhost:8080/reviews/actuator/health/liveness`

OpenAPI/Swagger:
- UI: `http://localhost:8080/reviews/swagger-ui/index.html`
- JSON: `http://localhost:8080/reviews/v3/api-docs`

## License
MIT
