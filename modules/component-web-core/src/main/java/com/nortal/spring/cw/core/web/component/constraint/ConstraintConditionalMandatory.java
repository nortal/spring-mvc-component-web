package com.nortal.spring.cw.core.web.component.constraint;

import com.nortal.spring.cw.core.web.component.element.AbstractConstraint;
import com.nortal.spring.cw.core.web.component.element.FieldElementUtil;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * Constraint that allows to make mandatory fields that depend on another field.
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 21.10.2013
 */
public class ConstraintConditionalMandatory extends AbstractConstraint {
   private static final long serialVersionUID = 1L;

   private final String originalPath;
   private final String dependablePath;

   public ConstraintConditionalMandatory(String originalPath, String dependablePath) {
      this.originalPath = originalPath;
      this.dependablePath = dependablePath;
   }

   @Override
   public void check() {
      FormDataElement original = (FormDataElement) getElementById(this.originalPath);
      FormElement dependable = getElementById(this.dependablePath);

      if (FieldElementUtil.isFieldEmpty(original) && isMandatoryCondition(dependable)) {
         original.addElementErrorMessage("mandatory.if.another.is.filled", translate(dependable.getFullLabel()));
      }
   }
}