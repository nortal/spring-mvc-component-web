package com.nortal.spring.cw.core.junit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.nortal.spring.cw.core.util.ReflectionUtil;

/**
 * @author Sander Soo
 * @since 13.02.2014
 */
// Extends because test getTypeArguments* requires subclass
public class ReflectionUtilTest extends ArrayList<String> {
   private static final long serialVersionUID = 1L;

   @Test
   public void testGetTypeArgumentsHasSubclass() {
      List<Class<?>> typeArguments = ReflectionUtil.getTypeArguments(ArrayList.class, getClass());
      assertEquals(String.class, typeArguments.get(0));
   }

   @Test
   public void testGetTypeArgumentsDoesNotHaveTypedSubclass() {
      List<Class<?>> typeArguments = ReflectionUtil.getTypeArguments(ReflectionUtilTest.class, getClass());
      assertEquals(0, typeArguments.size());
   }

   @Test
   public void testHasInterfaceTrue() {
      boolean hasInterface = ReflectionUtil.hasInterface(ArrayList.class, List.class);
      assertTrue(hasInterface);
   }

   @Test
   public void testHasInterfaceFalse() {
      boolean hasInterface = ReflectionUtil.hasInterface(String.class, Integer.class);
      assertFalse(hasInterface);
   }

   @Test
   public void testGetClass() {
      Class<?> class1 = ReflectionUtil.getClass(String.class);
      assertEquals(String.class, class1);
   }
}
