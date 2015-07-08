package com.nortal.spring.cw.core.web.component.modal;

/**
 * @author Margus Hanni
 * @since 04.03.2013
 */
public enum ModalSize {

   HUGE("huge"), LARGE("large");

   private String size;

   private ModalSize(String size) {
      this.size = size;
   }

   public String getValue() {
      return this.size;
   }

}
