package com.nortal.spring.cw.jsp.config;

import java.util.Arrays;

import net.sf.ehcache.config.CacheConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.cache.support.CompositeCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import com.nortal.spring.cw.core.i18n.CwMessageSource;
import com.nortal.spring.cw.jsp.servlet.view.CwJspInternalResourceViewResolver;

@Configuration
@EnableCaching
@EnableAsync
@ComponentScan(basePackages = { "com.nortal.spring.cw.core.i18n", "com.nortal.spring.cw.core.servlet.mvc",
      "com.nortal.spring.cw.core.support", "ee.epm.common.servlet.handler.resolver" })
@PropertySource(value = { "${CW_CONF_DIR:classpath:}/cw-jsp.properties" })
public class ApplicationConfig implements CachingConfigurer {

   private static final long DEFAULT_CACHE_TIME_TO_LIVE_SECONDS = (12 * 60 * 60);

   @Autowired
   private Environment env;

   @Bean(name = "messageSource")
   public CwMessageSource messageSource() {
      CwMessageSource cachedMessageSource = new CwMessageSource();
      cachedMessageSource.setUseCodeAsDefaultMessage(Boolean.TRUE);
      cachedMessageSource.setAlwaysUseMessageFormat(Boolean.TRUE);
      return cachedMessageSource;
   }

   @Bean(name = "messageSourceAccessor")
   public MessageSourceAccessor messageSourceAccessor() {
      return new MessageSourceAccessor(messageSource());
   }

   @Bean(name = "viewResolver")
   public CwJspInternalResourceViewResolver viewResolver() {
      CwJspInternalResourceViewResolver viewResolver = new CwJspInternalResourceViewResolver();

      viewResolver.setPrefix("/WEB-INF/jsp");
      viewResolver.setSuffix(".jsp");
      viewResolver.setViewPackage("com.nortal.spring.cw.jsp.web.portal");
      viewResolver.setComponentClassSuffix("Component");

      return viewResolver;
   }

   @Override
   @Bean
   public CacheManager cacheManager() {
      CompositeCacheManager ccm = new CompositeCacheManager();
      CacheManager[] mgrs = { baseCacheManager(), new EhCacheCacheManager() };
      ccm.setCacheManagers(Arrays.asList(mgrs));
      ccm.setFallbackToNoOpCache(false);

      return ccm;
   }

   @Override
   public KeyGenerator keyGenerator() {
      return new SimpleKeyGenerator();
   }

   @Bean(name = "baseCacheManager")
   public CacheManager baseCacheManager() {
      net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
      config.setName("baseCacheConfiguration");
      // config.addCache(buildCacheConfiguration(CwMessageSource.MESSAGE_CACHE));

      EhCacheCacheManager cacheManager = new EhCacheCacheManager();
      cacheManager.setCacheManager(net.sf.ehcache.CacheManager.newInstance(config));

      return cacheManager;
   }

   private CacheConfiguration buildCacheConfiguration(String cacheName) {
      return buildCacheConfiguration(cacheName, DEFAULT_CACHE_TIME_TO_LIVE_SECONDS);
   }

   private CacheConfiguration buildCacheConfiguration(String cacheName, Long timeToLiveSeconds) {
      CacheConfiguration cacheConfiguration = new CacheConfiguration();
      cacheConfiguration.setName(cacheName);
      cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
      cacheConfiguration.setMaxEntriesLocalHeap(1000L);
      cacheConfiguration.setTimeToLiveSeconds(timeToLiveSeconds);
      return cacheConfiguration;
   }

   @Override
   public CacheResolver cacheResolver() {
      return new SimpleCacheResolver(baseCacheManager());
   }

   @Override
   public CacheErrorHandler errorHandler() {
      return new SimpleCacheErrorHandler();
   }
}
