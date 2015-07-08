package com.nortal.spring.cw.core.web.component.css;

/**
 * @author Margus Hanni
 * @since 01.03.2013
 */
public enum ButtonElementCssClass {

	// @formatter:off
	BUTTON("button"),
	BUTTON_ALT("button alt"),	
	;
	// @formatter:on

	private String cssClass;

	private ButtonElementCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getValue() {
		return this.cssClass;
	}
}
