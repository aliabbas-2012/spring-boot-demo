package com.test.demo2.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.test.demo2.models.Customer;
import com.test.demo2.respnose.ResponseHandler;
import com.test.demo2.respositery.CustomerRepository;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        return customer.orElse(null);
    }

    @PostMapping
    public ResponseEntity<Object> createCustomer(@RequestBody @Valid Customer customer, BindingResult bindingResult) {
        
        if(bindingResult.hasErrors()){
            Map<String, Object> errors = new HashMap<String, Object>();
            bindingResult.getAllErrors().forEach(error -> {
                errors.put(error.getObjectName(), error.getDefaultMessage());
            });
            return ResponseHandler.generateResponse("Looks like you missed a field. Please complete all sections.", HttpStatus.BAD_REQUEST, errors);
        }
        return ResponseHandler.generateResponse("Customer registered successfully!", HttpStatus.OK, customerRepository.save(customer));
    }

    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer updatedCustomer) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()) {
            Customer existingCustomer = customer.get();
            existingCustomer.setFirstName(updatedCustomer.getFirstName());
            existingCustomer.setLastName(updatedCustomer.getLastName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            return customerRepository.save(existingCustomer);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
