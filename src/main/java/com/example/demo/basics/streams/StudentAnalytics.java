package com.example.demo.basics.streams;

import com.example.demo.basics.model.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentAnalytics {

	public List<String> activeStudentNames(List<Student> students) {
		return students.stream()
			.filter(Student::isActive)
			.map(Student::getName)
			.toList();
	}

	public Map<String, Integer> totalScoresByName(List<Student> students) {
		return students.stream()
			.collect(Collectors.toMap(
				Student::getName,
				Student::totalScore
			));
	}
}
