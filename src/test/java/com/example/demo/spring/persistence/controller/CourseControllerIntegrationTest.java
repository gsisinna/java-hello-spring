package com.example.demo.spring.persistence.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Integration test that covers HTTP, validation, security, and persistence together.
class CourseControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void coursesRequireBasicAuthentication() throws Exception {
		mockMvc.perform(get("/api/courses"))
			.andExpect(status().isUnauthorized());
	}

	@Test
	void authenticatedRequestReturnsSeededCourses() throws Exception {
		mockMvc.perform(get("/api/courses").with(httpBasic("student", "password")))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
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
	void validCoursePayloadPersistsInH2() throws Exception {
		mockMvc.perform(post("/api/courses")
				.with(httpBasic("student", "password"))
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "title": "Spring Data JPA",
					  "level": "INTERMEDIATE",
					  "durationInHours": 7,
					  "published": true
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value("Spring Data JPA"))
			.andExpect(jsonPath("$.durationInHours").value(7))
			.andExpect(jsonPath("$.level").value("INTERMEDIATE"));
	}

	@Test
	void updateCourseReplacesStoredValues() throws Exception {
		mockMvc.perform(put("/api/courses/1")
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
			.andExpect(jsonPath("$.title").value("Updated Java Generics"))
			.andExpect(jsonPath("$.level").value("ADVANCED"))
			.andExpect(jsonPath("$.published").value(false));
	}

	@Test
	void deleteCourseRemovesExistingRecord() throws Exception {
		mockMvc.perform(delete("/api/courses/2").with(httpBasic("student", "password")))
			.andExpect(status().isNoContent());

		mockMvc.perform(get("/api/courses/2").with(httpBasic("student", "password")))
			.andExpect(status().isNotFound());
	}
}
