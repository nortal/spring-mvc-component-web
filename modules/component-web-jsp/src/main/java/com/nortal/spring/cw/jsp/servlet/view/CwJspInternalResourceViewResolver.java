package com.nortal.spring.cw.jsp.servlet.view;

import static com.nortal.spring.cw.core.web.component.GenericElement.ID_DELIMITER;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.nortal.spring.cw.core.servlet.view.CwInternalResourceViewResolver;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.helper.UrlHelper;
import com.nortal.spring.cw.core.web.servlet.view.CustomView;

/**
 * @author Margus Hanni
 * @since 01.03.2013
 */
@org.springframework.stereotype.Component
public class CwJspInternalResourceViewResolver extends InternalResourceViewResolver implements CwInternalResourceViewResolver {
   private static final long serialVersionUID = 1L;

   private String viewPackage;
   private String componentClassSuffix;

   public CwJspInternalResourceViewResolver() {
   }

   public String getComponentClassSuffix() {
      return componentClassSuffix;
   }

   public void setComponentClassSuffix(String componentClassSuffix) {
      this.componentClassSuffix = componentClassSuffix;
   }

   public String getViewPath(Component component) {
      return getPrefix().concat(getView(component)).concat(getSuffix());
   }

   private String getView(Component component) {
      String view;
      if (component instanceof CustomView) {
         view = ((CustomView) component).getCustomViewPath();
      } else {
         view = getDefaultView(component);
      }
      return StringUtils.startsWith(view, UrlHelper.URL_SEPARATOR) ? view : UrlHelper.URL_SEPARATOR + view;
   }

   public String getDefaultView(Component component) {
      String view = StringUtils.removeStart(StringUtils.removeStartIgnoreCase(component.getClass().getName(), getViewPackage()),
            String.valueOf(ID_DELIMITER));
      view = StringUtils.replace(StringUtils.removeEndIgnoreCase(view, getComponentClassSuffix()), String.valueOf(ID_DELIMITER),
            UrlHelper.URL_SEPARATOR);
      return StringUtils.startsWith(view, UrlHelper.URL_SEPARATOR) ? view : UrlHelper.URL_SEPARATOR + view;
   }

   public String getViewPackage() {
      return viewPackage;
   }

   public void setViewPackage(String viewPackage) {
      this.viewPackage = viewPackage;
   }
}
