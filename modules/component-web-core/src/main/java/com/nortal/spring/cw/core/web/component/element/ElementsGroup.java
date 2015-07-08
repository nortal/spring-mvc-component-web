package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nortal.spring.cw.core.web.component.GenericElement;

/**
 * Hoidla vormi elementide jaoks.
 * 
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 08.01.2014
 */
public class ElementsGroup<T extends GenericElement> implements Serializable {

   private static final long serialVersionUID = 1L;
   private final Map<String, T> elements = new LinkedHashMap<>();

   /**
    * Lisab elemendi elementide gruppi. Grupis olevate elementide fieldPathid peavad olema unikaalsed.
    * 
    * @param element
    */
   public void add(T element) {
      elements.put(element.getId(), element);
   }

   /**
    * Tagastab grupist elemendi või nulli.
    * 
    * @param id
    * @return
    */
   public T getById(String id) {
      return elements.get(id);
   }

   /**
    * Tagastab listi kõikidest grupis olevatest elementidest
    * 
    * @return
    */
   public List<T> getList() {
      return new ArrayList<T>(elements.values());
   }

   /**
    * Võimaldab küsida grupist elementi. Küsimine järgib lisamise järjekorda.
    * 
    * @param index
    * @return
    */
   public T getByIndex(int index) {
      return getList().get(index);
   }

   /**
    * Lisa gruppi listi elemente. Kõik eelmised elemendid eemaldatakse.
    * 
    * @param rowElements
    */
   public void setElements(List<T> rowElements) {
      elements.clear();
      for (T element : rowElements) {
         add(element);
      }
   }

   /**
    * Eemaldab elemendi grupist elemendi fieldPathi järgi.
    * 
    * @param fieldPath
    */
   public void removeByPath(String fieldPath) {
      elements.remove(fieldPath);
   }
}