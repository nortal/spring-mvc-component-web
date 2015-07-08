package com.nortal.spring.cw.core.web.json;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nortal.spring.cw.core.web.holder.Message;

/**
 * 
 * @author Margus Hanni
 * 
 */
public class JsonResponse {

   private Map data;
   private boolean success;
   private Map<String, String> fieldErrors;
   private Map<String, Set<Message>> messages = new HashMap<String, Set<Message>>();

   public Map<?, ?> getData() {
      return data;
   }

   public void setData(Map<?, ?> data) {
      this.data = data;
   }

   public void addData(Object key, Object value) {
      if (data == null) {
         data = new HashMap<Object, Object>();
      }
      data.put(key, value);
   }

   public boolean isSuccess() {
      return success;
   }

   public void setSuccess(boolean success) {
      this.success = success;
   }

   public Map<String, String> getFieldErrors() {
      return fieldErrors;
   }

   public Map<String, Set<Message>> getMessages() {
      return messages;
   }

   public void setFieldErrors(Map<String, String> fieldErrors) {
      this.fieldErrors = fieldErrors;
   }

   public void setMessages(Map<String, Set<Message>> messages) {
      this.messages = messages;
   }

   public void addMessage(Message message) {
      Set<Message> messageSet = messages.get(message.getMessageType());
      if (messageSet == null) {
         messageSet = new HashSet<>();
         messages.put(message.getMessageType(), messageSet);
      }

      messageSet.add(message);
   }

}
