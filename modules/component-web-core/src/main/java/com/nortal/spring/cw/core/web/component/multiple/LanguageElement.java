package com.nortal.spring.cw.core.web.component.multiple;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.model.LangModel;
import com.nortal.spring.cw.core.web.component.css.FieldElementCssClass;
import com.nortal.spring.cw.core.web.component.element.AbstractBaseElement;
import com.nortal.spring.cw.core.web.component.element.AbstractValidator;
import com.nortal.spring.cw.core.web.component.element.FieldElementType;
import com.nortal.spring.cw.core.web.component.element.FieldErrorEnum;

/**
 * Tegemist on keele elemendiga. Vastavalt aktiivsetele keeltele kuvatakse vastavalt sama palju sisestusvälju. Keele andmemudel peab
 * implementeerima {@link LangModel}
 * 
 * @author Margus Hanni
 * @date 08.08.2013 Lauri Lättemäe
 */
public class LanguageElement extends AbstractBaseElement<List<LangModel>> {

   private static final long serialVersionUID = 1L;

   public static final Comparator<LangModel> COMPARATOR = new Comparator<LangModel>() {
      @Override
      public int compare(LangModel o1, LangModel o2) {
         return o1.getLangName().compareTo(o2.getLangName());
      }
   };

   private Class<? extends LangModel> modelClass;
   private Lang mandatoryLang;

   {
      addValidator(new AbstractValidator() {
         private static final long serialVersionUID = 1L;

         @Override
         public void validate() {
            if (isMandatory() && mandatoryLang != null) {
               Collection<LangModel> value = getValue();
               int index = 0;
               for (LangModel langModel : value) {
                  if (mandatoryLang.equals(langModel.getLang()) && StringUtils.isEmpty(langModel.getLangName())) {
                     getBindingResult().rejectValue(getLangFullPath(index), FieldErrorEnum.MANDATORY.getTranslationKey());
                  }
                  index++;
               }
            }
         }
      });
   }

   public LanguageElement() {
      super();
      setCssClass(FieldElementCssClass.TEXT);
   }

   @Override
   public FieldElementType getElementType() {
      return FieldElementType.LANGUAGE;
   }

   @Override
   public String getDisplayValue() {
      if (getValue() == null) {
         return StringUtils.EMPTY;
      }

      StringBuilder builder = new StringBuilder();
      for (LangModel langModel : getValue()) {
         builder.append(langModel.getLangName()).append("<br/>");
      }

      return builder.toString();
   }

   @Override
   public int compareTo(Object o) {
      return 0;
   }

   public LanguageElement setModelClass(Class<? extends LangModel> modelClass) {
      this.modelClass = modelClass;
      return this;
   }

   public Class<? extends LangModel> getModelClass() {
      return modelClass;
   }

   public Lang getMandatoryLang() {
      return mandatoryLang;
   }

   public void setMandatoryLang(Lang mandatoryLang) {
      this.mandatoryLang = mandatoryLang;
      setMandatory(true);
   }

   public String getLangFullPath(int index) {
      return getFullPath() + ".value[" + index + "].tekst";
   }

   @Override
   public void setValue(List<LangModel> value) {
      if (value != null) {
         Collections.sort(value, COMPARATOR);
      }
      super.setValue(value);
   }

   @Override
   public List<LangModel> getValue() {
      // Ära seda meetodit ära kustuta, Spring MVC vajab seda, vastasel juhul
      // tema jaoks getter ja setter tüübid ei kattu
      return super.getValue();
   }
}
