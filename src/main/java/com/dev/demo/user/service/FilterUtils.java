package com.dev.demo.user.service;

import java.util.*;
import java.util.regex.*;

import jakarta.persistence.criteria.*;

public class FilterUtils {

    private static final Pattern FILTER_PATTERN = Pattern.compile("(.+)\\.(.+)");

    public static boolean isValid(String str) {
        return str != null && !str.isEmpty();
    }

    public static boolean isValid(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static String[] splitFilter(String filter) {
        String[] parts = filter.split("=", 2);
        if (parts.length != 2) return null;

        Matcher matcher = FILTER_PATTERN.matcher(parts[0]);
        return matcher.matches() ? new String[]{matcher.group(1), matcher.group(2), parts[1]} : null;
    }

    public static Predicate[] getSearchPredicates(CriteriaBuilder cb, Path<String> path, String value, List<String> operators) {
        return operators.stream()
                .map(operator -> switch (operator.toLowerCase()) {
                    case "contains" -> cb.like(path, "%" + value + "%");
                    case "startswith" -> cb.like(path, value + "%");
                    case "endswith" -> cb.like(path, "%" + value);
                    case "exact" -> cb.equal(path, value);
                    default -> throw new IllegalArgumentException("Invalid search operator: " + operator);
                })
                .toArray(Predicate[]::new);
    }
}
