package com.nortal.spring.cw.core.web.component.single;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValue;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueSupport;

/**
 * @author Margus Hanni
 */
public class StringElement extends AbstractBaseElement<String> implements MultiValueSupport {

   private static final long serialVersionUID = 1L;

   private int length = Integer.MAX_VALUE;

   private int rows;

   private int cols;

   private MultiValue multiValue;

   {
      super.addValidator(new AbstractValidator() {

         private static final long serialVersionUID = 1L;

         @Override
         public void validate() {
            Object value = super.getValue();
            if (getLength() > 0 && value != null && StringUtils.length(value.toString()) > StringElement.this.getLength()) {
               super.getElement().addElementErrorMessage("field.text.error.to-long");
            }
         }
      });
   }

   public StringElement() {
      setCssClass(FieldElementCssClass.TEXT);
   }

   public int getLength() {
      return length;
   }

   public StringElement setLength(int length) {
      this.length = length;
      return this;
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.STRING;
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

      return getDisplayValue().compareTo(oc.getDisplayValue());
   }

   public int getRows() {
      return rows;
   }

   public int getCols() {
      return cols;
   }

   public StringElement setRows(int rows) {
      this.rows = rows;
      return this;
   }

   public StringElement setCols(int cols) {
      this.cols = cols;
      return this;
   }

   @Override
   public String getDisplayValue() {
      if (isMultiValueElement()) {
         return getMultiValue().getValue(getValue());
      }
      return getValue();
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

   public StringElement initRichText() {
      setCssClass(FieldElementCssClass.MCE_RICH_TEXT);
      setEscapeXml(false);
      return this;
   }

   @Override
   public Map<Object, MultiValueElement> getMultiValues() {
      return isMultiValueElement() ? getMultiValue().getFilteredValues() : null;
   }

   @Override
   public void setValue(String value) {
      super.setValue(StringUtils.trim(value));
   }

   @Override
   public String getValue() {
      // Ära seda meetodit ära kustuta, Spring vajab seda
      return super.getValue();
   }
}
