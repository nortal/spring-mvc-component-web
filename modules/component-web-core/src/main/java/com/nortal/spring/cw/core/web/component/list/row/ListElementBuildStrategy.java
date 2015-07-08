package com.nortal.spring.cw.core.web.component.list.row;

import java.io.Serializable;
import java.util.List;

import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;

public interface ListElementBuildStrategy extends Serializable {

   void modifyElement(Object baseEntity, FormElement listRowElement);

   List<FormElement> getCloneableListElements(EpmListComponent list);

}