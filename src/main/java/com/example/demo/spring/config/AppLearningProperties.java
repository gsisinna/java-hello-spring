package com.example.demo.spring.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "app.learning")
// Typed configuration example: Spring binds YAML properties into this class at startup.
public class AppLearningProperties {

	private String title;
	private List<String> focusTopics = new ArrayList<>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getFocusTopics() {
		return focusTopics;
	}

	public void setFocusTopics(List<String> focusTopics) {
		this.focusTopics = focusTopics;
	}
}
