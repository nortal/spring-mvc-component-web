package com.nortal.spring.cw.core.web.component.page;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.Assert;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;

/**
 * 
 * @author margush
 */
public class PageButtons implements ElementPath {

   private static final long serialVersionUID = 1L;

   private final Map<String, FormElementHolderMapItem> buttons = new LinkedHashMap<>();
   private String path;
   private ElementPath parentElementPath;

   public PageButtons(String path, ElementPath parentElementPath) {
      this.path = path;
      this.parentElementPath = parentElementPath;
   }

   public void add(FormElement element) {
      Assert.isInstanceOf(EventElement.class, element);
      element.setId(String.valueOf(buttons.size()));
      element.setParentElementPath(this);
      buttons.put(element.getId(), new FormElementHolderMapItem(element, this));
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {
      return MapUtils.unmodifiableMap(buttons);
   }

   @Override
   public String getPath() {
      return path;
   }

   @Override
   public ElementPath getParentElementPath() {
      return parentElementPath;
   }

   @Override
   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;
   }

   public void clear() {
      buttons.clear();
   }
}
