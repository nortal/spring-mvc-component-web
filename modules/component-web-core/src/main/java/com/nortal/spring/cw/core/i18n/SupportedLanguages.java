package com.nortal.spring.cw.core.i18n;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.support.user.CwPropertyKey;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 19.05.2015
 */
@Component
public class SupportedLanguages implements Serializable {

   private static final long serialVersionUID = 1L;

   @Autowired
   private Environment env;

   private Set<Lang> supported;

   private Lang defaultLanguage;

   @PostConstruct
   public void init() {
      defaultLanguage = env.getProperty(CwPropertyKey.DEFAULT_LANGUAGE, Lang.class);

      Set<Lang> languages = new HashSet<Lang>();

      for (String code : StringUtils.split(env.getProperty(CwPropertyKey.SUPPORTED_LANGUAGES), ",")) {
         languages.add(Lang.fromCode(code.trim().toUpperCase()));
      }

      supported = Collections.unmodifiableSet(languages);
   }

   public Set<Lang> getSupportedLanguages() {
      return supported;
   }

   public Lang getDefaultLanguage() {
      return defaultLanguage;
   }

   public boolean isSupportedLanguage(Locale locale) {
      return isSupportedLanguage(locale.getLanguage());
   }

   public boolean isSupportedLanguage(String code) {
      if (StringUtils.isEmpty(code) || !Lang.languageExists(code)) {
         return false;
      }

      Lang lang = Lang.fromCode(code);
      return lang != null && supported.contains(lang);
   }
}
