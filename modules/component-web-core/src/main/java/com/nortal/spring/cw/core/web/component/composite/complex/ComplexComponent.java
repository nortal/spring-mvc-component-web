package com.nortal.spring.cw.core.web.component.composite.complex;

import java.util.Map;

import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.step.StepComplexComponent;

/**
 * @author Margus Hanni
 * @since 01.03.2013
 */
public interface ComplexComponent extends Component {
   public static final String COMPOSITE_MAP_METHOD_NAME = "components";

   void add(Component component);

   void remove(Component component);

   void replace(Component component);

   void replace(Component oldComponent, Component newComponent);

   Map<String, Component> getComponents();

   <E extends Component> E getComponentByKey(String key);

   /**
    * Komponendi rahapõhine leidmine
    * 
    * @param path
    * @return
    */
   <E extends Component> E getComponentByPath(String... path);

   boolean isComponentExists(String componentName);

   boolean isComponentExistsAndIsVisible(String componentName);

   StepComplexComponent getStepComplexComponent();

   /**
    * Meetod tagastab esimese {@link StepComplexComponent} komponendi, mille leiab alates endast hierarhiliselt liikudes üles poole
    * 
    * @return
    */
   StepComplexComponent getFirstStepComplexComponent();
}
