package com.buhhu8.consumer.config.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention( RetentionPolicy.RUNTIME)
public @interface SwitchDataSource {

    String value() default "";
}
