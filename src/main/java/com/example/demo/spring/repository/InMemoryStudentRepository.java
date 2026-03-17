package com.example.demo.spring.repository;

import com.example.demo.basics.model.Student;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
// Simple fake repository that keeps data in memory instead of a database.
public class InMemoryStudentRepository {

	private final Map<Long, Student> students = new LinkedHashMap<>();
	private long nextId = 1L;

	public InMemoryStudentRepository() {
		// Seed data makes the API immediately useful when the app starts.
		Student ada = new Student("Ada", 20);
		ada.activate();
		ada.enroll("classes and objects");
		ada.enroll("methods");
		ada.addScore("java", 95);
		save(ada);

		Student sam = new Student("Sam", 16);
		sam.activate();
		sam.enroll("controller");
		sam.enroll("dependency injection");
		sam.addScore("spring", 88);
		save(sam);
	}

	public List<Student> findAll() {
		return new ArrayList<>(students.values());
	}

	public Optional<Student> findById(long id) {
		return Optional.ofNullable(students.get(id));
	}

	public Student save(Student student) {
		long id = student.getId() == 0L ? nextId++ : student.getId();
		Student storedStudent = student.withId(id);
		students.put(id, storedStudent);
		return storedStudent;
	}
}
