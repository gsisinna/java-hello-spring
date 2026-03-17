package com.example.demo.spring.controller;

import com.example.demo.basics.exceptions.InvalidScoreException;
import com.example.demo.spring.exception.StudentNotFoundException;
import com.example.demo.spring.model.ErrorResponse;
import com.example.demo.spring.springcommon.exception.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
// Converts thrown exceptions into consistent JSON responses for API clients.
public class ApiExceptionHandler {

	@ExceptionHandler(StudentNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleStudentNotFound(StudentNotFoundException exception) {
		return new ErrorResponse(exception.getMessage(), "Try a value such as /api/students/1");
	}

	@ExceptionHandler(InvalidScoreException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleInvalidScore(InvalidScoreException exception) {
		return new ErrorResponse(exception.getMessage(), "Use scores from 0 to 100");
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorResponse handleResourceNotFound(ResourceNotFoundException exception) {
		return new ErrorResponse(exception.getMessage(), "Check the resource id and try again");
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleValidation(MethodArgumentNotValidException exception) {
		// Returning the first field error keeps the learning example small and readable.
		String message = exception.getBindingResult().getFieldErrors().stream()
			.findFirst()
			.map(error -> error.getField() + ": " + error.getDefaultMessage())
			.orElse("Validation failed");
		return new ErrorResponse(message, "Fix the request body and send it again");
	}

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleConstraintViolation(ConstraintViolationException exception) {
		return new ErrorResponse(exception.getMessage(), "Fix the request data and try again");
	}
}
