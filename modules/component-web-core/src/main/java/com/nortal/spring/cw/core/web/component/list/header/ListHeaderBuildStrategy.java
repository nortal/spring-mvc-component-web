package com.nortal.spring.cw.core.web.component.list.header;

import java.io.Serializable;

/**
 * 
 * @author margush
 * 
 */
public interface ListHeaderBuildStrategy extends Serializable {

   public static final String HEADER_PATH = "headers[%d].%s";

   String getElementPath(int cellNumber);

   String getElementBetweenStartPath(int cellNumber);

   String getElementBetweenEndPath(int cellNumber);
}
