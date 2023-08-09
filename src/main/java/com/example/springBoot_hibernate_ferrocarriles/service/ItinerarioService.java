package com.example.springBoot_hibernate_ferrocarriles.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<Itinerario> getAllItinerarios() throws Exception {
		log.info("Listing all itineraries.");
		List<Itinerario> itinerarios=new ArrayList<Itinerario>();
		try {
		itinerarioRepository.findAll().forEach(itinerario1->itinerarios.add(itinerario1));
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return itinerarios;
		
	}
	
	public Itinerario getItinerarioById(Long id) throws Exception {
		log.info("Obtaining an itinerary by ID:{}", id);
		Itinerario itinerario = new Itinerario();
		try {
			itinerario = itinerarioRepository.findById(id).get();
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return itinerario;
	}
	
	public Itinerario addItinerario(Itinerario itinerario) throws Exception {
		log.info("Adding an itinerary:{}", itinerario);
		Itinerario itinerarioSalvado = new Itinerario();
		try {
			itinerarioSalvado = itinerarioRepository.save(itinerario);	
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return itinerarioSalvado;
	}
	
	public Itinerario updateItinerario(Long id, Itinerario itinerario) throws Exception {
		log.info("Updating an itinerary:{}", itinerario);
		Itinerario itinerarioSalvado = new Itinerario();
		try {
			itinerarioSalvado = itinerarioRepository.save(itinerario);
			Itinerario itinerary = itinerarioRepository.getReferenceById(id);
			itinerary.setCantidadKilometros(itinerario.getCantidadKilometros());
			itinerary.setCantidadVagones(itinerario.getCantidadVagones());
			itinerary.setProvinciaDestino(itinerario.getProvinciaDestino());
			itinerary.setProvinciaOrigen(itinerario.getProvinciaOrigen());
			itinerarioSalvado = itinerarioRepository.save(itinerary);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return itinerarioSalvado;
	}
	
	public void deleteItinerario(Long id) throws Exception {
		log.info("Deleting an itinerary by ID:{}", id);
		try {
			itinerarioRepository.deleteById(id);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} 
	}
	
}
