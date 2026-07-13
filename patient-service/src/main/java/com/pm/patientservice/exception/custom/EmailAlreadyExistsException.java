package com.pm.patientservice.exception.custom;

import com.pm.patientservice.exception.ErrorMessages;

public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super(String.format(ErrorMessages.PATIENT_EMAIL_EXISTS, email));
    }
}
