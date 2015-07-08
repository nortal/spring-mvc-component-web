package com.nortal.spring.cw.jsp.web.portal.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.nortal.spring.cw.core.security.UserSecurityUtil;
import com.nortal.spring.cw.core.security.annotation.Restricted;
import com.nortal.spring.cw.core.web.menu.AbstractMenuComponent;
import com.nortal.spring.cw.core.web.menu.model.MenuItem;
import com.nortal.spring.cw.core.web.menu.model.MenuItem.MenuItemHelperI;
import com.nortal.spring.cw.core.web.menu.model.MenuItemParameters;

/**
 * Tegemist on põhimenüükomponendiga, mida kasutatakse nii avaliku menüüriba kui ka administraatori menüüriba kuvamisel. Menüü ehitamisel on
 * aluseks {@link MainMenuItemEnum}, kus peamenüü võtmeks on enumi {@link MenuPointEnum} väärtus. Seejärel leitakse kõik definitsioonid mis
 * on seotud konkreetse enumiga ning lisatakse alamenüüpunktideks.
 * 
 * @author Margus Hanni
 * 
 */
@Component(value = "mainMenuComponent")
public class MainMenuComponent extends AbstractMenuComponent {

   private static final long serialVersionUID = 1L;
   private MenuItemHelper menuItemHelper = new MenuItemHelper();
   private Map<String, MenuItem> menuItems;
   @Autowired
   private RequestMappingHandlerMapping handlerMapping;

   /**
    * Meetod käivitatakse komponendi initsialiseerimisel automaatselt ning antud meetod otsib üles väljakuvatavad kõik menüüpunktid
    */
   @Override
   public List<MenuItem> populateMenuItems() {
      menuItems = new HashMap<String, MenuItem>();
      List<MenuItem> allMenuItems = new ArrayList<>();
      setMenuItemHelper(menuItemHelper);

      for (MainMenuItemEnum item : MainMenuItemEnum.values()) {

         String menuPoint = item.getMenuPoint().toString();

         MenuItem menu = menuItems.get(menuPoint);
         if (menu == null) {
            menu = new MenuItem("global.menu." + StringUtils.replace(menuPoint.toLowerCase(), "_", "-"), menuPoint);
            menuItems.put(item.getMenuPoint().toString(), menu);
         }

         MenuItem menuItem = MenuItem.of(item.getLabel(), item.getPageController());
         menuItem.getParameters().setCode(item.name());
         menu.getSubMenuItems().add(menuItem);
         allMenuItems.add(menuItem);
      }

      return allMenuItems;
   }

   /**
    * Meetod tagastab aktiivse menüüpunkti pealkirja
    * 
    * @return
    */
   public String getActiveMenuText() {
      MenuItem menuItem = getActiveMenu();

      return menuItem == null ? StringUtils.EMPTY : menuItem.getParameters().getText();
   }

   /**
    * Meetod tagastab kõik menüüpunktid, mille poole on kasutajal õigus pöörduda. Igal kontrolleril, mille juurdepääs on piiratud on küljes
    * annotatsioon {@link Restricted}, mis sisaldab konkreetset privileegi, mis peab olema kasutajal olemas, et näha konkreetset
    * menüüpunkti. Õiguste kontrollimiseks kasutatakse {@link UserSecurityUtil#checkClassPrivileges(Class)}
    * 
    * @return
    */
   public Map<String, MenuItem> getAllUserMenuItems() {
      Map<String, MenuItem> showMenuItems = new HashMap<>();

      if (menuItems == null) {
         return showMenuItems;
      }

      for (Entry<String, MenuItem> entry : menuItems.entrySet()) {
         MenuItem mainItem = showMenuItems.get(entry.getKey());

         if (mainItem == null) {
            mainItem = new MenuItem(entry.getValue().getParameters().getText(), "#");
            mainItem.setMenuItemHelper(menuItemHelper);
            showMenuItems.put(entry.getKey(), mainItem);
         }

         for (MenuItem subMenu : entry.getValue().getSubMenuItems()) {
            // kui peamenüüpunktil ei ole aadressit määratud, siis määrame
            // aadressiks esimese alammenüüpunkti aadressi
            if (addUserMenu(mainItem, subMenu) && StringUtils.equals(mainItem.getParameters().getUrl(), "#")) {
               mainItem.getParameters().setUrl(subMenu.getParameters().getUrl());
            }
         }
      }

      return showMenuItems;
   }

   private boolean addUserMenu(MenuItem menuItem, MenuItem subMenu) {
      /*
       * FIXME Margus - Hetkel näeb lahendus üpris kole välja. Lahenduse eesmärgiks on kontrollida kasutaja õigusi menüüpunkti nägemiseks.
       * Selleks sai hetkel teostatud lahendus mis otsib üles kõik Spring MVC mappingud, leiab üles vastutava kontrolleri ning kontrollib
       * kas kasutajal on õigus antud lehe avamiseks
       * 
       * Alrik - Selle võiks siis panna juba kuskile utiliit klassi. Iga menüü punkti jaoks arvutatakse vajalike privileegide komplekt
       * (skaneeritakse kohti kust seda kasutatakse). Töö käigus saab siis küsida, millist privileegide komplekti kasutajalt eeldatakse
       */
      for (Entry<RequestMappingInfo, HandlerMethod> mapping : handlerMapping.getHandlerMethods().entrySet()) {
         for (String path : mapping.getKey().getPatternsCondition().getPatterns()) {
            if (handlerMapping.getPathMatcher().match(path, subMenu.getParameters().getUrl())
                  && UserSecurityUtil.checkClassPrivileges(mapping.getValue().getBeanType())) {
               return menuItem.getSubMenuItems().add(subMenu);
            }
         }
      }

      return false;
   }

   private MenuItem getActiveMenu() {
      for (MenuItem item : CollectionUtils.emptyIfNull(getMenuItems())) {
         if (item.isSelected()) {
            return item;
         }
      }

      return null;
   }

   private class MenuItemHelper implements MenuItemHelperI {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean isSelected(MenuItemParameters parameters) {
         boolean result = StringUtils.isNotEmpty(parameters.getCode())
               && StringUtils.equalsIgnoreCase(parameters.getCode(), getUserRequestInfo().getActiveMainMenu());
         return result;
      }

      @Override
      public String getContextPath() {
         return getUserRequestInfo().getContextPath().concat("/").concat(getUserRequestInfo().getActiveLanguage().getCode().toLowerCase());
      }
   }

}
