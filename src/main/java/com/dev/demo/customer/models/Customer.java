package com.dev.demo.customer.models;
import com.dev.demo.customer.validations.email.UniqueEmail;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "first_name:Must not be blank")
    private String firstName;
    
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "last_name:Must not be blank")
    private String lastName;

    @Column(name = "father_name", nullable = false)
    @NotBlank(message = "father_name:Must not be blank")
    private String fatherName;

    @Column(unique = true)
    @NotBlank(message = "email: Should be required")
    @Email(message = "email:Email should be valid")
    @UniqueEmail(message = "email:Email Should be unique")
    private String email;

}