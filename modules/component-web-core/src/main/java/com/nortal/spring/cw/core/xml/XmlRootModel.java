package com.nortal.spring.cw.core.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * XML mudelobjekti juurelement. Antud objekti alusel osatakse laiendavatele
 * mudelobjektidele PDF genereerida
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 30.05.2013
 */
@XmlTransient
@XmlAccessorType(XmlAccessType.NONE)
public abstract class XmlRootModel implements XmlModel {
   private static final long serialVersionUID = 1L;

   /**
    * Tagastatakse käesoleva mudelobjekti PDFi malli nimetus
    * {@link XmlRootElement#name()} põhjal
    * 
    * @return {@link XmlRootElement#name()}
    */
   public String getTemplate() {
      XmlRootElement root = this.getClass().getAnnotation(XmlRootElement.class);
      return root.name();
   }

}
