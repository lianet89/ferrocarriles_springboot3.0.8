package com.example.springBoot_hibernate_ferrocarriles.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;
import com.example.springBoot_hibernate_ferrocarriles.repository.ItinerarioRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItinerarioService {
	private final ItinerarioRepository itinerarioRepository;
	
	@Autowired
	public ItinerarioService(final ItinerarioRepository itinerarioRepository) {
		this.itinerarioRepository = itinerarioRepository;
	}
	
	@CachePut(value = "itineraries")
	public List<Itinerario> getAllItinerarios() {
		log.info("Listing all itineraries.");
		List<Itinerario> itinerarios=itinerarioRepository.findAll();	
		if(itinerarios.isEmpty()) { 
			throw new ResourceNotFoundException("Not itineraries found.");
		}
		return itinerarios;		
	}
	
	@Cacheable(value = "itinerary")
	public Itinerario getItinerarioById(Long id) {
		log.info("Obtaining an itinerary by ID:{}", id);
		return itinerarioRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Not found an itinerary with id="+id));
	}
	
	public Itinerario addItinerario(Itinerario itinerario) {
		log.info("Adding an itinerary:{}", itinerario);
		return itinerarioRepository.save(itinerario);
	}
	
	public Itinerario updateItinerario(Long id, Itinerario itinerario) throws Exception{
		log.info("Updating an itinerary:{}", itinerario);
		itinerarioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found an itinerary with id="+id));
		return itinerarioRepository.save(itinerario);
	}
	
	public void deleteItinerario(Long id) {
		log.info("Deleting an itinerary by ID:{}", id);
		itinerarioRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Not found  an itinerary with id="+id));
		itinerarioRepository.deleteById(id);		 
	}
	
}
