package com.example.demo.spring.persistence.store;

import com.example.demo.spring.persistence.document.CourseDocument;
import com.example.demo.spring.persistence.domain.Course;
import org.springframework.stereotype.Component;

@Component
// Mapper pattern: keeps translation between domain objects and Mongo documents out of the service layer.
public class CourseDocumentMapper {

	public Course toDomain(CourseDocument document) {
		return new Course(
			document.getId(),
			document.getTitle(),
			document.getLevel(),
			document.getDurationInHours(),
			document.isPublished()
		);
	}

	public CourseDocument toDocument(Course course) {
		return new CourseDocument(
			course.id(),
			course.title(),
			course.level(),
			course.durationInHours(),
			course.published()
		);
	}
}
