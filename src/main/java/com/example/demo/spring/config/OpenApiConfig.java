package com.example.demo.spring.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Central place for OpenAPI metadata shown in Swagger UI and the generated YAML.
public class OpenApiConfig {

	@Bean
	public OpenAPI learningApi() {
		return new OpenAPI()
			.info(new Info()
				.title("Java Spring Learning API")
				.description("Learning project for core Java concepts, Spring Boot, Swagger UI, and OpenAPI YAML.")
				.version("v1")
				.contact(new Contact()
					.name("Learning Repo")
					.url("https://example.com"))
				.license(new License()
					.name("For learning use")
					.url("https://opensource.org/licenses/MIT")));
	}
}
