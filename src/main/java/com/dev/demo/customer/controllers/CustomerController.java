package com.dev.demo.customer.controllers;

import com.dev.demo.customer.dto.CustomerRequest;
import com.dev.demo.customer.dto.CustomerResponse;
import com.dev.demo.customer.service.CustomerService;
import com.dev.demo.customer.validations.email.UniqueEmail;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import com.dev.demo.customer.models.Customer;
import com.dev.demo.customer.respnose.ResponseHandler;
import com.dev.demo.customer.respositery.CustomerRepository;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerRepository customerRepository;
    private final CustomerService service;


    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    @PostMapping("/customers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponse> createCustomer(
            @NotNull
            @Valid
            @RequestBody
            @UniqueEmail
            CustomerRequest request) {
        return ResponseEntity.ofNullable(service.saveCustomer(request));
    }

    @PutMapping("/customers/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            updatedCustomer.setId(existingCustomer.getId());
            return customerRepository.save(updatedCustomer);
        }
        return null;
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
