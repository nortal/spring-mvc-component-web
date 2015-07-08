package com.nortal.spring.cw.core.web.component.constraint;

import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.element.AbstractConstraint;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.form.EpmFormComponent;
import com.nortal.spring.cw.core.web.component.list.ListComponent;

/**
 * Kontrollib, et uus sisestatud kuupäeva vahemik ei kattuks mõne teise vahemikuga. Täpne vahemiku kontrollimise loogika ja veateade tuleb
 * ise implementeerida.
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @param <T>
 *           entity class type
 * @param <E>
 *           value class type
 * @since 16.10.2013
 */

public abstract class AbstractConstraintIntervalNotOverlapping<T, E> extends AbstractConstraint {
   private static final long serialVersionUID = 1L;

   private String intervalStartPath;
   private String intervalEndPath;

   public AbstractConstraintIntervalNotOverlapping(String intervalStartPath, String intervalEndPath) {
      this.intervalStartPath = intervalStartPath;
      this.intervalEndPath = intervalEndPath;
   }

   @SuppressWarnings("unchecked")
   @Override
   public void check() {

      FormDataElement intervalStartWithVal = (FormDataElement) getElementById(intervalStartPath);
      FormDataElement intervalEndWithVal = (FormDataElement) getElementById(intervalEndPath);

      Component parent = (Component) intervalStartWithVal.getParent();
      T entity = getEntity(parent);

      if (entity != null && intervalOverlays(entity, (E) intervalStartWithVal.getRawValue(), (E) intervalEndWithVal.getRawValue())) {
         parent.addErrorMessage(getErrorMsgCode());
      }
   }

   @SuppressWarnings("unchecked")
   private T getEntity(Component elementParent) {
      T entity = null;
      if (elementParent instanceof ListComponent) {
         ListComponent parentList = (ListComponent) elementParent;
         entity = parentList.getActiveEntity();
      } else if (elementParent instanceof EpmFormComponent) {
         EpmFormComponent<?> parentForm = (EpmFormComponent<?>) elementParent;
         entity = (T) parentForm.getData();
      }
      return entity;
   }

   protected abstract boolean intervalOverlays(T entity, E startValue, E endValue);

   protected abstract String getErrorMsgCode();
}