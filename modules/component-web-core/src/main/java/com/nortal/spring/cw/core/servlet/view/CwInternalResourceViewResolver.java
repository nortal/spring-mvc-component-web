package com.nortal.spring.cw.core.servlet.view;

import java.io.Serializable;

import org.springframework.web.servlet.ViewResolver;

import com.nortal.spring.cw.core.web.component.composite.Component;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 20.05.2015
 */
public interface CwInternalResourceViewResolver extends ViewResolver, Serializable {

   String getComponentClassSuffix();

   String getViewPackage();

   String getDefaultView(Component component);

   String getViewPath(Component component);
}
