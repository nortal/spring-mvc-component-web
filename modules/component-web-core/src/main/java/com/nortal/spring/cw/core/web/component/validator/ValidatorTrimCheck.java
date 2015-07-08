package com.nortal.spring.cw.core.web.component.validator;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.element.AbstractValidator;

/**
 * Validaatori eesmärgiks on kontrollida kas elemendi väärtus ei lõppe ega alga tühikuga. Kui eksisteerib tühik, kuvatakse veateade
 * 
 * @author Margus Hanni
 * @since 28.11.2013
 */
public class ValidatorTrimCheck extends AbstractValidator {

   private static final long serialVersionUID = 1L;

   @Override
   public void validate() {
      String value = super.getValue();
      if (value != null && !StringUtils.trimToEmpty(value).equals(value)) {
         super.getElement().addElementErrorMessage("global.trim.check.failed.there-are-spaces");
      }
   }
}
