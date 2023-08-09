package com.example.springBoot_hibernate_ferrocarriles.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.springBoot_hibernate_ferrocarriles.dto.MotorCarDto;
import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;
import com.example.springBoot_hibernate_ferrocarriles.service.CocheMotorService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Motor cars", description = "Motor cars of Ferrocarriles API.")
public class CocheMotorController {
	@Autowired
	CocheMotorService cocheMotorService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	 @Operation(summary = "Get all motor cars.", description = "Get all motor cars.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved.")
	        })
	@GetMapping("/motor-cars")
    private ResponseEntity<List<MotorCarDto>> getAllCocheSMotor() throws Exception {
		List<MotorCarDto> listResponse = cocheMotorService.getAllCocheMotor().stream().map(motorCar -> modelMapper.map(motorCar, MotorCarDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listResponse);
    }
	 
	     
	 @Operation(summary = "Get a motor car by id.", description = "Getting a motor car as per the id.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."), 
	            @ApiResponse(responseCode = "404", description = "Not found - The motor car was not found.")
	        })
    @GetMapping("/motor-cars/{id}")
    private ResponseEntity<MotorCarDto> getCocheMotorById(@PathVariable("id") @Parameter(name = "id", description = "Motor car id", example = "1") Long id) throws Exception {
    	CocheMotor motorCar = cocheMotorService.getCocheMotorById(id);
    	MotorCarDto motorCarResponse = modelMapper.map(motorCar, MotorCarDto.class);
    	if(motorCar.getNumeroIdentificacion() != id) {
    		return new ResponseEntity<MotorCarDto>(motorCarResponse, HttpStatus.NOT_FOUND);
    	} else {     		
    		return ResponseEntity.ok().body(motorCarResponse);
    	}
    }   
	 
	 
	 @Operation(summary = "Add a motor car by id.", description = "Add a motor car as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "Successfully created.") 
	        })         
    @PostMapping("/motor-cars")
    private ResponseEntity<MotorCarDto> addCocheMotor(@Valid @RequestBody @Parameter(name = "itinerary", description = "Motor car to add.") MotorCarDto motorCarDto) throws Exception {
    	CocheMotor motorCarRequest = modelMapper.map(motorCarDto, CocheMotor.class);
    	CocheMotor motorCar = cocheMotorService.addCocheMotor(motorCarRequest);
    	MotorCarDto motorCarResponse = modelMapper.map(motorCar, MotorCarDto.class);
    	return new ResponseEntity<MotorCarDto>(motorCarResponse, HttpStatus.CREATED);
    }
    
	 @Operation(summary = "Update a motor car by id.", description = "Update a motor car as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully updated."), 
	            @ApiResponse(responseCode = "400", description = "Bad Request.")
	        })
    @PutMapping("/motor-cars/{id}")
    private ResponseEntity<MotorCarDto> updateCocheMotor(@Valid @PathVariable("id") @Parameter(name = "id", description = "Motor car id", example = "1") Long id, @RequestBody @Parameter(name = "Motor car", description = "Motor car properties") MotorCarDto motorCarDto) throws Exception {
    	CocheMotor motorCar = cocheMotorService.getCocheMotorById(id);
    	if(motorCar.getNumeroIdentificacion() != id) {
    		return new ResponseEntity<MotorCarDto>(modelMapper.map(motorCar, MotorCarDto.class), HttpStatus.BAD_REQUEST);
    	} else {    	
	    	CocheMotor motorCarRequest = modelMapper.map(motorCarDto, CocheMotor.class);
	    	CocheMotor motorCarUpdated = cocheMotorService.updateCocheMotor(id, motorCarRequest);
	    	MotorCarDto motorCarResponse = modelMapper.map(motorCarUpdated, MotorCarDto.class);
	    	return ResponseEntity.ok().body(motorCarResponse);
    	}
    }
	
	 @Operation(summary = "Delete a motor car by id", description = "Delete a motor car as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully deleted."), 
	            @ApiResponse(responseCode = "404", description = "Not found - The motor car was not found.")
	        })
    @DeleteMapping("/motor-cars/{id}")
    private ResponseEntity<String> deleteCocheMotor(@PathVariable("id") @Parameter(name = "id", description = "Motor car id", example = "1") Long id) throws Exception {
    	CocheMotor coche = cocheMotorService.getCocheMotorById(id);
    	if(coche.getNumeroIdentificacion() != id) {
    		return new ResponseEntity<String>("Motor car not found.", HttpStatus.NOT_FOUND);
    	} else {
	    	cocheMotorService.deleteCocheMotor(id);
	    	return new ResponseEntity<String>("Motor car deleted successfully", HttpStatus.OK);
    	}
    }
	

}
