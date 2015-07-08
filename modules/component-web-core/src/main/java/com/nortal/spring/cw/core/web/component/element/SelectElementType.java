package com.nortal.spring.cw.core.web.component.element;

/**
 * @author Margus Hanni
 * @since 12.03.2013
 */
public enum SelectElementType {

   RADIO(false, ""), SELECT(false, ""), MULTISELECT(true, "multiselect"), CHECKBOX(true, ""), LIST(false, "");

   private boolean multiSelect;
   private String cssClass;

   private SelectElementType(boolean multiSelect, String cssClass) {
      this.multiSelect = multiSelect;
      this.cssClass = cssClass;
   }

   public boolean isMultiSelect() {
      return multiSelect;
   }

   public String getCssClass() {
      return cssClass;
   }
}
