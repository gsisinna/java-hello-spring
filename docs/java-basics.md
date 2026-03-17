# Java Basics Guide

This repo covers the most important beginner Java concepts through small, focused classes.

## Classes and objects

Main example:

- `basics/model/Student.java`

What to notice:

- a class defines data and behavior
- `Student student = new Student("Mia", 19);` creates an object
- fields store state like `name`, `age`, `subjects`, and `scores`

## Variables and types

Examples in `Student.java`:

- `long id`
- `String name`
- `int age`
- `boolean active`
- `List<String> subjects`
- `Map<String, Integer> scores`

Learn the difference between:

- primitive types
  - `int`, `long`, `boolean`
- object/reference types
  - `String`, `List`, `Map`, custom classes

## Constructors

`Student` has overloaded constructors.

Why that matters:

- one constructor gives a simple entry point
- another constructor allows more detailed initialization

This is common in real Java code when you want both convenience and control.

## Methods

Examples:

- `activate()`
- `enroll(String subject)`
- `addScore(String subject, int score)`
- `level()`
- `totalScore()`

Methods:

- receive input through parameters
- use object state
- return values or change state

## `if` and `for`

Examples:

- `if` in `enroll`, `addScore`, and `level`
- `for` in `totalScore` and `subjectSummary`

Key idea:

- `if` chooses behavior
- `for` repeats behavior

## List and Map

`List<String>`:

- keeps ordered values
- used here for subjects

`Map<String, Integer>`:

- stores key/value pairs
- used here for scores

These are core Java collection types you will use constantly.

## Exceptions

Example:

- `InvalidScoreException`

Used in:

- `Student.addScore`

Reason:

- exceptions stop invalid behavior early
- they make failure explicit

## Packages

Packages organize code by topic and responsibility.

Examples:

- `com.example.demo.basics.model`
- `com.example.demo.spring.service`

This is how Java projects stay readable as they grow.

## Annotations

Examples:

- custom annotation: `@LearningExample`
- framework annotations later in Spring: `@Service`, `@RestController`, `@Entity`

Annotations add metadata to classes and methods. In Spring, annotations drive framework behavior.

## Interfaces

Examples:

- `Notifier`
- `EmailNotifier`

Key idea:

- an interface defines a contract
- classes implement that contract
- callers can depend on the interface instead of the concrete class

This becomes very important in framework code and testable design.

## Inheritance and polymorphism

Examples:

- `Person`
- `LearningStudent`

Key idea:

- child classes reuse parent behavior
- child classes can override methods
- polymorphism lets you use the parent type while executing child behavior

## Generics

Example:

- `Box<T>`

Key idea:

- generics let you write reusable classes while keeping type safety
- `Box<String>` and `Box<Integer>` use the same class with different types

## Streams and lambdas

Example:

- `StudentAnalytics`

Key operations:

- `stream()`
- `filter(...)`
- `map(...)`
- `collect(...)`

Use streams when you want to transform or summarize collections in a readable pipeline.

## How to practice

Good small exercises:

1. Add `averageScore()` to `Student`
2. Add a second `Notifier` implementation
3. Add another subclass of `Person`
4. Add a generic method to `Box`
5. Add a new analytics method using streams
