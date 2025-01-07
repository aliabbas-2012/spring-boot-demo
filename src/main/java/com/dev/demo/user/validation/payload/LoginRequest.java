package com.dev.demo.user.validation.payload;

import com.dev.demo.validation.custom.validation.StrongPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Username is required.")
    @Size(min = 5, max = 20, message = "The name must be from 5 to 20 characters.")
    private String username;

    @StrongPassword
    private String password;

}
