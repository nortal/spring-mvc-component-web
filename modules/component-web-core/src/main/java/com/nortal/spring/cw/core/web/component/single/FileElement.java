package com.nortal.spring.cw.core.web.component.single;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.file.holder.FileHolder;
import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FieldErrorEnum;
import com.nortal.spring.cw.core.web.component.element.FormDataElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.event.LinkElement;
import com.nortal.spring.cw.core.web.component.form.FormElementPathItem;
import com.nortal.spring.cw.core.web.util.DownloadUtil;

import eu.medsea.mimeutil.MimeType;
import eu.medsea.mimeutil.detector.MagicMimeMimeDetector;

/**
 * Failide komponent, mis lubab faile alla laadida kui ka üles laadida. Failide üles laadimiseks tuleb määratleda lubatud failide MIME
 * tüübid. Selleks tuleb luua ja täita ressursside kataloogis fail "allowed-mime-types.txt" ning failis üksteise alla lisada lubatud faili
 * mime tüübid. MIME tüüpide määratlemisel on lubatud regexi kasutamine. Kui faili ei ole defineeritud siis faile üles laadida ei lubata.
 * 
 * @author Margus Hanni
 * 
 */
@Slf4j
public class FileElement extends AbstractBaseElement<FileHolderModel> {

   public static final String EVENT_DOWNLOAD_FILE = "downloadFile";
   private static final Set<String> ALLOWED_MIME_TYPES = new HashSet<>();

   private static final long serialVersionUID = 1L;
   private MultipartFile multipartFile;
   private Class<? extends FileHolderModel> modelClass;
   private boolean allowDownload;
   /** Konstant: 1 MB on {@value} baiti */
   private static final int ONE_MB_IN_BYTES = 1048576;
   private static Long defaultAllowedMaxFileSize = 10L;
   private FormElementPathItem downloadLinkHolder;

   public FileElement() {
      super();
      setUseAjaxValidation(false);
   }

   static {
      try {
         for (String line : FileUtils
               .readLines(new File(FileElement.class.getClassLoader().getResource("allowed-mime-types.txt").getFile()))) {
            if (StringUtils.isNotEmpty(line) && !StringUtils.startsWith(line, "#")) {
               ALLOWED_MIME_TYPES.add(line);
            }
         }

         if (ALLOWED_MIME_TYPES.isEmpty()) {
            throw new AppBaseRuntimeException("Mime types are not specified");
         }
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e, "Error on loading allowed file mime types");
      }
   }

   {
      super.addValidator(new AbstractValidator() {
         private static final long serialVersionUID = 1L;

         @Override
         public void validate() {
            FileHolderModel value = getValue();
            if (value == null || !isMandatory() && !value.hasFile()) {
               return;
            }

            FormElement element = super.getElement();

            if (isMandatory() && !value.hasFile()) {
               element.addElementErrorMessage(FieldErrorEnum.MANDATORY.getTranslationKey());
               return;
            } else if (value.getFileSize() == null || value.getFileSize() == 0) {
               element.addElementErrorMessage("field.file.empty");
               return;
            }

            // TODO: create setter for overwrite
            long allowedMaxFileSize = defaultAllowedMaxFileSize;

            long maxFileSize = allowedMaxFileSize * ONE_MB_IN_BYTES;

            FileHolderModel fileModel = getValue();

            if (fileModel.getFileSize() > maxFileSize) {
               element.addElementErrorMessage("field.file.to-big", allowedMaxFileSize);
               return;
            }

            if (fileModel.getFileHolder() != null && fileModel.getFileHolder().getData() != null) {
               MagicMimeMimeDetector a = new MagicMimeMimeDetector();
               for (Object mimeType : a.getMimeTypesByteArray(fileModel.getFileHolder().getData())) {
                  if (!isMimeTypeAllowed(((MimeType) mimeType).toString())) {
                     element.addElementErrorMessage("field.file.mime-type-not-allowed", mimeType);
                     break;
                  }
               }

            }
         }
      });
   }

   private boolean isMimeTypeAllowed(String mimeType) {
      if (ALLOWED_MIME_TYPES.isEmpty()) {
         log.warn("Allowed mime types are not specified");
         return false;
      }

      for (String allowedType : ALLOWED_MIME_TYPES) {
         if (mimeType.matches(allowedType)) {
            return true;
         }
      }

      return false;
   }

   public FileElement setModelClass(Class<? extends FileHolderModel> modelClass) {
      this.modelClass = modelClass;
      return this;
   }

   public Class<? extends FileHolderModel> getModelClass() {
      return modelClass;
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.FILE;
   }

   @Override
   public String getDisplayValue() {
      if (getValue() == null || !getValue().hasFile()) {
         return StringUtils.EMPTY;
      }
      return getValue().getFilename();
   }

   @Override
   public int compareTo(Object o) {

      if (!(o instanceof FormDataElement)) {
         return 0;
      }

      FormDataElement oc = (FormDataElement) o;

      if (getValue() == null && oc.getRawValue() == null) {
         return 0;
      }

      if (getValue() == null && oc.getRawValue() != null) {
         return 1;
      }

      if (getValue() != null && oc.getRawValue() == null) {
         return -1;
      }

      return getDisplayValue().compareTo(oc.getDisplayValue());
   }

   public MultipartFile getMultipartFile() {
      return multipartFile;
   }

   public void setMultipartFile(MultipartFile multipartFile) throws IOException {
      this.multipartFile = multipartFile;

      if (!isValidMultipartFile(this.multipartFile)) {
         return;
      }

      if (getValue() == null) {
         setValue((FileHolderModel) BeanUtils.instantiate(modelClass));
      }

      FileHolderModel value = getValue();
      File file = FileHolder.createTempFile();
      this.multipartFile.transferTo(file);
      if (value.getFileHolder() != null) {
         value.getFileHolder().delete();
      }
      value.setFileHolder(new FileHolder(file));
      value.setMimeType(this.multipartFile.getContentType());
      value.setFileSize(this.multipartFile.getSize());
      value.setFilename(this.multipartFile.getOriginalFilename());
   }

   public FormElementPathItem getDownloadLink() {
      if (getValue() == null)
         return null;
      if (downloadLinkHolder == null) {
         LinkElement linkElement = new LinkElement(EVENT_DOWNLOAD_FILE, "#" + getValue().getFilename());
         linkElement.setParent(this);
         downloadLinkHolder = new FormElementPathItem("downloadLink", linkElement, this);
      } else {
         ((LinkElement) downloadLinkHolder.getElement()).setLabel("#" + getValue().getFilename());
      }

      return downloadLinkHolder;
   }

   @EventHandler(eventName = EVENT_DOWNLOAD_FILE, downloadStream = true)
   public void download(HttpServletResponse response, Errors errors) {

      if (!allowDownload) {
         log.warn("File download is not allowed");
      }

      try {
         DownloadUtil.download(response, getValue());
      } catch (IOException e) {
         log.error("FileElement#download", e);
         errors.rejectValue(getFullPath(), FieldErrorEnum.DOWNLOAD.getTranslationKey());
      }
   }

   public boolean isAllowDownload() {
      return allowDownload;
   }

   public boolean isCanDownload() {
      return isAllowDownload() && StringUtils.isNotEmpty(getDisplayValue());
   }

   public void setAllowDownload(boolean allowDownload) {
      this.allowDownload = allowDownload;
   }

   public static boolean isValidMultipartFile(MultipartFile multipartFile) {
      return multipartFile != null && StringUtils.isNotBlank(multipartFile.getOriginalFilename());
   }
}
