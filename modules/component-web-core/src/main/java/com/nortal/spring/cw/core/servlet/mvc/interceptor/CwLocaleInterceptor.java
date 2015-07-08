package com.nortal.spring.cw.core.servlet.mvc.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import com.nortal.spring.cw.core.i18n.SupportedLanguages;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.servlet.CwWebAttributes;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 20.05.2015
 */
@Component
public class CwLocaleInterceptor extends HandlerInterceptorAdapter {

   @Autowired
   private CwUserRequestInfo userRequestInfo;

   @Autowired
   private SupportedLanguages supportedLanguages;

   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
      return preHandlerInternal(request, response);

   }

   @Override
   public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
         throws Exception {

   }

   @Override
   public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

   }

   private boolean preHandlerInternal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      requestLanguage(request);
      requestInfo(request);

      return true;
   }

   private void requestLanguage(HttpServletRequest request) {
      String locale = request.getParameter(CwWebAttributes.LOCALE_REQUEST_PARAM);
      Lang lang = supportedLanguages.getDefaultLanguage();

      if (StringUtils.isNotEmpty(locale) && Lang.languageExists(locale)) {
         lang = Lang.fromCode(locale);
      }

      userRequestInfo.setActiveLanguage(lang);
   }

   private void requestInfo(HttpServletRequest request) throws UnsupportedEncodingException {
      String path = URLDecoder.decode(new UrlPathHelper().getPathWithinApplication(request), "UTF-8");

      String serverUrl = request.getScheme() + "://" + request.getServerName();
      if (request.isSecure() && request.getServerPort() != 443 || !request.isSecure() && request.getServerPort() != 80) {
         serverUrl += ":" + request.getServerPort();
      }

      userRequestInfo.setServerUrl(serverUrl);
      userRequestInfo.setContextPath(request.getContextPath());

      userRequestInfo.setApplicationPath(path);
   }
}
