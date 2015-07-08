package com.nortal.spring.cw.core.web.component.page;

import java.util.ArrayList;
import java.util.List;

import com.nortal.spring.cw.core.web.component.BaseElement;
import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;

import lombok.Getter;

/**
 * Lehe üldised nupud
 * 
 * @author Margus Hanni
 */
public class PageActions extends BaseElement implements ElementPath {

   private static final long serialVersionUID = 1L;
   @Getter
   private final PageButtons main = new PageButtons("main", this);
   @Getter
   private final PageButtons secondary = new PageButtons("secondary", this);

   private final static String COMPONENT_NAME = "pageActions";

   public PageActions(ComplexComponent parent) {
      super(COMPONENT_NAME);
      setParent(parent);
      setParentElementPath(getParent());
   }

   public EventElement addMainButton(EventElement eventElement) {
      if (eventElement.getParent() == null) {
         eventElement.setParent(getParent());
      }
      if (eventElement.getParentElementPath() == null) {
         eventElement.setParentElementPath(this);
      }
      main.add((FormElement) eventElement);
      return eventElement;
   }

   public EventElement addSecondaryButton(EventElement eventElement) {
      if (eventElement.getParent() == null) {
         eventElement.setParent(getParent());
      }
      secondary.add((FormElement) eventElement);
      return eventElement;
   }

   public List<EventElement> getMainButtons() {
      List<EventElement> elements = new ArrayList<>(main.getElementHolder().values().size());
      for (FormElementHolderMapItem holder : main.getElementHolder().values()) {
         elements.add(holder.getElement());
      }

      return elements;
   }

   public List<EventElement> getSecondaryButtons() {
      List<EventElement> elements = new ArrayList<>(secondary.getElementHolder().values().size());
      for (FormElementHolderMapItem holder : secondary.getElementHolder().values()) {
         elements.add(holder.getElement());
      }

      return elements;
   }

   public boolean isHasVisibleButtons() {
      for (FormElementHolderMapItem holder : main.getElementHolder().values()) {
         if (holder.getElement().isVisible()) {
            return true;
         }
      }
      for (FormElementHolderMapItem holder : secondary.getElementHolder().values()) {
         if (holder.getElement().isVisible()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isVisible() {
      return super.isVisible() && isHasVisibleButtons();
   }
}
