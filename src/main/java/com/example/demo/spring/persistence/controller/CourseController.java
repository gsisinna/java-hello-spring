package com.example.demo.spring.persistence.controller;

import com.example.demo.spring.model.ErrorResponse;
import com.example.demo.spring.persistence.model.CourseResponse;
import com.example.demo.spring.persistence.model.CreateCourseRequest;
import com.example.demo.spring.persistence.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "Database-backed examples for validation, JPA, H2, and security.")
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	@Operation(summary = "List courses", description = "Returns all persisted courses. This endpoint requires basic authentication.")
	@ApiResponse(
		responseCode = "200",
		description = "Courses loaded successfully",
		content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseResponse.class)))
	)
	public List<CourseResponse> findAll() {
		return courseService.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a course by id", description = "Returns one persisted course.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Course found",
			content = @Content(schema = @Schema(implementation = CourseResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "Course not found",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public CourseResponse findById(@PathVariable long id) {
		return courseService.findById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a course", description = "Validates the request body and stores a course in H2.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "Course created",
			content = @Content(schema = @Schema(implementation = CourseResponse.class))
		),
		@ApiResponse(
			responseCode = "400",
			description = "Validation failed",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public CourseResponse create(@Valid @RequestBody CreateCourseRequest request) {
		return courseService.create(request);
	}
}
