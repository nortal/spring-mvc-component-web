package com.nortal.spring.cw.core.web.component.event.confirmation;

import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.json.JsonResponse;

/**
 * Tegemist on üldise kinnitusakna liidesega. Antud liidest kasutatakse erinevate kinnitusakende kuvamisel.
 * 
 * @author Margus Hanni
 * 
 */
public interface ConfirmationDialog extends Component {

   /**
    * Kinnitusakna üldine nimi
    */
   String CONFIRMATION_MODAL_DIALOG = "confirmationModalDialog";

   /**
    * Tegemist on meetodiga, mille välja kutsumisel määratakse kinnitusaken avamiseks
    */
   void show();

   /**
    * 
    * @param acceptHandler
    */
   void setAcceptHandler(ElementHandler acceptHandler);

   JsonResponse getJsonResponse();
}
