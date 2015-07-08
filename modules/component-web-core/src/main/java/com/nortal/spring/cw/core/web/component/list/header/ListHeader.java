package com.nortal.spring.cw.core.web.component.list.header;

import lombok.Data;

import org.apache.commons.lang3.tuple.Pair;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.ElementPathList;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * @author Margus Hanni
 * @since 23.02.2013
 */
@Data
public class ListHeader implements ElementPathList {

   private static final long serialVersionUID = 1L;
   private FormElement element;
   private boolean sortable;
   private boolean filterable;
   private ListFilter customFilter;
   private Pair<BetweenHolder, BetweenHolder> between;
   private boolean allowFilterRange;
   private final int cellNr;
   private ElementPath parentElementPath;

   public ListHeader(Hierarchical parent, FormElement element, boolean sortable, boolean filterable, final int cellNr) {
      this.parentElementPath = parent;
      this.element = element;
      this.sortable = sortable;
      this.filterable = filterable;
      this.between = Pair.of(new BetweenHolder(element, this, true), new BetweenHolder(element, this, false));
      this.allowFilterRange = allowFilterRange(element.getElementType());
      this.cellNr = cellNr;
   }

   public FormElement getElement() {
      return element;
   }

   public void initComponent() {

      this.element = BeanUtil.clone(this.element);
      this.element.setParent((Hierarchical) parentElementPath);
      this.element.setParentElementPath(this);

      this.between = Pair.of(new BetweenHolder(BeanUtil.clone(this.between.getLeft().getElement()), this, true),
            new BetweenHolder(BeanUtil.clone(this.between.getRight().getElement()), this, false));

      // filtrite juures puudub vajadus sisestuse AJAXi vahendusel
      // valideerimiseks
      if (this.element instanceof FormDataElement) {
         ((FormDataElement) this.element).setUseAjaxValidation(false);
         ((FormDataElement) this.between.getLeft().getElement()).setUseAjaxValidation(false);
         ((FormDataElement) this.between.getRight().getElement()).setUseAjaxValidation(false);
      }

      if (element instanceof AbstractBaseElement) {
         ((AbstractBaseElement<?>) element).setMandatory(false);
      }
   }

   private boolean allowFilterRange(FieldElementType elementType) {
      switch (elementType) {
         case DATETIME:
         case DOUBLE:
         case INTEGER:
         case LONG:
            return true;
         default:
            return false;
      }
   }

   @Override
   public String getPath() {
      return ElementUtil.getNameForFullPath(this);
   }

   @Override
   public int getPathIndex() {
      return getCellNr();
   }

   @Override
   public ElementPath getParentElementPath() {
      return parentElementPath;
   }

   @Override
   public void setParentElementPath(ElementPath parentElementPath) {
      this.parentElementPath = parentElementPath;
   }

   public static final class BetweenHolder implements ElementPath {

      private static final long serialVersionUID = 1L;
      private final FormElement element;
      private final boolean first;
      private ElementPath parentElementPath;

      public BetweenHolder(FormElement element, ElementPath parentElementPath, boolean first) {
         this.element = element;
         this.element.setParentElementPath(this);
         this.parentElementPath = parentElementPath;
         this.first = first;
      }

      public FormElement getElement() {
         return element;
      }

      @Override
      public ElementPath getParentElementPath() {
         return parentElementPath;
      }

      @Override
      public void setParentElementPath(ElementPath elementPath) {
         this.parentElementPath = elementPath;
      }

      @Override
      public String getPath() {
         return "between." + (this.first ? "left" : "right");
      }
   }
}
