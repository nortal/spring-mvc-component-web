package com.nortal.spring.cw.core.exception;

/**
 * Erind, mis kutsutakse välja vigase päringu puhul, mis tähendab seda, et kas parameetrid on valet tüüpi või täpsustamata
 * 
 * @author Margus Hanni
 * @since 21.10.2013
 */
public class RequestParameterException extends GenericFailureException {
   private static final long serialVersionUID = 1L;

   public RequestParameterException(Object... messageArgs) {
      super(messageArgs);
   }
}