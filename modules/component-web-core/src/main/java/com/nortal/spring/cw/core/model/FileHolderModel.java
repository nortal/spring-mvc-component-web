package com.nortal.spring.cw.core.model;

import java.io.Serializable;

import com.nortal.spring.cw.core.file.holder.FileHolder;
import com.nortal.spring.cw.core.file.holder.FileHolderDataType;

/**
 * 
 * @author Margus Hanni
 * 
 */
public interface FileHolderModel extends Serializable {

   FileHolder getFileHolder();

   void setFileHolder(FileHolder fileHolder);

   /**
    * Tagastab {@link FileHolderModel} objekti või byte array
    * 
    * @return
    */
   Object getFileColumnValue();

   String getFileColumnName();

   /**
    * Määrab ära millisel kujul hoitakse manust andmebaasis tekst vs binaar
    * 
    * @return {@link FileHolderDataType}
    */
   FileHolderDataType getFileColumnType();

   boolean hasFile();

   Long getFileSize();

   void setFileSize(Long fileSize);

   String getFilename();

   void setFilename(String filename);

   String getMimeType();

   void setMimeType(String mimeType);
}
