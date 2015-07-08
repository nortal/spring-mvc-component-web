package com.nortal.spring.cw.core.cache.model;


/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
public interface IndividualCacheItemFinder {

	/**
	 * Finds a specific object for caching.
	 */
	Cacheable getForCaching(String key);
}
