package com.example.springBoot_hibernate_ferrocarriles.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	public List<Viaje> getAllViajes() throws Exception {
		log.info("Listing all travels.");
		List<Viaje> viajes = new ArrayList<Viaje>();
		try {
			viajeRepository.findAll().forEach(viaje1->viajes.add(viaje1));
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return viajes;		
	}
	
	public Viaje getViajeById(Long idViaje) throws Exception {
		log.info("Obtainig a travel by ID:", idViaje);
		Viaje viaje = new Viaje ();
		try {
			viaje = viajeRepository.findById(idViaje).get();		
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return viaje;	
	}
	
	public Viaje addViaje(Itinerario itinerario) throws Exception {	
		log.info("Adding a travel:{}", itinerario);
		Viaje nuevoViaje = new Viaje();
		try {
			int numeroViaje = viajeRepository.findAll().size()+1;
			boolean equipoDisponible =false;
			List<EquipoDeTraccion> linea = equipoDeTraccionService.getAllEquipoDeTraccion();	        
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
	        if (equipoDisponible ==false){
	            System.out.println("No existen equipos disponibles para el itinerario nÃºmero "+ itinerario.getNumeroIdentificacion()+".");
	        }	
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		}
		return nuevoViaje;
	}
	
	public Viaje updateViaje(Long numeroViaje, Itinerario itinerario, EquipoDeTraccion equipoDeTraccion) throws Exception {	
		log.info("Updating a travel:{}", numeroViaje);
		Viaje viajeSalvado = new Viaje ();
		try {
			Viaje travel = viajeRepository.getReferenceById(numeroViaje);
			travel.setEquipoDeTraccion(equipoDeTraccion);
			travel.setItinerario(itinerario);
			viajeSalvado = viajeRepository.save(travel);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		} return viajeSalvado;
	}
	
	
	public void deleteViaje(Long idViaje) throws Exception {
		log.info("Deleting a travel:{}", idViaje);
		try {
			Viaje viaje = viajeRepository.findById(idViaje).get();
			EquipoDeTraccion equipo = viaje.getEquipoDeTraccion();
			int kilometrajeActual = equipo.getKilometrajeRecorrido() + viaje.getItinerario().getCantidadKilometros();
			viajeRepository.deleteById(idViaje);
			equipo.setKilometrajeRecorrido(kilometrajeActual);
			equipo.setLineaDeTrenes(1);
			equipoDeTraccionService.updateEquipoDeTraccion((long) equipo.getNumeroIdentificacion(), equipo);
		} catch(Exception ex) {
			System.out.println("An error has occurred: " + ex.getMessage());
		}
	}
	
}
