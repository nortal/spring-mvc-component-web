package com.nortal.spring.cw.core.file.holder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.util.EpmCryptoUtil;

/**
 * Klass hoiab endas faili infot. <b>Antud klassi kasutada ajutiste failide puhul</b>. Meetod getDataAsByteArray tagastab faili sisu väljalt
 * binaryData, kui antud väli on tühi siis loetakse andmed faili objektist. Lisaks on antud klassil implementeeritud meetod finalize, mis on
 * abiks faili kustutamisel temp kataloogist.
 * 
 * @author Margus Hanni
 * 
 */
public class FileHolder implements Serializable {

   private static final long serialVersionUID = 1L;
   private File file;
   private FileHolderDataType dataType = FileHolderDataType.BINARY;
   private String mimeType = "application/octet-stream";

   private FileHolder() {
      super();
   }

   public FileHolder(File file) {
      this();
      this.file = file;
   }

   public static File createTempFile() {
      try {
         File file = File.createTempFile(FileHolder.class.getSimpleName(), null);
         file.deleteOnExit();
         return file;
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e);
      }
   }

   public static FileHolder createEmptyHolder() {
      return new FileHolder(createTempFile());
   }

   public FileHolder(String stringData) {
      this(stringData.getBytes());
      dataType = FileHolderDataType.STRING;
   }

   public FileHolder(byte[] binaryData) {
      try {
         file = createTempFile();
         FileUtils.writeByteArrayToFile(file, binaryData);
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e);
      }

   }

   public byte[] getData() {
      try {
         return FileUtils.readFileToByteArray(file);
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e);
      }
   }

   public void setData(Object data) {
      try {
         byte[] binaryData;

         if (data instanceof String) {
            binaryData = ((String) data).getBytes();
            dataType = FileHolderDataType.STRING;
         } else if (data instanceof byte[]) {
            binaryData = Arrays.copyOf((byte[]) data, ((byte[]) data).length);
         } else if (data instanceof InputStream) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            IOUtils.copy(((InputStream) data), os);
            binaryData = os.toByteArray();
         } else {
            throw new AppBaseRuntimeException("global.fileholder.error.not-supported-type");
         }

         FileUtils.writeByteArrayToFile(file, binaryData);
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e, "global.fileholder.error.write-data-into-file");
      }
   }

   public File getFile() {
      return file;
   }

   public boolean hasFile() {
      return file != null;
   }

   @Override
   protected void finalize() throws Throwable {
      delete();
      super.finalize();
   }

   public void delete() {
      FileUtils.deleteQuietly(file);
   }

   public long getFileSize() {
      return file == null ? 0 : FileUtils.sizeOf(file);
   }

   public FileHolderDataType getDataType() {
      return dataType;
   }

   public String getMimeType() {
      return mimeType;
   }

   public void setMimeType(String mimeType) {
      this.mimeType = mimeType;
   }

   public String getComputeFileHash() {
      return EpmCryptoUtil.md5Encrypt(getData());
   }
}
