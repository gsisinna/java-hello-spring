package com.example.demo.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Full MVC test for the public student endpoints.
class StudentControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getStudentsReturnsSeedData() throws Exception {
		mockMvc.perform(get("/api/students"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].name").value("Ada"))
			.andExpect(jsonPath("$[0].subjects[0]").value("classes and objects"))
			.andExpect(jsonPath("$[1].name").value("Sam"));
	}

	@Test
	void createStudentReturnsRequestResponseModel() throws Exception {
		mockMvc.perform(post("/api/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "name": "Lina",
					  "age": 23,
					  "active": true,
					  "subjects": ["annotations", "controller"],
					  "scores": {
					    "java": 91,
					    "spring": 89
					  }
					}
					"""))
			.andExpect(status().isCreated())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.name").value("Lina"))
			.andExpect(jsonPath("$.active").value(true))
			.andExpect(jsonPath("$.level").value("adult"))
			.andExpect(jsonPath("$.totalScore").value(180));
	}

	@Test
	void missingStudentReturns404() throws Exception {
		mockMvc.perform(get("/api/students/999"))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.message").value("Student with id 999 was not found"));
	}

	@Test
	void invalidScoreReturns400() throws Exception {
		mockMvc.perform(post("/api/students")
				.contentType(MediaType.APPLICATION_JSON)
				.content("""
					{
					  "name": "Kai",
					  "age": 18,
					  "active": true,
					  "subjects": ["exceptions"],
					  "scores": {
					    "java": 120
					  }
					}
					"""))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.message").value("Score must be between 0 and 100"))
			.andExpect(jsonPath("$.hint").value("Use scores from 0 to 100"));
	}
}
