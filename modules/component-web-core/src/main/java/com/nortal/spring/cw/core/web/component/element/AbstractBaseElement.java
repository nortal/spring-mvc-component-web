package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.ElementChangeable;
import com.nortal.spring.cw.core.web.component.EventBaseElement;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.util.BeanUtil;

/**
 * Elemendi põhiabstraktsioon
 * 
 * @author Margus Hanni
 */
public abstract class AbstractBaseElement<T> extends EventBaseElement implements FormDataElement {

   private static final long serialVersionUID = 1L;

   private Object originalValue;
   private boolean stateMarked;
   private T value;
   private boolean mandatory;
   private FieldElementCssClass cssClass;
   private List<ElementValidator> validators = new ArrayList<ElementValidator>();
   private List<ElementConstraint> constraints = new ArrayList<ElementConstraint>();
   private Object entity;
   private boolean useAjaxValidation = true;
   private boolean escapeXml = true;
   private ElementChangeable elementChangeable;

   {
      addValidator(new AbstractValidator() {

         private static final long serialVersionUID = 1L;

         @Override
         public void validate() {
            if (isMandatory() && FieldElementUtil.isFieldEmpty(super.getElement())) {
               super.getElement().addElementErrorMessage(FieldErrorEnum.MANDATORY.getTranslationKey());
            }
         }
      });
   }

   public AbstractBaseElement() {
      super(null);
   }

   public T getValue() {
      return value;
   }

   public boolean isMandatory() {
      return mandatory;
   }

   public void setValue(T value) {
      this.value = value;
   }

   public void setMandatory(boolean mandatory) {
      this.mandatory = mandatory;
   }

   public String getLabel() {
      return StringUtils.isEmpty(super.getLabel()) ? getId() : super.getLabel();
   }

   @Override
   public Object getRawValue() {
      return getValue();
   }

   @SuppressWarnings("unchecked")
   public void setRawValue(Object value) {
      setValue((T) value);
   }

   public String getCssClassValue() {
      return cssClass == null ? "" : cssClass.getValue();
   }

   // TODO: see on väga piirav... Refaktorda: addCssClass, clearCssClass
   // meetoditekst, classi hoida stringina.
   public FormElement setCssClass(FieldElementCssClass cssClass) {
      this.cssClass = cssClass;
      return this;
   }

   public FieldElementCssClass getCssClass() {
      return this.cssClass;
   }

   @Override
   @EventHandler(eventName = "validate")
   public final void validate() {
      if (isEditable()) {
         for (ElementValidator validator : validators) {
            // määrame elemendi alati uuesti, vajalik listi tööks
            validator.setParent((Component) getParent());
            validator.setElementId(this.getId());
            validator.validate();
         }
      }
   }

   @Override
   public final void checkConstraints() {
      for (ElementConstraint constraint : constraints) {
         constraint.setParent((Component) getParent());
         constraint.check();
      }
   }

   public List<ElementConstraint> getConstraints() {
      return constraints;
   }

   public void setConstraints(List<ElementConstraint> constraints) {
      this.constraints = constraints;
   }

   @Override
   public FormDataElement addConstraint(ElementConstraint constraint) {
      constraints.add(constraint);
      return this;
   }

   public FormDataElement addValidator(ElementValidator validator) {
      validators.add(validator);
      return this;
   }

   public Object getEntity() {
      return entity;
   }

   /**
    * {@inheritDoc}<br>
    * Täiendavalt väärtustatakse ära elemendi väli <i>value</i> {@link FormDataElement#setRawValue(Object)}. Elemendi väärtus leitakse
    * {@link AbstractBaseElement#getEntityFieldValue} vahendusel
    */
   public void setEntity(Object entity) {
      this.entity = entity;
      if (entity != null) {
         setRawValue(getEntityFieldValue());
      }
   }

   @Override
   public void convert() {
      if (entity != null) {
         BeanUtil.setValueByPath(entity, getId(), getRawValue());
      }
   }

   @Override
   public boolean isUseAjaxValidation() {
      return useAjaxValidation;
   }

   @Override
   public void setUseAjaxValidation(boolean useAjaxValidation) {
      this.useAjaxValidation = useAjaxValidation;
   }

   public boolean isChanged() {
      Object entityFieldValue = getEntityFieldValue();
      // at least one has to be not null
      if (entityFieldValue != null || getValue() != null) {
         // TODO: test this condition
         return entity == null || ObjectUtils.notEqual(entityFieldValue, getValue());
      }
      return false;
   }

   protected Object getEntityFieldValue() {
      return entity == null ? null : BeanUtil.getValueByPath(entity, getId());
   }

   public boolean isEscapeXml() {
      return escapeXml;
   }

   public void setEscapeXml(boolean escapeXml) {
      this.escapeXml = escapeXml;
   }

   public List<ElementValidator> getValidators() {
      return validators;
   }

   public void setValidators(List<ElementValidator> validators) {
      this.validators = validators;
   }

   @Override
   public void markedState() {
      Object oldValue = getValue();

      if (oldValue == null) {
         originalValue = oldValue;
      } else {
         if (oldValue instanceof Serializable) {
            this.originalValue = SerializationUtils.clone((Serializable) oldValue);
         }
      }

      this.stateMarked = true;
   }

   @SuppressWarnings("unchecked")
   @Override
   public void revertToMarkedState() {
      if (this.stateMarked) {
         setValue((T) originalValue);
         this.stateMarked = false;
      }
   }

   public ElementChangeable getElementChangeable() {
      return elementChangeable;
   }

   public void setElementChangeable(ElementChangeable elementChangeable) {
      this.elementChangeable = elementChangeable;
   }

   @Override
   public boolean isEditable() {
      if (this.elementChangeable != null) {
         return elementChangeable.isChangeable((Component) getParent());
      }

      return super.isEditable();
   }
}
