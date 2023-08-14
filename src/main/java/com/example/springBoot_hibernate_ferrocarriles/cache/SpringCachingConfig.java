package com.example.springBoot_hibernate_ferrocarriles.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@EnableCaching
@Configuration
public class SpringCachingConfig {
			
	@Autowired
	private CacheManager cacheManager;
	
	@Scheduled(cron = "0 * * * *?")
	public void clearCache() {
		for(String name:cacheManager.getCacheNames()) {
			cacheManager.getCache(name).clear();			
		}
	}
	
	@Bean("customKeyGenerator")
	public KeyGenerator keyGenerator() {
		return new CustomKeyGenerator();
	}
			
	
}
