package com.example.demo.spring.model;

import java.util.List;
import java.util.Map;

public record StudentResponse(
	long id,
	String name,
	int age,
	boolean active,
	String level,
	List<String> subjects,
	Map<String, Integer> scores,
	int totalScore,
	String subjectSummary
) {
}
