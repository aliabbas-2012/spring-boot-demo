package com.dev.demo.user.validation.request;

import java.util.Set;

import org.hibernate.validator.constraints.UniqueElements;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import com.dev.demo.validation.custom.validation.StrongPassword;
import com.dev.demo.validation.custom.validation.Unique;
import com.dev.demo.validation.custom.validation.FieldAuthorization;
import lombok.Data;


@Data
public class CreateUpdateRequest {

    private Long id; // For update scenarios

    @NotBlank(message = "Name is required.")
    @Size(min = 3, max = 20, message = "The name must be from 3 to 20 characters.")
    protected String name;

    @NotBlank(message = "Username is required.")
    @Size(min = 5, max = 20, message = "The name must be from 5 to 20 characters.")
    @Unique(service = "UserService", fieldName = "username", message = "username must be unique")
    protected String username;

    @NotEmpty(message = "The email is required.")
    @Email(message = "The email is not a valid email.")
    @Unique(service = "UserService", fieldName = "email", message = "email must be unique")
    protected String email;

    @Unique(service = "UserService", fieldName = "phoneNumber", message = "phone must be unique")
    protected String phoneNumber;
    protected String address;


    @StrongPassword
    protected String password;

    @NotEmpty(message = "one role is required least.")
    @UniqueElements
    @FieldAuthorization(service = "UserService", fieldName = "requestedRoles",  message = "You cannot update your role!, unless you are an admin")
    private Set<String> requestedRoles;

    @FieldAuthorization(service = "UserService", fieldName = "isActive", message = "You cannot change your status")
    private Boolean isActive;

}
