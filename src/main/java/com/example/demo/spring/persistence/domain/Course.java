package com.example.demo.spring.persistence.domain;

import com.example.demo.spring.persistence.model.CourseLevel;

// Domain model: framework-independent representation of a course in the application core.
public record Course(
	String id,
	String title,
	CourseLevel level,
	int durationInHours,
	boolean published
) {
}
