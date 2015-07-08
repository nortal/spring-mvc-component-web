package com.nortal.spring.cw.core.web.component.list.row;

import com.nortal.spring.cw.core.web.component.BaseElement;
import com.nortal.spring.cw.core.web.component.ElementPathList;
import com.nortal.spring.cw.core.web.component.element.FormElement;

public class RowCell extends BaseElement implements ElementPathList {

   private static final long serialVersionUID = 1L;
   private final FormElement element;
   private final int cellNr;

   public RowCell(final ListRow listRow, FormElement element, final int cellNr) {
      super(element.getId());
      this.element = element;
      this.cellNr = cellNr;
      this.setParentElementPath(listRow);
      element.setParentElementPath(this);
   }

   public FormElement getElement() {
      return element;
   }

   public int getCellNr() {
      return cellNr;
   }

   @Override
   public int getPathIndex() {
      return getCellNr();
   }

   @Override
   public void setEditable(boolean editable) {
      super.setEditable(editable);
      getElement().setEditable(editable);
   }

   @Override
   public void setVisible(boolean visible) {
      super.setVisible(visible);
      getElement().setVisible(visible);
   }
}
