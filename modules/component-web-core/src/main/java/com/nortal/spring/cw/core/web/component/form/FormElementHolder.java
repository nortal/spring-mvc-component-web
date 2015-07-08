package com.nortal.spring.cw.core.web.component.form;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * Erinevate vormi elementide hoidla. Kasutatakse {@link FormElementHolderMapItem} ja {@link FormElement} hoidmiseks mapis. Mõlemad objektid
 * hoiavad endas elemente {@link FormElement}. Käesolev klass lihtsustab elementidega toimetamist.
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 24.03.2014
 */
public class FormElementHolder implements Serializable {

   private static final long serialVersionUID = 1L;
   private final Map<String, FormElementHolderMapItem> holderElements = new LinkedHashMap<>();
   private final Map<String, FormElement> elements = new LinkedHashMap<>();

   public FormElement put(FormElement element, ElementPath parentElementPath) {
      String id = element.getId();
      if (holderElements.containsKey(element.getId())) {
         id += holderElements.size();
      }
      holderElements.put(id, new FormElementHolderMapItem(element, parentElementPath));
      elements.put(id, element);
      return element;
   }

   public void removeByKey(String key) {
      holderElements.remove(key);
      elements.remove(key);
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {
      return holderElements;
   }

   public Map<String, FormElement> getElements() {
      return elements;
   }

   public void clear() {
      holderElements.clear();
      elements.clear();
   }
}
