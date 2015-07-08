package com.nortal.spring.cw.core.web.component.list;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;

/**
 * Contains various properties for list component.
 * 
 * @author Margus Hanni
 * 
 */
public class ListProperties implements Serializable {

   private static final long serialVersionUID = 1L;
   private static final int DEFAULT_MAX_ROWS_ON_PAGE = 20;
   private static final int DEFAULT_MAX_LINKED_PAGES = 6;

   private int maxLinkedPages = DEFAULT_MAX_LINKED_PAGES;
   private int maxRowsOnPage = DEFAULT_MAX_ROWS_ON_PAGE;
   private String maxRowsOnPageAchievedMsg = GlobalLabel.LIST_ROW_VIEW_LIMIT_ACHIEVED;
   private boolean sortable = true;
   private boolean filterable = false;
   private boolean addRowSubmit;
   private boolean allowAdd;
   private boolean showRowNumber;
   private boolean allowPagination = true;
   private boolean allowEditButton;
   private boolean allowDeleteButton;
   private boolean showMarkAll;
   private int pageSize = 10;
   private boolean showAll;
   private FieldElementCssClass cssClass = FieldElementCssClass.DATA;

   public int getMaxLinkedPages() {
      return maxLinkedPages;
   }

   public boolean isSortable() {
      return sortable;
   }

   public boolean isFilterable() {
      return filterable;
   }

   public boolean isAddRowSubmit() {
      return addRowSubmit;
   }

   public boolean isAllowAdd() {
      return allowAdd;
   }

   public boolean isShowRowNumber() {
      return showRowNumber;
   }

   public boolean isAllowEditButton() {
      return allowEditButton;
   }

   public boolean isAllowDeleteButton() {
      return allowDeleteButton;
   }

   public ListProperties setMaxLinkedPages(int maxLinkedPages) {
      this.maxLinkedPages = maxLinkedPages;
      return this;
   }

   public ListProperties setSortable(boolean sortable) {
      this.sortable = sortable;
      return this;
   }

   public ListProperties setFilterable(boolean filterable) {
      this.filterable = filterable;
      return this;
   }

   public ListProperties setAddRowSubmit(boolean addRowSubmit) {
      this.addRowSubmit = addRowSubmit;
      return this;
   }

   public ListProperties setAllowAdd(boolean allowAdd) {
      this.allowAdd = allowAdd;
      return this;
   }

   public ListProperties setShowRowNumber(boolean showRowNumber) {
      this.showRowNumber = showRowNumber;
      return this;
   }

   public ListProperties setAllowEditButton(boolean allowEditButton) {
      this.allowEditButton = allowEditButton;
      return this;
   }

   public ListProperties setAllowDeleteButton(boolean allowDeleteButton) {
      this.allowDeleteButton = allowDeleteButton;
      return this;
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   /**
    * Kas listi komponendis kuvatakse lehekülgede numeratsiooni osa
    * 
    * @return {@link Boolean}
    */
   public boolean isAllowPagination() {
      return allowPagination;
   }

   /**
    * Lehekülgede numeratsiooni sisse/välja lülitamine. Kui väärtuseks on <code>false</code> siis listi komponendis lehtede nummereerimise
    * osa ei kuvata
    * 
    * @param allowPagination
    *           {@link Boolean}
    */
   public void setAllowPagination(boolean allowPagination) {
      this.allowPagination = allowPagination;
   }

   /**
    * Meetod tagastab tekstilisel kujul elemendi CSS klass, mis leitakse enumist {@link FieldElementCssClass}. Vaikeväärtusena on klassiks
    * {@link FieldElementCssClass#DATA}
    * 
    * @return {@link String}
    */
   public String getCssClassValue() {
      return cssClass == null ? StringUtils.EMPTY : cssClass.getValue();
   }

   /**
    * Elemendi CSS klassi määramine
    * 
    * @param cssClass
    *           {@link FieldElementCssClass}
    */
   public void setCssClass(FieldElementCssClass cssClass) {
      this.cssClass = cssClass;
   }

   /**
    * Kas tabeli päises kuvatakse märgista kõik või mitte ühtegi valikut. Tabeli esimene veerg peab olema <code>checkbox</code> väli.Valik
    * töötab ainult hetkel kuvatavate ridade peal
    * 
    * @return {@link Boolean}
    */
   public boolean isShowMarkAll() {
      return showMarkAll;
   }

   /**
    * Kas tabeli päises kuvatakse märgista kõik või mitte ühtegi valikut. Tabeli esimene veerg peab olema <code>checkbox</code> väli. Valik
    * töötab ainult hetkel kuvatavate ridade peal
    * 
    * @param showMarkAll
    *           {@link Boolean}
    */
   public void setShowMarkAll(boolean showMarkAll) {
      this.showMarkAll = showMarkAll;
   }

   /**
    * Meetod tagastab lubatud maksimaalse kuvatava kirjete arvu tabelis. Vaikeväärtusena leitakse lubatud kirjete arv süsteemsest
    * parameetrist {@link SysParameterEnum#MAX_KUVATAV_KIRJETE_ARV_LISTIS}
    * 
    * @return {@link Integer}
    */
   public int getMaxRowsOnPage() {
      return maxRowsOnPage;
   }

   /**
    * Maksimaalse tabelis kuvatavate kirjete arvu määramine
    * 
    * @param maxRowsOnPage
    *           Maksimaalne lupatud kirjete arv {@link Integer}
    */
   public void setMaxRowsOnPage(int maxRowsOnPage) {
      this.maxRowsOnPage = maxRowsOnPage;
   }

   /**
    * Meetod tagastab hoiatusteate, mis kuvatakse juhul kui tabelis kuvatavate maksimaalne kirjete arv on ületatud
    * 
    * @return {@link String}
    */
   public String getMaxRowsOnPageAchievedMsg() {
      return maxRowsOnPageAchievedMsg;
   }

   /**
    * Hoiatusteate määramine, mis kuvatakse juhul kui tabelis kuvatavate maksimaalne kirjete arv on ületatud
    * 
    * @param maxRowsOnPageAchievedMsg
    *           {@link String}
    */
   public void setMaxRowsOnPageAchievedMsg(String maxRowsOnPageAchievedMsg) {
      this.maxRowsOnPageAchievedMsg = maxRowsOnPageAchievedMsg;
   }

   /**
    * Meetod tagastab <code>true</code> kui tabelis kuvatakse kõiki kirjeid korraga, puudub tulemuse eraldi lehtedele paigutamine
    * 
    * @return {@link Boolean}
    */
   public boolean isShowAll() {
      return showAll;
   }

   /**
    * Kõikide tabeli kirjete korraga kuvamine. Kui <code>true</code> kuvatakse kõiki tabeli kirjeid korraga, puudub tulemuse eraldi
    * lehtedele paigutamine. Täiendavalt kõikide kirjete kuvamisel kontrollitakse {@link ListProperties#getMaxRowsOnPage()} väärtust, mis
    * peab olema suurem kui tabelis kuvatavate kirjete arv kokku.
    * 
    * @param showAll
    *           {@link Boolean}
    */
   public void setShowAll(boolean showAll) {
      this.showAll = showAll;
   }
}
