package com.nortal.spring.cw.core.web.component;

/**
 * Käesolev liides laiendab liidest {@link ElementPath} lisades elemendi indeksi olemasolu nõude. Kui element implementeerib käesolevat
 * indeksit siis eeldatakse, et antud element elab täiendavalt mõne listi sees. Meetod {@link ElementPathList#getPathIndex()} tagastab
 * elemendi positsiooni listis
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 23.03.2014
 */
public interface ElementPathList extends ElementPath {

   /**
    * Elemendi positsioon listis
    * 
    * @return {@link Integer}
    */
   int getPathIndex();

}
