package com.dev.demo.user.service;

import com.dev.demo.user.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    List<User> getAllEntities();
    User getEntityById(Long id);
    void createEntity(User entity, Set<String> requestedRoles);
    void updateEntity(Long id, User payload);
    void deleteEntity(Long id);
}