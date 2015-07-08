package com.nortal.spring.cw.jsp.web.tag;

import javax.servlet.jsp.JspException;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.tags.form.AbstractFormTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Abstrakte vormi tagide klass. Klass hoiab endas aktiivset elementi ning lisaks abistab põhikomponendi leidmist
 * 
 * @author margush
 * 
 * @param <T>
 */

public abstract class AbstractEpmFormTag<T extends FormElement> extends AbstractFormTag {

   private static final long serialVersionUID = 1L;
   protected T element;

   private ComplexComponent mainComponent;

   public T getElement() {
      return element;
   }

   public abstract void setElement(T element);

   protected ComplexComponent getMainComponent() {

      if (this.mainComponent != null) {
         return this.mainComponent;
      }

      this.mainComponent = ElementUtil.getMainComponent(element);

      return this.mainComponent;
   }

   protected Errors getErrors() {
      return getRequestContext().getErrors(getMainComponent().getId());
   }

   protected void showError(TagWriter tagWriter, FieldError error) throws JspException {
      if (error != null) {
         tagWriter.startTag("p");
         tagWriter.writeAttribute("id", id + ".error");
         tagWriter.writeAttribute("class", "error");
         tagWriter.appendValue(getRequestContext().getMessage(error.getCode()));
         tagWriter.endTag();
      }
   }

}
