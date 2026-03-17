package com.example.demo.spring.model;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;

@Schema(name = "CreateStudentRequest", description = "Request body used to create a new student.")
public record CreateStudentRequest(
	@Schema(description = "Student name", example = "Lina")
	String name,
	@Schema(description = "Student age", example = "23")
	int age,
	@Schema(description = "Whether the student starts as active", example = "true")
	boolean active,
	@Schema(description = "Topics that the student is studying", example = "[\"annotations\", \"controller\"]")
	List<String> subjects,
	@Schema(description = "Example scores by subject", example = "{\"java\":91,\"spring\":89}")
	Map<String, Integer> scores
) {
}
