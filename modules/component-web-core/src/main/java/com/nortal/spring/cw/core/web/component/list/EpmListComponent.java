package com.nortal.spring.cw.core.web.component.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.ComponentType;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent;
import com.nortal.spring.cw.core.web.component.composite.simple.AbstractTypeComponent;
import com.nortal.spring.cw.core.web.component.element.ElementsGroup;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;
import com.nortal.spring.cw.core.web.component.list.header.DefaultListHeaderBuilder;
import com.nortal.spring.cw.core.web.component.list.header.ListHeader;
import com.nortal.spring.cw.core.web.component.list.header.ListHeaderBuilder;
import com.nortal.spring.cw.core.web.component.list.holder.EpmPagedListHolder;
import com.nortal.spring.cw.core.web.component.list.row.DefaultListRowBuilder;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.component.list.row.ListRowBuilder;
import com.nortal.spring.cw.core.web.component.list.row.ListRowButtons;
import com.nortal.spring.cw.core.web.component.modal.ModalDialogComponent;
import com.nortal.spring.cw.core.web.exception.FieldNotFoundException;
import com.nortal.spring.cw.core.web.helper.FieldHelper;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Üldine listi komponent. Vastavalt vajadusele on võimalik sisse ja välja lülitada tabeli filtreerimine ja sorteerimine
 * 
 * @author Margus Hanni
 * @author Alrik Peets
 */
public class EpmListComponent<T> extends AbstractTypeComponent<T> implements ListComponent {

   private static final long serialVersionUID = 1L;
   private static final boolean DEFAULT_SORT_ORDER_ASC = true;
   private final ListRowButtons listRowButtons = new ListRowButtons();

   private List<ListHeader> headers = new ArrayList<>();
   private ElementsGroup<FormElement> formElementsGroup = new ElementsGroup<FormElement>();
   private int cellCount = 0;

   private ElementsGroup<FormElement> newDefaultFormElements;
   private NewRow newRow;

   private ListProperties properties = new ListProperties();
   private ListHeaderBuilder listHeaderBuilder = new DefaultListHeaderBuilder();
   private ListRowBuilder<T> listRowBuilder = new DefaultListRowBuilder<T>();
   private final EpmPagedListHolder<T> pagedListHolder = new EpmPagedListHolder<>(this);

   private ElementHandler addNewRowHandler;
   private ElementHandler deleteRowHandler;
   private ElementHandler editRowHandler;

   private FormElement activeFormElement;
   private ListRow activeListRow;
   private Object activeObject;

   public EpmListComponent(String componentName, Class<T> objectClass, String orderedField, boolean sortOrderAsc) {
      super(componentName, objectClass);

      pagedListHolder.getOrder().setOrderedField(orderedField);
      pagedListHolder.getOrder().setDesc(!sortOrderAsc);
   }

   public EpmListComponent(String componentName, Class<T> objectClass) {
      this(componentName, objectClass, null, DEFAULT_SORT_ORDER_ASC);
   }

   @Override
   @SuppressWarnings("unchecked")
   public <K extends FormElement> K add(String elementFieldPath) {
      return (K) add(elementFieldPath, properties.isSortable(), properties.isFilterable());
   }

   @Override
   @SuppressWarnings("unchecked")
   public <K extends FormElement> K add(FormElement element) {
      return (K) add(element, properties);
   }

   @Override
   public void remove(FormElement element) {
      formElementsGroup.removeByPath(element.getId());
   }

   @SuppressWarnings("unchecked")
   public <K extends FormElement> K add(String elementFieldPath, boolean sortable, boolean filterable) {
      FormElement element = FieldHelper.createElement(getObjectClass(), elementFieldPath);

      return (K) add(element, sortable, filterable);
   }

   @SuppressWarnings("unchecked")
   public <K extends FormElement> K add(FormElement element, boolean sortable, boolean filterable) {

      Assert.isTrue(!isInitialized(), "List is already initialized");

      formElementsGroup.add(element);
      headers.add(new ListHeader(this, element, sortable, filterable, cellCount));

      cellCount++;

      return (K) element;
   }

