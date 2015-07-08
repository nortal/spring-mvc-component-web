package com.nortal.spring.cw.core.xml;

/**
 * Üldine XML parsimise veakäsitlus
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 30.08.2013
 */
public class XmlParseException extends RuntimeException {
   private static final long serialVersionUID = 1L;

   public XmlParseException(String message) {
      this(message, null);
   }

   public XmlParseException(String message, Throwable cause) {
      super(message, cause);
   }
}
