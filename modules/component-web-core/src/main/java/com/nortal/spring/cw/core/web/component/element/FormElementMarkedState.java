package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

/**
 * Liidest kasutatakse elementide juures nende seisu fikseerimiseks ning
 * vajadusel hiljem fikseeritud seisu taastamiseks
 * 
 * @author Margus Hanni
 * @since 04.12.2013
 */
public interface FormElementMarkedState extends Serializable {

   /**
    * Elemendi algse seisu fikseerimine. Kui elemendi väärtuseks on objekt siis
    * objekt peab olema serialiseeritav ehk implementeerima klassi
    * {@link Serializable}, sama nõue on ka tema sees paiknevate objektidele
    */
   void markedState();

   /**
    * Taastame algse märgitud seisu
    */
   void revertToMarkedState();

}
