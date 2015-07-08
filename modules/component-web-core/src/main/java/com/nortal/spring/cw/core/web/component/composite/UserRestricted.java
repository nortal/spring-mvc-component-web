package com.nortal.spring.cw.core.web.component.composite;

/**
 * Selle liidesega märgistatakse komponente või kontollereid, kus tuleb enne andmete kuvamist või lehe avamist teostada lisa-kontrolle peale
 * privileegi-kontrolli.<br>
 * Lehe avamise ehk kontrolleri õigusi kontrollitakse ennem kontrolleri {@link ControllerComponent} initsialiseerimist. Komponendi õigusi
 * kontrollitakse peale {@link ControllerComponent} initsialiseerimist.
 * 
 * @author Alrik Peets
 * @author Margus Hanni
 */
public interface UserRestricted {

   /**
    * Kontrollib kas kasutajal on õigus näha komponendis kuvatavat infot või avada konkreetset lehekülge<br>
    * Meetod tagastab <code>true</code> kui kasutaja läbis kontrollid, vastasel juhul tagastatakse <code>false</code>.
    * 
    * @return {@link Boolean}
    */
   boolean checkAccess();
}
