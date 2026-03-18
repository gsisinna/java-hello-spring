# Java + Spring Boot Learning Repo

This project is built so you can learn, test, and modify small examples instead of reading a large codebase.

Full repo learning notes are in [docs/README.md](./docs/README.md).
Deployment notes and production files are in [deploy/README.md](./deploy/README.md).

## Software architecture

The repo now teaches two architectural styles:

- a simple layered flow for the student example
- a ports-and-adapters style for the course example

Read [docs/architecture.md](./docs/architecture.md) for the design rationale and package boundaries.

## What this repo teaches

### Plain Java

- classes and objects
- methods
- variables and types
- `if` and `for`
- constructors
- `List` and `Map`
- exceptions
- packages
- annotations
- interfaces
- inheritance and polymorphism
- generics
- streams and lambdas

### Spring Boot

- controller
- service
- dependency injection
- request/response model
- JSON request/response flow
- Swagger UI
- OpenAPI YAML
- validation
- MongoDB persistence
- basic authentication with Spring Security
- unit and web-layer tests
- enums
- `@ConfigurationProperties`

### Frontend

- Vue 3 + TypeScript with Vite
- Vuetify UI layer
- Pinia state management
- Vue Router navigation
- backend integration with the student and course APIs
- a UI workflow for learning, testing, and modifying the backend

## Start here

Read these files in order:

1. `src/main/java/com/example/demo/basics/model/Student.java`
2. `src/test/java/com/example/demo/basics/model/StudentTest.java`
3. `src/main/java/com/example/demo/basics/interfaces/Notifier.java`
4. `src/main/java/com/example/demo/basics/inheritance/LearningStudent.java`
5. `src/main/java/com/example/demo/basics/generics/Box.java`
6. `src/main/java/com/example/demo/basics/streams/StudentAnalytics.java`
7. `src/main/java/com/example/demo/spring/service/StudentService.java`
8. `src/main/java/com/example/demo/spring/persistence/domain/Course.java`
9. `src/main/java/com/example/demo/spring/persistence/service/CourseService.java`
10. `src/main/java/com/example/demo/spring/persistence/service/CourseApplicationService.java`
11. `src/main/java/com/example/demo/spring/persistence/store/MongoCourseStore.java`
12. `src/main/java/com/example/demo/spring/persistence/controller/CourseController.java`
13. `src/main/java/com/example/demo/spring/config/AppLearningProperties.java`
14. `src/test/java/com/example/demo/spring/persistence/controller/CourseControllerTest.java`

## Package guide

- `com.example.demo.basics.annotations`
  - custom annotation example
- `com.example.demo.basics.exceptions`
  - custom exception example
- `com.example.demo.basics.model`
  - plain Java class with fields, constructors, methods, `if`, `for`, `List`, and `Map`
- `com.example.demo.basics.interfaces`
  - interface and implementation examples
- `com.example.demo.basics.inheritance`
  - inheritance and polymorphism examples
- `com.example.demo.basics.generics`
  - generic class example
- `com.example.demo.basics.streams`
  - streams and lambda-style collection processing
- `com.example.demo.spring.repository`
  - in-memory data storage
- `com.example.demo.spring.service`
  - business logic
- `com.example.demo.spring.controller`
  - HTTP endpoints and error handling
- `com.example.demo.spring.model`
  - request and response models
- `com.example.demo.spring.persistence`
  - validation, MongoDB documents, layered architecture, and security examples
- `com.example.demo.spring.config`
  - OpenAPI config and typed configuration properties

## Run the app

Start MongoDB first:

```bash
docker compose up -d mongo
```

Then run the app:

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`.

## Run the frontend

The frontend lives in [frontend/README.md](./frontend/README.md).

For a one-command local preview of frontend + backend together, run:

```bash
./scripts/start-dev.sh
```

That will:

- start MongoDB with Docker Compose
- start the Spring Boot backend on `http://localhost:8080`
- start the Vue frontend on `http://localhost:5173`

Press `Ctrl+C` to stop the frontend and backend process. MongoDB stays available in Docker.

Manual start is also available if you want separate terminals.

If MongoDB is already running locally and you do not want Docker in the loop, use:

```bash
./scripts/start-local-dev.sh
```

That script:

- assumes MongoDB is already reachable on `localhost:27017`
- stops old repo-owned Java and Vite processes
- starts the backend on `http://localhost:8080`
- starts the frontend on `http://localhost:5173`

To stop any previous repo dev processes without starting again, run:

```bash
./scripts/stop-dev.sh
```

Start the backend first, then run:

```bash
cd frontend
npm install
npm run dev
```

