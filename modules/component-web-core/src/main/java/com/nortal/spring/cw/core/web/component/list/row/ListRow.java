package com.nortal.spring.cw.core.web.component.list.row;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.BaseElement;
import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.ElementPathList;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.element.ElementsGroup;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.element.FormElementMarkedState;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.list.holder.EpmPagedListHolder;
import com.nortal.spring.cw.core.web.json.JsonResponse;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Nimekirjade kuvamise rida. Kasutatakse {@link ListComponent} ridade kuvamisel. Igal real on oma nupud, mida on võimalik individuaalselt
 * hallata
 * 
 * @author Margus Hanni
 * @since 27.02.2013
 */
public class ListRow extends BaseElement implements FormElementMarkedState, ElementPathList {

   private static final long serialVersionUID = 1L;

   public static final String ACTIVE_LIST_ROW_KEY = "activeListRow";

   private ElementsGroup<RowCell> cells = new ElementsGroup<RowCell>();
   private final ListRowButtons rowButtons;

   private boolean deleted;
   private boolean newRow;
   private int rowNumber;
   private Object entity;

   public ListRow(Object entity, Component parent) {
      this(entity, null, parent);
   }

   public ListRow(Object entity, String elementName, Component parent) {
      super(elementName);
      this.entity = entity;
      setParent(parent);
      setParentElementPath(getParent());
      rowButtons = new ListRowButtons();
      rowButtons.setListRow(this);
   }

   public ElementsGroup<RowCell> getElementsGroup() {
      return cells;
   }

   public ListRowButtons getListRowButtons() {
      return rowButtons;
   }

   public List<RowCell> getRowCell() {
      return cells.getList();
   }

   @SuppressWarnings("unchecked")
   public <T> T getEntity() {
      return (T) entity;
   }

   public void setEntity(Object entity) {
      this.entity = entity;
   }

   public boolean isDeleted() {
      return deleted;
   }

   public void setDeleted(boolean deleted) {
      this.deleted = deleted;
   }

   /**
    * Kutsutakse välja listi rea kustutamisel
    * 
    */
   @EventHandler(eventName = ListRowButtons.LIST_EVENT_DELETE)
   public JsonResponse listDeleteRow(final ControllerComponent comp, final Map<String, String[]> params) {

      if (rowButtons.getDeleteButton().getConfirmation() == null) {
         deleteRow(params);
      } else {
         rowButtons.getDeleteButton().getConfirmation().setAcceptHandler(new ElementHandler() {

            private static final long serialVersionUID = 1L;

            @Override
            public String execute() {
               deleteRow(params);
               return null;
            }
         });

         rowButtons.getDeleteButton().getConfirmation().show();
         if (RequestUtil.isAjaxRequest(params)) {
            return rowButtons.getDeleteButton().getConfirmation().getJsonResponse();
         }
      }
      return null;
   }

   private void deleteRow(Map<String, String[]> params) {
      deleted = true;
      ListComponent composite = (ListComponent) getParent();

      composite.validate();
      if (!getBindingResult().hasErrors() && composite.getDeleteRowHandler() != null) {
         composite.setActiveEntity(this.entity);
         composite.refresh();
         composite.getDeleteRowHandler().execute();
      }
   }

   /**
    * Kutsutakse välja listi rea muutmisel
    * 
    */
   @EventHandler(eventName = ListRowButtons.LIST_EVENT_EDIT)
   public void listEditRow(ControllerComponent comp, Map<String, String> params) {

      ListComponent listComponent = (ListComponent) getParent();

      listComponent.setActiveEntity(this.entity);
      listComponent.setActiveListRow(this);

      if (isVisible() && isEditable()) {
         this.validate();
         if (getBindingResult().hasErrors()) {
            return;
         }
      }

      setEditable(!isEditable());

      if (isEditable()) {
         markedState();
         enableDisableInputFieldsToEdit(true);
         return;
      }

      this.convert();

      if (listComponent.getPagedListHolder().isDbBasedQuery()) {
         listEditDatabaseBasedRow(listComponent);
         return;
      }

      listEditMemoryBasedRow(listComponent);

      enableDisableInputFieldsToEdit(false);
   }

   /**
    * Kutsutakse välja listi rea muutmisel
    * 
    */
   @EventHandler(eventName = ListRowButtons.LIST_EVENT_EDIT_CANCEL)
   public void listEditCancelRow(ControllerComponent comp, Map<String, String> params) {
      revertToMarkedState();
      setEditable(!isEditable());
      enableDisableInputFieldsToEdit(false);
   }

