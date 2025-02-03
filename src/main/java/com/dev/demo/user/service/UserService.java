package com.dev.demo.user.service;


import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.dev.demo.user.model.User;


public interface UserService {
    Long getTotalUsers();
    Page<User> getAllEntities(Pageable pageable, String search, String[] filters, String[] searchColumns);
    User getEntityById(Long id);
    void createEntity(User entity, Set<String> requestedRoles);
    void updateEntity(Long id, User payload, Set<String> requestedRoles) ;
    void deleteEntity(Long id);
}
