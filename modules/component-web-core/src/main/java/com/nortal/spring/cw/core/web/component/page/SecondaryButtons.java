package com.nortal.spring.cw.core.web.component.page;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.Assert;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;
import com.nortal.spring.cw.core.web.util.ElementUtil;

public class SecondaryButtons implements ElementPath {

   private static final long serialVersionUID = 1L;

   private final Map<String, FormElementHolderMapItem> buttons = new LinkedHashMap<>();
   private ElementPath parentElementPath;

   public SecondaryButtons(ElementPath parentElementPath) {
      this.parentElementPath = parentElementPath;
   }

   public void add(FormElement element) {
      Assert.isInstanceOf(EventElement.class, element);
      element.setParentElementPath(this);
      buttons.put(element.getId(), new FormElementHolderMapItem(element, this));
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {
      return MapUtils.unmodifiableMap(buttons);
   }

   @Override
   public String getPath() {
      return ElementUtil.getNameForFullPath(this);
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
