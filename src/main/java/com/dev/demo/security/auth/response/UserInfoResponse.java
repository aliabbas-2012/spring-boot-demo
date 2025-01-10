package com.dev.demo.security.auth.response;

import java.util.List;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class UserInfoResponse {
    private Long id;
    private String username;
    private String email;
    private String jwtToken;
    private List<String> roles;
}
