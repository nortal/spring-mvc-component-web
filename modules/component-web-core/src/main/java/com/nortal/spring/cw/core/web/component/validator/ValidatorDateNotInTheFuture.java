package com.nortal.spring.cw.core.web.component.validator;

import java.util.Date;

import org.joda.time.LocalDate;

import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.form.EpmFormComponent;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;

/**
 * Vormi {@link EpmFormComponent} ja listi {@link EpmListComponent} kuupäeva välja kontrollimine. Kontrollitakse kas aktiivne väli ei ole
 * tulevikus
 * 
 * @author Margus Hanni
 * 
 */
public class ValidatorDateNotInTheFuture extends AbstractValidator {

   private static final long serialVersionUID = 1L;

   @Override
   public void validate() {

      if (getElement().getParent() instanceof EpmFormComponent) {
         if (getElement().isChanged() && getElement().getRawValue() != null
               && ((Date) getElement().getRawValue()).after(LocalDate.now().toDate())) {
            getElement().addElementErrorMessage("field.datetime.error.constraint.date-in-the-future");
         }
      }

      if (getElement().getParent() instanceof EpmListComponent) {
         ListComponent listComponent = (ListComponent) getElement().getParent();
         ListRow listRow = listComponent.getActiveListRow();

         if (listRow == null) {
            return;
         }

         DateTimeElement fActive = (DateTimeElement) listRow.getElementsGroup().getById(getElement().getId()).getElement();
         if (fActive.isChanged() && fActive.getValue() != null && fActive.getValue().after(LocalDate.now().toDate())) {
            fActive.addElementErrorMessage("field.datetime.error.constraint.date-in-the-future");
         }
      }
   }

}
