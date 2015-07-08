package com.nortal.spring.cw.core.web.component.support.file;

import java.io.InputStream;

import com.nortal.spring.cw.core.model.FileHolderModel;

/**
 * @author Margus Hanni
 * 
 */
public interface FileDownloadStream {

   InputStream getFileStream();

   InputStream getFileStream(FileHolderModel fileModel);

}
