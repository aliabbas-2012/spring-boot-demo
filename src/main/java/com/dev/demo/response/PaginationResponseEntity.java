package com.dev.demo.response;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class PaginationResponseEntity<T> extends ResponseEntity<Map<String, Object>> {

    public PaginationResponseEntity(Page<T> data) {
        super(createBody(data), determineStatus(data));
    }

    private static <T> Map<String, Object> createBody(Page<T> data) {
        if (data.isEmpty()) {
            return null;
        }
        Map<String, Object> response = new HashMap<>();
        response.put("items", data.getContent());
        response.put("total", data.getTotalElements());
        response.put("currentPage", data.getNumber());
        response.put("totalPages", data.getTotalPages());
        return response;
    }

    private static <T> HttpStatus determineStatus(Page<T> data) {
        return data.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
    }
}
