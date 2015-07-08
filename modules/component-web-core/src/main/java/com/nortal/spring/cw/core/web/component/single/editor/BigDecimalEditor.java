package com.nortal.spring.cw.core.web.component.single.editor;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.text.NumberFormat;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;

/**
 * Tegemist on klassiga, mis tegeleb vormilt saadetud reaalarvu konvertimisega stringist objektiks ning väärtuse kuvamisel objektist
 * tekstiliseks väärtuseks. Tekstilise väärtuse konvertimisel on kasutusel {@link NumberFormat#getNumberInstance()}, mis kasutab tekstilise
 * väärtuse konvertimisel regionaalseid seadeid. Konvertimise lõplikuks tulemuseks on {@link BigDecimal}
 * 
 * @author Margus Hanni
 * 
 */
public class BigDecimalEditor extends PropertyEditorSupport {

   /**
    * Tulemus lisatakse konkreetse vormi elemendi väärtuseks
    * 
    * @param text
    *           Konverditav tekstiline väärtus
    */
   @Override
   public void setAsText(String text) throws IllegalArgumentException {
      if (StringUtils.isEmpty(text)) {
         setValue(null);
         return;
      }

      try {
         setValue(new BigDecimal(StringUtils.replace(StringUtils.replace(text, ",", "."), " ", "")));
      } catch (Exception e) {
         throw new AppBaseRuntimeException(e);
      }
   }

   /**
    * Meetod tagastab objekti väärtuse tekstilisel kujul
    */
   @Override
   public String getAsText() {
      if (getValue() == null) {
         return StringUtils.EMPTY;
      }
      return StringUtils.replace(((BigDecimal) getValue()).toPlainString(), ".", ",");
   }
}
