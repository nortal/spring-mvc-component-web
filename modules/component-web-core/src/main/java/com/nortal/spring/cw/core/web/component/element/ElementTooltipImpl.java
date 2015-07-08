package com.nortal.spring.cw.core.web.component.element;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.web.component.GenericElement;
import com.nortal.spring.cw.core.web.util.ComponentUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * Elemendi abiinfo tingimuslik kuvamise implementatsioon
 * 
 * @author Margus Hanni
 * @since 29.11.2013
 */
public class ElementTooltipImpl implements ElementTooltip {

   @Autowired
   private CwMessageSource messageSource;

   private static final long serialVersionUID = 1L;

   private final AbstractSimpleElement element;
   private String tooltipMessageCode;
   private ElementTooltipVisibility tooltipVisibility = new ElementTooltipVisibility() {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean isVisible(ElementTooltip element) {
         return StringUtils.isNotEmpty(getText());
      }
   };

   public ElementTooltipImpl(AbstractSimpleElement element) {
      this.element = element;
      ComponentUtil.injectSpringResources(this);
   }

   @Override
   public String getText() {
      Lang lang = RequestUtil.getActiveLang();

      StringBuilder builder = new StringBuilder();
      if (!messageSource.isSimpleText(element.getFullLabel())) {
         String msgCode = element.getFullLabel() + ".tooltip";
         String message = messageSource.resolve(msgCode, lang);
         if (!StringUtils.equals(msgCode, message) && message != null) {
            builder.append(message);
         }
      }

      if (StringUtils.isNotEmpty(this.tooltipMessageCode)) {
         if (builder.length() != 0) {
            builder.append("<br/>");
         }

         String actualTooltipMessageCode = this.tooltipMessageCode;
         if (StringUtils.isNotEmpty(actualTooltipMessageCode) && !messageSource.isGlobalOrSimpleText(actualTooltipMessageCode)) {
            actualTooltipMessageCode = element.getFullLabel() + GenericElement.ID_DELIMITER + actualTooltipMessageCode;
         }

         String message = messageSource.resolve(actualTooltipMessageCode, lang);
         if (!StringUtils.equals(actualTooltipMessageCode, message)) {
            builder.append(message);
         }
      }
      return builder.toString();
   }

   public void setMessageCode(String tooltipMessageCode) {
      this.tooltipMessageCode = tooltipMessageCode;
   }

   @Override
   public boolean isVisible() {
      return tooltipVisibility.isVisible(this);
   }

   @Override
   public void setVisibility(ElementTooltipVisibility tooltipVisibility) {
      this.tooltipVisibility = tooltipVisibility;
   }

}
