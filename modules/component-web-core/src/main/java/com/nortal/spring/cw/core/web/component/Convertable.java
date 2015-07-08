/**
 * 
 */
package com.nortal.spring.cw.core.web.component;

/**
 * Defines an interface for objects which can be validated and converted.
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 10.03.2014
 */
public interface Convertable {

   /**
    * Validates containing components and elements.
    */
   boolean validate();

   /**
    * Converts containing components and elements.
    */
   void convert();
}