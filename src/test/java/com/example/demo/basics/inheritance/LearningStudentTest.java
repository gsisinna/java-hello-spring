package com.example.demo.basics.inheritance;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LearningStudentTest {

	@Test
	// Parent reference + child implementation demonstrates polymorphism.
	void subclassOverridesParentBehavior() {
		Person student = new LearningStudent("Mia", "Spring Boot");

		assertEquals("Student: Mia studies Spring Boot", student.description());
	}
}
