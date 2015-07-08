package com.nortal.spring.cw.core.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.web.PortResolver;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.security.web.savedrequest.SavedRequest;

import com.nortal.spring.cw.core.servlet.CwWebAttributes;

/**
 * <tt>RequestCache</tt> which stores the <tt>SavedRequest</tt> in the HttpSession.
 * 
 * The {@link DefaultSavedRequest} class is used as the implementation.
 * 
 * @author Luke Taylor
 * @since 3.0
 */
@Slf4j
public class HttpSessionRequestCache extends org.springframework.security.web.savedrequest.HttpSessionRequestCache {

   private PortResolver portResolver = new PortResolverImpl();
   private boolean createSessionAllowed = true;
   private boolean justUseSavedRequestOnGet;

   /**
    * Stores the current request, provided the configuration properties allow it.
    */
   @Override
   public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
      if (!justUseSavedRequestOnGet || "GET".equals(request.getMethod())) {
         DefaultSavedRequest savedRequest = new DefaultSavedRequest(request, portResolver);

         if (createSessionAllowed || request.getSession(false) != null) {
            // Store the HTTP request itself. Used by
            // AbstractAuthenticationProcessingFilter
            // for redirection after successful authentication (SEC-29)
            request.getSession().setAttribute(CwWebAttributes.SAVED_REQUEST, savedRequest);
            log.debug("DefaultSavedRequest added to Session: " + savedRequest);
         }
      }

   }

   @Override
   public SavedRequest getRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
      HttpSession session = currentRequest.getSession(false);

      if (session != null) {
         return (DefaultSavedRequest) session.getAttribute(CwWebAttributes.SAVED_REQUEST);
      }

      return null;
   }

   @Override
   public void removeRequest(HttpServletRequest currentRequest, HttpServletResponse response) {
      HttpSession session = currentRequest.getSession(false);

      if (session != null) {
         logger.debug("Removing DefaultSavedRequest from session if present");
         session.removeAttribute(CwWebAttributes.SAVED_REQUEST);
      }
   }

   /**
    * If <code>true</code>, will only use <code>DefaultSavedRequest</code> to determine the target URL on successful authentication if the
    * request that caused the authentication request was a GET. Defaults to false.
    */
   public void setJustUseSavedRequestOnGet(boolean justUseSavedRequestOnGet) {
      this.justUseSavedRequestOnGet = justUseSavedRequestOnGet;
   }

   /**
    * If <code>true</code>, indicates that it is permitted to store the target URL and exception information in a new
    * <code>HttpSession</code> (the default). In situations where you do not wish to unnecessarily create <code>HttpSession</code>s -
    * because the user agent will know the failed URL, such as with BASIC or Digest authentication - you may wish to set this property to
    * <code>false</code>.
    */
   public void setCreateSessionAllowed(boolean createSessionAllowed) {
      this.createSessionAllowed = createSessionAllowed;
   }

   public void setPortResolver(PortResolver portResolver) {
      this.portResolver = portResolver;
   }
}