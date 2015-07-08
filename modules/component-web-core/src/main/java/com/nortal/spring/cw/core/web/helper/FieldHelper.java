package com.nortal.spring.cw.core.web.helper;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.Range;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.model.LangModel;
import com.nortal.spring.cw.core.web.annotation.component.BooleanField;
import com.nortal.spring.cw.core.web.annotation.component.DateTimeField;
import com.nortal.spring.cw.core.web.annotation.component.DoubleField;
import com.nortal.spring.cw.core.web.annotation.component.IntegerCollectionField;
import com.nortal.spring.cw.core.web.annotation.component.IntegerField;
import com.nortal.spring.cw.core.web.annotation.component.LongCollectionField;
import com.nortal.spring.cw.core.web.annotation.component.LongField;
import com.nortal.spring.cw.core.web.annotation.component.StringCollectionField;
import com.nortal.spring.cw.core.web.annotation.component.StringField;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.multiple.FileCollectionElement;
import com.nortal.spring.cw.core.web.component.multiple.IntegerCollectionElement;
import com.nortal.spring.cw.core.web.component.multiple.LanguageElement;
import com.nortal.spring.cw.core.web.component.multiple.LongCollectionElement;
import com.nortal.spring.cw.core.web.component.multiple.StringCollectionElement;
import com.nortal.spring.cw.core.web.component.single.BooleanElement;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;
import com.nortal.spring.cw.core.web.component.single.DoubleElement;
import com.nortal.spring.cw.core.web.component.single.FileElement;
import com.nortal.spring.cw.core.web.component.single.IntegerElement;
import com.nortal.spring.cw.core.web.component.single.LongElement;
import com.nortal.spring.cw.core.web.component.single.StringElement;
import com.nortal.spring.cw.core.web.exception.FieldNotFoundException;
import com.nortal.spring.cw.core.web.util.BeanUtil;

/**
 * Klass koondab endas erinevaid vahendeid vormi väljadega manipuleerimiseks
 * 
 * @author Margus Hanni
 */
public final class FieldHelper {

   private FieldHelper() {
      super();
   }

   /**
    * Välja baasil vormi elemendi loomine. Kui Elemendi loomine ei õnnestunud, mis tähendab üldjuhul seda et tüüp ei ole lubatud, kutsutakse
    * välja RuntimeException
    * 
    * @param elementFieldPath
    * @param objClass
    * @return
    */
   public static <T extends FormElement> T createElement(Class<?> objClass, String elementFieldPath) {
      return createElement(BeanUtil.getMethodByFieldPath(objClass, elementFieldPath), elementFieldPath);
   }

   /**
    * Meetodi baasil vormi elemendi loomine. Kui Elemendi loomine ei õnnestunud, mis tähendab üldjuhul seda et tüüp ei ole lubatud,
    * kutsutakse välja RuntimeException
    * 
    * @param method
    * @param elementFieldPath
    * @return
    */
   public static <T extends FormElement> T createElement(Method method, String elementFieldPath) {

      T element = createElementByAnnotation(method, elementFieldPath);

      if (element == null) {
         element = createElementByType(method, elementFieldPath);
      }

      if (element == null) {
         throw new FieldNotFoundException(elementFieldPath);
      }

      return element;
   }

   @SuppressWarnings("unchecked")
   private static <T extends FormElement> T createElementByType(Method method, String elementFieldPath) {

      T element = null;
      Class<?> returnType = method.getReturnType();

      String label = elementFieldPath;
      boolean mandatory = false;

      if (returnType.equals(String.class)) {
         element = (T) new StringElement();
      } else if (returnType.equals(Long.class)) {
         element = (T) new LongElement();
      } else if (returnType.equals(Integer.class)) {
         element = (T) new IntegerElement();
      } else if (returnType.equals(BigDecimal.class)) {
         element = (T) new DoubleElement();
      } else if (returnType.equals(Boolean.class)) {
         element = (T) new BooleanElement();
      } else if (returnType.equals(Date.class) || returnType.equals(Time.class) || returnType.equals(Timestamp.class)) {
         element = (T) new DateTimeElement();
      } else if (FileHolderModel.class.isAssignableFrom(returnType)) {
         element = (T) new FileElement().setModelClass((Class<? extends FileHolderModel>) returnType);
      } else if (Collection.class.isAssignableFrom(returnType)) {
         element = createCollectionElementByType(method);
      }

      if (element != null) {
         elementBaseData((AbstractBaseElement<?>) element, elementFieldPath, mandatory, label);
      }

      return element;
   }

   @SuppressWarnings("unchecked")
   private static <T extends FormElement> T createCollectionElementByType(Method method) {
      T element = null;
      Type type = ((ParameterizedType) method.getGenericReturnType()).getActualTypeArguments()[0];

      if (type.equals(String.class)) {
         element = (T) new StringCollectionElement();
      } else if (type.equals(Long.class)) {
         element = (T) new LongCollectionElement();
      } else if (type.equals(Integer.class)) {
         element = (T) new IntegerCollectionElement();
      } else if (LangModel.class.isAssignableFrom((Class<?>) type)) {
         element = (T) new LanguageElement().setModelClass((Class<? extends LangModel>) type);
      } else if (FileHolderModel.class.isAssignableFrom((Class<?>) type)) {
         element = (T) new FileCollectionElement().setModelClass((Class<FileHolderModel>) type);
      } else {
         throw new NotImplementedException("Not implemented type: " + type);
      }

      return element;
   }

