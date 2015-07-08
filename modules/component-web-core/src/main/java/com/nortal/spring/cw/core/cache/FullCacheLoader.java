package com.nortal.spring.cw.core.cache;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
public interface FullCacheLoader extends CacheLoader {

	void reloadAll();
}
