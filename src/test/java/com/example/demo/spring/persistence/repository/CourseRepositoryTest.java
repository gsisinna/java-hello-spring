package com.example.demo.spring.persistence.repository;

import com.example.demo.spring.persistence.entity.CourseEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
// Repository-level persistence test against the in-memory H2 database.
class CourseRepositoryTest {

	@Autowired
	private CourseRepository courseRepository;

	@Test
	void repositoryPersistsCourseInH2() {
		CourseEntity saved = courseRepository.save(new CourseEntity("Validation Basics", "beginner", 4, true));

		assertTrue(saved.getId() > 0);
		assertTrue(courseRepository.findById(saved.getId()).isPresent());
	}
}
