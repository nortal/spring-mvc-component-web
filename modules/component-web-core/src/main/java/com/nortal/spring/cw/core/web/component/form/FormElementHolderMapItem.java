package com.nortal.spring.cw.core.web.component.form;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.ElementPathMap;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * Klassi kasutatakse vormi elemendi hoidmiseks tekitades juurde võimaluse määrata elemendi leidmiseks raja implementeerides
 * {@link ElementPathMap}. Elemendi välja kuvamiseks esitluskihis peab {@link FormElementHolderMapItem} omavas klassis eksiteerima meetod
 * getElementHolder, mis tagastab mapina erinevad vormi elemendid
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 22.03.2014
 */
public class FormElementHolderMapItem implements ElementPathMap {

   private static final long serialVersionUID = 1L;
   private final FormElement element;
   private ElementPath parentElementPath;

   public FormElementHolderMapItem(FormElement element, ElementPath parentElementPath) {
      this.element = element;
      this.element.setParentElementPath(this);
      this.parentElementPath = parentElementPath;
   }

   public FormElement getElement() {
      return element;
   }

   @Override
   public String getPath() {
      return "elementHolder";
   }

   @Override
   public String getPathKey() {
      return element.getId();
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