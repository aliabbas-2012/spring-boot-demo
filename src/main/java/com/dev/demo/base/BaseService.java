package com.dev.demo.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Field;

@Service("BaseService")
public class BaseService {

    @Autowired
    WebRequest webRequest;

    @Autowired
    PasswordEncoder encoder;

    protected Long getIdParameterFromRequest(String controller) {
        String requestUri = webRequest.getDescription(false); // false excludes scheme info like "http://"
        String route = String.format("/api/%s/", controller);
        String path = requestUri.substring(requestUri.indexOf(route) + route.length());
        String id = path.contains("/") ? path.substring(0, path.indexOf("/")) : path;
        if (id.isEmpty() || !id.matches("\\d+")) {
            return 0L; // Return 0 if ID is missing or invalid
        }
        return  Long.parseLong(id);
    }

    // Helper method to capitalize the first letter of a string
    protected String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    protected void updateNonNullFields(Object source, Object target) {
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

    protected String encodePassword(String password) {
        return encoder.encode(password);
    }
}
