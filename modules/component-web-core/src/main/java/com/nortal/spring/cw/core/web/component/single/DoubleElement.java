package com.nortal.spring.cw.core.web.component.single;

import java.math.BigDecimal;
import java.text.NumberFormat;

import com.nortal.spring.cw.core.util.number.BigDecimalUtil;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;

/**
 * Tegemist on elemendiga, mis hoiab enda sees {@link BigDecimal} tüüpi väärtust. Vaikeväärtusena on elemendi CSS klassiks
 * {@link FieldElementCssClass#TEXT_SMALL}. Täiendavalt on väimalik määrata, kas väljakuvatav väärtus on valuuta tüüpi.
 * 
 * @author Margus Hanni
 */
public class DoubleElement extends AbstractNumberElement<BigDecimal> {

   private static final long serialVersionUID = 1L;

   private boolean currencyType;

   public DoubleElement() {
      setCssClass(FieldElementCssClass.TEXT_SMALL);
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.DOUBLE;
   }

   /**
    * Tagastab BigDecimali täpsusega vähemalt 2 kui tegu on rahaga. Vastasel juhul täpsusega vähemalt 0.
    * 
    * @return
    */
   @Override
   public BigDecimal getValue() {
      return BigDecimalUtil.removeTrailingZeroes(super.getValue(), currencyType);
   }

   @Override
   public void setValue(BigDecimal value) {
      super.setValue(value);
   }

   @Override
   public String getDisplayValue() {
      if (getValue() == null) {
         return null;
      }

      if (this.currencyType) {
         return BigDecimalUtil.convertBigDecimalToCurrencyString(getValue());
      }

      return NumberFormat.getNumberInstance().format(getValue());
   }

   /**
    * Kas tegemist on valuuta väljaga, kuvatakse summa koos valuuta tähisega.
    * 
    * @param isCurrencyType
    * @return
    */
   public void setCurrencyType(boolean currencyType) {
      this.currencyType = currencyType;
   }

   public boolean isCurrencyType() {
      return currencyType;
   }
}
