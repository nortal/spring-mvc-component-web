package com.nortal.spring.cw.core.web.component;

import java.io.Serializable;

import com.nortal.spring.cw.core.web.component.element.EventElement;

/**
 * Tegemist on liidesega, mida kasutatakse elementides sündmuste käivitamisel
 * erinevate toimingute teostamisel. Elemendid mis kasutavad sellist
 * funktsionaalsust peavad implementeerima {@link EventElement}
 * 
 * @author Margus Hanni
 * @since 04.03.2013
 */
public interface ElementHandler extends Serializable {

   /**
    * Sündmuse händlermeetodi poolt välja kutsutav funktsionaalsuse
    * implementatsioon
    * 
    * @return
    */
   String execute();

   public static ElementHandler EMPTY_HANDLER = new ElementHandler() {

      private static final long serialVersionUID = 1L;

      @Override
      public String execute() {
         return null;
      }
   };
}
