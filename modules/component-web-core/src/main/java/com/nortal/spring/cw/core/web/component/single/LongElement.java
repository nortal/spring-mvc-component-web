package com.nortal.spring.cw.core.web.component.single;

import java.text.NumberFormat;

import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;

/**
 * @author Margus Hanni
 */
public class LongElement extends AbstractNumberElement<Long> {

   private static final long serialVersionUID = 1L;

   public LongElement() {
      setCssClass(FieldElementCssClass.TEXT_SMALL);
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.LONG;
   }

   @Override
   public String getDisplayValue() {
      if (isMultiValueElement()) {
         return getMultiValue().getValue(getValue());
      }
      if (getValue() == null) {
         return null;
      }
      return NumberFormat.getIntegerInstance().format(getValue());
   }

}
