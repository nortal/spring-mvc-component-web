package com.nortal.spring.cw.core.xml;

import org.eclipse.persistence.oxm.XMLNameTransformer;

/**
 * @author Margus Hanni
 * 
 */
public class EpmXMLNameTransformer implements XMLNameTransformer {

   @Override
   public String transformTypeName(String name) {
      return transformRootElementName(name) + "Type";
   }

   @Override
   public String transformElementName(String name) {
      return transformXmlElementName(name);
   }

   @Override
   public String transformAttributeName(String name) {
      return transformElementName(name);
   }

   @Override
   public String transformRootElementName(String name) {
      return name.substring(name.lastIndexOf('.') + 1);
   }

   public static String transformXmlElementName(String name) {
      StringBuilder strBldr = new StringBuilder();
      for (char character : name.toCharArray()) {
         if (Character.isUpperCase(character) || Character.isDigit(character)) {
            strBldr.append('_');
            strBldr.append(Character.toLowerCase(character));
         } else {
            strBldr.append(character);
         }
      }
      return strBldr.toString();
   }

}
