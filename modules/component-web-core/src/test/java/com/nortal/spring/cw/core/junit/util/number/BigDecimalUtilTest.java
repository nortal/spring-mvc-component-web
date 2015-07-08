package com.nortal.spring.cw.core.junit.util.number;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.nortal.spring.cw.core.util.number.BigDecimalUtil;

/**
 * @author Sander Soo
 * @since 12.02.2014
 */
public class BigDecimalUtilTest {
   private static final int randomSeed = 200;
   private static final BigDecimal minBigDecimal = BigDecimal.ZERO;
   private static final BigDecimal maxBigDecimal = new BigDecimal("5000000");
   private static BigDecimal[] numberArray;
   private Random rTest;

   @BeforeClass
   public static void setUpBeforeClass() {
      numberArray = new BigDecimal[10];
   }

   @Before
   public void setUp() throws Exception {
      // seeding random should guarantee same array of numbers every time with the same seed
      Random r = new Random(randomSeed / 2);
      for (int i = 0; i < numberArray.length - 1; i++) {
         numberArray[i] = new BigDecimal(r.nextInt(maxBigDecimal.intValue() - 10));
      }
      rTest = new Random(randomSeed);
   }

   @Test
   public void testMaxWithNoValues() {
      BigDecimal var = BigDecimalUtil.max();
      assertNull(var);
   }

   @Test
   public void testMaxWithOneValue() {
      BigDecimal var = BigDecimalUtil.max(maxBigDecimal);
      assertEquals(maxBigDecimal, var);
   }

   @Test
   public void testMaxWithMultipleValuesNoScale() {
      numberArray[numberArray.length - 1] = maxBigDecimal;
      BigDecimal varMax = BigDecimalUtil.max(numberArray);
      assertEquals(maxBigDecimal, varMax);
   }

   @Test
   public void testMaxWithMultipleValuesAndScale() {
      for (int i = 0; i < numberArray.length - 1; i++) {
         numberArray[i] = numberArray[i].setScale(rTest.nextInt(20), BigDecimal.ROUND_UP);
      }
      numberArray[numberArray.length - 1] = maxBigDecimal;
      BigDecimal varMax = BigDecimalUtil.max(numberArray);
      assertEquals(maxBigDecimal, varMax);
   }

   @Test(expected = IllegalArgumentException.class)
   public void testBetweenWithValueNull() {
      BigDecimalUtil.between(null, null, null);
   }

   @Test
   public void testBetweenWithStartAndEndNull() {
      boolean isBetween = BigDecimalUtil.between(maxBigDecimal, null, null);
      assertTrue(isBetween);
   }

   @Test
   public void testBetweenWithOnlyStartNull() {
      BigDecimal newMax = maxBigDecimal.divide(new BigDecimal(2));
      boolean isBetween = BigDecimalUtil.between(newMax, null, maxBigDecimal);
      assertTrue(isBetween);
   }

   @Test
   public void testBetweenWithOnlyEndNull() {
      BigDecimal newMax = maxBigDecimal.divide(new BigDecimal(2));
      boolean isBetween = BigDecimalUtil.between(newMax, minBigDecimal, null);
      assertTrue(isBetween);
   }

   @Test
   public void testBetweenWithStartAndEndDefinedIsBetween() {
      BigDecimal newMax = maxBigDecimal.divide(new BigDecimal(2));
      boolean isBetween = BigDecimalUtil.between(newMax, minBigDecimal, maxBigDecimal);
      assertTrue(isBetween);
   }

   @Test
   public void testBetweenWithStartAndEndDefinedIsNotBetween() {
      BigDecimal newMax = maxBigDecimal.multiply(new BigDecimal(2));
      boolean isBetween = BigDecimalUtil.between(newMax, minBigDecimal, maxBigDecimal);
      assertFalse(isBetween);
   }

   @Test
   public void testRemoveTrailingZeroesWithNull() {
      BigDecimal number = BigDecimalUtil.removeTrailingZeroes(null, false);
      assertNull(number);
   }

   @Test
   public void testRemoveTrailingZeroesWithIsNotCurrency() {
      BigDecimal numberWithoutZeroes = new BigDecimal(rTest.nextInt() + ".012");
      BigDecimal numberWithZeroes = new BigDecimal(numberWithoutZeroes.toString() + "00000");
      numberWithZeroes = BigDecimalUtil.removeTrailingZeroes(numberWithZeroes, false);
      assertEquals(numberWithoutZeroes, numberWithZeroes);
   }

   @Test
   public void testRemoveTrailingZeroesWithIsCurrency() {
      BigDecimal numberWithoutZeroes = new BigDecimal(rTest.nextInt() + ".10");
      BigDecimal numberWithZeroes = new BigDecimal(numberWithoutZeroes.toString() + "00000");
      numberWithZeroes = BigDecimalUtil.removeTrailingZeroes(numberWithZeroes, true);
      assertEquals(numberWithoutZeroes, numberWithZeroes);
   }

   @Test
   public void testConvertBigDecimalToCurrencyStringWithNull() {
      String currencyString = BigDecimalUtil.convertBigDecimalToCurrencyString(null);
      assertEquals("", currencyString);
   }

   @Test
   public void testConvertBigDecimalToCurrencyStringWithNotNull() {
      BigDecimal number = new BigDecimal(rTest.nextInt() + ".10");
      String currencyString = BigDecimalUtil.convertBigDecimalToCurrencyString(number);
      assertEquals(number + " €", currencyString);
   }
}
