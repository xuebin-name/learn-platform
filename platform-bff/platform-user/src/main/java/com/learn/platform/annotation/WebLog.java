package com.learn.platform.annotation;


import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {

    String description() default "";

    String action() default "";

    String method() default "";

    String module() default "";

    String exception() default "";

    String level() default "";
}
