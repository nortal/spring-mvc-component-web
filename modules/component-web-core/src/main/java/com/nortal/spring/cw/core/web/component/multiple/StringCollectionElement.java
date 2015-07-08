package com.nortal.spring.cw.core.web.component.multiple;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.single.values.MultiValue;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueSupport;

/**
 * @author Margus Hanni
 * @since 18.03.2013
 */
public class StringCollectionElement extends AbstractBaseElement<Collection<String>> implements MultiValueSupport {
   private static final long serialVersionUID = 1L;
   private MultiValue multiValue;

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.STRING_COLLECTION;
   }

   @Override
   public String getDisplayValue() {
      if (getValue() == null) {
         return StringUtils.EMPTY;
      }
      return StringUtils.join(getValue().toArray(new String[getValue().size()]), ", ");
   }

   @Override
   public int compareTo(Object o) {
      return 0;
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
   public String getCssClassValue() {
      String result = super.getCssClassValue();
      if (isMultiValueElement()) {
         result += getMultiValue().getCssClass();
      }
      return result;
   }

   @Override
   public Map<Object, MultiValueElement> getMultiValues() {
      return isMultiValueElement() ? getMultiValue().getFilteredValues() : null;
   }
}
