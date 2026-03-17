package com.example.demo.spring.persistence.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
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
					  "level": "beginner",
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
					  "level": "intermediate",
					  "durationInHours": 7,
					  "published": true
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.title").value("Spring Data JPA"))
			.andExpect(jsonPath("$.durationInHours").value(7));
	}
}
