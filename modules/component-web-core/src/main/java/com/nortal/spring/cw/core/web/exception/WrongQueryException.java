package com.nortal.spring.cw.core.web.exception;

import com.nortal.spring.cw.core.exception.AppBaseRuntimeException;

/**
 * @author Margus Hanni
 * @since 25.02.2013
 */
@SuppressWarnings("serial")
public class WrongQueryException extends AppBaseRuntimeException {

	public WrongQueryException(String errorMessageCode, Object... messageArgs) {
		super(errorMessageCode, messageArgs);
	}

}
