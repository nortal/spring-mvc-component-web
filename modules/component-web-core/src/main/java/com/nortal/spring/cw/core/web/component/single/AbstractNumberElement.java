package com.nortal.spring.cw.core.web.component.single;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.Range;

import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValue;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueSupport;

/**
 * @author Margus Hanni
 * @since 11.03.2013
 */
@SuppressWarnings("serial")
public abstract class AbstractNumberElement<T> extends AbstractBaseElement<T> implements MultiValueSupport {

   private Range<T> range;
   private MultiValue multiValue;

   {
      super.addValidator(new AbstractValidator() {
         @Override
         public void validate() {
            if (range != null && getValue() != null && !range.contains(getValue())) {
               super.getElement().addElementErrorMessage("field.number.error.not-in-range", range.getMinimum(), range.getMaximum());
            }
         }
      });
   }

   @Override
   public int compareTo(Object o) {

      if (!(o instanceof FormDataElement)) {
         return 0;
      }

      FormDataElement co = (FormDataElement) o;

      if (getValue() == null && co.getRawValue() == null) {
         return 0;
      }

      if (getValue() == null && co.getRawValue() != null) {
         return 1;
      }

      if (getValue() != null && co.getRawValue() == null) {
         return -1;
      }

      return new BigDecimal(getValue().toString()).compareTo(new BigDecimal(co.getRawValue().toString()));
   }

   public Range<T> getRange() {
      return range;
   }

   public void setRange(Range<T> range) {
      this.range = range;
   }

   @Override
   public MultiValue getMultiValue() {
      return multiValue;
   }

   @Override
   public void setMultiValue(MultiValue multiValue) {
      this.multiValue = multiValue;
   }

   @Override
   public boolean isMultiValueElement() {
      return multiValue != null;
   }

   @Override
   public Map<Object, MultiValueElement> getMultiValues() {
      return isMultiValueElement() ? getMultiValue().getFilteredValues() : null;
   }

}
