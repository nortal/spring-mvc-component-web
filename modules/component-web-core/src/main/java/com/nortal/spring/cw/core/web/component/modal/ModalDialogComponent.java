package com.nortal.spring.cw.core.web.component.modal;

import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.json.JsonResponse;

/**
 * Dialoogakende üldine liides
 * 
 * @author Margus Hanni
 * @since 04.03.2013
 */
public interface ModalDialogComponent extends Component {

   /**
    * Sündmuse nimetus {@value} , mis kutsutakse välja dialoogakna sulgemisel "Jah" nupu vahendusel. Vaikekäitmisena suunatakse sündmuse
    * töötlus meetodisse
    * {@link AbstractModalDialogComponent#modalDialogAccept(com.nortal.spring.cw.core.web.component.composite.ControllerComponent, java.util.Map)}
    */
   public static final String SUBMIT_MODAL_DIALOG_ACCEPT = "submitModalDialogAccept";

   /**
    * Sündmuse nimetus {@value} , mis kutsutakse välja modaalakna sulgemisel "Sulge" nupu vahendusel. Vaikekäitmisena suunatakse sündmuse
    * töötlus meetodisse
    * {@link AbstractModalDialogComponent#modalDialogClose(com.nortal.spring.cw.core.web.component.composite.ControllerComponent, java.util.Map)}
    */
   public static final String SUBMIT_MODAL_CLOSE = "submitModalClose";

   /**
    * Sündmuse nimetus {@value} , mis kutsutakse välja modaalakna sulgemisel "Katkesta" nupu vahendusel. Vaikekäitmisena suunatakse sündmuse
    * töötlus meetodisse
    * {@link AbstractModalDialogComponent#modalDialogCancel(com.nortal.spring.cw.core.web.component.composite.ControllerComponent, java.util.Map)
    * (ee.epm.common.web.component.composite.ControllerComponent, java.util.Map)}
    */
   public static final String SUBMIT_MODAL_CANCEL = "submitModalCancel";

   /**
    * Meetod tagastab modaalakna tüübi
    * 
    * @return {@link ModalType}
    */
   ModalType getType();

   /**
    * Meetod tagastab modaalakna omaduste objekti
    * 
    * @return {@link ModalProperties}
    */
   ModalProperties getProperties();

   /**
    * Meetod tagastab händleri, mis kutsutakse välja dialoogakna sulgemisel kasutades selleks nuppu "Jah"
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getAcceptHandler();

   /**
    * Meetod tagastab händleri, mis kutsutakse välja modaalakna sulgemisel kasutades selleks nuppu "Sulge"
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getCloseHandler();

   /**
    * Meetod tagastab händleri, mis kutsutakse välja modaalakna sulgemisel kasutades selleks nuppu "Katkesta"
    * 
    * @return {@link ElementHandler}
    */
   ElementHandler getCancelHandler();

   /**
    * Meetod tagastab JSONi andmeobjekti koos modaalakna erinevate omadustega: <br>
    * caption - akna pealkiri <br>
    * content - modaalakna sisu <br>
    * displayId - väljakuvatav objekti identifikaator <br>
    * componentName - modaalakna nimi <br>
    * sizeStyleClass - modaalakna suurust määrav CSS klass. Võimalikud suurused asuvad {@link ModalSize} enumis <br>
    * buttons - erinevate sünduste nupud
    * 
    * @return
    */
   JsonResponse getJsonResponse();

   /**
    * Meetodi välja kutsumisel märgitakse nähtavaks, sisuliselt kutsutakse välja {@link ModalDialogComponent#setVisible(true)}
    */
   void show();

   /**
    * Meetodi välja kutsumisel märgitakse varjatuks, sisuliselt kutsutakse välja {@link ModalDialogComponent#setVisible(false)}
    */
   void hide();

}
