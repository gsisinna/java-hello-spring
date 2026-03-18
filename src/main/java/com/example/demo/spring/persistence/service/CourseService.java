package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.domain.Course;

import java.util.List;

// Input port: the controller depends on this interface instead of a concrete implementation.
public interface CourseService {

	List<Course> findAll();

	Course findById(String id);

	Course create(UpsertCourseCommand command);

	Course update(String id, UpsertCourseCommand command);

	void delete(String id);
}
