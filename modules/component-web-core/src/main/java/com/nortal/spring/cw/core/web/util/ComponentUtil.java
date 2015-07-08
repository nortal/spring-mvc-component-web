package com.nortal.spring.cw.core.web.util;

import java.lang.reflect.Field;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.web.context.WebApplicationContext;

/**
 * Utility functions used in various components
 * 
 * @author Alrik Peets
 * @since 22.11.2013
 */
public final class ComponentUtil {

   private ComponentUtil() {
      super();
   }

   public static void injectSpringResources(final Object object) {
      ReflectionUtils.doWithFields(object.getClass(), new FieldCallback() {
         @Override
         public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
            Resource resource = field.getAnnotation(Resource.class);
            if (resource != null) {
               resolve(field, object, resource.name() == null ? null : resource.name());
            }

            Autowired autowired = field.getAnnotation(Autowired.class);
            if (autowired != null) {
               Qualifier qualifier = field.getAnnotation(Qualifier.class);
               resolve(field, object, qualifier == null ? null : qualifier.value());
            }
         }
      });
   }

   public static void injectSpringResources(final Object object, final String beanName) {
      WebApplicationContext webApplicationContext = (WebApplicationContext) BeanUtil.getApplicationContext();
      AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();
      autowireCapableBeanFactory.configureBean(object, beanName);
   }

   private static void resolve(Field field, Object object, String beanName) {
      if (StringUtils.isEmpty(beanName)) {
         setFieldValue(field, object, BeanUtil.getBean(field.getType()));
      } else {
         setFieldValue(field, object, BeanUtil.getBean(beanName));
      }
   }

   private static void setFieldValue(Field field, Object obj, Object value) {
      if (!field.isAccessible()) {
         field.setAccessible(true);
      }
      ReflectionUtils.setField(field, obj, value);
   }
}
