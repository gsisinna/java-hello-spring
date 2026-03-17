package com.example.demo.basics.annotations;

import com.example.demo.basics.model.Student;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LearningExampleTest {

	@Test
	// Reflection lets the test inspect annotation values at runtime.
	void studentClassUsesCustomAnnotation() {
		LearningExample annotation = Student.class.getAnnotation(LearningExample.class);

		assertNotNull(annotation);
		assertEquals("Student basics", annotation.title());
		assertArrayEquals(
			new String[] {
				"classes and objects",
				"methods",
				"variables and types",
				"if, for",
				"constructors",
				"List, Map",
				"exceptions",
				"packages",
				"annotations"
			},
			annotation.concepts()
		);
	}
}
