package com.example.demo.spring.persistence.store;

import com.example.demo.spring.persistence.domain.Course;

import java.util.List;
import java.util.Optional;

// Output port: the application core depends on this abstraction, not on Spring Data MongoDB.
public interface CourseStore {

	List<Course> findAll();

	Optional<Course> findById(String id);

	Course save(Course course);

	void deleteById(String id);

	boolean isEmpty();
}
