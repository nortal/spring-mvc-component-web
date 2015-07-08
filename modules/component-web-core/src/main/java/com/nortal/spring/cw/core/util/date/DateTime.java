package com.nortal.spring.cw.core.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * @author Margus Hanni
 * 
 */
public class DateTime extends AbstractBaseDateObject {

   private static final long serialVersionUID = 1L;
   private static final String PATTERN = "dd.MM.yyyy HH:mm";

   public DateTime(Date date) {
      super(date);
   }

   public DateTime(String text) throws ParseException {
      super(new SimpleDateFormat(PATTERN).parse(text));
   }

   public static String getAsText(Date value) {
      return new DateTime(value).getAsText();
   }

   @Override
   public String getPattern() {
      return PATTERN;
   }
}
