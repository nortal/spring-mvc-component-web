package com.nortal.spring.cw.core.web.component.single;

import org.apache.commons.lang3.BooleanUtils;

import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;

/**
 * @author Margus Hanni
 * @since 20.02.2013
 */
public class BooleanElement extends AbstractBaseElement<Boolean> {

   private static final long serialVersionUID = 1L;

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.BOOLEAN;
   }

   @Override
   public int compareTo(Object o) {

      if (!(o instanceof FormDataElement)) {
         return 0;
      }

      FormDataElement oc = (FormDataElement) o;

      if (getValue() == null && oc.getRawValue() == null) {
         return 0;
      }

      if (getValue() == null && oc.getRawValue() != null) {
         return 1;
      }

      if (getValue() != null && oc.getRawValue() == null) {
         return -1;
      }

      return getValue().compareTo((Boolean) oc.getRawValue());
   }

   @Override
   public String getDisplayValue() {
      return BooleanUtils.toString(getValue(), "*", "", "");
   }

   /**
    * Tegemist on checkbox väljaga, elemendile on määratud alati klassiks {@link FieldElementCssClass#CHECK}, täiendavalt saab lisada juurde
    * vajamineva CSS klassi, mis päritakse elemendi superklassist {@link AbstractBaseElement#getCssClassValue()}
    */
   @Override
   public String getCssClassValue() {
      return FieldElementCssClass.CHECK.getValue() + " " + super.getCssClassValue();
   }

   @Override
   public FieldElementCssClass getCssClass() {
      return !isEditable() && super.getCssClass() == null && BooleanUtils.isTrue(getValue()) ? FieldElementCssClass.CHECKED : super
            .getCssClass();
   }
}
