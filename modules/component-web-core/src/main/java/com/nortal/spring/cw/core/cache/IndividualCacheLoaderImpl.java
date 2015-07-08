package com.nortal.spring.cw.core.cache;

import java.util.Set;

import com.nortal.spring.cw.core.cache.model.Cacheable;
import com.nortal.spring.cw.core.cache.model.IndividualCacheItemFinder;

import lombok.extern.slf4j.Slf4j;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 */
@Slf4j
public class IndividualCacheLoaderImpl extends CacheLoaderImpl implements IndividualCacheLoader {

   private IndividualCacheItemFinder individualCacheItemFinder;

   public void reloadElement(String key) {
      Cacheable cachedObject = individualCacheItemFinder.getForCaching(key);
      if (cachedObject == null) {
         cache.remove(key);
      } else {
         EhCacheUtil.put(cachedObject, cache);
      }
   }

   public void reload(Set<String> keys) {
      log.debug("Reloading " + keys.size() + " element" + (keys.size() == 1 ? "" : "s") + " for cache: " + cacheType);
      for (String key : keys) {
         reloadElement(key);
      }
   }

   public void setIndividualCacheItemFinder(IndividualCacheItemFinder individualCacheItemFinder) {
      this.individualCacheItemFinder = individualCacheItemFinder;
   }
}
