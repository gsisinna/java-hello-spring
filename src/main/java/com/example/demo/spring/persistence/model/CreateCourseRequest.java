package com.example.demo.spring.persistence.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(name = "CreateCourseRequest", description = "Request body used to create a MongoDB-backed course.")
// Request DTO with validation rules that Spring checks before the service runs.
public record CreateCourseRequest(
	@NotBlank
	@Size(min = 3, max = 100)
	@Schema(description = "Course title", example = "Spring Boot Security")
	String title,
	@NotNull
	@Schema(description = "Course level", example = "INTERMEDIATE")
	CourseLevel level,
	@Min(1)
	@Schema(description = "Estimated duration in hours", example = "8")
	int durationInHours,
	@Schema(description = "Whether the course is published", example = "true")
	boolean published
) {
}
