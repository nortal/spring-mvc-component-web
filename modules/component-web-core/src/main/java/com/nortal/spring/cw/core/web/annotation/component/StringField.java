package com.nortal.spring.cw.core.web.annotation.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Margus Hanni
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface StringField {

   int length() default Integer.MAX_VALUE;

   boolean required() default false;

   boolean multiple() default false;

   int rows() default 1;

   int cols() default 25;

   String label() default "";
}
