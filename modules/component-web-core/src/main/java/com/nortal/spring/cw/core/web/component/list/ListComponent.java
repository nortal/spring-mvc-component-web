package com.nortal.spring.cw.core.web.component.list;

import java.util.List;

import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.simple.FormComponent;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.header.ListHeader;
import com.nortal.spring.cw.core.web.component.list.holder.EpmPagedListHolder;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.component.list.row.ListRowBuilder;

/**
 * @author Margus Hanni
 * @since 23.02.2013
 */
public interface ListComponent extends FormComponent {

   public static final String SUBMIT_LIST_COMPONENT = "submitListComponent";
   public static final String SUBMIT_LIST_ELEMENT_DISPLAY_ID = "submitListElementDisplayId";
   public static final String SUBMIT_LIST_SORT = "submitListTableSort";
   public static final String SUBMIT_LIST_FILTER = "submitListTableFilter";
   public static final String SUBMIT_LIST_PAGINATION = "submitListTablePagination";
   public static final String SUBMIT_LIST_ADDROW = "submitListTableAddRow";

   /**
    * Aktiivsel lehel olevad andmed
    * 
    * @return
    */
   <T> List<T> getActivePageDataList();

   /**
    * Tagastame kõik objektid ka need, mida visuaalselt ei näidatud, ehk sellised mis asusid mõnel teisel lehel.
    * 
    * @return
    */
   <T> List<T> getDataList();

   /**
    * Tabeli seisu uuendamine, ei kutsuta välja DataSource objekti. Väljakutsutavad sisemised meetodid: filter, resort, pagination,
    * refreshIds
    */
   void refresh();

   /**
    * Tabeli seisu uuendamine, kutsutakse välja ka DataSource objekt. Väljakutsutavad sisemised meetodid: filter, resort, pagination,
    * refreshIds
    */
   void refreshData();

   ListProperties getProperties();

   FormElement getFormElement(int rowId, int cellId);

   <T> T getListRowEntity(int rowId);

   /**
    * Meetod tagastab kõik uued lisandunud read, mis ei ole veel andmebaasi maha salvestatud
    * 
    * @return {@link List}
    */
   <T> List<T> getNewRowDataList();

   ElementHandler getAddNewRowHandler();

   /**
    * Listi rea kustutamise händler
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getDeleteRowHandler();

   /**
    * Listi rea muutmise händler
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getEditRowHandler();

   /**
    * Aktiivne element, mis on seotud viimase sündmustega, näiteks on tegu elemendiga mis kutsus välja onClick sündmuse
    * 
    * @return
    */
   public FormElement getActiveFormElement();

   /**
    * @param activeFormElement
    */
   public void setActiveFormElement(FormElement activeFormElement);

   /**
    * Aktiivne objekt, mis on seotud viimase sündmustega, näiteks on tegu objektiga, mis on seotud sündmuse välja kutsunud elemendiga
    * (kustutamine, lisamine, linkimine)
    * 
    * @return
    */
   public <T> T getActiveEntity();

   /**
    * @param activeObject
    */
   public void setActiveEntity(Object activeObject);

   /**
    * Listi ridu koostav komponent
    * 
    * @param listRowBuilder
    */
   <T> void setListRowBuilder(ListRowBuilder<T> listRowBuilder);

   /**
    * Aktiivse listi rea määramine. Meetod kutsutakse välja erinevate listi rea põistel toimingutel, nagu rea andmete muutmisel või
    * kustutamisel
    * 
    * @param listRow
    *           {@link ListRow}
    */
   void setActiveListRow(ListRow listRow);

   /**
    * Meetod tagastab aktiivse listi rea, mis muutus aktiivseks erinevate listi rea põistel toimingutel, nagu rea andmete muutmisel või
    * kustutamisel
    * 
    * @return {@link ListRow}
    */
   ListRow getActiveListRow();

   /**
    * Meetod tagastab <i>true</i> juhul kui list ei sisalda andmeid.<br>
    * Kontrollitakse nii eeldefineeritud andmeid läbi {@link ListComponent#getDataList()} ning täiendavalt kontrollitakse uusi ridu, mis on
    * kasutaja poolt täiendavalt lisatud läbi
    * 
    * @return {@link Boolean}
    */
   boolean isEmpty();

   /**
    * Meetod tagastab {@link EpmPagedListHolder}, hoiab endas listi ridu millega tehakse erinevaid toiminguid nagu andmete lehtedeks
    * tükeldamine, andmete järjestamine ning andmete filtreerimine.
    * 
    * @return {@link EpmPagedListHolder}
    */
   <T> EpmPagedListHolder<T> getPagedListHolder();

   List<ListHeader> getListHeader();

   List<ListRow> getListRow();

   ListRow refreshRow(ListRow listRow, int rowNumber);

   ListRow buildRow(Object data, int rowNumber);
}
