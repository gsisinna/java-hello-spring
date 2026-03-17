package com.example.demo.spring.persistence.service;

import com.example.demo.spring.springcommon.exception.ResourceNotFoundException;
import com.example.demo.spring.persistence.entity.CourseEntity;
import com.example.demo.spring.persistence.model.CourseResponse;
import com.example.demo.spring.persistence.model.CreateCourseRequest;
import com.example.demo.spring.persistence.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// Service layer for the database-backed example.
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public List<CourseResponse> findAll() {
		// Map entities to DTOs so the controller never exposes JPA objects directly.
		return courseRepository.findAll().stream()
			.map(this::toResponse)
			.toList();
	}

	public CourseResponse findById(long id) {
		return courseRepository.findById(id)
			.map(this::toResponse)
			.orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " was not found"));
	}

	public CourseResponse create(CreateCourseRequest request) {
		CourseEntity saved = courseRepository.save(
			new CourseEntity(
				request.title(),
				request.level(),
				request.durationInHours(),
				request.published()
			)
		);
		return toResponse(saved);
	}

	private CourseResponse toResponse(CourseEntity entity) {
		return new CourseResponse(
			entity.getId(),
			entity.getTitle(),
			entity.getLevel(),
			entity.getDurationInHours(),
			entity.isPublished()
		);
	}
}
