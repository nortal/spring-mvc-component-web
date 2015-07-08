package com.nortal.spring.cw.core.web.annotation.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Margus Hanni
 * @since 11.03.2013
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerField {
   boolean required() default false;

   String label() default "";

   int[] between() default { Integer.MIN_VALUE, Integer.MAX_VALUE };
}
