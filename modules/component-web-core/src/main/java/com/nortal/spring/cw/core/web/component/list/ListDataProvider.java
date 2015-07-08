package com.nortal.spring.cw.core.web.component.list;

import java.util.List;

/**
 * @author Margus Hanni
 * @since 26.02.2013
 */
public interface ListDataProvider<T> {

   List<T> loadData();

   long getSize();

   void prepare(ListComponent listComponent);

   List<T> getData();

}
