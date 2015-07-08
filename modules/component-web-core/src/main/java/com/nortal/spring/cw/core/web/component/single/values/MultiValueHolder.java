package com.nortal.spring.cw.core.web.component.single.values;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.nortal.spring.cw.core.web.component.element.SelectElementType;

/**
 * Antud klass implementeerib liidest {@link MultiValue}, mis on mõeldud esitluskihis mitmikväärtuste kuvamiseks. Tegemist on klassiga, mis
 * abistab vormi elementide loomist mitmikväärtuste hoidmiseks. Klassil on konstruktsioonid, mis lubavad anda ette oma enda defineeritud
 * {@link Map} kui ka määrata ära {@link BaseDomainValue} tüüpi objekt, mis tähendab seda, et antud loendi väärtused leitakse automaatselt
 * rakenduse mälust.<br>
 * Täiendavalt on võimalik erinevaid loendi väärtuseid välistada või just lubada, kasutades selleks {@link MultiValueValueFilter} pakutavaid
 * võimalusi võib määrata ära kindlad väärtused mida lubatakse kasutada <br>
 * Andmete hoidmiseks, kasutatakse {@link Map}, kus võtmeks on konkreetne objekt, mis peale valikut kirjutatakse tagasi <code>entity</code>
 * külge ning value on väärtus, mis kuvatakse välja valiku tekstina
 * 
 */
public class MultiValueHolder implements MultiValue, Serializable {

   private static final long serialVersionUID = 1L;

   private SelectElementType type;
   private boolean multiSelectAllowed;
   private Map<Object, MultiValueElement> valueMap;
   private MultiValueValueFilter filter;

   /**
    * Konstruktsioon tekitab multiväärtuse objekti, mis sisaldab ette antud väärtusi
    * 
    * @param type
    *           Multiväärtusi sisaldava elemendi tüüp
    * @param values
    *           Multiväärtused, kus võtmeks on konkreetne objekt, mis peale valikut kirjutatakse tagasi <code>entity</code> külge ning value
    *           on väärtus, mis kuvatakse välja valiku tekstina
    */
   public MultiValueHolder(SelectElementType type, Map<Object, Object> values) {
      this(type, values, null);
   }

   /**
    * Konstruktsioon tekitab multiväärtuse objekti, mis sisaldab ette antud väärtusi
    * 
    * @param type
    *           Multiväärtusi sisaldava elemendi tüüp
    * @param values
    *           Multiväärtused, kus võtmeks on konkreetne objekt, mis peale valikut kirjutatakse tagasi <code>entity</code> külge ning value
    *           on väärtus, mis kuvatakse välja valiku tekstina
    * @param domainValueFilter
    *           Väärtuste kuvamise filter
    */
   public MultiValueHolder(SelectElementType type, Map<Object, Object> values, MultiValueValueFilter<Object> filter) {
      this.type = type;
      this.multiSelectAllowed = type.isMultiSelect();
      this.setMultiValueFilter(filter);
      valueMap = convertValues(values);
   }

   /**
    * Konstruktsioon tekitab multiväärtuse objekti, mis sisaldab ette antud väärtusi. Täiendavalt kasutatakse eeldefineeritud
    * loendiväärtuste kuvamise filtrit
    * 
    * @param type
    * @param domainValueFilter
    */
   public <T> MultiValueHolder(SelectElementType type, MultiValueValueFilter<T> filter) {
      this.type = type;
      this.multiSelectAllowed = type.isMultiSelect();
      this.setMultiValueFilter(filter);
   }

   @Override
   public SelectElementType getElementType() {
      return type;
   }

   @Override
   public boolean isMultiSelectAllowed() {
      return multiSelectAllowed;
   }

   @Override
   public String getCssClass() {
      return type.getCssClass();
   }

   @Override
   public Map<Object, MultiValueElement> getFilteredValues() {
      return filter != null ? filter.getFilteredValues(this.valueMap) : this.valueMap;
   }

   @Override
   public void setDisabledByKey(Object... keys) {
      for (Object key : keys) {
         MultiValueElement value = this.valueMap.get(key);
         if (value != null) {
            value.setDisabled(true);
         }
      }
   }

   @Override
   public void setEnableByKey(Object... keys) {
      for (Object key : keys) {
         MultiValueElement value = this.valueMap.get(key);
         if (value != null) {
            value.setDisabled(false);
         }
      }
   }

   private Map<Object, MultiValueElement> convertValues(Map<Object, Object> values) {
      Map<Object, MultiValueElement> map = new LinkedHashMap<Object, MultiValueElement>();
      for (Entry<Object, Object> value : values.entrySet()) {
         map.put(value.getKey(), new MultiValueElement(value.getValue()));
      }
      return map;
   }

   @Override
   public String getValue(Object key) {
      String result = null;
      if (valueMap.containsKey(key)) {
         MultiValueElement mve = valueMap.get(key);

         result = mve.getValue() != null ? mve.getValue().toString() : null;

      }
      return result;
   }

   @Override
   @SuppressWarnings("unchecked")
   public <T> void setMultiValueFilter(MultiValueValueFilter<T> filter) {
      this.filter = (MultiValueValueFilter<Object>) filter;
   }

   /**
    * Custom converter for domain values. Input is taken from cache based on the target domainvalue type.
    * 
    * @author Alrik Peets
    */
   public interface MultiValueValueFilter<T> {

      /**
       * Väljundiks on filtreeritud väärtuste nimekiri
       * 
       * @param valueMap
       *           Väärtuste nimekiri
       * @return
       */
      Map<T, MultiValueElement> getFilteredValues(Map<T, MultiValueElement> valueMap);

   }
}
