package com.nortal.spring.cw.core.web.component.css;

/**
 * @author Margus Hanni
 * @since 27.02.2013
 */
public enum FieldElementCaptionCssClass {
	// @formatter:off
	ALERT("alert"),
	MSG("msg"),	;
	// @formatter:on

	private String cssClass;

	private FieldElementCaptionCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getValue() {
		return this.cssClass;
	}
}
