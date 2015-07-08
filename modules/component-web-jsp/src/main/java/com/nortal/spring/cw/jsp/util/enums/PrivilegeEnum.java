package com.nortal.spring.cw.jsp.util.enums;

import com.nortal.spring.cw.core.security.CwPrivilege;

/**
 * Portaali privileegid
 * 
 * @author Lauri Lättemäe (lauri.lattemae@nortal.com)
 * @since 12.09.2013
 */
public enum PrivilegeEnum implements CwPrivilege {

   // @formatter:off
   ;
   //@formatter:on
   private String code;

   private PrivilegeEnum(String code) {
      this.code = code;
   }

   public String getCode() {
      return code;
   }
}
