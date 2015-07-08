package com.nortal.spring.cw.core.web.exception;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;

/**
 * @author Margus Hanni
 * 
 */
@SuppressWarnings("serial")
public class FieldNotFoundException extends AppBaseRuntimeException {

   private static final String FIELD_NOT_FOUND = "global.error.field-not-found";

   public FieldNotFoundException(Object... messageArgs) {
      super(FIELD_NOT_FOUND, messageArgs);
   }

}
