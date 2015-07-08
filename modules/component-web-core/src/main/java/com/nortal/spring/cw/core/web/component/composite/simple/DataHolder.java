package com.nortal.spring.cw.core.web.component.composite.simple;

/**
 * Identifies an element as containing data
 * 
 * @author Alrik Peets
 * @param <C>
 *           Data class
 */
public interface DataHolder {
	<C> C getData();

	void setData(Object data);

}
