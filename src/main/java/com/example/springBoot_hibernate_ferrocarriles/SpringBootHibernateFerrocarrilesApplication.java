package com.example.springBoot_hibernate_ferrocarriles;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;

import com.example.springBoot_hibernate_ferrocarriles.cache.SpringCachingConfig;
import com.example.springBoot_hibernate_ferrocarriles.service.CocheMotorService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@OpenAPIDefinition(info = @Info(title = "FERROCARRILES OpenAPI", version = "1.0"))
@SpringBootApplication
public class SpringBootHibernateFerrocarrilesApplication {
	
	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}	

	public static void main(String[] args) {
		SpringApplication.run(SpringBootHibernateFerrocarrilesApplication.class, args);			
	}
	
}
