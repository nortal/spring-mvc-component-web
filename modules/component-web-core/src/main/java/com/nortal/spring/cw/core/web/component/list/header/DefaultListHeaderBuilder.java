package com.nortal.spring.cw.core.web.component.list.header;


/**
 * Vaikimisi listi päise elemendi initsialiseerimine ning path määramine
 * 
 * @author margush
 * 
 */
public class DefaultListHeaderBuilder implements ListHeaderBuilder {

   private static final long serialVersionUID = 1L;
   public static final ListHeaderBuildStrategy listHeaderBuildStrategy = new DefaultListHeaderBuildStrategy();

   public void init(final ListHeaderBuildStrategy buildStrategy, ListHeader header, final int cellNumber) {
      header.initComponent();
      customizeHeader(header);
   }

   // = Extension points for modifying ListHeader =
   /**
    * Called after ListHeader is initiated
    * 
    * @param header
    */
   public void customizeHeader(ListHeader header) {
      // By default we do nothing
   }

}
