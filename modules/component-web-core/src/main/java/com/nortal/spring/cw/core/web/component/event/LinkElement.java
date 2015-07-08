package com.nortal.spring.cw.core.web.component.event;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * Standardne lingi element, millel on olemas kõik {@link FormElement} tunnused. Lisaks on võimalik konstruktoris ette anda tõlke argumente.
 * 
 * @author Margus Hanni
 */
public class LinkElement extends AbstractEventElement {

   private static final long serialVersionUID = 1L;
   private static final String ELEMENT_NAME = "link";
   private Object[] labelArgs;

   /**
    * Konstruktor
    */
   public LinkElement() {
      super(ELEMENT_NAME);
   }

   /**
    * Konstruktor
    */
   public LinkElement(String elementName) {
      super(elementName);
   }

   /**
    * Konstruktor koos täiendavate argumentidega
    * 
    * @param eventName
    *           Väljakutsutava sündmuse nimi
    * @param label
    *           Tõlkekood
    * @param labelArgs
    *           Tõlke argumendid
    */
   public LinkElement(String eventName, String label, Object... labelArgs) {
      this(ELEMENT_NAME, eventName, label, labelArgs);
   }

   /**
    * @param elementName
    * @param eventName
    * @param label
    * @param labelArgs
    */
   public LinkElement(String elementName, String eventName, String label, Object... labelArgs) {
      super(elementName, label, eventName);
      this.labelArgs = labelArgs;
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.LINK;
   }

   /**
    * Meetod tagastab tõlke argumendid komaga eraldatud kujul. Kui algselt oli argumendiks määratud müni objekt, siis tulemi moodustamiseks
    * kutsutakse välja antud objekti {@link Object#toString()} millega saadakse objekti tekstiline väärtus
    * 
    * @return {@link String}
    */
   public String getLabelArgs() {
      return StringUtils.join(labelArgs, ",");
   }
}
