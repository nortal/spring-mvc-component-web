package com.nortal.spring.cw.core.util;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 28.10.2013
 */
public final class RichTextUtil {

   private RichTextUtil() {
      super();
   }

   public static String parseValue(String value) {
      if (StringUtils.isNotEmpty(value)) {
         String nValue = value;
         nValue = nValue.replaceAll("\\<.*?\\>", "");
         nValue = StringEscapeUtils.unescapeHtml4(nValue);
         nValue = StringUtils.removeStart(nValue, System.getProperty("line.separator"));
         nValue = StringUtils.removeEnd(nValue, System.getProperty("line.separator"));
         return nValue;
      }
      return value;
   }
}
