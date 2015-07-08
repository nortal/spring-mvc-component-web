package com.nortal.spring.cw.core.web.holder;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.i18n.model.MessageType;
import com.nortal.spring.cw.core.web.util.RequestUtil;

import lombok.Data;

/**
 * Sõnumi objekt. Kasutatakse nii JavaSkriptis kui ka JSPs teadete kuvamiseks
 * 
 * @author Margus Hanni
 * @since 22.02.2013
 */
@Data
public class Message {

   /**
    * {@value}
    */
   public static final String MSG_BODY_SUFFIX = ".body";
   private final String message;
   private final String messageBody;
   private final String langCode;
   private final String messageType;
   private String code;

   public Message(String messageType, String langCode, String message, String messageBody) {
      this.messageType = messageType;
      this.langCode = langCode;
      this.message = message;
      this.messageBody = messageBody;
   }

   /**
    * Sõnumi objekti loomine. Tulemuseks on sõnumiobjekt, mis sisaldab põhiteadet kui ka täiendavat teadet, mille tõlke kood koosneb
    * põhitõlkekoodist ning lõppeb sufiksiga {@link Message#MSG_BODY_SUFFIX}
    * 
    * @param code
    *           {@link String} tõlke kood
    * @param lang
    *           {@link Lang} keel, kui null siis leitakse aktiivne kasutaja keel
    * @param args
    *           {@link Object[]} tõlke argumendid
    * @return {@link Message}
    */
   public static Message createMessage(String code, Lang lang, Object... args) {

      Lang activeLanguage = RequestUtil.getActiveLang();

      Lang lLang = lang;

      if (activeLanguage == null) {
         activeLanguage = RequestUtil.getDefaultLanguage();
      }

      if (lLang == null) {
         lLang = activeLanguage;
      }

      /*
       * SysMessage sysMessage = MessageCacheUtil.getMessage(code); SysMessage sysMessageBody = MessageCacheUtil.getMessage(code +
       * MSG_BODY_SUFFIX);
       * 
       * String message = MessageUtil.get(code, lLang, args); String messageBody = sysMessageBody == null ? null :
       * MessageUtil.get(sysMessageBody.getCode(), lLang, args);
       * 
       * String type = sysMessage == null ? MessageType.UNKNOWN.getCode() : sysMessage.getType().getCode();
       * 
       * Message msg = new Message(type, lLang.getCode(), message, messageBody);
       */
      // TODO: implement me
      Message msg = new Message(MessageType.ERROR.getCode(), lLang.getCode(), code, code);

      // msg.setActiveLanguageMessage(lLang != null && activeLanguage != null && lLang.equals(activeLanguage));
      msg.setCode(code);
      return msg;
   }

   public boolean isActiveLanguageMessage() {
      Lang activeLang = RequestUtil.getActiveLang();
      return langCode != null && activeLang != null && langCode.equals(activeLang.getCode());
   }
}
