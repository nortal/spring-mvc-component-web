package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

/**
 * Elemendi abiinfo tingimuslik kuvamise abstraktsioon
 * 
 * @author Margus Hanni
 * @since 06.11.2013
 */
public interface ElementTooltip extends Serializable {

   /**
    * Elemendi abitekst
    * 
    * @return {@link String}
    */
   String getText();

   /**
    * Meetod tagastab <code>true</code> kui elemendi abiinfo on nähtav
    * 
    * @return {@link Boolean}
    */
   boolean isVisible();

   /**
    * Elemendi abiinfo tingimuslik kuvamine
    * 
    * @param tooltipVisibility
    *           {@link ElementTooltipVisibility}
    */
   void setVisibility(ElementTooltipVisibility tooltipVisibility);
}
