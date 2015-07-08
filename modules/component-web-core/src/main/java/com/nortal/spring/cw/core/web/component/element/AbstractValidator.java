package com.nortal.spring.cw.core.web.component.element;

/**
 * Elemendi validaatori abstraktsioon
 * 
 * @author Margus Hanni
 * @since 08.11.2013
 */
public abstract class AbstractValidator extends BaseElementCheck implements ElementValidator {

   private static final long serialVersionUID = 1L;
   private String elementFieldPath;

   public void setElementId(String elementFieldPath) {
      this.elementFieldPath = elementFieldPath;
   }

   @SuppressWarnings("unchecked")
   protected <E extends FormDataElement> E getElement() {
      return (E) getElementById(this.elementFieldPath);
   }

   @SuppressWarnings("unchecked")
   protected <E> E getValue() {
      FormDataElement element = getElement();
      if (element != null) {
         return (E) element.getRawValue();
      }
      return null;
   }
}
