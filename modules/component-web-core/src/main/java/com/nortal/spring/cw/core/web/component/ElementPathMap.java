package com.nortal.spring.cw.core.web.component;

/**
 * Käesolev liides laiendab liidest {@link ElementPath} lisades elemendi võtme olemasolu nõude. Kui element implementeerib käesolevat
 * indeksit siis eeldatakse, et antud element elab täiendavalt mõne mapi sees. Meetod {@link ElementPathList#getPathKey()} tagastab elemendi
 * võtme konkreetses mapis
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 23.03.2014
 */
public interface ElementPathMap extends ElementPath {

   /**
    * Elemendi võti mapis
    * 
    * @return {@link String}
    */
   String getPathKey();

}
