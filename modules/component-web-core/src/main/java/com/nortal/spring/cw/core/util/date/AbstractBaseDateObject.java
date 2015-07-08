package com.nortal.spring.cw.core.util.date;

import java.util.Date;

import org.joda.time.format.DateTimeFormat;

/**
 * 
 * @author Margus Hanni
 * 
 */
public abstract class AbstractBaseDateObject extends Date implements DateObject {

   private static final long serialVersionUID = 1L;

   public AbstractBaseDateObject(Date date) {
      super(date.getTime());
   }

   @Override
   public String getAsText() {
      return new org.joda.time.DateTime(this).toString(DateTimeFormat.forPattern(getPattern()));
   }

   @Override
   public int compareTo(DateObject dateObject) {
      return super.compareTo((Date) dateObject);
   }
}
