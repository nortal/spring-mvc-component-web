package com.nortal.spring.cw.core.servlet.mvc.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.compress.utils.Charsets;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.UrlPathHelper;

import com.nortal.spring.cw.core.i18n.SupportedLanguages;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.servlet.CwWebAttributes;
import com.nortal.spring.cw.core.servlet.http.CwSessionExpireListener;
import com.nortal.spring.cw.core.support.user.CwPropertyKey;
import com.nortal.spring.cw.core.web.helper.UrlHelper;
import com.nortal.spring.cw.core.web.util.BeanUtil;

@Slf4j
public class CwUrlRewriteFilter extends OncePerRequestFilter {
   // url patterns that contain static content and should not be forwarded to
   // our main servlet
   private static final Set<String> STATIC_RESOURCE_PATTERNS = new HashSet<String>();

   @Override
   protected void initFilterBean() throws ServletException {
      setStaticResourcePatterns(Arrays.asList(BeanUtil.getBean(Environment.class).getProperty(CwPropertyKey.STATIC_RESOURCE_PATTERNS)
            .split(",")));
      super.initFilterBean();
   }

   /**
    * url patterns that contain static content and should not be forwarded to our main servlet
    * 
    * @param patterns
    */
   private void setStaticResourcePatterns(List<String> patterns) {
      for (String pattern : patterns) {
         if (!STATIC_RESOURCE_PATTERNS.contains(pattern)) {
            STATIC_RESOURCE_PATTERNS.add(pattern);
         }
      }
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
         throws ServletException, IOException {

      SupportedLanguages supportedLanguages = BeanUtil.getBean(SupportedLanguages.class);

      String actualPath = URLDecoder.decode(new UrlPathHelper().getPathWithinApplication(request), Charsets.UTF_8.name());

      String path = StringUtils.removeStart(actualPath, UrlHelper.URL_SEPARATOR);

      String[] requestParts = path.split(UrlHelper.URL_SEPARATOR);

      String lang = requestParts.length > 0 ? requestParts[0] : null;

      // kui staatiline leht, siis suuname kohe sellele edasi
      if (isStaticResource(actualPath)) {
         // kui keel on täpsustatud siis tegu keelelise staatilise ressursiga
         if (supportedLanguages.isSupportedLanguage(lang)) {
            forwardToComponent(request, response, lang, removeLanguageFromPath(requestParts));
         } else {
            filterChain.doFilter(request, response);
         }
         return;
      }

      if (requestParts.length == 0 || !supportedLanguages.isSupportedLanguage(requestParts[0])) {
         redirectToLang(request, response, "", supportedLanguages.getDefaultLanguage());
         return;
      }

      setUserData(request);

      forwardToComponent(request, response, lang, removeLanguageFromPath(requestParts));
   }

   private String removeLanguageFromPath(String[] requestParts) {
      return UrlHelper.URL_SEPARATOR + StringUtils.join(Arrays.copyOfRange(requestParts, 1, requestParts.length), UrlHelper.URL_SEPARATOR);
   }

   private void redirectToPrimaryUrl(HttpServletRequest request, HttpServletResponse response, String path, String lang) {
      StringBuilder redirectUrl = new StringBuilder(request.getContextPath());
      redirectUrl.append(UrlHelper.URL_SEPARATOR);
      redirectUrl.append(lang);
      String lPath = path == null ? "home" : path;
      lPath = lPath.startsWith(UrlHelper.URL_SEPARATOR) ? lPath : (UrlHelper.URL_SEPARATOR + lPath);
      redirectUrl.append(lPath);
      response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
      response.setHeader("Location", redirectUrl.toString());
      response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
      response.setHeader("Expires", "Thu, 01 Jan 1970 00:00:00 GMT");
      response.setHeader("Connection", "close");
   }

   private void redirectToLang(HttpServletRequest request, HttpServletResponse response, String baseUrl, Lang lang) throws IOException {
      String redirectUrl = null;
      redirectUrl = new UrlPathHelper().getContextPath(request) + "/" + lang.getCode().toLowerCase() + "/" + baseUrl;

      boolean isPing = request.getRequestURI() != null;
      if (isPing) {
         log.trace("Redirect to " + redirectUrl);
      } else {
         log.debug("Redirect to " + redirectUrl);
      }
      response.sendRedirect(redirectUrl);
   }

   private RequestDispatcher getRequestDispatcher(final HttpServletRequest hsRequest, String toUrl) throws ServletException {
      final RequestDispatcher reqDisp = hsRequest.getRequestDispatcher(toUrl);
      if (reqDisp == null) {
         throw new ServletException("unable to get request dispatcher for " + toUrl);
      }

      return reqDisp;
   }

   private void setUserData(HttpServletRequest request) {
      setClientLocation(request);
   }

   private void setClientLocation(HttpServletRequest request) {
      String remoteAddr = request.getHeader("x-forwarded-for");
      if (StringUtils.isBlank(remoteAddr)) {
         remoteAddr = request.getRemoteAddr();
      }
      boolean isPing = request.getRequestURI() != null;

      if (isPing) {
         log.trace("remote address is: " + remoteAddr);
         log.trace("Referer: " + request.getHeader("Referer"));
      } else {
         log.debug("remote address is: " + remoteAddr);
         log.debug("Referer: " + request.getHeader("Referer"));
      }
   }

   /**
    * Forward request to main servlet.
    * 
    * @param request
    * @param response
    * @param lang
    *           application language
    * @param componentId
    *           path within application that will be used to service this request
    * @throws ServletException
    * @throws IOException
    */
   private void forwardToComponent(HttpServletRequest request, HttpServletResponse response, String lang, String path)
         throws ServletException, IOException {
      StringBuilder forward = new StringBuilder(UrlHelper.COMPONENT_FORWARD_SUFFIX);
      if (StringUtils.contains(path, '?')) {
         forward.append(path).append('&');
      } else {
         forward.append(path).append('?');
      }
      forward.append(CwWebAttributes.LOCALE_REQUEST_PARAM).append("=").append(lang);

      if (log.isDebugEnabled()) {
         log.debug("forward to " + forward);
      }

      boolean newSessionCreated = BooleanUtils.toBoolean((Boolean) request.getSession().getAttribute(
            CwSessionExpireListener.CW_NEW_SESSION_CREATED));

      RequestDispatcher rq = getRequestDispatcher(request, forward.toString());

      try {
         if (newSessionCreated) {
            request.getSession().removeAttribute(CwSessionExpireListener.CW_NEW_SESSION_CREATED);
         }
         rq.forward(request, response);
      } catch (Exception e) {
         if (newSessionCreated) {
            log.error("Exception forwarding to component: ", e);
            redirectToPrimaryUrl(request, response, removeLanguageFromPath(path.split("/")), lang);
            return;
         }
         throw e;
      }
   }

   private boolean isStaticResource(String path) {
      if (path.startsWith(UrlHelper.STATIC_FILE_PREFIX)) {
         return true;
      }
      for (String pattern : STATIC_RESOURCE_PATTERNS) {
         if (path.matches(pattern)) {
            return true;
         }
      }
      return false;
   }

}
