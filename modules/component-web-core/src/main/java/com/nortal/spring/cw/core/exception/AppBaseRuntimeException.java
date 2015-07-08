package com.nortal.spring.cw.core.exception;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;

/**
 * @author Margus Hanni
 * 
 */
public class AppBaseRuntimeException extends RuntimeException {
   private static final long serialVersionUID = 1L;
   private String messageCode;
   private Object[] messageArgs;

   public AppBaseRuntimeException(Throwable throwable) {
      this(throwable, throwable.getMessage());
   }

   public AppBaseRuntimeException(String messageCode, Object... messageArgs) {
      this(null, messageCode, messageArgs);
   }

   public AppBaseRuntimeException(Throwable throwable, String messageCode, Object... messageArgs) {
      // Lauri - siin tuleb keel ette anda kuna UserRequestInfot ei ole taustatöödel
      super(BeanUtil.getBean(CwMessageSource.class).resolve(StringUtils.trimToEmpty(messageCode), RequestUtil.getDefaultLanguage(),
            messageArgs), throwable);
      this.messageCode = messageCode;
      this.messageArgs = messageArgs;
   }

   public String getMessageCode() {
      return StringUtils.trimToEmpty(messageCode);
   }

   public Object[] getMessageArgs() {
      return messageArgs;
   }
}