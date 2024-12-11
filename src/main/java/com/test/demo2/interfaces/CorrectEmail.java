package com.test.demo2.interfaces;

import java.lang.annotation.*;
import com.test.demo2.validators.UniqueEmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrectEmail {

    String message() default "Email must be unique.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}