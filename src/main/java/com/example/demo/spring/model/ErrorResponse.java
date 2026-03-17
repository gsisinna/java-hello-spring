package com.example.demo.spring.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Error payload returned when the API rejects a request.")
public record ErrorResponse(String message, String hint) {
}
