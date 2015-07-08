package com.nortal.spring.cw.core.file.holder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;

/**
 * Antud klassi tuleb suhtuda kui failisüsteemis asuvasse kataloogi, kus asuvad kindlad failid. Tegemist on faile hoidva klassiga, mis hoiab
 * enda sees listina {@link FileHolder} objekti, mis omakorda hoiab enda sees juba konkreetset faili. Kataloog koos failidega asub serveri
 * ajutiste failide (temp) kataloogis. <br>
 * Antud klassis on implementeeritud meetod finalize, mis on abiks kataloogi ja failide kustutamisel temp kataloogist.
 * 
 * @author Margus Hanni
 * 
 */
public class DirectoryHolder {

   private final List<FileHolder> fileHolders = new ArrayList<>();
   private final File dir;

   public DirectoryHolder(String directoryName) {
      try {
         dir = File.createTempFile(directoryName, "-" + Long.toString(System.nanoTime()));
         // kustutame ja loome uuesti, vastasel juhul ei taha väga uus kataloog
         // tekkida
         dir.delete();
         dir.deleteOnExit();
         if (!dir.mkdir()) {
            throw new AppBaseRuntimeException("Error on creating directory");
         }
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e);
      }
   }

   public FileHolder getNewHolder(String filePrefix, String fileSuffix) {
      FileHolder holder = new FileHolder(createFile(filePrefix, fileSuffix));
      fileHolders.add(holder);
      return holder;
   }

   private File createFile(String filePrefix, String fileSuffix) {
      File file = new File(dir, filePrefix + (StringUtils.startsWith(fileSuffix, ".") ? fileSuffix : "." + fileSuffix));
      file.deleteOnExit();
      try {
         file.createNewFile();
      } catch (IOException e) {
         throw new AppBaseRuntimeException(e);
      }
      return file;
   }

   public FileHolder getNewHolder() {
      return getNewHolder(null, null);
   }

   public List<FileHolder> getFileHolders() {
      return fileHolders;
   }

   public boolean exists() {
      return dir.exists();
   }

   @Override
   protected void finalize() throws Throwable {
      for (FileHolder holder : fileHolders) {
         holder.delete();
      }
      FileUtils.deleteQuietly(dir);

      super.finalize();
   }
}
