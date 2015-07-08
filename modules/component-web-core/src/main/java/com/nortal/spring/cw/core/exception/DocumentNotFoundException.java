package com.nortal.spring.cw.core.exception;

/**
 * Erindit kasutatakse juhtudel, kui otsitakse kindlat dokumenti kindla IDga, kuid sellist dokumenti ei leitud
 * 
 * @author Margus Hanni
 * 
 */
public class DocumentNotFoundException extends GenericFailureException {
   private static final long serialVersionUID = 1L;
}