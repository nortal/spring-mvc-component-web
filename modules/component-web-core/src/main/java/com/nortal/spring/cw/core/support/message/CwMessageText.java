package com.nortal.spring.cw.core.support.message;

import java.io.Serializable;

import com.nortal.spring.cw.core.i18n.model.Lang;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @author Margus Hanni <margus.hanni@nortal.com>
 * @since 20.05.2015
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CwMessageText implements Serializable {

   private static final long serialVersionUID = 1L;

   private String messageCode;

   private Lang lang;
   private String textValue;

}
