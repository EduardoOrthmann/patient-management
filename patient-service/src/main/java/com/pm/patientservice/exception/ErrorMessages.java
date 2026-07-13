package com.pm.patientservice.exception;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ErrorMessages {
    public final String VALIDATION_ERROR = "Validation error";
    public final String PATIENT_EMAIL_EXISTS = "Patient with email %s already exists";
    public final String PATIENT_NOT_FOUND = "Patient with id %s not found";
    public final String TYPE_MISMATCH_ERROR = "Type mismatch error";
    public final String INTERNAL_SERVER_ERROR = "Internal server error";
}
