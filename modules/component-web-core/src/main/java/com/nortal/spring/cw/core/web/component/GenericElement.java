package com.nortal.spring.cw.core.web.component;

import java.io.Serializable;

import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Üldine veebi komponentide liides. Antud liidest implementeerivad nii {@link FormElement} kui ka {@link Component} klassid. Sisuliselt on
 * mõlemal juhul tegu veebis kuvatavate objektidega ning nendega tehakse osaliselt samu toiminguid. Igal veebi objektil on rada, tema
 * leidmiseks, label ehk teba nimi, mis vajadusel kuvatakse välja ning täiendavalt on muid omadusi mida saab objektide juures mõjutada.
 * Igale objektile on võimalik määrata tema omanik ehk parent, mis peab olema {@link Component} tüüpi, mis tähendab seda, et element ei saa
 * omada teist elemente vaid elemente saavad omada vaid komponendid ning komponendid saavad omada teisi komponente.
 * 
 * @author Margus Hanni
 * @since 06.03.2013
 */
public interface GenericElement extends Serializable {

   String ID_DELIMITER = BeanUtil.NESTED_DELIM;

   /**
    * Tagastab elemendi identifikaatori, mida kasutatakse komponendi leidmiseks hiljem selle väärdustamiseks või selle väärduse määramiseks.
    * Elemendi identifikaatori võib sisaldada punkte, listi spetsiifikat jne, mis aitab Spring MVC leida konkreetne objekt etteantud
    * mudelobjektist või komponendi seest, vastavalt meetodi implementatsioonile. Komponendi raja konvertimiseks
    * {@link GenericElement#getDisplayId()} tagastavale kujule saab kasutata utiliiti {@link ElementUtil#convertDisplayIdToPath(String)}
    * 
    * @return {@link String}
    */
   String getId();

   /**
    * Elemendi identifikaatori määramine, mida kasutatakse komponendi leidmiseks hiljem selle väärdustamiseks või selle väärduse
    * määramiseks. Elemendi identifikaatori võib sisaldada punkte, listi spetsiifikat jne, mis aitab Spring MVC leida konkreetne objekt
    * etteantud mudelobjektist või komponendi seest, vastavalt meetodi implementatsioonile
    * 
    * @param id
    *           Objekti esindav rada
    */
   void setId(String id);

   /**
    * Meetod tagastab objekti täispika raja objektide hierarhias. Vaikeväärtusena algab koostatav rada hierarhias kõige esimese
    * {@link Component} rajaga ning lõppeb käesoleva elemendi rajaga. <br>
    * Näide, kus leitakse komponendi seest kolmas element: components['komponendiNimi'].elements[2]
    * 
    * @return
    */
   String getFullPath();

   /**
    * Meetod tagastab objekti välja kuvatav ID, mida kasutatakse veebiraamistikus JavaScripti toimingute teostamiseks. Välja kuvatav ID
    * koostatakse elemendi rajast, selle koostamiseks kasutatakse {@link ElementUtil#convertPathToDisplayId(String)}
    * 
    * @return
    */
   String getDisplayId();

   /**
    * Kas antud objekt on muudetavas režiimis. Igale objektile on võimalik määrata kaks režiimi, vaate ja muutmise oma. Vastavalt
    * implementatsioonile käsitletakse neid veebikihis erinevalt. Näiteks elemendi puhul muutmisrežiimis muutub väli aktiivseks ning seda
    * saab muuta, vastasel juhul kuvatakse elemendi väärtus
    * 
    * @return
    */
   boolean isEditable();

   /**
    * Objekti töörežiimi määramine
    * 
    * @param editable
    *           <i>true</i> muudetav, <i>false</i> ei ole muudetav
    * 
    */
   void setEditable(boolean editable);

   /**
    * Tagastab kas objekt on veebikihis nähtav või mitte. Vastavalt implementatsioonile on võimalik veebikihis näidata või siis vastavalt
    * varjata vastavat objekti
    * 
    * @return
    */
   boolean isVisible();

   /**
    * Objekti nähtavuse määramine
    * 
    * @param visible
    *           <i>true</i> nähtav, <i>false</i> ei ole nähtav
    */
   void setVisible(boolean visible);

   /**
    * Registreerib elemendi põhise veateate koos argumentidega. Antud teade seotakse ära konkreetse elemendiga, mille rada leitakse
    * {@link GenericElement#getFullPath()} vahendusel. Kutsutakse välja
    * {@link org.springframework.validation.Errors#rejectValue(String, String, Object[], String)}
    */
   void addElementErrorMessage(String messageCode, Object... arguments);

}
