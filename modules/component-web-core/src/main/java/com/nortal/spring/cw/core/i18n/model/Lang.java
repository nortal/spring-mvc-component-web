package com.nortal.spring.cw.core.i18n.model;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public enum Lang {

   AA,
   AB,
   AF,
   AM,
   AR,
   AS,
   AZ,
   AY,
   BA,
   BE,
   BG,
   BH,
   BI,
   BN,
   BO,
   BR,
   CA,
   CO,
   CS,
   CY,
   DA,
   DE,
   DZ,
   EL,
   EN,
   EO,
   ES,
   ET,
   EU,
   FA,
   FI,
   FJ,
   FO,
   FR,
   FY,
   GA,
   GD,
   GL,
   GN,
   GU,
   HA,
   HI,
   HR,
   HU,
   HY,
   IA,
   IE,
   IK,
   IN,
   IS,
   IT,
   IW,
   JA,
   JI,
   JW,
   KA,
   KK,
   KL,
   KM,
   KN,
   KO,
   KS,
   KU,
   KY,
   LA,
   LN,
   LO,
   LT,
   LV,
   MG,
   MI,
   MK,
   ML,
   MN,
   MO,
   MR,
   MS,
   MT,
   MY,
   NA,
   NE,
   NL,
   NO,
   OC,
   OM,
   OR,
   PA,
   PL,
   PS,
   PT,
   QU,
   RM,
   RN,
   RO,
   RU,
   RW,
   SA,
   SD,
   SG,
   SH,
   SI,
   SK,
   SL,
   SM,
   SN,
   SO,
   SQ,
   SR,
   SS,
   ST,
   SU,
   SV,
   SW,
   ZH,
   ZU,
   TA,
   TE,
   TG,
   TH,
   TI,
   TK,
   TL,
   TN,
   TO,
   TR,
   TS,
   TT,
   TW,
   UK,
   UR,
   UZ,
   VI,
   VO,
   WO,
   XH,
   YO;

   public static boolean languageExists(String code) {

      for (Lang lang : values()) {
         if (StringUtils.equalsIgnoreCase(lang.getCode(), code)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isValidLanguageCode(String code) {
      if (code == null) {
         return false;
      }
      return languageExists(code);
   }

   public static Lang fromCode(String code) {
      return valueOf(code.toUpperCase());
   }

   public Lang fromLocale(Locale locale) {
      if (locale == null || locale.getLanguage() == null) {
         return null;
      }
      return fromCode(locale.getLanguage());
   }

   public String getCode() {
      return name();
   }

   public Locale toLocale() {
      return new Locale(name().toLowerCase());
   }

   public String getDisplayName() {
      return toLocale().getDisplayName();
   }
}
