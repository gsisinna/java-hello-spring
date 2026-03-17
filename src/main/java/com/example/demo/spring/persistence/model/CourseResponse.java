package com.example.demo.spring.persistence.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CourseResponse", description = "Persistent course returned from the database-backed API.")
// Response DTO for the secured course API.
public record CourseResponse(
	@Schema(description = "Generated database id", example = "1")
	Long id,
	@Schema(description = "Course title", example = "Spring Boot Security")
	String title,
	@Schema(description = "Course level", example = "INTERMEDIATE")
	CourseLevel level,
	@Schema(description = "Estimated duration in hours", example = "8")
	int durationInHours,
	@Schema(description = "Whether the course is published", example = "true")
	boolean published
) {
}
