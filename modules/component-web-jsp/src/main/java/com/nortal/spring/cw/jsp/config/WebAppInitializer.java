package com.nortal.spring.cw.jsp.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.nortal.spring.cw.core.servlet.mvc.filter.CwUrlRewriteFilter;

@Order(1)
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

   @Override
   public void onStartup(ServletContext servletContext) throws ServletException {

      super.onStartup(servletContext);

      addListeners(servletContext);
      addFilters(servletContext);
      servletContext.setInitParameter("defaultHtmlEscape", "true");
   }

   private void addListeners(ServletContext servletContext) {
      servletContext.addListener(new RequestContextListener());
   }

   private void addFilters(ServletContext servletContext) {
      // FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encoding-filter", new CharacterEncodingFilter());
      // encodingFilter.setInitParameter("encoding", "UTF-8");
      // encodingFilter.setInitParameter("forceEncoding", "true");
      // encodingFilter.addMappingForUrlPatterns(null, false, "/**");
      // servletContext.addFilter("exceptionFilter", new ExceptionFilter());
   }

   @Override
   protected Filter[] getServletFilters() {
      CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
      encodingFilter.setEncoding("UTF-8");
      encodingFilter.setForceEncoding(true);

      return new Filter[] { encodingFilter, new CwUrlRewriteFilter() };
   }

   @Override
   protected WebApplicationContext createRootApplicationContext() {
      AnnotationConfigWebApplicationContext ctx = (AnnotationConfigWebApplicationContext) super.createRootApplicationContext();
      return ctx;
   }

   @Override
   protected Class<?>[] getRootConfigClasses() {
      return new Class[] { ApplicationConfig.class };
   }

   @Override
   protected Class<?>[] getServletConfigClasses() {
      return new Class[] { CwSpringMvcConfig.class };
   }

   @Override
   protected String[] getServletMappings() {
      return new String[] { "/" };
   }
}
