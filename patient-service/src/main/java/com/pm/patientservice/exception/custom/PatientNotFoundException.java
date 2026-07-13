package com.pm.patientservice.exception.custom;

import com.pm.patientservice.exception.ErrorMessages;

import java.util.UUID;

public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException(UUID id) {
        super(String.format(ErrorMessages.PATIENT_NOT_FOUND, id));
    }
}
