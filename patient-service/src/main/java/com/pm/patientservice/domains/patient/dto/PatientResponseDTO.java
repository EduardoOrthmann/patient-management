package com.pm.patientservice.domains.patient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientResponseDTO {
    @Schema(description = "Unique identifier of the patient", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Name of the patient", example = "John Doe")
    private String name;

    @Schema(description = "Email of the patient", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Address of the patient", example = "123 Main St")
    private String address;

    @Schema(description = "Date of birth of the patient", example = "01-01-1990")
    private LocalDate dateOfBirth;
}
