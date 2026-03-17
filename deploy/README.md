# Production Deployment

This folder contains a simple single-server deployment target for the app.

## Files

- `docker-compose.prod.yml`
  - production stack with the app and MongoDB
- `app.env.example`
  - environment variables you should copy and customize before deployment

## Quick start

1. Copy the example env file:

```bash
cp deploy/app.env.example deploy/app.env
```

2. Edit the passwords and usernames in `deploy/app.env`.

3. Start the production stack:

```bash
docker compose --env-file deploy/app.env -f deploy/docker-compose.prod.yml up -d --build
```

4. Check health:

```bash
curl http://localhost:8080/actuator/health
```

## Notes

- The production profile uses MongoDB for persisted course documents.
- Swagger UI is disabled in the production profile.
- The app still exposes `/actuator/health` for deployment checks.
- For internet-facing deployment, put a reverse proxy or load balancer in front of the app.
