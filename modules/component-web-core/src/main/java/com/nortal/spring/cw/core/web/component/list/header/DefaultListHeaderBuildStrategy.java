package com.nortal.spring.cw.core.web.component.list.header;

/**
 * Vaikimisi listi päise elemendi ehitamise strateegia.
 * 
 * @author margush
 * 
 */
public class DefaultListHeaderBuildStrategy implements ListHeaderBuildStrategy {

   private static final long serialVersionUID = 1L;

   @Override
   public String getElementPath(int cellNumber) {
      return String.format(HEADER_PATH, cellNumber, "element");
   }

   @Override
   public String getElementBetweenEndPath(int cellNumber) {
      return String.format(HEADER_PATH, cellNumber, "between.right");
   }

   @Override
   public String getElementBetweenStartPath(int cellNumber) {
      return String.format(HEADER_PATH, cellNumber, "between.left");
   }
}
