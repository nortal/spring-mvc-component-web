package com.nortal.spring.cw.core.web.component.form;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * Klassi kasutatakse vormi elemendi hoidmiseks tekitades juurde võimaluse määrata elemendi leidmiseks raja implementeerides
 * {@link ElementPath}. Elemendi välja kuvamiseks esitluskihis peab {@link FormElementPathItem} omavas klassis eksiteerima meetod
 * konstruktoris määratud (pahtToElement), mis tagastab {@link FormElementPathItem}
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 09.04.2014
 */
public class FormElementPathItem implements ElementPath {

   private static final long serialVersionUID = 1L;
   private final FormElement element;
   private final String pahtToElement;
   private ElementPath parentElementPath;

   public FormElementPathItem(String pahtToElement, FormElement element, ElementPath parentElementPath) {
      this.pahtToElement = pahtToElement;
      this.element = element;
      this.element.setParentElementPath(this);
      this.parentElementPath = parentElementPath;
   }

   public FormElement getElement() {
      return element;
   }

   @Override
   public String getPath() {
      return pahtToElement;
   }

   @Override
   public ElementPath getParentElementPath() {
      return this.parentElementPath;
   }

   @Override
   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;

   }
}
