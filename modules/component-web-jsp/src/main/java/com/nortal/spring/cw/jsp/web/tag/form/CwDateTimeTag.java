package com.nortal.spring.cw.jsp.web.tag.form;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.servlet.tags.form.AbstractHtmlInputElementTag;
import org.springframework.web.servlet.tags.form.HiddenInputTag;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.nortal.spring.cw.core.web.component.element.DateTimeElementFormat;
import com.nortal.spring.cw.core.web.component.single.DateTimeElement;

/**
 * Antud klass tekitab kuupäeva välja koos kellaaja valikuga. Lähtuvalt elemendis {@link DateTimeElement} määratud formaadist näidatakse kas
 * ainult kuupäeva, kellaaja või siis mõlemat välja. <br>
 * <br>
 * Sisuliselt tekitatakse maksimaalselt neli välja: varjatud väli, milles hoitakse aktuaalset väärtust, kuupäeva väli, tunni ja minuti
 * väljad. Vastavalt määrangutele näidatakse kas ainult kuupäeva, kellaaja või kõiki välju
 * 
 * @author Margus Hanni
 * @since 19.03.2013
 * @jsp.tag name = "dateTime"
 */
@SuppressWarnings("serial")
public class CwDateTimeTag extends HiddenInputTag {

   private static final String READONLY_ATTRIBUTE = "readonly";
   private static final String SIZE_ATTRIBUTE = "size";
   private static final String MAXLENGTH_ATTRIBUTE = "maxlength";
   private static final String ALT_ATTRIBUTE = "alt";
   private static final String ONSELECT_ATTRIBUTE = "onselect";
   private static final String AUTOCOMPLETE_ATTRIBUTE = "autocomplete";
   private static final String ONCHANGE_ATTRIBUTE = "onchange";
   private static final String VALUE_ATTRIBUTE = "value";
   private static final String SELECTED_ATTRIBUTE = "selected";
   private static final String CSS_CLASS = "class";

   private static final String INPUT_ELEMENT = "input";
   private static final String SELECT_ELEMENT = "select";
   private static final String SPAN_ELEMENT = "span";
   private static final String OPTION_ELEMENT = "option";

   private ELEMENT_TYPE activeElementType = ELEMENT_TYPE.DATE;

   private enum ELEMENT_TYPE {
      DATE, HOUR, MINUTE,
   }

   private DateTimeElement dateTimeElement;
   private String size;
   private String maxlength;
   private String alt;
   private String onselect;
   private String autocomplete;
   private String readonly;
   private boolean hiddenField = true;
   private String objectId;

   /**
    * Set the value of the '<code>size</code>' attribute. May be a runtime expression.
    */
   public void setSize(String size) {
      this.size = size;
   }

   /**
    * Get the value of the '<code>size</code>' attribute.
    */
   protected String getSize() {
      return this.size;
   }

   /**
    * Set the value of the '<code>maxlength</code>' attribute. May be a runtime expression.
    */
   public void setMaxlength(String maxlength) {
      this.maxlength = maxlength;
   }

   /**
    * Get the value of the '<code>maxlength</code>' attribute.
    */
   protected String getMaxlength() {
      return this.maxlength;
   }

   /**
    * Set the value of the '<code>alt</code>' attribute. May be a runtime expression.
    */
   public void setAlt(String alt) {
      this.alt = alt;
   }

   /**
    * Get the value of the '<code>alt</code>' attribute.
    */
   protected String getAlt() {
      return this.alt;
   }

   /**
    * Set the value of the '<code>onselect</code>' attribute. May be a runtime expression.
    */
   public void setOnselect(String onselect) {
      this.onselect = onselect;
   }

   /**
    * Get the value of the '<code>onselect</code>' attribute.
    */
   protected String getOnselect() {
      return this.onselect;
   }

   /**
    * Set the value of the '<code>autocomplete</code>' attribute. May be a runtime expression.
    */
   public void setAutocomplete(String autocomplete) {
      this.autocomplete = autocomplete;
   }

   /**
    * Get the value of the '<code>autocomplete</code>' attribute.
    */
   protected String getAutocomplete() {
      return this.autocomplete;
   }

