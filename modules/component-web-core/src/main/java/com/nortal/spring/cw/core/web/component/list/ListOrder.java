package com.nortal.spring.cw.core.web.component.list;

/**
 * Antud klassi kasutatakse listi veergude järjestamisel. Klassis hoitakse
 * järjestatud veeru nime, milleks on üldjuhul selle elemendi enda rada ning
 * järjestust kas desc või asc
 * 
 * @author Margus Hanni
 */
public class ListOrder {

   private String orderedField;
   private boolean desc;

   /**
    * Meetod tagastab käesoleva elemendi raja, ehk veeru koodi mida
    * järjestatakse
    * 
    * @return {@link String}
    */
   public String getOrderedField() {
      return orderedField;
   }

   /**
    * Järjestatava veeru koodi ehk raja määramine
    * 
    * @param orderedField
    *           Järjestatava veeru kood
    * @return {@link ListOrder}
    */
   public ListOrder setOrderedField(String orderedField) {
      this.orderedField = orderedField;
      return this;
   }

   /**
    * Meetod tagastab <code>true</code> juhul kui veer on järjestatud suuremast
    * väiksema suunas
    * 
    * @return
    */
   public boolean isDesc() {
      return desc;
   }

   /**
    * Veeru järjestuse määramine
    * 
    * @param desc
    *           Veeru järjestise muutmine, kui <code>true</code> on järjestus
    *           desc vastasel juhul asc
    * @return {@link ListOrder}
    */
   public ListOrder setDesc(boolean desc) {
      this.desc = desc;
      return this;
   }

}