   private <K extends FormElement> K add(FormElement element, ListProperties properties) {
      return add(element, properties.isSortable(), properties.isFilterable());
   }

   /**
    * Kasutatakse JSP ridade joonistamisel
    * 
    * @return
    */
   public List<ListRow> getListRow() {
      Assert.isTrue(pagedListHolder.getDataProvider() != null, "DataSource is not initialized");
      return pagedListHolder.getPageListHolder();
   }

   @SuppressWarnings("unchecked")
   public T getListRowEntity(int rowId) {
      return (T) pagedListHolder.getPageListHolder().get(rowId).getEntity();
   }

   public FormElement getFormElement(int rowId, int cellId) {
      return pagedListHolder.getPageListHolder().get(rowId).getElementsGroup().getByIndex(cellId).getElement();
   }

   public List<FormElement> getFormElements() {
      return Collections.unmodifiableList(formElementsGroup.getList());
   }

   @SuppressWarnings("unchecked")
   public EpmPagedListHolder<T> getPagedListHolder() {
      return pagedListHolder;
   }

   @SuppressWarnings("unchecked")
   public List<T> getActivePageDataList() {
      List<ListRow> dataList = pagedListHolder.getPageListHolder();
      List<T> list = new ArrayList<>(dataList.size());
      for (ListRow row : dataList) {
         if (!row.isDeleted()) {
            list.add((T) row.getEntity());
         }
      }
      return list;
   }

   @SuppressWarnings("unchecked")
   public List<T> getDataList() {
      List<ListRow> dataList = pagedListHolder.getSource();
      List<T> list = new ArrayList<>(dataList.size());
      for (ListRow row : dataList) {
         if (!row.isDeleted()) {
            list.add((T) row.getEntity());
         }
      }
      return list;
   }

   @SuppressWarnings("unchecked")
   public List<T> getNewRowDataList() {
      List<ListRow> allRows = getPagedListHolder().getAllDataRows();
      List<T> list = new ArrayList<>(allRows.size());
      for (ListRow row : allRows) {
         if (!row.isDeleted() && row.isNewRow()) {
            list.add((T) row.getEntity());
         }
      }
      return list;
   }

   public void setDataProvider(ListDataProvider<T> dataProvider) {
      this.pagedListHolder.setDataProvider(dataProvider);
   }

   public List<ListHeader> getListHeader() {
      return headers;
   }

   public ListHeader getHeaderByPath(String fieldPath) {
      for (ListHeader listHeader : headers) {
         if (StringUtils.equals(listHeader.getElement().getId(), fieldPath)) {
            return listHeader;
         }
      }

      throw new FieldNotFoundException(fieldPath);
   }

   @Override
   public boolean validateAndConvert() {
      if (!this.validate()) {
         return false;
      }
      convert();
      return !getBindingResult().hasErrors();
   }

   @Override
   public void convert() {
      for (Iterator<ListRow> iter = pagedListHolder.getPageListHolder().iterator(); iter.hasNext();) {

         ListRow row = iter.next();

         if (row.isDeleted()) {
            continue;
         }
         row.convert();
      }
   }

   @Override
   public boolean validate() {
      for (ListRow row : pagedListHolder.getPageListHolder()) {
         if (!row.isDeleted()) {
            this.setActiveListRow(row);
            row.validate();
         }
      }
      this.setActiveListRow(null);
      return !getBindingResult().hasErrors();
   }

   @SuppressWarnings("unchecked")
   public List<ListRow> getNewRows() {
      return (List<ListRow>) CollectionUtils.select(getPagedListHolder().getAllDataRows(), new Predicate() {

         @Override
         public boolean evaluate(Object object) {
            ListRow listRow = (ListRow) object;
            return listRow.isNewRow();
         }
      });
   }

   public ListProperties getProperties() {
      return properties;
   }

   public int getCellCount() {
      return this.cellCount;
   }

