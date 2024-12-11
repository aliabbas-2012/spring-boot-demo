package com.test.demo2.respositery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo2.models.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}