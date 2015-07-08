package com.nortal.spring.cw.core.web.component.list.holder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.util.Assert;

import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.EpmListComponent;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.list.ListDataProvider;
import com.nortal.spring.cw.core.web.component.list.ListOrder;
import com.nortal.spring.cw.core.web.component.list.ListPagination;
import com.nortal.spring.cw.core.web.component.list.header.DefaultListFilter;
import com.nortal.spring.cw.core.web.component.list.header.ListFilter;
import com.nortal.spring.cw.core.web.component.list.header.ListHeader;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;

/**
 * Antud klass hoiab listi ridu millega tehakse erinevaid toiminguid nagu andmete lehtedeks tükeldamine, andmete järjestamine ning andmete
 * filtreerimine.
 * 
 * @author Margus Hanni
 */
public class EpmPagedListHolder<T> extends PagedListHolder<ListRow> {
   private static final long serialVersionUID = 1L;

   private ListDataProvider<T> dataProvider;
   private List<ListRow> dataRows;
   private List<ListRow> holder;
   private ListPagination pagination;
   private ListOrder order;
   private ListComponent listComponent;
   private ListFilter listFilter = new DefaultListFilter();

   public EpmPagedListHolder(EpmListComponent<T> listComponent) {
      this.listComponent = listComponent;

      pagination = new ListPagination();
      pagination.setPageNr(1);

      order = new ListOrder();
   }

   /**
    * Meetod tagastab tabeli read
    * 
    * @return {@link List}
    */
   public List<ListRow> getPageListHolder() {

      if (holder == null) {
         renewListHolder();
      }

      return holder;
   }

   /**
    * Uuendame Spring MVC tarbeks elementide raja
    */
   public void renewListHolder() {

      List<ListRow> dataList = getPageList();
      holder = new ArrayList<>(dataList.size());
      holder.addAll(dataList);

      int rowNum = 0;
      for (ListRow listRow : holder) {
         listComponent.refreshRow(listRow, rowNum++);
      }
   }

   /**
    * Meetod tagastab andmeobjekti ehk implementatsiooni andmete pärimiseks
    * 
    * @return {@link ListDataProvider}
    */
   public ListDataProvider<T> getDataProvider() {
      return dataProvider;
   }

   /**
    * Andmeobjekti määramine
    * 
    * @param dataProvider
    */
   public void setDataProvider(ListDataProvider<T> dataProvider) {
      this.dataProvider = dataProvider;
   }

   /**
    * Listi uuendamine. Ei teostata andmete pärimist. Andmete uuesti pärimiseks tuleb kasutada meetodit
    * {@link EpmPagedListHolder#markedToRefreshData()}. Antud meetod käivitab filtri, sorteerimine ja lehtedeks jaotamine.
    */
   public void refresh() {
      Assert.isTrue(getDataProvider() != null, "DataSource is not initialized");

      // pärime andmed uuesti ainult siis kui filterparameetrid on muutunud
      if (isDbBasedQuery()) {
         pagination();
         markedToRefreshData();
         // FIXME Margus - Siia tuleks lisada täiendav kontroll. Kui tegemist on
         // muudetava tabeliga siis ei tohi kohe uut päringut teostada kuna
         // sellisel juhul ehitatakse kogu list uuesti üles ning ListRow
         // määrangud tühistatakse. Kasutajalt tuleb küsida üle: Teil on
         // salvetamata andmeid, kas olete kindel et soovite jätkata?
         setAllDataRows(null);
      }

      if (getAllDataRows() == null) {
         List<T> datas = getDataProvider().getData();
         if (datas == null) {
            // kui null, tähendab see seda, et andmeid ei ole veel välja loetud,
            // teeme seda
            markedToRefreshData();
            datas = getDataProvider().getData();
         }

         setAllDataRows(new ArrayList<ListRow>(datas.size()));
         int rowNumber = 0;
         for (T data : datas) {
            getAllDataRows().add(listComponent.buildRow(data, rowNumber++));
         }
      }

      filter();
      resort();
      pagination();
      renewListHolder();
   }

   /**
    * Valmistame uuesti ette DataProvider, et toimuks andmebaasist uuesti andmete laadmimine. Andmete uuenduse toumumiseks tuleb täiendavalt
    * välja kutsuda meetod {@link EpmPagedListHolder#refresh()}
    */
   public void markedToRefreshData() {
      Assert.isTrue(getDataProvider() != null, "DataSource is not initialized");
      setAllDataRows(null);
      getDataProvider().prepare(listComponent);
   }

   /**
    * Meetod tagastab kõik read, ka need mis on kustutatud {@link ListRow#isDeleted()} = <code>true</code> või ei ole välja kuvatud
    * {@link ListRow#isVisible()} = <code>false</code>
    * 
    * @return
    */
   public List<ListRow> getAllDataRows() {
      return dataRows;
   }

   private void setAllDataRows(List<ListRow> dataRows) {
      this.dataRows = dataRows == null ? null : Collections.synchronizedList(dataRows);
   }

