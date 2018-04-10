package com.cnbmtech.system.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class EHCacheUtil {

	@Autowired
	CacheManager cacheManager;
	
	public List<String> get(String name) {
		Cache cache = cacheManager.getCache(name);
		Object o = cache.getNativeCache();
		System.out.println(o.toString());
		return null;
	}
	
}
