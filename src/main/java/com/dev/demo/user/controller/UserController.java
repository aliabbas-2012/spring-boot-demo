package com.dev.demo.user.controller;

import com.dev.demo.user.mapper.UserMapper;
import com.dev.demo.user.model.User;
import com.dev.demo.user.repository.UserRepository;
import com.dev.demo.user.service.UserService;
import com.dev.demo.user.validation.payload.SignupRequest;
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
    public UserController(UserService service, UserRepository userRepository) {
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

    @PostMapping
    public ResponseEntity<?> createEntity(@Valid @RequestBody SignupRequest signUpRequest) {
        User user = UserMapper.INSTANCE.toUser(signUpRequest);
        service.createEntity(user);

        return new ResponseEntity<>("User registered Successfully!", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public User updateEntity(@PathVariable Long id, @RequestBody User entity) {
        return service.updateEntity(id, entity);
    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.deleteEntity(id);
    }
}
