package com.dev.demo.user.validation.payload;

import com.dev.demo.validation.custom.validation.StrongPassword;
import com.dev.demo.validation.custom.validation.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateUpdateRequest {

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 20, message = "The name must be from 3 to 20 characters.")
    private String name;

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    @Unique(service = "UserService", fieldName = "email", message = "email must be unique")
    private String email;

    @Unique(service = "UserService", fieldName = "phoneNumber", message = "phone must be unique")
    private String phoneNumber;
    private String address;

    @StrongPassword
    private String password;

}
