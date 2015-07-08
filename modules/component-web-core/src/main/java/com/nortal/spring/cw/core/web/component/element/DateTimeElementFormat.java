package com.nortal.spring.cw.core.web.component.element;

import java.util.Date;

import com.nortal.spring.cw.core.util.date.DateObject;
import com.nortal.spring.cw.core.util.date.DateTime;
import com.nortal.spring.cw.core.util.date.SimpleDate;
import com.nortal.spring.cw.core.util.date.Time;

/**
 * @author Margus Hanni
 * @since 06.03.2013
 */
public enum DateTimeElementFormat {

   // @formatter:off
	DATE {
	  public DateObject getFormattedDateObject(Date date) {
	     return new SimpleDate(date);
	  }
	},
	TIME {
	  public DateObject getFormattedDateObject(Date date) {
	     return new Time(date); 
	  }
	},
	DATETIME {
	   public DateObject getFormattedDateObject(Date date) {
	      return new DateTime(date);
	   }
	};
	// @formatter:on

   public abstract DateObject getFormattedDateObject(Date date);
}
