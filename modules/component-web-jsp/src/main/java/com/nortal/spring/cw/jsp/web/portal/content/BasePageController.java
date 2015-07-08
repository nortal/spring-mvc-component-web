package com.nortal.spring.cw.jsp.web.portal.content;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.i18n.SupportedLanguages;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.support.user.CwUserSessionInfo;
import com.nortal.spring.cw.core.util.date.DateObject;
import com.nortal.spring.cw.core.web.component.single.editor.BigDecimalEditor;
import com.nortal.spring.cw.core.web.component.single.editor.DateTimeDateEditor;
import com.nortal.spring.cw.core.web.helper.UrlHelper;
import com.nortal.spring.cw.core.web.portal.SimpleMessageCodesResolver;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Slf4j
public class BasePageController implements Serializable {

   private static final long serialVersionUID = 1L;

   public static final String SUBMIT_CLIENT_CHANGE = "submitClientChange";
   public static final String SUBMIT_CLIENT_CHANGE_ID = "submitClientChangeId";

   @Autowired
   private CwMessageSource messageSource;

   @Autowired
   private SupportedLanguages supportedLanguages;

   @Autowired
   private CwUserRequestInfo userRequestInfo;
   @Autowired
   private CwUserSessionInfo userSessionInfo;
   @Autowired
   private MainMenuComponent mainMenuComponent;

   @InitBinder
   public void initBinder(WebDataBinder binder) {
      binder.setMessageCodesResolver(new SimpleMessageCodesResolver());
      binder.registerCustomEditor(DateObject.class, new DateTimeDateEditor());
      binder.registerCustomEditor(BigDecimal.class, new BigDecimalEditor());

      userRequestInfo.addBindingResult(binder.getBindingResult());
   }

   public String getContextPath() {
      return userRequestInfo.getContextPath();
   }

   public List<Lang> getMenuActiveLanguages() {
      return Arrays.asList(supportedLanguages.getSupportedLanguages().toArray(new Lang[] {}));
   }

   public Lang getActiveLanguage() {
      Lang lang = userRequestInfo.getActiveLanguage();
      if (lang == null) {
         lang = Lang.ET;
         log.warn("Language is not specified in user request. Use default language: " + lang);
      }

      return lang;
   }

   public String getActiveLanguageCode() {
      return getActiveLanguage().getCode().toLowerCase();
   }

   public String getActiveUrlPrefix() {
      return getContextPath() + UrlHelper.URL_SEPARATOR + getActiveLanguage().getCode().toLowerCase();
   }

   public MainMenuComponent getMainMenuComponent() {
      return mainMenuComponent;
   }

   protected String refreshActivePage() {
      return "redirect:" + getClass().getAnnotation(RequestMapping.class).value()[0];
   }

   protected String translate(String code, Object... args) {
      return messageSource.resolveByActiveLang(code, args);
   }

   protected CwUserSessionInfo getUserSessionInfo() {
      return userSessionInfo;
   }
}
