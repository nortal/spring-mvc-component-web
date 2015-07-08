package com.nortal.spring.cw.jsp.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.event.ButtonElement;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;
import com.nortal.spring.cw.core.web.component.modal.BaseModalComponent;
import com.nortal.spring.cw.core.web.component.modal.ModalDialogComponent;
import com.nortal.spring.cw.core.web.component.modal.ModalType;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping(value = { "/test/test-modal" })
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestModalController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;

   private static final String POPUP_WITH_SIMPLE_CONTENT = "popup-with-simple-content";
   private static final String POPUP_WITH_JSP_CONTENT = "popup-with-jsp-content";
   private static final String POPUP_WITH_JSP_CONTENT_WITH_BUTTONS = "popup-with-jsp-content-with-buttons";

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#Modaalaknad"));
      compositeComp.setMenuComponent(new TestMenuComponent());

      compositeComp.add(getModalPopUpWithSimpleContent());
      compositeComp.add(getModalPopUpWithJspContent());
      compositeComp.add(getModalPopUpWithJspContentWithButtons());
   }

   @EventHandler(eventName = POPUP_WITH_JSP_CONTENT_WITH_BUTTONS + "-button2")
   public void popuWithJspContentWithButtons() {
      addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT_WITH_BUTTONS
            + " : valisid nupu mille küljes on annotatsioon @EventHandler");
   }

   private BaseModalComponent getModalPopUpWithSimpleContent() {
      BaseModalComponent popup = new BaseModalComponent(ModalType.POPUP, POPUP_WITH_SIMPLE_CONTENT);
      popup.getModalButtons().addModalButton(ModalDialogComponent.SUBMIT_MODAL_DIALOG_ACCEPT, GlobalLabel.BUTTON_YES);
      popup.setCaption(new ComponentCaption("#Modaalakna pealkiri"));
      popup.setSimpleContent("<p>Modaalakna sisu. Modaalakna sisu toetab ka HTMLi vormingut</p>");
      popup.setAcceptHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_SIMPLE_CONTENT + " : valisid 'jah'");
            return null;
         }
      });
      popup.setCancelHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_SIMPLE_CONTENT + " : valisid 'katkesta'");
            return null;
         }
      });
      popup.setCloseHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_SIMPLE_CONTENT + " : valisid 'sulge'");
            return null;
         }
      });
      popup.getProperties().setLinkLabel("#Ava");

      return popup;
   }

   private BaseModalComponent getModalPopUpWithJspContent() {
      BaseModalComponent popup = new BaseModalComponent(ModalType.POPUP, POPUP_WITH_JSP_CONTENT);
      popup.getModalButtons().addModalButton(ModalDialogComponent.SUBMIT_MODAL_DIALOG_ACCEPT, GlobalLabel.BUTTON_YES);
      popup.setCaption(new ComponentCaption("#Modaalakna pealkiri"));
      popup.setAcceptHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT + " : valisid 'jah'");
            return null;
         }
      });
      popup.setCancelHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT + " : valisid 'katkesta'");
            return null;
         }
      });
      popup.setCloseHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT + " : valisid 'sulge'");
            return null;
         }
      });
      popup.getProperties().setLinkLabel("#Ava");

      return popup;
   }

   private BaseModalComponent getModalPopUpWithJspContentWithButtons() {
      BaseModalComponent popup = new BaseModalComponent(ModalType.POPUP, POPUP_WITH_JSP_CONTENT_WITH_BUTTONS);

      ButtonElement button1 = new ButtonElement("#Händleriga");
      button1.setOnClickHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT_WITH_BUTTONS
                  + " : valisid nupu mille küljes on onClickHandler");
            return null;
         }
      });

      popup.getModalButtons().addModalButton(button1);

      ButtonElement button2 = new ButtonElement(POPUP_WITH_JSP_CONTENT_WITH_BUTTONS + "-button2", "#Annotatsiooniga");
      popup.getModalButtons().addModalButton(button2);

      popup.setCaption(new ComponentCaption("#Modaalakna pealkiri"));
      popup.setAcceptHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT + " : valisid 'jah'");
            return null;
         }
      });
      popup.setCancelHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT + " : valisid 'katkesta'");
            return null;
         }
      });
      popup.setCloseHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: " + POPUP_WITH_JSP_CONTENT + " : valisid 'sulge'");
            return null;
         }
      });
      popup.getProperties().setLinkLabel("#Ava");

      return popup;
   }

}
