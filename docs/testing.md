# Testing Guide

This repo uses tests as part of the teaching material.

## Why there are different test types

Real projects need more than one kind of test.

This repo shows:

- unit tests
- configuration tests
- web/controller tests

## Unit tests

Examples:

- `StudentTest`
- `NotifierTest`
- `LearningStudentTest`
- `BoxTest`
- `StudentAnalyticsTest`
- `CourseServiceTest`

Use unit tests when:

- no Spring container is needed
- logic is small and isolated
- feedback should be fast

What they teach:

- behavior-focused assertions
- testing single classes
- mocking with Mockito in `CourseServiceTest`

## Configuration tests

Example:

- `CourseDataInitializerTest`

Purpose:

- verify seed behavior without needing a live MongoDB server
- confirm the app only inserts sample documents when the collection is empty

## Controller tests

Examples:

- `StudentControllerTest`
- `CourseControllerTest`
- `OpenApiDocumentationTest`

These tests use:

- `@SpringBootTest` or `@WebMvcTest`
- `MockMvc`

What they teach:

- HTTP request/response testing
- JSON assertions
- authentication testing with `httpBasic(...)`
- documentation endpoint checks

## How to read a test well

For each test, identify:

1. setup
2. action
3. assertion

Example shape:

- create or mock data
- call the method or HTTP endpoint
- assert the result

## Commands

Run all tests:

```bash
./gradlew test
```

Run one test class:

```bash
./gradlew test --tests com.example.demo.spring.persistence.controller.CourseControllerTest
```

Run one test method:

```bash
./gradlew test --tests com.example.demo.basics.model.StudentTest.studentShowsCoreJavaConceptsInOneClass
```

## Recommended learning workflow

1. Pick a class
2. Read its test first
3. Read the production code
4. Change one thing
5. rerun the relevant test

This makes the feedback loop much faster.
