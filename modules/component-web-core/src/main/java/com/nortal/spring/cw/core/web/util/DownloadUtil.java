package com.nortal.spring.cw.core.web.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.nortal.spring.cw.core.model.FileHolderModel;
import com.nortal.spring.cw.core.web.factory.FileStreamFactory;

/**
 * Utiliit, kuhu on koondatud faili alla laadimisega seotud toimingud
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 04.04.2014
 */
public final class DownloadUtil {

   /**
    * Faili alla laadimine
    * 
    * @param response
    *           {@link HttpServletResponse}
    * @param fileModel
    *           {@link FileModel}
    * @throws IOException
    */
   public static void download(HttpServletResponse response, FileHolderModel fileModel) throws IOException {

      if (fileModel.getFileHolder() == null) {
         InputStream inputStream = FileStreamFactory.instance(FileStreamFactory.DATABASE).getFileStream(fileModel);

         if (fileModel.getFileSize() <= Integer.MAX_VALUE) {
            response.setContentLength(fileModel.getFileSize().intValue());
         }
         download(response, fileModel.getFileColumnName(), fileModel.getFilename(), inputStream);
         inputStream.close();
      } else {
         download(response, fileModel.getFileColumnName(), fileModel.getFilename(), fileModel.getFileHolder().getData());
      }
   }

   /**
    * Faili alla laadimine
    * 
    * @param response
    *           {@link HttpServletResponse}
    * @param mimeType
    *           {@link String} Faili tüüp
    * @param filename
    *           {@link String} Faili nimi
    * @param data
    *           {@link String} Faili sisu
    * @throws IOException
    */
   public static void download(HttpServletResponse response, String mimeType, String filename, String data) throws IOException {
      download(response, mimeType, filename, data.getBytes());
   }

   /**
    * Faili alla laadimine
    * 
    * @param response
    *           {@link HttpServletResponse}
    * @param mimeType
    *           {@link String} Faili tüüp
    * @param filename
    *           {@link String} Faili nimi
    * @param data
    *           Faili sisu
    * @throws IOException
    */
   public static void download(HttpServletResponse response, String mimeType, String filename, byte[] data) throws IOException {
      prepareDownload(response, mimeType, filename);
      response.setContentLength(data.length);
      response.getOutputStream().write(data);
   }

   /**
    * Faili alla laadmine
    * 
    * @param response
    *           {@link HttpServletResponse}
    * @param mimeType
    *           {@link String} Faili tüüp
    * @param filename
    *           {@link String} Faili nimi
    * @param inputStream
    *           {@link InputStream} Faili sisu
    * @throws IOException
    */
   public static void download(HttpServletResponse response, String mimeType, String filename, InputStream inputStream) throws IOException {

      prepareDownload(response, mimeType, filename);

      BufferedInputStream bis = new BufferedInputStream(inputStream);
      int ch = 0;
      byte[] buf = new byte[10000];

      while ((ch = bis.read(buf)) >= 0) {
         response.getOutputStream().write(buf, 0, ch);
      }
   }

   /**
    * Faili alla laadimise ette valmistamine. Faili alla laadimiseks lisatakse {@link HttpServletResponse} juurde faili laadimist toetavad
    * päringu vastuse päised
    * 
    * @param response
    *           {@link HttpServletResponse}
    * @param mimeType
    *           Faili tüüp
    * @param filename
    *           Faili nimi
    */
   public static void prepareDownload(HttpServletResponse response, String mimeType, String filename) {
      response.setHeader("Cache-Control", "private");
      response.setHeader("Pragma", "private");
      response.setHeader("Content-Transfer-Encoding", "binary");

      response.setContentType(mimeType);
      response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

      Cookie cookie1 = new Cookie("downloadToken", "true");
      cookie1.setMaxAge(20);
      response.addCookie(cookie1);
   }
}
