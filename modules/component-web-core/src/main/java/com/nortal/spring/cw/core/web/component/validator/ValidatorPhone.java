package com.nortal.spring.cw.core.web.component.validator;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.element.AbstractValidator;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 21.04.2014
 */
public class ValidatorPhone extends AbstractValidator {
   private static final long serialVersionUID = 1L;

   private static final Pattern PATTERN = Pattern.compile("[0-9+\\s]+");

   @Override
   public void validate() {
      String value = super.getValue();
      if (StringUtils.isNotEmpty(value) && !PATTERN.matcher(value).matches()) {
         super.getElement().addElementErrorMessage("global.phone.format.error");
      }
   }
}
