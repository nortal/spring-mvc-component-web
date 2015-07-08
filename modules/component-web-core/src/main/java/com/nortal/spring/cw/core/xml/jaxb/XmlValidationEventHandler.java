package com.nortal.spring.cw.core.xml.jaxb;

import java.text.MessageFormat;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * Üldine XML parsimise veaolukordade käsitlus vastavalt vea tasemetele. Ignoreeritakse {@link ValidationEvent#WARNING} veaolukordi
 * (vaikimisi ignoreeritakse {@link ValidationEvent#WARNING} ja {@link ValidationEvent#ERROR} tasemeid)
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 30.08.2013
 */
@Slf4j
public class XmlValidationEventHandler implements ValidationEventHandler {

   @Override
   public boolean handleEvent(ValidationEvent event) {
      log.trace(MessageFormat.format("XmlValidationEventHandler.handleEvent: event={0}", event.getMessage()));

      return event.getSeverity() == ValidationEvent.WARNING;
   }
}
