package com.nortal.spring.cw.core.junit.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.nortal.spring.cw.core.util.RichTextUtil;

/**
 * @author Sander Soo
 * @since 12.02.2014
 */
public class RichTextUtilTest {

   @Test
   public void testParseValueWithEmptyString() {
      String parseValue = RichTextUtil.parseValue("");
      assertEquals("", parseValue);
   }

   @Test
   public void testParseValueWithNonEmptyString() {
      String parseValue = RichTextUtil.parseValue("<h1 name=\"nimi\" class=\"klassinimi\" hidden>tekst ja jutt</h1>");
      assertEquals("tekst ja jutt", parseValue);
   }
}
