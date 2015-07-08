package com.nortal.spring.cw.core.web.component.single;

import java.text.NumberFormat;

import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;

/**
 * @author Margus Hanni
 * @since 11.03.2013
 */
@SuppressWarnings("serial")
public class IntegerElement extends AbstractNumberElement<Integer> {

   public IntegerElement() {
      setCssClass(FieldElementCssClass.TEXT_SMALL);
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.INTEGER;
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
