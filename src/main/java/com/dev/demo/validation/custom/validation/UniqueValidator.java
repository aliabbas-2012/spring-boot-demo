package com.dev.demo.validation.custom.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;

public class UniqueValidator implements ConstraintValidator<Unique, Object> {

    @Autowired
    private ApplicationContext applicationContext;

    private String serviceName;
    private String fieldName;
    private String idField;

    @Override
    public void initialize(Unique annotation) {
        this.serviceName = annotation.service();
        this.fieldName = annotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Skip null values
        }
        Object serviceBean = applicationContext.getBean(serviceName);
        if (!(serviceBean instanceof FieldValueExists service)) {
            throw new IllegalArgumentException("Service must implement UniqueFieldService interface");
        }
        try {
            return service.fieldValueExists(value, fieldName);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}