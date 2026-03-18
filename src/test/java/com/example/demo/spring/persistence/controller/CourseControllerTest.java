package com.example.demo.spring.persistence.controller;

import com.example.demo.spring.controller.ApiExceptionHandler;
import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.model.CourseLevel;
import com.example.demo.spring.persistence.service.CourseService;
import com.example.demo.spring.persistence.service.UpsertCourseCommand;
import com.example.demo.spring.security.SecurityConfig;
import com.example.demo.spring.springcommon.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@Import({SecurityConfig.class, ApiExceptionHandler.class})
@TestPropertySource(properties = {
	"app.security.username=student",
	"app.security.password=password",
	"app.security.role=STUDENT"
})
// Web-layer test for the secured JSON API backed by a mocked service.
class CourseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CourseService courseService;

	@Test
	void coursesRequireBasicAuthentication() throws Exception {
		mockMvc.perform(get("/api/courses"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticatedRequestReturnsCourseDocumentsAsJson() throws Exception {
		when(courseService.findAll()).thenReturn(List.of(
			new Course("course-101", "Java Generics Deep Dive", CourseLevel.INTERMEDIATE, 6, true)
		));

		mockMvc.perform(get("/api/courses").with(httpBasic("student", "password")))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value("course-101"))
			.andExpect(jsonPath("$[0].title").value("Java Generics Deep Dive"));
	}

	@Test
	void invalidCoursePayloadReturnsValidationError() throws Exception {
		mockMvc.perform(post("/api/courses")
				.with(httpBasic("student", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "",
					  "level": "BEGINNER",
					  "durationInHours": 0,
					  "published": true
					}
					"""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").exists())
			.andExpect(jsonPath("$.hint").value("Fix the request body and send it again"));
	}

	@Test
	void validCoursePayloadReturnsCreatedDocument() throws Exception {
		when(courseService.create(any(UpsertCourseCommand.class)))
			.thenReturn(new Course("course-202", "Spring Data MongoDB", CourseLevel.INTERMEDIATE, 7, true));

		mockMvc.perform(post("/api/courses")
				.with(httpBasic("student", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "Spring Data MongoDB",
					  "level": "INTERMEDIATE",
					  "durationInHours": 7,
					  "published": true
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id").value("course-202"))
			.andExpect(jsonPath("$.title").value("Spring Data MongoDB"))
			.andExpect(jsonPath("$.level").value("INTERMEDIATE"));
	}

	@Test
	void updateCourseReplacesStoredValues() throws Exception {
		when(courseService.update(any(String.class), any(UpsertCourseCommand.class)))
			.thenReturn(new Course("course-101", "Updated Java Generics", CourseLevel.ADVANCED, 10, false));

		mockMvc.perform(put("/api/courses/course-101")
				.with(httpBasic("student", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "Updated Java Generics",
					  "level": "ADVANCED",
					  "durationInHours": 10,
					  "published": false
					}
					"""))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id").value("course-101"))
			.andExpect(jsonPath("$.title").value("Updated Java Generics"))
			.andExpect(jsonPath("$.published").value(false));
	}

	@Test
	void deleteCourseRemovesExistingDocument() throws Exception {
		doNothing().when(courseService).delete("course-202");
		doThrow(new ResourceNotFoundException("Course with id course-202 was not found"))
			.when(courseService).findById("course-202");

		mockMvc.perform(delete("/api/courses/course-202").with(httpBasic("student", "password")))
			.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/courses/course-202").with(httpBasic("student", "password")))
			.andExpect(status().isNotFound());
	}
}
