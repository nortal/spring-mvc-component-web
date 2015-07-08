package com.nortal.spring.cw.core.web.component.list.row;

import java.util.LinkedHashMap;
import java.util.Map;

import com.nortal.spring.cw.core.web.component.ElementPath;
import com.nortal.spring.cw.core.web.component.ElementVisibility;
import com.nortal.spring.cw.core.web.component.EventBaseElement;
import com.nortal.spring.cw.core.web.component.Hierarchical;
import com.nortal.spring.cw.core.web.component.element.ElementLabel;
import com.nortal.spring.cw.core.web.component.element.EventElement;
import com.nortal.spring.cw.core.web.component.element.FormElement;
import com.nortal.spring.cw.core.web.component.event.LinkElement;
import com.nortal.spring.cw.core.web.component.event.confirmation.DeleteConfirmationDialog;
import com.nortal.spring.cw.core.web.component.form.FormElementHolderMapItem;
import com.nortal.spring.cw.core.web.component.global.GlobalLabel;
import com.nortal.spring.cw.core.web.util.ElementUtil;

/**
 * Listi rea {@link ListRow} tegevuste nupud. Automaatselt initsialiseeritakse rea muutmise, kustutamise ja toimingu katkestamise nupud.
 * Antud nuppe ei kuvata automaatselt välja, mis tähendab seda, et nende vähtavus () {@link EventBaseElement#getVisibility()} on
 * <code>false</code>
 * 
 * @author Margus Hanni
 * @since 27.01.2014
 */
public class ListRowButtons implements ElementPath {

   private static final long serialVersionUID = 1L;

   public static final String LIST_EVENT_DELETE = "submitListTableDelete";
   public static final String LIST_EVENT_EDIT = "submitListTableEdit";
   public static final String LIST_EVENT_EDIT_CANCEL = "submitListTableEditCancel";

   private static final String LIST_DELETE_BUTTON = "listDeleteButton";
   private static final String LIST_EDIT_BUTTON = "listChangeButton";
   private static final String LIST_EDIT_CANCEL_BUTTON = "listChangeCancelButton";

   private ElementPath parentElementPath;
   private ListRow listRow;
   private final Map<String, FormElementHolderMapItem> buttons = new LinkedHashMap<>();

   public ListRow getListRow() {
      return listRow;
   }

   public void setListRow(ListRow listRow) {
      this.listRow = listRow;
      this.setParentElementPath(listRow);
   }

   public Map<String, FormElementHolderMapItem> getElementHolder() {

      // FIXME Margus - Kustutamise nupu jõuliselt kõige viimaseks lisamine. Tegemist on ajutisel lahendusega, seniks kuni elementide ja
      // komponentide refaktoreerimine on jõudnud lõpule

      Map<String, FormElementHolderMapItem> newMap = new LinkedHashMap<>();
      newMap.putAll(buttons);

      if (isHasDeleteButton()) {
         EventElement deleteLink = buttons.get(LIST_DELETE_BUTTON).getElement();
         newMap.remove(LIST_DELETE_BUTTON);
         newMap.put(LIST_DELETE_BUTTON, new FormElementHolderMapItem((FormElement) deleteLink, this));
      }

      return newMap;
   }

   public EventElement addEventButton(final EventElement eventElement) {
      String id = eventElement.getId();
      if (buttons.containsKey(eventElement.getId())) {
         id = eventElement.getId() + buttons.size();
      }
      buttons.put(id, new FormElementHolderMapItem((FormElement) eventElement, this));
      return eventElement;
   }

   public EventElement getDeleteButton() {
      if (isHasDeleteButton()) {
         return buttons.get(LIST_DELETE_BUTTON).getElement();
      }
      return null;
   }

   public EventElement getEditButton() {
      if (isHasEditButton()) {
         return buttons.get(LIST_EDIT_BUTTON).getElement();
      }
      return null;
   }

   public boolean isHasDeleteButton() {
      return buttons.containsKey(LIST_DELETE_BUTTON);
   }

   public boolean isHasEditButton() {
      return buttons.containsKey(LIST_EDIT_BUTTON);
   }

