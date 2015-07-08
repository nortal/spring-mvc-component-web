package com.nortal.spring.cw.core.web.component.element;


/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 09.04.2013
 */
public abstract class AbstractConstraint extends BaseElementCheck implements ElementConstraint {

   private static final long serialVersionUID = 1L;

   protected String translate(String code) {
      return getMessageSource().resolveByActiveLang(code);
   }
}