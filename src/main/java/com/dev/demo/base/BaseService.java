package com.dev.demo.base;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import com.dev.demo.auth.security.jwt.JwtUtils;
import com.dev.demo.auth.security.services.UserDetailsImpl;
import com.dev.demo.validation.custom.validation.Unique;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;



@Service("BaseService")
public abstract class BaseService {

    @Autowired
    WebRequest webRequest;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

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

    protected Boolean isPublicEndPoint() {
        String requestUri = webRequest.getDescription(false); // false excludes scheme info like "http://"
        return requestUri.matches("^uri=/api/auth/.*$")  || requestUri.matches("^uri=/api/test/.*$");
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

    protected Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getId();
    }

    protected Boolean getCurrentUserIsEnabled() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.isEnabled();
    }

    protected List<String> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

}
