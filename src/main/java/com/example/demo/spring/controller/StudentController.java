package com.example.demo.spring.controller;

import com.example.demo.spring.model.CreateStudentRequest;
import com.example.demo.spring.model.StudentResponse;
import com.example.demo.spring.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	public List<StudentResponse> findAll() {
		return studentService.findAll();
	}

	@GetMapping("/{id}")
	public StudentResponse findById(@PathVariable long id) {
		return studentService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StudentResponse create(@RequestBody CreateStudentRequest request) {
		return studentService.createStudent(request);
	}
}
