package com.nortal.spring.cw.core.web.component;

import java.io.Serializable;

import com.nortal.spring.cw.core.web.component.composite.Component;

/**
 * Tegemist on liidesega, mida kasutatakse erinevate elementide tingimuslikuks
 * muutmise oleku määramiseks
 * 
 * @author Margus Hanni
 * @since 04.12.2013
 */
public interface ElementChangeable extends Serializable {

   /**
    * Tagastab <i>true</i> kui element on muudetavas olekus, vastasel juhul
    * tagastab <i>false</i>
    * 
    * @param parent
    *           Elementi omav komponent, mille sees element eksisteerib
    * @return {@link Boolean}
    */
   boolean isChangeable(Component parent);

}
