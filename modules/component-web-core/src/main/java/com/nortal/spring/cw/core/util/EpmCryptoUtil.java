package com.nortal.spring.cw.core.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;

/**
 * 
 * @author Margus Hanni
 * @since 30.10.2013
 */
public final class EpmCryptoUtil {

   private static final String SHA_CIPHER = "SHA";
   private static final String MD5_CIPHER = "MD5";

   private EpmCryptoUtil() {
      super();
   }

   /**
    * Räsi arvutamine. SHA algoritmi mitte tpetamisel kutsutakse välja erind {@link RuntimeException}
    * 
    * @param what
    * @return
    */
   // TODO Antud meetod tõsta Nortal commons paketti klassi com.nortal.commons.util.security.CryptoUtil SHA <br>
   public static String shaEncrypt(byte[] what) {
      MessageDigest md = null;
      try {
         md = MessageDigest.getInstance(SHA_CIPHER);
      } catch (NoSuchAlgorithmException e) {
         throw new AppBaseRuntimeException(e);
      }
      md.update(what);

      byte[] dBytes = md.digest();

      return toHex(dBytes);
   }

   public static String md5Encrypt(byte[] data) {
      MessageDigest md = null;
      try {
         md = MessageDigest.getInstance(MD5_CIPHER);
      } catch (NoSuchAlgorithmException e) {
         throw new AppBaseRuntimeException(e);
      }
      md.update(data);

      byte[] dBytes = md.digest();

      return toHex(dBytes);
   }

   private static String toHex(byte[] dBytes) {
      char[] hex = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
      StringBuffer back = new StringBuffer();

      for (int i = 0; i < dBytes.length; ++i) {
         int d = dBytes[i];

         if (d < 0) {
            d = 256 + d;
         }

         int a = d / 16;
         int c = d % 16;
         back.append(hex[a]).append(hex[c]);
      }

      return back.toString();
   }
}
