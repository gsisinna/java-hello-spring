package com.example.demo.basics.inheritance;

// Parent class used to demonstrate inheritance and method overriding.
public class Person {

	private final String name;

	public Person(String name) {
		this.name = name;
	}

	public String description() {
		return "Person: " + name;
	}

	public String getName() {
		return name;
	}
}
