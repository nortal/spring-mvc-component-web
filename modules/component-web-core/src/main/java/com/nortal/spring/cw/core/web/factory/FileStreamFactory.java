package com.nortal.spring.cw.core.web.factory;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import com.nortal.spring.cw.core.web.component.support.file.FileDownloadStream;

/**
 * Klass hoiab endas erinevaid failide laadimise instantse. Initsialiseerimine toimub ressursifailis
 * 
 * @author Margus Hanni
 * 
 */
@Slf4j
public class FileStreamFactory {

   private static Map<String, Class<? extends FileDownloadStream>> REGISTERED = new HashMap<>();
   public static final String DATABASE = "database";

   public static FileDownloadStream instance(String id) {
      try {
         return (FileDownloadStream) BeanUtils.instantiate(REGISTERED.get(id));
      } catch (BeanInstantiationException | SecurityException e) {
         log.error("FileStreamFactory#instance", e);
      }

      return null;
   }

   public void setRegister(Map<String, Class<? extends FileDownloadStream>> objects) {
      REGISTERED.putAll(objects);
   }
}
