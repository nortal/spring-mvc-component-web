package com.nortal.spring.cw.jsp.model.xml.dokument.vorm;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.xml.XmlRootModel;
import com.nortal.spring.cw.jsp.model.xml.dokument.model.GeneralDokumentModel;

/**
 * Vormdokumendi XML alus mudel
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 21.03.2014
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlType
public abstract class XmlVormDokument<T extends GeneralDokumentModel> extends XmlRootModel {
   private static final long serialVersionUID = 1L;

   public static final String DEFAULT_VERSION = "v1";
   public static final String ROOT_NAME = "dokument";

   @XmlAttribute(required = true)
   private String kood;
   @XmlAttribute
   private String versioon;

   public abstract T getSisu();

   public abstract void setSisu(T sisu);

   @Override
   public String getTemplate() {
      String result = null;
      String targetPackage = getClass().getPackage().getName();
      String vdPackage = XmlVormDokument.class.getPackage().getName();

      result = StringUtils.removeStart(targetPackage, vdPackage);
      result = result.replaceFirst("\\.", "");
      result = result.replaceFirst("\\.", "/");
      return result;
   }
}
