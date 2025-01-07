package com.dev.demo.user.validation.payload;

import java.util.Set;
import com.dev.demo.validation.custom.validation.PasswordMatching;
import com.dev.demo.validation.custom.validation.StrongPassword;
import com.dev.demo.validation.custom.validation.Unique;
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

    @NotBlank(message = "Username is required.")
    @Size(min = 5, max = 20, message = "The name must be from 5 to 20 characters.")
    @Unique(service = "UserService", fieldName = "username", message = "username must be unique")
    private String username;

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    @Unique(service = "UserService", fieldName = "email", message = "email must be unique")
    private String email;

    @Unique(service = "UserService", fieldName = "phoneNumber", message = "phone must be unique")
    private String phoneNumber;
    private String address;


    @StrongPassword
    private String password;

    private String confirmPassword;

    private Set<String> role;

}
