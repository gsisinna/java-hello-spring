package com.example.demo.spring.model;

import java.util.List;
import java.util.Map;

public record CreateStudentRequest(
	String name,
	int age,
	boolean active,
	List<String> subjects,
	Map<String, Integer> scores
) {
}
