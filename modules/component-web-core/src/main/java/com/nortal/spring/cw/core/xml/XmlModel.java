package com.nortal.spring.cw.core.xml;

import java.io.Serializable;

/**
 * @author Margus Hanni
 * 
 */
public interface XmlModel extends Serializable {

   String NAMESPACE = "http://epm.agri.ee/";
   String PREFIX = "epm";
   String SYSTEM_ID = "epm-schema";
}
