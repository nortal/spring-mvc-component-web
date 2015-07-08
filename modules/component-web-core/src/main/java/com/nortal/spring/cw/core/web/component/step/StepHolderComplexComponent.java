package com.nortal.spring.cw.core.web.component.step;

import java.util.Map;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;
import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.composite.complex.AbstractComplexComponent;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.event.ButtonElement;
import com.nortal.spring.cw.core.web.component.event.LinkElement;
import com.nortal.spring.cw.core.web.component.global.ElementAction;
import com.nortal.spring.cw.core.web.util.RequestUtil;

import lombok.Getter;

/**
 * Tegemist on sammukomponendiga, mis hoiab enda sees sammul kuvatavaid komponente. Komponendil peab olema defineeritud
 * {@link StepSaveHandler}, mille implementatsioon kutsutakse välja järgmisse sammu edasi liikumisel või lihtsalt sammu salvestamisel. <br>
 * Komponendis on realiseeritud järgmiste sündmuste ({@link EventHandler}) töötlus:<br>
 * <ul>
 * <li>{@link ElementAction#BUTTON_PAGE_SAVE_EVENT} {@link StepHolderComplexComponent#saveStep(ControllerComponent, Map)}</li>
 * <li>
 * {@link ElementAction#BUTTON_PAGE_SAVE_NEXT_EVENT} {@link StepHolderComplexComponent#goSaveNext(ControllerComponent, Map)}</li>
 * <li>{@link ElementAction#BUTTON_PAGE_NEXT_EVENT} {@link StepHolderComplexComponent#goNext(ControllerComponent, Map)}</li>
 * <li>
 * {@link ElementAction#LINK_PAGE_BACK_EVENT} {@link StepHolderComplexComponent#goBack(ControllerComponent, Map)}</li>
 * <ul>
 * 
 * @author Margus Hanni
 * 
 */
public class StepHolderComplexComponent extends AbstractComplexComponent {

   private static final long serialVersionUID = 1L;
   private final StepSaveHandler saveHandler;
   @Getter
   private int stepNr;

   /**
    * : Komponendi nime eesliides, mille lõpp asendatakse vastava sammunumbriga
    */
   public static final String COMPONENT_NAME = "step%d";

   /**
    * Konstroktor, koos argumentidega {@link StepSaveHandler} ja {@code stepNr}. {@code stepNr} tähistab sammu numbrit, millesse antud
    * sammukomponent kuulub. Esimest sammu tähistab number 1
    * 
    * @param saveHandler
    *           {@link StepSaveHandler}
    * @param stepNr
    *           {@link Integer}
    */
   public StepHolderComplexComponent(StepSaveHandler saveHandler, int stepNr) {
      super(String.format(COMPONENT_NAME, stepNr));
      this.saveHandler = saveHandler;
      this.stepNr = stepNr;
   }

   /**
    * Meetod tagastab aktiivse sammu numbri
    * 
    * @return
    */
   public int getActiveStepNr() {
      return ((StepComplexComponent) getParent()).getActiveStepNr();
   }

   /**
    * Meetod tagastab sammude koguarvu
    * 
    * @return
    */
   public int getStepCount() {
      return ((StepComplexComponent) getParent()).getStepCount();
   }

   /**
    * Meetod tagastab <code>true</code>, kui aktiivseks sammuks on esimene samm
    * 
    * @return {@link Boolean}
    */
   public boolean isFirstStep() {
      return ((StepComplexComponent) getParent()).isFirstStep();
   }

   /**
    * Meetod tagastab <code>true</code>, kui aktiivseks sammuks on viimane samm
    * 
    * @return {@link Boolean}
    */
   public boolean isLastStep() {
      return ((StepComplexComponent) getParent()).isLastStep();
   }

