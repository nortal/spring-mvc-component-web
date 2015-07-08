package com.nortal.spring.cw.core.cache.model;

import java.util.Collection;


/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
public interface FullCacheItemFinder {

	/**
	 * Finds all items to be cached from appropriate data source.
	 * 
	 * @return list of items to be cached.
	 */
	Collection<? extends Cacheable> findAllForCaching();
}
