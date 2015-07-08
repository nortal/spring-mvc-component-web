package com.nortal.spring.cw.core.xml;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * XML mudelobjektide koostamise juhtimise abivahend, millega on võimalik koodis
 * määratud kirjeldused üle kirjutada. Antud annotatsiooniga on võimalik
 * {@link XmlRootElement} tasemel määrata objekti parsimisel kasutatavate
 * kirjelduse failide asukohad (otsitakse rakenduse classpathist).
 * 
 * @see <a
 *      href="http://www.eclipse.org/eclipselink/documentation/2.4/moxy/runtime003.htm">MOXy
 *      Metadata</a>
 * @author Lauri Lättemäe (lauri.lattemae@nortal.com)
 * @since 13.09.2013
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlBinding {
   String[] value();
}
