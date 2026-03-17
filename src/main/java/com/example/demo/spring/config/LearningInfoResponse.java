package com.example.demo.spring.config;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "LearningInfoResponse", description = "Typed configuration values exposed for learning purposes.")
// Response DTO for the configuration-properties example.
public record LearningInfoResponse(
	@Schema(description = "Configured learning title", example = "Java + Spring Boot Learning Repo")
	String title,
	@Schema(description = "Configured focus topics", example = "[\"java basics\", \"spring mvc\", \"jpa\"]")
	List<String> focusTopics
) {
}
