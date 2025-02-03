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

    public static Predicate getSearchPredicate(CriteriaBuilder criteriaBuilder, Path<String> fieldPath, String searchValue, String operator) {
        switch (operator.toLowerCase()) {
            case "contains":
                return criteriaBuilder.like(fieldPath, "%" + searchValue + "%");
            case "startswith":
                return criteriaBuilder.like(fieldPath, searchValue + "%");
            case "endswith":
                return criteriaBuilder.like(fieldPath, "%" + searchValue);
            case "equals":
                return criteriaBuilder.equal(fieldPath, searchValue);
            default:
                throw new IllegalArgumentException("Unsupported search operator: " + operator);
        }
    }

    public static String[] splitFilter(String filter) {
        String[] parts = filter.split("=", 2);
        if (parts.length != 2) return null;

        String fieldOperator = parts[0];
        String value = parts[1];

        String[] fieldParts = fieldOperator.split("\\.", 2);
        if (fieldParts.length != 2) return null;

        return new String[]{fieldParts[0], fieldParts[1], value}; // ["field", "operator", "value"]
    }
}
