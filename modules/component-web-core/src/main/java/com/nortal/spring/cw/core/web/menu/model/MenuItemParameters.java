package com.nortal.spring.cw.core.web.menu.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Menüüpunkti täiendavad parameetrid
 * 
 * @author Margus Hanni
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MenuItemParameters implements Serializable {
   private static final long serialVersionUID = 1L;

   /**
    * Menüüpunkti lingi nimi
    */
   @NonNull
   private String text;
   /**
    * Menüüpunkti vahendusel avatav aadress
    */
   @NonNull
   private String url;

   /**
    * Menüüpunkti kood
    */
   private String code;

}
