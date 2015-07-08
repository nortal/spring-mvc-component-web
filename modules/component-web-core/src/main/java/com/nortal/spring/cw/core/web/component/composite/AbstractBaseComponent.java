package com.nortal.spring.cw.core.web.component.composite;

import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.ObjectError;

import com.nortal.spring.cw.core.i18n.model.MessageType;
import com.nortal.spring.cw.core.web.component.BaseElement;
import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.ElementVisibility;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.core.web.holder.Message;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Komponentide põhiabstraktsioon
 * 
 * @author Margus Hanni
 * @author Alrik Peets
 * @since 20.02.2013
 * @param <T>
 */
@Slf4j
public abstract class AbstractBaseComponent extends BaseElement implements Component {

   private static final long serialVersionUID = 1L;
   private ElementPath parentElementPath;

   private static final LabelNamingStrategy DEFAULT_NAMING_STRATEGY = new LabelNamingStrategy() {
      @Override
      public String getLabel(Component target) {
         return target.getParent() == null ? target.getId() : ((Component) target.getParent()).getLabel() + ID_DELIMITER + target.getId();
      }
   };

   private ComponentCaption caption;
   private boolean initialized;
   private String label;
   private ElementVisibility visibility;
   @Getter(AccessLevel.PROTECTED)
   @Setter
   private boolean honorLabelNamingStrategy = true;
   @Getter(AccessLevel.PROTECTED)
   private LabelNamingStrategy labelNamingStrategy;

   public AbstractBaseComponent(String componentName) {
      super(componentName);
   }

   @Override
   public ComponentCaption getCaption() {
      return caption;
   }

   @Override
   public ComponentCaption setCaption(ComponentCaption caption) {
      if (caption != null) {
         caption.setParent(this);
         caption.setParentElementPath(this);
      }
      this.caption = caption;
      return caption;
   }

   @Override
   /**
    * This is called from ControllerComponent during adding.
    */
   public void initComponent() {
      initialized = true;
      // Nothing more to do here
   }

   /**
    * Meetodi välja kutsumisel märgistatakse komponent uuesti initsialiseerimiseks
    */
   public void reInit() {
      this.initialized = false;
   }

   @Override
   public String getLabel() {
      if (StringUtils.isEmpty(label)) {
         return getLabelNamingStrategy() != null ? getLabelNamingStrategy().getLabel(this) : getDefaultLabelNamingStrategy().getLabel(this);
      }
      return label;
   }

   @Override
   public void setLabel(String label) {
      this.label = label;
   }

   @Override
   public boolean isInitialized() {
      return initialized;
   }

   @Override
   public void addErrorMessage(String messageCode, Object... arguments) {
      Component componentWithCaption = getComponentWithCaption(this);
      if (componentWithCaption != null) {
         ObjectError objectError = new ObjectError(componentWithCaption.getId(), new String[] { messageCode }, arguments, null);
         getBindingResult().addError(objectError);
      } else {
         log.error("Cannot find message with caption. Message not added!");
      }
   }

   public void addMessage(MessageType messageType, String message, String messageBody) {
      addMessage(new Message(messageType.getCode(), getActiveLanguage().getCode(), message, messageBody));
   }

   public void addMessageWithPrefix(String code, Object... args) {

      String lCode = code;

      if (lCode != null && !StringUtils.startsWithAny(StringUtils.lowerCase(lCode), new String[] { "global.", "#" })) {
         lCode = getLabel() + "." + lCode;
      }
      addMessage(lCode, args);
   }

   public void addMessage(String code, Object... args) {
      Message msg = Message.createMessage(code, getActiveLanguage(), args);
      addMessage(msg);
   }

   protected void addMessage(Message message) {
      Component componentWithCaption = getComponentWithCaption(this);
      if (componentWithCaption != null) {
         RequestUtil.getControllerComponent().addMessage(componentWithCaption, message);
      } else {
         log.error("Cannot find message with caption. Message not added!");
      }
   }

   /**
    * Tagastame komponendi spetsiifilised teated ning tühjendame teadete hoidla
    * 
    * @return
    */
   public Map<String, Set<Message>> getMessagesAndClear() {
      return RequestUtil.getControllerComponent().getMessagesAndRemove(this);
   }

   public void setVisibility(ElementVisibility visibility) {
      this.visibility = visibility;
   }

   public ElementVisibility getVisibility() {
      return this.visibility;
   }

   /**
    * Komponendi tingimuslik kuvamine. Kui komponendile on määratud {@link ElementVisibility} kutsutakse välja kirjeldatud implementatsioon.
    * Vastasel juhul kutsutakse välja {@link BaseElement#isVisible()}
    */
   @Override
   public boolean isVisible() {
      if (visibility == null) {
         return super.isVisible();
      }

      return visibility.isVisible(getParent());
   }

   protected LabelNamingStrategy getDefaultLabelNamingStrategy() {
      return DEFAULT_NAMING_STRATEGY;
   }

   public void setLabelNamingStrategy(LabelNamingStrategy labelNamingStrategy) {
      if (isHonorLabelNamingStrategy()) {
         this.labelNamingStrategy = labelNamingStrategy;
      }
   }

   @Override
   public String getPath() {
      return ControllerComponent.COMPOSITE_MAP_METHOD_NAME;
   }

   @Override
   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;
   }

   @Override
   public ElementPath getParentElementPath() {
      return this.parentElementPath;
   }

   @Override
   public void setParent(Hierarchical parent) {
      super.setParent(parent);
      if (getParentElementPath() == null) {
         this.setParentElementPath(parent);
      }
   }

   private Component getComponentWithCaption(Component comp) {
      if (comp.getCaption() != null && comp.getCaption().isVisible()) {
         return comp;
      } else {
         Hierarchical parent = comp.getParent();
         if (parent instanceof Component) {
            Component parentComp = (Component) parent;
            if (parentComp.getCaption() != null) {
               return parentComp;
            } else {
               return getComponentWithCaption(parentComp);
            }
         }
      }
      return null;
   }
}
