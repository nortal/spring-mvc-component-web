package com.nortal.spring.cw.jsp.web.portal.content.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory;
import com.nortal.spring.cw.core.web.component.event.EventElementFactory.Type;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.core.web.component.step.StepComplexComponent;
import com.nortal.spring.cw.core.web.component.step.StepHolderComplexComponent;
import com.nortal.spring.cw.core.web.component.step.StepMovementHandler;
import com.nortal.spring.cw.core.web.component.step.StepSaveHandler;
import com.nortal.spring.cw.jsp.web.portal.component.test.TestComponent;
import com.nortal.spring.cw.jsp.web.portal.content.AbstractModelPageController;
import com.nortal.spring.cw.jsp.web.portal.menu.MainMenuItemEnum;
import com.nortal.spring.cw.jsp.web.portal.menu.MenuComponent;

/**
 * @author Margus Hanni
 * 
 */
@Controller
@RequestMapping(value = { "/test/test-komponent-samm" })
@MenuComponent(menuItem = MainMenuItemEnum.TEST)
public class TestKomponentSammController extends AbstractModelPageController {

   private static final long serialVersionUID = 1L;

   @Override
   protected void initControllerComponent(ControllerComponent compositeComp) {
      compositeComp.setCaption(new ComponentCaption("#Sammu komponent"));
      compositeComp.setMenuComponent(new TestMenuComponent());
      compositeComp.add(getStepComplexComponent());
   }

   private StepComplexComponent getStepComplexComponent() {
      StepComplexComponent stepComplexComponent = new StepComplexComponent();
      stepComplexComponent.setMovementHandler(new DokumentStepMovementHandler());
      StepSaveHandler stepSaveHandler = new DokumentStepSaveHandler();

      // Step 1
      StepHolderComplexComponent holderComplexComponent = new StepHolderComplexComponent(stepSaveHandler, 1);
      holderComplexComponent.setCaption(new ComponentCaption("#Esimene samm"));

      TestComponent testComponent = new TestComponent();

      holderComplexComponent.add(testComponent);
      holderComplexComponent.addMainButton(EventElementFactory.createButton(Type.SAVE_NEXT));
      stepComplexComponent.addStep(holderComplexComponent);

      // Step 2
      holderComplexComponent = new StepHolderComplexComponent(stepSaveHandler, 2);
      holderComplexComponent.setCaption(new ComponentCaption("#Teine samm"));
      holderComplexComponent.addSecondaryButton(EventElementFactory.createLink(Type.LINK_BACK));
      stepComplexComponent.addStep(holderComplexComponent);

      return stepComplexComponent;

   }

   private class DokumentStepMovementHandler implements StepMovementHandler {
      @Override
      public void moveTo(int stepNr) {
         addControllerMessage("#Liikusime sammu: " + stepNr);
      }

      @Override
      public boolean vetoMovement(int currentStepNr, int nextStepNr) {
         return false;
      }
   }

   public class DokumentStepSaveHandler implements StepSaveHandler {
      private static final long serialVersionUID = 1L;

      @Override
      public void saveStep(int stepNr) {
         if (getControllerComponent().getFirstStepComplexComponent().validateAndConvert()) {
            addControllerMessage("#Salvestasime samu: " + stepNr);
         }
      }
   }
}
