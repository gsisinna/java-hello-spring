# Learning Path

This repo works best if you study it in stages instead of reading everything at once.

## Stage 1: Plain Java objects

Start with:

- `src/main/java/com/example/demo/basics/model/Student.java`
- `src/test/java/com/example/demo/basics/model/StudentTest.java`

Learn:

- classes and objects
- fields and primitive/object types
- constructors
- methods
- `if`
- `for`
- `List`
- `Map`
- custom exceptions

## Stage 2: More Java building blocks

Continue with:

- `basics/interfaces`
- `basics/inheritance`
- `basics/generics`
- `basics/streams`

Learn:

- interfaces and implementation
- inheritance and polymorphism
- generic types
- streams, `filter`, `map`, `collect`

## Stage 3: First Spring MVC example

Read:

- `spring/controller/StudentController.java`
- `spring/service/StudentService.java`
- `spring/repository/InMemoryStudentRepository.java`
- `spring/model/*`

Learn:

- controller
- service
- dependency injection
- request/response model
- REST endpoint structure

## Stage 4: API documentation

Read:

- `spring/config/OpenApiConfig.java`
- Swagger/OpenAPI annotations in controllers and models
- `openapi.yaml`

Learn:

- OpenAPI generation
- Swagger UI
- how code annotations become API documentation

## Stage 5: Validation, persistence, security

Read:

- `spring/persistence/model/CreateCourseRequest.java`
- `spring/persistence/document/CourseDocument.java`
- `spring/persistence/repository/CourseRepository.java`
- `spring/persistence/config/CourseDataInitializer.java`
- `spring/persistence/service/CourseService.java`
- `spring/persistence/controller/CourseController.java`
- `spring/security/SecurityConfig.java`

Learn:

- validation with `@Valid`
- constraints like `@NotBlank` and `@Min`
- MongoDB document mapping
- JSON request/response flow
- basic auth with Spring Security

## Stage 6: Testing layers

Read:

- `CourseServiceTest`
- `CourseDataInitializerTest`
- `StudentControllerTest`
- `CourseControllerTest`

Learn:

- unit tests
- focused configuration tests
- controller tests with `MockMvc`
- security test helpers

## Good practice while learning

For each topic:

1. Read the production class.
2. Read the matching test.
3. Change one behavior.
4. Run `./gradlew test`.
5. If the tests fail, understand why before editing again.
