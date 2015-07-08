package com.nortal.spring.cw.core.web.component.multiple;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;
import com.nortal.spring.cw.core.web.component.single.FileElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValue;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueElement;
import com.nortal.spring.cw.core.web.component.single.values.MultiValueSupport;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * @author Taivo Käsper (taivo.kasper@nortal.com)
 * @since 29.07.2013
 */
public class FileCollectionElement extends AbstractBaseElement<Collection<FormElementHolderMapItem>> implements MultiValueSupport {
   private static final long serialVersionUID = 1L;

   private MultiValue multiValue;
   private Class<? extends FileHolderModel> modelClass;
   private boolean allowDownload;

   {
      super.addValidator(new AbstractValidator() {
         private static final long serialVersionUID = 1L;

         @Override
         public void validate() {
            FileCollectionElement element = super.getElement();

            for (FormElementHolderMapItem val : element.getValue()) {
               if (val.getElement() instanceof FileElement) {
                  FileElement fElement = (FileElement) val.getElement();
                  fElement.validate();
               }
            }
         }
      });
   }

   public FileCollectionElement() {
      super();
      setUseAjaxValidation(false);
      super.setOnChangeHandler(ElementHandler.EMPTY_HANDLER);
   }

   @Override
   public void setOnChangeHandler(ElementHandler handler) {
      throw new AppBaseRuntimeException("#On change eventhandler is not allowed because it is used by the file uploader!");
   }

   public void setMultipartFile(MultipartFile multipartFile) throws IOException {
      if (FileElement.isValidMultipartFile(multipartFile)) {

         final FileElement elem = getNewFileElement(null);
         elem.setMultipartFile(multipartFile);
         renewElementId(elem);
         FormElementHolderMapItem formElementHolderMapItem = new FormElementHolderMapItem(elem, this);
         getValue().add(formElementHolderMapItem);
      }
   }

   private void renewElementId(FormElement elem) {
      int size = CollectionUtils.size(getValue());
      renewElementId(elem, size);
   }

   private void renewElementId(FormElement elem, int index) {
      elem.setId(String.valueOf(index));
   }

   public Collection<FormElementHolderMapItem> getElementHolder() {
      return getValue();
   }

   @Override
   @SuppressWarnings("unchecked")
   public void setRawValue(Object value) {
      setValue(null);

      final List<FormElementHolderMapItem> elements = new ArrayList<FormElementHolderMapItem>();
      if (value != null) {
         for (FileHolderModel o : (List<FileHolderModel>) value) {
            final FileElement ele = getNewFileElement(o);
            FormElementHolderMapItem formElementHolderMapItem = new FormElementHolderMapItem(ele, this);
            getValue().add(formElementHolderMapItem);
            elements.add(formElementHolderMapItem);
         }
      }
      setValue(elements);
   }

   @Override
   public Collection<FormElementHolderMapItem> getValue() {
      if (super.getValue() == null) {
         setValue(new ArrayList<FormElementHolderMapItem>());
      }
      return super.getValue();
   }

   private FileElement getNewFileElement(FileHolderModel value) {
      final FileElement elem = new FileElement();
      elem.setParent(this.getParent());
      elem.setModelClass(modelClass);
      elem.setAllowDownload(allowDownload);
      elem.setEditable(true);

      // ElementFieldPath lisamine peab toimuma peale väärtuse lisamist.
      // Vastasel juhul üritatakse entity lugeda elemendi väärtust
      elem.setValue(value);
      renewElementId(elem);
      return elem;
   }

   @Override
   public Object getRawValue() {
      final Map<Object, MultiValueElement> multiValues = getMultiValues();
      final List<FileHolderModel> vals = new ArrayList<>();

      for (MultiValueElement val : multiValues.values()) {
         FormElementHolderMapItem formElHolderMapItem = (FormElementHolderMapItem) val.getValue();
         FileElement element = (FileElement) formElHolderMapItem.getElement();
         final FileHolderModel value = element.getValue();
         vals.add(value);
      }

      return vals;
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.FILE_COLLECTION;
   }

   @Override
   public String getDisplayValue() {
      @SuppressWarnings("unchecked")
      final List<FileHolderModel> value = (List<FileHolderModel>) getRawValue();
      final List<String> vals = new ArrayList<>();

      for (FileHolderModel fileModel : value) {
         vals.add(fileModel.getFilename());
      }
      return StringUtils.join(vals, ", ");
   }

   @Override
   public int compareTo(Object o) {
      return 0;
   }

   @Override
   public MultiValue getMultiValue() {
      return multiValue;
   }

   @Override
   public void setMultiValue(MultiValue multiValue) {
      this.multiValue = multiValue;
   }

   @Override
   public Map<Object, MultiValueElement> getMultiValues() {
      final Map<Object, MultiValueElement> map = new LinkedHashMap<Object, MultiValueElement>();

      for (FormElementHolderMapItem file : getValue()) {
         map.put(map.size(), new MultiValueElement(file));
      }
      return map;
   }

   @Override
   public boolean isMultiValueElement() {
      return multiValue != null;
   }

   @EventHandler(eventName = "removeFile")
   public String removeFile(final ControllerComponent comp, final Map<String, String> params) {
      final int param = Integer.parseInt(params.get(RequestUtil.PARAM_EPM_EVT_PARAM));

      final List<FormElementHolderMapItem> values = (List<FormElementHolderMapItem>) getValue();
      FormElementHolderMapItem item = values.get(param);

      values.remove(item);

      // Recalculate paths
      for (int i = 0; i < values.size(); i++) {
         FormElementHolderMapItem elementHolderItem = values.get(i);
         FormElement element = elementHolderItem.getElement();
         renewElementId(element, i);
      }

      return null;
   }

   public FileCollectionElement setModelClass(Class<? extends FileHolderModel> modelClass) {
      this.modelClass = modelClass;
      return this;
   }

   public boolean isAllowDownload() {
      return allowDownload;
   }

   public void setAllowDownload(boolean allowDownload) {
      this.allowDownload = allowDownload;
   }

   public String getRemoveLabel() {
      return getMessageSource().resolveByActiveLang("field.file.collection.remove");
   }

}