package com.dev.demo.validation.custom.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Constraint(validatedBy = FieldAuthorizationValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldAuthorization {
    String message() default "Field authorization check failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String service(); // Name of the service bean
    String fieldName(); // Name of the field to check

}