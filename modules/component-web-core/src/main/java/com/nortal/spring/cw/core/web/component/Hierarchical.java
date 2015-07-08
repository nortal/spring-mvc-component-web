package com.nortal.spring.cw.core.web.component;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Käesolevat liidest kasutatakse komponentide juures, mis elavad teise komponendi sees, ehk omavad ülemobjekti
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 24.03.2014
 */
public interface Hierarchical extends ElementPath, GenericElement {

   /**
    * Objekti omava ülemkomponendi määramine, mille sees antud element või komponent elab. Ülem võib olla ka määramata, sellisel juhul
    * suunatakse erinevad toimingud otse kontrollerisse
    * 
    * @param parent
    *           Objekti ülem komponent
    */
   void setParent(Hierarchical parent);

   /**
    * Meetod tagastab objekti ülemkomponendi
    * 
    * @return
    */
   @JsonIgnore
   Hierarchical getParent();

}
