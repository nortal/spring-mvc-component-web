package com.nortal.spring.cw.core.web.annotation.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Margus Hanni
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateTimeField {

   boolean required() default false;

   String label() default "";

   String[] between() default { StringUtils.EMPTY, StringUtils.EMPTY };

}
