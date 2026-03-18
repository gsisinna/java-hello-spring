# Frontend

This folder contains the dedicated frontend for the Java Spring learning repo.

Stack:

- Vue 3
- TypeScript
- Vite
- Vuetify
- Vue Router
- Pinia

## Purpose

The frontend is built to make the backend easier to learn and modify:

- dashboard for the repo overview
- student screen for the public JSON endpoints
- course screen for the secured MongoDB CRUD flow
- learning screen that maps the UI back to the backend architecture

## Run locally

From the repo root, start MongoDB and the Spring Boot backend first:

```bash
docker compose up -d mongo
./gradlew bootRun
```

Then start the frontend:

```bash
cd frontend
npm install
npm run dev
```

The frontend runs on `http://localhost:5173`.

During development, Vite proxies these paths to the backend on `http://localhost:8080`:

- `/api`
- `/v3`
- `/swagger-ui`
- `/actuator`

## Useful commands

```bash
npm run dev
npm run build
npm run type-check
```

## Notes

- The course screen uses the default backend learning credentials: `student` / `password`
- Swagger UI remains available through the backend at `/swagger-ui.html`
