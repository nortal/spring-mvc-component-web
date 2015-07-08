package com.nortal.spring.cw.jsp.model.xml.dokument.vorm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nortal.spring.cw.core.util.ClassScanner;

import lombok.extern.slf4j.Slf4j;

/**
 * Vormdokumentide XML mudelite tehas
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 16.10.2013
 */
@Slf4j
@SuppressWarnings("unchecked")
public class XmlVormDokumentFactory {
   // TODO See tuleks tõsta kuskile konfi
   private static String PACKAGES = "ee.epm.portal";

   private static Map<String, Class<XmlVormDokument<?>>> MAPPINGS;

   // Lauri TODO: mõtle siia mingi normaalne intsialiseerimine välja
   static {
      MAPPINGS = new HashMap<>();

      try {
         Collection<Class<?>> classes = new ClassScanner(PACKAGES).withAssignableFilter(XmlVormDokument.class).findClasses();
         for (Class<?> clazz : classes) {
            XmlVormDokument<?> vd = ((Class<XmlVormDokument<?>>) clazz).newInstance();
            MAPPINGS.put(vd.getKood(), (Class<XmlVormDokument<?>>) clazz);
         }
      } catch (Exception e) {
         log.error("XmlModelFactory#MAPPINGS", e);
      }
   }

   public static <V extends XmlVormDokument<?>> Class<V> getXmlVormDokument(String code) {
      return (Class<V>) MAPPINGS.get(code);
   }
}
