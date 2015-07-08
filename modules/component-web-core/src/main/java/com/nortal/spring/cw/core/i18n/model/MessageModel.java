package com.nortal.spring.cw.core.i18n.model;

import java.io.Serializable;

import lombok.Data;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 29.08.2013
 */
@Data
public class MessageModel implements Serializable {
   private static final long serialVersionUID = 1L;

   private String code;

   private Object[] params;

   public MessageModel() {
   }

   public MessageModel(String code) {
      this(code, (Object[]) null);
   }

   public MessageModel(String code, Object... params) {
      this.code = code;
      this.params = params;
   }
}
