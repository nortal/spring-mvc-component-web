package com.nortal.spring.cw.core.web.component.element;

import java.io.Serializable;

import com.nortal.spring.cw.core.web.component.composite.Component;

/**
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @author Margus Hanni
 * @create 09.04.2013
 */
public interface ElementValidator extends Serializable {
   void validate();

   void setElementId(String elementId);

   // TODO Alrik: Selle setParent asemel võiks check/validate meetod võtta
   // parameetrina Component'i ... sisuliselt kutsutaks seda meetodit siis välja
   // check(this); viisil. Võimalik et selline lähenemine annab võimaluse
   // ehitada ka veel üldisemaid constrainte/validaatoreid. Validaatori puhul
   // saaks sisuliselt sama validaatorit rakendada suvalise elemendi peal,
   // konstraint aga töötaks aktiivse elemendi kontekstis - ehk siis põhielement
   // (mis läheb ka check/validate meetodile ette) ja siis teised vms. Järgmine
   // nädal võiks veidi arutada sel teemal
   void setParent(Component parent);
}
