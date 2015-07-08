package com.nortal.spring.cw.core.web.factory;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.BeanUtils;

import com.nortal.spring.cw.core.web.component.support.file.FileSaveLoad;

/**
 * 
 * @author Margus Hanni
 * 
 */
@Slf4j
public class FileSaveLoadFactory {

   private static Map<String, Class<? extends FileSaveLoad>> REGISTERED = new HashMap<>();
   public static final String DATABASE = "database";

   public static FileSaveLoad instance(String id) {
      try {
         return (FileSaveLoad) BeanUtils.instantiate(REGISTERED.get(id));
      } catch (BeanInstantiationException | SecurityException e) {
         log.error("FileStreamFactory#instance", e);
      }

      return null;
   }

   public void setRegister(Map<String, Class<? extends FileSaveLoad>> objects) {
      REGISTERED.putAll(objects);
   }
}
