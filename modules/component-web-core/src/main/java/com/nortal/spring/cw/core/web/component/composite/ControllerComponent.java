package com.nortal.spring.cw.core.web.component.composite;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;

import com.nortal.spring.cw.core.exception.UserAccessRestrictedException;
import com.nortal.spring.cw.core.i18n.MessageHolder;
import com.nortal.spring.cw.core.servlet.view.CwInternalResourceViewResolver;
import com.nortal.spring.cw.core.web.component.composite.complex.AbstractComplexComponent;
import com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent;
import com.nortal.spring.cw.core.web.component.modal.ModalDialogComponent;
import com.nortal.spring.cw.core.web.holder.Message;
import com.nortal.spring.cw.core.web.menu.MenuComponent;
import com.nortal.spring.cw.core.web.servlet.view.ConfigurableCustomView;
import com.nortal.spring.cw.core.web.servlet.view.ViewMode;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Main component for a controller. Allows adding sub-components, defining process-steps and supplying the datamodel to sub-components
 * 
 * @author Margus Hanni
 * @author Alrik Peets
 * @since 19.02.2013
 */
public class ControllerComponent extends AbstractComplexComponent {

   private static final long serialVersionUID = 1L;
   public final static String MODEL_COMP = RequestUtil.MODEL_COMP;

   @Autowired
   private MessageHolder messageHolder;
   @Autowired
   @Qualifier("viewResolver")
   private CwInternalResourceViewResolver viewResolver;

   private MenuComponent menuComponent;
   private Model model;
   private Map<String, String> parameters = new LinkedHashMap<>();
   private String labelPrefix;
   private String redirectUrl;

   private UserRestricted userRestricted = new UserRestricted() {
      @Override
      public boolean checkAccess() {
         return true;
      }
   };

   public ControllerComponent() {
      super(null);
   }

   public void init(Object callerController, String modelName) {
      super.setId(modelName);
      String packageName = viewResolver.getViewPackage();
      labelPrefix = StringUtils.removeEndIgnoreCase(callerController.getClass().getName(), BeanUtil.CONTROLLER_SUFFIX).toLowerCase();
      labelPrefix = StringUtils.removeStart(labelPrefix, packageName + ID_DELIMITER);
      labelPrefix = StringUtils.substringAfter(labelPrefix, ".");

      if (callerController instanceof UserRestricted) {
         this.userRestricted = (UserRestricted) callerController;
      }
   }

   /**
    * Komponendi lisamine. Kui parameeter <code>init<code> on <code>true</code> teostatakse lisaks komponendi initsialiseerimine
    * {@link Component#initComponent()}, teostatakse õiguste kontroll {@link ControllerComponent#checkUserRestrictions(Collection)} ning
    * kõige lõpus kutsutakse välja {@link Component#afterInitComponent()}
    * 
    * @param component
    *           {@link Component}
    * @param init
    *           {@link Boolean}
    */
   public void add(Component component, boolean init) {
      super.add(component);
      if (init) {
         component.initComponent();
         checkUserRestrictions(Arrays.asList(component));
         component.afterInitComponent();
      }
   }

   @Override
   public void initComponent() {
      super.initComponent();
      checkUserRestrictions();
      afterInitComponent();
   }

   public void setMenuComponent(MenuComponent menuComponent) {
      this.menuComponent = menuComponent;
      menuComponent.init();
   }

   public MenuComponent getMenuComponent() {
      return this.menuComponent;
   }

   public Model getModel() {
      return model;
   }

   public void setModel(Model model) {
      this.model = model;
   }

   @Override
   public void setLabel(String label) {
      this.labelPrefix = label;
   }

   @Override
   public String getLabel() {
      return labelPrefix;
   }

   /**
    * Meetodi välja kutsumisel kontrollitakse kas komponenti on vaja uuesti initsialiseerida, kui on siis tühendatakse holder ja
    * tagastatakse true ning eemaldatakse uuendamise lipuke. <br>
    * Täiendavalt kontrollitakse kas kontrolleri komponendi initsialiseerimiseks on kasutajal piisavalt õigusi. Õiguste kontroll peab oleva
    * eraldi implementeeritud, kasutades selleks liidest {@link UserRestricted}. Õiguste puudumise korral kutsutakse välja erind
    * {@link UserAccessRestrictedException}
    * 
    * @throws UserAccessRestrictedException
    * @return {@link Boolean}
    */
   public boolean needToInitComponent() {

      if (!userRestricted.checkAccess()) {
         throw new UserAccessRestrictedException();
      }

      boolean init = !this.isInitialized();
      if (init) {
         getComponents().clear();
         getPageActions().getMain().clear();
         getPageActions().getSecondary().clear();
      }
      return init;
   }

