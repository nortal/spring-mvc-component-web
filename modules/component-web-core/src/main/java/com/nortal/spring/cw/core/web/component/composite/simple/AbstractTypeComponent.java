package com.nortal.spring.cw.core.web.component.composite.simple;

import com.nortal.spring.cw.core.web.component.ElementPathMap;
import com.nortal.spring.cw.core.web.component.composite.AbstractBaseComponent;

/**
 * Baaskomponendi abstraktsioon koos tüübiga. Kasutatakse lihtkomponentide juures, mis esindavad ühte kindlat mudelobjekti
 * 
 * @author Margus Hanni
 * @since 01.03.2013
 */
public abstract class AbstractTypeComponent<T> extends AbstractBaseComponent implements FormComponent, ElementPathMap {
   private static final long serialVersionUID = 1L;

   private final Class<T> objectClass;

   protected AbstractTypeComponent(String componentName, Class<T> objectClass) {
      super(componentName);
      this.objectClass = objectClass;
   }

   public Class<T> getObjectClass() {
      return objectClass;
   }

   @Override
   public void initComponentsIfNeeded() {
      if (!isInitialized()) {
         initComponent();
      }
   }

   @Override
   public String getPathKey() {
      return getId();
   }

}
