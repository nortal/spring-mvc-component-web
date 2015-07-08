package com.nortal.spring.cw.core.web.component.step;

/**
 * Liides, mille implementeerimisel on võimalik kontrollida erinevate protsessisammude vahelist navigeerimist. Kui
 * {@link StepMovementHandler#moveTo(int)} tagastab <i>false</i>, siis navigeerimine katkestatakse ning kasutaja jääb olemasolevasse
 * protsessisammu
 * 
 * @author Margus Hanni
 * 
 */
public interface StepMovementHandler {

   /**
    * Etteantud protsessisammu liikumine.
    * 
    * @param stepNr
    *           Protsessisammu number
    * @return
    */
   void moveTo(int stepNr);

   /**
    * Kontrollitakse, kas liikumine sammust <code>currentStepNr</code> sammu <code>nextStepNr</code> on lubatud. Kontrollide alla võivad
    * kuuluda näiteks aktiivse sammu andmete korrektsuse kontroll. Meetod tagastab <code>true</code> olukorras kui liikumine on keelatud.
    * 
    * @param currentStepNr
    *           aktiivse sammu number
    * @param nextStepNr
    *           sammu, kuhu liikuda kavatsetakse, number
    * @return
    */
   boolean vetoMovement(int currentStepNr, int nextStepNr);

   /**
    * Tühi händleri implementatsioon, mis lubad iga liikumise sammude vahel
    */
   StepMovementHandler DEFAULT_HANDLER = new StepMovementHandler() {

      @Override
      public void moveTo(int stepNr) {
      }

      @Override
      public boolean vetoMovement(int currentStepNr, int nextStepNr) {
         return false;
      }
   };

}
