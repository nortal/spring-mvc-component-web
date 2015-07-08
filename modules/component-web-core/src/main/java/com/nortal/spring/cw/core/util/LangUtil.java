package com.nortal.spring.cw.core.util;

import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 01.10.2013
 */
public class LangUtil {

   /**
    * Tagastatakse väärtus kogumist vastavalt keelele. Juhul, kui keele spetsiifilist väärtust ei leita ja vaikeväärtuse järgi otsing on
    * määratud, siis otsitakse väärtust portaali vaikimisi keele järgi.
    * 
    * @param data
    *           väärtuste kogum
    * @param lang
    *           otsingu keele
    * @param fallback
    *           vaikeväärtuse otsingu määrang
    * @return
    */
   @SuppressWarnings("unchecked")
   public static <V> V getLocaleData(Map<Lang, ?> data, Lang lang, boolean fallback) {
      V result = null;
      if (MapUtils.isNotEmpty(data)) {
         if (lang != null && data.containsKey(lang)) {
            result = (V) data.get(lang);
         } else {
            Lang defaultLanguage = RequestUtil.getDefaultLanguage();
            if (fallback && data.containsKey(defaultLanguage)) {
               result = (V) data.get(defaultLanguage);
            }
         }
      }
      return result;
   }
}
