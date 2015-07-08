package com.nortal.spring.cw.core.exception;

/**
 * Erindit kasutatakse olukordades kus üritatakse luua sama tüüpi dokumenti
 * korduvalt, kuid teatud tingimustel ei ole see lubatud. Nagu näiteks sama
 * liiki dokument on juba loodud ning teist sellist dokumenti ei ole lubatud
 * luua.
 * 
 * @author Margus Hanni
 * @since 22.10.2013
 */
public class DocumentAlreadyExistsException extends AppBaseRuntimeException {
   private static final long serialVersionUID = 1L;

   public DocumentAlreadyExistsException() {
      super("global.error.document-already-exists");
   }
}
