package com.dev.demo.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(@NotBlank String firstName,
                              @NotBlank String lastName,
                              @NotBlank @Email String email,
                              @NotBlank String fatherName
                              ) {
}