   /**
    * Writes the '<code>input</code>' tag to the supplied {@link TagWriter}. Uses the value returned by {@link #getType()} to determine
    * which type of ' <code>input</code>' element to render.
    */
   @Override
   protected int writeTagContent(TagWriter tagWriter) throws JspException {
      super.writeTagContent(tagWriter);

      objectId = getId();
      hiddenField = false;

      String elementValue = getDisplayString(getBoundValue(), getPropertyEditor());
      elementValue = processFieldValue(getName(), elementValue, "hidden");

      if (dateTimeElement.getFormat() == DateTimeElementFormat.DATETIME || dateTimeElement.getFormat() == DateTimeElementFormat.DATE) {
         activeElementType = ELEMENT_TYPE.DATE;
         tagWriter.startTag(INPUT_ELEMENT);

         writeDefaultAttributes(tagWriter);

         if (!hasDynamicTypeAttribute()) {
            tagWriter.writeAttribute("type", getType());
         }

         tagWriter.writeAttribute(VALUE_ATTRIBUTE, StringUtils.substringBefore(elementValue, " "));
         tagWriter.writeAttribute(ONCHANGE_ATTRIBUTE, "return dateTimeChangeEvent('" + objectId + "')");

         // custom optional attributes
         writeOptionalAttribute(tagWriter, SIZE_ATTRIBUTE, getSize());
         writeOptionalAttribute(tagWriter, MAXLENGTH_ATTRIBUTE, "10");
         writeOptionalAttribute(tagWriter, ALT_ATTRIBUTE, getAlt());
         writeOptionalAttribute(tagWriter, ONSELECT_ATTRIBUTE, getOnselect());
         writeOptionalAttribute(tagWriter, AUTOCOMPLETE_ATTRIBUTE, getAutocomplete());

         if (isReadonly()) {
            writeOptionalAttribute(tagWriter, READONLY_ATTRIBUTE, READONLY_ATTRIBUTE);
         }

         if (isDisabled()) {
            writeOptionalAttribute(tagWriter, DISABLED_ATTRIBUTE, DISABLED_ATTRIBUTE);
         }

         tagWriter.endTag();
      }

      if (dateTimeElement.getFormat() == DateTimeElementFormat.DATETIME || dateTimeElement.getFormat() == DateTimeElementFormat.TIME) {

         String time = StringUtils.substringAfter(elementValue, " ");
         time = StringUtils.isEmpty(time) ? elementValue : time;

         int hour = NumberUtils.toInt(StringUtils.substringBefore(time, ":"));
         int minute = NumberUtils.toInt(StringUtils.substringAfter(time, ":"));

         tagWriter.startTag(SPAN_ELEMENT);
         tagWriter.writeAttribute(CSS_CLASS, "nowrap");

         // tunnid
         activeElementType = ELEMENT_TYPE.HOUR;
         tagWriter.startTag(SELECT_ELEMENT);

         writeDefaultAttributes(tagWriter);
         tagWriter.writeAttribute(ONCHANGE_ATTRIBUTE, "return dateTimeChangeEvent('" + objectId + "')");

         if (isReadonly() || isDisabled()) {
            writeOptionalAttribute(tagWriter, DISABLED_ATTRIBUTE, DISABLED_ATTRIBUTE);
         }

         for (int i = 0; i <= 23; i++) {
            String value = String.format("%02d", i);
            tagWriter.startTag(OPTION_ELEMENT);
            tagWriter.writeAttribute(VALUE_ATTRIBUTE, value);

            if (hour == i) {
               tagWriter.writeAttribute(SELECTED_ATTRIBUTE, SELECTED_ATTRIBUTE);
            }

            tagWriter.appendValue(value);
            tagWriter.endTag();
         }
         tagWriter.endTag();

         // minutid
         activeElementType = ELEMENT_TYPE.MINUTE;
         tagWriter.startTag(SELECT_ELEMENT);

         writeDefaultAttributes(tagWriter);
         tagWriter.writeAttribute(ONCHANGE_ATTRIBUTE, "return dateTimeChangeEvent('" + objectId + "')");

         if (isReadonly() || isDisabled()) {
            writeOptionalAttribute(tagWriter, DISABLED_ATTRIBUTE, DISABLED_ATTRIBUTE);
         }

         for (int i = 0; i <= 59; i++) {
            String value = String.format("%02d", i);
            tagWriter.startTag(OPTION_ELEMENT);
            tagWriter.writeAttribute(VALUE_ATTRIBUTE, value);

            if (minute == i) {
               tagWriter.writeAttribute(SELECTED_ATTRIBUTE, SELECTED_ATTRIBUTE);
            }

            tagWriter.appendValue(value);
            tagWriter.endTag();
         }
         tagWriter.endTag();
         tagWriter.endTag();
      }

      return SKIP_BODY;
   }

