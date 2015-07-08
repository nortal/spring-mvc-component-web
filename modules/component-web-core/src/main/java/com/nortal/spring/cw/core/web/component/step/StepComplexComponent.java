package com.nortal.spring.cw.core.web.component.step;

import com.nortal.spring.cw.core.web.component.composite.complex.AbstractComplexComponent;

/**
 * Komplekskomponent, mille eesmärgiks on enda sees hoida erinevaid protsessisamme ning hõlpsustada erinevate sammude vahelist
 * navigeerimist. Erinevate sammude vahel navigeerimisel on võimalik defineerida {@link StepMovementHandler}, mille implementatsioonis on
 * võimalik kontrollida, kas kasutajal lubatakse liikuda soovitud protsessisammu või mitte. StepComplexComponent sees elavateks sammudeks
 * {@link StepHolderComplexComponent}, mis omakorda hoiavad enda sees komponente, mida antud protsessisammus soovitakse näha.
 * 
 * @author Margus Hanni
 * 
 */
public class StepComplexComponent extends AbstractComplexComponent {

   private static final long serialVersionUID = 1L;
   private boolean showStepNumbers;

   /**
    * Komponendi üldine nimi on {@value} , mida kasutatakse sammukomponendi lisamisel komplekskomponendi sisse ning hiljem selle alusel
    * komponendi leidmiseks
    */
   public static final String STEP_COMPONENT_NAME = "stepComplex";
   private int steps;
   private int activeStep = 1;
   private StepMovementHandler movementHandler = StepMovementHandler.DEFAULT_HANDLER;

   public StepComplexComponent() {
      super(STEP_COMPONENT_NAME);
   }

   /**
    * Meetod tagastab aktiivse sammu numbri. Esimese sammu number on 1
    * 
    * @return
    */
   public int getActiveStepNr() {
      return activeStep;
   }

   /**
    * Aktiivse sammu numbri määramine
    * 
    * @param activeStep
    *           Aktiise sammu number
    */
   public void setActiveStepNr(int activeStep) {
      this.movementHandler.moveTo(activeStep);
      this.activeStep = activeStep;
   }

   /**
    * Meetod tagatab defineeritud protsessisammu koguarvu
    * 
    * @return
    */
   public int getStepCount() {
      return steps;
   }

   /**
    * Meetod tagastab aktiivse sammu komponendid. Aktiivse sammu leidmiseks kasutatakse meetodit
    * {@link StepComplexComponent#getActiveStepNr()}
    * 
    * @return
    */
   public StepHolderComplexComponent getActiveStep() {
      return getStep(getActiveStepNr());
   }

   /**
    * Meetod tagastab etteantud sammu komponendi, mis vastab vastavalt komponendi sammu numbrile
    * 
    * @param stepNr
    *           Sammukomponendi number
    * @return
    */
   public StepHolderComplexComponent getStep(int stepNr) {
      return getComponentByKey(String.format(StepHolderComplexComponent.COMPONENT_NAME, stepNr));
   }

   /**
    * Meetod tagastab <i>true</i>, kui aktiivseks sammuks on esimene protsessisamm
    * 
    * @return
    */
   public boolean isFirstStep() {
      return activeStep == 1;
   }

   /**
    * Meetod tagastab <i>true</i>, kui aktiivseks sammuks on viimane protsessisamm
    * 
    * @return
    */
   public boolean isLastStep() {
      return activeStep == steps;
   }

   /**
    * Meetodi välja kutsumisel liigutakse järgmisse protsessisammu. Täiendavalt kutusutakse välja {@link StepMovementHandler}
    * implementatsioon, kus kontrollitakse kas järgmisse sammu tohib edasi liikuda. Kui händlerit ei ole eraldi määratud siis lubatakse
    * alati edasi liikuda. <br>
    * Täiendavalt kontrollitakse kas järgmine samm on nähtav {@link StepHolderComplexComponent#isVisible()}==<code>true</code>, kui ei ole
    * siis otsitakse järgmist sammu mis on. Kui aga mitte ükski järgmine samm ei ole nähtav, siis edasi liikumist ei toimu
    */
   public void goNext() {

      int goTo = activeStep;
      for (int i = activeStep + 1; i <= getStepCount(); i++) {
         if (getStep(i).isVisible()) {
            goTo = i;
            break;
         }
      }

      if (activeStep == goTo) {
         return;
      }

      // kui liikumist ei toimunud siis ei käivita ka händlderit
      if (movementHandler.vetoMovement(activeStep, goTo)) {
         return;
      }

      movementHandler.moveTo(goTo);

      if (goTo <= steps) {
         activeStep = goTo;
      }
   }

   /**
    * Meetodi välja kutsumisel liigutakse eelmisse protsessisammu. Täiendavalt kutusutakse välja {@link StepMovementHandler#moveTo}
    * implementatsioon. Kui händlerit ei ole eraldi määratud siis lubatakse alati tagasi liikuda.<br>
    * Täiendavalt kontrollitakse kas eelmine samm on nähtav {@link StepHolderComplexComponent#isVisible()}==<code>true</code>, kui ei ole
    * siis otsitakse järgmist sammu mis on. Kui aga mitte ükski järgmine samm ei ole nähtav, siis tagasi liikumist ei toimu.
    */
   public void goBack() {

      int goTo = activeStep;
      for (int i = activeStep - 1; i >= 0; i--) {
         if (getStep(i).isVisible()) {
            goTo = i;
            break;
         }
      }

      // kui liikumist ei toimunud siis ei käivita ka händlderit
      if (activeStep == goTo) {
         return;
      }

      movementHandler.moveTo(goTo);

      if (activeStep >= goTo) {
         activeStep = goTo;
      }

   }

   /**
    * Meetodi välja kutsumisel liigutakse numbrina sisendiks olevasse protsessisammu.
    */
   public void goTo(int stepNr) {
      setActiveStepNr(stepNr);
   }

   /**
    * Uue sammukomponendi lisamine, mis sisaldab endas konkreetseid komponente, mida soovitakse antud sammus välja kuvada
    * 
    * @param copmpnent
    */
   public void addStep(StepHolderComplexComponent copmpnent) {
      copmpnent.setParent(this);
      steps++;
      add(copmpnent);
   }

   /**
    * Protsessisammude vahelist liikumist kontrolliva händleri määramine
    * 
    * @param movementHandler
    *           {@link StepMovementHandler} implementatsioon
    */
   public void setMovementHandler(StepMovementHandler movementHandler) {
      this.movementHandler = movementHandler;
   }

   /**
    * Meetod tagastab <code>true</code>, kui sammude ees tuleb täiendavalt kuvada sammu number. Vaikimisi väärtuseks on <code>false</code>
    * 
    * @return {@link Boolean}
    */
   public boolean isShowStepNumbers() {
      return showStepNumbers;
   }

   /**
    * Sammu ees täiendava numbri kuvamine
    * 
    * @param showStepNumbers
    *           {@link Boolean}
    */
   public void setShowStepNumbers(boolean showStepNumbers) {
      this.showStepNumbers = showStepNumbers;
   }
}
