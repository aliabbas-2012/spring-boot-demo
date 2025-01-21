package com.dev.demo.validation.custom.validation;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.dev.demo.base.BaseService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

public class FieldAuthorizationValidator extends BaseService implements ConstraintValidator<FieldAuthorization, Object>  {

    @Autowired
    private ApplicationContext applicationContext;


    private String serviceName;
    private String fieldName;

    @Override
    public void initialize(FieldAuthorization annotation) {
        this.serviceName = annotation.service();
        this.fieldName = annotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object serviceBean = applicationContext.getBean(serviceName);
        if (!(serviceBean instanceof FieldValueAuthorization service)) {
            throw new IllegalArgumentException("Service must implement UniqueFieldService interface");
        }

        try {
            return service.fieldValueAuthorize(value, fieldName);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}