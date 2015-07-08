package com.nortal.spring.cw.core.web.component.constraint;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.element.AbstractConstraint;
import com.nortal.spring.cw.core.web.component.element.FieldElementUtil;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * Constraint for setting that one of the elements is mandatory. This constraint must be set to an element on which we want the error
 * message to show up.
 * 
 * List of elements must also contain the element itself when it is one of the elements that has to be mandatory
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 21.10.2013
 */
public class ConstraintOneOfTheseIsMandatory extends AbstractConstraint {
   private static final long serialVersionUID = 1L;

   private String[] formElementIds;

   public ConstraintOneOfTheseIsMandatory(String... formElementIds) {
      this.formElementIds = formElementIds;
   }

   @Override
   public void check() {
      if (formElementIds == null) {
         return;
      }

      for (String path : formElementIds) {
         FormElement element = getElementById(path);
         if (!FieldElementUtil.isFieldEmpty((FormDataElement) FieldElementUtil.findActiveListRowElementByElement(element))) {
            return;
         }
      }

      List<String> mandatoryTranslations = new ArrayList<String>();
      for (String path : formElementIds) {
         FormElement element = getElementById(path);
         mandatoryTranslations.add("\"" + translate(element.getFullLabel()) + "\"");
      }
      getElementById(formElementIds[0]).addElementErrorMessage("one.of.these.has.to.be.filled",
            StringUtils.join(mandatoryTranslations.toArray(), ", "));

   }
}