package com.example.demo.spring.config;

import com.example.demo.spring.persistence.store.CourseStore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
// Verifies that typed YAML properties are bound and exposed through a controller.
class LearningInfoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CourseStore courseStore;

	@Test
	void learningInfoReturnsConfigurationPropertiesValues() throws Exception {
		mockMvc.perform(get("/api/learning-info"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.title").value("Java + Spring Boot Learning Repo"))
			.andExpect(jsonPath("$.focusTopics[0]").value("java basics"))
			.andExpect(jsonPath("$.focusTopics[3]").value("mongodb"));
	}
}