   @SuppressWarnings("unchecked")
   private static <T extends FormElement> T createElementByAnnotation(Method method, String elementFieldPath) {

      T element = null;

      if (method.isAnnotationPresent(StringField.class)) {
         element = (T) FieldHelper.createStringElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(LongField.class)) {
         element = (T) FieldHelper.createLongElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(DoubleField.class)) {
         element = (T) FieldHelper.createDoubleElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(IntegerField.class)) {
         element = (T) FieldHelper.createIntegerElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(BooleanField.class)) {
         element = (T) FieldHelper.createBooleanElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(DateTimeField.class)) {
         element = (T) FieldHelper.createDateTimeElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(StringCollectionField.class)) {
         element = (T) FieldHelper.createStringCollectionElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(LongCollectionField.class)) {
         element = (T) FieldHelper.createLongCollectionElement(method, elementFieldPath);
      } else if (method.isAnnotationPresent(IntegerCollectionField.class)) {
         element = (T) FieldHelper.createIntegerCollectionElement(method, elementFieldPath);
      }

      return element;
   }

   private static BooleanElement createBooleanElement(Method method, String elementFieldPath) {
      BooleanField field = method.getAnnotation(BooleanField.class);

      BooleanElement elementComp = new BooleanElement();
      elementBaseData(elementComp, elementFieldPath, false, field.label());

      return elementComp;
   }

   public static DoubleElement createDoubleElement(Method method, String elementFieldPath) {
      DoubleField field = method.getAnnotation(DoubleField.class);

      String label = StringUtils.isEmpty(field.label()) ? elementFieldPath : field.label();
      boolean mandatory = field.required();

      DoubleElement elementComp = new DoubleElement();
      Range<BigDecimal> range = Range.between(BigDecimal.valueOf(field.between()[0]), BigDecimal.valueOf(field.between()[1]));
      elementComp.setRange(range);
      elementBaseData(elementComp, elementFieldPath, mandatory, label);

      return elementComp;
   }

   private static LongElement createLongElement(Method method, String elementFieldPath) {
      LongField field = method.getAnnotation(LongField.class);

      boolean mandatory = field.required();

      LongElement elementComp = new LongElement();
      Range<Long> longRange = Range.between(field.between()[0], field.between()[1]);
      elementComp.setRange(longRange);
      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static IntegerElement createIntegerElement(Method method, String elementFieldPath) {
      IntegerField field = method.getAnnotation(IntegerField.class);

      boolean mandatory = field.required();

      IntegerElement elementComp = new IntegerElement();

      Range<Integer> intRange = Range.between(field.between()[0], field.between()[1]);
      elementComp.setRange(intRange);
      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static StringElement createStringElement(Method method, String elementFieldPath) {
      StringField field = method.getAnnotation(StringField.class);

      boolean mandatory = field.required();
      StringElement elementComp = new StringElement();
      elementComp.setLength(field.length());
      elementComp.setRows(field.rows());
      elementComp.setCols(field.cols());
      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static DateTimeElement createDateTimeElement(Method method, String elementFieldPath) {
      DateTimeField field = method.getAnnotation(DateTimeField.class);

      boolean mandatory = field.required();

      DateTimeElement elementComp = new DateTimeElement();
      Date min = StringUtils.isEmpty(field.between()[0]) ? null : DateTime.parse(field.between()[0]).toDate();
      Date max = StringUtils.isEmpty(field.between()[1]) ? null : DateTime.parse(field.between()[1]).toDate();

      Range<Long> longRange = null;
      if (min != null && max != null) {
         longRange = Range.between(min.getTime(), max.getTime());
      } else if (min != null || max != null) {
         longRange = Range.is((min == null ? max : min).getTime());
      }

      elementComp.setRange(longRange);
      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static StringCollectionElement createStringCollectionElement(Method method, String elementFieldPath) {
      StringCollectionField field = method.getAnnotation(StringCollectionField.class);

      boolean mandatory = field.required();

      StringCollectionElement elementComp = new StringCollectionElement();

      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static LongCollectionElement createLongCollectionElement(Method method, String elementFieldPath) {
      LongCollectionField field = method.getAnnotation(LongCollectionField.class);

      boolean mandatory = field.required();

      LongCollectionElement elementComp = new LongCollectionElement();

      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static IntegerCollectionElement createIntegerCollectionElement(Method method, String elementFieldPath) {
      IntegerCollectionField field = method.getAnnotation(IntegerCollectionField.class);

      boolean mandatory = field.required();

      IntegerCollectionElement elementComp = new IntegerCollectionElement();

      elementBaseData(elementComp, elementFieldPath, mandatory, field.label());

      return elementComp;
   }

   private static void elementBaseData(AbstractBaseElement<?> baseElementComp, String elementFieldPath, boolean mandatory, String label) {
      baseElementComp.setId(elementFieldPath);
      baseElementComp.setMandatory(mandatory);
      baseElementComp.setLabel(label);
   }

}
