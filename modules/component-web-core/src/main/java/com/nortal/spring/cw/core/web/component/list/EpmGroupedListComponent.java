package com.nortal.spring.cw.core.web.component.list;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * Tabelite laiendus ridade grupeerimiseks veeru järgi - vaata täpsemalt "Minu andmete" valdkondade kontaktide tabelit. Hetkel realiseeritud
 * "Minu andmete" nõuetest lähtuvalt seega lisafunktsionaalsuse jaoks tuleks see komponent põhjalikumalt läbi mõelda.
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 10.04.2014
 */
@SuppressWarnings("unchecked")
public class EpmGroupedListComponent<T> extends EpmListComponent<T> {
   private static final long serialVersionUID = 1L;

   @Getter
   @Setter(AccessLevel.PROTECTED)
   private String groupElementId;

   public EpmGroupedListComponent(String componentName, Class<T> objectClass) {
      super(componentName, objectClass);
   }

   public EpmGroupedListComponent(String componentName, Class<T> objectClass, String orderedField, boolean sortOrderAsc) {
      super(componentName, objectClass, orderedField, sortOrderAsc);
   }

   /**
    * Veeru kaaridstamine - täiendavalt saab määrata, kas selle veeru järgi toimub grupeerimine
    * 
    * @param elementFieldPath
    * @param groupBy
    * @return
    */
   public <K extends FormElement> K add(String elementFieldPath, boolean groupBy) {
      K result = super.add(elementFieldPath);
      if (groupBy) {
         setGroupElementId(result.getId());
      }
      return result;
   }

   /**
    * Tagastab grupeeritud veeru väärtused
    * 
    * @return
    */
   public List<FormElement> getGroupElements() {
      List<FormElement> result = new ArrayList<>();
      for (ListRow row : getListRow()) {
         result.add(row.getElementsGroup().getById(groupElementId).getElement());
      }
      return result;
   }

   /**
    * Tagastab tabeli read kaardistatud grupeerimise veeru järgi
    * 
    * @return
    */
   public Map<String, ListRow> getGroupedRows() {
      Map<String, ListRow> result = new LinkedHashMap<>();
      for (ListRow row : getListRow()) {
         String value = row.getElementsGroup().getById(groupElementId).getElement().getDisplayValue();
         result.put(value, row);
      }
      return result;
   }
}
