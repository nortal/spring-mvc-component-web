package com.nortal.spring.cw.jsp.web.tag.fragment;

import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * Tegemist on komponendiga, mis registreerib ära kindla fragmendi, mida saab
 * päringuskoobis mujal välja kutsuda
 * 
 * @author Margus Hanni
 * @since 18.03.2013
 */
public class CreateFragmentTag extends SimpleTagSupport {

	private String name;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void setJspBody(JspFragment jspBody) {
		JspFragmentHolder.put(getJspContext(), name, jspBody);
	}

}
