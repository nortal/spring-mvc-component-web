package com.nortal.spring.cw.core.web.component.list;

import java.util.ArrayList;
import java.util.List;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Listi komponendi lisatavad read
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 08.04.2014
 */
public class NewRow implements ElementPath {

   private static final long serialVersionUID = 1L;
   private final List<ListRow> listRows = new ArrayList<>();
   private ElementPath parentElementPath;

   public NewRow(ListRow listRow, ElementPath parentElementPath) {
      listRow.setParentElementPath(this);
      listRows.add(listRow);
      this.parentElementPath = parentElementPath;
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

   public List<ListRow> getListRow() {
      return listRows;
   }
}
