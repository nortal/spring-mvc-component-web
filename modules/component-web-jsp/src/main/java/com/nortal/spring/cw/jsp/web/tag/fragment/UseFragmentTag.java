package com.nortal.spring.cw.jsp.web.tag.fragment;

import java.io.IOException;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Tegemist on komponendiga, mis kutsub päringu skoobist välja kindla nimega
 * fragmendi ning väärtustab selle sisu
 * 
 * @author Margus Hanni
 * @since 18.03.2013
 */
public class UseFragmentTag extends SimpleTagSupport {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspContext ctx = getJspContext();
		ctx.getOut().write(JspFragmentHolder.evaluate(ctx, name));
		ctx.getOut().flush();
	}
}