The frontend runs on `http://localhost:5173` and proxies API requests to the backend on `http://localhost:8080`.

## Production deployment

This repo now includes a simple production deployment target using Docker Compose and MongoDB.

Main files:

- `deploy/docker-compose.prod.yml`
- `deploy/app.env.example`
- `src/main/resources/application-prod.yml`

Main production differences:

- MongoDB for persisted course documents
- `prod` Spring profile
- externalized credentials
- public `/actuator/health` endpoint for health checks
- Swagger UI disabled in production

Quick start:

```bash
cp deploy/app.env.example deploy/app.env
docker compose --env-file deploy/app.env -f deploy/docker-compose.prod.yml up -d --build
curl http://localhost:8080/actuator/health
```

## Run with Docker

Build and start the app container:

```bash
docker compose up --build app
```

Then open:

- `http://localhost:8080/api/students`
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/api/courses`

To stop it:

```bash
docker compose down
```

## Try the API

Get the seeded students:

```bash
curl http://localhost:8080/api/students
```

Get one student:

```bash
curl http://localhost:8080/api/students/1
```

Create a student:

```bash
curl -X POST http://localhost:8080/api/students \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Lina",
    "age": 23,
    "active": true,
    "subjects": ["annotations", "controller"],
    "scores": {
      "java": 91,
      "spring": 89
    }
  }'
```

Get secured courses with basic auth:

```bash
curl -u student:password http://localhost:8080/api/courses
```

Show typed configuration properties:

```bash
curl http://localhost:8080/api/learning-info
```

Create a secured MongoDB-backed course:

```bash
curl -u student:password -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Data MongoDB",
    "level": "INTERMEDIATE",
    "durationInHours": 7,
    "published": true
  }'
```

Update a course:

```bash
curl -u student:password -X PUT http://localhost:8080/api/courses/course-101 \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Updated Java Generics",
    "level": "ADVANCED",
    "durationInHours": 10,
    "published": false
  }'
```

Delete a course:

```bash
curl -u student:password -X DELETE http://localhost:8080/api/courses/course-202
```

## Swagger and OpenAPI

After starting the app with `./gradlew bootRun`, open:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`
- OpenAPI YAML: `http://localhost:8080/v3/api-docs.yaml`

Swagger UI is generated from the controller and model annotations in the code.

To export the generated YAML to a file automatically, run:

```bash
./gradlew exportOpenApiYaml
```

That writes:

```text
openapi.yaml
```

## Run the tests

```bash
./gradlew test
```

Run the tests inside a container:

```bash
docker compose run --rm test
```

Different test layers in this repo:

- unit tests: small classes without Spring, such as `StudentTest` and `CourseServiceTest`
- configuration tests: focused tests such as `CourseDataInitializerTest`
- web/controller tests: `StudentControllerTest`, `CourseControllerTest`, `OpenApiDocumentationTest`, and `LearningInfoControllerTest`

## GitHub CI

This repo includes a GitHub Actions workflow at `.github/workflows/ci.yml`.

It runs on every push and pull request, sets up Java 17, and executes:

```bash
./gradlew test --no-daemon
```

## What to edit first

1. In `Student.java`, change how `level()` works.
2. In `Student.java`, add another method such as `averageScore()`.
3. In `StudentService.java`, change how new students are created.
4. In `StudentController.java`, add a new endpoint such as `DELETE /api/students/{id}`.
5. In `CourseController.java`, add a new secured endpoint such as `DELETE /api/courses/{id}`.
6. In `CourseControllerTest.java`, add a failing test before you change the code.

## Quick concept map

- Constructors: `Student(...)`
- Methods: `enroll`, `addScore`, `level`, `totalScore`
- `if`: `enroll`, `addScore`, `level`
- `for`: `totalScore`, `subjectSummary`, service mapping methods
- `List`: subjects
- `Map`: scores
- Interfaces: `Notifier`, `EmailNotifier`
- Inheritance: `Person`, `LearningStudent`
- Generics: `Box<T>`
- Streams: `StudentAnalytics`
- Enums: `CourseLevel`
- Exceptions: `InvalidScoreException`, `StudentNotFoundException`
- Annotations: `@LearningExample`, `@Service`, `@RestController`, `@RestControllerAdvice`, `@Document`, `@Valid`
- Dependency injection: `StudentController` gets `StudentService`, `StudentService` gets `InMemoryStudentRepository`
- Validation: `CreateCourseRequest`
- Persistence: `CourseDocument`, `CourseRepository`, MongoDB
- Security: HTTP basic auth for `/api/courses/**` with user `student` / `password`
- Typed configuration: `AppLearningProperties` bound from `application.yml`
