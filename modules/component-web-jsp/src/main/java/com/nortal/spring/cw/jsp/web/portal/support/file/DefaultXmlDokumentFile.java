package com.nortal.spring.cw.jsp.web.portal.support.file;

import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.web.component.support.file.FileSaveLoad;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.jsp.service.file.FileService;

/**
 * @author Margus Hanni
 * 
 */
public class DefaultXmlDokumentFile implements FileSaveLoad {

   @Override
   public FileHolderModel save(FileHolderModel fileModel) {
      BeanUtil.getBean(FileService.class).updateIfFileExists(fileModel);
      return fileModel;
   }

   @Override
   public FileHolderModel load(FileHolderModel fileModel) {
      BeanUtil.getBean(FileService.class).initFileHolder(fileModel);
      return fileModel;
   }
}
