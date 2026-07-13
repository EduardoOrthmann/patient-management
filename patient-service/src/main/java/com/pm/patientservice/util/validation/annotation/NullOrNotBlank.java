package com.pm.patientservice.util.validation.annotation;

import com.pm.patientservice.util.validation.validator.NullOrNotBlankValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NullOrNotBlankValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrNotBlank {
    String message() default "Name must be null or not blank";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
