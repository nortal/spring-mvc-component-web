package com.nortal.spring.cw.core.web.component.event.confirmation;

import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;
import com.nortal.spring.cw.core.web.component.modal.ConfirmationModalDialogComponent;
import com.nortal.spring.cw.core.web.component.modal.ModalDialogComponent;
import com.nortal.spring.cw.core.web.component.page.ComponentCaption;

/**
 * Kustutamisel välja kutsutav dialoogaken
 * 
 * @author margush
 * 
 */
public class DeleteConfirmationDialog extends ConfirmationModalDialogComponent {

   private static final long serialVersionUID = 1L;

   public DeleteConfirmationDialog(Hierarchical parent) {
      super(parent);
      getModalButtons().addModalButton(ModalDialogComponent.SUBMIT_MODAL_DIALOG_ACCEPT, GlobalLabel.BUTTON_YES);
   }

   @Override
   public ComponentCaption getCaption() {
      return new ComponentCaption("global.confirm.delete.title");
   }
}