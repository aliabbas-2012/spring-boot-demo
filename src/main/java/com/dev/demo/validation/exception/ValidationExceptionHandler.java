package com.dev.demo.validation.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> field_errors = new HashMap<String, String>();
        Map<String, String> general_errors = new HashMap<String, String>();

        ex.getAllErrors().forEach(err -> {
            if (Objects.equals(err.getCode(), "PasswordMatching")) {
                general_errors.put(err.getCode(),err.getDefaultMessage());
            }
        });
        ex.getBindingResult().getFieldErrors().forEach(err -> field_errors.put(err.getField(),err.getDefaultMessage()));

        Map<String, Object> result = new HashMap<>();
        result.put("field_errors", field_errors);
        result.put("general_errors", general_errors);

        return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
    }
}
