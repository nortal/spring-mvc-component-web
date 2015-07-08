package com.nortal.spring.cw.core.support.message;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.cache.model.Cacheable;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.i18n.model.MessageType;
import com.nortal.spring.cw.core.util.LangUtil;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 20.05.2015
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CwMessage implements Cacheable, Serializable {
   private static final long serialVersionUID = 1L;

   private String code;
   private MessageType type;
   private String description;
   private final Map<Lang, CwMessageText> texts = new LinkedHashMap<Lang, CwMessageText>();

   public void addTexts(Collection<CwMessageText> texts) {
      for (CwMessageText text : texts) {
         this.texts.put(text.getLang(), text);
      }
   }

   public CwMessageText getText(Lang lang) {
      return getText(lang, true);
   }

   public CwMessageText getText(Lang lang, boolean fallback) {
      Map<Lang, CwMessageText> texts = new HashMap<>();
      for (Lang item : getTexts().keySet()) {
         CwMessageText text = getTexts().get(item);
         if (StringUtils.isNotEmpty(text.getTextValue())) {
            texts.put(item, text);
         }
      }
      return LangUtil.getLocaleData(texts, lang, fallback);
   }

   @Override
   public Object getCacheKey() {
      return getCode();
   }
}
