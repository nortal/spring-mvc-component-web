package com.nortal.spring.cw.core.web.component.validator;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.nortal.spring.cw.core.web.component.element.AbstractValidator;

/**
 * Panga viitenumbri kontrollimine (http://www.pangaliit.ee/et/arveldused/viitenumber).<br>
 * Viitenumber peab vastama meetodile 7-3-1 (http://www.pangaliit.ee/et/?option=com_content&view=article&id=146)
 * 
 * @author Margus Hanni
 * @since 29.10.2013
 */
public class ValidatorBankReferenceNumber extends AbstractValidator {

   private static final long serialVersionUID = 1L;

   @Override
   public void validate() {
      String value = getValue();
      if (StringUtils.isNotEmpty(value) && !testControl(value)) {
         getElement().addElementErrorMessage("field.error.bank-reference-rumber.does-not-meet-the-standard");
      }
   }

   /**
    * Testime viitenumbrit, kas see vastab 7-3-1 meetodile.<br>
    * Koodijupp pärineb WM pangalingi lahenduses klassist PacketReferenceNumberParameter. Natuke täiendatud, lisatud
    * {@link NumberUtils#isNumber(String)} ja {@link StringUtils#length(String)} kontroll
    * 
    */
   private boolean testControl(String referenceNumber) {

      if (!NumberUtils.isNumber(referenceNumber) || StringUtils.length(referenceNumber) > 20) {
         return false;
      }

      int summa = 0;
      int array[] = new int[] { 7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1, 7, 3, 1, 7 };
      int controlNumber = Integer.parseInt(referenceNumber.substring(referenceNumber.length() - 1, referenceNumber.length()));

      for (int x = referenceNumber.length() - 2; x >= 0; x--) {
         int oneDig = Integer.parseInt(referenceNumber.substring(x, x + 1));
         summa += oneDig * array[referenceNumber.length() - x - 2];
      }

      summa = (10 - summa % 10) % 10;

      if (controlNumber != summa) {
         return false;
      }

      return true;
   }
}
