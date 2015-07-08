package com.nortal.spring.cw.jsp.web.portal.component.dokument;

import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;

/**
 * Dokumendi salvestamise-esitamise abstraktsioon
 * 
 * @author Margus Hanni
 * 
 */
public interface DokumentSaveHandler {

   String save(ControllerComponent cc);

   String publish(ControllerComponent cc);
}
