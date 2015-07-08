/**
 * 
 */
package com.nortal.spring.cw.core.exception;

/**
 * Kasutatakse veaolukorras kasutajale üldise teate näitamiseks.
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 03.02.2014
 */
public class GenericFailureException extends AppBaseRuntimeException {
   private static final long serialVersionUID = 1L;

   private static final String ERROR_CODE = "global.page.does.not.exist.or.not.sufficient.privileges";

   public GenericFailureException() {
      super(ERROR_CODE);
   }

   public GenericFailureException(Object... messageArgs) {
      super(null, ERROR_CODE, messageArgs);
   }
}