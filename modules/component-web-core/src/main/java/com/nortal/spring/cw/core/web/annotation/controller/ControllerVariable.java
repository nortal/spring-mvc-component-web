package com.nortal.spring.cw.core.web.annotation.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Nimetab ära Controlleri poolt kasutatavad parameetrid. Parameetrid sisalduvad
 * URLis Controller URL mappingu lõpus. Parameetrite olemasolu ei ole
 * kohustuslik, küll aga järjekord.
 * 
 * @author Alrik Peets
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerVariable {
   String[] names() default {};
}
