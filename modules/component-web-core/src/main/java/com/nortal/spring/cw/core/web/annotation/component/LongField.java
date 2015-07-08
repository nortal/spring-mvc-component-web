package com.nortal.spring.cw.core.web.annotation.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Margus Hanni
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LongField {
   boolean required() default false;

   String label() default "";

   long[] between() default { Long.MIN_VALUE, Long.MAX_VALUE };
}
