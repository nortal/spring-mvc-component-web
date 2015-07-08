package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

/**
 * Liidest kasutatakse elemendi abiinfo tingimuslikuks näitamiseks. Teatud olukordades ei ole välja juures abiinfo kuvamine vajalik või ka
 * võimalik näiteks juhul kui sisuline abiinfo tekst puudub, sellisel juhul ei ole vaja näidata ka abiinfo ikooni.
 * 
 * @author Margus Hanni
 * @since 06.11.2013
 */
public interface ElementTooltipVisibility extends Serializable {

   /**
    * Kui meetod tagastab <code>true</code> kuvatakse abiinfo ikoon koos abiinfo aknaga, vastasel juhul ei kuvata
    * 
    * @param tooltip
    *           {@link ElementTooltip} Konkreetne element, mis implementeerib liidest {@link ElementTooltip}
    * @return {@link Boolean
    */
   public boolean isVisible(ElementTooltip tooltip);
}
