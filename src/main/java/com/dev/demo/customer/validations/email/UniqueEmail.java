package com.dev.demo.customer.validations.email;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "Email must be unique.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}