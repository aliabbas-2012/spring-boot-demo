package com.dev.demo.security.login.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.dev.demo.security.login.models.ERole;
import com.dev.demo.security.login.models.Role;
import com.dev.demo.user.model.User;
import com.dev.demo.user.validation.request.LoginRequest;
import com.dev.demo.user.validation.request.SignupRequest;
import com.dev.demo.security.login.payload.response.UserInfoResponse;
import com.dev.demo.security.login.payload.response.MessageResponse;
import com.dev.demo.security.login.security.jwt.JwtUtils;
import com.dev.demo.security.login.security.services.UserDetailsImpl;

import com.dev.demo.user.mapper.UserMapper;
import com.dev.demo.user.service.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserService service;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        UserInfoResponse user = UserMapper.INSTANCE.toUserInfoResponse(userDetails);
        user.setJwtToken(jwtToken);
        user.setRoles(roles);

        return ResponseEntity
            .ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {


        // Create new user's account
        signUpRequest.setPassword(encoder.encode(signUpRequest.getPassword()));
        Set<String> requestedRoles = signUpRequest.getRequestedRoles();

        User user = UserMapper.INSTANCE.toRegister(signUpRequest);
        service.createEntity(user, requestedRoles);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @DeleteMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }
}