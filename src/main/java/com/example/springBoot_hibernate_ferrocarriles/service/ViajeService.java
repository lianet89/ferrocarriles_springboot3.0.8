package com.example.springBoot_hibernate_ferrocarriles.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.springBoot_hibernate_ferrocarriles.exception.ResourceNotFoundException;
import com.example.springBoot_hibernate_ferrocarriles.model.EquipoDeTraccion;
import com.example.springBoot_hibernate_ferrocarriles.model.Itinerario;
import com.example.springBoot_hibernate_ferrocarriles.model.Viaje;
import com.example.springBoot_hibernate_ferrocarriles.repository.ViajeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ViajeService {	
	private final ViajeRepository viajeRepository;
	private final EquipoDeTraccionService equipoDeTraccionService;
	

	@Autowired
	public ViajeService(final ViajeRepository viajeRepository, final EquipoDeTraccionService equipoDeTraccionService) {
		this.viajeRepository = viajeRepository;		
		this.equipoDeTraccionService = equipoDeTraccionService;
	}
	
	@CachePut(value = "travels")
	public List<Viaje> getAllViajes() {
		log.info("Listing all travels.");
		List<Viaje> viajes = viajeRepository.findAll();
		if(viajes.isEmpty()) { 			
			throw new ResourceNotFoundException("Not travels found");
		}
		 return viajes;		
	}
	
	@Cacheable(value = "travel")
	public Viaje getViajeById(Long idViaje) {
		log.info("Obtainig a travel by ID:", idViaje);
		return viajeRepository.findById(idViaje)
				.orElseThrow(()-> new ResourceNotFoundException("Not found a travel with id="+idViaje));	
	}
	
	public Viaje addViaje(Itinerario itinerario) {	
		log.info("Adding a travel:{}", itinerario);
		Viaje nuevoViaje = new Viaje();		
		int numeroViaje = viajeRepository.findAll().size()+1;
		boolean equipoDisponible =false;
		List<EquipoDeTraccion> linea = equipoDeTraccionService.getAllEquipoDeTraccion();
		
		if(linea.isEmpty()) {
			throw new ResourceNotFoundException("Not traction equipments found.");
		}
		
	    Comparator<EquipoDeTraccion> porKilometraje = Comparator.comparing(EquipoDeTraccion::getKilometrajeRecorrido);
	    Collections.sort(linea, porKilometraje);
	        
	    for (EquipoDeTraccion equipo:linea) {
	         if (equipo.cantidadMaxCoches()>=itinerario.getCantidadVagones() && equipo.getLineaDeTrenes()==1){
	             equipoDisponible = true;
	             nuevoViaje = new Viaje(numeroViaje, itinerario, equipo);
	             viajeRepository.save(nuevoViaje);
	             equipo.setLineaDeTrenes(2);
	             equipoDeTraccionService.updateEquipoDeTraccion((long) equipo.getNumeroIdentificacion(), equipo);
	             break;
	         }
	    }
	     if (equipoDisponible == false){
	    	 throw new ResourceNotFoundException("Not traction equipments available for the itinerary " + itinerario.getProvinciaOrigen() 
	    	 			+ " - " + itinerario.getProvinciaDestino());
	     }			
		return nuevoViaje;
	}
	
	public Viaje updateViaje(int numeroViaje, Itinerario itinerario, EquipoDeTraccion equipoDeTraccion) {	
		log.info("Updating a travel:{}", numeroViaje);
		viajeRepository.findById((long)numeroViaje).orElseThrow(()-> new ResourceNotFoundException("Not found a travel with id="+ numeroViaje));
		Viaje travel = new Viaje(numeroViaje, itinerario, equipoDeTraccion);
		return viajeRepository.save(travel);
	}
	
	
	public void deleteViaje(Long numeroViaje) {
		log.info("Deleting a travel:{}", numeroViaje);
		Viaje viaje = viajeRepository.findById((long)numeroViaje).orElseThrow(()-> new ResourceNotFoundException("Not found a travel with id="+ numeroViaje));
		EquipoDeTraccion equipo = viaje.getEquipoDeTraccion();
		int kilometrajeActual = equipo.getKilometrajeRecorrido() + viaje.getItinerario().getCantidadKilometros();
		viajeRepository.deleteById(numeroViaje);
		equipo.setKilometrajeRecorrido(kilometrajeActual);
		equipo.setLineaDeTrenes(1);
		equipoDeTraccionService.updateEquipoDeTraccion((long) equipo.getNumeroIdentificacion(), equipo);		
	}
	
}
