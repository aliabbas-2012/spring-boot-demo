package com.dev.demo.user.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import com.dev.demo.validation.custom.validation.FieldValueAuthorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dev.demo.auth.models.ERole;
import com.dev.demo.auth.models.Role;
import com.dev.demo.base.BaseService;
import com.dev.demo.user.repository.UserRepository;
import com.dev.demo.auth.repository.RoleRepository;
import com.dev.demo.validation.custom.validation.FieldValueExists;
import com.dev.demo.user.model.User;

@Service("UserService")
public class UserServiceImpl extends BaseService implements UserService, FieldValueExists, FieldValueAuthorization {

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Long getTotalUsers() {
        return repository.count();
    }

    @Override
    public List<User> getAllEntities() {
        return repository.fetchAllUsers();
    }

    @Override
    public User getEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }


    @Override
    public void createEntity(User entity, Set<String> requestedRoles) {
        Set<Role> roles = assignRoles(requestedRoles);
        entity.setRoles(roles);
        entity.setPassword(encodePassword(entity.getPassword()));
        repository.save(entity);
    }

    @Override
    public void updateEntity(Long id, User payload, Set<String> requestedRoles) {
        if (repository.existsById(id)) {
            User existingEntity = repository.findById(id).orElse(null);
            payload.setId(id);
            updateNonNullFields(payload, existingEntity);
            Set<Role> roles = assignRoles(requestedRoles);
            existingEntity.setRoles(roles);
            existingEntity.setPassword(encodePassword(payload.getPassword()));
            repository.save(Objects.requireNonNull(existingEntity));
        }
    }

    @Override
    public void deleteEntity(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (value == null || fieldName == null || fieldName.isEmpty()) {
            return false; // Skip validation for null or empty fields
        }

        String methodName = "existsBy" + capitalize(fieldName)+"AndIdNot";
        Method method = repository.getClass().getMethod(methodName, String.class, Long.class);

        // Dynamically invoke the method with the provided value
        return !(boolean) method.invoke(repository, value.toString(), getIdParameterFromRequest("users"));
    }

    @Override
    public boolean fieldValueAuthorize(Object value, String fieldName) {

        if (isPublicEndPoint()) {
            if (value instanceof Set<?> valueSet) {
                return validateAssignedRoles(valueSet);
            }
            else return value instanceof Boolean;
        }
        else {
            List<String> roles = getCurrentUserRoles();
            if (roles.contains(ERole.ROLE_ADMIN.toString())) return true;

            if (value instanceof Set<?> valueSet) {
                return validateAssignedRoles(valueSet, roles);
            } else if (value instanceof Boolean) {
                // Prevent changes to the 'active' field if it's a boolean
                System.out.println(getCurrentUserIsEnabled());
                return value == getCurrentUserIsEnabled(); // Return false if the user attempts to change `active` to true
            }
        }

        return false;
    }

    private boolean validateAssignedRoles(Set<?> valueSet, List<String> roles) {
        return (valueSet.contains("mod") && roles.contains(ERole.ROLE_MODERATOR.toString())) ||
                (valueSet.contains("user") && roles.contains(ERole.ROLE_USER.toString()));
    }

    private boolean validateAssignedRoles(Set<?> valueSet) {
        return valueSet.contains("user") && valueSet.size() == 1;
    }

    private Set<Role> assignRoles(Set<String> requestedRoles) {
        Set<Role> roles = new HashSet<Role>();
        if (requestedRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {

            requestedRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }
        return roles;
    }
}