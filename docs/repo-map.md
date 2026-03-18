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

- `spring.persistence.domain`
  - framework-independent business model
- `spring.persistence.document`
  - MongoDB document classes
- `spring.persistence.repository`
  - Spring Data repository interfaces used only by the adapter
- `spring.persistence.config`
  - seed data and persistence bootstrap
- `spring.persistence.service`
  - input port and application service
- `spring.persistence.store`
  - output port plus MongoDB adapter and mapper
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
  - app config, MongoDB config, SpringDoc config
- `openapi.yaml`
  - generated OpenAPI YAML committed at repo root

## Test packages

Tests mirror the production code structure on purpose.

- basics tests
  - explain Java concepts with assertions
- service tests
  - isolate business logic
- config tests
  - verify seed/bootstrap behavior
- controller tests
  - verify HTTP behavior
