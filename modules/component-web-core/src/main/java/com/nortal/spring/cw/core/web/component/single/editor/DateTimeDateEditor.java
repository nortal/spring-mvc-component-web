package com.nortal.spring.cw.core.web.component.single.editor;

import java.beans.PropertyEditorSupport;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.util.date.DateObject;
import com.nortal.spring.cw.core.util.date.DateTime;
import com.nortal.spring.cw.core.util.date.SimpleDate;
import com.nortal.spring.cw.core.util.date.Time;

/**
 * Tegemist on klassiga, mis tegeleb vormilt saadetud kuupäeva väärtuse konvertimisega tekstilisest väärtusest objektiks ning väärtuse
 * kuvamisel objektist tekstiliseks väärtuseks. Antud klass saab aru kas sisendina olev väärtuse näol on tegemist kellaajaga, kuupäevaga või
 * kuupäevaga koos kellaajaga. Vastavalt ajatüübile koostatakse erinevad ajaobjektid. Ajaobjektid implementeerivad objekti
 * {@link DateObject}. Tüübi tuvastamine toimub sisendteksti pikkuse järgi<br>
 * Kasutusel on järgnevad ajaobjektid: <br>
 * kellaaeg, sisendteksti pikkuseks on 5 sümbolit: {@link Time}<br>
 * kuupäev, sisendteksti pikkuseks on 10 sümbolit: {@link SimpleDate}<br>
 * kuupäev koos kellaajaga, kõik ülejäänud: {@link DateTime}<br>
 * 
 * 
 * @author Margus Hanni
 * @since 06.03.2013
 */
public class DateTimeDateEditor extends PropertyEditorSupport {

   /**
    * Tulemus lisatakse konkreetse vormi elemendi väärtuseks
    * 
    * @param text
    *           Konverditav tektiline väärtus
    */
   @Override
   public void setAsText(String text) throws IllegalArgumentException {

      int len = StringUtils.length(text);

      if (StringUtils.isEmpty(text) || StringUtils.containsAny(text, "00.00.0000", "00.00.0000 00:00") || len == 6
            && StringUtils.equals(text, " 00:00")) {
         setValue(null);
      } else {
         try {
            if (len == 5) {
               setValue(new Time(text));
            } else if (len == 10) {
               setValue(new SimpleDate(text));
            } else {
               setValue(new DateTime(text));
            }
         } catch (Exception ex) {
            throw new IllegalArgumentException("Could not parse datetime: " + ex.getMessage(), ex);
         }
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

      return ((DateObject) getValue()).getAsText();
   }
}
