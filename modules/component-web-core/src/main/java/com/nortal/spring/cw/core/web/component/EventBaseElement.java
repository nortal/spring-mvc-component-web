package com.nortal.spring.cw.core.web.component;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.annotation.component.EventHandler;
import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.composite.ComponentType;
import com.nortal.spring.cw.core.web.component.composite.ControllerComponent;
import com.nortal.spring.cw.core.web.component.element.AbstractSimpleElement;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.event.ButtonElement;
import com.nortal.spring.cw.core.web.component.event.LinkElement;
import com.nortal.spring.cw.core.web.component.event.confirmation.ConfirmationDialog;
import com.nortal.spring.cw.core.web.component.global.ElementAction;
import com.nortal.spring.cw.core.web.component.list.ListComponent;
import com.nortal.spring.cw.core.web.component.list.row.ListRow;
import com.nortal.spring.cw.core.web.component.list.row.ListRowButtons;
import com.nortal.spring.cw.core.web.util.BeanUtil;

/**
 * Sündmuste element, millelele klõpsates käivitatakse onClick sündmus. Kui element asub listi sees leitakse täiendavalt listi rea entity
 * kui ka lisaks konkreetne sündmuse välja kutsunud element. Defineeritud <i>eventName</i> vastand peab leiduma kas antud elemendi ülem
 * komponendis või kontrolleris annotatsiooniga meetodis {@link EventHandler#eventName()}
 * 
 * @author Margus Hanni
 */
public class EventBaseElement extends AbstractSimpleElement implements EventElement {

   private static final long serialVersionUID = 1L;
   private String eventName;
   private ElementHandler onClickHandler;
   private ElementHandler onChangeHandler;
   private ConfirmationDialog confirmation;
   private ElementVisibility elementVisibility;

   /**
    * Konstruktor, mille argumendiks on elemendi nimi
    * 
    * @param elementName
    *           Elemendi nimi
    */
   public EventBaseElement(String elementName) {
      super(elementName);
   }

   /**
    * Konstruktor, mille argumendiks on elemendi nimi ja selle nimetus
    * 
    * @param elementName
    *           Elemendi nimi
    * @param label
    *           Tõlke kood või nimi
    */
   public EventBaseElement(String elementName, String label) {
      super(elementName);
      setLabel(label);
   }

   /**
    * Konstruktor, mille argumendiks on elemendi nimi, selle nimetus ning välja kutsutava nimi
    * 
    * @param elementName
    *           Elemendi nimi
    * @param label
    *           Tõlke kood või nimi
    * @param eventName
    *           Väljakutsutav sündmus
    */
   public EventBaseElement(String elementName, String label, String eventName) {
      super(elementName);
      setLabel(label);
      this.eventName = eventName;
   }

   /**
    * Väljakutsutava sündmuse nimi, mis peab olema määratud annotatsioonis {@link EventHandler#eventName()}, mille alusel leitakse üles
    * sündmust täitev meetod.
    * 
    * @return
    */
   public String getEventName() {
      return eventName;
   }

   /**
    * Väljakutsutava sündmuse nimi määramine. Antud nimi peab olema määratud annotatsioonis {@link EventHandler#eventName()}, mille alusel
    * leitakse üles sündmust täitev meetod.
    * 
    * @param eventName
    *           Sündmuse nimi
    */
   public void setEventName(String eventName) {
      this.eventName = eventName;
   }

   @Override
   public void setOnClickHandler(ElementHandler handler) {
      this.eventName = ElementAction.SUBMIT_ON_CLICK;
      this.onClickHandler = handler;
   }

   @Override
   public ElementHandler getOnClickHandler() {
      return this.onClickHandler;
   }

   @Override
   public void setOnChangeHandler(ElementHandler handler) {
      this.eventName = ElementAction.SUBMIT_ON_CHANGE;
      this.onChangeHandler = handler;
   }

   @Override
   public ElementHandler getOnChangeHandler() {
      return this.onChangeHandler;
   }

