package com.nortal.spring.cw.core.web.component.event;

import com.nortal.spring.cw.core.web.component.css.ButtonElementCssClass;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;

/**
 * 
 * Standardne nupu element tüüpi button.
 * 
 * 
 * @author Margus Hanni
 * @since 01.03.2013
 */
public class ButtonElement extends AbstractEventElement {

   private static final long serialVersionUID = 1L;

   private static final String ELEMENT_NAME = "button";

   private ButtonElementCssClass cssButtonClass = ButtonElementCssClass.BUTTON;

   public ButtonElement() {
      super(ELEMENT_NAME);
   }

   public ButtonElement(String label) {
      this(ELEMENT_NAME, null, label);
   }

   public ButtonElement(String eventName, String label) {
      this(ELEMENT_NAME, eventName, label);
   }

   public ButtonElement(String elementName, String eventName, String label) {
      this(elementName, eventName, label, ButtonElementCssClass.BUTTON);
   }

   public ButtonElement(String eventName, String label, ButtonElementCssClass cssClass) {
      this(ELEMENT_NAME, eventName, label, cssClass);
   }

   public ButtonElement(String elementName, String eventName, String label, ButtonElementCssClass cssClass) {
      super(elementName, label, eventName);
      this.cssButtonClass = cssClass;
   }

   public ButtonElementCssClass getButtonCssClass() {
      return cssButtonClass;
   }

   public ButtonElement setButtonCssClass(ButtonElementCssClass cssClass) {
      this.cssButtonClass = cssClass;
      return this;
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.BUTTON;
   }
}
