package com.nortal.spring.cw.core.web.component;

import java.io.Serializable;

/**
 * Tegemist on liidesega, mida kasutatakse erinevate elementide tingimuslikuks näitamiseks või varjamiseks
 * 
 * @author Margus Hanni
 * @since 02.12.2013
 */
public interface ElementVisibility extends Serializable {

   /**
    * Tagastab <i>true</i> kui element on veebis antud hetkel nähtav, vastasel juhul tagastab <i>false</i>
    * 
    * @param parent
    *           Elementi omav komponent, mille sees element eksisteerib
    * @return {@link Boolean}
    */
   boolean isVisible(Hierarchical parent);

}
