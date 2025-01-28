package com.dev.demo.user.service;

import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GenericSpecification<T> {

    public static <T> Specification<T> withDynamicFilters(List<String> filterStrings) {
        return (Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filterStrings != null) {
                for (String filterString : filterStrings) {
                    String[] parts = filterString.split("=", 2);
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid filter format: " + filterString);
                    }

                    String fieldOperator = parts[0];
                    String value = parts[1];

                    Matcher matcher = Pattern.compile("(.+)\\.(.+)").matcher(fieldOperator);
                    if (!matcher.matches()) {
                        throw new IllegalArgumentException("Invalid filter format: " + filterString);
                    }

                    String field = matcher.group(1);
                    String operator = matcher.group(2);

                    switch (operator) {
                        case "startsWith":
                            predicates.add(criteriaBuilder.like(root.get(field), value + "%"));
                            break;
                        case "endsWith":
                            predicates.add(criteriaBuilder.like(root.get(field), "%" + value));
                            break;
                        case "contains":
                            predicates.add(criteriaBuilder.like(root.get(field), "%" + value + "%"));
                            break;
                        default:
                            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
                    }
                }
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
