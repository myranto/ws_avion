package com.springboot.sgbd.inter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface KeyAnnotation{
    public String column() default "";
}
