package com.dev.demo.user.service;

public class OperatorHelper {

    public static String applyLikeOperator(String value, String operator) {
        return switch (operator) {
            case "startsWith" -> value + "%";
            case "endsWith" -> "%" + value;
            case "contains" -> "%" + value + "%";
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
