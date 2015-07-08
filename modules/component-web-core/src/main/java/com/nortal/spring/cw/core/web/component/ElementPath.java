package com.nortal.spring.cw.core.web.component;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Käesolev liides on mõeldud erinevate elementide raja määramiseks. Iga element teab ainult enda rada hierarhias ning on teadlik vaid
 * ülemobjekti rajast, mille sees antud element elab. Elemendi täisraja leidmisel itereeritakse üle kõikide objektide, alustades elemendist
 * ning lõpetades viimase komponendiga
 * 
 * @author Margus Hanni
 * @since 24.03.2014
 */
public interface ElementPath extends Serializable {

   /**
    * Elemendi enda rada. Tagastatav väärtus lisatakse ülemobjekti raja järele
    * 
    * @return {@link String}
    */
   String getPath();

   /**
    * Ülemobjekti rajaobjekt
    * 
    * @return {@link ElementPath}
    */
   @JsonIgnore
   ElementPath getParentElementPath();

   /**
    * Ülemobjekti raja objekti lisamine
    * 
    * @param elementPath
    *           {@link ElementPath}
    */
   void setParentElementPath(ElementPath elementPath);
}
