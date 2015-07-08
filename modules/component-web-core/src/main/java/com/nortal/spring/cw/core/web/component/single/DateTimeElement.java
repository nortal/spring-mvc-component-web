package com.nortal.spring.cw.core.web.component.single;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.Range;

import com.nortal.spring.cw.core.util.date.DateObject;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.element.DateTimeElementFormat;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValue;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueSupport;

/**
 * @author Margus Hanni
 */
public class DateTimeElement extends AbstractBaseElement<DateObject> implements MultiValueSupport {
   private static final long serialVersionUID = 1L;

   private DateTimeElementFormat format;
   private Range<Long> range;
   private MultiValue multiValue;

   {
      super.addValidator(new AbstractValidator() {
         private static final long serialVersionUID = 1L;

         @Override
         public void validate() {
            if (isEditable() && range != null && getValue() != null && !range.contains(((Date) getValue()).getTime())) {
               super.getElement().addElementErrorMessage("field.datetime.error.not-in-range");
            }
         }
      });
   }

   public DateTimeElement() {
      setCssClass(FieldElementCssClass.DATE);
      format = DateTimeElementFormat.DATETIME;
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.DATETIME;
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

      return ((DateObject) getRawValue()).compareTo((DateObject) oc.getRawValue());
   }

   public DateTimeElementFormat getFormat() {
      return format;
   }

   public DateTimeElement setFormat(DateTimeElementFormat format) {
      this.format = format;
      return this;
   }

   @Override
   public String getDisplayValue() {
      if (getValue() == null) {
         return null;
      }

      return ((DateObject) getValue()).getAsText();
   }

   public Range<Long> getRange() {
      return range;
   }

   public void setRange(Range<Long> range) {
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

   @Override
   public void setRawValue(Object value) {
      if (value == null) {
         setValue(null);
         return;
      }

      DateObject val = format != null ? format.getFormattedDateObject((Date) value) : (DateObject) value;

      super.setValue(val);
   }
}
