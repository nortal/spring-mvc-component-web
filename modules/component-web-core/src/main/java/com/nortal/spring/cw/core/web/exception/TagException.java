package com.nortal.spring.cw.core.web.exception;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;

/**
 * @author Margus Hanni
 * @since 18.03.2013
 */
@SuppressWarnings("serial")
public class TagException extends AppBaseRuntimeException {

	public TagException(String errorMessageCode, Object... messageArgs) {
		super(errorMessageCode, messageArgs);
	}

	public TagException(Throwable throwable) {
		super(throwable);
	}

}
