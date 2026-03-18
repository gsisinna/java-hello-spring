# Architecture Guide

This repo uses two architectural levels on purpose:

- a simple layered flow for the student example
- a more explicit ports-and-adapters flow for the course example

That split is intentional for learning. You can start with the simpler version and then see how a more maintainable design looks when persistence and security become more important.

## High-level structure

```text
HTTP request
  -> controller
  -> use-case interface
  -> application service
  -> store interface
  -> Mongo adapter
  -> Spring Data repository
  -> MongoDB
```

For responses, the flow goes back in the opposite direction, with the controller mapping domain objects into response DTOs.

## Why the course module is more structured

The student flow is intentionally small and direct so beginners can learn controller/service/repository basics first.

The course flow uses stronger boundaries because it is the better example for long-term maintainability:

- the controller should not know MongoDB details
- the application service should not depend on web DTOs
- the domain model should not depend on Spring Data classes
- persistence-specific mapping should live in the adapter layer

This keeps each layer focused on one job.

## Main patterns used

### Layered architecture

The repo still follows a familiar layered shape:

- controller layer
- service/application layer
- persistence layer

That makes the code easy to navigate.

### Dependency inversion

The application service depends on `CourseStore`, not on `MongoRepository`.

That means the core use case depends on an interface, and the Mongo adapter implements it. This is the main architectural improvement in the repo.

### Ports and adapters

The course module uses a lightweight hexagonal style:

- input port: `CourseService`
- output port: `CourseStore`
- adapter: `MongoCourseStore`

This is useful because it makes framework code replaceable instead of central to the core logic.

### Repository pattern

`CourseRepository` is the Spring Data repository for MongoDB.

It is not used directly by the controller or application service anymore. It sits behind `MongoCourseStore`.

### Mapper pattern

There are two kinds of translation in the course module:

- controller maps request DTOs into application commands and domain objects into response DTOs
- `CourseDocumentMapper` maps domain objects into Mongo documents and back

This avoids leaking transport or database concerns across layers.

## Package roles in the course module

- `spring.persistence.domain`
  - framework-independent domain model
- `spring.persistence.service`
  - input port and application service
- `spring.persistence.store`
  - output port and Mongo adapter
- `spring.persistence.document`
  - MongoDB document classes
- `spring.persistence.repository`
  - Spring Data repository interfaces
- `spring.persistence.controller`
  - HTTP entry points and request/response mapping

## Why this is better than the previous version

Before the refactor:

- the service knew about request DTOs
- the service returned response DTOs
- the service depended directly on the Mongo repository

After the refactor:

- the controller owns HTTP DTO mapping
- the service owns use-case logic
- the store adapter owns persistence translation
- the domain model stays free of Spring and Mongo classes

That is a cleaner separation of responsibilities.

## Tradeoff

This architecture adds more files.

That is the cost of clearer boundaries.

For a tiny project, that would sometimes be too much. For a learning repo, it is useful because it shows what "better architecture" actually looks like in code rather than only describing it in theory.
