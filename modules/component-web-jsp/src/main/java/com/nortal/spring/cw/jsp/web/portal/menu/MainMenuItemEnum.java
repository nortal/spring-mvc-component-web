package com.nortal.spring.cw.jsp.web.portal.menu;

import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.content.test.TestEventController;

/**
 * @author Margus Hanni
 * 
 */
public enum MainMenuItemEnum {

   //@formatter:off
   TEST(MenuPointEnum.TEST, "global.menu.test-components", TestEventController.class),
   ;
   //@formatter:on

   private MenuPointEnum menuPoint;
   private String label;
   private Class<? extends AbstractModelPageController> pageController;

   private MainMenuItemEnum(MenuPointEnum menuPoint, String label, Class<? extends AbstractModelPageController> pageController) {
      this.menuPoint = menuPoint;
      this.label = label;
      this.pageController = pageController;
   }

   public Class<? extends AbstractModelPageController> getPageController() {
      return pageController;
   }

   public String getLabel() {
      return label;
   }

   public MenuPointEnum getMenuPoint() {
      return menuPoint;
   }
}
