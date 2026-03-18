package com.example.demo.spring.persistence.config;

import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.store.CourseStore;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CourseDataInitializerTest {

	@Test
	void seedRunnerAddsSampleDocumentsWhenCollectionIsEmpty() throws Exception {
		CourseStore courseStore = mock(CourseStore.class);
		when(courseStore.isEmpty()).thenReturn(true);

		new CourseDataInitializer().seedCourses(courseStore).run();

		verify(courseStore, times(2)).save(any(Course.class));
	}

	@Test
	void seedRunnerSkipsInsertWhenDocumentsAlreadyExist() throws Exception {
		CourseStore courseStore = mock(CourseStore.class);
		when(courseStore.isEmpty()).thenReturn(false);

		new CourseDataInitializer().seedCourses(courseStore).run();

		verify(courseStore, never()).save(any(Course.class));
	}
}
