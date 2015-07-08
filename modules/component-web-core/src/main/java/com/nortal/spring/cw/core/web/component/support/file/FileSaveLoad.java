package com.nortal.spring.cw.core.web.component.support.file;

import com.nortal.spring.cw.core.model.FileHolderModel;

/**
 * 
 * @author Margus Hanni
 * 
 */
public interface FileSaveLoad {

   FileHolderModel save(FileHolderModel fileModel);

   FileHolderModel load(FileHolderModel fileModel);

}
