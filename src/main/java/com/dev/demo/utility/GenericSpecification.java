package com.dev.demo.utility;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;

public class GenericSpecification<T> {

    public static <T> Specification<T> build(List<String> filterStrings, String search, List<String> searchColumns) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (FilterUtils.isValid(search) && FilterUtils.isValid(searchColumns)) {
                applySearch(root, criteriaBuilder, search, searchColumns, predicates);
            }

            if (FilterUtils.isValid(filterStrings)) {
                applyFilters(root, criteriaBuilder, filterStrings, predicates);
            }

            return predicates.isEmpty()
                    ? criteriaBuilder.conjunction()
                    : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static <T> void applySearch(Root<T> root, CriteriaBuilder criteriaBuilder, String search, List<String> searchColumns, List<Predicate> predicates) {
        searchColumns.forEach(searchColumn -> {
            String[] parts = searchColumn.split("\\.");
            if (parts.length != 2) throw new IllegalArgumentException("Invalid search format: " + searchColumn);

            String fieldName = parts[0];
            String operator = parts[1];

            Path<?> fieldPath = root.get(fieldName);
            predicates.add(FilterUtils.getTypedPredicate(criteriaBuilder, fieldPath, search, operator));
        });
    }

    private static <T> void applyFilters(Root<T> root, CriteriaBuilder criteriaBuilder, List<String> filterStrings, List<Predicate> predicates) {
        filterStrings.forEach(filter -> {
            String[] parts = FilterUtils.splitFilter(filter);
            if (parts == null) throw new IllegalArgumentException("Invalid filter format: " + filter);

            Path<?> fieldPath = root.get(parts[0]);
            predicates.add(FilterUtils.getTypedPredicate(criteriaBuilder, fieldPath, parts[2], parts[1]));
        });
    }
}
