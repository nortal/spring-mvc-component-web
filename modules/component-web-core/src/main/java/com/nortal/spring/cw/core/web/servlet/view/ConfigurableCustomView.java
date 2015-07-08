package com.nortal.spring.cw.core.web.servlet.view;

/**
 * Üldine liides, mille implementeerimisel tekitatakse komponendile täiendav vaate režiimi vahetuse funktsionaalsus. Mis tähendab seda, et
 * komponendile on võimalik ette anda vaaterežiim. Režiime on kaks, vaatamine (VIEW) või muutumine (EDIT), mis on defineeritud enumis
 * {@link ViewMode}. Vastavalt implementatsioonile on võimalik komponendi sees määrata, missugust JSPd kasutatakse, samas komponent ise ja
 * tema andmestik jääb samaks kuid muutub visuaalne pool.
 * 
 * @author Margus Hanni
 * 
 */
public interface ConfigurableCustomView extends CustomView {
   /**
    * Komponendi kuvamisrežiimi määramine
    * 
    * @param viewMode
    *           {@link ViewMode}
    */
   void setViewMode(ViewMode viewMode);
}
