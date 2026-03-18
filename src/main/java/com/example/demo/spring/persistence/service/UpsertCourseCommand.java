package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.model.CourseLevel;

// Application input model: what the use case needs, without any web or Mongo concerns attached.
public record UpsertCourseCommand(
	String title,
	CourseLevel level,
	int durationInHours,
	boolean published
) {
}
