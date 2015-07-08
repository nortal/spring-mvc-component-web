package com.nortal.spring.cw.jsp.service.file;

import java.io.InputStream;

import com.nortal.spring.cw.core.model.FileHolderModel;

/**
 * @author Margus Hanni
 * 
 */
public interface FileService {

   InputStream getInputStream(FileHolderModel fileModel);

   void updateIfFileExists(FileHolderModel fileModel);

   void initFileHolder(FileHolderModel fileModel);
}
