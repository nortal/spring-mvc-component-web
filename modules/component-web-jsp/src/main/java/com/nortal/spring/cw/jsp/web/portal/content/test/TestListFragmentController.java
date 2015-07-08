package com.nortal.spring.cw.jsp.web.portal.content.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.element.DateTimeElementFormat;
import com.nortal.spring.cw.core.web.component.list.DefaultListDataProvider;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;
import com.nortal.spring.cw.jsp.web.portal.component.test.model.ObjectTest;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping("/test/test-list-fragment")
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestListFragmentController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;
   private static final String FRAGMENT_CELL_LIST = "fragment-cell-list";
   private static final String FRAGMENT_ROW_LIST = "fragment-row-list";

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#List kus on kasutatud fragmente"));
      compositeComp.setMenuComponent(new TestMenuComponent());
      compositeComp.add(getFragmentCellList());
      compositeComp.add(getFrafmentRowList());
   }

   private ListComponent getFragmentCellList() {
      EpmListComponent<ObjectTest> listComp = new EpmListComponent<>(FRAGMENT_CELL_LIST, ObjectTest.class, "integerField", true);
      listComp.setCaption(new ComponentCaption("#List kus on üle kirjutatud viies veerg"));
      listComp.getProperties().setShowRowNumber(true);
      listComp.getProperties().setSortable(true);
      listComp.getProperties().setFilterable(true);
      listComp.getProperties().setAllowDeleteButton(true);
      listComp.getProperties().setAllowEditButton(true);

      DateTimeElement dateField = listComp.add("dateField");
      dateField.setFormat(DateTimeElementFormat.DATE);

      DateTimeElement timeField = listComp.add("timeField");
      timeField.setFormat(DateTimeElementFormat.TIME);

      DateTimeElement dateTimeField = listComp.add("dateTimeField");
      dateTimeField.setFormat(DateTimeElementFormat.DATETIME);

      listComp.add("decimalField").setEditable(true);
      listComp.add("integerField").setEditable(true);
      listComp.add("longField").setEditable(true);
      listComp.add("textField").setEditable(true);

      listComp.setDataProvider(new ListDataProvider());

      listComp.getProperties().setAllowAdd(true);
      return listComp;
   }

   private ListComponent getFrafmentRowList() {
      EpmListComponent<ObjectTest> listComp = new EpmListComponent<>(FRAGMENT_ROW_LIST, ObjectTest.class, "integerField", true);
      listComp.setCaption(new ComponentCaption("#List kus on üle kirjutatud rea tekitamine"));
      listComp.getProperties().setShowRowNumber(true);
      listComp.getProperties().setSortable(true);
      listComp.getProperties().setFilterable(true);
      listComp.getProperties().setAllowDeleteButton(true);
      listComp.getProperties().setAllowEditButton(true);

      DateTimeElement dateField = listComp.add("dateField");
      dateField.setFormat(DateTimeElementFormat.DATE);

      DateTimeElement timeField = listComp.add("timeField");
      timeField.setFormat(DateTimeElementFormat.TIME);

      DateTimeElement dateTimeField = listComp.add("dateTimeField");
      dateTimeField.setFormat(DateTimeElementFormat.DATETIME);

      listComp.add("decimalField");
      listComp.add("integerField");
      listComp.add("longField");
      listComp.add("textField");

      listComp.setDataProvider(new ListDataProvider());

      listComp.getProperties().setAllowAdd(true);
      return listComp;
   }

   private class ListDataProvider extends DefaultListDataProvider<ObjectTest> {
      @Override
      public List<ObjectTest> loadData() {
         return getTestDataList();
      }
   }

   private List<ObjectTest> getTestDataList() {
      List<ObjectTest> datas = new ArrayList<>();
      Date date = new Date();
      for (int i = 0; i < 2000; i++) {
         ObjectTest objectTest = new ObjectTest();

         objectTest.setDateField(new org.joda.time.DateTime().plusDays(i).toLocalDate().toDate());
         objectTest.setTimeField(DateUtils.addMinutes(date, i));
         objectTest.setDateTimeField(DateUtils.addMinutes(DateUtils.addMinutes(date, i), i));

         objectTest.setDecimalField(new BigDecimal(i * 1.32));
         objectTest.setIntegerField(i);
         objectTest.setLongField(i * 2L);
         objectTest.setTextField(String.format("Tekst - %d ", i));

         datas.add(objectTest);
      }

      return datas;
   }
}
