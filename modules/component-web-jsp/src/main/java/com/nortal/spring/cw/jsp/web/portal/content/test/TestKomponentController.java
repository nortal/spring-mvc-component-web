package com.nortal.spring.cw.jsp.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.global.ElementAction;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.jsp.web.portal.component.test.TestComponent;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping(value = { "/test/test-komponent" })
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestKomponentController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#Komponendi kasutamine"));
      compositeComp.setMenuComponent(new TestMenuComponent());

      compositeComp.add(new TestComponent());
   }

   @EventHandler(eventName = ElementAction.BUTTON_PAGE_SAVE_EVENT)
   public void pageActionButtonPageSaveEvent(ControllerComponent compositeComp) {
      if (compositeComp.validateAndConvert()) {
         addControllerMessage(GlobalLabel.SAVE_OK);
      }
   }

}
