package com.example.springBoot_hibernate_ferrocarriles.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import com.example.springBoot_hibernate_ferrocarriles.dto.LocomotiveDto;
import com.example.springBoot_hibernate_ferrocarriles.dto.TractionEquipmentDto;
import com.example.springBoot_hibernate_ferrocarriles.service.ReportesService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Reports", description = "Reports of Ferrocarriles API.")
public class ReportesController {
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	private ReportesService reportesService;
	
	
	@Operation(summary = "Get a list of suitable traction equipments.", description = "Get a list of suitable traction equipments for cover an itinerary.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."),
    		@ApiResponse(responseCode = "404", description = "Not traction equipments found.")
        })
	@GetMapping("/reports/siutable-traction-equipment/{id}")
	public ResponseEntity <List<TractionEquipmentDto>> siutableTractionEquipment (@PathVariable("id") @Parameter(name = "id", description = "Itinerary id", example = "1") int id) {
		List<TractionEquipmentDto> listResponse = reportesService.equiposAptosParaItinerario(id).stream().map(tractionEquipment -> modelMapper.map(tractionEquipment, TractionEquipmentDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listResponse);
	}
	
	
	 @Operation(summary = "Get a list of locomotives of a brand.", description = "Get a list of locomotives of a specfied brand.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."),
	    		@ApiResponse(responseCode = "404", description = "Not locomotives found.")
	        })
	@GetMapping("/reports/locomotives-of-a-brand/{marca}")
	public ResponseEntity<List<LocomotiveDto>> locomotivesOfABrand(@PathVariable("marca") @Parameter(name = "brand", description = "Locomotive brand", example = "MLW") String marca) {
		List<LocomotiveDto> listResponse = reportesService.locomotorasDeMarca(marca).stream().map(locomotive -> modelMapper.map(locomotive, LocomotiveDto.class)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listResponse);
	}
	
	 
	 @Operation(summary = "Get the origin with more destinations.", description = "Get the origin with more destinations.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."),
	    		@ApiResponse(responseCode = "404", description = "Not itineraries found.")
	        })
	@GetMapping("/reports/origin-with-more-destinations")
	public ResponseEntity<String> originWithMoreDestinations() {
		 String stringResponse = reportesService.originMoreDestinations();
		 return ResponseEntity.ok().body(stringResponse);
	}
	 
	 
	 @Operation(summary = "Get a combination of itineraries.", description = "Get a combination of itineraries that can cover a specified itinerary.")
	    @ApiResponses(value = {
	    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."),
	    		@ApiResponse(responseCode = "404", description = "Not itineraries found.")
	        })	
	@GetMapping("/reports/cover-itinerary/{id}")
	public ResponseEntity<String> coverItinerary(@PathVariable("id") @Parameter(name = "id", description = "Itinerary id", example = "1") int id) {
		String stringResponse = reportesService.coverItinerary(id);
		return ResponseEntity.ok().body(stringResponse);
	}
	
}


