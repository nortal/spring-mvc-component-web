package com.nortal.spring.cw.core.web.component.single.values;

import java.io.Serializable;

/**
 * Tegemist {@link MultiValue} elemendiga, mida kasutatakse valikulise väärtuse kuvamisel.
 * 
 * @author Margus Hanni
 * @since 11.12.2013
 */
public class MultiValueElement implements Serializable {

   private static final long serialVersionUID = 1L;
   public static final MultiValueElement SEPARATOR = new MultiValueElement(MultiValueHolder.SEPARATOR, true);

   private Object value;
   private boolean disabled;

   public MultiValueElement(Object value) {
      this.setValue(value);
   }

   public MultiValueElement(Object value, boolean disabled) {
      this.setValue(value);
      this.disabled = disabled;
   }

   /**
    * Väljakuvatav väärtus
    * 
    * @return {@link Object}
    */
   public Object getValue() {
      return value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   /**
    * Kas konkreetne väärtus on lubatud või keelatud
    * 
    * @return {@link Boolean}
    */
   public boolean isDisabled() {
      return disabled;
   }

   public void setDisabled(boolean disabled) {
      this.disabled = disabled;
   }

   /**
    * Et JSP ei peaks ekstra välja kutsuma {@link MultiValueElement#value} siis kirjutame üle {@link MultiValueElement#toString()} meetodi
    */
   @Override
   public String toString() {
      return value == null ? null : value.toString();
   }
}
