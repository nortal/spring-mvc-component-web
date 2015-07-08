package com.nortal.spring.cw.jsp.web.tag.form;

import org.springframework.web.servlet.tags.form.ButtonTag;

/**
 * Antud klass täiendab Spring MVC {@link ButtonTag} tekitades võimaluse määramiseks kas on tegu <code>submit</code> või tavalise
 * <code>button</code> nupuga
 * 
 * @author Margus Hanni
 * @since 22.02.2013
 * @jsp.tag name = "button"
 */
public class CwButtonTag extends ButtonTag {

   private static final long serialVersionUID = 1L;

   private boolean isSubmitButton = true;

   public boolean isSubmitButton() {
      return isSubmitButton;
   }

   public void setSubmitButton(boolean isSubmitButton) {
      this.isSubmitButton = isSubmitButton;
   }

   @Override
   protected String getType() {
      return this.isSubmitButton ? super.getType() : "button";
   }

}