   /**
    * Meetod tagastab <code>true</code>, kui eksisteerib mõni nähtav ({@link EventElement#isVisible()} == true) link
    * 
    * @return {@link Boolean}
    */
   public boolean isHasActionButtons() {
      for (FormElementHolderMapItem holder : getElementHolder().values()) {
         if (holder.getElement().isVisible()) {
            return true;
         }
      }

      return false;
   }

   /**
    * Meetod tagastab sündmuse elemendi, mille nimeks on etteantud elemendi nimi. Kui elementi ei leitud, tagastatakse <code>null</code>
    * 
    * @param elementName
    *           {@link String} Otsitava sündmuse elemendi nimi
    * @return {@link EventElement}
    */
   public EventElement getElementByName(final String elementName) {
      for (FormElementHolderMapItem ee : buttons.values()) {
         FormElement element = ee.getElement();
         if (element.getId().equals(elementName)) {
            return element;
         }
      }
      return null;
   }

   public LinkElement allowDeleteButton(final boolean allow) {

      LinkElement deleteLink;
      if (isHasDeleteButton()) {
         deleteLink = (LinkElement) buttons.get(LIST_DELETE_BUTTON).getElement();
      } else {
         deleteLink = createDeleteLink();
      }

      deleteLink.setVisibility(new ElementVisibility() {

         private static final long serialVersionUID = 1L;

         @Override
         public boolean isVisible(Hierarchical parent) {
            return allow && !ListRowButtons.this.getListRow().isEditable();
         }
      });

      return deleteLink;
   }

   public LinkElement allowEditButton(final boolean allow) {
      LinkElement editLink;
      if (isHasEditButton()) {
         editLink = (LinkElement) buttons.get(LIST_EDIT_BUTTON).getElement();
      } else {
         editLink = createEditLink();
      }
      editLink.setVisible(allow);

      return editLink;
   }

   public void createDefaultButtons() {
      createEditLink();
      createDeleteLink();
   }

   private LinkElement createEditLink() {
      // muutmine
      LinkElement editLink = (LinkElement) addEventButton(new LinkElement(LIST_EDIT_BUTTON));
      editLink.setEventName(LIST_EVENT_EDIT);

      editLink.setElementLabel(new ElementLabel() {
         private static final long serialVersionUID = 1L;

         @Override
         public String getLabel() {
            return ListRowButtons.this.getListRow().isEditable() ? GlobalLabel.LIST_BUTTON_SAVE : GlobalLabel.LIST_BUTTON_EDIT;
         }
      });

      editLink.setParent(getListRow().getParent());
      editLink.setVisible(false);

      // muutmise katkestamine
      LinkElement editCancelLink = new LinkElement(LIST_EVENT_EDIT_CANCEL, GlobalLabel.LIST_BUTTON_CANCEL);
      editCancelLink.setParent(getListRow().getParent());
      editCancelLink.setVisible(false);
      editCancelLink.setId(LIST_EDIT_CANCEL_BUTTON);
      addEventButton(editCancelLink);

      return editLink;
   }

   private LinkElement createDeleteLink() {
      // kustutamine
      LinkElement deleteLink = new LinkElement(LIST_EVENT_DELETE, GlobalLabel.LIST_BUTTON_DELETE);
      deleteLink.setId(LIST_DELETE_BUTTON);
      DeleteConfirmationDialog confirmationDialog = new DeleteConfirmationDialog(getListRow().getParent());
      confirmationDialog.setParentElementPath(deleteLink);
      deleteLink.setConfirmation(confirmationDialog);
      deleteLink.setParent(getListRow().getParent());
      deleteLink.setVisible(false);
      addEventButton(deleteLink);

      return deleteLink;
   }

   @Override
   public String getPath() {
      return ElementUtil.getNameForFullPath(this);
   }

   @Override
   public ElementPath getParentElementPath() {
      return parentElementPath;
   }

   @Override
   public void setParentElementPath(ElementPath elementPath) {
      this.parentElementPath = elementPath;
   }
}
