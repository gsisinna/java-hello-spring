package com.example.demo.basics.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotifierTest {

	@Test
	void interfaceImplementationCanBeUsedPolymorphically() {
		Notifier notifier = new EmailNotifier();

		assertEquals("email", notifier.channel());
		assertEquals("[email] Welcome to Java", notifier.send("Welcome to Java"));
	}
}
