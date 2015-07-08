package com.nortal.spring.cw.core.web.menu;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.web.component.BaseElement;
import com.nortal.spring.cw.core.web.menu.model.MenuItem;
import com.nortal.spring.cw.core.web.menu.model.MenuItemParameters;
import com.nortal.spring.cw.core.web.menu.model.MenuItem.MenuItemHelperI;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Menüükomponendi baas-lahendus
 * 
 * @author Alrik Peets
 */
@Component
public abstract class AbstractMenuComponent extends BaseElement implements MenuComponent {
   private static final long serialVersionUID = 1L;

   private List<MenuItem> menuItems;

   private CwUserRequestInfo userRequestInfo;

   private MenuItemHelperI menuItemHelper = new MenuItemHelper();

   public final static String MENU_COMPONENT_NAME = "menuComponent";

   public AbstractMenuComponent() {
      this(MENU_COMPONENT_NAME);
   }

   public AbstractMenuComponent(String elementName) {
      super(elementName);
   }

   public void setMenuItemHelper(MenuItemHelperI menuItemHelper) {
      this.menuItemHelper = menuItemHelper;
   }

   public List<MenuItem> getMenuItems() {
      return menuItems;
   }

   public void init() {
      menuItems = populateMenuItems();
      for (MenuItem item : menuItems) {
         item.setMenuItemHelper(menuItemHelper);
      }
   }

   protected CwUserRequestInfo getUserRequestInfo() {
      if (userRequestInfo == null) {
         userRequestInfo = RequestUtil.getUserRequestInfo();
      }
      return userRequestInfo;
   }

   private class MenuItemHelper implements MenuItemHelperI {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean isSelected(MenuItemParameters parameters) {
         // meil võivad olla lisaks ka kontrolleri parameetrid, seega arvestame
         // ainult aadressi algust, kui kattub siis märgime aktiivseks
         boolean result = StringUtils.isNotEmpty(parameters.getUrl())
               && StringUtils.startsWithIgnoreCase(getUserRequestInfo().getApplicationPath(), parameters.getUrl());
         return result;
      }

      @Override
      public String getContextPath() {
         return getUserRequestInfo().getContextPath().concat("/")
               .concat(getUserRequestInfo().getActiveLanguage().getCode().toLowerCase());
      }

   }

   public abstract List<MenuItem> populateMenuItems();
}
