package com.nortal.spring.cw.core.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

/**
 * WD'sse edastatava XML dokumendi sisu ja loogikat ühendav klass.
 * 
 * @author Alrik Peets
 * @since 25.11.2013
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.NONE)
public class XmlWDDokumentRootModel extends XmlRootModel implements XmlWDDokumentModel {
   private static final long serialVersionUID = 1L;

   // Add DokumentMeta XML representation

}
