package com.nortal.spring.cw.core.web.component.step;

import com.nortal.spring.cw.core.web.component.ElementVisibility;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.css.ButtonElementCssClass;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory.Type;

/**
 * Tegemist on sammuobjektiga toimetamist toetava abiklassiga
 * 
 * @author Margus Hanni
 * 
 */
public final class StepHolderHelper {

   private StepHolderHelper() {
      super();
   }

   public static StepHolderComplexComponent addVormDokumentSaveAndPdfButtons(StepHolderComplexComponent holderComplexComponent) {

      holderComplexComponent.addMainButton(EventElementFactory.createButton(Type.SHOW_PDF, ButtonElementCssClass.BUTTON_ALT));
      holderComplexComponent.addMainButton(EventElementFactory.createButton(Type.SAVE)).setVisibility(new ElementVisibility() {
         private static final long serialVersionUID = 1L;

         @Override
         public boolean isVisible(Hierarchical component) {
            StepHolderComplexComponent complexCopmpnent = (StepHolderComplexComponent) component;
            return complexCopmpnent.getActiveStepNr() < complexCopmpnent.getStepCount();
         }
      });
      holderComplexComponent.addMainButton(EventElementFactory.createButton(Type.SAVE_NEXT)).setVisibility(new ElementVisibility() {
         private static final long serialVersionUID = 1L;

         @Override
         public boolean isVisible(Hierarchical component) {
            StepHolderComplexComponent complexCopmpnent = (StepHolderComplexComponent) component;
            return complexCopmpnent.getActiveStepNr() < complexCopmpnent.getStepCount();
         }
      });
      return holderComplexComponent;
   }

}
