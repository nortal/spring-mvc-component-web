package com.nortal.spring.cw.core.web.component.element;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.form.EpmFormComponent;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.component.list.row.RowCell;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Põhiklass, mida kasutatakse erinevate elemendi põhiste validaatorite või kitsenduste loomisel.
 * 
 * @author Margus Hanni
 * @since 02.12.2013
 */
public class BaseElementCheck {

   @Autowired
   private CwMessageSource messageSource;

   private Component parent;

   /**
    * Meetod tagastab elementi omava komponendi
    * 
    * @return {@link Component}
    */
   public Hierarchical getParent() {
      return parent;
   }

   /**
    * Elementi omava komponendi määramine
    * 
    * @param parent
    *           {@link Component}
    */
   public void setParent(Component parent) {
      this.parent = parent;
   }

   protected FormElement getElementById(String elementId) {
      switch (parent.getComponentType()) {
         case FORM:
            return ((EpmFormComponent<?>) parent).getElement(elementId);
         case LIST:
            ListComponent listComponent = (ListComponent) parent;
            ListRow listRow = listComponent.getActiveListRow();
            if (listRow == null) {
               // leiame target elemendi ülemobjekti ja kasutame seda
               String targetComponentDisplayId = RequestUtil.getUserRequestInfo().getRequestParameter(RequestUtil.PARAM_EPM_EVT_TARGET);
               if (StringUtils.isNotEmpty(targetComponentDisplayId)) {
                  String targetComponent = StringUtils.substringBeforeLast(ElementUtil.convertDisplayIdToPath(targetComponentDisplayId),
                        BeanUtil.NESTED_DELIM);
                  listRow = (ListRow) ((RowCell) BeanUtil.getValueByPath(RequestUtil.getControllerComponent(), targetComponent))
                        .getParentElementPath();
               }
            }

            if (listRow == null) {
               throw new AppBaseRuntimeException("[ListRow] must not be null ");
            }

            return listRow.getElementsGroup().getById(elementId).getElement();
         default:
            throw new AppBaseRuntimeException("Parent component type is not supported: " + parent.getComponentType());
      }
   }

   /**
    * By default it checks that dependable element is not empty. If it is not empty then current element can not be empty also.
    * 
    * @param dependableElement
    * @return boolean
    */
   protected boolean isMandatoryCondition(FormElement dependableElement) {
      return !FieldElementUtil.isFieldEmpty((FormDataElement) dependableElement);
   }

   protected CwMessageSource getMessageSource() {
      return messageSource;
   }
}
