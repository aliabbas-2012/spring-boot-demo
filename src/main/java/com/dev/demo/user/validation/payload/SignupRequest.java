package com.dev.demo.user.validation.payload;

import com.dev.demo.validation.custom.validation.PasswordMatching;
import com.dev.demo.validation.custom.validation.StrongPassword;
import jakarta.validation.constraints.*;
import lombok.Data;
@Data
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password must be matched!"
)
public class SignupRequest {

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 20, message = "The name must be from 3 to 20 characters.")
    private String name;

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    private String email;

    private String phoneNumber;
    private String address;


    @StrongPassword
    private String password;

    private String confirmPassword;

}
