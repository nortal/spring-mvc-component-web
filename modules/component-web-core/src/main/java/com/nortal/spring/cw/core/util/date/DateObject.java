package com.nortal.spring.cw.core.util.date;

import java.util.Date;

/**
 * 
 * @author Margus Hanni
 * 
 */
public interface DateObject extends Comparable<Date> {

   String getAsText();

   boolean before(Date when);

   boolean after(Date paramDate);

   long getTime();

   int compareTo(DateObject dateObject);

   String getPattern();
}
