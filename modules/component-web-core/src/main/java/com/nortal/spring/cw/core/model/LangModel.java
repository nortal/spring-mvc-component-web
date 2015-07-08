package com.nortal.spring.cw.core.model;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.web.component.multiple.LanguageElement;

/**
 * Antud abstraktsiooni kasutatakse esitluskihis keele elementide kuvamisel {@link LanguageElement}. Lihtsustab erinevate erinevates keeltes
 * sisestusväljade kuvamist kuna vastavalt aktiivsetele keeltele kuvatakse vastavalt sama palju sisestusvälju
 * 
 * @author Margus Hanni
 * 
 */
public interface LangModel {
   Lang getLang();

   void setLang(Lang lang);

   String getLangName();

   void setLangName(String langName);

}