package com.nortal.spring.cw.core.web.component.element;

/**
 * Erinevate vormelementide tüübi enum
 * 
 * @author Margus Hanni
 */
public enum FieldElementType {

   // @formatter:off
	DATETIME,
	STRING,
	LONG,
	INTEGER,
	DOUBLE,
	BOOLEAN,
	STRING_COLLECTION,
	LONG_COLLECTION,
	INTEGER_COLLECTION,
	LINK,
	BUTTON,
	FILE,
	FILE_COLLECTION,
	LANGUAGE,
	SIMPLE_TEXT;
	// @formatter:on

   public String getString() {
      return this.name().toLowerCase();
   }
}
