package com.nortal.spring.cw.core.junit.util;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.nortal.spring.cw.core.util.EpmCryptoUtil;

/**
 * @author Sander Soo
 * @since 12.02.2014
 */
public class EpmCryptoUtilTest {
   private static String data1;
   private static String data2;
   private static String data3;

   @BeforeClass
   public static void setUpBeforeClass() throws Exception {
      data1 = "minu andmed";
      data2 = "aeiouõaöü";
      data3 = "+-*/\\}][{6€$£@1`?=)(/&%¤#\"!~";
   }

   @Test
   public void testShaEncryptNormalLetters() {
      String shaEncryptedValue1 = EpmCryptoUtil.shaEncrypt(data1.getBytes());
      String shaEncryptedValue2 = EpmCryptoUtil.shaEncrypt(data1.getBytes());
      assertEquals(shaEncryptedValue1, shaEncryptedValue2);
   }

   @Test
   public void testShaEncryptEstonianSpecificLetters() {
      String shaEncryptedValue1 = EpmCryptoUtil.shaEncrypt(data2.getBytes());
      String shaEncryptedValue2 = EpmCryptoUtil.shaEncrypt(data2.getBytes());
      assertEquals(shaEncryptedValue1, shaEncryptedValue2);
   }

   @Test
   public void testShaEncryptRandomSymbols() {
      String shaEncryptedValue1 = EpmCryptoUtil.shaEncrypt(data3.getBytes());
      String shaEncryptedValue2 = EpmCryptoUtil.shaEncrypt(data3.getBytes());
      assertEquals(shaEncryptedValue1, shaEncryptedValue2);
   }
}