   /**
    * Tegemist on meetodiga, mis kutsutakse välja sündmuse {@link ElementAction#BUTTON_PAGE_SAVE_EVENT} käivitumisel. Kui komponentide sisu
    * valideerimine õnnestus ning on eksisteerib {@link StepSaveHandler}, kutsutakse omakorda välja andmete salvestamine, ehk
    * {@link StepHolderComplexComponent#saveStep()}
    * 
    * @param cc
    *           {@link ControllerComponent}
    */
   @EventHandler(eventName = ElementAction.BUTTON_PAGE_SAVE_EVENT)
   public void saveStep() {
      this.validate();
      this.convert();
      if (saveHandler != null) {
         saveHandler.saveStep(getActiveStepNr());
      }
   }

   /**
    * Tegemist on meetodiga, mis kutsutakse välja sündmuse {@link ElementAction#BUTTON_PAGE_SAVE_NEXT_EVENT} käivitumisel. Ennem järgmisse
    * sammu edasi liikumist, kutsutakse välja sammusalvestamise meetod {@link StepHolderComplexComponent#saveStep(ControllerComponent)}. Kui
    * elementide valideerimisel esines vigu, siis järgmisse sammu edasi suunamist ei toimu. Kui valideerimise vigu ei esine kutsutakse välja
    * {@link StepComplexComponent#goNext()}
    * 
    * @param cc
    *           {@link ControllerComponent}
    */
   @EventHandler(eventName = ElementAction.BUTTON_PAGE_SAVE_NEXT_EVENT)
   public void goSaveNext() {
      saveStep();
      if (getBindingResult().hasErrors()) {
         return;
      }
      getStepComplexComponent().goNext();
   }

   /**
    * Tegemist on meetodiga, mis kutsutakse välja sündmuse {@link ElementAction#BUTTON_PAGE_NEXT_EVENT} käivitumisel. Kui valideerimise vigu
    * ei esine kutsutakse välja {@link StepComplexComponent#goNext()}
    * 
    */
   @EventHandler(eventName = ElementAction.BUTTON_PAGE_NEXT_EVENT)
   public void goNext() {
      getStepComplexComponent().goNext();
   }

   /**
    * Tegemist on meetodiga, mis kutsutakse välja sündmuse {@link ElementAction#LINK_PAGE_BACK_EVENT} käivitumisel. Kutsutakse välja
    * {@link StepComplexComponent#goBack()}
    * 
    */
   @EventHandler(eventName = ElementAction.LINK_PAGE_BACK_EVENT)
   public void goBack() {
      getStepComplexComponent().goBack();
   }

   /**
    * Meetod tagastab sammukomponendi ülemobjekti, mille see antud sammukomponent elab.
    */
   public StepComplexComponent getStepComplexComponent() {
      return (StepComplexComponent) getParent();
   }

   /**
    * Sammukomponenti uue sündmuse nupu lisamine
    * 
    * @param eventElement
    *           {@link ButtonElement}
    * @return {@link EventElement}
    */
   public EventElement addMainButton(ButtonElement eventElement) {
      return getPageActions().addMainButton(eventElement);
   }

   /**
    * Sammukomponenti uue teisejärgulise sündmuse nupu lisamine
    * 
    * @param eventElement
    *           {@link LinkElement}
    * @return {@link EventElement}
    */
   public EventElement addSecondaryButton(LinkElement eventElement) {
      return getPageActions().addSecondaryButton(eventElement);
   }

   /**
    * Sammukomponendis tagasi liikumine. Map peab sisaldama parameetrit {@link RequestUtil#PARAM_EPM_EVT_PARAM}. Täiendavalt ei ole lubatud
    * 
    * @param params
    */
   @EventHandler(eventName = "goToLastStep")
   public void goToLastStep(Map<String, String> params) {
      int stepNr = Integer.valueOf(params.get(RequestUtil.PARAM_EPM_EVT_PARAM));

      // ei luba aktiivsest sammust edasi liikuda, liigume vaid tagasi
      if (getActiveStepNr() <= stepNr) {
         throw new AppBaseRuntimeException("Only back movement is allowed");
      }

      ((StepComplexComponent) getParent()).goTo(stepNr);
   }
}
