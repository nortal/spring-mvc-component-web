package com.nortal.spring.cw.core.web.component.composite.simple;

import com.nortal.spring.cw.core.web.component.composite.Component;
import com.nortal.spring.cw.core.web.component.element.FormElement;

/**
 * @author Margus Hanni
 * @since 01.03.2013
 */
public interface FormComponent extends Component {

   <F extends FormElement> F add(String elementPath);

   <F extends FormElement> F add(FormElement element);

   void remove(FormElement element);
}