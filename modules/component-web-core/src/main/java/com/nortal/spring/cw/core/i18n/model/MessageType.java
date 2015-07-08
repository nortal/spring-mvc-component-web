package com.nortal.spring.cw.core.i18n.model;

import lombok.Data;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 */
@Data
public class MessageType {
   private static final long serialVersionUID = 1L;

   private final String code;

   public static final MessageType INFO = new MessageType("INFO");
   public static final MessageType ERROR = new MessageType("ERROR");
   public static final MessageType WARNING = new MessageType("WARNING");
   public static final MessageType LABEL = new MessageType("LABEL");
   public static final MessageType OK = new MessageType("OK");
   public static final MessageType UNKNOWN = new MessageType("UNKNOWN");

   private MessageType(String code) {
      this.code = code;
   }
}
