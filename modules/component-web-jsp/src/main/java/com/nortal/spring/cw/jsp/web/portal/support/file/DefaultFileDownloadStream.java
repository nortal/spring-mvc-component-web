package com.nortal.spring.cw.jsp.web.portal.support.file;

import java.io.InputStream;

import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.web.component.support.file.FileDownloadStream;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.jsp.service.file.FileService;

/**
 * @author Margus Hanni
 * 
 */
public class DefaultFileDownloadStream implements FileDownloadStream {

   @Override
   public InputStream getFileStream(FileHolderModel fileModel) {
      return BeanUtil.getBean(FileService.class).getInputStream(fileModel);
   }

   @Override
   public InputStream getFileStream() {
      throw new UnsupportedOperationException("Method not implemented");
   }
}
