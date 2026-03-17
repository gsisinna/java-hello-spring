package com.example.demo.spring.exception;

// Student-specific exception used by the in-memory example.
public class StudentNotFoundException extends RuntimeException {

	public StudentNotFoundException(long id) {
		super("Student with id " + id + " was not found");
	}
}
