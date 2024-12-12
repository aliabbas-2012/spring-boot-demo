package com.dev.demo.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import com.dev.demo.customer.validations.email.UniqueEmail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;


@AllArgsConstructor
@Data
public class CustomerRequest {

    @Autowired
    @NotBlank(message = "firstName:Must not be blank")
    private String firstName;

    @Autowired
    @NotBlank(message = "lastName:Must not be blank")
    private String lastName;

    @Autowired
    @NotBlank(message = "fatherName:Must not be blank")
    private String fatherName;

    @Autowired
    @NotBlank(message = "email:Email is mandatory")
    @Email(message = "email:Invalid email format")
    @UniqueEmail
    private String email;

}