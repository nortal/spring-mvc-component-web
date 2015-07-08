package com.nortal.spring.cw.core.web.component.css;

/**
 * Erinevate HTML elementide stiiliklasside komplektid. Antud komplektid annab üldjuhul ette visual, mis tähendab et iseseisvalt ei tohiks
 * neid väga juurde tekkida ning oluline on säilitada klasside komplektsus, et lihtsustada nende hilisemat kasutamist.
 * 
 * @author Margus Hanni
 * @since 25.02.2013
 */
public enum FieldElementCssClass {

   // @formatter:off
	TEXT("text"),
	TEXT_DATA("text data"),
	TEXT_MIN("text min"),
	TEXT_SMALL("text small"),
	TEXT_MEDIUM("text medium"),
	TEXT_WIDE("text wide"),
	TEXT_MAX("text max"),
	TEXT_WIDE_ADDRESS_AUTOCOMPLETE("text wide address-autocomplete autocomplete"),
	SMALL("small"),
	MIN("min"),
	DATE("date"),
	ERROR("error"),
	DATA("data"),
	MCE_RICH_TEXT("mceRichText"),
	REGULAR("regular"),
	MEDIUM("medium"),
	WIDE("wide"),
	BOLD("bold"),
	INFO("info"),
	LINKS("links"),
	CHECK("check"),
	CHECKED("checked");
	// @formatter:on

   private String cssClass;

   private FieldElementCssClass(String cssClass) {
      this.cssClass = cssClass;
   }

   /**
    * Meetod tagastab enumi väärtuse tekstilisel kujul, milleks on kas ühe klassi nimetus või klasside grupid.
    * 
    * @return {@link String}
    */
   public String getValue() {
      return this.cssClass;
   }

}