   @Override
   public void refresh() {
      if (getProperties().isAllowAdd() && newRow != null) {
         createNewRow();
      }
      pagedListHolder.setMaxLinkedPages(properties.getMaxLinkedPages());
      pagedListHolder.refresh();
   }

   @Override
   public void refreshData() {
      pagedListHolder.markedToRefreshData();
      refresh();
   }

   public NewRow getNewRow() {
      if (getProperties().isAllowAdd() && newRow == null) {
         createNewRow();
      }

      return newRow;
   }

   public void setEditable(boolean editable) {
      super.setEditable(editable);
      properties.setAllowDeleteButton(editable);
      properties.setAllowEditButton(editable);
   }

   public ElementHandler getAddNewRowHandler() {
      return addNewRowHandler;
   }

   public void setAddNewRowHandler(ElementHandler addNewRowHandler) {
      this.addNewRowHandler = addNewRowHandler;
   }

   public ElementHandler getDeleteRowHandler() {
      return deleteRowHandler;
   }

   public void setDeleteRowHandler(ElementHandler deleteRowHandler) {
      this.deleteRowHandler = deleteRowHandler;
   }

   @Override
   public void initComponent() {
      int lCellCount = 0;

      for (ListHeader header : headers) {
         this.listHeaderBuilder.init(DefaultListHeaderBuilder.listHeaderBuildStrategy, header, lCellCount);
         lCellCount++;
      }

      refresh();

      super.initComponent();
   }

   @EventHandler(eventName = ListComponent.SUBMIT_LIST_SORT)
   public void listSort(ControllerComponent comp, Map<String, String> params) {

      String listComponentName = params.get(ListComponent.SUBMIT_LIST_COMPONENT);
      refreshListComponent(listComponentName);
   }

   /**
    * Kutsutakse välja listi filtreerimisel
    * 
    */
   @EventHandler(eventName = ListComponent.SUBMIT_LIST_FILTER)
   public void listFilter(ControllerComponent comp, Map<String, String> params) {

      String listComponentName = params.get(ListComponent.SUBMIT_LIST_COMPONENT);
      refreshListComponent(listComponentName);
   }

   @EventHandler(eventName = ListComponent.SUBMIT_LIST_PAGINATION)
   public void listPagination(ControllerComponent comp, Map<String, String> params) {

      String listComponentName = params.get(ListComponent.SUBMIT_LIST_COMPONENT);

      refreshListComponent(listComponentName);
   }

   /**
    * Uuendame tabeliobjekti
    * 
    */
   private void refreshListComponent(String listComponentName) {
      ComplexComponent component = (ComplexComponent) getParent();

      ListComponent listComponent = component.getComponentByKey(listComponentName);
      listComponent.validateAndConvert();

      if (component instanceof ModalDialogComponent) {
         ((ModalDialogComponent) component).show();
      }

      if (!getBindingResult().hasErrors()) {
         listComponent.refresh();
      }
   }

   public ElementHandler getEditRowHandler() {
      return editRowHandler;
   }

   public void setEditRowHandler(ElementHandler editRowHandler) {
      this.editRowHandler = editRowHandler;
   }

   /**
    * Meetod tagastab tabeli päise elemendi, leides elemendi veeru elemendi raha alusel. Elemendi mitte leidmisel on tulemuseks NULL
    * 
    */
   public ListHeader getHeaderByElementFieldPath(String elementFieldPath) {
      for (ListHeader header : headers) {
         if (StringUtils.equals(header.getElement().getId(), elementFieldPath)) {
            return header;
         }
      }

      return null;
   }

   /**
    * This has to be called before setDataSource to function
    * 
    */
   public EventElement addRowActionButton(EventElement eventElement) {
      listRowButtons.addEventButton(eventElement);
      return eventElement;
   }

   public Map<String, FormElementHolderMapItem> getRowCustomActionButtonsMap() {
      return Collections.unmodifiableMap(listRowButtons.getElementHolder());
   }

