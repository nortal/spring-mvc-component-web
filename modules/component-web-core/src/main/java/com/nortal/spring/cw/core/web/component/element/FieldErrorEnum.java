package com.nortal.spring.cw.core.web.component.element;

/**
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 15.08.2013
 */
public enum FieldErrorEnum {

   // @formatter:off
   MANDATORY("field.error.mandatory"), 
   DOWNLOAD("field.error.download")
   ;
   // @formatter:on

   private String translationKey;

   private FieldErrorEnum(String translationKey) {
      this.translationKey = translationKey;
   }

   public String getTranslationKey() {
      return translationKey;
   }
}