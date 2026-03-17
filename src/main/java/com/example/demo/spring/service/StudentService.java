package com.example.demo.spring.service;

import com.example.demo.basics.model.Student;
import com.example.demo.spring.exception.StudentNotFoundException;
import com.example.demo.spring.model.CreateStudentRequest;
import com.example.demo.spring.model.StudentResponse;
import com.example.demo.spring.repository.InMemoryStudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StudentService {

	private final InMemoryStudentRepository repository;

	public StudentService(InMemoryStudentRepository repository) {
		this.repository = repository;
	}

	public List<StudentResponse> findAll() {
		List<StudentResponse> responses = new ArrayList<>();
		for (Student student : repository.findAll()) {
			responses.add(toResponse(student));
		}
		return responses;
	}

	public StudentResponse findById(long id) {
		Student student = repository.findById(id)
			.orElseThrow(() -> new StudentNotFoundException(id));
		return toResponse(student);
	}

	public StudentResponse createStudent(CreateStudentRequest request) {
		Student student = new Student(request.name(), request.age());

		if (request.active()) {
			student.activate();
		}

		for (String subject : safeSubjects(request.subjects())) {
			student.enroll(subject);
		}

		for (Map.Entry<String, Integer> entry : safeScores(request.scores()).entrySet()) {
			student.addScore(entry.getKey(), entry.getValue());
		}

		return toResponse(repository.save(student));
	}

	private List<String> safeSubjects(List<String> subjects) {
		if (subjects == null) {
			return List.of();
		}
		return subjects;
	}

	private Map<String, Integer> safeScores(Map<String, Integer> scores) {
		if (scores == null) {
			return Map.of();
		}
		return scores;
	}

	private StudentResponse toResponse(Student student) {
		return new StudentResponse(
			student.getId(),
			student.getName(),
			student.getAge(),
			student.isActive(),
			student.level(),
			student.getSubjects(),
			student.getScores(),
			student.totalScore(),
			student.subjectSummary()
		);
	}
}
