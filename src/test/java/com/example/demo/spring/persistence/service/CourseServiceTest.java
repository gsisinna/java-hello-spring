package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.model.CourseLevel;
import com.example.demo.spring.persistence.store.CourseStore;
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
	// Mocking the output port keeps this test focused on application behavior only.
	void createMapsCommandIntoDomainObject() {
		CourseStore courseStore = mock(CourseStore.class);
		when(courseStore.save(any(Course.class)))
			.thenReturn(new Course("course-1", "Spring Security", CourseLevel.ADVANCED, 8, true));

		CourseService service = new CourseApplicationService(courseStore);

		Course response = service.create(new UpsertCourseCommand("Spring Security", CourseLevel.ADVANCED, 8, true));

		assertEquals("course-1", response.id());
		assertEquals("Spring Security", response.title());
		assertEquals(CourseLevel.ADVANCED, response.level());
		assertEquals(8, response.durationInHours());
	}

	@Test
	void findByIdThrowsWhenCourseDoesNotExist() {
		CourseStore courseStore = mock(CourseStore.class);
		when(courseStore.findById("missing-course")).thenReturn(Optional.empty());

		CourseService service = new CourseApplicationService(courseStore);

		assertThrows(ResourceNotFoundException.class, () -> service.findById("missing-course"));
	}
}
