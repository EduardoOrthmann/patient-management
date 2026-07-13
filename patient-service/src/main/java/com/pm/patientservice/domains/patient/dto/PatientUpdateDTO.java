package com.pm.patientservice.domains.patient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pm.patientservice.util.validation.annotation.NullOrNotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUpdateDTO {

    @Schema(description = "Name of the patient", example = "John Doe")
    @NullOrNotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @Schema(description = "Email of the patient", example = "john.doe@example.com")
    @NullOrNotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Address of the patient", example = "123 Main St")
    @NullOrNotBlank(message = "Address cannot be blank")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @Schema(description = "Phone number of the patient", example = "555-1234")
    @NullOrNotBlank(message = "Phone cannot be blank")
    @Size(min = 1, max = 15, message = "Phone must be between 1 and 15 characters")
    private String phone;

    @Schema(description = "Date of birth of the patient", example = "01-01-1990")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
