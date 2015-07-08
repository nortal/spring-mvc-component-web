package com.nortal.spring.cw.jsp.web.portal.content.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.util.date.DateTime;
import com.nortal.spring.cw.core.util.date.Time;
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
 */
@Controller
@RequestMapping("/test/test-list")
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestListController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;
   private static final String LIST = "list";

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#List"));
      compositeComp.setMenuComponent(new TestMenuComponent());
      compositeComp.add(getList());
   }

   private ListComponent getList() {
      EpmListComponent<ObjectTest> listComp = new EpmListComponent<>(LIST, ObjectTest.class, "integerField", true);
      listComp.getProperties().setShowRowNumber(true);
      listComp.getProperties().setSortable(true);
      listComp.getProperties().setFilterable(true);
      listComp.getProperties().setAllowDeleteButton(true);
      listComp.getProperties().setAllowEditButton(true);

      DateTimeElement dateField = listComp.add("dateField");
      dateField.setFormat(DateTimeElementFormat.DATE);
      dateField.setEditable(true);

      DateTimeElement timeField = listComp.add("timeField");
      timeField.setFormat(DateTimeElementFormat.TIME);
      timeField.setEditable(true);

      DateTimeElement dateTimeField = listComp.add("dateTimeField");
      dateTimeField.setFormat(DateTimeElementFormat.DATETIME);
      dateTimeField.setEditable(true);

      listComp.add("decimalField").setEditable(true);
      listComp.add("integerField").setEditable(true);
      listComp.add("longField").setEditable(true);
      listComp.add("textField").setEditable(true);

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
      Date date;
      try {
         date = new DateTime("18.09.2013 12:00");
      } catch (ParseException e1) {
         throw new AppBaseRuntimeException(e1);
      }

      for (int i = 0; i < 2000; i++) {
         ObjectTest objectTest = new ObjectTest();

         objectTest.setDateField(new org.joda.time.DateTime().plusDays(i).toLocalDate().toDate());
         try {
            // FIXME Margus - Ei ole hea lahendus, tuleme siia kunagi tagasi
            objectTest.setTimeField(new Time(Time.getAsText(DateUtils.addMinutes(DateUtils.addMinutes(date, i), i))));
         } catch (ParseException e) {
            throw new AppBaseRuntimeException(e);
         }
         objectTest.setDateTimeField(DateUtils.addMinutes(DateUtils.addMinutes(date, i), i));

         objectTest.setDecimalField(new BigDecimal(i * 1.32).setScale(2, RoundingMode.UP));
         objectTest.setIntegerField(i);
         objectTest.setLongField(i * 2L);
         objectTest.setTextField(String.format("Tekst - %d ", i));

         datas.add(objectTest);
      }

      return datas;
   }
}
