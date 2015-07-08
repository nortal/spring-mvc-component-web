package com.nortal.spring.cw.core.web.component.composite.complex;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.web.component.ElementPathMap;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.composite.AbstractBaseComponent;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.composite.ComponentType;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.composite.simple.DataHolder;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.modal.ModalDialogComponent;
import com.nortal.spring.cw.core.web.component.page.PageActions;
import com.nortal.spring.cw.core.web.component.step.StepComplexComponent;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * @author Margus Hanni
 * @since 01.03.2013
 */
public abstract class AbstractComplexComponent extends AbstractBaseComponent implements ComplexComponent, ElementPathMap {
   private static final long serialVersionUID = 1L;

   private Map<String, Component> componentHolder = new LinkedHashMap<>();
   private final PageActions pageActions = new PageActions(this);

   public AbstractComplexComponent(String componentName) {
      super(componentName);
   }

   @Override
   public void add(final Component component) {

      if (isComponentExists(component.getId())) {
         throw new AppBaseRuntimeException(String.format("Component '%s' already exists", component.getId()));
      }

      if (component.getParent() == null) {
         component.setParent(this);
      }

      if (getLabelNamingStrategy() != null) {
         component.setLabelNamingStrategy(getLabelNamingStrategy());
      }

      componentHolder.put(component.getId(), component);
   }

   @Override
   public void remove(Component component) {
      if (component != null) {
         componentHolder.remove(component.getId());
      }
   }

   public void remove(String componentName) {
      if (StringUtils.isNotBlank(componentName)) {
         componentHolder.remove(componentName);
      }
   }

   @Override
   public void replace(Component component) {
      remove(component);
      add(component);
   }

   @Override
   public void replace(Component oldComponent, Component newComponent) {
      remove(oldComponent);
      add(newComponent);
   }

   /**
    * Meetod tagastab aktiivse {@link ControllerComponent}, mis leitakse {@link CwUserRequestInfo#getControllerComponent()}
    * 
    * @return {@link ControllerComponent}
    */
   public ControllerComponent getControllerComponent() {
      return RequestUtil.getControllerComponent();
   }

   @Override
   public Map<String, Component> getComponents() {
      return componentHolder;
   }

   public Map<String, ListComponent> getListItems() {
      Map<String, ListComponent> listCompositeMap = new HashMap<>();
      for (Entry<String, Component> comp : getComponents().entrySet()) {
         if (comp.getValue() instanceof ListComponent) {
            listCompositeMap.put(comp.getKey(), (ListComponent) comp.getValue());
         }
      }

      return listCompositeMap;
   }

   @SuppressWarnings("unchecked")
   public <E extends Component> E getComponentByKey(String key) {
      return (E) getComponents().get(key);
   }

   @SuppressWarnings("unchecked")
   public <E extends Component> E getComponentByPath(String... path) {
      ComplexComponent component = null;
      for (String key : path) {
         if (component == null) {
            component = getComponentByKey(key);
         } else {
            component = component.getComponentByKey(key);
         }
      }

      return (E) component;
   }

   @SuppressWarnings("unchecked")
   public <E extends Component> E getFirstComponentByType(Class<E> type) {
      E result = null;
      for (Component item : componentHolder.values()) {
         if (type.isAssignableFrom(item.getClass())) {
            result = (E) item;
            break;
         }

         if (item instanceof AbstractComplexComponent) {
            result = ((AbstractComplexComponent) item).getFirstComponentByType(type);

            if (result != null) {
               break;
            }
         }
      }
      return result;
   }

   public <C> C getData(String key) {
      return ((DataHolder) getComponents().get(key)).getData();
   }

   @Override
   public void initComponentsIfNeeded() {
      for (Component component : getComponents().values()) {
         component.initComponentsIfNeeded();
      }
      if (!isInitialized()) {
         initComponent();
      }
   }

   public boolean isListAddRowRequest() {

      for (ListComponent listComposite : getListItems().values()) {
         if (listComposite.getProperties().isAddRowSubmit()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean validateAndConvert() {
      for (Component component : componentHolder.values()) {
         if (component.isVisible()) {
            component.validateAndConvert();
         }
      }

      return !getBindingResult().hasErrors();
   }

   @Override
   public boolean validate() {
      for (Component component : componentHolder.values()) {
         if (component.isVisible()) {
            component.validate();
         }
      }

      return !getBindingResult().hasErrors();
   }

   @Override
   public void convert() {
      for (Component component : componentHolder.values()) {
         if (component.isVisible()) {
            component.convert();
         }
      }
   }

   public void initComponent() {
      init(getComponents().values());
      super.initComponent();
   }

   private void init(Collection<Component> collections) {
      for (Component component : collections) {
         component.initComponentsIfNeeded();
         if (component instanceof ComplexComponent) {
            init(((ComplexComponent) component).getComponents().values());
         }
      }
   }

   @Override
   public void afterInitComponent() {
      afterInit(getComponents().values());
   }

   private void afterInit(Collection<Component> collections) {
      for (Component component : collections) {
         component.afterInitComponent();
         if (component instanceof ComplexComponent) {
            afterInit(((ComplexComponent) component).getComponents().values());
         }
      }
   }

   @Override
   public ComponentType getComponentType() {
      return ComponentType.COMPLEX;
   }

   public boolean isComponentExists(String componentName) {
      return componentHolder.containsKey(componentName);
   }

   public boolean isComponentExistsAndIsVisible(String componentName) {
      return isComponentExists(componentName) && getComponentByKey(componentName).isVisible();
   }

   public StepComplexComponent getStepComplexComponent() {
      return getComponentByKey(StepComplexComponent.STEP_COMPONENT_NAME);
   }

   public StepComplexComponent getFirstStepComplexComponent() {
      if (getStepComplexComponent() != null) {
         return getStepComplexComponent();
      }
      if (getParent() != null) {
         return ((ComplexComponent) getParent()).getFirstStepComplexComponent();
      }

      return null;
   }

   public void setStepComplexComponent(StepComplexComponent stepComponent) {
      stepComponent.setParent(this);
      add(stepComponent);
   }

   public PageActions getPageActions() {
      return pageActions;
   }

   public Set<Component> getModalComps() {
      Set<Component> comps = new HashSet<>();
      for (Component composite : componentHolder.values()) {
         if (composite instanceof ModalDialogComponent) {
            comps.add(composite);
         }
         if (composite instanceof ComplexComponent) {
            Set<Component> components = ((AbstractComplexComponent) composite).getModalComps();
            if (components != null) {
               comps.addAll(components);
            }
         }
         if (composite instanceof ListComponent) {
            FormElement formElement = ((ListComponent) composite).getActiveFormElement();
            if (formElement != null && formElement.getConfirmation() != null) {
               comps.add(formElement.getConfirmation());
            }
         }
      }

      return comps;
   }

   /**
    * @param labelStrategy
    */
   @Override
   public void setLabelNamingStrategy(LabelNamingStrategy labelNamingStrategy) {
      if (isHonorLabelNamingStrategy()) {
         super.setLabelNamingStrategy(labelNamingStrategy);
         if (MapUtils.isNotEmpty(componentHolder)) {
            for (Component item : componentHolder.values()) {
               item.setLabelNamingStrategy(labelNamingStrategy);
            }
         }
      }
   }

   @Override
   public void setParent(Hierarchical parent) {
      super.setParent(parent);
      if (getParentElementPath() == null) {
         this.setParentElementPath(parent);
      }
   }

   @Override
   public String getPathKey() {
      return getId();
   }
}
