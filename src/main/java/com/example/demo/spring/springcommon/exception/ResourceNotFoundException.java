package com.example.demo.spring.springcommon.exception;

// Generic not-found exception reused by the persistence example.
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
