# Java + Spring Boot Learning Repo

This project is built so you can learn, test, and modify small examples instead of reading a large codebase.

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

### Spring Boot

- controller
- service
- dependency injection
- request/response model

## Start here

Read these files in order:

1. `src/main/java/com/example/demo/basics/model/Student.java`
2. `src/test/java/com/example/demo/basics/model/StudentTest.java`
3. `src/main/java/com/example/demo/spring/service/StudentService.java`
4. `src/main/java/com/example/demo/spring/controller/StudentController.java`
5. `src/test/java/com/example/demo/spring/controller/StudentControllerTest.java`

## Package guide

- `com.example.demo.basics.annotations`
  - custom annotation example
- `com.example.demo.basics.exceptions`
  - custom exception example
- `com.example.demo.basics.model`
  - plain Java class with fields, constructors, methods, `if`, `for`, `List`, and `Map`
- `com.example.demo.spring.repository`
  - in-memory data storage
- `com.example.demo.spring.service`
  - business logic
- `com.example.demo.spring.controller`
  - HTTP endpoints and error handling
- `com.example.demo.spring.model`
  - request and response models

## Run the app

```bash
./gradlew bootRun
```

The app starts on `http://localhost:8080`.

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

## Run the tests

```bash
./gradlew test
```

## What to edit first

1. In `Student.java`, change how `level()` works.
2. In `Student.java`, add another method such as `averageScore()`.
3. In `StudentService.java`, change how new students are created.
4. In `StudentController.java`, add a new endpoint such as `DELETE /api/students/{id}`.
5. In `StudentControllerTest.java`, add a test before you change the code.

## Quick concept map

- Constructors: `Student(...)`
- Methods: `enroll`, `addScore`, `level`, `totalScore`
- `if`: `enroll`, `addScore`, `level`
- `for`: `totalScore`, `subjectSummary`, service mapping methods
- `List`: subjects
- `Map`: scores
- Exceptions: `InvalidScoreException`, `StudentNotFoundException`
- Annotations: `@LearningExample`, `@Service`, `@RestController`, `@RestControllerAdvice`
- Dependency injection: `StudentController` gets `StudentService`, `StudentService` gets `InMemoryStudentRepository`
