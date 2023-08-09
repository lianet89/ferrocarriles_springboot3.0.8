package com.example.springBoot_hibernate_ferrocarriles;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


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
