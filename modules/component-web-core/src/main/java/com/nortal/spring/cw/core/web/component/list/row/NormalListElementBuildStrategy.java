package com.nortal.spring.cw.core.web.component.list.row;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;

/**
 * Kasutatakse tavalise listi rea loomiseks.
 * 
 * @author Alrik Peets
 */
public class NormalListElementBuildStrategy extends BaseListElementBuildStrategy {
   private static final long serialVersionUID = 1L;

   @Override
   public void modifyElement(Object baseEntity, FormElement listRowElement) {
      if (StringUtils.isNotEmpty(listRowElement.getId())) {
         if (listRowElement instanceof FormDataElement) {
            ((FormDataElement) listRowElement).setEntity(baseEntity);
         }
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public List<FormElement> getCloneableListElements(EpmListComponent list) {
      return list.getFormElements();
   }

}