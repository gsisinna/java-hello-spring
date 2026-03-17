package com.example.demo.spring.controller;

import com.example.demo.spring.model.CreateStudentRequest;
import com.example.demo.spring.model.ErrorResponse;
import com.example.demo.spring.model.StudentResponse;
import com.example.demo.spring.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Students", description = "Student examples for learning Spring Boot controllers, services, and dependency injection.")
// Thin controller: it maps HTTP requests to service calls and returns DTOs.
public class StudentController {

	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping
	@Operation(summary = "List students", description = "Returns the in-memory student list used by this learning project.")
	@ApiResponse(
		responseCode = "200",
		description = "Students loaded successfully",
		content = @Content(array = @ArraySchema(schema = @Schema(implementation = StudentResponse.class)))
	)
	public List<StudentResponse> findAll() {
		return studentService.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a student by id", description = "Returns one student or a 404 error if the student does not exist.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Student found",
			content = @Content(schema = @Schema(implementation = StudentResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "Student not found",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public StudentResponse findById(@PathVariable long id) {
		return studentService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a student", description = "Creates a new student from a request model and returns the response model.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "Student created",
			content = @Content(schema = @Schema(implementation = StudentResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "Invalid request data",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public StudentResponse create(@RequestBody CreateStudentRequest request) {
		return studentService.createStudent(request);
	}
}
