package com.nortal.spring.cw.core.web.component.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import com.nortal.spring.cw.core.web.component.element.AbstractValidator;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 27.05.2013
 */
public class ValidatorEmail extends AbstractValidator {
   private static final long serialVersionUID = 1L;

   @Override
   public void validate() {
      String value = super.getValue();
      if (StringUtils.isNotEmpty(value) && !EmailValidator.getInstance().isValid(value)) {
         super.getElement().addElementErrorMessage("global.email.format.error");
      }
   }
}