   /**
    * Listi ridade filtreerimine. Vastavalt andmeallikale käitub filter erinevalt. Kui andmeallikaks on ListQuery siis täiendavalt
    * filtreerimist ei toimu kuna see tehakse ära andmebaasi päringute vahendusel. Kui ei ole tegu andmebaasi allikaga itereeritakse üle
    * kõikide listi ridade ning kuvatakse välja vaid need read, mis vastavad filtri tingimustele. Lisaks määratakse
    * {@link ListRow#setVisible(boolean)}
    */
   private void filter() {
      synchronized (this) {
         List<ListRow> allRows = getAllDataRows();

         List<ListRow> rows = new ArrayList<>(allRows.size());

         for (ListRow listRow : allRows) {
            listRow.setVisible(isDbBasedQuery() || showRow(listRow));
            if (listRow.isVisible()) {
               rows.add(listRow);
            }
         }

         setSource(rows);
      }
   }

   /**
    * Meetodis kontrollitakse kas argumendina olev listi rida vastab määratud filtri tingimustele. Kui vastav siis tagastatakse true
    * vastasel juhul false
    * 
    * @param row
    * @return
    */
   private boolean showRow(ListRow row) {

      int cellCount = 0;
      for (ListHeader header : listComponent.getListHeader()) {
         FormElement element = row.getElementsGroup().getByIndex(cellCount).getElement();
         if (!(element instanceof FormDataElement)) {
            continue;
         }

         FormDataElement dataElement = (FormDataElement) element;

         if (header.getCustomFilter() == null) {
            if (listFilter.filter(header, row, dataElement.getRawValue())) {
               return false;
            }
         } else {
            if (header.getCustomFilter().filter(header, row, dataElement.getRawValue())) {
               return false;
            }
         }

         cellCount++;
      }
      return true;
   }

   public void resort() {
      super.resort();
      if (StringUtils.isNotEmpty(getOrder().getOrderedField())) {
         final String orderedField = getOrder().getOrderedField();

         Collections.sort(getSource(), new Comparator<ListRow>() {
            public int compare(ListRow o1, ListRow o2) {
               int compareTo = o1.getElementsGroup().getById(orderedField).getElement()
                     .compareTo(o2.getElementsGroup().getById(orderedField).getElement());
               return getOrder().isDesc() ? compareTo * -1 : compareTo;
            }
         });
      }
   }

   private void pagination() {

      boolean showAll = listComponent.getProperties().isShowAll();

      if (showAll) {
         getPagination().setPageNr(0);
      } else if (getPagination().getPageNr() == 0) {
         getPagination().setPageNr(1);
      }

      int maxRowsOnPage = listComponent.getProperties().getMaxRowsOnPage();

      setPageSize(showAll ? maxRowsOnPage + 1 : listComponent.getProperties().getPageSize());

      if (showAll && getLastElementOnPage() > maxRowsOnPage - 1) {
         // kuvame hoiatuse, kui kuvatavate kirjete arv ületab lubatud kuvatavate kirjete arvu
         // TODO: Message lisamise loogika lisada ComponentCaption juurde
         listComponent.addMessage(listComponent.getProperties().getMaxRowsOnPageAchievedMsg(), maxRowsOnPage);
         setPageSize(maxRowsOnPage);
      }

      if (getPagination().getPageNr() > 0) {
         setPage(getPagination().getPageNr() - 1);
      }
   }

   // TODO memory-based ja DB-based listid tuleks vist ikka lahku tõsta ... oleks vast selgem
   public boolean isDbBasedQuery() {
      return false;
   }

   /**
    * Meetod tagastab <code>true</code> juhul kui lehtede tükeldamine on välja lülitatud ning kasutajale näidatakse kõiki ridu korraga
    * 
    * @return {@link Boolean}
    */
   public boolean isShowAll() {
      return listComponent.getProperties().isShowAll();
   }

   /**
    * Meetod tagastab objekti, mis hoiab endas ridade lehtedeks tükeldamise informatsioonis
    * 
    * @return {@link ListPagination}
    */
   public ListPagination getPagination() {
      return pagination;
   }

   /**
    * Objekti {@link ListPagination} määramine
    * 
    * @param pagination
    *           {@link ListPagination}
    */
   public void setPagination(ListPagination pagination) {
      this.pagination = pagination;
   }

   /**
    * Meetod tagastab objekti, mis hoiab endas veeru järjestamise informatsioonis
    * 
    * @return {@link ListOrder}
    */
   public ListOrder getOrder() {
      return order;
   }

   /**
    * Objekti {@link ListOrder} määramine
    * 
    * @param order
    *           {@link ListOrder}
    */
   public void setOrder(ListOrder order) {
      this.order = order;
   }

   /**
    * Return the total number of elements in the source list.
    */
   public int getNrOfElements() {
      if (isDbBasedQuery()) {
         return (int) dataProvider.getSize();
      }

      return super.getNrOfElements();
   }

   @Override
   public List<ListRow> getPageList() {
      if (isDbBasedQuery()) {
         return getSource();
      }

      return super.getPageList();
   }

   /**
    * Meetod tagastab nimekirja elementide arvu, mis on kasutajale nähtavad. Nimekirja suuruse leidmisel ei arvestata sisse kustutatud
    * kirjeid.
    */
   public int getSize() {

      // Kui tegu andmebaasi tüüpi päringuga siis küsime listi suuruse QueryResult küljest
      if (isDbBasedQuery()) {
         return getNrOfElements();
      }

      // Leiame read kokku. Ei arvesta ridu, mis on märgitud kustutamiseks
      int size = 0;
      for (ListRow listRow : getSource()) {
         if (!listRow.isDeleted() && listRow.isVisible()) {
            size++;
         }
      }

      return size;
   }
}
