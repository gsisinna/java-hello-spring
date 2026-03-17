# Spring Boot Guide

This repo contains two Spring learning tracks:

- a simple in-memory student API
- a more realistic secured course API with validation and persistence

## Spring Boot structure

A common Spring Boot flow is:

1. controller
2. service
3. repository
4. model/document

This repo shows both:

- simple flow with in-memory data
- document-backed flow with MongoDB

## Controllers

Examples:

- `StudentController`
- `CourseController`

Controller responsibilities:

- receive HTTP requests
- map path/query/body data
- call services
- return response DTOs

Typical annotations:

- `@RestController`
- `@RequestMapping`
- `@GetMapping`
- `@PostMapping`

## Services

Examples:

- `StudentService`
- `CourseService`

Service responsibilities:

- business logic
- orchestration
- mapping between models
- enforcing application rules

Keep controllers thin and move logic here.

## Dependency injection

This repo uses constructor injection.

Examples:

- `StudentController(StudentService studentService)`
- `CourseService(CourseRepository courseRepository)`

Why it matters:

- easier testing
- clearer dependencies
- better separation of responsibilities

## Request and response models

Examples:

- `CreateStudentRequest`
- `StudentResponse`
- `CreateCourseRequest`
- `CourseResponse`
- `ErrorResponse`

Why use DTOs:

- avoid exposing internal structure directly
- make API contracts explicit
- support validation and docs generation

## Validation

Main example:

- `CreateCourseRequest`

Annotations:

- `@Valid`
- `@NotBlank`
- `@Size`
- `@Min`

Flow:

1. request hits controller
2. Spring validates the DTO
3. invalid input triggers an exception
4. `ApiExceptionHandler` turns it into a clean error response

## Configuration properties

Main files:

- `AppLearningProperties`
- `LearningInfoController`

Key idea:

- `@ConfigurationProperties` maps structured YAML into a typed Java object
- this is cleaner than scattering config lookups through the code
- the `/api/learning-info` endpoint makes the bound values visible at runtime

## Exception handling

Main example:

- `ApiExceptionHandler`

This centralizes API errors so controllers stay focused on happy-path behavior.

Handled cases include:

- resource not found
- invalid score
- request validation failures

## MongoDB and JSON documents

Main files:

- `CourseDocument`
- `CourseRepository`
- `application.yml`
- `CourseDataInitializer`

Key ideas:

- `@Document` maps a class to a MongoDB collection
- a `MongoRepository` gives CRUD operations
- `CourseLevel` enum keeps the `level` field restricted to valid values
- MongoDB stores JSON-like documents instead of rows
- `CourseDataInitializer` seeds example documents at startup

## Spring Security

Main file:

- `SecurityConfig`

What it shows:

- route-level authorization
- open routes for docs and simple examples
- protected routes for `/api/courses/**`
- in-memory user for learning
- HTTP Basic authentication

Credentials in this repo:

- username: `student`
- password: `password`

## Swagger and OpenAPI

Main files:

- `OpenApiConfig`
- controller/model annotations
- `openapi.yaml`

What it teaches:

- code-first API docs
- generated JSON/YAML specs
- Swagger UI as an interactive learning tool

## Good next exercises

1. Add `DELETE /api/courses/{id}`
2. Add `PUT /api/courses/{id}`
3. Add enum-based course levels
4. Add a query endpoint such as `GET /api/courses?published=true`
5. Replace basic auth with role-based rules
