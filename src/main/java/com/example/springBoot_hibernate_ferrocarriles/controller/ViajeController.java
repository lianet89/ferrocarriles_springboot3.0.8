package com.example.springBoot_hibernate_ferrocarriles.controller;

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

import com.example.springBoot_hibernate_ferrocarriles.service.ViajeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.example.springBoot_hibernate_ferrocarriles.service.EquipoDeTraccionService;
import com.example.springBoot_hibernate_ferrocarriles.dto.ItineraryDto;
import com.example.springBoot_hibernate_ferrocarriles.dto.LocomotiveDto;
import com.example.springBoot_hibernate_ferrocarriles.dto.MotorCarDto;
import com.example.springBoot_hibernate_ferrocarriles.dto.TractionEquipmentDto;
import com.example.springBoot_hibernate_ferrocarriles.dto.TravelDto;
import com.example.springBoot_hibernate_ferrocarriles.model.CocheMotor;
import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;
import com.example.springBoot_hibernate_ferrocarriles.model.Locomotora;
import com.example.springBoot_hibernate_ferrocarriles.model.Viaje;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Travels", description = "Travels of Ferrocarriles API.")
public class ViajeController {
	@Autowired
	ViajeService viajeService;
	
	@Autowired
	EquipoDeTraccionService equipoDeTraccionService;
	
	@Autowired
	ModelMapper modelMapper;

	
	@Operation(summary = "Get all travels.", description = "Get all travels.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved.")
        })
	@GetMapping("/travels")
	public ResponseEntity<List<TravelDto>> getAllTravels() throws Exception {
		List<TravelDto> listResponse = viajeService.getAllViajes().stream().map(travel -> modelMapper.map(travel, TravelDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listResponse);
	}
	
	
	@Operation(summary = "Get a travel by id.", description = "Get a travel as per the id.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."), 
            @ApiResponse(responseCode = "404", description = "Not found - The travel was not found.")
        })
	@GetMapping("/travels/{id}")
	public ResponseEntity<TravelDto> getTravelById(@PathVariable("id") @Parameter(name = "id", description = "Travel id", example = "1") Long id) throws Exception {
		Viaje travel = viajeService.getViajeById(id);
    	TravelDto travelResponse = modelMapper.map(travel, TravelDto.class);
    	if(travel.getNumeroViaje() != id) {
    		return new ResponseEntity<TravelDto>(travelResponse, HttpStatus.NOT_FOUND);
    	} else {     		
    		return ResponseEntity.ok().body(travelResponse);
    	}
	}
	
	
	@Operation(summary = "Add a travel by id.", description = "Add a travel as per the id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.") 
        })
	@PostMapping("/travels")
	public ResponseEntity<TravelDto> addTravel(@Valid @RequestBody @Parameter(name = "itinerary", description = "Itinerary for the travel that will add.") ItineraryDto itineraryDto) throws Exception {	
		Itinerario itineraryRequest = modelMapper.map(itineraryDto, Itinerario.class);
		Viaje travel = viajeService.addViaje(itineraryRequest);
		TravelDto travelResponse = modelMapper.map(travel, TravelDto.class);
    	return new ResponseEntity<TravelDto>(travelResponse, HttpStatus.CREATED);
	}	
	
	
	 @Operation(summary = "Update a travel by id.", description = "Update a travel as per the id, the itinerary and the traction equipment.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully updated."), 
	            @ApiResponse(responseCode = "400", description = "Bad Request.")
	        })
	@PutMapping("/travels/{id}")
	public ResponseEntity<TravelDto> updateTravel(@Valid @PathVariable("id") @Parameter(name = "id", description = "Travel's number", example = "1") int numeroViaje, @RequestBody @Parameter(name = "itinerary", description = "Travel's itinerary") ItineraryDto itineraryDto, @Parameter(name = "traction equipment", description = "Travel's traction equipment") TractionEquipmentDto tractionEquipmentDto ) throws Exception {
    	Viaje travel = viajeService.getViajeById((long)numeroViaje);
    	if(travel.getNumeroViaje() != numeroViaje) {
    		return new ResponseEntity<TravelDto>(modelMapper.map(travel, TravelDto.class), HttpStatus.BAD_REQUEST);
    	} else {    	
	    	Itinerario itineraryRequest = modelMapper.map(itineraryDto, Itinerario.class);
	    	EquipoDeTraccion tractionEquipmentRequest = modelMapper.map(tractionEquipmentDto, EquipoDeTraccion.class);
	    	Viaje travelUpdated = viajeService.updateViaje((long)numeroViaje, itineraryRequest, tractionEquipmentRequest);
	    	TravelDto travelResponse = modelMapper.map(travelUpdated, TravelDto.class);
	    	return ResponseEntity.ok().body(travelResponse);
    	}
	}
	
	 
	 @Operation(summary = "Delete a travel by id", description = "Delete a travel as per the id.")
	    @ApiResponses(value = {
	            @ApiResponse(responseCode = "200", description = "Successfully deleted."), 
	            @ApiResponse(responseCode = "404", description = "Not found - The itinerary was not found.")
	        })
	@DeleteMapping("/travels/{id}")
	public ResponseEntity<String> deleteTravel(@PathVariable("id") @Parameter(name = "id", description = "Travel id", example = "1") Long id) throws Exception {
		Viaje travel = viajeService.getViajeById(id);
    	if(travel.getNumeroViaje() != id) {
    		return new ResponseEntity<String>("Travel not found.", HttpStatus.NOT_FOUND);
    	} else {
    		viajeService.deleteViaje(id);
	    	return new ResponseEntity<String>("Travel deleted successfully", HttpStatus.OK);
    	}
	}

}
