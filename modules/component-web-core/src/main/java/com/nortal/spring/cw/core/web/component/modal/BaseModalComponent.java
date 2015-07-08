package com.nortal.spring.cw.core.web.component.modal;

import com.nortal.spring.cw.core.web.component.composite.ComponentType;

public class BaseModalComponent extends AbstractModalDialogComponent {
   private static final long serialVersionUID = 1L;

   private ModalType type;

   public BaseModalComponent(ModalType type, String componentName) {
      super(componentName);
      this.type = type;
   }

   @Override
   public ModalType getType() {
      return type;
   }

   @Override
   public ComponentType getComponentType() {
      return ComponentType.MODAL;
   }

}
