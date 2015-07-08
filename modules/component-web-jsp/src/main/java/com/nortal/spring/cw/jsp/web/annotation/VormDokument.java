package com.nortal.spring.cw.jsp.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.nortal.spring.cw.jsp.model.xml.dokument.vorm.XmlVormDokument;

/**
 * Vormdokumentide annotatsioon, mida kasutatakse konkreetse vormdokumendi objekti tuvastamisel. Väli <code>code</code>, ehk teenuse kood,
 * peab olema vastavuses andmebaasis defineeritud teenuse koodiga {@link Teenus#getKood()}. Teenuse kood peab olema unikaalne. <br>
 * Väli <code>version</code> tähistab konkreetse dokumendi versioonisystematic
 * 
 * @author Margus Hanni
 * 
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VormDokument {

   // /**
   // * Dokumendi kood
   // *
   // * @return
   // */
   // String code();
   //
   // /**
   // * Dokumendi versioon
   // *
   // * @return
   // */
   // double version() default 1.0;

   /**
    * @return
    */
   Class<? extends XmlVormDokument<?>> type();
}
