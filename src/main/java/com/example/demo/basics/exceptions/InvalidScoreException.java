package com.example.demo.basics.exceptions;

// Small custom exception that shows how domain rules can reject invalid input.
public class InvalidScoreException extends RuntimeException {

	public InvalidScoreException(String message) {
		super(message);
	}
}
