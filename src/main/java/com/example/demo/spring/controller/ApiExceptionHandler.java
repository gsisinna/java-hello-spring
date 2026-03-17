package com.example.demo.spring.controller;

import com.example.demo.basics.exceptions.InvalidScoreException;
import com.example.demo.spring.exception.StudentNotFoundException;
import com.example.demo.spring.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
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
}
