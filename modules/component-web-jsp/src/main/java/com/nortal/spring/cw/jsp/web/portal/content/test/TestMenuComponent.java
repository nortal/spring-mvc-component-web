package com.nortal.spring.cw.jsp.web.portal.content.test;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.nortal.spring.cw.core.web.menu.AbstractMenuComponent;
import com.nortal.spring.cw.core.web.menu.model.MenuItem;
import com.nortal.spring.cw.core.web.util.RequestUtil;

@Component
public class TestMenuComponent extends AbstractMenuComponent {
   private static final long serialVersionUID = 1L;

   @Override
   public List<MenuItem> populateMenuItems() {
      List<MenuItem> items = new ArrayList<MenuItem>();

      MenuItem menu1 = MenuItem.of("#Sündmused", RequestUtil.controllerPath(TestEventController.class));
      items.add(menu1);

      MenuItem menu2 = MenuItem.of("#Modaalaknad", RequestUtil.controllerPath(TestModalController.class));
      items.add(menu2);

      MenuItem menu3 = MenuItem.of("#Parameetrid", RequestUtil.controllerPath(TestParamController.class, "121", "23"));
      menu3.getSubMenuItems().add(MenuItem.of("#Valik 1", RequestUtil.controllerPath(TestParamController.class, "134", "2")));
      menu3.getSubMenuItems().add(MenuItem.of("#Valik 2", RequestUtil.controllerPath(TestParamController.class, "133", "232")));
      items.add(menu3);

      MenuItem menu4 = MenuItem.of("#Vorm", RequestUtil.controllerPath(TestVormController.class));
      items.add(menu4);

      MenuItem menu5 = MenuItem.of("#Listid", RequestUtil.controllerPath(TestListController.class));
      menu5.getSubMenuItems().add(MenuItem.of("#Fragmendiga", RequestUtil.controllerPath(TestListFragmentController.class)));
      items.add(menu5);

      MenuItem menu6 = MenuItem.of("#Komponendid", RequestUtil.controllerPath(TestKomponentController.class));
      menu6.getSubMenuItems().add(MenuItem.of("#Samm", RequestUtil.controllerPath(TestKomponentSammController.class)));
      items.add(menu6);

      return items;
   }
}
