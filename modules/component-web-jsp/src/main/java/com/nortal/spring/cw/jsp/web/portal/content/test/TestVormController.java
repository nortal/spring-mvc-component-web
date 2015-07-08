package com.nortal.spring.cw.jsp.web.portal.content.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.element.DateTimeElementFormat;
import com.nortal.spring.cw.core.web.component.element.SelectElementType;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory.Type;
import com.nortal.spring.cw.core.web.component.form.EpmFormComponent;
import com.nortal.spring.cw.core.web.component.global.ElementAction;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;
import com.nortal.spring.cw.core.web.component.multiple.StringCollectionElement;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;
import com.nortal.spring.cw.core.web.component.single.StringElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueHolder;
import com.nortal.spring.cw.core.web.component.validator.ValidatorBankReferenceNumber;
import com.nortal.spring.cw.jsp.web.portal.component.test.model.ObjectTest;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping("/test/test-vorm")
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestVormController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;

   private static final String VORM = "vorm";

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#Vorm"));
      compositeComp.setMenuComponent(new TestMenuComponent());
      compositeComp.add(getVorm());

      compositeComp.getPageActions().addMainButton(EventElementFactory.createButton(Type.SAVE));

      // Ma natuke abusesin seda vorm-dokumenti. Ärme seda koodi veel ära kustuta, eks.
      // testWDServices();
   }

   @EventHandler(eventName = ElementAction.BUTTON_PAGE_SAVE_EVENT)
   public void pageActionButtonPageSaveEvent(ControllerComponent compositeComp) {
      if (compositeComp.validateAndConvert()) {
         addControllerMessage(GlobalLabel.SAVE_OK);
      }
   }

   private EpmFormComponent<ObjectTest> getVorm() {

      /*
       * Väljade omadused leitakse mudelobjekti annotatsioonidelt
       */

      EpmFormComponent<ObjectTest> formComp = new EpmFormComponent<>(VORM, ObjectTest.class);
      formComp.setEditable(true);

      DateTimeElement dateTimeField = formComp.add("dateTimeField");
      dateTimeField.setFormat(DateTimeElementFormat.DATETIME);

      DateTimeElement dateField = formComp.add("dateField");
      dateField.setFormat(DateTimeElementFormat.DATE);

      DateTimeElement timeField = formComp.add("timeField");
      timeField.setFormat(DateTimeElementFormat.TIME);

      StringCollectionElement list = formComp.add("list");
      list.setMultiValue(new MultiValueHolder(SelectElementType.MULTISELECT, testMultiselectData()));

      formComp.add("decimalField");
      formComp.add("integerField");
      formComp.add("longField");

      StringElement bankReferenceNumberField = formComp.add("bankReferenceNumberField");
      bankReferenceNumberField.addValidator(new ValidatorBankReferenceNumber());

      formComp.add("textField");
      formComp.add("requiredTextField");

      StringElement longTextField = formComp.add("longTextField");
      longTextField.initRichText();

      formComp.setData(getData());
      return formComp;
   }

   private Map<Object, Object> testMultiselectData() {
      Map<Object, Object> dataMap = new HashMap<>();

      for (int i = 1; i < 12; i++) {
         dataMap.put("valik_" + i, "Valik " + i);
      }

      return dataMap;
   }

   private ObjectTest getData() {
      ObjectTest objectTest = new ObjectTest();
      objectTest.setDateField(new Date());
      objectTest.setDateTimeField(new Date());
      objectTest.setTimeField(new Date());
      objectTest.setDecimalField(new BigDecimal(10.50));
      objectTest.setIntegerField(9);
      objectTest.setLongField(99999999999L);
      objectTest.setTextField("Tekstiväli");
      objectTest.setRequiredTextField("Kohustuslik tekstiväli");
      objectTest.setLongTextField("");

      return objectTest;
   }
}
