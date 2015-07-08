package com.nortal.spring.cw.core.web.component.list.header;

import java.util.Date;

import lombok.Data;

import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.nortal.spring.cw.core.util.date.Time;
import com.nortal.spring.cw.core.web.component.element.DateTimeElementFormat;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.header.ListHeader.BetweenHolder;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * @author margush
 */
@Data
public class DefaultListFilter implements ListFilter {
   private static final long serialVersionUID = 1L;

   @Override
   public boolean filter(ListHeader header, ListRow listRow, Object value) {
      FieldElementType elementType = header.getElement().getElementType();
      FormElement element = header.getElement();
      Pair<BetweenHolder, BetweenHolder> between = header.getBetween();

      if (!(element instanceof FormDataElement)) {
         return false;
      }

      FormDataElement dataElement = (FormDataElement) element;

      if (ElementUtil.isEmpty(dataElement) && ElementUtil.isEmpty((FormDataElement) between.getLeft().getElement())
            && ElementUtil.isEmpty((FormDataElement) between.getRight().getElement())) {
         return false;
      }

      if (value == null) {
         return true;
      }

      if (header.isAllowFilterRange()) {
         return checkFilterRange(value, between, elementType);
      }

      return filter(elementType, dataElement, value);
   }

   private boolean filter(FieldElementType elementType, FormDataElement dataElement, Object value) {

      switch (elementType) {
         case STRING:
            Object rawValue = dataElement.getRawValue();

            if (StringUtils.isEmpty((String) rawValue)) {
               return false;
            }

            if (StringUtils.isEmpty((String) value)) {
               return true;
            }

            return !StringUtils.containsIgnoreCase((String) value, (String) rawValue);
         case BOOLEAN:
            rawValue = dataElement.getRawValue();
            return (Boolean) rawValue != ((Boolean) value);
         default:
            break;
      }

      return false;
   }

   private boolean checkFilterRange(Object value, Pair<BetweenHolder, BetweenHolder> between, FieldElementType elementType) {
      if (elementType == FieldElementType.DOUBLE || elementType == FieldElementType.INTEGER || elementType == FieldElementType.LONG) {
         return filter((Number) value, (Number) ((FormDataElement) between.getLeft().getElement()).getRawValue(),
               (Number) ((FormDataElement) between.getRight().getElement()).getRawValue());
      }

      if (elementType == FieldElementType.DATETIME) {
         DateTimeElement fst = (DateTimeElement) between.getLeft().getElement();
         DateTimeElement snd = (DateTimeElement) between.getRight().getElement();
         Date begin = (Date) fst.getRawValue();
         Date end = (Date) snd.getRawValue();

         // tegemist on kellaaja väljaga siis ei filtreeri kui mõlemad ajad on
         // 00:00
         if (fst.getFormat() == DateTimeElementFormat.TIME && snd.getFormat() == DateTimeElementFormat.TIME
               && Time.getAsText(begin).equals("00:00") && Time.getAsText(end).equals("00:00")) {
            return false;
         }

         Long check = ((Date) value).getTime();

         return filter(check, begin == null ? null : begin.getTime(), end == null ? null : end.getTime());
      }

      return false;
   }

   private boolean filter(Number check, Number begin, Number end) {
      Range<Double> range = Range.between(begin == null ? Double.MIN_VALUE : begin.doubleValue(),
            end == null ? Double.MAX_VALUE : end.doubleValue());
      return !range.contains(check.doubleValue());
   }

}
