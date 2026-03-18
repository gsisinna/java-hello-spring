package com.example.demo.spring.persistence.store;

import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.repository.CourseRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
// Adapter pattern: translates between the CourseStore port and the Spring Data MongoDB repository.
public class MongoCourseStore implements CourseStore {

	private final CourseRepository courseRepository;
	private final CourseDocumentMapper courseDocumentMapper;

	public MongoCourseStore(CourseRepository courseRepository, CourseDocumentMapper courseDocumentMapper) {
		this.courseRepository = courseRepository;
		this.courseDocumentMapper = courseDocumentMapper;
	}

	@Override
	public List<Course> findAll() {
		return courseRepository.findAll().stream()
			.map(courseDocumentMapper::toDomain)
			.toList();
	}

	@Override
	public Optional<Course> findById(String id) {
		return courseRepository.findById(id)
			.map(courseDocumentMapper::toDomain);
	}

	@Override
	public Course save(Course course) {
		return courseDocumentMapper.toDomain(
			courseRepository.save(courseDocumentMapper.toDocument(course))
		);
	}

	@Override
	public void deleteById(String id) {
		courseRepository.deleteById(id);
	}

	@Override
	public boolean isEmpty() {
		return courseRepository.count() == 0;
	}
}
