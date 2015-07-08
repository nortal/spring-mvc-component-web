package com.nortal.spring.cw.jsp.web.portal.component.test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nortal.spring.cw.core.web.component.ElementVisibility;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.element.DateTimeElementFormat;
import com.nortal.spring.cw.core.web.component.element.SelectElementType;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory.Type;
import com.nortal.spring.cw.core.web.component.form.EpmFormComponent;
import com.nortal.spring.cw.core.web.component.multiple.StringCollectionElement;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;
import com.nortal.spring.cw.core.web.component.single.StringElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueHolder;
import com.nortal.spring.cw.jsp.servlet.view.ResolvableViewComponent;
import com.nortal.spring.cw.jsp.web.portal.component.test.model.ObjectTest;

/**
 * @author Margus Hanni
 * 
 */
public class TestComponent extends ResolvableViewComponent {

   private static final long serialVersionUID = 1L;
   public static final String TEST_KOMPONENT = "testKomponent";
   private static final String VORM = "vorm";

   public TestComponent() {
      super(TEST_KOMPONENT);
   }

   @Override
   public void initComponent() {
      setCaption(new ComponentCaption("#Test komponent"));
      buildForm();
      super.initComponent();
   }

   private void buildForm() {
      add(getVorm());

      // Näitame komponendi salvestuse nuppu ainult siis kui sammu komponent ei
      // ole aktiivne
      getPageActions().addMainButton(EventElementFactory.createButton(Type.SAVE).setVisibility(new ElementVisibility() {

         private static final long serialVersionUID = 1L;

         @Override
         public boolean isVisible(Hierarchical parent) {
            return getFirstStepComplexComponent() == null;
         }
      }));
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
      list.setMultiValue(new MultiValueHolder(SelectElementType.MULTISELECT, getTestMultiselectData()));

      formComp.add("decimalField");
      formComp.add("integerField");
      formComp.add("longField");

      formComp.add("textField");
      formComp.add("requiredTextField");

      StringElement longTextField = formComp.add("longTextField");
      longTextField.initRichText();

      formComp.setData(getData());
      return formComp;
   }

   private Map<Object, Object> getTestMultiselectData() {
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
      objectTest.setDecimalField(BigDecimal.valueOf(0.50));
      objectTest.setIntegerField(10);
      objectTest.setLongField(111111L);
      objectTest.setTextField("Tekstiväli");
      objectTest.setRequiredTextField("Kohustuslik tekstiväli");
      objectTest.setLongTextField("");

      return objectTest;
   }
}
