package com.nortal.spring.cw.core.web.component;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.support.user.CwUserRequestInfo;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.util.ComponentUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Veebiraamistiku põhiline elemendi implementatsioon, mida kasutavad kõik veebielemendid
 * 
 * @author Margus Hanni
 * @since 07.03.2013
 */
public class BaseElement implements GenericElement {
   private static final long serialVersionUID = 1L;

   @Autowired
   private CwMessageSource messageSource;

   private String id;
   private boolean editable = false;
   private boolean visible = true;
   private Hierarchical parent;
   private ElementPath parentElementPath;

   /**
    * Igal elemendil peab olema raamistiku kontekstis nimi/identifikaator, mille alusel on võimalik teda hiljem komponendi seest üles leida.
    * Elemendi nimi peab komponendi põhiselt olema unikaalne
    * 
    * @param id
    *           Elemendi identifikaator
    */
   public BaseElement(String id) {
      this.id = id;
      if (this.getClass().getAnnotation(Component.class) == null) {
         ComponentUtil.injectSpringResources(this);
      }
   }

   @Override
   public String getFullPath() {
      return ElementUtil.getFullPath(this);
   }

   @Override
   public String getDisplayId() {
      return ElementUtil.convertPathToDisplayId(getFullPath());
   }

   @Override
   public boolean isEditable() {
      return editable;
   }

   @Override
   public void setEditable(boolean editable) {
      this.editable = editable;
   }

   @Override
   public void setVisible(boolean visible) {
      this.visible = visible;
   }

   @Override
   public boolean isVisible() {
      return visible;
   }

   public void setParent(Hierarchical parent) {
      this.parent = parent;
   }

   public String getParentDisplayId() {
      return this.parent == null ? StringUtils.EMPTY : this.parent.getDisplayId();
   }

   public Hierarchical getParent() {
      return this.parent;
   }

   /**
    * Meetod tagastab elemendi identifikaatori
    * 
    * @return {@link String}
    */
   @Override
   public String getId() {
      return id;
   }

   /**
    * Meetod tagastab aktiivse keele, mis leitakse kasutaja päringu seest, ehk {@link CwUserRequestInfo#getActiveLanguage()} vahendusel
    * 
    * @return
    */
   protected Lang getActiveLanguage() {
      return RequestUtil.getActiveLang();
   }

   /**
    * Meetod lisab elemendile veateated. Kui selline veakood on juba registreeritud elemendil, siis seda teist korda ei lisata.
    */
   @Override
   public void addElementErrorMessage(String messageCode, Object... arguments) {
      BindingResult bindingResult = getBindingResult();
      FieldError fieldError = bindingResult.getFieldError(getFullPath());

      if (fieldError == null || fieldError != null && !ArrayUtils.contains(fieldError.getCodes(), messageCode)) {
         bindingResult.rejectValue(getFullPath(), messageCode, arguments, messageCode);
      }
   }

   /**
    * Meetod tagastab üldise Spring MVC päringu tulemusi sisaldava objekti. {@link BindingResult} hoiab endas erinevaid veateateid, mis on
    * seotud kas konkreetse komponendi või elemendiga. Käesoleva objekti tagastamiseks kasutatakse
    * {@link CwUserRequestInfo#getBindingResult(Class)}
    * 
    * @return
    */
   @JsonIgnore
   public BindingResult getBindingResult() {
      return RequestUtil.getUserRequestInfo().getBindingResult(ControllerComponent.class);
   }

   /**
    * Elemendi idendifikaatori määramine
    * 
    * @param id
    *           {@link String} identifikaator nimi
    */
   @Override
   public void setId(String id) {
      this.id = id;
   }

   public ElementPath getParentElementPath() {
      return parentElementPath;
   }

   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;
   }

   public String getPath() {
      return ElementUtil.getNameForFullPath(this);
   }

   protected CwMessageSource getMessageSource() {
      return messageSource;
   }
}
