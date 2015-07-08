package com.nortal.spring.cw.core.web.component.modal;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.event.ButtonElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolder;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Modaalakna erinevate nuppude hoidla
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 24.03.2014
 */
public class ModalButtons implements ElementPath {

   private static final long serialVersionUID = 1L;
   private ElementPath parentElementPath;
   private final FormElementHolder elementHolder = new FormElementHolder();

   public ModalButtons(ModalDialogComponent parent) {
      this.parentElementPath = parent;
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {
      return elementHolder.getElementHolder();
   }

   @Override
   public String getPath() {
      return ElementUtil.getNameForFullPath(this);
   }

   @Override
   public ElementPath getParentElementPath() {
      return parentElementPath;
   }

   @Override
   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;
   }

   /**
    * Helper function for JSPs - returns the name of the first (and usually only) button
    * 
    * @return
    */
   public String getFirstButtonName() {
      return elementHolder.getElements().isEmpty() ? "" : CollectionUtils.get(elementHolder.getElements().values(), 0).getLabel();
   }

   /**
    * 
    * @param buttonEvent
    *           {@link ButtonElement}
    * @return
    */
   public ButtonElement addModalButton(ButtonElement buttonEvent) {
      if (buttonEvent.getParent() == null) {
         buttonEvent.setParent((Component) this.parentElementPath);
      }
      this.elementHolder.put(buttonEvent, this);
      return buttonEvent;
   }

   public Collection<FormElementHolderMapItem> getButtons() {
      return this.elementHolder.getElementHolder().values();
   }

   public void clearModalButtons() {
      this.elementHolder.clear();
   }

   /**
    * @param buttonEvent
    *           A ModalComponent eventName
    * @param buttonLabel
    *           Label code for button
    * @return
    */
   public ButtonElement addModalButton(String buttonEvent, String buttonLabel) {
      return addModalButton(new ButtonElement(String.valueOf(elementHolder.getElements().size()), buttonEvent, buttonLabel));
   }

}
