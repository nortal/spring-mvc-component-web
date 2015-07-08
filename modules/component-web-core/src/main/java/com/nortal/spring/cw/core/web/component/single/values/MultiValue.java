package com.nortal.spring.cw.core.web.component.single.values;

import java.util.Map;

import com.nortal.spring.cw.core.web.component.element.SelectElementType;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueHolder.MultiValueValueFilter;

/**
 * Tegemist on liidesega, mida kasutatakse vormi elementide juures, kus on vajalik kuvada mitmikvalikuid. Toetatud on vormi elemendid, mis
 * on kirjeldatud enumis {@link SelectElementType}. Valitud väärtuseid hoitakse kollektsioonis.
 * 
 * 
 */
public interface MultiValue {

   String SEPARATOR = "--------------------------";

   /**
    * Mitmikväärtust hoidva vormi elemendi tüüp
    * 
    * @return
    */
   SelectElementType getElementType();

   /**
    * Vormielemendi stiili klass
    * 
    * @return
    */
   String getCssClass();

   /**
    * Meetod tagatsab filtreeritud multivaliku väärtused. Võtmeks on väärtus, mis edastatakse vormi saatmisel, kui see on valitud ning
    * väärtuseks on välja kuvatav objekt
    * 
    * @return
    */
   Map<Object, MultiValueElement> getFilteredValues();

   /**
    * Kas tegemist on multiväärtuse valikuga, kus on lubatud valida mitu väärtust korraga. Kui meetod tagastab false, Vastasel juhul
    * kuvatakse loetelu, kuid valida on lubatud neist vaid üks
    * 
    * @return
    */
   boolean isMultiSelectAllowed();

   /**
    * Määrab ära keelatud väärtused
    * 
    * @param values
    */
   void setDisabledByKey(Object... values);

   /**
    * Määrab ära lubatud väärtused
    * 
    * @param values
    */
   void setEnableByKey(Object... keys);

   /**
    * Tagastab võtmele vastava väärtuse
    * 
    * @param key
    * @return
    */
   String getValue(Object key);

   /**
    * Määrab valikute filtri
    * 
    * @param filter
    */
   <T> void setMultiValueFilter(MultiValueValueFilter<T> filter);

}
