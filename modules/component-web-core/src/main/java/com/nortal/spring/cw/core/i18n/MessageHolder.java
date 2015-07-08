package com.nortal.spring.cw.core.i18n;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.holder.Message;

/**
 * Tegemist on klassiga, kus hoitakse erinevate komponentide sõnumeid, mus tuleb kasutajaliidese vahendusel välja kuvada.
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 23.04.2014
 */
@org.springframework.stereotype.Component
public class MessageHolder implements Serializable {

   private static final long serialVersionUID = 1L;
   private final Map<String, Set<Message>> componentMessageMap = new HashMap<String, Set<Message>>();

   /**
    * Komponendi spetsiifilise teate lisamine
    * 
    * @param component
    *           {@link Component}
    * @param message
    *           {@link Message}
    */
   public void addMessage(Component component, Message message) {
      String fullPath = component.getFullPath();
      Set<Message> messages = componentMessageMap.get(fullPath);
      if (messages == null) {
         messages = new HashSet<Message>();
         componentMessageMap.put(fullPath, messages);
      }
      messages.add(message);
   }

   /**
    * Komponendi spetsiifiliste teadete tagastamine. Peale esmakordset meetodi välja kutsumist eemaldatakse konkreetse komponendi sõnumid
    * sõnumihoidlast
    * 
    * @param component
    *           {@link Component}
    * @return
    */
   public Map<String, Set<Message>> getMessagesAndRemove(Component component) {
      String fullPath = component.getFullPath();
      Set<Message> messages = componentMessageMap.get(fullPath);
      if (messages == null) {
         return null;
      }

      Map<String, Set<Message>> messageMap = new HashMap<String, Set<Message>>();
      for (Message message : messages) {
         Set<Message> set = messageMap.get(message.getMessageType());
         if (set == null) {
            set = new HashSet<Message>();
            messageMap.put(message.getMessageType(), set);
         }
         set.add(message);
      }

      componentMessageMap.remove(fullPath);

      return messageMap;
   }
}
