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

import com.example.springBoot_hibernate_ferrocarriles.dto.LocomotiveDto;
import com.example.springBoot_hibernate_ferrocarriles.dto.MotorCarDto;
import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;
import com.example.springBoot_hibernate_ferrocarriles.model.Locomotora;
import com.example.springBoot_hibernate_ferrocarriles.service.LocomotoraService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Locomotives", description = "Locomotives of Ferrocarriles API.")
public class LocomotoraController {
	@Autowired
	LocomotoraService locomotoraService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	 @Operation(summary = "Get all locomotives.", description = "Get all locomotives.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved.")
	        })
	@GetMapping("/locomotives")
	public ResponseEntity<List<LocomotiveDto>> getAllLocomotoras() throws Exception {
		List<LocomotiveDto> listResponse = locomotoraService.getAllLocomotora().stream().map(locomotive -> modelMapper.map(locomotive, LocomotiveDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listResponse);
	}
	
	 
	 @Operation(summary = "Get a locomotive by id.", description = "Get a locomotive as per the id.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."), 
	            @ApiResponse(responseCode = "404", description = "Not found - The locomotive was not found.")
	        })
	@GetMapping("/locomotives/{id}")
	public ResponseEntity<LocomotiveDto> getLocomotoraById(@PathVariable("id") @Parameter(name = "id", description = "Locomotive id", example = "1") Long id) throws Exception {
		Locomotora locomotive = locomotoraService.getLocomotoraById(id);
		LocomotiveDto locomotiveResponse = modelMapper.map(locomotive, LocomotiveDto.class); 
    	if(locomotive.getNumeroIdentificacion() != id) {
    		return new ResponseEntity<LocomotiveDto>(locomotiveResponse, HttpStatus.NOT_FOUND);
    	} else {     		
    		return ResponseEntity.ok().body(locomotiveResponse);
    	}
		
	}
	 
	
	 @Operation(summary = "Add a locomotive by id.", description = "Add a locomotive as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "201", description = "Successfully created.") 
	        })
	@PostMapping("/locomotives")
	public ResponseEntity<LocomotiveDto> addLocomotora(@Valid @RequestBody @Parameter(name = "locomotive", description = "Locomotive to add.") LocomotiveDto locomotiveDto) throws Exception {
		Locomotora locomotiveRequest = modelMapper.map(locomotiveDto, Locomotora.class);
		Locomotora locomotive = locomotoraService.addLocomotora(locomotiveRequest);
		LocomotiveDto locomotiveResponse = modelMapper.map(locomotive, LocomotiveDto.class);
    	return new ResponseEntity<LocomotiveDto>(locomotiveResponse, HttpStatus.CREATED);
	}
	 
	 
	 @Operation(summary = "Update a locomotive by id.", description = "Update a locomotive as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully updated."), 
	            @ApiResponse(responseCode = "400", description = "Bad Request.")
	        })
	@PutMapping("/locomotives/{id}")
	public ResponseEntity<LocomotiveDto> updateLocomotora( @Valid @PathVariable("id") @Parameter(name = "id", description = "Locomotive id", example = "1") Long id, @RequestBody @Parameter(name = "locomotive", description = "Locomotive properties") LocomotiveDto locomotiveDto) throws Exception {
		Locomotora locomotive = locomotoraService.getLocomotoraById(id);
    	if(locomotive.getNumeroIdentificacion() != id) {
    		return new ResponseEntity<LocomotiveDto>(modelMapper.map(locomotive, LocomotiveDto.class), HttpStatus.BAD_REQUEST);
    	} else {    	
    		Locomotora locomotiveRequest = modelMapper.map(locomotiveDto, Locomotora.class);
    		Locomotora locomotiveUpdated = locomotoraService.updateLocomotora(id, locomotiveRequest);
	    	LocomotiveDto locomotiveResponse = modelMapper.map(locomotiveUpdated, LocomotiveDto.class);
	    	return ResponseEntity.ok().body(locomotiveResponse);
    	}
	}
	
	 
	 @Operation(summary = "Delete a locomotive by id", description = "Delete a locomotive as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully deleted."), 
	            @ApiResponse(responseCode = "404", description = "Not found - The locomotive was not found.")
	        })
	@DeleteMapping("/locomotives/{id}")
	public ResponseEntity<String> deleteLocomotora(@PathVariable("id") @Parameter(name = "id", description = "Locomotive id", example = "1") Long id) throws Exception {
		Locomotora locomotive = locomotoraService.getLocomotoraById(id);
    	if(locomotive.getNumeroIdentificacion() != id) {
    		return new ResponseEntity<String>("Locomotive not found.", HttpStatus.NOT_FOUND);
    	} else {
    		locomotoraService.deleteLocomotora(id);
	    	return new ResponseEntity<String>("Locomotive deleted successfully", HttpStatus.OK);
    	}
	}

}
