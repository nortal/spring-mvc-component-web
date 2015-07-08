package com.nortal.spring.cw.core.web.component.constraint;

import com.nortal.spring.cw.core.web.component.form.EpmFormComponent;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;

/**
 * Vormi {@link EpmFormComponent} ja listi {@link EpmListComponent} kahe kuupäeva välja võrdlemine. Kontrollitakse kas aktiivne väli ei ole
 * suurem võrreldavast väljast
 * 
 * @author Margus Hanni
 * 
 */
public class ConstraintTwoDate extends ConstraintCompareOneToAnother {
   private static final long serialVersionUID = 1L;

   public ConstraintTwoDate(String active, String compare) {
      super(active, compare, ComparisonType.LESS_OR_EQUAL);
   }

   @Override
   protected void addErrorMsg(ComparisonType type) {
      getComparable()
            .addElementErrorMessage("field.datetime.error.constraint.value-bigger-than", translate(getComparedTo().getFullLabel()));
   }
}