package com.nortal.spring.cw.core.web.component.modal;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.ElementHandler;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.composite.complex.AbstractComplexComponent;
import com.nortal.spring.cw.core.web.component.composite.complex.ComplexComponent;
import com.nortal.spring.cw.core.web.json.JsonResponse;

/**
 * Modaalakende põhiabstraktsioon, milles on kirjeldatud modaalakende põhilised omadused. Modaalaken an vaikimisi varjatud, mis tähendab
 * seda et {@link ModalDialogComponent#isVisible()}==false
 * 
 * @author Margus Hanni
 * @since 04.03.2013
 */
public abstract class AbstractModalDialogComponent extends AbstractComplexComponent implements ModalDialogComponent {

   private static final long serialVersionUID = 1L;
   private String simpleContent;
   private String jspContent;
   private ElementHandler acceptHandler;
   private ElementHandler cancelHandler;
   private ElementHandler closeHandler;
   private final ModalProperties properties = new ModalProperties(this);
   private final ModalButtons buttons = new ModalButtons(this);

   public AbstractModalDialogComponent(String componentName) {
      super(componentName);
      hide();
   }

   /**
    * Meetod tagastab modaalakna sisu, mis määratakse otse kontrolleris
    * 
    * @return
    */
   public String getSimpleContent() {
      return simpleContent;
   }

   public ElementHandler getAcceptHandler() {
      return acceptHandler;
   }

   /**
    * Modaalakna sisu määramine. Kui <i>simpleContent</i> on määratud siis JSP põhist modaalakna sisu ei tekitata
    * 
    * @param simpleContent
    */
   public void setSimpleContent(String simpleContent) {
      this.simpleContent = simpleContent;
   }

   /**
    * Modaalakna sulgemisel läbi "Jah" nupu väljakutsutava händleri määramine
    * 
    * @param cancelHandler
    *           {@link ElementHandler} Käivitatav implementatsioon
    */
   public void setAcceptHandler(ElementHandler acceptHandler) {
      this.acceptHandler = acceptHandler;
   }

   public ModalProperties getProperties() {
      return properties;
   }

   @EventHandler(eventName = ModalDialogComponent.SUBMIT_MODAL_CLOSE)
   public String modalDialogClose(ControllerComponent comp, Map<String, String> params) {
      comp.hideOpenedModalDialog();
      String redirect = null;
      /*
       * TODO Margus - lihtsalt sulgemisel peame tegema modal andmetele reseti Mõtted: serialiseerida kogu ControllerComponent ning hiljem
       * deserialiseerida? Tulemuse talletamiseks kasutame EHCache't?
       */

      ModalDialogComponent modalComp = this;
      if (modalComp.getCloseHandler() != null) {
         redirect = modalComp.getCloseHandler().execute();
      }

      return redirect;
   }

   @EventHandler(eventName = ModalDialogComponent.SUBMIT_MODAL_CANCEL)
   public String modalDialogCancel(ControllerComponent comp, Map<String, String> params) {
      comp.hideOpenedModalDialog();
      String redirect = null;
      // TODO Margus - lihtsalt sulgemisel peame tegema modal andmetele reseti

      ModalDialogComponent modalComp = this;
      if (modalComp.getCancelHandler() != null) {
         redirect = modalComp.getCancelHandler().execute();
      }

      return redirect;
   }

   @EventHandler(eventName = ModalDialogComponent.SUBMIT_MODAL_DIALOG_ACCEPT)
   public String modalDialogAccept(ControllerComponent comp, Map<String, String> params) {
      hide();
      ModalDialogComponent modalComp = this;
      String redirect = null;

      if (modalComp.validateAndConvert()) {
         if (modalComp.getAcceptHandler() != null) {
            redirect = modalComp.getAcceptHandler().execute();
         }
         if (!ModalType.DIALOG.equals(modalComp.getType()) && comp.getBindingResult().hasErrors()) {
            this.setVisible(true);
         }
      } else {
         this.setVisible(true);
      }
      return redirect;
   }

   public ElementHandler getCancelHandler() {
      return cancelHandler;
   }

   /**
    * Modaalakna sulgemisel läbi "Katkesta" nupu väljakutsutava händleri määramine
    * 
    * @param cancelHandler
    *           {@link ElementHandler} Käivitatav implementatsioon
    */
   public void setCancelHandler(ElementHandler cancelHandler) {
      this.cancelHandler = cancelHandler;
   }

   public ElementHandler getCloseHandler() {
      return closeHandler;
   }

   /**
    * Modaalakna sulgemisel läbi "Sulge" nupu väljakutsutava händleri määramine
    * 
    * @param cancelHandler
    *           {@link ElementHandler} Käivitatav implementatsioon
    */
   public void setCloseHandler(ElementHandler closeHandler) {
      this.closeHandler = closeHandler;
   }

   public JsonResponse getJsonResponse() {

      Map<String, Object> jsonMap = new HashMap<String, Object>();

      jsonMap.put("caption", getCaption().getFullLabel());
      jsonMap.put("content", getContent());

      jsonMap.put("displayId", getDisplayId());
      jsonMap.put("id", getId());
      jsonMap.put("sizeStyleClass", getProperties().getSizeStyleClass());
      jsonMap.put("modalButtons", getModalButtons());

      JsonResponse response = new JsonResponse();
      response.setData(jsonMap);

      return response;
   }

   /**
    * Meetod tagastab <i>true</i> juhul kui modaalaknale on määratud läbi definitsiooni akna sisu
    * 
    * @return @link {@link Boolean}
    */
   public boolean isHasSimpleContent() {
      return StringUtils.isNotEmpty(getSimpleContent());
   }

   /**
    * Meetod tagastab JSPs defineeritud tulemi. JSPs on võimalik juhtida, mida avanevas modaalaknas kuvatakse, selleks võib olla tavaline
    * teks või mõni teine komponent
    * 
    * @return {@link String}
    */
   public String getJspContent() {
      return jspContent;
   }

   /**
    * Meetod määrab JSPs defineeritud tulemi. JSPs on võimalik juhtida, mida avanevas modaalaknas kuvatakse, selleks võib olla tavaline teks
    * või mõni teine komponent. <b>Antud meetod kutsutakse välja tagis <i>modal.tag</i></b>
    * 
    * @return {@link String}
    */
   public void setJspContent(String jspContent) {
      this.jspContent = jspContent;
   }

   /**
    * Meetod tagastab modaalakna sisu. Kui on määratud {@link AbstractModalDialogComponent#setSimpleContent(String)}, ehk
    * {@link AbstractModalDialogComponent#isHasSimpleContent()} on tõene, sellisel juhul tagastatakse meetodi
    * {@link AbstractModalDialogComponent#getSimpleContent()} tulemus, vastasel juhul tagastatakse
    * {@link AbstractModalDialogComponent#getJspContent()}
    * 
    * @return
    */
   public String getContent() {
      return isHasSimpleContent() ? getSimpleContent() : getJspContent();
   }

   @Override
   public void show() {
      Hierarchical parent = this.getParent();

      if (parent == null) {
         parent = getControllerComponent();
         this.setParent(parent);
      }

      if (parent instanceof ComplexComponent) {
         ((ComplexComponent) parent).replace(this);
      }

      this.setVisible(true);
      ((Component) parent).initComponentsIfNeeded();
   }

   @Override
   public void hide() {
      this.setVisible(false);
   }

   public ModalButtons getModalButtons() {
      return buttons;
   }
}
