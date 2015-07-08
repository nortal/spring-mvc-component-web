package com.nortal.spring.cw.jsp.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;
import com.nortal.spring.cw.core.web.component.modal.ConfirmationModalDialogComponent;
import com.nortal.spring.cw.core.web.component.modal.ModalDialogComponent;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 */
@Controller
@RequestMapping(value = { "/test/test-event" })
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestEventController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;

   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#Sündmused"));
      compositeComp.setMenuComponent(new TestMenuComponent());
   }

   @EventHandler(eventName = "eventWithoutConfirmation")
   public void eventWithoutConfirmation() {
      addControllerMessage("#Käivitus sündmus: eventWithoutConfirmation");
   }

   @EventHandler(eventName = "eventWithConfirmation")
   public void eventWithConfirmation() {
      addControllerMessage("#Käivitus sündmus: eventWithConfirmation");
      ConfirmationModalDialogComponent dialog = new ConfirmationModalDialogComponent(getControllerComponent());
      dialog.getModalButtons().addModalButton(ModalDialogComponent.SUBMIT_MODAL_DIALOG_ACCEPT, GlobalLabel.BUTTON_YES);
      dialog.setCaption(new ComponentCaption("#Kas valid jah?"));
      dialog.setAcceptHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: eventWithConfirmation : valisid 'jah'");
            return null;
         }
      });
      dialog.setCancelHandler(new ElementHandler() {

         private static final long serialVersionUID = 1L;

         @Override
         public String execute() {
            addControllerMessage("#Käivitus sündmus: eventWithConfirmation : valisid 'katkesta'");
            return null;
         }
      });
      dialog.show();
   }

   @EventHandler(eventName = "eventOpenModalWindow")
   public void eventOpenModalWindow() {
      addControllerMessage("#Käivitus sündmus: eventOpenModalWindow");
   }
}
