package com.nortal.spring.cw.core.xml.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.nortal.spring.cw.core.util.ClassScanner;
import com.nortal.spring.cw.core.xml.XmlRootModel;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 16.10.2013
 */
@SuppressWarnings("unchecked")
@Slf4j
public class XmlModelFactory {
   // TODO See tuleks tõsta kuskile konfi
   private static String PACKAGES = "ee.epm.portal";

   private static Map<String, Class<? extends XmlRootModel>> MAPPINGS;

   // Lauri TODO: mõtle siia mingi normaalne intsialiseerimine välja
   static {
      MAPPINGS = new HashMap<>();

      try {
         Collection<Class<?>> classes = new ClassScanner(PACKAGES).withAnnotationFilter(XmlRootElement.class).findClasses();
         for (Class<?> clazz : classes) {
            if (XmlRootModel.class.isAssignableFrom(clazz)) {
               XmlRootElement rootElement = clazz.getAnnotation(XmlRootElement.class);
               MAPPINGS.put(rootElement.name(), (Class<? extends XmlRootModel>) clazz);
            }
         }
      } catch (Exception e) {
         log.error("XmlModelFactory#MAPPINGS", e);
      }

      log.debug("MAPPINGS: " + MAPPINGS);
   }

   public static <V extends XmlRootModel> Class<V> getXmlModel(String code) {
      return (Class<V>) MAPPINGS.get(code);
   }
}
