package com.test.demo2.respositery;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo2.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}