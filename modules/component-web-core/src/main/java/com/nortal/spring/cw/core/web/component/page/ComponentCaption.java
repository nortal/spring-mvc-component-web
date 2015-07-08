package com.nortal.spring.cw.core.web.component.page;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.css.FieldElementCaptionCssClass;
import com.nortal.spring.cw.core.web.component.element.AbstractSimpleElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.event.LinkElement;
import com.nortal.spring.cw.core.web.component.form.FormElementHolder;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;

/**
 * Tegemist on üldise pealkirja komponendiga. Komponenti kasutatakse kõikide suuremate fragmentide juures nagu vorm, list. Lisaks on antud
 * komponenti võimalik kasutada ka muudes kohtades. Komponendi kasutamisel tuleb ära määrata komponendi <i>label</i> kood, mis tõlgitakse
 * ning kuvatakse. Lisaks on võimalik määrata spetsiifilisem CSS klass. Klassid on määratud enumis {@link FieldElementCaptionCssClass}.<br>
 * Pealkirja komponent hoiab enda sees erinevaid linke. Lingi lisamisel kuvatakse see välja pealkirja ja selle eraldava rea vahel. <br>
 * Vastavalt tõlgete olemasolul kuvatakse vajadusel välja ka abiinfo link. Antud juhul on oluline õige sufiksi kasutamine, milleks peab
 * olema <b>*.help</b>. Vastavuse olemasolul kuvatakse abiinfo link. Automaatseks abiinfo välja avamiseks peab väli <b>openHelpInfo</b>
 * olema <b>true</b>
 * 
 * @author Alrik Peets
 */
public class ComponentCaption extends AbstractSimpleElement implements Hierarchical {
   private final FormElementHolder elementHolder = new FormElementHolder();
   private static final long serialVersionUID = 1L;
   private static final String ELEMENT_NAME = "caption";
   private static final String HELP_SUFFIX = "help";
   private FieldElementCaptionCssClass styleClass;
   private boolean openHelpInfo;
   private int level;
   private String helpSimpleInfoText;

   public ComponentCaption() {
      this(ELEMENT_NAME, null);
   }

   public ComponentCaption(String label) {
      this(label, null);
   }

   public ComponentCaption(String label, FieldElementCaptionCssClass styleClass) {
      super(ELEMENT_NAME);
      setLabel(label != null ? label : ELEMENT_NAME);
      this.styleClass = styleClass;
   }

   public String getStyleClass() {
      return styleClass != null ? styleClass.getValue() : "";
   }

   public void setStyleClass(FieldElementCaptionCssClass styleClass) {
      this.styleClass = styleClass;
   }

   public void setParent(Component parent) {
      super.setParent(parent);
      for (FormElement item : elementHolder.getElements().values()) {
         if (item.getParent() == null) {
            item.setParent(parent);
         }
      }
   }

   /**
    * Lisame pealkirja komponendile täiendava sündmuse lingi. Kui elemendile ei ole ülemat määratud (Parent) siis lisatava lingi ülemaks
    * saab antud pealkirja komponendi ülemobjekt
    * 
    * @param link
    *           {@link LinkElement}
    * @return
    */
   public ComponentCaption addLink(final LinkElement link) {
      // kui parent, ei ole määratud, siis määrame komponendi millele kuulub
      // pealkirja komponent
      if (link.getParent() == null) {
         link.setParent(this.getParent());
      }

      elementHolder.put(link, this);
      return this;
   }

   /**
    * Lisame pealkirja komponendile täiendava sündmuse lingi. Kui elemendile ei ole ülemat määratud (Parent) siis lisatava lingi ülemaks
    * saab antud pealkirja komponendi ülemobjekt
    * 
    * @param event
    * @param label
    * @param labelArgs
    * @return
    */
   public ComponentCaption addLink(String event, String label, String... labelArgs) {
      return addLink(new LinkElement(String.valueOf(elementHolder.getElements().size()), event, label, labelArgs));
   }

   public String getHelpCode() {
      return (((Component) getParent()).getLabel() == null ? getLabel() : ((Component) getParent()).getLabel()) + ID_DELIMITER
            + HELP_SUFFIX;
   }

   public boolean isOpenHelpInfo() {
      return openHelpInfo;
   }

   public void setOpenHelpInfo(boolean openHelpInfo) {
      this.openHelpInfo = openHelpInfo;
   }

   /**
    * Tagastab HTMLile vastava pealkirja taseme (nt. HTMLis h1, h2 jne)
    * 
    * @return
    */
   public int getLevel() {
      return level;
   }

   /**
    * Seab HTMLile vastava pealkirja taseme (nt. HTMLis h1, h2 jne)
    * 
    * @param level
    * @return
    */
   public ComponentCaption setLevel(int level) {
      this.level = level;
      return this;
   }

   public String getSimpleHelpInfoText() {
      return helpSimpleInfoText;
   }

   public void setSimpleHelpInfoText(String helpInfoText) {
      this.helpSimpleInfoText = helpInfoText;
   }

   public boolean getHasSimpleHelpInfoText() {
      return StringUtils.isNotEmpty(this.helpSimpleInfoText);
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {
      return elementHolder.getElementHolder();
   }

   public Map<String, FormElement> getElements() {
      return elementHolder.getElements();
   }

   @Override
   public String getPath() {
      return ELEMENT_NAME;
   }
}
