package com.nortal.spring.cw.core.util;

import java.util.Calendar;
import java.util.Date;

import org.joda.time.Interval;

import com.nortal.spring.cw.core.model.DateRangeModel;

/**
 * {@link DateRangeModel) liidesega seotud utiliit-funktsioonid
 * 
 * @author Alrik Peets
 */
public final class DateRangeModelUtil {

   /**
    * Märgib {@link DateRangeModel} klassi suletuks. Lõpp-ajaks saab hetkeaeg - 1 sekund.
    * 
    * @param drm
    */
   public static void close(DateRangeModel drm) {
      // TODO Lauri Lättemäe: some utility method in DateUtil would be nice
      Calendar cal = Calendar.getInstance();
      cal.setTime(new Date());
      cal.add(Calendar.SECOND, -1);
      drm.setEndDate(cal.getTime());
   }

   /**
    * Kontrollib kas {@link DateRangeModel} on aktiivne käesolevas hetkes. Aktiivsuse tunnuseks on, et hetke-aeg on pärast algus- ning enne
    * lõpp-kuupäeva. Kui lõpp-kuupäev on NULL, siis on see hilisem igast ajahetkest.
    * 
    * @param drm
    * @return
    */
   public static boolean isActive(DateRangeModel drm) {
      return new Interval(drm.getStartDate().getTime(), drm.getEndDate().getTime()).contains(new Date().getTime());
   }
}
