package com.nortal.spring.cw.core.web.menu;

import java.util.List;

import com.nortal.spring.cw.core.web.menu.model.MenuItem;

/**
 * Kontrollerites kasutatav kõrvalemenüü komponendi liides
 * 
 * @author Alrik Peets
 */
public interface MenuComponent {
   /**
    * Initialize MenuComponent
    */
   void init();

   List<MenuItem> getMenuItems();
}
