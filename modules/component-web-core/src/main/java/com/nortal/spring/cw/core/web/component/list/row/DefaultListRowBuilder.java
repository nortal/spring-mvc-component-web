package com.nortal.spring.cw.core.web.component.list.row;

import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;

import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;
import com.nortal.spring.cw.core.web.util.BeanUtil;

/**
 * Vaikimisi ListRow ehitamine
 * 
 * @author Alrik Peets
 */
public class DefaultListRowBuilder<T> implements Serializable, ListRowBuilder<T> {
   private static final long serialVersionUID = 1L;

   public static final ListElementBuildStrategy normalElementBuildStrategy = new NormalListElementBuildStrategy();
   public static final ListElementBuildStrategy newElementBuildStrategy = new NewListElementBuildStrategy();

   /**
    * Listi rea loomine. Vastavalt lisatud elementide arvule ning järjestusele koostatakse listi uus rida ning reale omistatakse vastavalt
    * identifikaator ehk path. Lisaks omistatakse rea iga elemendile samuti path, mille alusel leiab vajadusel elemendi üles. MVC leiab
    * elemendi hoidlast üles vastavalt path väärtusele ning omistab elemendile läbi vormi sisestatud väärtuse. Kui listi komponendis (@link
    * {@link EpmListComponent}) on määratud rea toimingute nupud, siis need kloonitakse ning lisatakse rea elemendi külge. Vajadusel saab
    * rea ehitamisest kohendada, kasutades selleks meetodit customizeListRow
    */
   @Override
   public ListRow buildRow(final Object entity, EpmListComponent<T> list, final ListElementBuildStrategy buildStrategy, final int rowNumber) {
      ListRow row = new ListRow(entity, list);
      row.setEditable(list.isEditable());
      row.setRowNumber(rowNumber);

      for (Entry<String, FormElementHolderMapItem> holder : list.getRowCustomActionButtonsMap().entrySet()) {
         final EventElement eventElement = BeanUtil.clone(holder.getValue().getElement());
         eventElement.setParentElementPath(row);

         if (eventElement.getParent() == null) {
            eventElement.setParent(list);
         }

         row.getListRowButtons().addEventButton(eventElement);
      }

      row.getListRowButtons().allowEditButton(list.getProperties().isAllowEditButton());
      row.getListRowButtons().allowDeleteButton(list.getProperties().isAllowDeleteButton());

      List<FormElement> formElements = buildStrategy.getCloneableListElements(list);
      for (int i = 0; i < formElements.size(); i++) {
         FormElement localElement = BeanUtil.clone(formElements.get(i));

         if (localElement.getParent() == null) {
            localElement.setParent(list);
         }

         final int cellNr = i;
         if (localElement instanceof FormDataElement) {
            buildStrategy.modifyElement(entity, localElement);
         }

         RowCell cell = new RowCell(row, localElement, cellNr);
         row.getElementsGroup().add(cell);
      }

      return row;
   }

   @Override
   public ListRow refreshRow(final ListElementBuildStrategy buildStrategy, final ListRow listRow, final int rowNumber) {
      listRow.setRowNumber(rowNumber);

      for (int i = 0; i < listRow.getRowCell().size(); i++) {
         final RowCell cell = listRow.getRowCell().get(i);
         FormElement formElement = cell.getElement();
         customizeListElement(formElement);
      }

      customizeListRow(listRow);

      return listRow;
   }

   // = Extension points for modifying ListRow and ListElement construction =
   /**
    * Called after ListRow is created and initialized and before any elements are added
    * 
    * @param listRow
    */
   public void customizeListRow(ListRow listRow) {
      // By default we do nothing
   }

   /**
    * Called after element is created, but is not yet added to the ListRow
    * 
    * @param element
    */
   public void customizeListElement(FormElement element) {
      // By default do nothing
   }
}
