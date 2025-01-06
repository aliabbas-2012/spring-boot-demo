package com.dev.demo.user.service;

import com.dev.demo.user.model.User;
import com.dev.demo.user.repository.UserRepository;
import org.springframework.web.context.request.WebRequest;
import com.dev.demo.validation.custom.validation.FieldValueExists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service("UserService")
public class UserServiceImpl implements UserService, FieldValueExists {

    private final UserRepository repository;
    private final Map<String, Method> methodCache = new HashMap<>();
    private final WebRequest webRequest;

    @Autowired
    public UserServiceImpl(UserRepository repository, WebRequest webRequest) {
        this.repository = repository;
        this.webRequest = webRequest;
    }


    public Long getUserIdFromRequest() {
        String requestUri = webRequest.getDescription(false); // false excludes scheme info like "http://"
        // Extract the last segment of the URI (e.g., '1' from '/api/users/1')
        String path = requestUri.substring(requestUri.indexOf("/api/users/") + "/api/users/".length());
        String id = path.contains("/") ? path.substring(0, path.indexOf("/")) : path;

        return  Long.parseLong(id);
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
    public User updateEntity(Long id, User payload) {
        if (repository.existsById(id)) {
            User existingEntity = repository.findById(id).orElse(null);
            payload.setId(id);
            updateNonNullFields(payload, existingEntity);
            return repository.save(Objects.requireNonNull(existingEntity));
        }
        return null;
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
        System.out.println(methodName);
        Method method = repository.getClass().getMethod(methodName, String.class, Long.class);

        // Dynamically invoke the method with the provided value
        return !(boolean) method.invoke(repository, value.toString(), getUserIdFromRequest());
    }

    // Helper method to capitalize the first letter of a string
    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    private void updateNonNullFields(Object source, Object target) {
        Field[] fields = source.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(source);
                if (value != null) { // Only update non-null fields
                    Field targetField = target.getClass().getDeclaredField(field.getName());
                    targetField.setAccessible(true);
                    targetField.set(target, value);
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Log error or handle exceptions as needed
                System.err.println("Field update failed: " + field.getName());
            }
        }
    }
}