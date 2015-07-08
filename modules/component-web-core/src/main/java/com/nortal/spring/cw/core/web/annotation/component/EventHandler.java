package com.nortal.spring.cw.core.web.annotation.component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventHandler {
   String eventName();

   /**
    * Kas tegemist on faili alla laadimisega. Kui TRUE siis peale handler
    * meetodi käivitamist päringut vaatesse edasi ei suunata
    * 
    */
   boolean downloadStream() default false;
}
