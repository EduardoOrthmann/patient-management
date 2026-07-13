package com.pm.patientservice.domains.patient.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pm.patientservice.util.validation.annotation.NullOrNotBlank;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatientUpdateDTO {
    @NullOrNotBlank(message = "Name cannot be blank")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    private String name;

    @NullOrNotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NullOrNotBlank(message = "Address cannot be blank")
    @Size(min = 1, max = 200, message = "Address must be between 1 and 200 characters")
    private String address;

    @NullOrNotBlank(message = "Phone cannot be blank")
    @Size(min = 1, max = 15, message = "Phone must be between 1 and 15 characters")
    private String phone;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
