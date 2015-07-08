package com.nortal.spring.cw.core.util.number;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @created 08.10.2013
 */
public class BigDecimalUtil {

   /**
    * Finds maximum value from array of decimals. This method is null safe.
    * 
    * @param values
    *           {@Link BigDecimal[]}
    * @return
    */
   public static BigDecimal max(BigDecimal... values) {
      BigDecimal maxValue = null;
      if (values != null) {
         for (BigDecimal value : values) {
            if (maxValue == null || value != null && value.compareTo(maxValue) >= 0) {
               maxValue = value;
            }
         }
      }
      return maxValue;
   }

   /**
    * If start is null, value has to less than end.<br>
    * If end is null, value has to be greater than start.<br>
    * If start and end are null, <code>true</code> is returned.<br>
    * 
    * @param value
    *           {@link BigDecimal}
    * @param start
    *           {@link BigDecimal}
    * @param end
    *           {@link BigDecimal}
    * @throws IllegalArgumentException
    *            if value is null
    * @return {@link Boolean}
    */
   public static boolean between(BigDecimal value, BigDecimal start, BigDecimal end) {
      Assert.isTrue(value != null, "BigDecimal value is null");
      if (start == null && end == null) {
         return true;
      }
      return (start == null || (start.compareTo(value) <= 0)) && (end == null || (end.compareTo(value) >= 0));
   }

   /**
    * If value is less than end and greater than start, <code>true</code> is returned.<br>
    * If start or end are null, <code>false</code> is returned.<br>
    * 
    * @param value
    *           {@link BigDecimal}
    * @param start
    *           {@link BigDecimal}
    * @param end
    *           {@link BigDecimal}
    * @throws IllegalArgumentException
    *            if value is null
    * @return {@link Boolean}
    */
   public static boolean betweenWithStartEndDefined(BigDecimal value, BigDecimal start, BigDecimal end) {
      Assert.isTrue(value != null, "BigDecimal value is null");
      if (start == null || end == null) {
         return false;
      }
      return (start == null || (start.compareTo(value) <= 0)) && (end == null || (end.compareTo(value) >= 0));
   }

   /**
    * Convert BigDecimal to currency or quantity. Currency has always at least 2 fractions and quantity 0 or more. Maximum precision allowed
    * is 6 decimal places (numbers after decimal point)
    * 
    * @param value
    * @param isCurrency
    * @return value, with trailing zeroes removed
    */
   public static BigDecimal removeTrailingZeroes(BigDecimal value, boolean isCurrency) {
      if (value == null) {
         return null;
      }
      DecimalFormat df;
      if (isCurrency) {
         df = new DecimalFormat("0.00####");
      } else {
         df = new DecimalFormat("0.######");
      }
      String format = df.format(value);
      return new BigDecimal(format.replace(",", "."));
   }

   /**
    * Adds currency symbol to the value and removes trailing zeroes.
    * 
    * @param value
    * @return
    */
   public static String convertBigDecimalToCurrencyString(BigDecimal value) {
      if (value == null) {
         return StringUtils.EMPTY;
      }

      // currency formatter works different on different computers
      return removeTrailingZeroes(value, true) + " €";
   }

}