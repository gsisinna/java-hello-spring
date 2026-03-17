package com.example.demo.basics.generics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoxTest {

	@Test
	void genericClassWorksWithDifferentTypes() {
		Box<String> textBox = new Box<>("Java");
		Box<Integer> numberBox = new Box<>(17);

		assertEquals("Java", textBox.getValue());
		assertEquals(17, numberBox.getValue());
	}
}
