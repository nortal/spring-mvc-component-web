package com.nortal.spring.cw.jsp.web.portal.component.test.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.nortal.spring.cw.core.web.annotation.component.DateTimeField;
import com.nortal.spring.cw.core.web.annotation.component.DoubleField;
import com.nortal.spring.cw.core.web.annotation.component.IntegerField;
import com.nortal.spring.cw.core.web.annotation.component.LongField;
import com.nortal.spring.cw.core.web.annotation.component.StringCollectionField;
import com.nortal.spring.cw.core.web.annotation.component.StringField;

/**
 * @author Margus Hanni
 * 
 */
public class ObjectTest implements Serializable {

   private static final long serialVersionUID = 1L;
   private String textField;
   private String requiredTextField;
   private String longTextField;
   private String bankReferenceNumberField;
   private Date dateTimeField;
   private Date dateField;
   private Date timeField;
   private BigDecimal decimalField;
   private Integer integerField;
   private Long longField;
   private List<String> list;

   @StringField(label = "#Teksti väli")
   public String getTextField() {
      return textField;
   }

   @StringField(required = true, label = "#Kohustuslik tekstiväli")
   public String getRequiredTextField() {
      return requiredTextField;
   }

   @StringField(label = "#Mitmerealine tekstiväli", multiple = true, cols = 90, rows = 5)
   public String getLongTextField() {
      return longTextField;
   }

   @StringField(label = "#Panga viitenumber")
   public String getBankReferenceNumberField() {
      return bankReferenceNumberField;
   }

   @DateTimeField(label = "#Kuupäeva väli koos kellaajaga")
   public Date getDateTimeField() {
      return dateTimeField;
   }

   @DateTimeField(label = "#Kuupäeva väli")
   public Date getDateField() {
      return dateField;
   }

   @DateTimeField(label = "#Kellaaja väli")
   public Date getTimeField() {
      return timeField;
   }

   @DoubleField(label = "#Reaalarv")
   public BigDecimal getDecimalField() {
      return decimalField;
   }

   @IntegerField(label = "#Täisarv - integer", between = { 10, 20 })
   public Integer getIntegerField() {
      return integerField;
   }

   @LongField(label = "#Täisarv - long")
   public Long getLongField() {
      return longField;
   }

   @StringCollectionField(label = "#List")
   public List<String> getList() {
      return list;
   }

   public void setTextField(String textField) {
      this.textField = textField;
   }

   public void setRequiredTextField(String requiredTextField) {
      this.requiredTextField = requiredTextField;
   }

   public void setLongTextField(String longTextField) {
      this.longTextField = longTextField;
   }

   public void setDateTimeField(Date dateTimeField) {
      this.dateTimeField = dateTimeField;
   }

   public void setDateField(Date dateField) {
      this.dateField = dateField;
   }

   public void setTimeField(Date timeField) {
      this.timeField = timeField;
   }

   public void setDecimalField(BigDecimal decimalField) {
      this.decimalField = decimalField;
   }

   public void setIntegerField(Integer integerField) {
      this.integerField = integerField;
   }

   public void setLongField(Long longField) {
      this.longField = longField;
   }

   public void setList(List<String> list) {
      this.list = list;
   }

   public void setBankReferenceNumberField(String bankReferenceNumberField) {
      this.bankReferenceNumberField = bankReferenceNumberField;
   }

}
