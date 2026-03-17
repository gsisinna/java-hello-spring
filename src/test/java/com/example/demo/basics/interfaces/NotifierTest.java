package com.example.demo.basics.interfaces;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NotifierTest {

	@Test
	// Code written against the interface can swap implementations later.
	void interfaceImplementationCanBeUsedPolymorphically() {
		Notifier notifier = new EmailNotifier();

		assertEquals("email", notifier.channel());
		assertEquals("[email] Welcome to Java", notifier.send("Welcome to Java"));
	}
}
