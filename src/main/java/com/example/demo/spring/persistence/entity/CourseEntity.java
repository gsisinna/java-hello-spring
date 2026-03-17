package com.example.demo.spring.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String level;

	@Column(nullable = false)
	private int durationInHours;

	@Column(nullable = false)
	private boolean published;

	protected CourseEntity() {
	}

	public CourseEntity(String title, String level, int durationInHours, boolean published) {
		this.title = title;
		this.level = level;
		this.durationInHours = durationInHours;
		this.published = published;
	}

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getLevel() {
		return level;
	}

	public int getDurationInHours() {
		return durationInHours;
	}

	public boolean isPublished() {
		return published;
	}
}
