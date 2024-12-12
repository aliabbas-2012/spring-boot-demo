package com.dev.demo.customer.respositery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.demo.customer.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}