   /**
    * Kutsutakse välja uue listi rea lisamisel
    * 
    */
   @EventHandler(eventName = ListComponent.SUBMIT_LIST_ADDROW)
   public void listAddRow(ControllerComponent comp, Map<String, String> params) {

      ListComponent listComponent = (ListComponent) getParent();

      listComponent.setActiveEntity(this.getEntity());
      listComponent.setActiveListRow(this);

      this.validate();

      // kui esineb vigu siis ei jätka
      if (this.getBindingResult().hasErrors()) {
         return;
      }

      this.convert();

      if (listComponent.getPagedListHolder().isDbBasedQuery()) {
         listAddDatabaseBasedRow(listComponent);
         return;
      }

      listAddMemoryBasedRow(listComponent);
      this.setEditable(false);
      enableDisableInputFieldsToEdit(false);
   }

   public void validate() {
      for (RowCell cell : getRowCell()) {
         FormElement element = cell.getElement();
         if (this.isElementEditable(element) && element instanceof FormDataElement) {
            FormDataElement formDataElement = (FormDataElement) element;
            formDataElement.validate();
         }
      }

      if (!getBindingResult().hasErrors()) {
         // kontrollime kitsendusi juhul kui valideerimisel ei ole esinenud vigu
         this.checkConstraints();
      }
   }

   private void checkConstraints() {
      for (RowCell cell : getRowCell()) {
         FormElement element = cell.getElement();
         if (element instanceof FormDataElement) {
            FormDataElement dataElement = (FormDataElement) element;
            dataElement.checkConstraints();
         }
      }
   }

   public void convert() {
      for (RowCell cell : getRowCell()) {
         FormElement element = cell.getElement();
         if (element.isEditable() && element instanceof FormDataElement && StringUtils.isNotEmpty(element.getId())) {
            ((FormDataElement) element).convert();
         }
      }
   }

   public boolean isNewRow() {
      return newRow;
   }

   public void setNewRow(boolean newRow) {
      this.newRow = newRow;
   }

   private void listAddMemoryBasedRow(ListComponent listComponent) {

      EpmPagedListHolder<Object> listHolder = listComponent.getPagedListHolder();

      listHolder.getAllDataRows().add(this);

      if (listComponent.getAddNewRowHandler() != null) {
         listComponent.getAddNewRowHandler().execute();
      }

      listComponent.refresh();
   }

   private void listEditMemoryBasedRow(ListComponent listComponent) {

      if (listComponent.getEditRowHandler() != null) {
         listComponent.getEditRowHandler().execute();
      }

      listComponent.refresh();
   }

   private void listAddDatabaseBasedRow(ListComponent listComponent) {
      if (listComponent.getAddNewRowHandler() == null) {
         throw new AppBaseRuntimeException(
               "Data provider is a database-based provider. EpmListComponent#getAddNewRowHandler() must not be null");
      }

      listComponent.getAddNewRowHandler().execute();
      listComponent.refreshData();
   }

   private void listEditDatabaseBasedRow(ListComponent listComponent) {
      if (listComponent.getEditRowHandler() == null) {
         throw new AppBaseRuntimeException(
               "Data provider is a database-based provider. EpmListComponent#getEditRowHandler() must not be null");
      }

      listComponent.getEditRowHandler().execute();
      listComponent.refreshData();
   }

   private void enableDisableInputFieldsToEdit(boolean enable) {
      EventElement deleteButton = rowButtons.getDeleteButton();
      // kui eksisteerib kustutamise nupp siis rea muutmisel peidame selle ära
      if (deleteButton != null) {
         deleteButton.setVisible(!enable);
      }

      getListComponent().refreshRow((ListRow) this, rowNumber);
   }

   @Override
   public void markedState() {
      for (RowCell cell : getRowCell()) {
         FormElement element = cell.getElement();
         if (element instanceof FormDataElement) {
            ((FormDataElement) element).markedState();
         }
      }
   }

   @Override
   public void revertToMarkedState() {
      for (RowCell cell : getRowCell()) {
         FormElement element = cell.getElement();
         if (element instanceof FormDataElement) {
            ((FormDataElement) element).revertToMarkedState();
         }
      }
   }

   public int getRowNumber() {
      return rowNumber;
   }

   public void setRowNumber(int rowNumber) {
      this.rowNumber = rowNumber;
   }

   public boolean isElementEditable(FormElement element) {
      return this.isEditable() && element.isEditable();
   }

   private ListComponent getListComponent() {
      return (ListComponent) getParent();
   }

   @Override
   public int getPathIndex() {
      return getRowNumber();
   }

}
