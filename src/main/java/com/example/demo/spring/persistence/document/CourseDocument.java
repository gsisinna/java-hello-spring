package com.example.demo.spring.persistence.document;

import com.example.demo.spring.persistence.model.CourseLevel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "courses")
// MongoDB document used by the course API. Mongo stores JSON-like documents instead of rows.
public class CourseDocument {

	@Id
	private String id;

	private String title;
	private CourseLevel level;
	private int durationInHours;
	private boolean published;

	public CourseDocument() {
		// Spring Data MongoDB needs a no-args constructor when hydrating documents from the database.
	}

	public CourseDocument(String id, String title, CourseLevel level, int durationInHours, boolean published) {
		this.id = id;
		this.title = title;
		this.level = level;
		this.durationInHours = durationInHours;
		this.published = published;
	}

	public CourseDocument(String title, CourseLevel level, int durationInHours, boolean published) {
		this(null, title, level, durationInHours, published);
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public CourseLevel getLevel() {
		return level;
	}

	public int getDurationInHours() {
		return durationInHours;
	}

	public boolean isPublished() {
		return published;
	}

	public void update(String title, CourseLevel level, int durationInHours, boolean published) {
		this.title = title;
		this.level = level;
		this.durationInHours = durationInHours;
		this.published = published;
	}
}
