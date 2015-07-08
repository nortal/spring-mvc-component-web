package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

/**
 * Elemendi labeli ehk nimetuse abstraktsioon. Antud liides on mõeldud
 * kasutamiseks kohas kus on vajadus erinevate elementide juures tingimuslikult
 * kuvada elemendi erinevaid nimetusi.
 * 
 * @author Margus Hanni
 * @since 03.12.2013
 */
public interface ElementLabel extends Serializable {

   /**
    * Meetod tagastab elemendi nime vastavalt implementatsioonile
    * 
    * @return {@link String}
    */
   String getLabel();
}