   public boolean isHasActionButtons() {
      if (properties.isAllowDeleteButton() || properties.isAllowEditButton()) {
         return true;
      }

      for (ListRow row : getListRow()) {
         if (row.getListRowButtons().isHasActionButtons()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public FormElement getActiveFormElement() {
      return activeFormElement;
   }

   @Override
   public void setActiveFormElement(FormElement activeFormElement) {
      this.activeFormElement = activeFormElement;
   }

   @SuppressWarnings({ "unchecked", "hiding" })
   @Override
   public <T> T getActiveEntity() {
      return (T) activeObject;
   }

   @Override
   public void setActiveEntity(Object activeObject) {
      this.activeObject = activeObject;
   }

   @SuppressWarnings("unchecked")
   public <E> void setListRowBuilder(ListRowBuilder<E> listRowBuilder) {
      this.listRowBuilder = (ListRowBuilder<T>) listRowBuilder;
   }

   public void setListHeaderBuilder(ListHeaderBuilder listHeaderBuilder) {
      this.listHeaderBuilder = listHeaderBuilder;
   }

   public boolean isShowActionColumn() {
      return isHasActionButtons() || properties.isFilterable() || properties.isAllowAdd();
   }

   public boolean getHasData() {
      for (ListRow row : getListRow()) {
         // piisab ühest reast, mis ei ole kustutatud
         if (!row.isDeleted()) {
            return true;
         }
      }

      return false;
   }

   public boolean isShowFilter() {
      return properties.isFilterable() && (getHasData() || !isFilterEmpty());
   }

   public boolean isFilterEmpty() {

      for (ListHeader header : getListHeader()) {

         if (header.getElement() instanceof FormDataElement) {
            FormDataElement dataElement = (FormDataElement) header.getElement();

            if (!ElementUtil.isEmpty(dataElement)) {
               return false;
            }

            // kontrollime üle ka vahemiku
            if (header.isAllowFilterRange()
                  && (!ElementUtil.isEmpty((FormDataElement) header.getBetween().getRight().getElement()) || !ElementUtil
                        .isEmpty((FormDataElement) header.getBetween().getRight().getElement()))) {
               return false;
            }
         }
      }

      return true;
   }

   @Override
   public boolean isEmpty() {
      return CollectionUtils.isEmpty(getDataList()) && CollectionUtils.isEmpty(getNewRows());
   }

   public ListRowBuilder<T> getListRowBuilder() {
      return listRowBuilder;
   }

   @Override
   public ComponentType getComponentType() {
      return ComponentType.LIST;
   }

   public void setActiveListRow(ListRow listRow) {
      this.activeListRow = listRow;
   }

   public ListRow getActiveListRow() {
      return activeListRow;
   }

   public ListRow refreshRow(ListRow listRow, int rowNumber) {
      listRow.setParentElementPath(this);
      return getListRowBuilder().refreshRow(DefaultListRowBuilder.normalElementBuildStrategy, listRow, rowNumber);
   }

   public ListRow buildRow(Object data, int rowNumber) {
      return getListRowBuilder().buildRow(data, this, DefaultListRowBuilder.normalElementBuildStrategy, rowNumber);
   }

   /**
    * Meetod mis tagastab elemendid, koos algsätetega.
    * 
    * @return {@link ElementsGroup}
    */
   public ElementsGroup<FormElement> getNewDefaultFormElements() {
      if (newDefaultFormElements == null) {
         newDefaultFormElements = new ElementsGroup<FormElement>();

         for (FormElement element : getFormElements()) {
            FormElement elementClone = (FormElement) BeanUtil.clone(element);
            newDefaultFormElements.add(elementClone);
         }
      }

      return newDefaultFormElements;
   }

   private void createNewRow() {
      ListRow row = listRowBuilder.buildRow(BeanUtils.instantiateClass(getObjectClass()), this,
            DefaultListRowBuilder.newElementBuildStrategy, 0);
      modifyNewRow(row);

      this.newRow = new NewRow(row, this);
   }

   private void modifyNewRow(ListRow newRow) {
      newRow.setNewRow(true);
      newRow.setEditable(true);
   }

   @Override
   public void afterInitComponent() {
      // Nothing to do here
   }
}
