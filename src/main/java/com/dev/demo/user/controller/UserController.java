package com.dev.demo.user.controller;

import com.dev.demo.user.mapper.UserMapper;
import com.dev.demo.user.model.User;
import com.dev.demo.user.repository.UserRepository;
import com.dev.demo.user.service.UserService;
import com.dev.demo.user.validation.payload.CreateUpdateRequest;
import com.dev.demo.user.validation.payload.SignupRequest;
import com.dev.demo.validation.custom.validation.Unique;
import jakarta.validation.Constraint;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;


    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> getAllEntities() {
        return service.getAllEntities();
    }

    @GetMapping("/{id}")
    public User getEntityById(@PathVariable Long id) {
        return service.getEntityById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = UserMapper.INSTANCE.toRegister(signUpRequest);
        service.createEntity(user);

        return new ResponseEntity<>("User registered Successfully!", HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createEntity(@Valid @RequestBody CreateUpdateRequest createUpdateRequest) {
        User user = UserMapper.INSTANCE.toUser(createUpdateRequest);
        service.createEntity(user);

        return new ResponseEntity<>("User created Successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable Long id,
        @Valid @RequestBody CreateUpdateRequest createUpdateRequest) {

        User user = UserMapper.INSTANCE.toUser(createUpdateRequest);
        service.updateEntity(id, user);
        return new ResponseEntity<>("User updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.deleteEntity(id);
    }
}
