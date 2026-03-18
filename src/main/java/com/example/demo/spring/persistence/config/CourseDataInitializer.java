package com.example.demo.spring.persistence.config;

import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.model.CourseLevel;
import com.example.demo.spring.persistence.store.CourseStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Seeds a few MongoDB documents so the secured API has data to read immediately.
public class CourseDataInitializer {

	@Bean
	@ConditionalOnProperty(name = "app.courses.seed.enabled", havingValue = "true", matchIfMissing = true)
	CommandLineRunner seedCourses(CourseStore courseStore) {
		return args -> {
			if (!courseStore.isEmpty()) {
				return;
			}

			courseStore.save(new Course(null, "Java Generics Deep Dive", CourseLevel.INTERMEDIATE, 6, true));
			courseStore.save(new Course(null, "Spring Boot REST APIs", CourseLevel.BEGINNER, 5, true));
		};
	}
}
