package com.dev.demo.user.validation.request;

import com.dev.demo.validation.custom.validation.PasswordMatching;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@PasswordMatching(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password must be matched!"
)
public class SignupRequest extends CreateUpdateRequest {
    private String confirmPassword;
}
