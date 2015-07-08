package com.nortal.spring.cw.core.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

/**
 * Code snippet taken (and slightly modified) from: http://tidalwave.it/fabrizio/blog/scanning-annotated-classes-spring/
 * 
 * By default scanning all packages starting with "ee". Can be overridden with system property
 * "ee.epm.common.util.ClassScanner.basePackages" or by using alternate constructor
 * 
 * @author Alrik Peets
 * @since 25.11.2013
 */
public class ClassScanner {
   // Can add more packages separated by colon (:) using system property ee.epm.common.util.ClassScanner.basePackages

   private String basePackages = System.getProperty(ClassScanner.class.getCanonicalName() + ".basePackages", "ee");

   private final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);

   public ClassScanner() {
   }

   public ClassScanner(String basePackages) {
      this.basePackages = basePackages;
   }

   public final Collection<Class<?>> findClasses() {
      final List<Class<?>> classes = new ArrayList<>();

      for (final String basePackage : basePackages.split(":")) {
         for (final BeanDefinition candidate : scanner.findCandidateComponents(basePackage)) {
            classes.add(ClassUtils.resolveClassName(candidate.getBeanClassName(), ClassUtils.getDefaultClassLoader()));
         }
      }

      return classes;
   }

   public ClassScanner withIncludeFilter(final TypeFilter filter) {
      scanner.addIncludeFilter(filter);
      return this;
   }

   public ClassScanner withAnnotationFilter(final Class<? extends Annotation> annotationClass) {
      return withIncludeFilter(new AnnotationTypeFilter(annotationClass));
   }

   public ClassScanner withAssignableFilter(final Class<?> assignableType) {
      return withIncludeFilter(new AssignableTypeFilter(assignableType));
   }
}
