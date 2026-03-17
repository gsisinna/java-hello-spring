# Spring Boot Guide

This repo contains two Spring learning tracks:

- a simple in-memory student API
- a more realistic secured course API with validation and persistence

## Spring Boot structure

A common Spring Boot flow is:

1. controller
2. service
3. repository
4. model/entity

This repo shows both:

- simple flow with in-memory data
- database-backed flow with JPA

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

## Exception handling

Main example:

- `ApiExceptionHandler`

This centralizes API errors so controllers stay focused on happy-path behavior.

Handled cases include:

- resource not found
- invalid score
- request validation failures

## JPA and H2

Main files:

- `CourseEntity`
- `CourseRepository`
- `application.yml`
- `data.sql`

Key ideas:

- `@Entity` maps a class to a database table
- a `JpaRepository` gives CRUD operations
- H2 provides an in-memory database for easy learning
- `data.sql` seeds example rows at startup

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

## H2 console

With the app running:

- `http://localhost:8080/h2-console`

Use JDBC URL from `application.yml`:

- `jdbc:h2:mem:learningdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`

This helps you connect API behavior to actual persisted data.

## Good next exercises

1. Add `DELETE /api/courses/{id}`
2. Add `PUT /api/courses/{id}`
3. Add enum-based course levels
4. Replace basic auth with role-based rules
5. Add `@Validated` service rules