   private boolean hasDynamicTypeAttribute() {
      return getDynamicAttributes() != null && getDynamicAttributes().containsKey("type");
   }

   /**
    * Writes the '<code>value</code>' attribute to the supplied {@link TagWriter}. Subclasses may choose to override this implementation to
    * control exactly when the value is written.
    */
   protected void writeValue(TagWriter tagWriter) throws JspException {
      String value = getDisplayString(getBoundValue(), getPropertyEditor());
      String type = hasDynamicTypeAttribute() ? (String) getDynamicAttributes().get("type") : getType();
      tagWriter.writeAttribute("value", processFieldValue(getName(), value, type));
   }

   /**
    * Flags {@code type="checkbox"} and {@code type="radio"} as illegal dynamic attributes.
    */
   @Override
   protected boolean isValidDynamicAttribute(String localName, Object value) {
      if ("type".equals(localName)) {
         if ("checkbox".equals(value) || "radio".equals(value)) {
            return false;
         }
      }
      return true;
   }

   /**
    * Get the value of the '<code>type</code>' attribute. Subclasses can override this to change the type of '<code>input</code>' element
    * rendered. Default value is '<code>text</code>'.
    */
   protected String getType() {
      return "text";
   }

   public DateTimeElement getElement() {
      return dateTimeElement;
   }

   /**
    * @jsp.attribute type = "ee.epm.common.web.component.single.DateTimeElement" required = "true" rtexprvalue="true"
    */
   public void setElement(DateTimeElement dateTimeElement) {
      this.dateTimeElement = dateTimeElement;
   }

   /**
    * Sets the value of the '<code>readonly</code>' attribute. May be a runtime expression.
    * 
    * @see #isReadonly()
    */
   public void setReadonly(String readonly) {
      this.readonly = readonly;
   }

   /**
    * Gets the value of the '<code>readonly</code>' attribute. May be a runtime expression.
    * 
    * @see #isReadonly()
    */
   protected String getReadonly() {
      return this.readonly;
   }

   /**
    * Is the current HTML tag readonly?
    * <p>
    * Note: some {@link AbstractHtmlInputElementTag} subclasses (such a those for checkboxes and radiobuttons) may contain readonly
    * attributes, but are not affected by them since their values don't change (only their status does.)
    */
   protected boolean isReadonly() throws JspException {
      // TODO Test this
      Object evaluated = evaluate(READONLY_ATTRIBUTE, getReadonly());
      return (Boolean.TRUE.equals(evaluated) || (evaluated instanceof String && Boolean.valueOf((String) evaluated)));
   }

   @Override
   protected String getName() throws JspException {
      if (hiddenField) {
         return super.getName();
      }

      return getElementId();
   }

   @Override
   public String getId() {
      if (hiddenField) {
         return super.getId();
      }

      return getElementId();
   }

   private String getElementId() {
      if (activeElementType == ELEMENT_TYPE.DATE) {
         return objectId + "-date";
      }

      if (activeElementType == ELEMENT_TYPE.HOUR) {
         return objectId + "-hour";
      }

      if (activeElementType == ELEMENT_TYPE.MINUTE) {
         return objectId + "-minute";
      }

      return null;
   }

   @Override
   protected String resolveCssClass() throws JspException {
      if (activeElementType == ELEMENT_TYPE.HOUR) {
         return "date-hour";
      }
      if (activeElementType == ELEMENT_TYPE.MINUTE) {
         return "date-minute";
      }
      return super.resolveCssClass();
   }
}
