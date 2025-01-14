package com.dev.demo.auth.access;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizeOwnership {
    String[] value();
}
