package com.test.demo2.respnose;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseHandler {
	public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
		Map<String, Object> map = new HashMap<String, Object>();
        Integer status_value = status.value();
		map.put("message", message);
		map.put("status", status_value);
        if (status_value <= 201) {
            map.put("data", responseObj);
        }
		else {
            map.put("errors", responseObj);
        }

		return new ResponseEntity<Object>(map, status);
	}
}