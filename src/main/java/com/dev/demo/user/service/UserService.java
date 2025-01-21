package com.dev.demo.user.service;

import java.util.List;
import java.util.Set;
import com.dev.demo.user.model.User;
import org.springframework.data.jpa.repository.Query;

public interface UserService {
    Long getTotalUsers();
    List<User> getAllEntities();
    User getEntityById(Long id);
    void createEntity(User entity, Set<String> requestedRoles);
    void updateEntity(Long id, User payload, Set<String> requestedRoles) ;
    void deleteEntity(Long id);
}
