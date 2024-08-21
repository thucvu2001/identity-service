package com.thucvu.identityservice.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD}) // annotation apply with field
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobConstraint {

    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