   /**
    * Päringuparameetri lisamine
    * 
    * @param key
    *           Parameetri võti
    * @param value
    *           Parameetri väärtus
    */
   public void addParameter(String key, String value) {
      parameters.put(key, value);
   }

   public String getParameter(String key) {
      return parameters.get(key);
   }

   public void putParameters(Map<String, String> parameters) {
      if (parameters != null) {
         this.parameters.putAll(parameters);
      }
   }

   @SuppressWarnings("unchecked")
   public Map<String, String> getParameters() {
      return MapUtils.unmodifiableMap(this.parameters);
   }

   /**
    * Basic implementation for redirecting request by this component
    * 
    * @return
    */
   public String getRedirectUrl() {
      return redirectUrl;
   }

   /**
    * Basic implementation for redirecting request by this component
    * 
    * @param redirectUrl
    */
   public void setRedirectUrl(String redirectUrl) {
      this.redirectUrl = redirectUrl;
   }

   public void setRenewComponentViewMode(ViewMode viewMode, ComplexComponent... viewModes) {
      for (ComplexComponent component : viewModes) {
         setRenewComponentViewModeInner(viewMode, component);
      }
   }

   public Component getOpenedModalDialog() {

      if (getModalComps() == null) {
         return null;
      }

      for (Component component : getModalComps()) {
         if (component.isVisible()) {
            return component;
         }
      }

      return null;
   }

   public void hideOpenedModalDialog() {
      ModalDialogComponent dialogComponent = (ModalDialogComponent) getOpenedModalDialog();
      if (dialogComponent != null) {
         dialogComponent.setVisible(false);
      }
   }

   /**
    * Meetod tagastab õiguste lisakontrollide teostamise implementatsiooni
    * 
    * @return {@link UserRestricted}
    */
   public UserRestricted getUserRestricted() {
      return userRestricted;
   }

   /**
    * Kontrolleri sees olavate komponendi äriloogiliste juurdepääsu kontrollide teostamine. Kõikidel komponentidel millel on küljes liides
    * {@link UserRestricted}, kutsutakse välja antud liidese meeotod {@link UserRestricted#checkAccess()}. Kui meetod tagastab
    * <code>false</code>, kutsutakse välja erind {@link UserAccessRestrictedException}
    * 
    * @throws UserAccessRestrictedException
    */
   protected void checkUserRestrictions() {
      checkUserRestrictions(getComponents().values());
   }

   /**
    * Komponendi äriloogiliste juurdepääsu kontrollide teostamine. Kõikidel komponentidel millel on küljes liides {@link UserRestricted},
    * kutsutakse välja antud liidese meeotod {@link UserRestricted#checkAccess()}. Kui meetod tagastab <code>false</code>, kutsutakse välja
    * erind {@link UserAccessRestrictedException}
    * 
    * @throws UserAccessRestrictedException
    */
   protected void checkUserRestrictions(Collection<Component> components) {
      for (Component component : components) {
         if (component instanceof UserRestricted) {
            if (!((UserRestricted) component).checkAccess()) {
               // kui õigustega on probleeme, siis varjame kontrollerkomponendi
               // koos vea välja kutsuva komponendiga ning kuvame veateate
               component.setVisible(false);
               this.setVisible(false);
               throw new UserAccessRestrictedException();
            }
         }
         if (component instanceof ComplexComponent) {
            checkUserRestrictions(((ComplexComponent) component).getComponents().values());
         }
      }
   }

   private void setRenewComponentViewModeInner(ViewMode viewMode, ComplexComponent complexComponent) {
      for (Component component : complexComponent.getComponents().values()) {

         if (component instanceof ConfigurableCustomView) {
            ((ConfigurableCustomView) component).setViewMode(viewMode);
         }
         if (component instanceof ComplexComponent) {
            setRenewComponentViewModeInner(viewMode, (ComplexComponent) component);
         }
      }
   }

   /**
    * Komponendi spetsiifilise teate lisamine
    * 
    * @param component
    *           {@link Component}
    * @param message
    *           {@link Message}
    */
   public void addMessage(Component component, Message message) {
      messageHolder.addMessage(component, message);
   }

   /**
    * Komponendi spetsiifiliste teadete tagastamine. Peale esmakordset meetodi välja kutsumist eemaldatakse konkreetse komponendi sõnumid
    * sõnumihoidlast
    * 
    * @param component
    *           {@link Component}
    * @return
    */
   public Map<String, Set<Message>> getMessagesAndRemove(Component component) {
      return messageHolder.getMessagesAndRemove(component);
   }
}