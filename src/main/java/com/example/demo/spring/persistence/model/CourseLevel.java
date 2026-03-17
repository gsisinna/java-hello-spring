package com.example.demo.spring.persistence.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Allowed course levels.")
// Enum example: use a fixed set of valid values instead of free-form strings.
public enum CourseLevel {
	BEGINNER,
	INTERMEDIATE,
	ADVANCED
}
