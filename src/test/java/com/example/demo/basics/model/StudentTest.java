package com.example.demo.basics.model;

import com.example.demo.basics.exceptions.InvalidScoreException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentTest {

	@Test
	void studentShowsCoreJavaConceptsInOneClass() {
		Student student = new Student("Mia", 19);

		student.activate();
		student.enroll("methods");
		student.enroll("List and Map");
		student.addScore("java", 45);
		student.addScore("spring", 50);

		assertEquals("Mia", student.getName());
		assertEquals(19, student.getAge());
		assertTrue(student.isActive());
		assertEquals("adult", student.level());
		assertEquals(95, student.totalScore());
		assertEquals("methods, List and Map", student.subjectSummary());
	}

	@Test
	void invalidScoreThrowsCustomException() {
		Student student = new Student("Noah", 17);

		assertThrows(InvalidScoreException.class, () -> student.addScore("java", 101));
	}
}
