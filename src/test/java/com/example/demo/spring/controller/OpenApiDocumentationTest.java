package com.example.demo.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OpenApiDocumentationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void openApiYamlIsAvailable() throws Exception {
		mockMvc.perform(get("/v3/api-docs.yaml"))
			.andExpect(status().isOk())
			.andExpect(content().contentTypeCompatibleWith(MediaType.valueOf("application/vnd.oai.openapi")))
			.andExpect(content().string(org.hamcrest.Matchers.containsString("openapi:")))
			.andExpect(content().string(org.hamcrest.Matchers.containsString("Java Spring Learning API")))
			.andExpect(content().string(org.hamcrest.Matchers.containsString("/api/students")));
	}

	@Test
	void swaggerUiEntryPointIsAvailable() throws Exception {
		mockMvc.perform(get("/swagger-ui.html"))
			.andExpect(status().is3xxRedirection())
			.andExpect(header().string("Location", org.hamcrest.Matchers.containsString("/swagger-ui/index.html")));
	}
}
