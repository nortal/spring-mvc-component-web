package com.nortal.spring.cw.core.junit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import com.nortal.spring.cw.core.junit.util.testhelper.TestAnnotation;
import com.nortal.spring.cw.core.junit.util.testhelper.TestBean;
import com.nortal.spring.cw.core.util.ClassScanner;
import com.nortal.spring.cw.core.xml.XmlModel;

/**
 * @author Sander Soo
 * @since 20.02.2014
 */
public class ClassScannerTest {
   private ClassScanner cs;

   @Before
   public void setUp() throws Exception {
      cs = new ClassScanner(getClass().getPackage().getName());
   }

   @Test
   public void testFindClassesWithIncludeFilter() {
      Collection<Class<?>> foundClasses = cs.withIncludeFilter(new AnnotationTypeFilter(TestAnnotation.class)).findClasses();
      assertTrue(foundClasses.contains(TestBean.class));
      assertEquals(1, foundClasses.size());
   }

   @Test
   public void testFindClassesWithAnnotationFilter() {
      Collection<Class<?>> foundClasses = cs.withAnnotationFilter(TestAnnotation.class).findClasses();
      assertTrue(foundClasses.contains(TestBean.class));
      assertEquals(1, foundClasses.size());
   }

   @Test
   public void testFindClassesWithAssignableFilter() {
      Collection<Class<?>> foundClasses = cs.withAssignableFilter(XmlModel.class).findClasses();
      assertTrue(foundClasses.contains(TestBean.class));
      assertEquals(1, foundClasses.size());
   }

}
