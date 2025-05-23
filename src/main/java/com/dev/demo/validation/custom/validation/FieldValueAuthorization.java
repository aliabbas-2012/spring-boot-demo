package com.dev.demo.validation.custom.validation;

import java.lang.reflect.InvocationTargetException;

public interface FieldValueAuthorization {
    /**
     * Checks whether or not a given value exists for a given field
     *
     * @param value The value to check for
     * @param fieldName The name of the field for which to check if the value exists
     * @return True if the value exists for the field; false otherwise
     * @throws UnsupportedOperationException
     */
    boolean fieldValueAuthorize(Object value, String fieldName) throws UnsupportedOperationException, NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}