   /**
    * Tegemist on üldise sündmuse elemendi händlermeetodiga, mis kutsutakse välja <i>click</i> sündmuse puhul.
    * 
    * @param comp
    *           Kontrollerkomponent, mis on defineeritud põhikontrolleris
    * @return
    */
   @EventHandler(eventName = ElementAction.SUBMIT_ON_CLICK)
   public String onClick(final ControllerComponent comp) {
      if (this.getParent() instanceof ListComponent) {
         markListActiveElement(comp);
      }
      if (confirmation == null) {
         return getOnClickHandler().execute();
      } else {
         confirmation.setAcceptHandler(new ElementHandler() {

            private static final long serialVersionUID = 1L;

            @Override
            public String execute() {
               return EventBaseElement.this.getOnClickHandler().execute();
            }
         });
         confirmation.show();
         return null;
      }
   }

   /**
    * Tegemist on üldise sündmuse elemendi händlermeetodiga, mis kutsutakse välja <i>change</i> sündmuse puhul.
    * 
    * @param comp
    *           Kontrollerkomponent, mis on defineeritud põhikontrolleris
    * @return
    */
   @EventHandler(eventName = ElementAction.SUBMIT_ON_CHANGE)
   public String onChange(final ControllerComponent comp) {
      if (((Component) this.getParent()).getComponentType() == ComponentType.LIST) {
         markListActiveElement(comp);
      }
      return getOnChangeHandler().execute();
   }

   public void markListActiveElement(final ControllerComponent comp) {
      ListComponent listComponent = (ListComponent) this.getParent();
      // leiame rea objekti
      Object object = BeanUtil.getValueByPath(
            comp,
            StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(getFullPath(), String.valueOf(ID_DELIMITER)),
                  String.valueOf(ID_DELIMITER)));

      if (object instanceof ListRowButtons) {
         object = ((ListRowButtons) object).getListRow();
      }

      listComponent.setActiveEntity(((ListRow) object).getEntity());
      if (this instanceof FormElement) {
         listComponent.setActiveFormElement((FormElement) this);
      }
   }

   @Override
   public ConfirmationDialog getConfirmation() {
      if (confirmation != null) {
         confirmation.setId(this.getId());
      }

      return confirmation;
   }

   @Override
   public ConfirmationDialog setConfirmation(ConfirmationDialog confirmation) {
      if (confirmation != null && confirmation.getParentElementPath() == null) {
         confirmation.setParentElementPath(this);
      }

      return this.confirmation = confirmation;
   }

   /**
    * Meetod tagastab <i>true</i> kui antud elemendis eksisteerib kinnitusakna implementatsioon. Vastasel juhul tagastab <i>false</i>
    * 
    * @return
    */
   public boolean isHasConfirmation() {
      return confirmation != null;
   }

   public boolean isVisible() {
      return elementVisibility == null ? super.isVisible() : elementVisibility.isVisible(getParent());
   }

   public EventElement setVisibility(ElementVisibility elementVisibility) {
      this.elementVisibility = elementVisibility;
      return this;
   }

   public ElementVisibility getVisibility() {
      return this.elementVisibility;
   }

   /**
    * Meetod tagastab <code>true</code> kui eksisteerib onClick händler ehk {@link EventElement#getOnClickHandler()}!=null
    * 
    * @return {@link Boolean}
    */
   public boolean isHasOnClickHandler() {
      return this.getOnClickHandler() != null;
   }

   /**
    * Meetod tagastab <code>true</code> kui eksisteerib onClick händler ehk {@link EventElement#getOnChangeHandler()}!=null
    * 
    * @return {@link Boolean}
    */
   public boolean isHasOnChangeHandler() {
      return this.getOnChangeHandler() != null;
   }

   /**
    * Meetod tagastab sündmuse ID, mida kasutatakse esitluskihis päringu saatmiseks õigesse komponenti. Kui elemendi küljes eksiteerib
    * onClick või onChange händler, suunatakse päring lingi {@link LinkElement} või nupu {@link ButtonElement} elementi. Vastasel juhul
    * suunatakse päring sündmuse elemendi ülemelementi.
    * 
    * @return {@link String}
    */
   public String getEventDisplayId() {
      if (isHasOnClickHandler() || isHasOnChangeHandler()) {
         return super.getDisplayId();
      }

      return ((Component) getParent()).getDisplayId();
   }
}