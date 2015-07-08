package com.nortal.spring.cw.core.cache;

import java.util.Set;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
public interface IndividualCacheLoader extends CacheLoader {

	void reloadElement(String key);

	void reload(Set<String> keys);
}
