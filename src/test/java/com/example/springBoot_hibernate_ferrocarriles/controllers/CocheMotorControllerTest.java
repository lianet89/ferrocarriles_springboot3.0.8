package com.example.springBoot_hibernate_ferrocarriles.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import com.example.springBoot_hibernate_ferrocarriles.controller.CocheMotorController;
import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;
import com.example.springBoot_hibernate_ferrocarriles.service.CocheMotorService;

class CocheMotorControllerTest {
	private MockMvc mockMvc;
	
	@InjectMocks
	private CocheMotorController lookupController;
	
	@Mock
	CocheMotorService motorCarService;
	
	/*@Mock
	  ModelMapper modelMapper;
	*/
	
	@BeforeEach
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(lookupController).build();
	}
	
	@Test
	void whenSubmitMotorCar_thenMotorCarIsGenerated() throws Exception {
		CocheMotor newMotorCar = new CocheMotor();
		newMotorCar.setNumeroIdentificacion(1);
		
		CocheMotor result = new CocheMotor();
		result.setClimatizado(true);
		result.setKilometrajeRecorrido(100);
		result.setLineaDeTrenes(1);
		result.setNumeroIdentificacion(8);
		result.setPotenciaMotor(2000);
		
		Mockito.when(motorCarService.addCocheMotor(Mockito.any(CocheMotor.class))).thenReturn(result);
		
		this.mockMvc
			.perform(post("/motor-cars").content(asJsonString(newMotorCar)).contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andDo(print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.numeroIdentificacion", is(notNullValue())))
			//.andExpect(MockMvcResultMatchers.jsonPath("$.numeroIdentificacion", is(equalTo(asJsonString(result.getNumeroIdentificacion())))))
			.andReturn();
	}
	
	private static String asJsonString(final Object obj) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
