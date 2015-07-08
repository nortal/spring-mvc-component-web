package com.nortal.spring.cw.core.web.component.multiple;

import com.nortal.spring.cw.core.web.component.element.FieldElementType;

/**
 * @author Margus Hanni
 * @since 19.03.2013
 */
public class IntegerCollectionElement extends AbstractNumberCollectionElement<Integer> {

   private static final long serialVersionUID = 1L;

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.INTEGER_COLLECTION;
   }

}
