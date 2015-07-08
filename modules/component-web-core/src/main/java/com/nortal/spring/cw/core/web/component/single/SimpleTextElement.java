package com.nortal.spring.cw.core.web.component.single;

import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;

/**
 * Tegemist on elemendiga, mis hoiab enda sees lihtteksti. Sisuliselt on tegemist standardelemendiga, selle erandiga et elemendi väärtus ei
 * ole muudetav
 * 
 * @author Margus Hanni
 * 
 */
public class SimpleTextElement extends AbstractBaseElement<String> {

   private static final long serialVersionUID = 1L;

   /**
    * Elemendi konstruktor
    * 
    * @param elementPath
    *           elemendi rada
    * @param value
    *           Elemendi väärtus. Lubatud on lisada nii tõlkekood kui ka tavalist teksti.
    */
   public SimpleTextElement(String elementPath, String value) {
      String message = getMessageSource().resolveByActiveLang(value);
      setRawValue(message == null ? value : message);
      setId(elementPath);
      setCssClass(FieldElementCssClass.INFO);
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.SIMPLE_TEXT;
   }

   @Override
   public String getDisplayValue() {
      return getValue();
   }

   @Override
   public int compareTo(Object o) {
      if (!(o instanceof SimpleTextElement)) {
         return 0;
      }

      return getDisplayValue().compareTo(((SimpleTextElement) o).getDisplayValue());
   }

   /**
    * Antud elemendil ei saa olla mudelobjekti, seega ignoreerime automaatset protsessi
    */
   @Override
   public void setEntity(Object entity) {
      super.setEntity(null);
   }

}
