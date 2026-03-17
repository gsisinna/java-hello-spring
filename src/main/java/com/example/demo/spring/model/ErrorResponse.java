package com.example.demo.spring.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Error payload returned when the API rejects a request.")
public record ErrorResponse(
	@Schema(description = "Human-readable error message", example = "Course with id 99 was not found")
	String message,
	@Schema(description = "A small suggestion for the next action", example = "Check the resource id and try again")
	String hint
) {
}
