package com.nortal.spring.cw.core.web.component.modal;

/**
 * @author Margus Hanni
 * @since 04.03.2013
 */
public enum ModalButtonName {

	// @formatter:off
	ACCEPT("global.modal.dialog.accept"),
	SAVE("global.modal.dialog.save"),
	OK("global.modal.dialog.ok"),
	;
	//@formatter:on

	private String labelCode;

	private ModalButtonName(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getCode() {
		return labelCode;
	}

}
