package com.nortal.spring.cw.core.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * WebApplicationContextUtils vajab beanide saamiseks Servlet konteksti.
 * Alloleva klassi abil on võimalik sellest mööda minna (vajalik näiteks testide
 * puhul)
 * 
 * @author Alrik Peets
 */
public class SpringApplicationContext implements ApplicationContextAware {

   private static ApplicationContext appContext;

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      appContext = applicationContext;
   }

   public static ApplicationContext getApplicationContext() {
      return appContext;
   }

   public static Object getBean(String beanName) {
      return appContext.getBean(beanName);
   }

   public static <T> T getBean(Class<T> requiredType) {
      return appContext.getBean(requiredType);
   }

}
