package com.nortal.spring.cw.core.web.component.form;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import com.nortal.spring.cw.core.web.component.composite.ComponentType;
import com.nortal.spring.cw.core.web.component.composite.simple.AbstractTypeComponent;
import com.nortal.spring.cw.core.web.component.composite.simple.DataHolder;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.helper.FieldHelper;

/**
 * @author Margus Hanni
 * @since 20.02.2013
 * @param <T>
 */
public class EpmFormComponent<T> extends AbstractTypeComponent<T> implements DataHolder {

   private static final long serialVersionUID = 1L;
   private T entity;
   private final FormElementHolder elementHolder = new FormElementHolder();

   public EpmFormComponent(String componentName, Class<T> objectClass) {
      super(componentName, objectClass);
   }

   @Override
   public <K extends FormElement> K add(String elementFieldPath) {
      Assert.isTrue(!isInitialized(), "Component is already initialized");

      FormElement element = FieldHelper.createElement(getObjectClass(), elementFieldPath);
      element.setEditable(isEditable());
      element.setVisible(isVisible());

      return add(element);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <K extends FormElement> K add(final FormElement element) {

      element.setParent(this);
      elementHolder.put(element, this);

      return (K) element;
   }

   @Override
   public void remove(FormElement element) {
      elementHolder.removeByKey(element.getId());
   }

   @Override
   public boolean validateAndConvert() {

      validate();

      if (!getBindingResult().hasErrors()) {
         convert();
      }
      return !getBindingResult().hasErrors();
   }

   @Override
   public void convert() {
      for (FormElement element : elementHolder.getElements().values()) {
         if (element instanceof FormDataElement && element.isEditable()) {
            ((FormDataElement) element).convert();
         }
      }
   }

   @Override
   public boolean validate() {
      for (FormElement element : elementHolder.getElements().values()) {
         if (element instanceof FormDataElement) {
            FormDataElement dataElement = (FormDataElement) element;
            dataElement.validate();
         }
      }

      if (!getBindingResult().hasErrors()) {
         // kontrollime kitsendusi juhul kui valideerimisel ei ole esinenud vigu
         this.checkConstraints();
      }

      return !getBindingResult().hasErrors();
   }

   private void checkConstraints() {
      for (FormElement element : elementHolder.getElements().values()) {
         if (element instanceof FormDataElement) {
            FormDataElement dataElement = (FormDataElement) element;
            dataElement.checkConstraints();
         }
      }
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {
      return elementHolder.getElementHolder();
   }

   @SuppressWarnings("unchecked")
   public <E extends FormElement> E getElement(String id) {
      return (E) elementHolder.getElements().get(id);
   }

   @SuppressWarnings("unchecked")
   @Override
   public T getData() {
      return entity;
   }

   @SuppressWarnings("unchecked")
   @Override
   public void setData(Object entity) {
      this.entity = (T) entity;

      for (Entry<String, FormElement> entry : elementHolder.getElements().entrySet()) {
         if (StringUtils.isNotEmpty(entry.getKey())) {
            ((FormDataElement) entry.getValue()).setEntity(this.entity);
         }
      }
   }

   @Override
   public ComponentType getComponentType() {
      return ComponentType.FORM;
   }

   @Override
   public void afterInitComponent() {
      // Nothing to do here
   }

   public Map<String, FormElement> getElements() {
      return elementHolder.getElements();
   }
}
