package com.nortal.spring.cw.core.web.component.list;

import java.util.List;

/**
 * 
 * @author margush
 * 
 * @param <T>
 */
public abstract class DefaultListDataProvider<T> implements ListDataProvider<T> {

   private List<T> dataRows;

   @Override
   public void prepare(ListComponent listComponent) {
      // tühistame siin vana seisu, et meetod getData loeks sisse uue seisu.
      // Vastasel juhul toimub topelt laadimine
      dataRows = null;
   }

   @Override
   public long getSize() {
      return getData() == null ? 0 : getData().size();
   }

   @Override
   public List<T> getData() {
      if (dataRows == null) {
         dataRows = this.loadData();
      }
      return dataRows;
   }

}
