# Deployment Guide

This page explains how the repository can be deployed as a small production-style service.

## Deployment target in this repo

The repo includes a practical single-host deployment target:

- Docker Compose
- Spring Boot app container
- MongoDB container
- production Spring profile
- externalized secrets through an env file

Main files:

- `deploy/docker-compose.prod.yml`
- `deploy/app.env.example`
- `src/main/resources/application-prod.yml`

## Why this deployment setup exists

The default app setup is for learning:

- local MongoDB URI
- Swagger UI enabled
- simple local credentials

The production setup changes that:

- MongoDB runs as a separate service
- credentials come from environment variables
- Swagger UI is disabled
- `/actuator/health` stays available for health checks

## Production profile

File:

- `application-prod.yml`

What it does:

- reads the MongoDB connection URI from env vars
- keeps actuator health enabled
- reads security credentials from env vars

## Security configuration for deployment

The app now reads auth settings from typed configuration instead of hardcoded strings.

See:

- `AppSecurityProperties`
- `SecurityConfig`

This means deployment credentials can change without code changes.

## Health endpoint

Public endpoint:

- `/actuator/health`

Use it for:

- container health checks
- load balancer checks
- quick smoke tests after deployment

## Deploy steps

1. Copy the env example:

```bash
cp deploy/app.env.example deploy/app.env
```

2. Change the secret values.

3. Start the stack:

```bash
docker compose --env-file deploy/app.env -f deploy/docker-compose.prod.yml up -d --build
```

4. Verify:

```bash
curl http://localhost:8080/actuator/health
curl -u "$APP_SECURITY_USERNAME:$APP_SECURITY_PASSWORD" http://localhost:8080/api/courses
```

## Files to review before real deployment

- `Dockerfile`
- `deploy/docker-compose.prod.yml`
- `deploy/app.env`
- `application-prod.yml`

## What is still intentionally simple

This repo is still a learning project, so deployment is not fully enterprise-grade yet.

Still missing if you wanted a harder production setup:

- MongoDB schema migration tooling
- TLS termination with Nginx/Caddy or a cloud load balancer
- image publishing to a registry
- secret manager integration
- metrics/log shipping
- autoscaling/orchestration such as Kubernetes
