package com.nortal.spring.cw.core.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Margus Hanni
 * 
 */
public class Time extends AbstractBaseDateObject {
   private static final long serialVersionUID = 1L;
   public static final String PATTERN = "HH:mm";

   public Time(Date date) {
      super(date);
   }

   public Time(String text) throws ParseException {
      super(new SimpleDateFormat(PATTERN).parse(text));
   }

   public static String getAsText(Date value) {
      return new Time(value).getAsText();
   }

   @Override
   public String getPattern() {
      return PATTERN;
   }
}
