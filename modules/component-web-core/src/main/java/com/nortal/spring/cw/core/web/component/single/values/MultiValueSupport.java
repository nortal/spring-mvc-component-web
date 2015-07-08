package com.nortal.spring.cw.core.web.component.single.values;

import java.util.Map;

/**
 * Liidest kasutatakse vormi elementide juures, kus on vaja telitada väärtuste mitmikvalikut. Liidese põhi objektiks on {@link MultiValue},
 * mis sisaldab üldist loetelu väärtustest. Antud liidest peavad implementeerima kõik vormi elemendid, kus on vaja kuvada ühe elemendi
 * juures rohkem valitavaid väärtusi.
 * 
 * @author Margus Hanni
 * @author Alrik Peets
 * @since 11.03.2013
 */
public interface MultiValueSupport {

   /**
    * Meetod tagstab liidese, mida kasutatakse vormi elementide juures, kus on vajalik kuvada mitmikvalikuid.
    * 
    * @return
    */
   MultiValue getMultiValue();

   /**
    * Mitmikväärtst sisaldav objekt
    * 
    * @param multiValue
    */
   void setMultiValue(MultiValue multiValue);

   /**
    * Kas tegemist on multiväärtuse valikuga, kus on lubatud valida mitu väärtust korraga. Kui meetod tagastab false, Vastasel juhul
    * kuvatakse loetelu, kuid valida on lubatud neist vaid üks
    * 
    * @return
    */
   boolean isMultiValueElement();

   /**
    * Meetod tagatsab multivaliku väärtused. Võtmeks on väärtus, mis edastatakse vormi saatmisel, kui see on valitud. Väärtuseks on välja
    * kuvatav objekt
    * 
    * @return
    */
   Map<Object, MultiValueElement> getMultiValues();

}
