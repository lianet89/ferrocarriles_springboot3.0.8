package com.example.springBoot_hibernate_ferrocarriles.cache;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Component;

@Component
public class SpringCacheCustomizer implements CacheManagerCustomizer<ConcurrentMapCacheManager> {
	
	@Override
	public void customize (ConcurrentMapCacheManager cacheManager) {
		cacheManager.setCacheNames(Arrays.asList("ferrocarriles"));
	}	

}
