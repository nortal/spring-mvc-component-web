package com.nortal.spring.cw.core.web.component.modal;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.web.util.ComponentUtil;

/**
 * Contains various properties for showing modal windows.
 * 
 * @author Alrik Peets
 */
public class ModalProperties implements Serializable {
   private static final long serialVersionUID = 1L;

   private static final String DEFAULT_LINK_LABEL = "show";

   @Autowired
   private CwMessageSource messageSource;

   private String linkLabel;
   private ModalSize sizeStyleClass;
   private boolean showLink = true;
   private boolean showCancelLink = true;
   private final ModalDialogComponent parent;

   public ModalProperties(ModalDialogComponent parent) {
      this.parent = parent;
      ComponentUtil.injectSpringResources(this);
   }

   public String getLinkLabel() {
      if (getParent() == null) {
         return StringUtils.isEmpty(linkLabel) ? DEFAULT_LINK_LABEL : linkLabel;
      }

      if (messageSource.isGlobalOrSimpleText(linkLabel)) {
         return linkLabel;
      }

      return getParent().getLabel() + "." + (StringUtils.isEmpty(linkLabel) ? DEFAULT_LINK_LABEL : linkLabel);
   }

   public ModalProperties setLinkLabel(String linkLabel) {
      this.linkLabel = linkLabel;
      return this;
   }

   public String getSizeStyleClass() {
      return sizeStyleClass != null ? sizeStyleClass.getValue() : "";
   }

   /**
    * @param sizeStyleClass
    *           {@link ModalSize}
    * @return
    */
   public ModalProperties setSizeStyleClass(ModalSize sizeStyleClass) {
      this.sizeStyleClass = sizeStyleClass;
      return this;
   }

   public boolean isShowLink() {
      return showLink;
   }

   public ModalProperties setShowLink(boolean showLink) {
      this.showLink = showLink;
      return this;
   }

   public ModalDialogComponent getParent() {
      return parent;
   }

   public boolean isShowCancelLink() {
      return showCancelLink;
   }

   public void setShowCancelLink(boolean showCancelLink) {
      this.showCancelLink = showCancelLink;
   }
}
