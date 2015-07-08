package com.nortal.spring.cw.core.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * JAXB standard erind, mis kutsutakse välja juhul kui valideerimine ei õnnestunud. Sõnumi koodiks on: global.jaxb.error.xml-validating
 * 
 * @author Margus Hanni
 * @since 05.12.2013
 */
public class JaxbValidatingException extends AppBaseRuntimeException {

   private static final long serialVersionUID = 1L;

   public JaxbValidatingException(Throwable throwable) {
      super(throwable, "global.jaxb.error.xml-validating", ExceptionUtils.getRootCauseMessage(throwable));
   }

}
