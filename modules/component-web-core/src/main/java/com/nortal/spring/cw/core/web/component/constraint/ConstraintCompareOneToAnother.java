package com.nortal.spring.cw.core.web.component.constraint;

import com.nortal.spring.cw.core.web.component.element.AbstractConstraint;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;

/**
 * Ühe välja väärtuse võrdlemiseks teise välja väärtusega. Saab ette anda oodatava välja väärtuste suhte.
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 06.11.2013
 */
public class ConstraintCompareOneToAnother extends AbstractConstraint {

   private static final long serialVersionUID = 1L;
   private String comparablePath;
   private String comparedToPath;
   private ComparisonType comparisonType;
   private String customTranslationCode;

   public ConstraintCompareOneToAnother(String comparablePath, String comparedToPath, ComparisonType comparisonType) {
      this.comparablePath = comparablePath;
      this.comparedToPath = comparedToPath;
      this.comparisonType = comparisonType;
   }

   @Override
   public void check() {
      FormDataElement comparable = getComparable();
      FormDataElement comparedTo = getComparedTo();

      if (comparable.getRawValue() != null && comparedTo.getRawValue() != null) {
         switch (comparisonType) {
            case LESS:
               if (comparable.compareTo(comparedTo) >= 0) {
                  addErrorMsg(comparisonType);
               }
               break;
            case LESS_OR_EQUAL:
               if (comparable.compareTo(comparedTo) > 0) {
                  addErrorMsg(comparisonType);
               }
               break;
            case EQUAL:
               if (comparable.compareTo(comparedTo) != 0) {
                  addErrorMsg(comparisonType);
               }
               break;
            case GREATER_OR_EQUAL:
               if (comparable.compareTo(comparedTo) < 0) {
                  addErrorMsg(comparisonType);
               }
               break;
            case GREATER:
               if (comparable.compareTo(comparedTo) <= 0) {
                  addErrorMsg(comparisonType);
               }
               break;
         }
      }
   }

   protected void addErrorMsg(ComparisonType type) {
      getComparable().addElementErrorMessage(customTranslationCode == null ? type.getErrorTranslationCode() : customTranslationCode,
            translate(getComparedTo().getFullLabel()));
   }

   public void setCustomTranslationCode(String customTranslationCode) {
      this.customTranslationCode = customTranslationCode;
   }

   protected FormDataElement getComparable() {
      return (FormDataElement) getElementById(this.comparablePath);
   }

   protected FormDataElement getComparedTo() {
      return (FormDataElement) getElementById(this.comparedToPath);
   }

   public static enum ComparisonType {
      // @formatter:off
      LESS("field.error.constraint.value-less-than"), 
      LESS_OR_EQUAL("field.error.constraint.value-less-than-or-equal"), 
      EQUAL("field.error.constraint.value-equal"), 
      GREATER_OR_EQUAL("field.error.constraint.value-greater-or-equal"), 
      GREATER("field.error.constraint.value-greater");
      // @formatter:on

      private String errorTranslationCode;

      private ComparisonType(String errorTranslationCode) {
         this.errorTranslationCode = errorTranslationCode;
      }

      public String getErrorTranslationCode() {
         return errorTranslationCode;
      }
   }
}