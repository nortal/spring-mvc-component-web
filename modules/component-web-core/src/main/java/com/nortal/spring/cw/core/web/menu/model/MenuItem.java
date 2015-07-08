package com.nortal.spring.cw.core.web.menu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Kõrvalmenüüs kasutatav menüüpunkti element
 * 
 * @author Alrik Peets
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuItem implements Serializable {
   private static final long serialVersionUID = 1L;
   private List<MenuItem> subMenuItems;
   private MenuItemHelperI menuItemHelper;
   private MenuItemParameters parameters;

   public MenuItem(String text, String url) {
      parameters = new MenuItemParameters(text, url);
   }

   public String getMenuUrl() {
      return menuItemHelper.getContextPath() + parameters.getUrl();
   }

   public List<MenuItem> getSubMenuItems() {
      if (subMenuItems == null) {
         subMenuItems = new ArrayList<MenuItem>();
      }
      return subMenuItems;
   }

   public boolean isSelected() {
      for (MenuItem item : getSubMenuItems()) {
         if (item.isSelected()) {
            return true;
         }
      }
      return menuItemHelper.isSelected(parameters);
   }

   public void setMenuItemHelper(MenuItemHelperI menuItemHelper) {
      this.menuItemHelper = menuItemHelper;
      for (MenuItem item : getSubMenuItems()) {
         item.setMenuItemHelper(menuItemHelper);
      }
   }

   public interface MenuItemHelperI extends Serializable {
      boolean isSelected(MenuItemParameters parameters);

      String getContextPath();
   }

   public static MenuItem of(String text, Class<?> controller) {
      String url = controller.getAnnotation(RequestMapping.class).value()[0];
      return new MenuItem(text, url);
   }

   public static MenuItem of(String text, String url) {
      return new MenuItem(text, url);
   }

   public MenuItemParameters getParameters() {
      return parameters;
   }
}
