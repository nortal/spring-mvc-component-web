package com.nortal.spring.cw.jsp.web.tag.multiple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.core.i18n.model.Lang;
import com.nortal.spring.cw.core.model.LangModel;
import com.nortal.spring.cw.core.web.component.multiple.LanguageElement;
import com.nortal.spring.cw.core.web.util.BeanUtil;
import com.nortal.spring.cw.core.web.util.ElementUtil;
import com.nortal.spring.cw.core.web.util.RequestUtil;
import com.nortal.spring.cw.jsp.web.tag.AbstractEpmFormTag;

/**
 * Keele tag. Vastavalt aktiivsetele keeltele genereeritakse sisestusväljad. <br>
 * Kui keele elemendil ei ole ühtegi keelt määratud, antud juhul keele loend on tühi siis tagi siseselt tekitatakse vastavalt tühi loend <br>
 * Keele elemendi loend peab implementeerima {@link LangModel}
 * 
 * @see LanguageElement
 * @author Margus Hanni
 * 
 */
// TODO IS NOT ready
public class CwLanguageInputTag extends AbstractEpmFormTag<LanguageElement> {
   private static final long serialVersionUID = 1L;

   @Override
   protected int writeTagContent(TagWriter tagWriter) throws JspException {
      List<Lang> langs = new ArrayList<>();
      // TODO find all active languages
      // langs.addAll(DomainCacheUtil.getActiveBaseDomainValues(Lang.class));

      if (element.getValue() == null) {
         element.setRawValue(new ArrayList<>());
      }

      // Collections.sort(langs);

      // kui listis keel puudub, siis tekitame objekti
      for (Lang lang : langs) {
         if (!hasLanguage(lang)) {
            LangModel model = BeanUtils.instantiate(element.getModelClass());
            model.setLang(lang);
            element.getValue().add(model);
         }
      }

      reSort();

      Lang activeLang = RequestUtil.getActiveLang();

      String fieldLabel = BeanUtil.getBean(CwMessageSource.class).resolveByActiveLang(element.getFullLabel());

      int index = 0;

      Errors errors = getErrors();

      for (Lang lang : langs) {
         String path = element.getLangFullPath(index);
         String id = ElementUtil.convertPathToDisplayId(path);

         FieldError error = errors.getFieldError(path);

         tagWriter.startTag("tr");
         tagWriter.startTag("th");

         if (error != null) {
            tagWriter.writeAttribute("class", "error");
         }

         if (element.getMandatoryLang() != null && element.getMandatoryLang().equals(lang)) {
            tagWriter.startTag("span");
            tagWriter.writeAttribute("class", "req");
            tagWriter.appendValue("*");
            tagWriter.endTag();
         }

         tagWriter.startTag("label");
         tagWriter.writeAttribute("for", id);
         tagWriter.appendValue(super.getDisplayString(getRequestContext().getMessage(fieldLabel, new Object[] { activeLang.getCode() })));
         tagWriter.appendValue(":");
         tagWriter.endTag();
         tagWriter.endTag();

         tagWriter.startTag("td");
         tagWriter.startTag("input");

         tagWriter.writeAttribute("value", super.getDisplayString(StringUtils.trimToEmpty(getValue(lang))));
         tagWriter.writeAttribute("class", StringUtils.trimToEmpty(element.getCssClassValue()) + (error == null ? " " : " error"));

         tagWriter.writeAttribute("name", path);
         tagWriter.writeAttribute("id", id);
         tagWriter.endTag();

         showError(tagWriter, error);

         tagWriter.endTag();

         tagWriter.endTag();

         index++;
      }

      return SKIP_BODY;
   }

   @Override
   public void setElement(LanguageElement element) {
      this.element = element;
   }

   private void reSort() {
      Collections.sort(element.getValue(), LanguageElement.COMPARATOR);
   }

   private String getValue(Lang lang) {

      for (LangModel langModel : element.getValue()) {
         if (langModel.getLang().equals(lang)) {
            return langModel.getLangName();
         }
      }

      return StringUtils.EMPTY;
   }

   private boolean hasLanguage(Lang lang) {

      for (LangModel langModel : element.getValue()) {
         if (langModel.getLang().equals(lang)) {
            return true;
         }
      }

      return false;
   }

}
