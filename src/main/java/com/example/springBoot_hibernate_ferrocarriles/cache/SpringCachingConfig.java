package com.example.springBoot_hibernate_ferrocarriles.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.springBoot_hibernate_ferrocarriles.service.CocheMotorService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCaching
@EnableScheduling
public class SpringCachingConfig {

    
    @Bean
    CacheManager cacheManager() {
    	log.info("Creating cache manager.");
		return new ConcurrentMapCacheManager();
	}
	
    
	@Scheduled(cron = "0 0/10 * * * *")
	public void clearCache() {
		//TODO: implementar log
		log.info("Method clear cache.");
		for(String name:cacheManager().getCacheNames()) {
			//TODO: implementar log
			log.info("Cleaning chache " + name);
			cacheManager().getCache(name).clear();			
		}
	}
	/*
	@Bean
	public KeyGenerator keyGenerator() {
		log.info("Generating key.");
		return new CustomKeyGenerator();
	}
	*/		
	
}
