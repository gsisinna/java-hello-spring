package com.example.demo.spring.persistence.config;

import com.example.demo.spring.persistence.document.CourseDocument;
import com.example.demo.spring.persistence.repository.CourseRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseDataInitializerTest {

	@Test
	void seedRunnerAddsSampleDocumentsWhenCollectionIsEmpty() throws Exception {
		CourseRepository repository = mock(CourseRepository.class);
		when(repository.count()).thenReturn(0L);

		new CourseDataInitializer().seedCourses(repository).run();

		verify(repository, times(2)).save(any(CourseDocument.class));
	}

	@Test
	void seedRunnerSkipsInsertWhenDocumentsAlreadyExist() throws Exception {
		CourseRepository repository = mock(CourseRepository.class);
		when(repository.count()).thenReturn(3L);

		new CourseDataInitializer().seedCourses(repository).run();

		verify(repository, never()).save(any(CourseDocument.class));
		assertEquals(3L, repository.count());
	}
}
