package com.test.demo2.validators;


import com.test.demo2.interfaces.CorrectEmail;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import com.test.demo2.respositery.CustomerRepository;

@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<CorrectEmail, String> {

    private final CustomerRepository repository;

    @Override
    public void initialize(CorrectEmail constraintAnnotation) {
        // You can perform initialization here if needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !repository.findByEmail(email).isPresent();
    }
}