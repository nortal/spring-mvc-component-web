package com.nortal.spring.cw.core.servlet.http;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Portaali sisene sessiooni kuulaja. Tegemist on kuulajaga, mis aitab meil kinni püüda sessiooni aegumise viga. Kui sessioon lõpeb kas siis
 * aegumise või küpsise kustutamise tagajärjel, siis registreerime uue parameetri {@link CwSessionExpireListener#CW_NEW_SESSION_CREATED},
 * mille väärtuseks on <code>true</code>
 * 
 * @author Margus Hanni
 * @since 02.10.2013
 */
public class CwSessionExpireListener implements HttpSessionListener {

   /**
    * @{value
    */
   public static final String CW_NEW_SESSION_CREATED = CwSessionExpireListener.class.getName() + ".cwNewSessionCreated";

   @Override
   public void sessionCreated(HttpSessionEvent arg0) {
      arg0.getSession().setAttribute(CW_NEW_SESSION_CREATED, true);
   }

   @Override
   public void sessionDestroyed(HttpSessionEvent arg0) {
      // nothing to do here
   }

}
