package com.nortal.spring.cw.jsp.web.tag;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.web.util.BeanUtil;

/**
 * Klass sisaldab erinevaid Tag utiliite
 * 
 * @author Margus Hanni
 */
public class TagUtils {

	public static boolean instanceOf(Object obj, String className) {
		boolean check;

		try {
			check = Class.forName(className).isInstance(obj);
		} catch (ClassNotFoundException e) {
			check = false;
		}

		return check;
	}

	public static boolean hasProperty(Object obj, String propertyName) {

		String methodName = BeanUtil.GET + StringUtils.capitalize(propertyName);
		for (Method method : obj.getClass().getMethods()) {
			if (StringUtils.equals(method.getName(), methodName)) {
				return true;
			}
		}

		return false;
	}
}
