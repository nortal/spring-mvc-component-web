package com.nortal.spring.cw.core.web.component.list.header;

import java.io.Serializable;

/**
 * 
 * @author margush
 * 
 */
public interface ListHeaderBuilder extends Serializable {

   void init(ListHeaderBuildStrategy nameStrategy, ListHeader header, int cellNumber);

}
