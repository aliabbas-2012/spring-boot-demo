package com.dev.demo.user.service;

import com.dev.demo.user.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllEntities();
    User getEntityById(Long id);
    User createEntity(User entity);
    User updateEntity(Long id, User payload);
    void deleteEntity(Long id);
}