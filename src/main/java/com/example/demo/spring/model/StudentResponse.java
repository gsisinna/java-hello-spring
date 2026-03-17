package com.example.demo.spring.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(name = "StudentResponse", description = "Response returned by the student API.")
// Outgoing DTO used instead of exposing internal domain objects directly.
public record StudentResponse(
	@Schema(description = "Generated student id", example = "3")
	long id,
	@Schema(description = "Student name", example = "Lina")
	String name,
	@Schema(description = "Student age", example = "23")
	int age,
	@Schema(description = "Whether the student is active", example = "true")
	boolean active,
	@Schema(description = "Derived level based on age", example = "adult")
	String level,
	@Schema(description = "Topics being studied", example = "[\"annotations\", \"controller\"]")
	List<String> subjects,
	@Schema(description = "Scores by subject", example = "{\"java\":91,\"spring\":89}")
	Map<String, Integer> scores,
	@Schema(description = "Total of all score values", example = "180")
	int totalScore,
	@Schema(description = "Readable summary of the subjects list", example = "annotations, controller")
	String subjectSummary
) {
}
