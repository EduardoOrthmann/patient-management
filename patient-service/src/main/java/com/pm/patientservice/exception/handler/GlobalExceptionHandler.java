package com.pm.patientservice.exception.handler;

import com.pm.patientservice.exception.ErrorMessages;
import com.pm.patientservice.exception.custom.EmailAlreadyExistsException;
import com.pm.patientservice.exception.custom.PatientNotFoundException;
import com.pm.patientservice.exception.dto.ErrorDto;
import com.pm.patientservice.exception.dto.FieldErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ErrorMessages.VALIDATION_ERROR)
                        .status(HttpStatus.BAD_REQUEST.name())
                        .errors(ex.getBindingResult().getFieldErrors().stream()
                                .map(fieldError -> new FieldErrorDto(fieldError.getField(), fieldError.getDefaultMessage()))
                                .toList()
                        )
                        .build()
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ErrorMessages.TYPE_MISMATCH_ERROR)
                        .status(HttpStatus.BAD_REQUEST.name())
                        .errors(
                                List.of(new FieldErrorDto(ex.getName(), ex.getMessage()))
                        )
                        .build()
        );
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDto> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.BAD_REQUEST.name())
                        .build()
        );
    }

    @ExceptionHandler(PatientNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDto> handlePatientNotFoundException(PatientNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ErrorDto.builder()
                        .message(ex.getMessage())
                        .status(HttpStatus.NOT_FOUND.name())
                        .build()
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                ErrorDto.builder()
                        .message(ErrorMessages.INTERNAL_SERVER_ERROR)
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                        .build()
        );
    }

}
