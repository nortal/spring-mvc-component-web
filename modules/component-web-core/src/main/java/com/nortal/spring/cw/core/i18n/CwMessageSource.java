package com.nortal.spring.cw.core.i18n;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.AbstractMessageSource;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.i18n.model.MessageModel;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 20.05.2015
 */
public class CwMessageSource extends AbstractMessageSource implements Serializable {

   private static final long serialVersionUID = 1L;
   public static final String TEXT_MESSAGE_SYMBOL = "#";

   @Override
   protected MessageFormat resolveCode(String code, Locale locale) {

      // XXX: Hetkel on nii, et kui tekstis on üks ' siis see eemaldatakse, seega praegu on lihtsaks lahenduseks see et '=>''.
      // Foorumites ka soovitatakse nii teha. Seega loodame, et keegi ei tekita tõlkeid kus juba on sees ''.
      // While it is well documented in java.text.MessageFormat that apostrophes need to be escaped by using a double apostrophe
      String message = StringUtils.replace(code, "'", "''");

      return super.createMessageFormat(StringUtils.isEmpty(message) ? code : message, locale);
   }

   public final String resolve(MessageModel model, Lang lang) {
      return resolve(model.getCode(), lang.toLocale(), model.getParams());
   }

   public final String resolve(String code, Lang lang, Object... args) {
      return resolve(code, lang.toLocale(), args);
   }

   public final String resolve(String code, Locale locale, Object... args) {
      if (StringUtils.isNotEmpty(code) && isSimpleText(code)) {
         return code.substring(1);
      }

      String message = getMessage(code, args, locale);
      return StringUtils.isEmpty(message) ? code : message;
   }

   public final String resolveByActiveLang(String code, Object... args) {
      return resolve(code, RequestUtil.getActiveLang().toLocale(), args);
   }

   public final String resolve(String code, String languageCode) {
      return resolve(code, Lang.fromCode(languageCode).toLocale());
   }

   /**
    * Meetod tagastab <code>true</code> juhul kui sisendiks olev väärtuse alguseks on "global." või "#"
    * 
    * @param input
    *           {@link String}
    * @return {@link Boolean}
    */
   public boolean isGlobalOrSimpleText(String input) {
      return StringUtils.startsWithAny(input, new String[] { CwMessageSource.TEXT_MESSAGE_SYMBOL, "global." });
   }

   /**
    * Meetod tagastab <code>true</code> juhul kui sisendiks olev väärtuse alguseks on "#"
    * 
    * @param input
    *           {@link String}
    * @return {@link Boolean}
    */
   public boolean isSimpleText(String input) {
      return StringUtils.startsWith(input, CwMessageSource.TEXT_MESSAGE_SYMBOL);
   }
}
