package com.nortal.spring.cw.core.junit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.util.LangUtil;

/**
 * @author Sander Soo
 * @since 12.02.2014
 */
public class LangUtilTest {
   private Map<Lang, String> dataMap;
   private String value;

   @Before
   public void setUp() {
      value = "Eesti asjad";
      dataMap = new HashMap<Lang, String>();
      dataMap.put(Lang.ET, value);
   }

   @Test
   public void testGetLocaleDataWithoutFallback() {
      String localeData = LangUtil.getLocaleData(dataMap, Lang.ET, false);
      assertEquals(value, localeData);
   }

   @Test
   public void testGetLocaleDataWithoutFallbackExistsLocally() {
      String localeData = LangUtil.getLocaleData(dataMap, Lang.AA, false);
      assertNull(localeData);
   }

   @Test
   public void testGetLocaleDataWithFallbackExistsLocally() {
      String localeData = LangUtil.getLocaleData(dataMap, Lang.ET, true);
      assertEquals(value, localeData);
   }

   @Test
   public void testGetLocaleDataWithFallbackDoesNotExistsLocally() {
      String localeData = LangUtil.getLocaleData(dataMap, Lang.AA, true);
      assertNull(localeData);
   }
}
