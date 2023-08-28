package com.example.springBoot_hibernate_ferrocarriles.controller;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.springBoot_hibernate_ferrocarriles.service.EquipoDeTraccionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.springBoot_hibernate_ferrocarriles.dto.TractionEquipmentDto;
import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Traction equipments", description = "Traction equipments of Ferrocarriles API.")
public class EquipoDeTraccionController {
	@Autowired
	EquipoDeTraccionService equipoDeTraccionService;
	
	@Autowired
	ModelMapper modelMapper;
	
	
	@Operation(summary = "Get all traction equipments.", description = "Get all traction equipments.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."),
    		@ApiResponse(responseCode = "404", description = "Not traction equipments found.")
        })
	@GetMapping("/traction-equipments")
	public ResponseEntity<List<TractionEquipmentDto>> getAllEquipoDeTraccion() {
		List<TractionEquipmentDto> listResponse = equipoDeTraccionService.getAllEquipoDeTraccion().stream().map(tractionEquipment -> modelMapper.map(tractionEquipment, TractionEquipmentDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listResponse);
	}
	
	
	@Operation(summary = "Get a traction equipment by id.", description = "Get a traction equipment as per the id.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."), 
            @ApiResponse(responseCode = "404", description = "Not found - The traction equipment was not found.")
        })
	@GetMapping("/traction-equipments/{id}")
	public ResponseEntity<TractionEquipmentDto> getEquipoDeTraccionById(@PathVariable("id") @Parameter(name = "id", description = "Traction equipment id", example = "1") Long id) {
		EquipoDeTraccion tractionEquipment = equipoDeTraccionService.getEquipoDeTraccionById(id); 
    	return ResponseEntity.ok().body(modelMapper.map(tractionEquipment, TractionEquipmentDto.class));		
	}
	
	/*
	@PostMapping("/traction-equipments")
	public ResponseEntity<TractionEquipmentDto> addEquipoDeTraccion(@Valid @RequestBody TractionEquipmentDto tractionEquipmentDto) throws Exception {
		EquipoDeTraccion tractionEquipmentRequest = modelMapper.map(tractionEquipmentDto, EquipoDeTraccion.class);
		EquipoDeTraccion tractionEquipment = equipoDeTraccionService.addEquipoDeTraccion(tractionEquipmentRequest);
		TractionEquipmentDto tractionEquipmentResponse = modelMapper.map(tractionEquipment, TractionEquipmentDto.class);
    	return new ResponseEntity<TractionEquipmentDto>(tractionEquipmentResponse, HttpStatus.CREATED);
    }
	
	@PutMapping("/traction-equipments/{id}")
	public ResponseEntity<TractionEquipmentDto> updateEquipoDeTraccion(@Valid @PathVariable("id") Long id, @RequestBody TractionEquipmentDto tractionEquipmentDto) throws Exception {
		EquipoDeTraccion tractionEquipmentRequest = modelMapper.map(tractionEquipmentDto, EquipoDeTraccion.class);
		EquipoDeTraccion tractionEquipment = equipoDeTraccionService.updateEquipoDeTraccion(id, tractionEquipmentRequest);
		TractionEquipmentDto tractionEquipmentResponse = modelMapper.map(tractionEquipment, TractionEquipmentDto.class);
    	return ResponseEntity.ok().body(tractionEquipmentResponse);
	}
	*/
	
	@Operation(summary = "Delete a traction equipment by id", description = "Delete a traction equipment as per the id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted."), 
            @ApiResponse(responseCode = "404", description = "Not found - The traction equipment was not found.")
        })
	@DeleteMapping("/traction-equipments/{id}")
	public ResponseEntity<String> deleteEquipoDeTraccion(@PathVariable("id") @Parameter(name = "id", description = "Traction equipment id", example = "1") Long id) {
		equipoDeTraccionService.deleteEquipoDeTraccion(id);
	    return new ResponseEntity<String>("Traction equipment deleted successfully", HttpStatus.OK);    	
	}

}
