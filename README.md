# Java + Spring Boot Learning Repo

This project is built so you can learn, test, and modify small examples instead of reading a large codebase.

Full repo learning notes are in [docs/README.md](./docs/README.md).

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
- Swagger UI
- OpenAPI YAML
- validation
- JPA + H2 persistence
- basic authentication with Spring Security
- unit, repository, and integration tests

## Start here

Read these files in order:

1. `src/main/java/com/example/demo/basics/model/Student.java`
2. `src/test/java/com/example/demo/basics/model/StudentTest.java`
3. `src/main/java/com/example/demo/basics/interfaces/Notifier.java`
4. `src/main/java/com/example/demo/basics/inheritance/LearningStudent.java`
5. `src/main/java/com/example/demo/basics/generics/Box.java`
6. `src/main/java/com/example/demo/basics/streams/StudentAnalytics.java`
7. `src/main/java/com/example/demo/spring/service/StudentService.java`
8. `src/main/java/com/example/demo/spring/persistence/service/CourseService.java`
9. `src/main/java/com/example/demo/spring/persistence/controller/CourseController.java`
10. `src/test/java/com/example/demo/spring/persistence/controller/CourseControllerIntegrationTest.java`

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
  - validation, JPA, H2, and security examples

## Run the app

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`.

## Run with Docker

Build and start the app container:

```bash
docker compose up --build app
```

Then open:

- `http://localhost:8080/api/students`
- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/h2-console`

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

Create a secured database-backed course:

```bash
curl -u student:password -X POST http://localhost:8080/api/courses \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Spring Data JPA",
    "level": "intermediate",
    "durationInHours": 7,
    "published": true
  }'
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
- repository tests: JPA + H2 with a Spring Boot integration test, such as `CourseRepositoryTest`
- integration tests: full Spring Boot + MockMvc, such as `StudentControllerTest` and `CourseControllerIntegrationTest`

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
6. In `CourseControllerIntegrationTest.java`, add a failing test before you change the code.

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
- Exceptions: `InvalidScoreException`, `StudentNotFoundException`
- Annotations: `@LearningExample`, `@Service`, `@RestController`, `@RestControllerAdvice`, `@Entity`, `@Valid`
- Dependency injection: `StudentController` gets `StudentService`, `StudentService` gets `InMemoryStudentRepository`
- Validation: `CreateCourseRequest`
- Persistence: `CourseEntity`, `CourseRepository`, H2
- Security: HTTP basic auth for `/api/courses/**` with user `student` / `password`
