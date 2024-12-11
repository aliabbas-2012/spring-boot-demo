package com.test.demo2.validators;

import com.test.demo2.interfaces.CorrectEmail;
import com.test.demo2.models.Customer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.test.demo2.respositery.CustomerRepository;

import java.util.Optional;

@Component
public class UniqueEmailValidator implements ConstraintValidator<CorrectEmail, String> {

    @Autowired
    private CustomerRepository repository;

    @Override
    public void initialize(CorrectEmail constraintAnnotation) {
        // You can perform initialization here if needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null) {
            return true; // Null values are handled by @NotNull if needed
        }
        System.out.println("=============");
        System.out.println(email);

        try {
            Optional<Customer> customer = repository.findByEmail(email);
            return !customer.isPresent();
        } catch (NullPointerException e) {
            return true;
        }
    }
}
