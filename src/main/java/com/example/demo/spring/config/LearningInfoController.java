package com.example.demo.spring.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Learning Info", description = "Configuration-properties example exposed as a small public endpoint.")
// Small endpoint that makes @ConfigurationProperties visible from outside the app.
public class LearningInfoController {

	private final AppLearningProperties properties;

	public LearningInfoController(AppLearningProperties properties) {
		this.properties = properties;
	}

	@GetMapping("/api/learning-info")
	@Operation(summary = "Show learning configuration", description = "Returns values loaded from app.learning.* in application.yml.")
	@ApiResponse(
		responseCode = "200",
		description = "Configuration loaded successfully",
		content = @Content(schema = @Schema(implementation = LearningInfoResponse.class))
	)
	public LearningInfoResponse learningInfo() {
		return new LearningInfoResponse(properties.getTitle(), properties.getFocusTopics());
	}
}
