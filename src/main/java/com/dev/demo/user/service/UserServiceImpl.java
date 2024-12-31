package com.dev.demo.user.service;

import com.dev.demo.user.model.User;
import com.dev.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Autowired
    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<User> getAllEntities() {
        return repository.findAll();
    }

    @Override
    public User getEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User createEntity(User entity) {
        return repository.save(entity);
    }

    @Override
    public User updateEntity(Long id, User entity) {
        if (repository.existsById(id)) {
            entity.setId(id);
            return repository.save(entity);
        }
        return null;
    }

    @Override
    public void deleteEntity(Long id) {
        repository.deleteById(id);
    }
}