package com.nortal.spring.cw.core.web.helper;

import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.i18n.model.Lang;

/**
 * @author Margus Hanni
 */
public class UrlHelper {

   public static final String URL_SEPARATOR = "/";

   // The request URL suffix added when forwarding to Spring MVC components
   public static final String COMPONENT_FORWARD_SUFFIX = "/";

   // Common prefix for static content
   public static final String STATIC_FILE_PREFIX = "/static/";

   public static String constructUrl(Class<?> controller, Lang lang) {
      String path = getUrlFromRequestMapping(controller);
      return UrlHelper.URL_SEPARATOR.concat(lang.getCode().toLowerCase()).concat(path);
   }

   public static String getUrlFromRequestMapping(Class<?> controller) {
      return controller.getAnnotation(RequestMapping.class).value()[0];
   }
}
