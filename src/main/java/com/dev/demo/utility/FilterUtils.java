package com.dev.demo.utility;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import java.util.List;

public class FilterUtils {

    public static boolean isValid(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public static boolean isValid(List<String> list) {
        return list != null && !list.isEmpty();
    }

    public static Predicate getTypedPredicate(CriteriaBuilder criteriaBuilder, Path<?> fieldPath, String value, String operator) {
        Class<?> fieldType = fieldPath.getJavaType();

        if (fieldType == String.class) {
            return getStringPredicate(criteriaBuilder, (Path<String>) fieldPath, value, operator);
        } else if (fieldType == Boolean.class) {
            return getBooleanPredicate(criteriaBuilder, (Path<Boolean>) fieldPath, value, operator);
        } else if (Number.class.isAssignableFrom(fieldType)) {
            return getNumericPredicate(criteriaBuilder, (Path<Number>) fieldPath, value, operator);
        } else {
            throw new IllegalArgumentException("Unsupported field type: " + fieldType.getSimpleName());
        }
    }

    private static Predicate getStringPredicate(CriteriaBuilder criteriaBuilder, Path<String> fieldPath, String value, String operator) {
        switch (operator.toLowerCase()) {
            case "contains": return criteriaBuilder.like(fieldPath, "%" + value + "%");
            case "startswith": return criteriaBuilder.like(fieldPath, value + "%");
            case "endswith": return criteriaBuilder.like(fieldPath, "%" + value);
            case "equals": return criteriaBuilder.equal(fieldPath, value);
            default: throw new IllegalArgumentException("Unsupported string operator: " + operator);
        }
    }

    private static Predicate getNumericPredicate(CriteriaBuilder criteriaBuilder, Path<Number> fieldPath, String value, String operator) {
        Double numericValue = Double.parseDouble(value); // Convert input to number

        switch (operator.toLowerCase()) {
            case "gt": return criteriaBuilder.gt(fieldPath.as(Double.class), numericValue);
            case "gte": return criteriaBuilder.ge(fieldPath.as(Double.class), numericValue);
            case "lt": return criteriaBuilder.lt(fieldPath.as(Double.class), numericValue);
            case "lte": return criteriaBuilder.le(fieldPath.as(Double.class), numericValue);
            case "equals": return criteriaBuilder.equal(fieldPath.as(Double.class), numericValue);
            default: throw new IllegalArgumentException("Unsupported numeric operator: " + operator);
        }
    }

    private static Predicate getBooleanPredicate(CriteriaBuilder criteriaBuilder, Path<Boolean> fieldPath, String value, String operator) {
        boolean boolValue = Boolean.parseBoolean(value);
        if ("equals".equalsIgnoreCase(operator)) {
            return criteriaBuilder.equal(fieldPath, boolValue);
        } else {
            throw new IllegalArgumentException("Unsupported boolean operator: " + operator);
        }
    }

    public static String[] splitFilter(String filter) {
        String[] parts = filter.split("=", 2);
        if (parts.length != 2) return null;

        String fieldOperator = parts[0];
        String value = parts[1];

        String[] fieldParts = fieldOperator.split("\\.", 2);
        if (fieldParts.length != 2) return null;

        return new String[]{fieldParts[0], fieldParts[1], value};
    }
}
