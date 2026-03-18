package com.example.demo.spring.persistence.controller;

import com.example.demo.spring.model.ErrorResponse;
import com.example.demo.spring.persistence.domain.Course;
import com.example.demo.spring.persistence.model.CourseResponse;
import com.example.demo.spring.persistence.model.CreateCourseRequest;
import com.example.demo.spring.persistence.service.CourseService;
import com.example.demo.spring.persistence.service.UpsertCourseCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@Tag(name = "Courses", description = "MongoDB-backed examples for validation, JSON request/response models, and security.")
// Second controller path: shows validation, JSON APIs, MongoDB persistence, and security together.
public class CourseController {

	private final CourseService courseService;

	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}

	@GetMapping
	@Operation(summary = "List courses", description = "Returns all persisted course documents as JSON. This endpoint requires basic authentication.")
	@ApiResponse(
		responseCode = "200",
		description = "Courses loaded successfully",
		content = @Content(array = @ArraySchema(schema = @Schema(implementation = CourseResponse.class)))
	)
	public List<CourseResponse> findAll() {
		return courseService.findAll().stream()
			.map(this::toResponse)
			.toList();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get a course by id", description = "Returns one persisted course document.")
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
	public CourseResponse findById(@PathVariable String id) {
		return toResponse(courseService.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Create a course", description = "Validates the request body and stores a course document in MongoDB.")
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
		return toResponse(courseService.create(toCommand(request)));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a course", description = "Replaces an existing MongoDB document using the validated JSON request body.")
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "Course updated",
			content = @Content(schema = @Schema(implementation = CourseResponse.class))
		),
		@ApiResponse(
			responseCode = "404",
			description = "Course not found",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public CourseResponse update(@PathVariable String id, @Valid @RequestBody CreateCourseRequest request) {
		return toResponse(courseService.update(id, toCommand(request)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@Operation(summary = "Delete a course", description = "Deletes a course document by id.")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Course deleted"),
		@ApiResponse(
			responseCode = "404",
			description = "Course not found",
			content = @Content(schema = @Schema(implementation = ErrorResponse.class))
		)
	})
	public void delete(@PathVariable String id) {
		courseService.delete(id);
	}

	private UpsertCourseCommand toCommand(CreateCourseRequest request) {
		return new UpsertCourseCommand(
			request.title(),
			request.level(),
			request.durationInHours(),
			request.published()
		);
	}

	private CourseResponse toResponse(Course course) {
		return new CourseResponse(
			course.id(),
			course.title(),
			course.level(),
			course.durationInHours(),
			course.published()
		);
	}
}
