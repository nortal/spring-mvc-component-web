package com.nortal.spring.cw.core.cache;

import java.util.Collection;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.collections.CollectionUtils;

import com.nortal.spring.cw.core.cache.model.Cacheable;
import com.nortal.spring.cw.core.cache.model.FullCacheItemFinder;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
@Slf4j
public class FullCacheLoaderImpl extends CacheLoaderImpl implements FullCacheLoader {
   private FullCacheItemFinder fullCacheItemFinder;

   @PostConstruct
   public void reloadAll() {
      log.info("RELOAD: " + super.getCacheType());
      Collection<? extends Cacheable> allForCaching = fullCacheItemFinder.findAllForCaching();
      cache.remove(CollectionUtils.subtract(EhCacheUtil.findAll(cache), allForCaching));
      EhCacheUtil.putAll(allForCaching, cache);
   }

   public void setFullCacheItemFinder(FullCacheItemFinder fullCacheItemFinder) {
      this.fullCacheItemFinder = fullCacheItemFinder;
   }
}
