package com.nortal.spring.cw.core.support.user;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.WebApplicationContext;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;

/**
 * @author Margus Hanni
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CwUserRequestInfo implements Serializable {
   private static final long serialVersionUID = 1L;

   private Lang activeLanguage;
   private String activeMainMenu;
   private String contextPath;
   private String applicationPath;
   private String serverUrl;
   private ControllerComponent controllerComponent;
   private long elementTabindex = 0;
   private Map<String, String> requestParameters = new LinkedHashMap<>();

   private Map<Class<?>, BindingResult> bindingResultMap = new HashMap<>();

   public Lang getActiveLanguage() {
      return activeLanguage;
   }

   public void setActiveLanguage(Lang activeLanguage) {
      this.activeLanguage = activeLanguage;
   }

   public String getContextPath() {
      return contextPath;
   }

   public void setContextPath(String contextPath) {
      this.contextPath = contextPath;
   }

   public String getApplicationPath() {
      return applicationPath;
   }

   public void setApplicationPath(String applicationPath) {
      this.applicationPath = applicationPath;
   }

   public String getApplicationRequestKey(Class<?> callerClass) {
      return getApplicationRequestKey(callerClass, applicationPath);
   }

   public String getApplicationRequestKey(Class<?> callerClass, String applicationPath) {
      return callerClass.getName().concat("_").concat(activeLanguage.getCode()).concat("_").concat(applicationPath);
   }

   public String getActiveMainMenu() {
      return activeMainMenu;
   }

   public void setActiveMainMenu(String activeMainMenu) {
      this.activeMainMenu = activeMainMenu;
   }

   public BindingResult getBindingResult(Class<?> targetClass) {
      return bindingResultMap.get(targetClass);
   }

   public void addBindingResult(BindingResult bindingResult) {
      Object target = bindingResult.getTarget();
      if (target != null) {
         this.bindingResultMap.put(target.getClass(), bindingResult);
      }
   }

   public String getServerUrl() {
      return serverUrl;
   }

   public void setServerUrl(String serverUrl) {
      this.serverUrl = serverUrl;
   }

   /**
    * Meetod tagastab aktiivse päringu {@link ControllerComponent} objekti
    * 
    * @return {@link ControllerComponent}
    */
   public ControllerComponent getControllerComponent() {
      return controllerComponent;
   }

   /**
    * Aktiivse {@link ControllerComponent} määramine
    * 
    * @param controllerComponent
    *           {@link ControllerComponent}
    */
   public void setControllerComponent(ControllerComponent controllerComponent) {
      this.controllerComponent = controllerComponent;
   }

   /**
    * Meetod tagastab elemendi järgmise tabindexi. Kasutatakse sisendväljade kui ka nuppude järjestamiseks
    * 
    * @return {@link Long}
    */
   public long getNextElementTabindex() {
      return ++elementTabindex;
   }

   /**
    * Meetod tagastab päringu parameetri väärtuse vastavalt argumendiks olevale võtmele. Kui sellist parameetrit ei eksisteeri tagastatakse
    * <code>null</code>
    * 
    * @param parameterKey
    *           {@link String} Parameetri võti
    * @return {@link String}
    */
   public String getRequestParameter(String parameterKey) {
      return requestParameters.get(parameterKey);
   }

   /**
    * Eelmise päringuga serverisse saadetud päringu parameetrid
    * 
    * @param requestParameters
    *           {@link Map}
    */
   public void setRequestParameters(Map<String, String> requestParameters) {
      this.requestParameters = requestParameters;
   }
}
