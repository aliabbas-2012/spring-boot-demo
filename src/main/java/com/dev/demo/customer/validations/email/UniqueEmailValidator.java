package com.dev.demo.customer.validations.email;

import com.dev.demo.customer.dto.CustomerRequest;
import com.dev.demo.customer.models.Customer;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.dev.demo.customer.respositery.CustomerRepository;

import java.util.Optional;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private CustomerRepository repository;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        // You can perform initialization here if needed
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("------here.-------");
        System.out.println(email);
        if (email == null) return true;

        try {
            Optional<Customer> customer = repository.findByEmail(email);
            return !customer.isPresent();
        } catch (NullPointerException e) {
            return true;
        }
    }
}
