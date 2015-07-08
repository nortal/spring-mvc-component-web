package com.nortal.spring.cw.core.web.portal;

import java.io.Serializable;

import org.springframework.validation.MessageCodesResolver;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 10.03.2013
 */
public class SimpleMessageCodesResolver implements MessageCodesResolver, Serializable {

   private static final long serialVersionUID = 1L;

   @Override
   public String[] resolveMessageCodes(String errorCode, String objectName) {
      return new String[] { errorCode };
   }

   @Override
   public String[] resolveMessageCodes(String errorCode, String objectName, String field, Class<?> fieldType) {
      return new String[] { errorCode };
   }
}
