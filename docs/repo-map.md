# Repo Map

This page explains why each package exists.

## Entry point

- `com.example.demo.DemoApplication`
  - starts Spring Boot

## Java basics packages

- `basics.annotations`
  - custom annotation example
- `basics.exceptions`
  - custom exception example
- `basics.model`
  - core object model for Java syntax and behavior
- `basics.interfaces`
  - interface + implementation
- `basics.inheritance`
  - parent/child class example
- `basics.generics`
  - generic class example
- `basics.streams`
  - collection processing with streams

## Spring MVC packages

- `spring.controller`
  - student endpoints and centralized exception handling
- `spring.service`
  - application logic for the student example
- `spring.repository`
  - in-memory repository for the student example
- `spring.model`
  - DTOs for requests and responses
- `spring.exception`
  - student-specific exception

## Spring persistence packages

- `spring.persistence.entity`
  - JPA entity classes
- `spring.persistence.repository`
  - Spring Data repository interfaces
- `spring.persistence.service`
  - business logic using the repository layer
- `spring.persistence.controller`
  - secured HTTP endpoints
- `spring.persistence.model`
  - validated request/response DTOs

## Shared framework packages

- `spring.config`
  - OpenAPI configuration
- `spring.security`
  - Spring Security configuration
- `spring.springcommon.exception`
  - reusable exception types

## Resource files

- `application.yml`
  - app config, H2 config, SpringDoc config
- `data.sql`
  - seed database records
- `openapi.yaml`
  - generated OpenAPI YAML committed at repo root

## Test packages

Tests mirror the production code structure on purpose.

- basics tests
  - explain Java concepts with assertions
- service tests
  - isolate business logic
- repository tests
  - verify database persistence
- controller tests
  - verify HTTP behavior
