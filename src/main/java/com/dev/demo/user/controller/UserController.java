package com.dev.demo.user.controller;

import java.util.List;
import java.util.Set;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.dev.demo.user.mapper.UserMapper;
import com.dev.demo.user.model.User;
import com.dev.demo.user.service.UserService;
import com.dev.demo.user.validation.request.CreateUpdateRequest;



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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createEntity(@Valid @RequestBody CreateUpdateRequest createUpdateRequest) {
        User user = UserMapper.INSTANCE.toUser(createUpdateRequest);
        Set<String> requestedRoles = createUpdateRequest.getRequestedRoles();
        service.createEntity(user, requestedRoles);

        return new ResponseEntity<>("User created Successfully!", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEntity(@PathVariable Long id,
        @Valid @RequestBody CreateUpdateRequest createUpdateRequest) {

        User user = UserMapper.INSTANCE.toUser(createUpdateRequest);
        Set<String> requestedRoles = createUpdateRequest.getRequestedRoles();
        service.updateEntity(id, user, requestedRoles);
        return new ResponseEntity<>("User updated Successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteEntity(@PathVariable Long id) {
        service.deleteEntity(id);
    }
}
