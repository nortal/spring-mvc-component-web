package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

import org.eclipse.persistence.internal.jpa.rs.metadata.model.Link;

import com.nortal.spring.cw.core.web.component.GenericElement;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;

/**
 * Tegemist on liidese kirjeldusega, mis laiendab liidest {@link GenericElement} , milles kirjeldatakse ära üldised vormelementide omadused.
 * Antud liidest implementeerivad kõik vormelemendid.<br>
 * Igal vormelemendile on võimalik lisaks määrata, et elemendi väärtuse muutumisel või sellele klikkamisel kutsutaks välja mingi
 * ettemääratud sündmus. Selleks laiendab antud liides {@link EventElement}.<br>
 * Antud liides laiendab täiendavalt liidest {@link Comparable}
 * 
 * @author Margus Hanni
 */
public interface FormElement extends Comparable<Object>, GenericElement, EventElement, Serializable {

   /**
    * Meetod tagastab konkreetse elemendi tüübi
    * 
    * @return {@link FieldElementType}
    */
   FieldElementType getElementType();

   /**
    * Elemendi stiili (CSS) klassi komplekti määramine
    * 
    * @param cssClass
    *           {@link FieldElementCssClass}
    * @return {@link FormElement}
    */
   FormElement setCssClass(FieldElementCssClass cssClass);

   /**
    * Meetod tagastab elemendi defineeritud tõlkekoodi
    * 
    * @return {@link String}
    */
   String getLabel();

   /**
    * Meetod tagastab elemendi defineeritud tõlkekoodi, mille ees on elemendi ülema tõlkekood, moodustades nii täispika elemendi tõlkekoodi
    * 
    * @return {@link String}
    */
   String getFullLabel();

   /**
    * Meetod tagastab elemendi väärtuse tekstilisel kujul, mida kasutakse esitluskihis antud elemendid väärtuse kuvamiseks
    * 
    * @return {@link String}
    */
   String getDisplayValue();

   /**
    * Elemendi abiinfo lisamine. Kui eksisteerib tõlkekood, mis algab elemendi täispika label koodiga {@link Link
    * FormElement#getFullLabel()} ning lõppeb järelliidesega <code>.tooltip</code>, siis kuvatakse elemendi juures vastav info tekst. Lisaks
    * on võimalik elemendile täiendavalt lisada infoteksti, millega tegeleb antud meetod.<br>
    * Kui eksisteerb nii tooltip tõlge ning lisatakse täiendavalt infoteks (tooltip), siis näidatakse mõlemat infoteksti korraga.
    * Tõlkekoodile lisatakse ette elemendi label {@link Link FormElement#getFullLabel()}
    * 
    * @param messageCode
    *           {@link String}
    */
   void setTooltipMessageCode(String messageCode);

   /**
    * Meetod tagastab elemendiga seotud tooltip objekti
    * 
    * @return {@link ElementTooltip}
    */
   ElementTooltip getTooltip();

   /**
    * Elemendi implementeeritava labeli kuvamise lisamine.
    * 
    * @param label
    *           {@link ElementLabel}
    */
   void setElementLabel(ElementLabel label);

}
