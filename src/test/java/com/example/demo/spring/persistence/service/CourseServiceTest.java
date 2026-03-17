package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.document.CourseDocument;
import com.example.demo.spring.persistence.model.CourseLevel;
import com.example.demo.spring.persistence.model.CourseResponse;
import com.example.demo.spring.persistence.model.CreateCourseRequest;
import com.example.demo.spring.persistence.repository.CourseRepository;
import com.example.demo.spring.springcommon.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CourseServiceTest {

	@Test
	// Mocking the repository keeps this test focused on service behavior only.
	void createMapsRequestIntoDocumentAndResponse() {
		CourseRepository repository = mock(CourseRepository.class);
		when(repository.save(any(CourseDocument.class)))
			.thenReturn(new CourseDocumentFixture().savedDocument());

		CourseService service = new CourseService(repository);

		CourseResponse response = service.create(new CreateCourseRequest("Spring Security", CourseLevel.ADVANCED, 8, true));

		assertEquals("course-1", response.id());
		assertEquals("Spring Security", response.title());
		assertEquals(CourseLevel.ADVANCED, response.level());
		assertEquals(8, response.durationInHours());
	}

	@Test
	void findByIdThrowsWhenCourseDoesNotExist() {
		CourseRepository repository = mock(CourseRepository.class);
		when(repository.findById("missing-course")).thenReturn(Optional.empty());

		CourseService service = new CourseService(repository);

		assertThrows(ResourceNotFoundException.class, () -> service.findById("missing-course"));
	}

	private static class CourseDocumentFixture {
		private CourseDocument savedDocument() {
			// Reflection is used here only to simulate a generated MongoDB id in a unit test.
			CourseDocument document = new CourseDocument("Spring Security", CourseLevel.ADVANCED, 8, true);
			try {
				var field = CourseDocument.class.getDeclaredField("id");
				field.setAccessible(true);
				field.set(document, "course-1");
			} catch (ReflectiveOperationException e) {
				throw new IllegalStateException(e);
			}
			return document;
		}
	}
}
