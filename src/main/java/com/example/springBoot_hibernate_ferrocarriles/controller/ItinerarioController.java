package com.example.springBoot_hibernate_ferrocarriles.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.transform.Source;
import javax.xml.validation.Validator;

import org.modelmapper.ModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.springBoot_hibernate_ferrocarriles.dto.ItineraryDto;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;
import com.example.springBoot_hibernate_ferrocarriles.service.ItinerarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;


@RestController
@Tag(name = "Itineraries", description = "Itineraries of Ferrocarriles API.")
public class ItinerarioController {	   
    @Autowired
    ItinerarioService itinerarioService;   
    
    @Autowired
	ModelMapper modelMapper;
    
    
    @Operation(summary = "Get all itineraries.", description = "Get all itineraries.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."),
    		@ApiResponse(responseCode = "404", description = "Not itineraries found.")
        })
    @GetMapping("/itineraries")
    private ResponseEntity<List<ItineraryDto>> getAllItinerarios() {
    	List<ItineraryDto> listResponse = itinerarioService.getAllItinerarios().stream()
    			.map(itineraties -> modelMapper.map(itineraties, ItineraryDto.class)).collect(Collectors.toList());
    	return ResponseEntity.ok().body(listResponse);
    }
    
    
    @Operation(summary = "Get an itinerary by id.", description = "Get an itinerary as per the id.")
    @ApiResponses(value = {
    		@ApiResponse(responseCode = "200", description = "Successfully retrieved."), 
            @ApiResponse(responseCode = "404", description = "Not found - The itinerary was not found.")
        })
    @GetMapping("/itineraries/{id}")
    private ResponseEntity<ItineraryDto> getItinerarioById(@PathVariable("id") @Parameter(name = "id", description = "Itinerary id", example = "1") Long id) {
    	Itinerario itinerary = itinerarioService.getItinerarioById(id); 
    	return ResponseEntity.ok().body(modelMapper.map(itinerary, ItineraryDto.class));
    }
    
    
    @Operation(summary = "Add an itinerary by id.", description = "Add an itinerary as per the id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created.") 
        })
    @PostMapping("/itineraries")
    private ResponseEntity<ItineraryDto> addItinerario(@Valid @RequestBody @Parameter(name = "itinerary", description = "Itinerary to add.") ItineraryDto itineraryDto) {
    	Itinerario itineraryRequest = modelMapper.map(itineraryDto, Itinerario.class);
    	Itinerario itinerary = itinerarioService.addItinerario(itineraryRequest);
    	ItineraryDto itineraryDtoResponse = modelMapper.map(itinerary, ItineraryDto.class);
    	return new ResponseEntity<ItineraryDto>(itineraryDtoResponse, HttpStatus.CREATED);	
    }
    
    
    @Operation(summary = "Update an itinerary by id.", description = "Update an itinerary as per the id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated."), 
            @ApiResponse(responseCode = "400", description = "Bad Request.")
        })
    @PutMapping("/itineraries/{id}")
    private ResponseEntity<ItineraryDto> updateItinerario(@Valid @PathVariable("id") @Parameter(name = "id", description = "Itinerary id", example = "1") Long id,  
    		@RequestBody @Parameter(name = "itinerary", description = "Itinerary properties") ItineraryDto itineraryDto) throws Exception{
    	Itinerario itineraryRequest = modelMapper.map(itineraryDto, Itinerario.class);
    	Itinerario itineraryUpdated = itinerarioService.updateItinerario(id, itineraryRequest);
    	ItineraryDto itineraryDtoResponse = modelMapper.map(itineraryUpdated, ItineraryDto.class);
	    return ResponseEntity.ok().body(itineraryDtoResponse);    	
    }
    
    
    @Operation(summary = "Delete an itinerary by id", description = "Delete an itinerary as per the id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted."), 
            @ApiResponse(responseCode = "404", description = "Not found - The itinerary was not found.")
        })
    @DeleteMapping("/itineraries/{id}")
    private ResponseEntity<String>deleteItinerario(@PathVariable("id") @Parameter(name = "id", description = "Itinerary id", example = "1") Long id) {
    	itinerarioService.deleteItinerario(id);
	    return new ResponseEntity<String>("Itinerary deleted successfully", HttpStatus.OK);
    }
    
}
