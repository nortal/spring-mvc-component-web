package com.nortal.spring.cw.core.support.user;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;

/**
 * @author Margus Hanni
 * @since 05.03.2013
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CwUserSessionInfo implements Serializable {
   private static final long serialVersionUID = 1L;

   @Getter
   @Setter
   private String loginUrl;

   private Pair<String, ControllerComponent> activeCompositeHolder;

   public ControllerComponent getActiveControllerComponent(String applicationRequestKey) {
      if (existsActiveControllerComponent(applicationRequestKey)) {
         return activeCompositeHolder.getRight();
      }

      return null;
   }

   public boolean existsActiveControllerComponent(String applicationRequestKey) {
      if (activeCompositeHolder == null) {
         return false;
      }
      return activeCompositeHolder.getLeft().equals(applicationRequestKey);
   }

   public void setActiveControllerComponent(String applicationRequestKey, ControllerComponent compositeComp) {
      activeCompositeHolder = Pair.<String, ControllerComponent> of(applicationRequestKey, compositeComp);
   }
}
