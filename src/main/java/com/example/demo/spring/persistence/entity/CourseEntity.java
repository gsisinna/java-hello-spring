package com.example.demo.spring.persistence.entity;

import com.example.demo.spring.persistence.model.CourseLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "courses")
// JPA entity mapped to the "courses" table in the H2 database.
public class CourseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CourseLevel level;

	@Column(nullable = false)
	private int durationInHours;

	@Column(nullable = false)
	private boolean published;

	protected CourseEntity() {
		// Required by JPA when it creates entity instances reflectively.
	}

	public CourseEntity(String title, CourseLevel level, int durationInHours, boolean published) {
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

	public CourseLevel getLevel() {
		return level;
	}

	public void update(String title, CourseLevel level, int durationInHours, boolean published) {
		this.title = title;
		this.level = level;
		this.durationInHours = durationInHours;
		this.published = published;
	}

	public int getDurationInHours() {
		return durationInHours;
	}

	public boolean isPublished() {
		return published;
	}
}
