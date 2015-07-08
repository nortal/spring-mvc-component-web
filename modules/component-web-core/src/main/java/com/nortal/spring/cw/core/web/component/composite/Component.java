package com.nortal.spring.cw.core.web.component.composite;

import org.springframework.validation.BindingResult;

import com.nortal.spring.cw.core.i18n.model.MessageType;
import com.nortal.spring.cw.core.web.component.Convertable;
import com.nortal.spring.cw.core.web.component.GenericElement;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;
import com.nortal.spring.cw.core.web.holder.Message;

/**
 * Defines a generic component on a page. A component consists either of other components or elements.
 * 
 * @author Margus Hanni
 * @author Alrik Peets
 * @since 27.02.2013
 */
public interface Component extends GenericElement, Convertable, Hierarchical {

   /**
    * Validates and if valid converts containing components and elements.
    * 
    */
   boolean validateAndConvert();

   /**
    * Get component caption object.
    * 
    * @return
    */
   ComponentCaption getCaption();

   /**
    * Set component caption.
    * 
    * @param caption
    */
   ComponentCaption setCaption(ComponentCaption caption);

   /**
    * Initializes component.
    */
   void initComponent();

   /**
    * Is component initialized
    * 
    * @return
    */
   boolean isInitialized();

   /**
    * Initialize component if it is not initialized yet
    */
   void initComponentsIfNeeded();

   /**
    * Call after component initialization
    */
   void afterInitComponent();

   /**
    * Tagastame komponendi sildi.<br>
    * Komponendi silt koostatakse järgnevalt:<br>
    * <ul>
    * <li>Kui komponendi silti ei ole täiendavalt määratud ( {@link Component#setLabel(String)}) ning komponendil on ülemkomponent (parent),
    * koosneb sildi kood {@link Component#getParent()} + {@link Component#getLabel()} + ID_DELIMITER + {@link Component#getComponentName()}</li>
    * <li>Kui puudub ülemkomponent (parent) on sildiks {@link Component#getComponentName()}</li>
    * <li>Vastasel juhul tagastatakse määratud silt</li>
    * </ul>
    */
   String getLabel();

   /**
    * Komponendi sildi määramine
    */
   void setLabel(String label);

   /**
    * Komponendi label prefiksi leidmine. Antud labelit kasutavad teised komponendid prefiksina. <br>
    * Prefiks koosneb: <br>
    * komponendi klassi nimest ilma sufiksita<br>
    * millest eemaldatakse vaadete paketi algus {@link EpmInternalResourceViewResolver#getViewPackage()}
    */
   // String getDefaultLabel();

   /**
    * Return Component type
    * 
    * @return
    */
   ComponentType getComponentType();

   /**
    * Registreerib komponendi põhise veateate koos argumentidega. Antud teade seotakse ära konkreetse elemendiga, mille rada leitakse
    * {@link Component#getComponentName()} vahendusel. Kutsutakse välja
    * {@link BindingResult#addError(org.springframework.validation.ObjectError)} TODO seda meetodit ei pruugi meil enam vaja minna, hetkel
    * jätan alles
    */
   void addErrorMessage(String messageCode, Object... arguments);

   /**
    * Komponendi põhise teate lisamine
    * 
    * @param messageType
    *           {@link MessageType} Teate tüüp
    * @param message
    *           Konkreetne teade
    * @param messageBody
    *           Täiendav teate sisu
    */
   void addMessage(MessageType messageType, String message, String messageBody);

   /**
    * Komponendi põhise teate koos argumentidega lisamine. Tõlke koodile lisatakse prefiksina antud komponendi tõlkekood, mille tagastab
    * {@link Component#getLabel()}. Tõlke sõnumiobjeki loomiseks kasutatakse meetodit
    * {@link Message#createMessage(String, com.nortal.spring.cw.core.i18n.model.Lang, Object...)}
    * 
    * @param code
    *           Tõlke kood
    * @param args
    *           Tõlke argumendid
    */
   void addMessageWithPrefix(String code, Object... args);

   /**
    * Komponendi põhise teate koos argumentidega lisamine. Tõlke sõnumiobjeki loomiseks kasutatakse meetodit
    * {@link Message#createMessage(String, com.nortal.spring.cw.core.i18n.model.Lang, Object...)}
    * 
    * @param code
    *           Tõlke kood
    * @param args
    *           Tõlke argumendid
    */
   void addMessage(String code, Object... args);

   /**
    * @param labelNamingStrategy
    */
   void setLabelNamingStrategy(LabelNamingStrategy labelNamingStrategy);

   /**
    * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
    * @since 16.01.2014
    */
   public interface LabelNamingStrategy {
      String getLabel(Component target);
   }
}
