package com.nortal.spring.cw.core.web.component.modal;

import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.event.confirmation.ConfirmationDialog;

/**
 * 
 * @author Margus Hanni
 * 
 */
public class ConfirmationModalDialogComponent extends AbstractModalDialogComponent implements ConfirmationDialog {

   public ConfirmationModalDialogComponent(Hierarchical parent) {
      super(ConfirmationDialog.CONFIRMATION_MODAL_DIALOG);
      setParent(parent);
   }

   private static final long serialVersionUID = 1L;

   @Override
   public ModalType getType() {
      return ModalType.DIALOG;
   }

   @Override
   public String getPath() {
      return "confirmation";
   }
}
