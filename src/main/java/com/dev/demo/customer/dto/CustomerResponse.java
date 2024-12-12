package com.dev.demo.customer.dto;

public record CustomerResponse(Long id,
                               String firstName,
                               String lastName,
                               String email,
                               String fatherName
                               ) {
}
