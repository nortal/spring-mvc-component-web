package com.nortal.spring.cw.core.web.component.element;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;

/**
 * Abi funktsioonid välja väärtuste kontrollimiseks
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 24.10.2013
 */
public final class FieldElementUtil {

   private FieldElementUtil() {
      super();
   }

   public static boolean isFieldEmpty(FormDataElement element) {
      Object value = element == null ? null : element.getRawValue();
      return value == null || value instanceof String && StringUtils.isEmpty((String) value) || value instanceof Collection
            && CollectionUtils.isEmpty((Collection<?>) value);
   }

   /**
    * List element value can be found from active {@Link ListRow}. Form element has new value directly.
    * 
    * @param element
    * @return {@Link FormElement}
    */
   public static FormElement findActiveListRowElementByElement(FormElement element) {
      Hierarchical parent = element.getParent();
      Assert.isTrue(parent != null, "Elemendil ei ole parent komponenti");
      Assert.isTrue(element != null, "Element ei tohi olla null");

      if (parent instanceof EpmListComponent) {
         ListComponent listComponent = (ListComponent) parent;
         ListRow listRow = listComponent.getActiveListRow();

         if (listRow == null) {
            return element;
         }

         return listRow.getElementsGroup().getById(element.getId()).getElement();
      }
      return element;
   }

}