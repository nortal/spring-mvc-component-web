package com.nortal.spring.cw.jsp.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.annotation.controller.ControllerVariable;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping("/test/test-param")
@ControllerVariable(names = { TestParamController.CONTROLLER_PARAM_1, TestParamController.CONTROLLER_PARAM_2 })
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestParamController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;

   protected static final String CONTROLLER_PARAM_1 = "param1";
   protected static final String CONTROLLER_PARAM_2 = "param2";

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#Parameetrid"));
      compositeComp.setMenuComponent(new TestMenuComponent());
   }

   public String getParam1() {
      return getControllerComponent().getParameter(CONTROLLER_PARAM_1);
   }

   public String getParam2() {
      return getControllerComponent().getParameter(CONTROLLER_PARAM_2);
   }
}
