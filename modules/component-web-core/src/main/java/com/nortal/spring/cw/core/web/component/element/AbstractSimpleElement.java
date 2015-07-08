package com.nortal.spring.cw.core.web.component.element;

import com.nortal.spring.cw.core.web.component.BaseElement;
import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Lihteelmendi üldine abstraktsioon, mis sisaldab elemendi labelit ning abiinfoteksti. Vaikeväärtusena kuvatakse abiinfo ikooni juhul, kui
 * elemendile on määratud abiinfo, ehk {@link AbstractSimpleElement#getText()} ei tagasta tühjust
 * 
 * @author Margus Hanni
 */
public abstract class AbstractSimpleElement extends BaseElement implements ElementPath {

   private static final long serialVersionUID = 1L;
   private final ElementTooltipImpl elementTooltip;
   private ElementLabel elementLabel;
   private ElementPath parentElementPath;

   /**
    * @param elementName
    */
   public AbstractSimpleElement(String elementName) {
      super(elementName);
      elementTooltip = new ElementTooltipImpl(this);
   }

   public String getLabel() {
      if (elementLabel == null) {
         return null;
      }

      return elementLabel.getLabel();
   }

   public void setLabel(final String label) {
      if (label != null) {
         this.elementLabel = ElementUtil.createSimpleLabel(label);
      }
   }

   public String getFullLabel() {
      if (getMessageSource().isGlobalOrSimpleText(getLabel())) {
         return getLabel();
      }

      return !(getParent() instanceof Component) ? getLabel() : ((Component) getParent()).getLabel() + ID_DELIMITER + getLabel();
   }

   public void setElementLabel(ElementLabel label) {
      this.elementLabel = label;
   }

   public ElementLabel getElementLabel() {
      return this.elementLabel;
   }

   public void setTooltipMessageCode(String messageCode) {
      elementTooltip.setMessageCode(messageCode);
   }

   public ElementTooltip getTooltip() {
      return elementTooltip;
   }

   @Override
   public String getPath() {
      return "element";
   }

   @Override
   public ElementPath getParentElementPath() {
      return parentElementPath;
   }

   @Override
   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;
   }

   @Override
   public void setParent(Hierarchical parent) {
      super.setParent(parent);
      if (getParentElementPath() == null) {
         this.setParentElementPath(parent);
      }
   }
}
