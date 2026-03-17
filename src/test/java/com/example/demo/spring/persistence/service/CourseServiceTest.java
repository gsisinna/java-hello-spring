package com.example.demo.spring.persistence.service;

import com.example.demo.spring.persistence.entity.CourseEntity;
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
	void createMapsRequestIntoEntityAndResponse() {
		CourseRepository repository = mock(CourseRepository.class);
		when(repository.save(any(CourseEntity.class)))
			.thenReturn(new CourseEntityFixture().savedEntity());

		CourseService service = new CourseService(repository);

		CourseResponse response = service.create(new CreateCourseRequest("Spring Security", "advanced", 8, true));

		assertEquals(1L, response.id());
		assertEquals("Spring Security", response.title());
		assertEquals("advanced", response.level());
		assertEquals(8, response.durationInHours());
	}

	@Test
	void findByIdThrowsWhenCourseDoesNotExist() {
		CourseRepository repository = mock(CourseRepository.class);
		when(repository.findById(99L)).thenReturn(Optional.empty());

		CourseService service = new CourseService(repository);

		assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
	}

	private static class CourseEntityFixture {
		private CourseEntity savedEntity() {
			CourseEntity entity = new CourseEntity("Spring Security", "advanced", 8, true);
			try {
				var field = CourseEntity.class.getDeclaredField("id");
				field.setAccessible(true);
				field.set(entity, 1L);
			} catch (ReflectiveOperationException e) {
				throw new IllegalStateException(e);
			}
			return entity;
		}
	}
}
