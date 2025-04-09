package com.dev.demo.auth.access;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import com.dev.demo.base.BaseService;


@Aspect
@Component
public class AuthorizeOwnershipAccess extends BaseService {


    @Around("@annotation(com.dev.demo.auth.access.AuthorizeOwnership)")
    public Object performAuthorization(ProceedingJoinPoint joinPoint) throws Throwable {
        // Extract method details
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AuthorizeOwnership annotation = signature.getMethod().getAnnotation(AuthorizeOwnership.class);
        Object[] args = joinPoint.getArgs();
        boolean authorized = isAuthorized((Long) args[0], annotation);
        if (!authorized) {
            throw new SecurityException("You do not have ownership of this record.");
        }

        // Proceed with the original method if ownership is valid
        return joinPoint.proceed();
    }

    private boolean isAuthorized(Long recordId, AuthorizeOwnership annotation) {
        List<String> allowedRoles = new ArrayList<>(Arrays.asList(annotation.value()));
        Long currentUserId = getCurrentUserId();
        return validateOwnership(currentUserId, recordId) || validateAllowedRoles(allowedRoles);
    }

    private boolean validateAllowedRoles(List<String> allowedRoles) {
        List<String> currentUserRoles =  getCurrentUserRoles();

        for (String element : currentUserRoles) {
            if (allowedRoles.contains(element)) {
                return true;
            }
        }
        return false;
    }


    private boolean validateOwnership(Long currentUserId, Long recordId) {
        return currentUserId.equals(recordId);
    }
}
