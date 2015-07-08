package com.nortal.spring.cw.core.web.component.event;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.EventBaseElement;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * @author Margus Hanni
 */
public abstract class AbstractEventElement extends EventBaseElement implements FormElement {

   private static final long serialVersionUID = 1L;
   private FieldElementCssClass cssClass;
   private boolean sendFormDataOnEvent = true;
   private String params = StringUtils.EMPTY;

   public AbstractEventElement(String elementName) {
      super(elementName);
      setEditable(false);
   }

   public AbstractEventElement(String elementName, String label) {
      super(elementName, label, null);
   }

   public AbstractEventElement(String elementName, String label, String eventName) {
      super(elementName, label, eventName);
   }

   @Override
   public int compareTo(Object o) {
      if (!(o instanceof FormElement)) {
         return 0;
      }

      return getLabel().compareTo(((FormElement) o).getLabel());
   }

   @Override
   public FormElement setCssClass(FieldElementCssClass cssClass) {
      this.cssClass = cssClass;
      return this;
   }

   public FieldElementCssClass getCssClass() {
      return cssClass;
   }

   @Override
   public String getDisplayValue() {
      return getFullLabel();
   }

   public boolean isSendFormDataOnEvent() {
      return sendFormDataOnEvent;
   }

   public void setSendFormDataOnEvent(boolean sendFormDataOnEvent) {
      this.sendFormDataOnEvent = sendFormDataOnEvent;
   }

   public String getParams() {
      return params;
   }

   public void setParams(String params) {
      this.params = params;
   }
}
