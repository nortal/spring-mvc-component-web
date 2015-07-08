package com.nortal.spring.cw.core.web.component.element;

import java.util.Collection;

/**
 * Tegemist on liidese kirjeldusega, mis täiendavalt implementeerib liidest {@link FormElement}. Antud liidest implementeerivad kõik
 * elemendid, mis sisaldavad endas mingisuguses vormis andmeid, olgu siis tegu lihtsate andmetega nagu {@link String} või loendeid
 * {@link Collection}
 * 
 * @author Margus Hanni
 * 
 */
public interface FormDataElement extends FormElement, FormElementMarkedState {

   /**
    * Meetod tagastab mudelobjekti, mille üksik välja (<i>property</i>) väärtus antud element sisaldab ning hiljem peale konvertimist
    * {@link FormDataElement#convert()} kirjutab elemendi sees oleva väärtuse tagasi mudelobjekti
    * 
    * @return {@link Object}
    */
   Object getEntity();

   /**
    * Mudelobjekti määramine, mille üksik välja (<i>property</i>) väärtus antud element sisaldab ning hiljem peale konvertimist
    * {@link FormDataElement#convert()} kirjutab elemendi sees oleva väärtuse tagasi mudelobjekti.
    * 
    * @param entity
    *           {@link Object}
    */
   void setEntity(Object entity);

   /**
    * Meetodi välja kutsumisel kirjutatakse elemendi väärtuse tagasi olemobjekti
    */
   void convert();

   /**
    * Meetodi välja kutsumisel rakendatakse elemendi spetsiifilised vailidaatorid, mis implementeerivad liidest {@link ElementValidator}
    * ning täiendavate piirangute implementatsioonid, mis laiendavad klassi {@link AbstractValidator}
    */
   void validate();

   /**
    * Meetodi välja kutsumisel rakendatakse elemendi spetsiifilised kitsenduste kontrollid, mis implementeerivad liidest
    * {@link ElementConstraint} ning täiendavate piirangute implementatsioonid, mis laiendavad klassi {@link AbstractConstraint}
    */
   void checkConstraints();

   /**
    * Elemendile toore väärtuse lisamine. Väärtuse tüüp peab vastama elemendi enda geneerilisele tüübile
    * 
    * @param value
    *           {@link Object}
    */
   void setRawValue(Object value);

   /**
    * Meetod tagastab elemendi väärtuse toorena
    * 
    * @return {@link Object}
    */
   Object getRawValue();

   /**
    * Meetod tagastab <code>true</code> kui AJAX põhine valideerimine on sisse lülitatud, mis tähendab seda, et kui kasutajaliideses välja
    * väärtus muutub saadetakse välja väärtus valideerimiseks elemendi validaatorisse. Vea korral kuvatakse vastav veateade
    * 
    * @return {@link Boolean}
    */
   boolean isUseAjaxValidation();

   /**
    * AJAX põhine valideerimine on sisse/välja lülitamine
    * 
    * @param useAjaxValidation
    *           {@link Boolean}
    */
   void setUseAjaxValidation(boolean useAjaxValidation);

   /**
    * Elemendi kohustuslikuks/mitte kohustuslikuks määramine. Kui element on kohustuslik, kuvatakse kasutusliideses elemendi juures * ning
    * salvestamisel rakenduvad kohustuslikkuse kontrollid
    * 
    * @param mandatory
    *           {@link Boolean}
    */
   void setMandatory(boolean mandatory);

   /**
    * Meetod tagastab <code>true</code> juhul kui tegemist on kohustusliku elemendiga, mis tähendab seda et antud elemendi väärtus ei tohi
    * olla tühi
    * 
    */
   boolean isMandatory();

   /**
    * Meetod tagastab TRUE kui elemendi juures on määratud andmeolem ning andmeolemis olev välja väärtus on erinev elemendi enda väärtusega
    * 
    * @return {@link Boolean}
    */
   boolean isChanged();

   /**
    * HTML tagide teisaldmise või mitte teisaldamise sisse/välja lülitamine. Kui teisaldamine on sisse lülitatud asendatakse esitluskihis
    * elemendi väärtuse kuvamisel sümbolid <,>,&,'," vastavateks HTML koodideks.<br>
    * <b>Oluline on, et sümbolite teisaldamine oleks alati sisse lülitatud ning välja tuleks see lülitada kohtades, kus XSS rünnakud ei saa
    * tekitada kahju</b>
    * 
    * @param escapeXml
    *           {@link Boolean}
    */
   void setEscapeXml(boolean escapeXml);

   /**
    * Täiendava elemendi andmestiku kontrolliva reeglite implementatsiooni lisamine
    * 
    * @param constraint
    *           {@link ElementConstraint}
    * @return {@link FormDataElement}
    */
   FormDataElement addConstraint(ElementConstraint constraint);

   /**
    * Täiendava elemendi andmestiku kontrolliva reeglite implementatsiooni lisamine
    * 
    * @param validator
    *           {@link ElementValidator}
    * @return {@link FormDataElement}
    */
   FormDataElement addValidator(ElementValidator validator);
}
