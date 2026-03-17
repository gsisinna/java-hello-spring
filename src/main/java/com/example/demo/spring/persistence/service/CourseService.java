package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.document.CourseDocument;
import com.example.demo.spring.springcommon.exception.ResourceNotFoundException;
import com.example.demo.spring.persistence.model.CourseResponse;
import com.example.demo.spring.persistence.model.CreateCourseRequest;
import com.example.demo.spring.persistence.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// Service layer for the MongoDB-backed example.
public class CourseService {

	private final CourseRepository courseRepository;

	public CourseService(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	public List<CourseResponse> findAll() {
		// Map Mongo documents to DTOs so the controller never exposes persistence objects directly.
		return courseRepository.findAll().stream()
			.map(this::toResponse)
			.toList();
	}

	public CourseResponse findById(String id) {
		return courseRepository.findById(id)
			.map(this::toResponse)
			.orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " was not found"));
	}

	public CourseResponse create(CreateCourseRequest request) {
		CourseDocument saved = courseRepository.save(
			new CourseDocument(
				request.title(),
				request.level(),
				request.durationInHours(),
				request.published()
			)
		);
		return toResponse(saved);
	}

	public CourseResponse update(String id, CreateCourseRequest request) {
		CourseDocument document = courseRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " was not found"));

		document.update(
			request.title(),
			request.level(),
			request.durationInHours(),
			request.published()
		);

		return toResponse(courseRepository.save(document));
	}

	public void delete(String id) {
		CourseDocument document = courseRepository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("Course with id " + id + " was not found"));
		courseRepository.delete(document);
	}

	private CourseResponse toResponse(CourseDocument document) {
		return new CourseResponse(
			document.getId(),
			document.getTitle(),
			document.getLevel(),
			document.getDurationInHours(),
			document.isPublished()
		);
	}
}
