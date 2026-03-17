# Tooling Guide

This page explains how to run the repo and how the support tooling fits into the learning flow.

## Gradle

Main file:

- `build.gradle`

Useful commands:

```bash
./gradlew bootRun
./gradlew test
./gradlew bootJar
./gradlew exportOpenApiYaml
```

What they do:

- `bootRun`
  - runs the app for development
- `test`
  - runs the full test suite
- `bootJar`
  - builds a runnable Spring Boot jar
- `exportOpenApiYaml`
  - runs the app temporarily and writes `openapi.yaml`

## Docker

Main files:

- `Dockerfile`
- `docker-compose.yml`
- `deploy/docker-compose.prod.yml`
- `.dockerignore`

Commands:

```bash
docker compose up --build app
docker compose run --rm test
docker compose down
```

Use Docker when:

- you want a consistent runtime
- you want to test the app without relying on local Java tooling
- you want to deploy the app with the production compose stack

## GitHub Actions

Main file:

- `.github/workflows/ci.yml`

Purpose:

- run tests automatically on push and pull request

This is the first step toward real CI/CD habits.

## Production deployment files

Main files:

- `deploy/docker-compose.prod.yml`
- `deploy/app.env.example`
- `src/main/resources/application-prod.yml`

Use these when you want:

- MongoDB document storage
- externalized secrets
- health checks through `/actuator/health`
- a deployment-oriented compose stack

## OpenAPI and Swagger

Important outputs:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/v3/api-docs`
- `http://localhost:8080/v3/api-docs.yaml`
- `openapi.yaml`

Use these to compare:

- source code
- runtime docs
- committed API contract

## MongoDB

Useful while learning persistence:

- local MongoDB container through `docker compose up --build app`
- production-style MongoDB service through `deploy/docker-compose.prod.yml`

This lets you inspect how JSON requests become persisted MongoDB documents.

## Good habits to build from this repo

1. Run tests before and after edits
2. Keep business logic out of controllers
3. Document APIs with OpenAPI annotations
4. Use DTOs for request/response boundaries
5. Commit in small, topic-focused steps
