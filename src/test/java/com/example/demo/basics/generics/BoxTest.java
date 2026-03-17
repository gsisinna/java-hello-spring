package com.example.demo.basics.generics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxTest {

	@Test
	// Same class, different type parameters: that is the core generics idea.
	void genericClassWorksWithDifferentTypes() {
		Box<String> textBox = new Box<>("Java");
		Box<Integer> numberBox = new Box<>(17);

		assertEquals("Java", textBox.getValue());
		assertEquals(17, numberBox.getValue());
	}
}
