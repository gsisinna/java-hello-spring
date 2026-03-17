package com.example.demo.spring.persistence.config;

import com.example.demo.spring.persistence.document.CourseDocument;
import com.example.demo.spring.persistence.model.CourseLevel;
import com.example.demo.spring.persistence.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Seeds a few MongoDB documents so the secured API has data to read immediately.
public class CourseDataInitializer {

	@Bean
	@ConditionalOnProperty(name = "app.courses.seed.enabled", havingValue = "true", matchIfMissing = true)
	CommandLineRunner seedCourses(CourseRepository courseRepository) {
		return args -> {
			if (courseRepository.count() > 0) {
				return;
			}

			courseRepository.save(new CourseDocument("Java Generics Deep Dive", CourseLevel.INTERMEDIATE, 6, true));
			courseRepository.save(new CourseDocument("Spring Boot REST APIs", CourseLevel.BEGINNER, 5, true));
		};
	}
}
