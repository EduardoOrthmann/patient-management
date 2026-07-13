package com.pm.patientservice.exception.dto;

public record FieldErrorDto(
        String field,
        String message
) {
}
