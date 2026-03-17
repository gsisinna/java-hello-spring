package com.example.demo.spring.service;

import com.example.demo.spring.exception.StudentNotFoundException;
import com.example.demo.spring.model.CreateStudentRequest;
import com.example.demo.spring.model.StudentResponse;
import com.example.demo.spring.repository.InMemoryStudentRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentServiceTest {

	@Test
	// Unit-style test for the service mapping and creation logic.
	void createStudentBuildsResponseFromRequestModel() {
		StudentService service = new StudentService(new InMemoryStudentRepository());

		StudentResponse response = service.createStudent(
			new CreateStudentRequest(
				"Lina",
				23,
				true,
				List.of("annotations", "controller"),
				Map.of("java", 91, "spring", 89)
			)
		);

		assertTrue(response.id() > 0);
		assertEquals("Lina", response.name());
		assertEquals("adult", response.level());
		assertEquals(180, response.totalScore());
		assertEquals("annotations, controller", response.subjectSummary());
	}

	@Test
	void findByIdThrowsWhenStudentDoesNotExist() {
		StudentService service = new StudentService(new InMemoryStudentRepository());

		assertThrows(StudentNotFoundException.class, () -> service.findById(999L));
	}
}
