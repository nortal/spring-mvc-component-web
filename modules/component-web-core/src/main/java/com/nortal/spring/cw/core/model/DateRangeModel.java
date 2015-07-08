package com.nortal.spring.cw.core.model;

import java.util.Date;

public interface DateRangeModel {

   Date getStartDate();

   Date getEndDate();

   void setEndDate(Date date);

   void setStartDate(Date date);
}
