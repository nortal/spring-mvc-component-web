package com.nortal.spring.cw.core.web.component.list.row;

import java.util.List;

import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;

/**
 * Kasutatakse editable listi ADD vormi rea loomiseks
 * 
 * @author Alrik Peets
 */
public class NewListElementBuildStrategy extends BaseListElementBuildStrategy {
   private static final long serialVersionUID = 1L;

   @Override
   public void modifyElement(Object baseEntity, FormElement listRowElement) {
      if (listRowElement instanceof FormDataElement) {
         FormDataElement element = (FormDataElement) listRowElement;
         element.setEntity(baseEntity);
      }
   }

   @Override
   public List<FormElement> getCloneableListElements(EpmListComponent list) {
      return list.getNewDefaultFormElements().getList();
   }
}