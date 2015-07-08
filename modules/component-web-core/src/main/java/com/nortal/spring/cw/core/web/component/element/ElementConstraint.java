package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

import com.nortal.spring.cw.core.web.component.composite.Component;

/**
 * Elemendi spetsiifiliste kitsenduste liides
 * 
 * @author Margus Hanni
 * @since 08.11.2013
 */
public interface ElementConstraint extends Serializable {

   void check();

   void setParent(Component parent);
}
