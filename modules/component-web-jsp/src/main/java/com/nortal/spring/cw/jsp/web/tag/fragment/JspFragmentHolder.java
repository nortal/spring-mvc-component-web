package com.nortal.spring.cw.jsp.web.tag.fragment;

import java.io.StringWriter;
import java.util.HashMap;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;

import com.nortal.spring.cw.core.web.exception.TagException;

/**
 * Tegemist on komponendiga, mis hoiab endas erinevaid päringu skoobis elavaid
 * fragmente
 * 
 * @author Margus Hanni
 * @since 18.03.2013
 */
public class JspFragmentHolder {

	private static final String HOLDER_KEY = "__FRAGMENT_REG__";
	private static int scope = PageContext.REQUEST_SCOPE;

	public static void put(JspContext ctx, String name, JspFragment frag) {
		HashMap<String, JspFragment> attribute = getFragments(ctx);
		if (attribute == null) {
			attribute = new HashMap<String, JspFragment>();
			ctx.setAttribute(HOLDER_KEY, attribute, scope);
		}
		attribute.put(name, frag);
	}

	public static String evaluate(JspContext ctx, String name) {
		HashMap<String, JspFragment> attribute = getFragments(ctx);
		if (attribute != null) {
			try {
				JspFragment frag = (JspFragment) attribute.get(name);
				StringWriter writer = new StringWriter();
				StringBuffer sb = writer.getBuffer();
				frag.invoke(writer);
				writer.flush();
				return sb.toString();
			} catch (Exception e) {
				throw new TagException(e);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, JspFragment> getFragments(JspContext ctx) {
		return (HashMap<String, JspFragment>) ctx.getAttribute(HOLDER_KEY, scope);
	}
}
