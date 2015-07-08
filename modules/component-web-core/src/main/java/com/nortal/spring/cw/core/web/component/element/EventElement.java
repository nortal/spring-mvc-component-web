package com.nortal.spring.cw.core.web.component.element;

import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.ElementVisibility;
import com.nortal.spring.cw.core.web.component.GenericElement;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.event.confirmation.ConfirmationDialog;
import com.nortal.spring.cw.core.web.component.global.ElementAction;

/**
 * Tegemist on üldise veebi komponenti laiendava liidese, mis lisab elemendile sündmuste funktsionaalsust. Vastavalt elemendi
 * implementatsioonile on toetatud sündmused <i>click</i> (sündmuse nimeks peab olema {@link ElementAction#SUBMIT_ON_CLICK}) ja
 * <i>change</i> (sündmuse nimeks peab olema {@link ElementAction#SUBMIT_ON_CHANGE}). Täiendavalt on vastavalt implementatsioonile võimalik
 * defineerida sündmuse eelne kinnituse üle küsimine, selleks peab elemendis olema implementeeritd meetod
 * {@link EventElement#getConfirmation()}
 * 
 * @author Margus Hanni
 */
public interface EventElement extends GenericElement, Hierarchical {

   /**
    * Elemendi sündmuse töötleja, mis kutsutakse välja sündmuse puhul <i>click</i>. Eventi nimeks määratakse
    * {@link ElementAction#SUBMIT_ON_CLICK}
    * 
    * @param handler
    *           {@link ElementHandler} Sündmust töötleva funktsionaalsuse implementatsioon
    */
   void setOnClickHandler(ElementHandler handler);

   /**
    * Meetod tagastab elemendi sündmuse töötleja, mis kutsutakse välja sündmuse puhul <i>click</i>
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getOnClickHandler();

   /**
    * Elemendi sündmuse töötleja, mis kutsutakse välja sündmuse puhul <i>change</i> Eventi nimeks määratakse
    * {@link ElementAction#SUBMIT_ON_CHANGE}
    * 
    * @param handler
    *           Sündmust töötleva funktsionaalsuse implementatsioon
    */
   void setOnChangeHandler(ElementHandler handler);

   /**
    * Meetod tagastab elemendi sündmuse töötleja, mis kutsutakse välja sündmuse puhul <i>change</i>
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getOnChangeHandler();

   /**
    * Meetod tagastab kinnitusakna, mis kutsutakse välja vastavalt implementatsioonile ennem konkreetse sündmuse funkstionaalsuse
    * käivitamist
    * 
    * @return
    */
   ConfirmationDialog getConfirmation();

   /**
    * Elemendi sündmuse kinnitus akna määramine, mis kutsutakse välja vastavalt implementatsioonile ennem konkreetse sündmuse
    * funkstionaalsuse käivitamist
    * 
    * @param confirmation
    *           {@link ConfirmationDialog} Kuvatav kinnitusaken
    * @return {@link ConfirmationDialog}
    */
   ConfirmationDialog setConfirmation(ConfirmationDialog confirmation);

   /**
    * Konkreetse elemendi tingimusliku nähtavuse defineerimine. Vastavalt vajadusele on võimalik elemente teatud tingimustel peita vüis siis
    * vajadusel kuvada.
    * 
    * @param elementVisibility
    *           Elemendi nähtavuse implementatsioon
    * @return {@link EventElement}
    */
   EventElement setVisibility(ElementVisibility elementVisibility);
}
