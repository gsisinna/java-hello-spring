package com.example.demo.basics.model;

import com.example.demo.basics.annotations.LearningExample;
import com.example.demo.basics.exceptions.InvalidScoreException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@LearningExample(
	title = "Student basics",
	concepts = {
		"classes and objects",
		"methods",
		"variables and types",
		"if, for",
		"constructors",
		"List, Map",
		"exceptions",
		"packages",
		"annotations"
	}
)
// Central plain-Java example used to teach fields, constructors, methods, collections, and control flow.
public class Student {

	private final long id;
	private final String name;
	private final int age;
	private boolean active;
	private final List<String> subjects;
	private final Map<String, Integer> scores;

	public Student(String name, int age) {
		this(0L, name, age, false, List.of(), Map.of());
	}

	public Student(long id, String name, int age) {
		this(id, name, age, false, List.of(), Map.of());
	}

	public Student(
		long id,
		String name,
		int age,
		boolean active,
		List<String> subjects,
		Map<String, Integer> scores
	) {
		// Defensive copies keep callers from mutating the internal state by accident.
		this.id = id;
		this.name = name;
		this.age = age;
		this.active = active;
		this.subjects = new ArrayList<>(subjects);
		this.scores = new LinkedHashMap<>(scores);
	}

	public void activate() {
		this.active = true;
	}

	public void enroll(String subject) {
		// Early return keeps invalid values out of the subjects list.
		if (subject == null || subject.isBlank()) {
			return;
		}
		this.subjects.add(subject.trim());
	}

	public void addScore(String subject, int score) {
		// Throwing an exception is the explicit "fail fast" path for invalid state.
		if (score < 0 || score > 100) {
			throw new InvalidScoreException("Score must be between 0 and 100");
		}
		this.scores.put(subject, score);
	}

	public String level() {
		if (age >= 18) {
			return "adult";
		}
		return "junior";
	}

	public int totalScore() {
		// Classic for-each loop example over a Map's values.
		int total = 0;
		for (int value : scores.values()) {
			total += value;
		}
		return total;
	}

	public String subjectSummary() {
		if (subjects.isEmpty()) {
			return "No subjects yet";
		}

		// StringBuilder is efficient when building strings in a loop.
		StringBuilder summary = new StringBuilder();
		for (String subject : subjects) {
			if (!summary.isEmpty()) {
				summary.append(", ");
			}
			summary.append(subject);
		}
		return summary.toString();
	}

	public Student withId(long newId) {
		// Returns a new instance instead of mutating the id after construction.
		return new Student(newId, name, age, active, subjects, scores);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public boolean isActive() {
		return active;
	}

	public List<String> getSubjects() {
		return new ArrayList<>(subjects);
	}

	public Map<String, Integer> getScores() {
		return new LinkedHashMap<>(scores);
	}
}
