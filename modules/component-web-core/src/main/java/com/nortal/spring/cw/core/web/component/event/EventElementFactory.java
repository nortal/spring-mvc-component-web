package com.nortal.spring.cw.core.web.component.event;

import com.nortal.spring.cw.core.web.component.css.ButtonElementCssClass;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.global.ElementAction;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;

/**
 * Factory to create event elements
 * 
 * @author Margus Hanni
 * 
 */
public class EventElementFactory {

   public enum Type {
      //@formatter:off
      PREVIEW(ElementAction.BUTTON_PREVIEW_EVENT, GlobalLabel.BUTTON_PREVIEW),
      SAVE(ElementAction.BUTTON_PAGE_SAVE_EVENT, GlobalLabel.BUTTON_SAVE),
      ADD_NEW(ElementAction.LINK_ADD_NEW_EVENT, GlobalLabel.LIST_LINK_ADD_NEW),
      SHOW_PDF(ElementAction.EVENT_PAGE_SHOW_PDF, GlobalLabel.BUTTON_SHOW_PDF),
      SAVE_NEXT(ElementAction.BUTTON_PAGE_SAVE_NEXT_EVENT, GlobalLabel.BUTTON_SAVE_NEXT), 
      NEXT(ElementAction.BUTTON_PAGE_NEXT_EVENT, GlobalLabel.BUTTON_NEXT), 
      COPY(ElementAction.LINK_COPY_EVENT, GlobalLabel.LIST_BUTTON_COPY), 
      PUBLISH(ElementAction.BUTTON_PAGE_PUBLISH, GlobalLabel.BUTTON_PUBLISH), 
      SAVE_PUBLISH(ElementAction.BUTTON_PAGE_PUBLISH, GlobalLabel.BUTTON_SAVE_PUBLISH), 
      DELETE(ElementAction.BUTTON_DELETE_EVENT, GlobalLabel.BUTTON_DELETE), 
      CHANGE(ElementAction.BUTTON_CHANGE_EVENT, GlobalLabel.BUTTON_CHANGE), 
      LINK_BACK(ElementAction.LINK_PAGE_BACK_EVENT, GlobalLabel.LINK_BACK), 
      LINK_CANCEL(ElementAction.LINK_PAGE_CANCEL_EVENT, GlobalLabel.LINK_BACK);
      //@formatter:on

      private String eventName;
      private String label;

      private Type(String eventName, String label) {
         this.eventName = eventName;
         this.label = label;
      }

      public String getEventName() {
         return eventName;
      }

      public String getLabel() {
         return label;
      }
   };

   public static final LinkElement createLink(Type type) {
      return (LinkElement) createElement(type, null, null, false);
   }

   public static final LinkElement createLink(Type type, FieldElementCssClass cssClass) {
      return (LinkElement) createElement(type, null, cssClass, false);
   }

   public static final ButtonElement createButton(Type type) {
      return (ButtonElement) createElement(type, null, null, true);
   }

   public static final ButtonElement createButton(Type type, ButtonElementCssClass cssClass) {
      return (ButtonElement) createElement(type, cssClass, null, true);
   }

   private static final EventElement createElement(Type type, ButtonElementCssClass buttonCssClass, FieldElementCssClass linkCssClass,
         boolean button) {
      if (button) {
         if (buttonCssClass == null) {
            return new ButtonElement(type.getEventName(), type.getLabel());
         } else {
            return new ButtonElement(type.getEventName(), type.getLabel()).setButtonCssClass(buttonCssClass);
         }
      }

      if (linkCssClass == null) {
         return new LinkElement(type.getEventName(), type.getLabel());
      } else {
         LinkElement element = new LinkElement(type.getEventName(), type.getLabel());
         element.setCssClass(linkCssClass);
         return element;
      }
   }
}
