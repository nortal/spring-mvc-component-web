package com.nortal.spring.cw.core.cache;

import java.util.Set;

import javax.annotation.PostConstruct;

import com.nortal.spring.cw.core.cache.model.CompleteCacheItemFinder;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
public class CompleteCacheLoaderImpl extends CacheLoaderImpl implements CompleteCacheLoader {

   private CompleteCacheItemFinder completeCacheItemFinder;

   private FullCacheLoaderImpl allCacheLoader;
   private IndividualCacheLoaderImpl selectedCacheLoader;

   @PostConstruct
   public void init() {
      allCacheLoader = new FullCacheLoaderImpl();
      selectedCacheLoader = new IndividualCacheLoaderImpl();

      configure(allCacheLoader);
      configure(selectedCacheLoader);

      allCacheLoader.setFullCacheItemFinder(completeCacheItemFinder);
      selectedCacheLoader.setIndividualCacheItemFinder(completeCacheItemFinder);

      reloadAll();
   }

   private void configure(CacheLoaderImpl cacheLoader) {
      cacheLoader.setCache(cache);
      cacheLoader.setCacheType(cacheType);
   }

   public void reloadAll() {
      allCacheLoader.reloadAll();
   }

   public void reloadElement(String key) {
      selectedCacheLoader.reloadElement(key);
   }

   public void reload(Set<String> keys) {
      selectedCacheLoader.reload(keys);
   }

   public void setCompleteCacheItemFinder(CompleteCacheItemFinder completeCacheItemFinder) {
      this.completeCacheItemFinder = completeCacheItemFinder;
   }

}
