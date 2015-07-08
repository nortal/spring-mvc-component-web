package com.nortal.spring.cw.core.servlet.handler.resolver;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import com.nortal.spring.cw.core.i18n.SupportedLanguages;
import com.nortal.spring.cw.core.i18n.model.Lang;

/**
 * This class resolves the locale according to rules specified in "VisitEstonia keelsus" document. To get the correct behaviour, its
 * <tt>resolveLocale()</tt> method should be called when
 * <ul>
 * <li>the URL contains locale as request parameter ("?locale=smth") -- this is the case when forwarding internally to Spring MVC components
 * </li>
 * <li>there is no language information in the URL, neither in request parameters nor in path.</li>
 * </ul>
 * If nothing else succeeds <i>defaultLocale</i> is returned.
 * <p>
 * 
 * @author Lauri Tulmin (<a href="mailto:lauri.tulmin@webmedia.ee">lauri.tulmin@webmedia.ee</a>)
 * @author Nikita Salnikov-Tarnovski (<a href="mailto:nikita@webmedia.ee">nikita@webmedia.ee</a>)
 * @author Taimo Peelo (<a href="mailto:taimo@webmedia.ee">taimo@webmedia.ee</a>)
 */
@Component
public class CwLocaleResolver extends AbstractLocaleResolver {

   @Autowired
   private SupportedLanguages supportedLanguages;

   @PostConstruct
   public void init() {
      if (supportedLanguages.getDefaultLanguage() != null) {
         setDefaultLocale(supportedLanguages.getDefaultLanguage().toLocale());
      }

      if (getDefaultLocale() == null) {
         throw new IllegalStateException("default locale must be set");
      }

      if (!supportedLanguages.isSupportedLanguage(getDefaultLocale())) {
         throw new IllegalStateException("default locale set to unsupported value: " + getDefaultLocale());
      }
   }

   /**
    * Resolve the current locale via the given request. <br>
    * Should return a default locale as fallback in any case. <br>
    * Must be called <code>ONLY</code> when the request does <i>NOT</i> specify locale in the URL part ("http://host/locale"). Can be called
    * when request contains locale as request parameter ("locale=xyz").
    */
   @Override
   public Locale resolveLocale(HttpServletRequest request) {
      Locale result = null;
      Lang lang = Lang.fromCode(request.getParameter("locale"));
      if (lang != null) {
         result = lang.toLocale();
      }

      // this is probably reached only when deploying to localhost :)
      if (result == null) {
         result = getDefaultLocale();
      }
      return result;
   }

   @Override
   public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
      throw new UnsupportedOperationException();
   }
}
