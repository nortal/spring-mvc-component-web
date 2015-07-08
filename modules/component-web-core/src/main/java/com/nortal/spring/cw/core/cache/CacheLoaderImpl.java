package com.nortal.spring.cw.core.cache;

import net.sf.ehcache.Ehcache;

/**
 * Imported and refactored from EMPIS project
 * 
 * @author Lauri Lättemäe <lauri.lattemae@nortal.com>
 * @since 15.02.2013
 * 
 */
public class CacheLoaderImpl implements CacheLoader {
	protected Ehcache cache;
	protected String cacheType;

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	public void setCacheType(String cacheType) {
		this.cacheType = cacheType;
	}

	public String getCacheType() {
		return cacheType;
	}
}
