package com.nortal.spring.cw.core.web.component.list.row;

import com.nortal.spring.cw.core.web.component.list.EpmListComponent;

/**
 * 
 * @author Alrik Peets
 */
public interface ListRowBuilder<T> {

   ListRow buildRow(Object entity, EpmListComponent<T> list, ListElementBuildStrategy nameStrategy, int rowNumber);

   ListRow refreshRow(ListElementBuildStrategy buildStrategy, ListRow listRow, int rowNumber);

}