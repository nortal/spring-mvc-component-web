package com.nortal.spring.cw.core.junit.util.testhelper;

import java.util.ArrayList;
import java.util.List;

import com.nortal.spring.cw.core.xml.XmlModel;

import lombok.Data;

/**
 * extending for various tests <br>
 * Comparable - for sorting<br>
 * XmlModel - ClassScannerTest for test with assignable filter<br>
 * 
 * @author Sander Soo
 * @since 26.02.2014
 */
@Data
@TestAnnotation(version = 2)
public class TestBean implements Comparable<TestBean>, XmlModel {
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String name;
   private String message;

   private List<String> types;

   public TestBean() {
      super();
      this.id = 0;
      this.name = Integer.toString(id);
      initTypes();
   }

   public TestBean(Integer id) {
      super();
      this.id = id;
      this.name = Integer.toString(id);
      initTypes();
   }

   public TestBean(Integer id, String name) {
      super();
      this.id = id;
      this.name = name;
      initTypes();
   }

   @Override
   public int compareTo(TestBean tb) {
      return this.getId().compareTo(tb.getId());
   }

   private void initTypes() {
      types = new ArrayList<String>();
      types.add(getName());
   }
}
