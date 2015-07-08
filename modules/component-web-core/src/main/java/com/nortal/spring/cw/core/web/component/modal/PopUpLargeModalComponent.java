package com.nortal.spring.cw.core.web.component.modal;

/**
 * Tegemist on suure PopUp aknaga, mille suuruse klassiks on
 * {@link ModalSize#LARGE}. Täiendavalt ei näidata modaalakna "show" nuppu<br>
 * Klass laiendab {@link BaseModalComponent}
 * 
 * @author Margus Hanni
 * 
 */
public class PopUpLargeModalComponent extends BaseModalComponent {

   private static final long serialVersionUID = 1L;

   public PopUpLargeModalComponent(String componentName) {
      super(ModalType.POPUP, componentName);
      getProperties().setSizeStyleClass(ModalSize.LARGE).setShowLink(false);
   }

}
