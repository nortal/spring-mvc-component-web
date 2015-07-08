package com.nortal.spring.cw.core.junit.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.nortal.spring.cw.core.model.DateRangeModel;
import com.nortal.spring.cw.core.util.DateRangeModelUtil;

/**
 * @author Sander Soo
 * @since 20.02.2014
 */
@RunWith(MockitoJUnitRunner.class)
public class DateRangeModelUtilTest {

   private final class AnswerImplementation implements Answer<Object> {
      public Object answer(InvocationOnMock invocation) {
         Object[] args = invocation.getArguments();
         Date date = (Date) args[0];
         endDate.setTime(date.getTime());
         return null;
      }
   }

   @Mock
   private DateRangeModel drm;
   private Date startDate;
   private Date endDate;

   @Before
   public void setUp() throws Exception {
      startDate = new Date(System.currentTimeMillis() - 1000000);
      endDate = new Date(System.currentTimeMillis() + 1000000);
      Mockito.doReturn(endDate).when(drm).getEndDate();
      Mockito.doReturn(startDate).when(drm).getStartDate();
      Mockito.doAnswer(new AnswerImplementation()).when(drm).setEndDate(Mockito.any(Date.class));
   }

   @Test
   public void testIsActiveYesIs() {
      boolean isActive = DateRangeModelUtil.isActive(drm);
      assertTrue(isActive);
   }

   @Test
   public void testIsActiveNoIsNot() {
      drm.setEndDate(drm.getStartDate());
      boolean isActive = DateRangeModelUtil.isActive(drm);
      assertFalse(isActive);
   }

   @Test
   public void testClose() {
      boolean isActive = DateRangeModelUtil.isActive(drm);
      assertTrue(isActive);
      DateRangeModelUtil.close(drm);
      isActive = DateRangeModelUtil.isActive(drm);
      assertFalse(isActive);
   }
}
